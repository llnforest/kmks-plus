package com.kmks.w2.utils;

import com.kmks.w2.domain.dto.DispatchCenterDto;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.utils.LogUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * UDP工具类
 *
 * @author Star
 * @description: TODO
 * @date 2023/12/15 11:29
 */
@Slf4j
public class TcpUtils {
    // 车载 plateNum->channel
    private static Map<String, Channel> carChannelMap = new HashMap<>();
    private static String responseStartMark = "$ZX";

    private static Map<String, DispatchCenterDto> dispatchCenterMap = new TreeMap<>();


    /**
     * 获取调度中心数据
     *
     * @return {@link Map}<{@link String}, {@link DispatchCenterDto}>
     */
    public static Map<String, DispatchCenterDto> getDispatchCenterMap() {
        return dispatchCenterMap;
    }

    /**
     * 获取调度中心对应车辆数据
     *
     * @return {@link Map}<{@link String}, {@link DispatchCenterDto}>
     */
    public static DispatchCenterDto getDispatchCenterDto(String kcbh) {
        if (dispatchCenterMap.containsKey(kcbh)) {
            return dispatchCenterMap.get(kcbh);
        }
        throw new FailException("考车不在线");
    }

    /**
     * 设置信息初始化状态
     *
     * @return {@link Map}<{@link String}, {@link DispatchCenterDto}>
     */
    public static void setDispatchCenterDtoZt(String kcbh, Boolean zt) {
        if (dispatchCenterMap.containsKey(kcbh)) {
            dispatchCenterMap.get(kcbh).setZt(zt ? 1 : 0);
            return;
        }
        throw new FailException("考车不在线");
    }

    /**
     * 设置上传监管状态
     *
     * @return {@link Map}<{@link String}, {@link DispatchCenterDto}>
     */
    public static void setDispatchCenterDtoSczt(String kcbh, String sczt) {
        if (dispatchCenterMap.containsKey(kcbh)) {
            dispatchCenterMap.get(kcbh).setSczt(sczt);
            return;
        }
        throw new FailException("考车不在线");
    }

    /**
     * 回答
     *
     * @param channel 频道
     * @param mark    指定标记
     * @param result  结果
     * @param data    数据
     */
    public static void response(Channel channel, String mark, Boolean result, List<String> data) {
        // 插入头
        data.add(0, mark);
        // 插入结果
        data.add(1, result ? "1" : "0");
        String message = responseStartMark + ";" + String.join(";", data) + "\r\n";
        LogUtils.tcp(">>>{}", message);
        channel.writeAndFlush(message.getBytes(Charset.forName("GBK")));
    }

    /**
     * 发送到车载
     *
     * @param plateNum 车牌号
     * @param message  消息
     */
    public static void sendToCar(String plateNum, byte[] message) {
        if (carChannelMap.containsKey(plateNum)) {
            try {
                LogUtils.tcp(">>>{}", new String(message, "GBK"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            carChannelMap.get(plateNum).writeAndFlush(message);
        }

    }


    /**
     * 添加车载
     *
     * @param plateNum 车牌号
     * @param channel  频道
     */
    public static void addCarChannel(String plateNum, Channel channel) {
        carChannelMap.put(plateNum, channel);
    }

    /**
     * 获取车载频道
     *
     * @param plateNum 车牌号
     * @return {@link Channel}
     */
    public static Channel getCarChannel(String plateNum) {
        return carChannelMap.get(plateNum);
    }

    /**
     * 删除车载
     *
     * @param plateNum 车牌号
     */
    public static void removeCarChannel(String plateNum) {
        carChannelMap.remove(plateNum);
    }

    /**
     * 删除车载
     *
     * @param channel 频道
     */
    public static void removeCarChannel(Channel channel) {
//        carChannelMap.keySet().removeIf(key->channel.remoteAddress().toString().equals(carChannelMap.get(key).remoteAddress().toString()));
        carChannelMap.keySet().removeIf(key -> {
            if (channel.remoteAddress() == carChannelMap.get(key).remoteAddress()) {
                dispatchCenterMap.get(key).setKszt("考车下线");
                return true;
            }
            return false;
        });
    }
}
