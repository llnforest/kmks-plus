package com.kmks.w2.netty.handler;

import cn.hutool.core.date.DateUtil;
import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.dto.DispatchCenterDto;
import com.kmks.w2.domain.gateDto.FaceRecognizeBo;
import com.kmks.w2.domain.gateDto.ScoreResultDto;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.domain.vo.W2RecordsVo;
import com.kmks.w2.hcnet.service.HCNetService;
import com.kmks.w2.service.ICarService;
import com.kmks.w2.service.IFaceService;
import com.kmks.w2.service.IW2KcxxService;
import com.kmks.w2.utils.FileUtil;
import com.kmks.w2.utils.TcpUtils;
import com.kmks.w2.websocket.map.DispatchDataMap;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.OsdNumberEnum;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * 消息处理程序
 *
 * @author Star
 * @description: TODO
 * @date 2023/12/6 12:02
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageHandler {
    private final ICarService carService;
    private final IFaceService faceService;

    private final HCNetService hcNetService;


    private String delimiter = "\\|";


    /**
     * 考车连接
     * <p>
     * 格式 指令标志;车辆编号
     * 格式 1000;1234
     * 指令 $CZ;1000;02
     * <p>
     * 回复：$ZX;1000;结果标志
     * 指令：$ZX;1000;1
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz1000(Channel channel, String[] message) {
        // 处理调度中心页面展示数据
        try {
            W2KcxxVo w2KcxxVo = carService.carOnLine(message[1]);

            //发送至合码器
            //考车信息
            hcNetService.refreshOSD(w2KcxxVo.getKch(), "考车信息：" + w2KcxxVo.getKch() + "号车 " + w2KcxxVo.getCph(), OsdNumberEnum.CARINFO);
            //考试证型
            hcNetService.refreshOSD(w2KcxxVo.getKch(), "考试证型：" + w2KcxxVo.getKscx(), OsdNumberEnum.CERT);
            //考试状态
            hcNetService.refreshOSD(w2KcxxVo.getKch(), "考试状态：考车上线", OsdNumberEnum.STATUS);

            Map<String, DispatchCenterDto> dispatchCenterMap = TcpUtils.getDispatchCenterMap();
            //已存在上线车辆
            if (dispatchCenterMap.containsKey(message[1])) {
                dispatchCenterMap.get(message[1]).setKszt("考车上线");
            } else {
                //不存在上线车辆
                DispatchCenterDto dispatchCenterDto = new DispatchCenterDto();
                dispatchCenterDto.setKszt("考车上线");
                dispatchCenterDto.setSczt("未上传");
                dispatchCenterDto.setZt(1);
                dispatchCenterDto.setKcbh(message[1]);
                dispatchCenterDto.setKcxx(w2KcxxVo.getCph());
                dispatchCenterMap.put(message[1], dispatchCenterDto);
            }
            TcpUtils.addCarChannel(message[1], channel);
            TcpUtils.response(channel, message[0], true, new ArrayList<>());
        } catch (FailException e) {
            handleException(channel, message, e);
        }

    }

    /**
     * 时间同步
     * <p>
     * 格式 指令标志;车辆编号
     * 格式 1100;1234
     * 指令 $CZ;1100;1234
     * <p>
     * 回复：$ZX;1100;结果标志;时间
     * 指令：$ZX;1100;1;2024-04-25 10:11:11
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz1100(Channel channel, String[] message) {
        handleInitTcp(message[1], "时间同步");
        try {
            // 同步调度中心数据
            String time = carService.syncTime();
            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(time)));
        } catch (FailException e) {
            handleException(channel, message, e);
        }

    }


    /**
     * 呼叫中心
     * <p>
     * 格式 指令标志;车辆编号;时间
     * 格式 1111;02;2024-04-25 10:11:11
     * 指令 $CZ;1111;02;2024-04-25 10:11:11
     * <p>
     * 回复：$ZX;1111;结果标志
     * 指令：$ZX;1111;1
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz1111(Channel channel, String[] message) {
        log.info("msg:{}", message);
        //TODO: 调度中心页面提示呼叫中心
        DispatchDataMap.getDispatchSocketsBeanMap().forEach((key, value) -> {
            try {
                value.session.getBasicRemote().sendText(JsonUtils.toJsonString(R.ok(4, message[1] + "号车呼叫中心")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        TcpUtils.response(channel, message[0], true, new ArrayList<>());
    }

    /**
     * 开始考试（人工手动申请）
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;姓名;考试科目;速度
     * 格式 2000;02;2024-04-25 10:11:11;220681199211100013;张贺博;2;0
     * 指令 $CZ;2000;02;2024-04-25 10:11:11;220681199211100013;张贺博;2;0
     * <p>
     * 回复：$ZX;2000;结果标志;证件号码;姓名;驾校名称;考试车型;第几次;考试项目;考试科目
     * 指令：$ZX;2000;1;3421221*;李四;八一驾校;C1;1;23,24,25;2
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2000(Channel channel, String[] message) {
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "人工开始");
            W2QueuingVo queuingVo = carService.applyExam(message[1], message[2], message[3], message[5], "");
            dispatchCenterDto.setKsfs(queuingVo.getScore());
            dispatchCenterDto.setKscj("5");
            dispatchCenterDto.setKscs(queuingVo.getDjc());
            dispatchCenterDto.setKsxm(queuingVo.getXm());
            dispatchCenterDto.setZjhm(queuingVo.getZjhm());
            //发送至合码器
            //考生姓名
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考生姓名：" + queuingVo.getXm(), OsdNumberEnum.NAME);
            //证件号码
            hcNetService.refreshOSD(queuingVo.getKcbh(), "证件号码：" + queuingVo.getZjhm(), OsdNumberEnum.IDCARD);
            //考试状态
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试状态：人工开始", OsdNumberEnum.STATUS);
            //考试项目
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试项目：-", OsdNumberEnum.PROJECT);
            //考试成绩
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试成绩：-", OsdNumberEnum.SCORE);
            //考试扣分
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试扣分：-", OsdNumberEnum.DISCOUNT);
            //速度里程
            hcNetService.refreshOSD(queuingVo.getKcbh(), "速度里程：-", OsdNumberEnum.DISCOUNT);

            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(queuingVo.getZjhm(), queuingVo.getXm(), StringUtils.isBlank(queuingVo.getJxmc()) ? "" : queuingVo.getJxmc(), queuingVo.getKscx(), String.valueOf(queuingVo.getDjc()), queuingVo.getZkxms(), queuingVo.getKskm())));
        } catch (FailException e) {
            handleException(channel, message, e);
        }
    }

    /**
     * 考试申请
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;姓名;考试科目;照片;速度
     * 格式 2001;02;2024-04-25 10:11:11;220681199211100013;张贺博;2;**;0
     * 指令 $CZ;2001;02;2024-04-25 10:11:11;220681199211100013;张贺博;2;**;0
     * <p>
     * 回复：$ZX;2001;结果标志;驾校名称;考试车型;第几次;考试项目;是否夜考;扣分;路线;是否正常考试;流水号;考试员档案号;考试员身份证;考试科目;是否特殊处理
     * 指令：$ZX;2001;1;;C1;1;1021,22,23,24,25;0;0;;1;;;;2;0
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2001(Channel channel, String[] message) {
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "考试申请");
            W2QueuingVo queuingVo = carService.applyExam(message[1], message[2], message[3], message[5], message[6]);
            dispatchCenterDto.setKsfs(queuingVo.getScore());
            dispatchCenterDto.setKscj("5");
            dispatchCenterDto.setKscs(queuingVo.getDjc());
            dispatchCenterDto.setKsxm(queuingVo.getXm());
            dispatchCenterDto.setZjhm(queuingVo.getZjhm());
            //发送至合码器
            //考生姓名
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考生姓名：" + queuingVo.getXm(), OsdNumberEnum.NAME);
            //证件号码
            hcNetService.refreshOSD(queuingVo.getKcbh(), "证件号码：" + queuingVo.getZjhm(), OsdNumberEnum.IDCARD);
            //考试状态
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试状态：申请考试", OsdNumberEnum.STATUS);
            //考试项目
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试项目：-", OsdNumberEnum.PROJECT);
            //考试成绩
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试成绩：-", OsdNumberEnum.SCORE);
            //考试扣分
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试扣分：-", OsdNumberEnum.DISCOUNT);
            //速度里程
            hcNetService.refreshOSD(queuingVo.getKcbh(), "速度里程：-", OsdNumberEnum.DISCOUNT);

            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(StringUtils.isBlank(queuingVo.getJxmc()) ? "" : queuingVo.getJxmc(), queuingVo.getKscx(), String.valueOf(queuingVo.getDjc()), queuingVo.getZkxms(), queuingVo.getSfyk(), String.valueOf(100l - queuingVo.getScore()), queuingVo.getRLine() == null ? "" : String.valueOf(queuingVo.getRLine()), "1", StringUtils.isBlank(queuingVo.getLsh()) ? "" : queuingVo.getLsh(), "", StringUtils.isBlank(queuingVo.getKg()) ? "" : queuingVo.getKg(), queuingVo.getKskm(), "0")));
        } catch (FailException e) {
            handleException(channel, message, e);
        }
    }

    /**
     * 考试开始
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;照片;速度;校验
     * 格式 2002;02;2024-04-25 10:11:11;220681199211100013;**;0;checkBit
     * 指令 $CZ;2002;02;2024-04-25 10:11:11;220681199211100013;**;0;checkBit
     * <p>
     * 回复：$ZX;2002;结果标志;校验
     * 指令：$ZX;2002;1;checkBit
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2002(Channel channel, String[] message) {
        String checkBit = message[6];
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "考试开始");
            W2QueuingVo queuingVo = carService.startExam(message[1], message[2], message[3], message[4], message[5]);
            dispatchCenterDto.setKsfs(queuingVo.getScore());
            dispatchCenterDto.setKscj("5");
            //发送至合码器
            //考试状态
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试状态：考试开始", OsdNumberEnum.STATUS);
            //成绩次数
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试成绩：" + queuingVo.getScore() + " 第" + queuingVo.getDjc() + "次", OsdNumberEnum.SCORE);
            //考试扣分
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试扣分：" + (100l - queuingVo.getScore()), OsdNumberEnum.DISCOUNT);

            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(checkBit)));
        } catch (FailException e) {
            handleException(channel, message, e, checkBit);
        }
    }

    /**
     * 考试结束
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;分数;照片;速度;校验位
     * 格式 2003;02;2024-04-25 10:11:11;220681199211100013;100;zp;18;checkBit
     * 指令 $CZ;2003;02;2024-04-25 10:11:11;220681199211100013;100;zp;18;checkBit
     * <p>
     * 回复：$ZX;2003;结果标志;校验位
     * 指令：$ZX;2003;1;checkBit
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2003(Channel channel, String[] message) {
        String checkBit = message[7];
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "考试结束");
            W2QueuingVo queuingVo = carService.finishExam(message[1], message[2], message[3], message[4], message[5], message[6]);

            dispatchCenterDto.setKsfs(queuingVo.getScore());
            dispatchCenterDto.setKscj(String.valueOf(queuingVo.getKscj()));


            //发送至合码器
            //考试状态
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试状态：考试结束", OsdNumberEnum.STATUS);


            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(checkBit)));
        } catch (FailException e) {
            handleException(channel, message, e, checkBit);
        }
    }


    /**
     * 项目开始
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;项目名称;项目ID;照片;速度;校验
     * 格式 2004;02;2024-04-25 10:11:11;220681199211100013;倒车入库;2502;zp;18;checkBit
     * 指令 $CZ;2004;02;2024-04-25 10:11:11;220681199211100013;倒车入库;2502;zp;18;checkBit
     * <p>
     * 回复：$ZX;2004;结果标志;校验
     * 指令：$ZX;2004;1;checkBit
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2004(Channel channel, String[] message) {
        String checkBit = message[8];
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "项目开始");
            String dqxm = carService.startProgram(message[1], message[2], message[3], message[5], message[6], message[7]);


            //发送至合码器
            //考试状态
            hcNetService.refreshOSD(message[1], "考试状态：项目开始", OsdNumberEnum.STATUS);
            //考试项目
            hcNetService.refreshOSD(message[1], "考试项目：" + dqxm, OsdNumberEnum.PROJECT);

            // 切换场地摄像头
            hcNetService.dynamicDecode(message[1], message[5]);

            dispatchCenterDto.setDqxm(dqxm);

            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(checkBit)));
        } catch (FailException e) {
            handleException(channel, message, e, checkBit);
        }
    }

    /**
     * 项目结束
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;项目名称;项目ID;照片;速度;校验
     * 格式 2005;02;2024-04-25 10:11:11;220681199211100013;倒车入库;2199;zp;18;checkBit
     * 指令 $CZ;2005;02;2024-04-25 10:11:11;220681199211100013;倒车入库;2199;zp;18;checkBit
     * <p>
     * 回复：$ZX;2005;结果标志;校验
     * 指令：$ZX;2005;1;checkBit
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2005(Channel channel, String[] message) {
        String checkBit = message[8];
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "项目结束");
            W2QueuingVo queuingVo = carService.finishProgram(message[1], message[2], message[3], message[5], message[6], message[7]);

            //发送至合码器
            //考试状态
            hcNetService.refreshOSD(message[1], "考试状态：项目结束", OsdNumberEnum.STATUS);

            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(checkBit)));
        } catch (FailException e) {
            handleException(channel, message, e, checkBit);
        }
    }

    /**
     * 项目扣分
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;项目名称;项目ID;扣分代码;扣分值;扣分明细;扣分类型;照片;速度;校验
     * 格式 2006;02;2024-04-25 10:11:11;220681199211100013;倒车入库;2199;20401;100;车辆入库停止后，车身出线;1;zp;18;checkBit
     * 指令 $CZ;2006;02;2024-04-25 10:11:11;220681199211100013;倒车入库;2199;20401;100;车辆入库停止后，车身出线;1;zp;18;checkBit
     * <p>
     * 回复：$ZX;2006;结果标志;校验
     * 指令：$ZX;2006;1;checkBit
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2006(Channel channel, String[] message) {
        String checkBit = message[12];
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "上传扣分");
            W2QueuingVo queuingVo = carService.deductPoint(message[1], message[2], message[3], message[5], message[6], message[9], message[10], message[11]);
            dispatchCenterDto.setKsfs(queuingVo.getScore());
            ScoreResultDto scoreResultDto = carService.handleScoreResult(queuingVo.getKskm(), queuingVo.getDjc(), queuingVo.getScore());
            dispatchCenterDto.setKscj(scoreResultDto.getKsjg());

            //发送至合码器
            //成绩次数
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试成绩：" + queuingVo.getScore() + " 第" + queuingVo.getDjc() + "次", OsdNumberEnum.SCORE);
            //考试扣分
            hcNetService.refreshOSD(queuingVo.getKcbh(), "考试扣分：" + (100l - queuingVo.getScore()), OsdNumberEnum.DISCOUNT);

            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(checkBit)));
        } catch (FailException e) {
            handleException(channel, message, e, checkBit);
        }
    }

    /**
     * 查找下一个项目ID
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证
     * 格式 2010;02;2024-04-25 10:11:11;220681199211100013
     * 指令 $CZ;2010;02;2024-04-25 10:11:11;220681199211100013
     * <p>
     * 回复：$ZX;2010;结果标志;项目ID
     * 指令：$ZX;2010;1;23
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2010(Channel channel, String[] message) {
        log.info("msg:{}", message);
        //TODO: 未找到对应接口


        TcpUtils.response(channel, message[0], false, new ArrayList<>(Arrays.asList("10")));
    }


    /**
     * 查找下一个考生
     * <p>
     * 格式 指令标志;车辆编号;时间;校验
     * 格式 2011;02;2024-04-25 10:11:11;checkBit
     * 指令 $CZ;2011;02;2024-04-25 10:11:11;checkBit
     * <p>
     * 回复：$ZX;2011;结果标志;姓名;身份证;指纹;线路;夜考;校验
     * 指令：$ZX;2011;1;王五;**;**;3;1;checkBit
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2011(Channel channel, String[] message) {
        String checkBit = message[3];
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "查找下一个考生");
            W2QueuingVo queuingVo = carService.getNextStudent(message[1]);

            dispatchCenterDto.setKsxm(queuingVo.getXm());
            dispatchCenterDto.setZjhm(queuingVo.getZjhm());
            dispatchCenterDto.setKskm(queuingVo.getKskm());
            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(queuingVo.getXm(), queuingVo.getZjhm(), StringUtils.isBlank(queuingVo.getFinger()) ? "" : queuingVo.getFinger(), queuingVo.getRLine() == null ? "" : String.valueOf(queuingVo.getRLine()), queuingVo.getSfyk(), checkBit)));
        } catch (FailException e) {
            handleException(channel, message, e, checkBit);
        }
    }

    /**
     * 查找下一个考生
     * <p>
     * 格式 指令标志;车辆编号;时间;校验
     * 格式 2012;02;2024-04-25 10:11:11;checkBit
     * 指令 $CZ;2012;02;2024-04-25 10:11:11;checkBit
     * <p>
     * 回复：$ZX;2012;结果标志;姓名;身份证;指纹;线路;夜考;校验
     * 指令：$ZX;2012;1;王五;**;**;3;1;checkBit
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2012(Channel channel, String[] message) {
        // 同cz2011
        String checkBit = message[3];
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "查找下一个考生");
            W2QueuingVo queuingVo = carService.getNextStudent(message[1]);

            dispatchCenterDto.setKsxm(queuingVo.getXm());
            dispatchCenterDto.setZjhm(queuingVo.getZjhm());
            dispatchCenterDto.setKskm(queuingVo.getKskm());
            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(queuingVo.getXm(), queuingVo.getZjhm(), StringUtils.isBlank(queuingVo.getFinger()) ? "" : queuingVo.getFinger(), queuingVo.getRLine() == null ? "" : String.valueOf(queuingVo.getRLine()), queuingVo.getSfyk(), checkBit)));
        } catch (FailException e) {
            handleException(channel, message, e, checkBit);
        }
    }

    /**
     * 人脸比对
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;姓名;拍照图片
     * 格式 2018;02;2024-04-25 10:11:11;**;王三;**
     * 指令 $CZ;2018;02;2024-04-25 10:11:11;**;王三;**
     * <p>
     * 回复：$ZX;2018;结果标志
     * 指令：$ZX;2018;1
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz2018(Channel channel, String[] message) {
        try {
            // 同步调度中心数据
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "人证比对");
            R<Void> result = carService.getFaceRecognizeResult(message[3], message[5]);
            //比对结果
            if (result.getCode() == R.SUCCESS) {
                TcpUtils.response(channel, message[0], true, new ArrayList<>());
                return;
            }
            dispatchCenterDto.setZt(0);
            TcpUtils.response(channel, message[0], false, new ArrayList<>(Arrays.asList(result.getMsg())));
        } catch (FailException e) {
            handleException(channel, message, e);
        }

    }

    /**
     * 查询图片
     * 获取大网照或门禁照片
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;标志
     * 格式 3002;02;2024-04-25 10:11:11;**;1
     * 指令 $CZ;3002;02;2024-04-25 10:11:11;**;1
     * <p>
     * 回复：$ZX;3002;结果标志;图片
     * 指令：$ZX;3002;1;**
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz3002(Channel channel, String[] message) {
        try {
            DispatchCenterDto dispatchCenterDto = handleInitTcp(message[1], "查询照片");
            W2RecordsVo todayStudent = carService.getTodayStudent(message[3], message[1]);

            TcpUtils.response(channel, message[0], true, new ArrayList<>(Arrays.asList(FileUtil.convertImageToBase64(todayStudent.getJbzp()))));
        } catch (FailException e) {
            handleException(channel, message, e);
        }
    }

    /**
     * 车辆距离速度
     * <p>
     * 格式 指令标志;车辆编号;时间;身份证;距离;车速
     * 格式 8888;02;2024-04-25 10:11:11;220681199211100013;1515;5
     * 指令 $CZ;8888;02;2024-04-25 10:11:11;220681199211100013;1515;5
     * <p>
     * 回复：$ZX;8888;结果标志
     * 指令：$ZX;8888;1
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz8888(Channel channel, String[] message) {
        // 同步调度中心数据
        try {
            //发送至合码器
            //速度里程
            hcNetService.refreshOSD(message[1], "速度：" + message[5] + "里程：" + message[4], OsdNumberEnum.SPEED);
            //考试时间
            hcNetService.refreshOSD(message[1], "考试时间：" + message[2], OsdNumberEnum.TIME);
            //上传照片
            hcNetService.setVideoPicture(message[1]);

            DispatchCenterDto dispatchCenterDto = TcpUtils.getDispatchCenterDto(message[1]);
            dispatchCenterDto.setLc(message[4]);
            dispatchCenterDto.setCs(message[5]);
            TcpUtils.response(channel, message[0], true, new ArrayList<>());
        } catch (FailException e) {
            TcpUtils.response(channel, message[0], false, new ArrayList<>(Arrays.asList(e.getMessage())));
        }
    }

    /**
     * 照片比对（自定义）
     * <p>
     * 格式 指令标志;身份证;证件照片;拍照图片
     * 格式 30100;341523199801019001;**;**
     * 指令 $CZ;30100;341523199801019001;**;**
     * <p>
     * 回复：$ZX;30100;结果标志
     * 指令：$ZX;30100;1
     *
     * @param message 消息 imei
     * @param channel channel
     */
    public void cz30100(Channel channel, String[] message) {
        try {
            FaceRecognizeBo faceRecognizeBo = new FaceRecognizeBo();
            faceRecognizeBo.setZjhm(message[1]);
            faceRecognizeBo.setZjzp(message[2]);
            faceRecognizeBo.setJbzp(message[3]);
            // 同步调度中心数据
            Boolean result = faceService.faceRecognize(faceRecognizeBo);
            //比对结果
            if (result) {
                TcpUtils.response(channel, message[0], true, new ArrayList<>());
                return;
            }
            TcpUtils.response(channel, message[0], false, new ArrayList<>(Arrays.asList("照片比对不相似")));
        } catch (FailException e) {
            handleException(channel, message, e);
        }

    }

    /**
     * 下发评判
     * <p>
     * 下发：$ZX;6000;结果;考车编号;时间;扣分代码;扣分值;扣分信息
     * 指令：$ZX;6000;1;05;2024-06-16 12:01:12;20302;100;车辆停止后，车身距离路边缘线超出50cm
     *
     * @param channel channel
     * @param mark    做记号
     * @param message 消息
     */
    public void zx6000(Channel channel, String mark, String[] message) {
        // 同步调度中心数据
        DispatchCenterDto dispatchCenterDto = handleInitTcp(message[0], "下发评判");
        TcpUtils.response(channel, mark, true, new ArrayList<>(Arrays.asList(message)));
    }

    private void handleException(Channel channel, String[] message, FailException e) {
        TcpUtils.response(channel, message[0], false, new ArrayList<>(Arrays.asList(e.getMessage())));
        TcpUtils.setDispatchCenterDtoZt(message[1], false);
    }

    private void handleException(Channel channel, String[] message, FailException e, String checkBit) {
        TcpUtils.response(channel, message[0], false, new ArrayList<>(Arrays.asList(e.getMessage(), checkBit)));
        TcpUtils.setDispatchCenterDtoZt(message[1], false);
    }

    private DispatchCenterDto handleInitTcp(String kcbh, String kszt) {
        DispatchCenterDto dispatchCenterDto = TcpUtils.getDispatchCenterDto(kcbh);
        dispatchCenterDto.setSczt("未上传");
        dispatchCenterDto.setZt(1);
        dispatchCenterDto.setKszt(kszt);
        return dispatchCenterDto;
    }
}
