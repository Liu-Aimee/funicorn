package com.funicorn.basic.common.base.util;

import com.funicorn.basic.common.base.model.DateModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author Aimee
 * @since 2021/7/16 14:28
 */
@SuppressWarnings("unused")
public class DateUtil {

	/**
	 * 获取两个时间段的小时集合
	 * @param startTime 开始时间点
	 * @param endTime 结束时间点
	 * @return List
	 * */
	public static List<DateModel> getDifferHourList(Date startTime, Date endTime) {
		List<DateModel> dateModels = new ArrayList<>();
		if (startTime.compareTo(endTime) >= 0) {
			return dateModels;
		}
		LocalDateTime startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime endDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		long differDay = ChronoUnit.HOURS.between(startDate, endDate);
		if (differDay==0) {
			DateModel dateModel = new DateModel();
			dateModel.setStartDateTime(startTime);
			dateModel.setEndDateTime(endTime);
			dateModels.add(dateModel);
		} else {
			DateModel firstDateModel = new DateModel();
			firstDateModel.setStartDateTime(startTime);
			firstDateModel.setEndDateTime(DateUtil.getHourEndTime(startTime.getTime()));
			dateModels.add(firstDateModel);
			startTime = DateUtil.getHourStartTime(startTime.getTime());
			for (int i = 1; i < differDay; i++) {
				Date date = DateUtil.addHour(startTime, i);
				DateModel dateModel = new DateModel();
				dateModel.setStartDateTime(date);
				dateModel.setEndDateTime(DateUtil.getHourEndTime(date.getTime()));
				dateModels.add(dateModel);
			}

			DateModel endDateModel = new DateModel();
			endDateModel.setStartDateTime(DateUtil.addHour(startTime, (int)differDay));
			endDateModel.setEndDateTime(endTime);
			dateModels.add(endDateModel);
		}
		return dateModels;
	}

	/**
	 * 获取两个时间段的天集合
	 * @param startTime 开始时间点
	 * @param endTime 结束时间点
	 * @return List
	 * */
	public static List<DateModel> getDifferDayList(Date startTime, Date endTime) {
		List<DateModel> dateModels = new ArrayList<>();
		if (startTime.compareTo(endTime) >= 0) {
			return dateModels;
		}
		LocalDate startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long differDay = ChronoUnit.DAYS.between(startDate, endDate);
		if (differDay==0) {
			DateModel dateModel = new DateModel();
			dateModel.setStartDateTime(startTime);
			dateModel.setEndDateTime(endTime);
			dateModels.add(dateModel);
			return dateModels;
		}
		//切换到startTime当天的开始的时间点
		startTime = DateUtil.getDayStartTime(startTime.getTime());
		for (int i = 0; i < differDay; i++) {
			Date date = DateUtil.addDay(startTime, i);
			DateModel dateModel = new DateModel();
			dateModel.setStartDateTime(date);
			dateModel.setEndDateTime(DateUtil.getDayEndTime(date.getTime()));
			dateModels.add(dateModel);
		}

		Date date = DateUtil.addDay(startTime, (int)differDay);
		DateModel endDateModel = new DateModel();
		endDateModel.setStartDateTime(date);
		endDateModel.setEndDateTime(endTime);
		dateModels.add(endDateModel);
		return dateModels;
	}

	/**
	 * 获取两个时间段的周集合
	 * @param startTime 开始时间点
	 * @param endTime 结束时间点
	 * @return List
	 * */
	public static List<DateModel> getDifferWeekList(Date startTime, Date endTime) {
		List<DateModel> dateModels = new ArrayList<>();
		if (startTime.compareTo(endTime) >= 0) {
			return dateModels;
		}
		LocalDate startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long differWeek = ChronoUnit.WEEKS.between(startDate, endDate);
		if (differWeek==0) {
			DateModel dateModel = new DateModel();
			dateModel.setStartDateTime(startTime);
			dateModel.setEndDateTime(endTime);
			dateModel.setWeek(DateUtil.getYearWeek(startTime));
			dateModels.add(dateModel);
		} else {
			DateModel firstDateModel = new DateModel();
			firstDateModel.setStartDateTime(startTime);
			firstDateModel.setEndDateTime(DateUtil.getWeekEndTime(startTime.getTime()));
			firstDateModel.setWeek(DateUtil.getYearWeek(startTime));
			dateModels.add(firstDateModel);
			startTime = DateUtil.getWeekStartTime(startTime.getTime());
			for (int i = 1; i < differWeek; i++) {
				DateModel dateModel = new DateModel();
				Date date = DateUtil.addWeek(startTime,i);
				dateModel.setStartDateTime(date);
				dateModel.setEndDateTime(DateUtil.getWeekEndTime(date.getTime()));
				dateModel.setWeek(DateUtil.getYearWeek(date));
				dateModels.add(dateModel);
			}
			DateModel endDateModel = new DateModel();
			endDateModel.setStartDateTime(DateUtil.addWeek(startTime,(int)differWeek));
			endDateModel.setEndDateTime(endTime);
			endDateModel.setWeek(DateUtil.getYearWeek(endDateModel.getStartDateTime()));
			dateModels.add(endDateModel);
		}
		return dateModels;
	}

	/**
	 * 获取两个时间段的月集合
	 * @param startTime 开始时间点
	 * @param endTime 结束时间点
	 * @return List
	 * */
	public static List<DateModel> getDifferMonthList(Date startTime, Date endTime) {
		List<DateModel> dateModels = new ArrayList<>();
		if (startTime.compareTo(endTime) >= 0) {
			return dateModels;
		}
		LocalDate startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long differMonth = ChronoUnit.MONTHS.between(startDate, endDate);
		if (differMonth==0) {
			DateModel dateModel = new DateModel();
			dateModel.setStartDateTime(startTime);
			dateModel.setEndDateTime(endTime);
			dateModel.setMonth(DateUtil.getMonth(startTime));
			dateModels.add(dateModel);
		} else {
			DateModel firstDateModel = new DateModel();
			firstDateModel.setStartDateTime(startTime);
			firstDateModel.setEndDateTime(DateUtil.getMonthEndTime(startTime.getTime()));
			firstDateModel.setMonth(DateUtil.getMonth(startTime));
			dateModels.add(firstDateModel);
			//切换到开始时间当月的开始时间
			startTime = DateUtil.getMonthStartTime(startTime.getTime());
			for (int i = 1; i < differMonth; i++) {
				DateModel dateModel = new DateModel();
				Date date = DateUtil.addMonth(startTime,i);
				dateModel.setStartDateTime(date);
				dateModel.setEndDateTime(DateUtil.getMonthEndTime(date.getTime()));
				dateModel.setMonth(DateUtil.getMonth(date));
				dateModels.add(dateModel);
			}
			DateModel endDateModel = new DateModel();
			endDateModel.setStartDateTime(DateUtil.addMonth(startTime,(int)differMonth));
			endDateModel.setEndDateTime(endTime);
			endDateModel.setMonth(DateUtil.getMonth(endDateModel.getStartDateTime()));
			dateModels.add(endDateModel);
		}
		return dateModels;
	}

	/**
	 * 获取指定时间1小时内的开始时间点
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
	 */
	public static Date getHourStartTime(Long timeStamp) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(new Date(timeStamp));
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		return currentDate.getTime();
	}

	/**
	 * 获取指定时间1小时内的结束时间点
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
	 */
	public static Date getHourEndTime(Long timeStamp) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(new Date(timeStamp));
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.MILLISECOND, 999);
		return currentDate.getTime();
	}

	/**
	 * 获取指定日期的当天开始时间点
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
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
	 * 获取指定日期的当天结束时间点
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
	 */
	public static Date getDayEndTime(Long timeStamp) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(new Date(timeStamp));
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.MILLISECOND, 999);
		return currentDate.getTime();
	}

	/**
	 * 获取指定日期当周的开始时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
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
	 * 获取指定日期当周的结束时间
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
	 * 获取指定时间当月的开始时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
	 */
	public static Date getMonthStartTime(Long timeStamp) {
		return getMonthStartTime(timeStamp, "GMT+8:00");
	}

	/**
	 * 获取指定时间当月的结束时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @return Date
	 */
	public static Date getMonthEndTime(Long timeStamp) {
		return getMonthEndTime(timeStamp, "GMT+8:00");
	}

	/**
	 * 获取指定时间当月的开始时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @param timeZone  如 GMT+8:00
	 * @return Date
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
	 * 获取指定时间当月的结束时间
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @param timeZone  如 GMT+8:00
	 * @return Date
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
	 * 获取当年第多少月
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

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY__MM__DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
	public static final String MM_DD_HH_MM = "MM-dd HH:mm";
	public static final String MM__DD_HH_MM = "MM/dd HH:mm";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY__MM__DD_HH_MM = "yyyy/MM/dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY__MM__DD = "yyyy/MM/dd";
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY__MM = "yyyy/MM";
	public static final String HH_MM = "HH:mm";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYY__MM__DD_HH = "yyyy/MM/dd HH";
	public static final String MM_DD = "MM-dd";
	public static final String MM__DD = "MM/dd";
	public static final String _YYYY = "yyyy";
	public static final String _MM = "MM";
	public static final String _DD = "dd";
	public static final String _HH = "HH";
	public static final String __MM = "mm";
	public static final String _SS = "ss";

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

	/**
	 * 单元测试
	 * */
	public static void main(String[] args) {
		List<DateModel> dateModels = DateUtil.getDifferMonthList(Objects.requireNonNull(strToDate("2022/01/21 00:00:00", YYYY_MM_DD_HH_MM_SS))
				,Objects.requireNonNull(strToDate("2022/04/30 1:53:00", YYYY_MM_DD_HH_MM_SS)));
		dateModels.forEach(dateModel -> {
			System.out.println(dateModel.getStartDateTime());
			System.out.println(dateModel.getEndDateTime());
			System.out.println(dateModel.getWeek());
			System.out.println(dateModel.getMonth());
			System.out.println("-------------------------");
		});
	}

}
