package com.bjsts.core.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class CoreDateUtils {
	private static final Logger logger = LoggerFactory.getLogger(CoreDateUtils.class.getName());
	
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd";
		
	public static String formatDate(Date date) {
		return formatDate(date, DATE);
	}
	
	public static String formatDateTime(Date date) {
		return formatDate(date, DATETIME);
	}

	public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern, Locale.CHINA);
	}

    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        if (datetime == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return datetime.format(formatter);
        } catch (Exception e) {
            logger.error("本地时间格式化错误, dateStr={}, pattern={}", datetime, pattern);
            logger.error(e.getMessage(), e);
            return null;
        }
    }
	
	public static String formatDate(String dateStr, String srcPattern, String desPattern) {
		Date date = parseDate(dateStr, srcPattern);
		if (date == null) {
			return null;
		}
		return formatDate(date, desPattern);
	}

	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, DATE);
	}
	public static Date parseDateTime(String dateStr) {
		return parseDate(dateStr, new String[] {
                DATETIME,
                "yyyy-MM-dd HH:mm:ss.SSS",
        });
	}

	public static Date parseDate(long timeMillis) {
        return Date.from(Instant.ofEpochMilli(timeMillis));
    }

	public static Date parseDate(String dateStr, String pattern) {
		return parseDate(StringUtils.trim(dateStr), new String[]{pattern});
	}

    public static Date parseDate(String dateStr, String[] patterns) {
        if (dateStr == null) {
            return null;
        }
        try {
            return DateUtils.parseDateStrictly(dateStr, patterns);
        } catch (ParseException e) {
            logger.error("日期转换错误, dateStr={}, pattern={}", dateStr, StringUtils.join(patterns, ","));
            logger.error(e.getMessage(), e);
            return null;
        }
    }

	public static boolean test(String dateStr, String pattern) {
		return test(dateStr, new String[]{pattern});
	}

    public static boolean test(String dateStr, String[] patterns) {
        if (dateStr == null) {
            return false;
        }
        try {
            DateUtils.parseDateStrictly(dateStr, patterns);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date convertTo(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime convertFrom(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 计算周岁年龄
     * @param birthday 生日日期
     * @return 年龄
     */
	public static int calculateAge(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.setTime(birthday);
        int birthYear = birthdayCalendar.get(Calendar.YEAR);

        int diffYears = currentYear - birthYear;
        if (diffYears < 0) {
            throw new RuntimeException("输入生日日期晚于当前时间");
        }
        birthdayCalendar.add(Calendar.YEAR, diffYears);

        if (birthdayCalendar.after(calendar)) {
            // 加上年份后在当前时间之后, 说明未满周岁
            return diffYears - 1;
        }

        return diffYears;
    }

    /**
     * 是否在2个时间段范围内
     * @param timeStr 时间段 格式: 23:00:00-06:00:00
     * @param date 待比较时间
     * @return boolean
     */
    public static boolean isBetweenTime(String timeStr, LocalDateTime date) {
        String[] startEnd = StringUtils.split(timeStr, "-");

        LocalTime start = LocalTime.parse(startEnd[0], DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime end = LocalTime.parse(startEnd[1], DateTimeFormatter.ofPattern("HH:mm:ss"));

        return date.toLocalTime().isAfter(start) && date.toLocalTime().isBefore(end.plusSeconds(1));
    }

    private static String[] chineseWeekDateName = new String[] {"日", "一", "二", "三", "四", "五", "六"};

    /**
     * 格式化显示中文的周几
     * @param date 日期
     * @return 周几
     */
    public static String formatChineseWeekDateName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int weekDate = calendar.get(Calendar.DAY_OF_WEEK);

        return "周" + chineseWeekDateName[weekDate - 1];
    }

    /**
     * 春节期间
     */
    public final static Map<Integer, String> springFestivalPeriodMap = Maps.newHashMap();
    static {
        springFestivalPeriodMap.put(2013, "2013-02-09 00:00:00|2013-02-15 00:00:00");
        springFestivalPeriodMap.put(2014, "2014-01-30 00:00:00|2014-02-05 00:00:00");
        springFestivalPeriodMap.put(2015, "2015-02-18 00:00:00|2015-02-24 00:00:00");
        springFestivalPeriodMap.put(2016, "2016-02-07 00:00:00|2016-02-13 00:00:00");
        springFestivalPeriodMap.put(2017, "2017-01-27 00:00:00|2017-02-03 00:00:00");
        springFestivalPeriodMap.put(2018, "2018-02-15 00:00:00|2018-02-22 00:00:00");
        springFestivalPeriodMap.put(2019, "2019-02-04 00:00:00|2019-02-11 00:00:00");
        springFestivalPeriodMap.put(2020, "2020-01-24 00:00:00|2020-01-31 00:00:00");
        springFestivalPeriodMap.put(2021, "2021-02-11 00:00:00|2021-02-17 00:00:00");
        springFestivalPeriodMap.put(2022, "2022-01-31 00:00:00|2022-02-06 00:00:00");
        springFestivalPeriodMap.put(2023, "2023-01-21 00:00:00|2023-01-27 00:00:00");
    }

    /**
     * 时间是否在春节期间
     * @param date 日期
     * @return 是否在春节期间
     */
    public static boolean isDuringSpringFestival(Date date) {
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        int year = ldt.getYear();
        String springFestivalPeriod = springFestivalPeriodMap.get(year);
        if (StringUtils.isEmpty(springFestivalPeriod)) {
            return false;
        }

        String[] startEnd = StringUtils.split(springFestivalPeriod, "|");

        LocalDateTime start = LocalDateTime.parse(startEnd[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(startEnd[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return ldt.isAfter(start) && ldt.isBefore(end.plusSeconds(1));
    }
}
