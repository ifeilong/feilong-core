/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.core.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 扩展 {@link DateUtil}类,更多人性化的操作及转换 .
 * 
 * @author feilong
 * @version 1.0.1 Aug 4, 2010 9:06:54 PM
 * @see DateUtil
 * @since 1.0.0
 */
public final class CalendarUtil{

    /** Don't let anyone instantiate this class. */
    private CalendarUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得任意日期的00:00:00.
     * <p>
     * 例如: {@code 2011-01-01 10:20:20  return 2011-01-01 00:00:00}.
     * </p>
     * 
     * @param date
     *            the date
     * @return 获得任意日期的00:00:00
     */
    public static Date resetDateByDay(Date date){
        Calendar calendar = resetCalendarByDay(date);
        return toDate(calendar);
    }

    /**
     * 获得任意日期的00:00:00.
     * <p>
     * 例如: {@code 2011-01-01 10:20:20 return 2011-01-01 00:00:00}.
     * </p>
     * 
     * @param date
     *            the date
     * @return 获得任意日期的00:00:00
     */
    public static Calendar resetCalendarByDay(Date date){
        Calendar calendar = DateUtil.toCalendar(date);
        return resetDayBegin(calendar);
    }

    // [start]private

    /**
     * 一天开始,<code>00:00:00.000</code>
     *
     * @param calendar
     *            the calendar
     * @return the calendar
     * @see Calendar#set(int, int)
     * @see Calendar#HOUR_OF_DAY
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#MILLISECOND
     * @since 1.3.0
     */
    public static Calendar resetDayBegin(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 一天结束,最后的时间 <code>23:59:59.999</code>
     *
     * @param calendar
     *            the calendar
     * @return the calendar
     * @see Calendar#set(int, int)
     * @see Calendar#HOUR_OF_DAY
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#MILLISECOND
     * @since 1.3.0
     */
    public static Calendar resetDayEnd(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    // [end]
    /**
     * 获得日历字段值.
     *
     * @param date
     *            date
     * @param field
     *            Calendar字段:<br>
     *            月份:Calendar.MONTH(真实值需要加1处理),<br>
     *            日:Calendar.DAY_OF_MONTH,<br>
     *            年份:Calendar.YEAR<br>
     *            ...
     * @return 获得日历字段值
     * @see Calendar#YEAR
     * @see Calendar#MONTH
     * @see Calendar#DAY_OF_MONTH
     * @see Calendar#DATE
     * @see Calendar#HOUR
     * @see Calendar#HOUR_OF_DAY
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#DAY_OF_WEEK
     */
    public static int getCalendarFieldValue(Date date,int field){
        Calendar calendar = DateUtil.toCalendar(date);
        return calendar.get(field);
    }

    /**
     * 获得阳历中月份的最大天数The days in the month of solar calendar(阳历).
     * 
     * @param year
     *            年
     * @param month
     *            月
     * @return 最大的天数
     * @see Calendar#getActualMaximum(int)
     */
    public static int getMaxDayOfMonth(int year,int month){
        Calendar calendar = toCalendar(year, month, 1);
        return getMaxDayOfMonth(calendar);
    }

    /**
     * 获得阳历中月份的最大天数The days in the month of solar calendar(阳历).
     * 
     * @param calendar
     *            calendar
     * @return the max day of month
     */
    public static int getMaxDayOfMonth(Calendar calendar){
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得这一天在这一年中的偏移量 The offset days from New Year and the day when point out in solar calendar.
     * 
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return 获得这一天在这一年中的偏移量
     * @see com.feilong.core.date.DateUtil#getDayOfYear(Date)
     */
    public static int getDayOfYear(int year,int month,int day){
        Calendar calendar = toCalendar(year, month, day);
        return calendar.get(Calendar.DAY_OF_YEAR);// - 1
    }

    /**
     * 将calendar转成Date.
     * 
     * @param calendar
     *            calendar
     * @return Date
     * @since 1.0
     */
    public static final Date toDate(Calendar calendar){
        return calendar.getTime();
    }

    /**
     * 将Calendar转成string.
     * 
     * @param calendar
     *            calendar
     * @param datePattern
     *            日期pattern {@link DatePattern}
     * @return string
     */
    public static String toString(Calendar calendar,String datePattern){
        Date date = toDate(calendar);
        return DateUtil.date2String(date, datePattern);
    }

    /**
     * 将日期字符串转成Calendar.
     *
     * @param dateString
     *            将日期字符串
     * @param datePattern
     *            日期pattern {@link DatePattern}
     * @return Calendar
     * @since 1.3.0
     */
    public static Calendar toCalendar(String dateString,String datePattern){
        Date date = DateUtil.string2Date(dateString, datePattern);
        Calendar calendar = DateUtil.toCalendar(date);
        return calendar;
    }

    /**
     * 设置日历字段 YEAR、MONTH 和 DAY_OF_MONTH 的值.
     * <p>
     * 保留其他日历字段以前的值.如果不需要这样做，则先调用 clear()..
     * </p>
     * 
     * @param year
     *            用来设置 YEAR 日历字段的值
     * @param month
     *            用来设置 MONTH 日历字段的值.此处传递是我们口头意义上的月份, 内部自动进行-1的操作<br>
     *            比如 8月就传递 8 ; 9月就传9 <br>
     *            注:Java 的date Month 值是基于 0 的.例如，0 表示 January.
     * @param day
     *            用来设置 DAY_OF_MONTH 日历字段的值.
     * @return the calendar
     * @see Calendar#clear()
     */
    public static Calendar toCalendar(int year,int month,int day){
        Calendar calendar = new GregorianCalendar();

        // 在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
        calendar.clear();

        calendar.set(year, month - 1, day);
        return calendar;
    }
}