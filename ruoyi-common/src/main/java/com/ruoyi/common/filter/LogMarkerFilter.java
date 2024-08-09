package com.ruoyi.common.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.MarkerFactory;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/23 17:21
 */
public class LogMarkerFilter extends Filter<ILoggingEvent> {
    private String marker;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getMarker() != null && event.getMarker().contains(MarkerFactory.getMarker(marker))) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
