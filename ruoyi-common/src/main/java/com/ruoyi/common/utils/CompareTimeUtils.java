package com.ruoyi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Star
 * @description: TODO
 * @date 2023/3/2 15:10
 */
public class CompareTimeUtils {
    /**
     * 判断时间是否在时间段内
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }
    /**
     * 比较一个 HH:mm:ss 是否在一个时间段内
     * 如：14:33:00 是否在 09:30:00 和 12:00:00 内
     */
    public static boolean timeIsInRound(String str1, String start, String end,String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date now = null;
        Date beginTime = null;
        Date endTime = null;

        try {
            now = df.parse(str1);
            beginTime = df.parse(start);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return belongCalendar(now, beginTime, endTime);
    }

    /**
     * 14:33:00 是否在 09:30:00 - 12:00:00 内
     *
     * @param str1  14:33:00
     * @param round 09:30:00 - 12:00:00
     */
    public static boolean timeIsInRound(String str1, String round, String pattern) {
        String[] roundTime = round.split("-");
        return timeIsInRound(str1, roundTime[0], roundTime[1],pattern);
    }
}
