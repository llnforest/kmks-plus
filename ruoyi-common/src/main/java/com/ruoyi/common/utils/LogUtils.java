package com.ruoyi.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/23 15:33
 */
@Slf4j
public class LogUtils {
    
    private static final int MAX_LOG_LENGTH = 100;
    public static final Marker tcp = MarkerFactory.getMarker("TCP");

    public static final Marker server = MarkerFactory.getMarker("SERVER");

    public static void tcp(String s, Object... arguments) {
        log.info(tcp, s, arguments);
    }

    public static void server(String s, Object... arguments) {
        log.info(server, s, arguments);
    }

    /**
     * 格式化消息
     *
     * @param s         s
     * @param arguments 论据
     * @return {@link String}
     */
    private static String formatMessage(String s, Object... arguments) {
        // 格式化消息，类似于String.format()
        String message = String.format(s, arguments);
        if (message.length() > MAX_LOG_LENGTH) {
            return message.substring(0, MAX_LOG_LENGTH) + "..."; // 截断并添加省略号
        } else {
            return message;
        }
    }
}
