package com.kmks.w2.hcnet;

import com.kmks.w2.hcnet.sdk.HCNetSDK;
import com.ruoyi.common.utils.LogUtils;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author Star
 * @description: TODO
 * @date 2024/8/9 15:06
 */
@Slf4j
public class HCNetTool {
    public static HCNetSDK hCNetSDK = null;

    public static int lUserID = -1;

    public static void load() {
        hCNetSDK = (HCNetSDK) Native.loadLibrary("D:\\kmksService\\commonEnv\\hcNet\\dll\\HCNetSDK.dll", HCNetSDK.class);
    }

    public static boolean init() {
        if (lUserID == -1) {
            boolean init = hCNetSDK.NET_DVR_Init();
            LogUtils.hc("初始化：{}", init);
            if (!init) {
                return init;
            }

        }
        return hCNetSDK.NET_DVR_Init();
    }

    /**
     * @param m_sDeviceIP 设备ip地址
     * @param wPort       端口号，设备网络SDK登录默认端口8000
     * @param m_sUsername 用户名
     * @param m_sPassword 密码
     */
    public static void login(String m_sDeviceIP, short wPort, String m_sUsername, String m_sPassword) {
        /* 注册 */
        // 设备登录信息
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();

        // 设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());
        m_strLoginInfo.wPort = wPort;
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());
        // 是否异步登录：false- 否，true- 是
        m_strLoginInfo.bUseAsynLogin = false;
        // write()调用后数据才写入到内存中
        m_strLoginInfo.write();
        lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);

        if (lUserID == -1) {
            System.out.println("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            System.out.println("登录成功！");

            // read()后，结构体中才有对应的数据
            m_strDeviceInfo.read();
            return;
        }
    }


    //设备注销 SDK释放
    public static void logout() {
        if (lUserID >= 0) {
            if (hCNetSDK.NET_DVR_Logout(lUserID) == false) {
                System.out.println("注销失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            }
            System.out.println("注销成功");
            hCNetSDK.NET_DVR_Cleanup();
            return;
        } else {
            System.out.println("设备未登录");
            hCNetSDK.NET_DVR_Cleanup();
            return;
        }
    }

    /**
     * 测试文字覆盖
     **/
    public static Boolean setOsdText() {
        HCNetSDK.NET_DVR_OUTPUT_OSD_CFG osdCfg = new HCNetSDK.NET_DVR_OUTPUT_OSD_CFG();
        int dwOsdWinNo = (1 << 24) | (1 << 16) | 6;
        int nSize = osdCfg.size();
        IntByReference dwReturn = new IntByReference(0);
        osdCfg.write();
        if (!hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_OUTPUT_OSD_CFG, dwOsdWinNo, osdCfg.getPointer(), nSize, dwReturn)) {
            log.info("OSD获取失败: NET_DVR_OUTPUT_OSD_CFG failed, error code=" + hCNetSDK.NET_DVR_GetLastError());
            return false;
        } else {
            osdCfg.read();
        }


        osdCfg.byOSDColor = 2;
        osdCfg.byFontSize = 2;
        osdCfg.byEnabled = 1;
        osdCfg.dwOsdWinNo = 0;
        osdCfg.dzSize = osdCfg.size();// 必须写死128
        byte[] osdByteContent = "测试内容，这是一个测试内容".getBytes(Charset.forName("GBK"));
        byte[] osdByte = new byte[64];
        System.arraycopy(osdByteContent, 0, osdByte, 0, Math.min(osdByteContent.length, 64));
        osdCfg.byContent = osdByte;

        osdCfg.struRect.dwXCoordinate = 20;
        osdCfg.struRect.dwYCoordinate = 1460;
        osdCfg.struRect.dwHeight = 80;
        osdCfg.struRect.dwWidth = 400;

        osdCfg.write();
        boolean b = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_OUTPUT_OSD_CFG, dwOsdWinNo, osdCfg.getPointer(), osdCfg.size());
        osdCfg.read();
        log.info("text:{}", hCNetSDK.NET_DVR_GetLastError());
        log.info("result:{}", b);
        return b;
    }

    public static Boolean setVideo() {
        HCNetSDK.NET_DVR_IPC_PROTO_LIST m_struProtoList = new HCNetSDK.NET_DVR_IPC_PROTO_LIST();
        m_struProtoList.dwSize = m_struProtoList.size(); // 设置结构体大小

        // 使用本地方法获取IPC协议列表
        m_struProtoList.write();
        boolean result = hCNetSDK.NET_DVR_GetIPCProtoList(lUserID, m_struProtoList.getPointer());
        m_struProtoList.read();
        if (!result) {
            log.info("获取IPC协议列表失败");
        } else {
            String text = new String(m_struProtoList.struProto[0].byDescribe);
            log.info("获取IPC协议列表成功:{}", text);
        }

        HCNetSDK.NET_DVR_PU_STREAM_CFG_V41 cfg_v41 = new HCNetSDK.NET_DVR_PU_STREAM_CFG_V41();
        int dwDecChanNum = (1 << 24) | (1 << 16) | 1;


        cfg_v41.dwSize = cfg_v41.size();
        cfg_v41.byStreamMode = 1;

        cfg_v41.uDecStreamMode.struDecStreamDev.struStreamMediaSvrCfg.byValid = 0;

        byte[] devIpContent = "192.168.1.83".getBytes(Charset.forName("GBK"));
        byte[] devIpByte = new byte[64];
        System.arraycopy(devIpContent, 0, devIpByte, 0, Math.min(devIpContent.length, 64));

        byte[] devNameContent = "admin".getBytes(Charset.forName("GBK"));
        byte[] devNameByte = new byte[32];
        System.arraycopy(devNameContent, 0, devNameByte, 0, Math.min(devNameContent.length, 32));

        byte[] devPassContent = "dzjl8888".getBytes(Charset.forName("GBK"));
        byte[] devPassByte = new byte[16];
        System.arraycopy(devPassContent, 0, devPassByte, 0, Math.min(devPassContent.length, 16));
        // 设置union 类型（必须要）
        cfg_v41.uDecStreamMode.setType(HCNetSDK.NET_DVR_DEC_STREAM_DEV_EX.class);

        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byChanType = 0;//通道类型：0-普通通道，1-零通道，2-流ID，3-本地输入源
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.dwChannel = 1;//通道号，通道类型为普通通道，零通道，本地输入源，虚拟屏服务器通道，拼接通道，屏幕服务器，分布式网络源时填此字段
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.wDVRPort = 8000;//端口号
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byTransProtocol = 0;//传输协议类型0-TCP，1-UDP
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byTransMode = 0;//传输码流模式 0－主码流 1－子码流
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byFactoryType = (byte) m_struProtoList.struProto[0].dwType;//前端设备厂家类型,通过接口获取

        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.byAddress = devIpByte;//设备域名
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.sUserName = devNameByte;//布防主机登陆帐号
        cfg_v41.uDecStreamMode.struDecStreamDev.struDevChanInfo.sPassword = devPassByte;//布防主机密码

        cfg_v41.write();
        boolean b = hCNetSDK.NET_DVR_MatrixStartDynamic_V41(lUserID, dwDecChanNum, cfg_v41.getPointer());
        cfg_v41.read();

        String devIp = new String(devIpByte);
        String devName = new String(devNameByte);
        String devPass = new String(devPassByte);

        log.info("IP:{},name:{},pass:{}", devIp, devName, devPass);
        log.info("error:{}", hCNetSDK.NET_DVR_GetLastError());
        log.info("result:{}", b);
        return b;
    }

    public static Boolean showImg() {
        HCNetSDK.NET_DVR_OUTPUT_PIC_CFG pic_cfg = new HCNetSDK.NET_DVR_OUTPUT_PIC_CFG();
        int dwOsdWinNo = (1 << 24) | (1 << 16) | 1;

        pic_cfg.dwSize = pic_cfg.size();
        pic_cfg.dwOutputPicNo = 2; // 图片序号
        pic_cfg.byEnabled = 1;
        pic_cfg.byFlash = 0;
        pic_cfg.byTranslucent = 0;

        pic_cfg.struRect.dwXCoordinate = 0;
        pic_cfg.struRect.dwYCoordinate = 960;
        pic_cfg.struRect.dwHeight = 960;
        pic_cfg.struRect.dwWidth = 960;

        pic_cfg.write();
        boolean b = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_OUTPUT_PIC_WIN_CFG, dwOsdWinNo, pic_cfg.getPointer(), pic_cfg.size());
        pic_cfg.read();
        log.info("text:{}", hCNetSDK.NET_DVR_GetLastError());
        log.info("result:{}", b);
        return b;
    }


    public static Boolean setImgInfo() {
        HCNetSDK.NET_DVR_OUTPUT_PIC_INFO pic_info = new HCNetSDK.NET_DVR_OUTPUT_PIC_INFO();
        byte[] picNameContent = "01".getBytes(Charset.forName("GBK"));
        byte[] picNameByte = new byte[32];
        System.arraycopy(picNameContent, 0, picNameByte, 0, Math.min(picNameContent.length, 32));


        pic_info.dwSize = pic_info.size();
        pic_info.byUsed = 2; // 图片序号
        pic_info.sPicName = picNameByte;

        pic_info.write();
        boolean b = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_OUTPUT_PIC_INFO, 1, pic_info.getPointer(), pic_info.size());
        pic_info.read();
        log.info("设置图片参数:{}", b);
        log.info("设置图片参数error:{}", hCNetSDK.NET_DVR_GetLastError());
        return b;
    }

    public static long uploadImg() {
        String fileName = "D:\\驾考BS升级\\合码器\\1.bmp";
        HCNetSDK.NET_DVR_PICCFG net_dvr_piccfg = new HCNetSDK.NET_DVR_PICCFG();
        net_dvr_piccfg.dwSize = net_dvr_piccfg.size();
        net_dvr_piccfg.byUseType = 4; //1- 底图，2- GIF图片，3- CAD图片，4- 输出口图片
        net_dvr_piccfg.bySequence = 2; // 图片序号
//        net_dvr_piccfg.byWallNo = 1;

        byte[] sPicNameContent = "01".getBytes(Charset.forName("GBK"));
        byte[] sPicNameByte = new byte[32];
        System.arraycopy(sPicNameContent, 0, sPicNameByte, 0, Math.min(sPicNameContent.length, 32));
        net_dvr_piccfg.sPicName = sPicNameByte;

        net_dvr_piccfg.struBasemapCfg.byScreenIndex = 1;
        net_dvr_piccfg.struBasemapCfg.byMapNum = 1;
        net_dvr_piccfg.struBasemapCfg.wSourWidth = 0;
        net_dvr_piccfg.struBasemapCfg.wSourHeight = 0;
        net_dvr_piccfg.write();
        long result = hCNetSDK.NET_DVR_PicUpload(lUserID, fileName, net_dvr_piccfg.getPointer());
        net_dvr_piccfg.read();
        log.info("上传结果：{}", result);
        if (result == -1l) log.info("上传error:{}", hCNetSDK.NET_DVR_GetLastError());
        return result;
    }

    public static void getUploadStatus(long lUploadHandle) throws Exception {
        // 4294967295
        long l = hCNetSDK.NET_DVR_GetPicUploadState(lUploadHandle);
        log.info("上传状态：{}", l);


        while (true) {
            long process = hCNetSDK.NET_DVR_GetPicUploadProgress(lUploadHandle);
            log.info("上传进度：{}", process);
            if (process >= 100l) {
                boolean result = hCNetSDK.NET_DVR_CloseUploadHandle(lUploadHandle);
                log.info("关闭上传状态：{}", result);
                break;
            }
            Thread.sleep(1000);
        }

    }

    public static Boolean removeImg() {
        HCNetSDK.NET_DVR_DELETE_OUPUT_PIC ouput_pic = new HCNetSDK.NET_DVR_DELETE_OUPUT_PIC();
        ouput_pic.bySequence = 1;// 图片序号
        ouput_pic.write();
        boolean b = hCNetSDK.NET_DVR_RemoteControl(lUserID, HCNetSDK.NET_DVR_DELETE_OUPUT_PIC, ouput_pic.getPointer(), ouput_pic.size());
        ouput_pic.read();
        log.info("删除状态：{}", b);
        if (!b) {
            log.info("删除error:{}", hCNetSDK.NET_DVR_GetLastError());
        }
        return b;
    }
}



