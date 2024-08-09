package com.kmks.w2.netty.decoder;


import cn.hutool.core.date.DateUtil;
import com.kmks.w2.netty.handler.MessageHandler;
import com.kmks.w2.utils.TcpUtils;
import com.ruoyi.common.utils.LogUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Star
 * @description: TODO
 * @date 2023/12/6 14:04
 */
@Component
@Slf4j
public class TcpMessageDecoder {
    private final String mark = "$CZ";
    private final String splitMark = ";";
    @Resource
    private MessageHandler messageHandler;

    public Object tcpInstance;
    public Map<String, Method> tcpMethodsMap = new HashMap<>();

    @PostConstruct
    public void tcpInit() throws Exception {

        Class<? extends MessageHandler> aClass = messageHandler.getClass();
        tcpInstance = messageHandler;
        for (Method method : aClass.getMethods()) {
            tcpMethodsMap.put(method.getName(), method);
        }
    }


    /**
     * 处理消息
     * 1）消息首尾以#
     * 2）消息标记和消息体之间以:分割
     *
     * @param message 消息
     * @throws Exception 例外
     */
    public void handleMessage(String message, ChannelHandlerContext ctx) throws Exception {
        // 处理TCP消息的逻辑
        if (!message.contains(splitMark)) return;
        // 验证首$CZ
        if (!message.startsWith(mark)) return;
        LogUtils.tcp("<<<{}",message);
        // 去除换行和分割
        String[] splits = message.replaceAll("[\r\n]+$", "").split(splitMark);
        if (tcpMethodsMap.containsKey("cz"+splits[1])) {
            tcpMethodsMap.get("cz"+splits[1]).invoke(tcpInstance, ctx.channel(), Arrays.copyOfRange(splits, 1, splits.length));
        }else{
            TcpUtils.response(ctx.channel(),splits[1],false,new ArrayList<>(Arrays.asList("无此指令")));
        }
    }


}
