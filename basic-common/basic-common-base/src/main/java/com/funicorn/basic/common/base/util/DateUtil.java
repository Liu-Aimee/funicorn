package com.funicorn.basic.common.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Aimee
 * @since 2021/7/16 14:28
 */
@SuppressWarnings("unused")
public class DateUtil {

	/**
	 * 获取当月开始时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
	 */
	public static Date getMonthStartTime(Long timeStamp) {
		return getMonthStartTime(timeStamp, "GMT+8:00");
	}

	/**
	 * 获取当月的结束时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
	 */
	public static Date getMonthEndTime(Long timeStamp) {
		return getMonthEndTime(timeStamp, "GMT+8:00");
	}

	/**
	 * 获取当月开始时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @param timeZone  如 GMT+8:00
	 * @return Long
	 */
	public static Date getMonthStartTime(Long timeStamp, String timeZone) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		calendar.setTimeInMillis(timeStamp);
		calendar.add(Calendar.YEAR, 0);
		calendar.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当月的结束时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @param timeZone  如 GMT+8:00
	 * @return Long
	 */
	public static Date getMonthEndTime(Long timeStamp, String timeZone) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		calendar.setTimeInMillis(timeStamp);
		calendar.add(Calendar.YEAR, 0);
		calendar.add(Calendar.MONTH, 0);
		// 获取当前月最后一天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取当周的开始时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Long
	 */
	public static Date getWeekStartTime(Long timeStamp) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(new Date(timeStamp));
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);

		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return currentDate.getTime();
	}

	/**
	 * 获取当天的开始时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Long
	 */
	public static Date getDayStartTime(Long timeStamp) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(new Date(timeStamp));
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获取当月结束时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Long
	 */
	public static Date getWeekEndTime(Long timeStamp) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(new Date(timeStamp));
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return currentDate.getTime();
	}

	/**
	 * 判断当月第几周
	 * 
	 * @param date 日期
	 * @return int
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 第几周
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 判断当年第几周
	 * 
	 * @param date 日期
	 * @return int
	 */
	public static int getYearWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 第几周
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 判断第几月
	 * 
	 * @param date 日期
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 第几月
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 *
	 * 增加多少分钟
	 * @param date 日期
	 * @param minute 分钟
	 * @return Date
	 */
	public static Date addMinute(Date date, int minute) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 增加一小时
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);
		return calendar.getTime();
	}

	/**
	 *
	 * 增加一小时
	 * @param date 日期
	 * @param hour 小时
	 * @return Date
	 */
	public static Date addHour(Date date, int hour) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 增加一小时
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
		return calendar.getTime();
	}

	/**
	 *
	 * 增加一天
	 * @param date 日期
	 * @param day 天数
	 * @return Date
	 */
	public static Date addDay(Date date, int day) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 增加一天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
		return calendar.getTime();
	}

	/**
	 *
	 * 增加一年
	 * @param date 日期
	 * @param year 年数
	 * @return Date
	 */
	public static Date addYear(Date date, int year) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 增加一年
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 *
	 * 增加一个周
	 * @param date 日期
	 * @param week 周期
	 * @return Date
	 */
	public static Date addWeek(Date date, int week) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 增加一周
		calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) + week);
		return calendar.getTime();
	}

	/**
	 *
	 * 增加一个月
	 * @param date 日期
	 * @param month 月数
	 * @return Date
	 */
	public static Date addMonth(Date date, int month) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 增加一月
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
		return calendar.getTime();
	}

	/**
	 * 计算相差天数
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return long 天数
	 */
	public static long calDifferDays(Date startTime, Date endTime) {
		LocalDate startDate1 = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate1 = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ChronoUnit.DAYS.between(startDate1, endDate1);
	}

	/**
	 * 计算相差月份数
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return long 月份数
	 */
	public static long calDifferMonths(Date startTime, Date endTime) {
		LocalDate startDate1 = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate1 = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ChronoUnit.MONTHS.between(startDate1, endDate1);
	}

	/**
	 * 计算相差年份数
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return long 年数
	 */
	public static long calDifferYears(Date startTime, Date endTime) {
		LocalDate startDate1 = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate1 = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ChronoUnit.YEARS.between(startDate1, endDate1);
	}

	/**
	 * 计算相差分钟
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return long 分钟
	 */
	public static long calDifferMinutes(Date startTime, Date endTime) {
		LocalDateTime startDate1 = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime endDate1 = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return ChronoUnit.MINUTES.between(startDate1, endDate1);
	}

	/**
	 * 计算相差多少秒
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return long 秒数
	 */
	public static long calDifferSeconds(Date startTime, Date endTime) {
		LocalDateTime startDate1 = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime endDate1 = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return ChronoUnit.SECONDS.between(startDate1, endDate1);
	}

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
	public static final String MM_DD_HH_MM = "MM/dd HH:mm";
	public static final String YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy/MM/dd";
	public static final String YYYY_MM = "yyyy/MM";
	public static final String HH_MM = "HH:mm";
	public static final String YYYY_MM_DD_HH = "yyyy/MM/dd HH";
	public static final String MM_DD = "MM/dd";
	public static final String YYYY = "yyyy";
	public static final String MM = "MM";
	public static final String DD = "dd";

	/**
	 * 日期转换字符串
	 * @param time 时间
	 * @param format 格式
	 * @return String
	 * */
	public static String dateToStr(Date time, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(time);
	}

	/**
	 * 字符串转换日期
	 * @param time 时间
	 * @param format 格式
	 * @return Date
	 * */
	public static Date strToDate(String time, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
