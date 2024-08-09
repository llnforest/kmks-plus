package com.ruoyi.common.filter;

/**
 * @author Star
 * @description: TODO
 * @date 2024/8/7 9:50
 */

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;

public class LengthLimitingTurboFilter extends TurboFilter {
    private int maxLength = 1000;

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        if (format != null && format.length() > maxLength) {
            format = format.substring(0, maxLength) + "...";
        }

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof String) {
                    String param = (String) params[i];
                    if (param.length() > maxLength) {
                        params[i] = param.substring(0, maxLength) + "...";
                    }
                }
            }
        }

        return FilterReply.NEUTRAL;
    }
}

