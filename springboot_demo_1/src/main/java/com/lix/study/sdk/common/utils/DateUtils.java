package com.lix.study.sdk.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtils {

	private static final Map<String, ThreadLocal<SimpleDateFormat>> FMT_MAP = new ConcurrentHashMap<>(8);
	/**
	 * yyyy-MM-dd日期类型
	 */
	public static final String FMT_YMD = "yyyy-MM-dd";
	public static final String FMT_ZHCN = "yyyy年MM月dd日";
	/**
	 * yyyy-MM-dd HH:mm:ss时间类型
	 */
	public static final String FMT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	public static final String FMT_YMD_A_HMS = "yyyy-MM-dd a HH:mm:ss";
	/**
	 * 精确到毫秒
	 */
	public static final String FMT_YMD_HMS_S = "yyyy-MM-dd HH:mm:ss:SSS";
	
	/**
	 * 毫秒级
	 */
	public static final long SECOND = 1000L;
	public static final long MINUTE = SECOND * 60;
	public static final long HOUR = MINUTE *60;
	public static final long DAY = HOUR *24;

    private DateUtils() {
    }

    /**
     * jdk8的写法
     * 就是往map里添加DateFormat
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getFmt(String pattern) {
        return FMT_MAP.computeIfAbsent(pattern,
                k -> ThreadLocal.withInitial(() -> new SimpleDateFormat(k)))
                .get();
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return getFmt(FMT_YMD_HMS).format(date);
    }

    /**
     * yyyy-MM-dd 格式
     *
     * @param date
     * @return
     */
    public static String formatYMD(Date date) {
        return getFmt(FMT_YMD).format(date);
    }

    /**
     * yyyy年MM月dd日 格式
     *
     * @param date
     * @return
     */
    public static String formatZHCN(Date date) {
        return getFmt(FMT_ZHCN).format(date);
    }

    /**
     * 自定义格式format
     * 
     * @param date
     * @param fmt
     * @return
     */
    public static String format(Date date, String fmt) {
        return getFmt(fmt).format(date);
    }

    public static Date nextDay(Date date) {
        return shiftDay(date, 1);
    }

    public static Date preDay(Date date) {
        return shiftDay(date, -1);
    }

    /**
     * 按天前后移动
     *
     * @param date
     * @param day
     * @return
     */
    public static Date shiftDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 日期truncate
     * 
     * @param date
     * @return
     */
    public static Date truncate(Date date) {
        return truncateCalendar(date).getTime();
    }

    private static Calendar truncateCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * 验证生日的有效性
     *
     * @param format
     * @param birthday
     * @return
     * @throws ParseException
     */
    public static boolean isBirthday(String format, String birthday) {
        try {
            Date date = parseStr(format, birthday);
            // 生日比现在小
            return date.before(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 转换字符串
     *
     * @param format
     *            格式
     * @param value
     *            需要转换的值
     * @return
     * @throws ParseException
     */
    public static Date parseStr(String format, String value) throws ParseException {
        SimpleDateFormat simpleDateFormat = getFmt(format);
        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(value);
    }

    /**
     * 将yyyy-MM-dd格式字符串转成日期
     * 
     * @param value
     * @return
     * @throws ParseException 
     */
    public static Date parseYmdStr(String value) throws ParseException {
        return parseStr(FMT_YMD, value);
    }

    /**
     * 当前日期yyyy-MM-dd
     * 
     * @return
     */
    public static String getCurrentDate() {
        return formatYMD(new Date());
    }

    /**
     * 当前时间yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    public static String getCurrentTime() {
        return formatDateTime(new Date());
    }

    /**
     * 计算两个日期之间的间隔
     * 
     * @param from
     * @param to
     * @param resultUtil
     *            Calendar.YEAR Calendar.MONTH default:day
     * @return
     * @throws ParseException 
     */
    public static int calcInterval(String from, String to, int resultUtil) throws ParseException {
        return calcInterval(parseYmdStr(from), parseYmdStr(to), resultUtil);
    }

    /**
     * 计算两个日期之间的间隔
     *
     * @param from
     * @param to
     * @param resultUtil Calendar.YEAR Calendar.MONTH default:day
     * @return
     * @throws ParseException 
     */
    public static int calcInterval(Date from, String to, int resultUtil) throws ParseException {
        return calcInterval(from, parseYmdStr(to), resultUtil);
    }

    /**
     * 计算两个日期之间的间隔
     * 
     * @param from
     * @param to
     * @param resultUtil
     *            Calendar.YEAR Calendar.MONTH default:day
     * @return
     */
    public static int calcInterval(Date from, Date to, int resultUtil) {
        Calendar start = truncateCalendar(from);
        Calendar end = truncateCalendar(to);

        int startYear = start.get(Calendar.YEAR);
        int endYear = end.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH);
        int endMonth = end.get(Calendar.MONTH);
        int startMonthDay = start.get(Calendar.DAY_OF_MONTH);
        int endMonthDay = end.get(Calendar.DAY_OF_MONTH);
        int endYearDay = end.get(Calendar.DAY_OF_YEAR);

        int year = endYear - startYear;

        if (resultUtil == Calendar.YEAR) {
            if (endMonth <= startMonth) {
                year--;
                if (endMonth == startMonth && endMonthDay >= startMonthDay
                        || needLeapYearAdjust(startYear, endYear, endYearDay)) {
                    year++;
                }
            }
            return year;

        } else if (resultUtil == Calendar.MONTH) {
            int months = year * 12;
            months += endMonth - startMonth;
            if (endMonthDay < startMonthDay) {
                months--;
                if (needLeapYearAdjust(startYear, endYear, endYearDay)) {
                    months++;
                }
            }
            return months;
        }
        // 缺省返回天数
        long millis = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (millis / DAY);
    }

    private static boolean needLeapYearAdjust(int startYear, int endYear, int endYearDay) {
        // 2月28号，第31+28=59天
        // 开始年是闰年，结束年不是的话，2月28号即已满岁/满月
        return endYearDay == 59 && isLeapYear(startYear) && !isLeapYear(endYear);
    }

    /**
     * 计算年龄按照月数返回
     * 
     * @param birthday
     * @param endDate
     * @return
     */
    public static int calcAgeMonth(Date birthday, Date endDate) {
        return calcInterval(birthday, endDate, Calendar.MONTH);
    }

    /**
     * 计算年龄
     *
     * @param birthday
     * @param endDate
     * @return
     */
    public static int calcAge(Date birthday, Date endDate) {
        return calcInterval(birthday, endDate, Calendar.YEAR);
    }

    /**
     * 按当前日期计算年龄
     * 
     * @param birthday
     * @return
     * @throws ParseException 
     */
    public static int calcAge(String birthday) throws ParseException {
        return calcAge(parseYmdStr(birthday));
    }

    /**
     * 按当前日期计算年龄
     *
     * @param birthday
     * @return
     */
    public static int calcAge(Date birthday) {
        return calcInterval(birthday, new Date(), Calendar.YEAR);
    }

    /**
     * 计算年龄
     * 
     * @param birthday
     * @param endDate
     * @return
     * @throws ParseException 
     */
    public static int calcAge(String birthday, String endDate) throws ParseException {
        return calcInterval(parseYmdStr(birthday), parseYmdStr(endDate), Calendar.YEAR);
    }

    /**
     * 计算两个日期之间间隔月数
     *
     * @param from
     * @param to
     * @return
     */
    public static int getMonthsBetween(Date from, Date to) {
        return calcInterval(from, to, Calendar.MONTH);
    }

    /**
     * 计算日期与当前日期之间间隔月数
     * @param date
     * @return
     */
    public static int getMonthsBetween(Date date) {
        return calcInterval(date, new Date(), Calendar.MONTH);
    }

    /**
     * 比较两个日期大小
     *
     * @param from
     * @param to
     * @return
     * @throws ParseException 
     */
    public static int compareDate(Date from, String to) throws ParseException {
        return calcInterval(from, parseYmdStr(to), Calendar.DATE);
    }

    /**
     * 是否闰年
     * 
     * @param String
     *            year
     * @return
     */
    public static boolean isLeapYear(String year) {
        return isLeapYear(Integer.valueOf(year));
    }

    /**
     * 是否闰年
     * 
     * @param Date
     *            date
     * @return
     */
    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isLeapYear(cal.get(Calendar.YEAR));
    }

    /**
     * 是否闰年
     * 
     * @param int year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
    }


    /**
     * 时间增加
     * @param date 原始时间
     * @param count 增加的数量
     * @param resultUtil 增加的格式(年月日)
     * @return
     */
    private static Date addCalendar(Date date, int count, int resultUtil) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (resultUtil == Calendar.YEAR) {
            calendar.add(Calendar.YEAR, count);
        } else if (resultUtil == Calendar.MONTH) {
            calendar.add(Calendar.MONTH, count);
        } else {
            calendar.add(Calendar.DATE, count);
        }
        return calendar.getTime();
    }

    /**
     * 日期增加年
     * @param date
     * @param count
     * @return
     */
    public static Date addYear(Date date, int count) {
        return addCalendar(date, count, Calendar.YEAR);
    }

    /**
     * 日期增加月
     * @param date
     * @param count
     * @return
     */
    public static Date addMonth(Date date, int count) {
        return addCalendar(date, count, Calendar.MONTH);
    }

    /**
     * 日期增加天
     * @param date
     * @param count
     * @return
     */
    public static Date addDay(Date date, int count) {
        return addCalendar(date, count, Calendar.DATE);
    }

    /**
     * 将2个时间(年月日+时分秒)连接
     * @param day yyyy-MM-dd
     * @param time hh:mm:ss
     * @return
     */
    public static Date contactDayAndTime(Date day, Date time) {
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);
        Calendar result = Calendar.getInstance();
        result.setTime(day);
        result.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        result.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        result.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
        return result.getTime();
    }
    
    public static int getMaxDayOfMonth(String year, String month) {
    	return getMaxDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
    }
    /**
     * 返回某年某月的最大天数，月份错误返回0
     * @param year
     * @param month
     * @return
     */
	public static int getMaxDayOfMonth(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			return isLeapYear(year) ? 29 : 28;
		default:
			return 0;
		}
	}
}
