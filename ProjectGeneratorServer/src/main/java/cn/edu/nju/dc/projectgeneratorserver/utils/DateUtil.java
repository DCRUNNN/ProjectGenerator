package cn.edu.nju.dc.projectgeneratorserver.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author dc
 * @date 2019/12/17 23:17
 */
public class DateUtil
{

    private DateUtil()
    {

    }

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentFormatTime()
    {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN, Locale.CHINA));
    }
}
