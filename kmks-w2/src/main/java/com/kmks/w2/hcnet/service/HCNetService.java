package com.kmks.w2.hcnet.service;

import com.kmks.w2.domain.bo.W2ConfigCarBo;
import com.kmks.w2.domain.bo.W2ConfigDeviceBo;
import com.kmks.w2.domain.bo.W2ConfigSwitchBo;
import com.kmks.w2.domain.vo.W2ConfigCarVo;
import com.kmks.w2.domain.vo.W2ConfigDeviceVo;
import com.kmks.w2.domain.vo.W2ConfigSwitchVo;
import com.kmks.w2.hcnet.sdk.HCNetSDK;
import com.kmks.w2.service.IW2ConfigCarService;
import com.kmks.w2.service.IW2ConfigDeviceService;
import com.kmks.w2.service.IW2ConfigSwitchService;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.enums.OsdNumberEnum;
import com.ruoyi.common.exception.hc.HcException;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.sun.jna.Native;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Star
 * @description: TODO
 * @date 2024/8/19 11:36
 */
@Service
public class HCNetService {

    public HCNetSDK hCNetSDK = null;

    public Map<Long,Integer> userIdMap = new HashMap<>();

    public Map<String,W2ConfigCarVo> configCarMap = new HashMap<>();
    public Map<Long,W2ConfigDeviceVo> configDeviceMap = new HashMap<>();
    public Map<String,W2ConfigSwitchVo> configSwitchMap = new HashMap<>();

    @Resource
    private ISysConfigService configService;

    @Resource
    private IW2ConfigCarService configCarService;

    @Resource
    private IW2ConfigDeviceService configDeviceService;

    @Resource
    private IW2ConfigSwitchService configSwitchService;

    /**
     * 初始化和登录
     *
     * @param carNo 考车编号
     * @return {@link Boolean}
     */
    public Boolean initAndLogin(String carNo){
        // 判断是否开启合码器
        if(!configService.selectConfigByKey(CacheNames.HC_OPEN).equals("1")){
            return false;
        }
        this.init();
        this.initData();
        Long deviceNo = getConfigCarVo(carNo).getDeviceno();
        if(!userIdMap.containsKey(deviceNo)){
            W2ConfigDeviceVo w2ConfigDeviceVo = getConfigDeviceVo(deviceNo);
            Boolean login = login(carNo, w2ConfigDeviceVo);
            if(!login){
                throw new HcException("考车号【{}】设备号【{}】登录失败",carNo,deviceNo);
            }
        }
        return true;
    }


    /**
     * 全部注销 SDK释放
     */
    public void logoutAll() {
        for (Map.Entry<String, W2ConfigCarVo> entry : configCarMap.entrySet()) {
            logoutByDevice(entry.getValue().getDeviceno());
        }
        hCNetSDK.NET_DVR_Cleanup();
    }

    /**
     * 刷新文字信息
     *
     * @param carNo         考车编号
     * @param content       显示类型
     * @param osdNumberEnum osd编号枚举
     * @return {@link Boolean}
     */
    @Async
    public Boolean refreshOSD(String carNo, String content, OsdNumberEnum osdNumberEnum){
        if(!initAndLogin(carNo)){
            return false;
        }
        LogUtils.hc("【刷新文字信息】考车号:{},内容:{}", carNo, content);
        int osdNumber = osdNumberEnum.ordinal() + 1;
        HCNetSDK.NET_DVR_OUTPUT_OSD_CFG osdCfg = new HCNetSDK.NET_DVR_OUTPUT_OSD_CFG();
        int dwOsdWinNo =(1 << 24) | (configCarMap.get(carNo).getDeviceoutputno().intValue() << 16) | osdNumber;

        osdCfg.byOSDColor = 2;
        osdCfg.byFontSize = 2;
        osdCfg.byEnabled = 1;
        osdCfg.dwOsdWinNo = 0;
        osdCfg.dzSize = osdCfg.size();// 必须写死128

        byte[] osdByteContent = content.getBytes(Charset.forName("GBK"));
        byte[] osdByte = new byte[64];
        System.arraycopy(osdByteContent, 0, osdByte, 0, Math.min(osdByteContent.length, 64));
        osdCfg.byContent = osdByte;

        osdCfg.struRect.dwXCoordinate = 20;
        osdCfg.struRect.dwYCoordinate = 980 + osdNumber * 80;
        osdCfg.struRect.dwHeight = 80;
        osdCfg.struRect.dwWidth = 900;

        osdCfg.write();
        boolean result = hCNetSDK.NET_DVR_SetDVRConfig(userIdMap.get(configCarMap.get(carNo).getDeviceno()), HCNetSDK.NET_DVR_SET_OUTPUT_OSD_CFG, dwOsdWinNo, osdCfg.getPointer(), osdCfg.size());
        osdCfg.read();
        if(!result){
            LogUtils.hc("【刷新文字信息】失败原因:{}", hCNetSDK.NET_DVR_GetLastError());
        }
        return result;
    }

    /**
     * 动态解码
     *
     * @param carNo     考车编号
     * @param projectCode 项目代码
     * @return {@link Boolean}
     */
    @Async
    public Boolean dynamicDecode(String carNo,String projectCode){
        if(!initAndLogin(carNo)){
            return false;
        }
        W2ConfigCarVo configCarVo = getConfigCarVo(carNo);
        W2ConfigSwitchVo configSwitchVo = getConfigSwitchVo(projectCode);
        HCNetSDK.NET_DVR_PU_STREAM_CFG_V41 cfg_v41 = new HCNetSDK.NET_DVR_PU_STREAM_CFG_V41();
        LogUtils.hc("【动态解码】：考车编号:{}，项目代码:{}：IP:{},name:{},pass:{}", carNo, projectCode, configSwitchVo.getDeviceip(),configSwitchVo.getDeviceusername(),configSwitchVo.getDevicepassword());

        int lUserID = userIdMap.get(configCarVo.getDeviceno());
//        int dwDecChanNum =(1 << 24) | (1 << 16) | 1;//1 字节电视墙号+1 字节子窗口号+2 字节窗口号，例如：1<<24 | 1<<16 | 1
        int dwDecChanNum =configCarVo.getVideochannel().intValue();//子窗口号(组合)

        cfg_v41.dwSize = cfg_v41.size();
        cfg_v41.byStreamMode = 1;/*取流模式，0-无效，1-通过IP或域名取流，2-通过URL取流,3-通过动态域名解析向设备取流*/
        cfg_v41.uDecStreamMode.struDecStreamDev.struStreamMediaSvrCfg.byValid = 0;

        byte[] devIpContent = configSwitchVo.getDeviceip().getBytes(Charset.forName("GBK"));
        byte[] devIpByte = new byte[64];
        System.arraycopy(devIpContent, 0, devIpByte, 0, Math.min(devIpContent.length, 64));

        byte[] devNameContent = configSwitchVo.getDeviceusername().getBytes(Charset.forName("GBK"));
        byte[] devNameByte = new byte[32];
        System.arraycopy(devNameContent, 0, devNameByte, 0, Math.min(devNameContent.length, 32));

        byte[] devPassContent = configSwitchVo.getDevicepassword().getBytes(Charset.forName("GBK"));
        byte[] devPassByte = new byte[16];
        System.arraycopy(devPassContent, 0, devPassByte, 0, Math.min(devPassContent.length, 16));
        // 设置union 类型（必须要）
        cfg_v41.uDecStreamMode.setType(HCNetSDK.NET_DVR_DEC_STREAM_DEV_EX.class);

        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byChanType = configSwitchVo.getChanneltype().byteValue();//通道类型：0-普通通道，1-零通道，2-流ID，3-本地输入源
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.dwChannel = configSwitchVo.getDevicechannel().byteValue();//通道号，通道类型为普通通道，零通道，本地输入源，虚拟屏服务器通道，拼接通道，屏幕服务器，分布式网络源时填此字段
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.wDVRPort= Short.parseShort(configSwitchVo.getDeviceport());//端口号
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byTransProtocol=configSwitchVo.getProtocol().byteValue();//传输协议类型0-TCP，1-UDP
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byTransMode=configSwitchVo.getBitstreamtype().byteValue();//传输码流模式 0－主码流 1－子码流
//        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byFactoryType= (byte) m_struProtoList.struProto[0].dwType;//前端设备厂家类型,通过接口获取
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byFactoryType= configSwitchVo.getVendortype().byteValue();//前端设备厂家类型,通过接口获取

        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byAddress = devIpByte;//设备域名
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.sUserName=devNameByte;//布防主机登陆帐号
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.sPassword=devPassByte;//布防主机密码

        cfg_v41.write();
        boolean result = hCNetSDK.NET_DVR_MatrixStartDynamic_V41(lUserID, dwDecChanNum, cfg_v41.getPointer());
        cfg_v41.read();


        if(!result){
            LogUtils.hc("【动态解码】失败原因:{}", hCNetSDK.NET_DVR_GetLastError());
        }
        return result;
    }

    /**
     * 停止动态解码
     *
     * @param carNo 考车编号
     * @return {@link Boolean}
     */
    @Async
    public Boolean stopDynamic(String carNo){
        if(!initAndLogin(carNo)){
            return false;
        }
        LogUtils.hc("【停止动态解码】考车号:{}", carNo);
        W2ConfigCarVo configCarVo = getConfigCarVo(carNo);
        boolean result = hCNetSDK.NET_DVR_MatrixStopDynamic(userIdMap.get(configCarVo.getDeviceno()), configCarVo.getVideochannel().intValue());
        if(!result){
            LogUtils.hc("【停止动态解码】失败原因:{}", hCNetSDK.NET_DVR_GetLastError());
        }
        return result;

    }


    /**
     * 设置视频图片
     *
     * @param carNo 考车编号
     * @return {@link Boolean}
     */
    @Async
    public Boolean setVideoPicture(String carNo){
        if(!initAndLogin(carNo)){
            return false;
        }
        int picSeq = getPicSeqNumber(carNo, false);
        removeUploadPicture(carNo,picSeq);
        long lUploadHandle = uploadVideoPic(carNo,picSeq);
        try {
            getUploadStatus(lUploadHandle);
        } catch (Exception e) {
            LogUtils.hc("【上传进度】考车编号:{},句柄:{},异常:{}",carNo,lUploadHandle,e.getMessage());
            throw new RuntimeException(e);
        }
        return showVideoPicture(carNo,picSeq);
    }

    /**
     * 显示视频图片
     *
     * @param carNo  考车便阿红
     * @param picSeq 图片序号
     * @return {@link Boolean}
     */
    public Boolean showVideoPicture(String carNo,int picSeq){
        W2ConfigCarVo configCarVo = getConfigCarVo(carNo);

        HCNetSDK.NET_DVR_OUTPUT_PIC_CFG pic_cfg = new HCNetSDK.NET_DVR_OUTPUT_PIC_CFG();
        int picWindowNum = (picSeq % 2 == 1) ? 1 : 2;
        int dwOsdWinNo =(1 << 24) | (configCarVo.getDeviceoutputno().intValue() << 16) | picWindowNum;

        pic_cfg.dwSize = pic_cfg.size();
        pic_cfg.dwOutputPicNo = picSeq; // 图片序号
        pic_cfg.byEnabled = 1;
        pic_cfg.byFlash = 0;
        pic_cfg.byTranslucent = 0;
        pic_cfg.dwOutputPicWinNo = dwOsdWinNo;//输出口图片窗口号(组合)：1 字节设备号+1 字节输出口号+2 字节输出口图片窗口序号，获取全部时有效

        pic_cfg.struRect.dwXCoordinate = (picWindowNum - 1)*960;
        pic_cfg.struRect.dwYCoordinate = 960;
        pic_cfg.struRect.dwHeight = 960;
        pic_cfg.struRect.dwWidth = 960;

        pic_cfg.write();
        boolean result = hCNetSDK.NET_DVR_SetDVRConfig(userIdMap.get(configCarVo.getDeviceno()), HCNetSDK.NET_DVR_SET_OUTPUT_PIC_WIN_CFG, dwOsdWinNo, pic_cfg.getPointer(), pic_cfg.size());
        pic_cfg.read();
        if(!result){
            LogUtils.hc("【图片显示设置】失败：{}",hCNetSDK.NET_DVR_GetLastError());
        }
        return result;
    }


    /**
     * 上传视频图片
     *
     * @param carNo  考车编号
     * @param picSeq 图片序号
     * @return long
     */
    public long uploadVideoPic(String carNo,int picSeq){
        String fileName = configService.selectConfigByKey(CacheNames.HC_trajectory_path)+carNo+".bmp";
        HCNetSDK.NET_DVR_PICCFG net_dvr_piccfg = new HCNetSDK.NET_DVR_PICCFG();
        net_dvr_piccfg.dwSize = net_dvr_piccfg.size();
        net_dvr_piccfg.byUseType = 4; //1- 底图，2- GIF图片，3- CAD图片，4- 输出口图片
        net_dvr_piccfg.bySequence = (byte) picSeq; // 图片序号
//        net_dvr_piccfg.byWallNo = 1;

        byte[] sPicNameContent = carNo.getBytes(Charset.forName("GBK"));
        byte[] sPicNameByte = new byte[32];
        System.arraycopy(sPicNameContent, 0, sPicNameByte, 0, Math.min(sPicNameContent.length, 32));
        net_dvr_piccfg.sPicName = sPicNameByte;

        net_dvr_piccfg.struBasemapCfg.byScreenIndex = 1;
        net_dvr_piccfg.struBasemapCfg.byMapNum = 1;
        net_dvr_piccfg.struBasemapCfg.wSourWidth = 0;
        net_dvr_piccfg.struBasemapCfg.wSourHeight = 0;

        net_dvr_piccfg.write();
        long result = hCNetSDK.NET_DVR_PicUpload(userIdMap.get(getConfigCarVo(carNo).getDeviceno()),fileName,net_dvr_piccfg.getPointer());
        net_dvr_piccfg.read();
        if(result == -1l){
            LogUtils.hc("【图片上传】{} 失败原因:{}",fileName,hCNetSDK.NET_DVR_GetLastError());
        }
        return result;
    }

    /**
     * 获取上传进度和关闭上传状态
     *
     * @param lUploadHandle 上传句柄
     * @throws Exception 例外
     */
    public void getUploadStatus(long lUploadHandle) throws Exception{
        // 4294967295
//        long l = hCNetSDK.NET_DVR_GetPicUploadState(lUploadHandle);
//        log.info("上传状态：{}",l);
        while(true){
            long process = hCNetSDK.NET_DVR_GetPicUploadProgress(lUploadHandle);
            LogUtils.hc("【上传进度】句柄:{}，上传进度：{}",process);
            if(process >= 100l){
                boolean result = hCNetSDK.NET_DVR_CloseUploadHandle(lUploadHandle);
                LogUtils.hc("【上传进度】句柄:{}，关闭上传状态：{}",lUploadHandle,result);
                break;
            }
            Thread.sleep(1000);
        }

    }

    /**
     * 删除上传图片
     *
     * @param carNo  考车便阿红
     * @param picSeq 图片序号
     * @return {@link Boolean}
     */
    public Boolean removeUploadPicture(String carNo,int picSeq){
        LogUtils.hc("【删除图片】考车编号:{},图片序号:{}",carNo,picSeq);
        HCNetSDK.NET_DVR_DELETE_OUPUT_PIC ouput_pic = new HCNetSDK.NET_DVR_DELETE_OUPUT_PIC();
        ouput_pic.bySequence = picSeq;// 图片序号
        ouput_pic.write();
        boolean b = hCNetSDK.NET_DVR_RemoteControl(userIdMap.get(getConfigCarVo(carNo).getDeviceno()), HCNetSDK.NET_DVR_DELETE_OUPUT_PIC, ouput_pic.getPointer(), ouput_pic.size());
        ouput_pic.read();
        if(!b){
            LogUtils.hc("【删除图片】失败原因:{}",hCNetSDK.NET_DVR_GetLastError());
        }
        return b;
    }

    /**
     * 获取图片序号
     *
     * @param carNo 汽车没有
     * @param flag  标记：true 门禁合成图   false 轨迹图
     * @return int
     */
    public int getPicSeqNumber(String carNo,boolean flag){
        Integer carNum = Integer.valueOf(carNo);
        int tempCarNo = carNum % 8;
        if (tempCarNo == 0) {
            tempCarNo = 8;
        }

        // 计算两种图片序号
        // true返回序号1  false返回序号2
        if (flag) {
            return tempCarNo * 2 - 1;
        } else {
            return tempCarNo * 2;
        }
    }

    /**
     * 按设备注销
     *
     * @param deviceNo 设备编号
     */
    private void logoutByDevice(Long deviceNo){
        if (userIdMap.containsKey(deviceNo) && userIdMap.get(deviceNo)>=0)
        {
            if (!hCNetSDK.NET_DVR_Logout(userIdMap.get(deviceNo))) {
                LogUtils.hc("【注销】设备编号:{} 注销失败，错误码为{}",deviceNo,hCNetSDK.NET_DVR_GetLastError());
            }else{
                userIdMap.remove(deviceNo);
                LogUtils.hc("【注销】设备编号:{} 注销成功",deviceNo);
            }
        }
        else{
            LogUtils.hc("【注销】设备未登录");
        }
    }

    /**
     * 获取设备编号
     *
     * @param carNo 考车编号
     * @return {@link W2ConfigCarVo}
     */
    private W2ConfigCarVo getConfigCarVo(String carNo){
        this.renderConfigCarMap();
        if(!configCarMap.containsKey(carNo)){
            LogUtils.hc("考车号【{}】不存在",carNo);
            throw new HcException("考车号【{}】不存在",carNo);
        }
        return configCarMap.get(carNo);
    }

    /**
     * 获取设备信息
     *
     * @param deviceNo 设备编号
     * @return {@link W2ConfigDeviceVo}
     */
    private W2ConfigDeviceVo getConfigDeviceVo(Long deviceNo){
        this.renderConfigDeviceMap();
        if(!configDeviceMap.containsKey(deviceNo)){
            LogUtils.hc("设备编号【{}】不存在",deviceNo);
            throw new HcException("设备编号【{}】不存在",deviceNo);
        }
        return configDeviceMap.get(deviceNo);
    }

    /**
     * 获取设备项目切换信息
     *
     * @param projectCode 项目代码
     * @return {@link W2ConfigSwitchVo}
     */
    public W2ConfigSwitchVo getConfigSwitchVo(String projectCode){
        renderConfigSwitchMap();
        if(!configSwitchMap.containsKey(projectCode)){
            LogUtils.hc("项目代码【{}】不存在",projectCode);
            throw new HcException("项目代码【{}】不存在",projectCode);
        }
        return configSwitchMap.get(projectCode);
    }

    /**
     * 渲染考车号和设备号对应关系
     */
    public void renderConfigCarMap(){
        if(configCarMap.isEmpty()){
            List<W2ConfigCarVo> w2ConfigCarVos = configCarService.queryList(new W2ConfigCarBo());
            configCarMap = w2ConfigCarVos.stream().collect(Collectors.toMap(W2ConfigCarVo::getCarno,v->v));
        }
    }

    /**
     * 渲染项目编号和设备对应关系
     */
    public void renderConfigSwitchMap(){
        if(configSwitchMap.isEmpty()){
            List<W2ConfigSwitchVo> w2ConfigSwitchVos = configSwitchService.queryList(new W2ConfigSwitchBo());
            configSwitchMap = w2ConfigSwitchVos.stream().collect(Collectors.toMap(W2ConfigSwitchVo::getProjectcode,v->v));
        }
    }

    /**
     * 渲染项目编号和设备对应关系
     */
    public void renderConfigDeviceMap(){
        if(configDeviceMap.isEmpty()){
            List<W2ConfigDeviceVo> w2ConfigDeviceVos = configDeviceService.queryList(new W2ConfigDeviceBo());
            configDeviceMap = w2ConfigDeviceVos.stream().collect(Collectors.toMap(W2ConfigDeviceVo::getDeviceno,v->v));
        }
    }

    /**
     * 登录
     *
     * @param carNo    考车编号
     * @param w2ConfigDeviceVo 合码器设备配置
     * @return {@link Boolean}
     */
    private Boolean login(String carNo,W2ConfigDeviceVo w2ConfigDeviceVo) {
        /* 注册 */
        // 设备登录信息
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();

        // 设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(w2ConfigDeviceVo.getDeviceip().getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, w2ConfigDeviceVo.getDeviceip().length());
        m_strLoginInfo.wPort =Short.parseShort(w2ConfigDeviceVo.getDeviceport()) ;
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(w2ConfigDeviceVo.getDevicename().getBytes(), 0, m_strLoginInfo.sUserName, 0, w2ConfigDeviceVo.getDevicename().length());
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(w2ConfigDeviceVo.getDeviceip().getBytes(), 0, m_strLoginInfo.sPassword, 0, w2ConfigDeviceVo.getDeviceip().length());
        // 是否异步登录：false- 否，true- 是
        m_strLoginInfo.bUseAsynLogin = false;
        // write()调用后数据才写入到内存中
        m_strLoginInfo.write();
        int lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);

        if (lUserID == -1) {
            LogUtils.hc("考车号【{}】关联设备号【{}】登录失败：{}，{}，{}，{}",carNo,w2ConfigDeviceVo.getDeviceno(),w2ConfigDeviceVo.getDeviceip(),w2ConfigDeviceVo.getDeviceport(),w2ConfigDeviceVo.getDevicename(),w2ConfigDeviceVo.getDevicepassword());
            return false;
        } else {
            LogUtils.hc("考车号【{}】关联设备号【{}】登录成功",carNo,w2ConfigDeviceVo.getDeviceno());
            userIdMap.put(w2ConfigDeviceVo.getDeviceno(),lUserID);
            // read()后，结构体中才有对应的数据
            m_strDeviceInfo.read();
            return true;
        }
    }

    /**
     * 加载和初始化
     *
     * @return {@link Boolean}
     */
    private Boolean init(){
        if(hCNetSDK == null){
            hCNetSDK = (HCNetSDK) Native.loadLibrary(configService.selectConfigByKey(CacheNames.HC_SDK_PATH), HCNetSDK.class);
            boolean init = hCNetSDK.NET_DVR_Init();
            LogUtils.hc("初始化状态：{}",init);
            if(!init){
                throw new HcException("设备初始化失败");
            }
        }
        return true;
    }

    /**
     * 初始化数据
     */
    public void initData(){
        renderConfigCarMap();
        renderConfigSwitchMap();
        renderConfigDeviceMap();
    }

    public void resetData(){
        configSwitchMap = new HashMap<>();
        configCarMap = new HashMap<>();
        configDeviceMap = new HashMap<>();
    }
}
