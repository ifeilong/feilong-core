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

import com.feilong.core.text.DateFormatUtil;
import com.feilong.core.util.Validator;

/**
 * {@link java.util.Date}操作工具类(feilong-core核心类之一).
 * 
 * <h3>常用方法:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>字符串转日期</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#string2Date(String, String)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>日期转字符串</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#date2String(Date, String)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>日期加减</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#addDay(Date, int)}</li>
 * <li>{@link DateUtil#addHour(Date, int)}</li>
 * <li>{@link DateUtil#addMinute(Date, int)}</li>
 * <li>{@link DateUtil#addMonth(Date, int)}</li>
 * <li>{@link DateUtil#addSecond(Date, int)}</li>
 * <li>{@link DateUtil#addWeek(Date, int)}</li>
 * <li>{@link DateUtil#addYear(Date, int)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>获得日期某部值</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#getDayOfMonth(Date)}</li>
 * <li>{@link DateUtil#getDayOfWeek(Date)}</li>
 * <li>{@link DateUtil#getDayOfYear(Date)}</li>
 * <li>{@link DateUtil#getHourOfDay(Date)}</li>
 * <li>{@link DateUtil#getHourOfYear(Date)}</li>
 * <li>{@link DateUtil#getMinute(Date)}</li>
 * <li>{@link DateUtil#getMonth(Date)}</li>
 * <li>{@link DateUtil#getSecond(Date)}</li>
 * <li>{@link DateUtil#getYear(Date)}</li>
 * <li>{@link DateUtil#getTime(Date)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>获得两个日期间隔</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#getIntervalDay(long)}</li>
 * <li>{@link DateUtil#getIntervalDay(Date, Date)}</li>
 * <li>{@link DateUtil#getIntervalDay(String, String, String)}</li>
 * 
 * <li>{@link DateUtil#getIntervalWeek(long)}</li>
 * <li>{@link DateUtil#getIntervalWeek(Date, Date)}</li>
 * <li>{@link DateUtil#getIntervalWeek(String, String, String)}</li>
 * 
 * <li>{@link DateUtil#getIntervalHour(long)}</li>
 * <li>{@link DateUtil#getIntervalHour(Date, Date)}</li>
 * 
 * <li>{@link DateUtil#getIntervalMinute(long)}</li>
 * <li>{@link DateUtil#getIntervalSecond(long)}</li>
 * <li>{@link DateUtil#getIntervalSecond(Date, Date)}</li>
 * 
 * <li>{@link DateUtil#getIntervalTime(Date, Date)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>判断闰年</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#isLeapYear(int)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>判断相等</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#isEquals(Date, Date, String)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>判断早晚</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#isBefore(Date, String, String)}</li>
 * <li>{@link DateUtil#isBefore(String, String, String)}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>判断日期区间</td>
 * <td>
 * <ul>
 * <li>{@link DateUtil#isInTime(Date, Date, Date)}</li>
 * <li>{@link DateUtil#isInTime(Date, String, String, String)}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <h3>通过这个类,还可以获得以下数据:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>获得下一周的第一天时间</td>
 * <td>Date nextWeekDay = addDay(date, 7);<br>
 * return getFirstDateOfThisWeek(nextWeekDay);</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>获得下一周的最后一天时间</td>
 * <td>Date nextWeekDay = addDay(date, 7);<br>
 * return getLastDateOfThisWeek(nextWeekDay);</td>
 * </tr>
 * <tr valign="top">
 * <td>获得上一周的第一天时间</td>
 * <td>Date nextWeekDay = addDay(date, -7);<br>
 * return getFirstDateOfThisWeek(nextWeekDay);</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>获得上一周的最后一天时间</td>
 * <td>Date nextWeekDay = addDay(date, -7);<br>
 * return getLastDateOfThisWeek(nextWeekDay);</td>
 * </tr>
 * <tr valign="top">
 * <td>获得下个月第一天</td>
 * <td>return DateUtil.getFirstDateOfThisMonth(DateUtil.addMonth(now, +1)));</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>获得下个月最后一天</td>
 * <td>return DateUtil.getLastDateOfThisMonth(DateUtil.addMonth(now, +1)));</td>
 * </tr>
 * <tr valign="top">
 * <td>获得上个月第一天</td>
 * <td>return DateUtil.getFirstDateOfThisMonth(DateUtil.addMonth(now, -1)));</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>获得上个月最后一天</td>
 * <td>return DateUtil.getLastDateOfThisMonth(DateUtil.addMonth(now, -1)));</td>
 * </tr>
 * <tr valign="top">
 * <td>获得去年第一天</td>
 * <td>return DateUtil.getFirstDateOfThisYear(DateUtil.addYear(now, -1));</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>获得去年最后一天</td>
 * <td>return DateUtil.getLastDateOfThisYear(DateUtil.addYear(now, -1)));</td>
 * </tr>
 * <tr valign="top">
 * <td>获得明年第一天</td>
 * <td>return DateUtil.getFirstDateOfThisYear(DateUtil.addYear(now, +1));</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>获得明年最后一天</td>
 * <td>return DateUtil.getLastDateOfThisYear(DateUtil.addYear(now, +1)));</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.0 2010-1-27 下午01:53:21
 * @version 1.0.5 2014-5-6 10:04
 * @see CalendarUtil
 * @see DatePattern
 * @see DateFormatUtil
 * @see org.apache.commons.lang3.time.DateUtils
 * @since 1.0.0
 */
public final class DateUtil{

    /** Don't let anyone instantiate this class. */
    private DateUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得当天的开始时间 也就是当天的 <code>00:00:00</code>.
     * 
     * <p>
     * 例如:{@code 2011-01-01 10:20:20  return 2011-01-01 00:00:00}.
     * </p>
     * 
     * @param date
     *            the date
     * @return 获得指定日期的 <code>00:00:00</code>
     * @since 1.5.0
     */
    public static Date getFirstDateOfThisDay(Date date){
        Calendar calendar = CalendarUtil.resetDayBegin(date);
        return CalendarUtil.toDate(calendar);
    }

    /**
     * 获得当天的结束时间 也就是当天的 <code>23:59:59.999</code>.
     * 
     * <p>
     * 例如:{@code 2011-01-01 10:20:20  return 2011-01-01 23:59:59.999}.
     * </p>
     *
     * @param date
     *            the date
     * @return 获得指定日期的 <code>23:59:59.999</code>
     * @since 1.5.0
     */
    public static Date getLastDateOfThisDay(Date date){
        Calendar calendar = DateUtil.toCalendar(date);
        return CalendarUtil.toDate(CalendarUtil.resetDayEnd(calendar));
    }

    // *****************************week****************************************************
    /**
     * 获得传入date 所在的<span style="color:red">星期 第一天(周日)</span> <code>00:00:00.000</code> 到毫秒.
     * <p>
     * 注意:按照外国制,<span style="color:red">周日为一个星期第一天,周六为最后一天</span><br>
     * 注意:会自动跨月,跨年操作
     * </p>
     * 
     * <pre>
     * 如果 现在是 2012-10-11 17:10:30.701(周四),
     * return 2012-10-07 00:00:00.000
     * 
     * 跨年
     * getFirstDateOfThisWeek(2014-01-01 05:00:00)
     * return 2013-12-29 00:00:00.000
     * </pre>
     * 
     * @param date
     *            the date
     * @return 传入date 所在星期的第一天 <code>00:00:00.000</code> 到毫秒
     * @see #toCalendar(Date)
     * @see Calendar#set(int, int)
     * @see CalendarUtil#resetDayBegin(Calendar)
     * @see Calendar#getTime()
     */
    public static Date getFirstDateOfThisWeek(Date date){
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        CalendarUtil.resetDayBegin(calendar);
        return CalendarUtil.toDate(calendar);
    }

    /**
     * 获得传入date 所在<span style="color:red">星期的最后一天(周六)</span> <code>23:59:59.999</code> 到毫秒.
     * 
     * <p>
     * 注意:按照外国制,<span style="color:red">周日为一个星期第一天,周六为最后一天</span><br>
     * 注意:会自动跨月,跨年操作
     * </p>
     * 
     * <pre>
     * 如果 现在是 2012-10-11 17:10:30.701 (周四),
     * return 2012-10-13 23:59:59.999
     * 
     * 跨年
     * getLastDateOfThisWeek(2014-12-31 05:00:00)
     * return 2015-01-03 23:59:59.999
     * </pre>
     * 
     * @param date
     *            任意date
     * @return 传入date 所在星期的最后一天 <code>23:59:59.999</code> 到毫秒
     * @see #toCalendar(Date)
     * @see Calendar#set(int, int)
     * @see CalendarUtil#resetDayEnd(Calendar)
     * @see Calendar#getTime()
     * @since 1.0.1
     */
    public static Date getLastDateOfThisWeek(Date date){
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        CalendarUtil.resetDayEnd(calendar);
        return CalendarUtil.toDate(calendar);
    }

    // *********************************************************************************

    /**
     * 获得当天所在月的第一天,<code>00:00:00</code> 到毫秒.
     * 
     * <p>
     * 如果 现在是 2012-10-11 17:10:30.701 (周四),<br>
     * return 2012-10-01 00:00:00
     * </p>
     * 
     * @param date
     *            the date
     * @return Date
     * @see #toCalendar(Date)
     * @see Calendar#set(int, int)
     * @see CalendarUtil#resetDayBegin(Calendar)
     * @see Calendar#getTime()
     */
    public static Date getFirstDateOfThisMonth(Date date){
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        CalendarUtil.resetDayBegin(calendar);
        return CalendarUtil.toDate(calendar);
    }

    /**
     * 获得当天<span style="color:red">所在月的最后一天</span> <code>23:59:59.999</code> 到毫秒.
     * <p>
     * 以当前月的实际天数为准,也就是说,2月会自动区分闰年 是28天还是29天
     * </p>
     * 
     * <pre>
     * 如果 现在是 2012-10-11 17:10:30.701,
     * return 2012-10-31 23:59:59.999
     * </pre>
     * 
     * @param date
     *            the date
     * @return Date
     * @see #toCalendar(Date)
     * @see Calendar#set(int, int)
     * @see CalendarUtil#resetDayEnd(Calendar)
     * @see Calendar#getTime()
     */
    public static Date getLastDateOfThisMonth(Date date){
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        CalendarUtil.resetDayEnd(calendar);
        return CalendarUtil.toDate(calendar);
    }

    /**
     * 获得指定日期<span style="color:red">所在年的第一天</span>,<code>00:00:00.000</code> 到毫秒.
     * 
     * <pre>
     * 如果 现在是 2012-10-11 17:10:30.701,
     * return 2012-01-01 00:00:00
     * </pre>
     * 
     * @param date
     *            指定日期
     * @return date
     * @see #toCalendar(Date)
     * @see Calendar#set(int, int)
     * @see CalendarUtil#resetDayBegin(Calendar)
     * @see Calendar#getTime()
     */
    public static Date getFirstDateOfThisYear(Date date){
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        CalendarUtil.resetDayBegin(calendar);
        return CalendarUtil.toDate(calendar);
    }

    /**
     * 获得指定日期所在年的第一个星期 <code>week</code>.
     * 
     * <pre>
     * 如果 现在是 2015-7-29 14:08
     * 
     * DateUtil.getFirstWeekOfSpecifyDateYear(NOW, Calendar.FRIDAY)
     * return 2015-01-02 00:00:00.000
     * 
     * DateUtil.getFirstWeekOfSpecifyDateYear(NOW, Calendar.MONDAY)
     * return 2015-01-05 00:00:00.000
     * </pre>
     * 
     * @param date
     *            指定日期
     * @param week
     *            周几 星期天开始为1 依次2 3 4 5 6 7,建议使用 常量 {@link Calendar#SUNDAY}, {@link Calendar#MONDAY}, {@link Calendar#TUESDAY},
     *            {@link Calendar#WEDNESDAY}, {@link Calendar#THURSDAY}, {@link Calendar#FRIDAY}, {@link Calendar#SATURDAY}
     * @return the first week of specify date year
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     * @since 1.3.0
     */
    public static Date getFirstWeekOfSpecifyDateYear(Date date,int week){
        Calendar calendar = toCalendar(date);
        calendar.clear();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, week);

        //DAY_OF_WEEK_IN_MONTH 指示当前月中的第几个星期。与 DAY_OF_WEEK 字段一起使用时,就可以唯一地指定某月中的某一天。
        //与 WEEK_OF_MONTH 和 WEEK_OF_YEAR 不同,该字段的值并不 取决于 getFirstDayOfWeek() 或 getMinimalDaysInFirstWeek()。

        //DAY_OF_MONTH 1 到 7 总是对应于 DAY_OF_WEEK_IN_MONTH 1；
        //8 到 14 总是对应于 DAY_OF_WEEK_IN_MONTH 2,依此类推。
        //DAY_OF_WEEK_IN_MONTH 0 表示 DAY_OF_WEEK_IN_MONTH 1 之前的那个星期。
        //负值是从一个月的末尾开始逆向计数,因此,一个月的最后一个星期天被指定为 DAY_OF_WEEK = SUNDAY, DAY_OF_WEEK_IN_MONTH = -1。
        //因为负值是逆向计数的,所以它们在月份中的对齐方式通常与正值的不同。
        //例如,如果一个月有 31 天,那么 DAY_OF_WEEK_IN_MONTH -1 将与 DAY_OF_WEEK_IN_MONTH 5 和 DAY_OF_WEEK_IN_MONTH 4 的末尾相重叠
        return CalendarUtil.toDate(calendar);
    }

    /**
     * 获得当天<span style="color:red">所在年的最后一天</span> <code>23:59:59.999</code> 到毫秒<br>
     * 
     * <pre>
     * 如果 现在是 2012-10-11 17:10:30.701,
     * 
     * return 2012-12-31 23:59:59.999
     * </pre>
     * 
     * @param date
     *            任意date
     * @return Date
     * @see #toCalendar(Date)
     * @see Calendar#set(int, int)
     * @see CalendarUtil#resetDayEnd(Calendar)
     * @see Calendar#getTime()
     */
    public static Date getLastDateOfThisYear(Date date){
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        CalendarUtil.resetDayEnd(calendar);
        return CalendarUtil.toDate(calendar);
    }

    // [start]operate 时间操作(加减)

    /**
     * 指定时间,加减年份(仅 {@link java.util.Calendar#YEAR}进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <pre>
     * addYear(2012-06-29 00:33:05,5)
     * return 20<span style="color:red">17</span>-06-29 00:33:05
     * 
     * addYear(2012-06-29 00:33:05,-5)
     * return 20<span style="color:red">07</span>-06-29 00:33:05
     * </pre>
     * 
     * @param date
     *            指定时间
     * @param year
     *            增加年份 可以是负数 表示前面多少
     * @return 加减年份后的时间
     * @see #operateDate(Date, int, int)
     * @see Calendar#YEAR
     * @see org.apache.commons.lang3.time.DateUtils#addYears(Date, int)
     */
    public static Date addYear(Date date,int year){
        return operateDate(date, Calendar.YEAR, year);
    }

    /**
     * 指定时间加减月份,(仅仅 {@link java.util.Calendar#MONTH}进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <pre>
     * addMonth(2012-10-16 23:12:43,5)
     * return 2013-03-16 23:12:43.932
     * 
     * addMonth(2012-10-16 23:12:43,-5)
     * return 2012-05-16 23:12:43.943
     * </pre>
     * 
     * @param date
     *            指定时间
     * @param month
     *            加减月份, <span style="color:red">可以是负数</span>,表示前面多少<br>
     *            比如-3 表示 3个月之前
     * @return 加减月份后的时间
     * @see #operateDate(Date, int, int)
     * @see Calendar#MONTH
     * @see org.apache.commons.lang3.time.DateUtils#addMonths(Date, int)
     */
    public static Date addMonth(Date date,int month){
        return operateDate(date, Calendar.MONTH, month);
    }

    /**
     * 指定时间加减天数 (仅仅 {@link java.util.Calendar#DAY_OF_MONTH}进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <pre>
     * addDay(2012-06-29 00:42:26,5)
     * return 2012-07-04 00:42:26
     * 
     * addDay(2012-06-29 00:42:26,-5)
     * return 2012-06-24 00:42:26
     * 
     * addDay(2014-12-31 02:10:05,5)
     * return 2015-01-05 02:10:05.000
     * 
     * addDay(2014-01-01 02:10:05,-5)
     * return 2013-12-27 02:10:05.000
     * </pre>
     * 
     * @param date
     *            指定时间
     * @param day
     *            需要加减的天数,<span style="color:red">可以是负数</span>,表示前面多少<br>
     * @return 日期加减天数
     * @see #operateDate(Date, int, int)
     * @see Calendar#DAY_OF_MONTH
     * @see org.apache.commons.lang3.time.DateUtils#addDays(Date, int)
     */
    public static Date addDay(Date date,int day){
        // Calendar.DAY_OF_MONTH 它与 Calendar.DATE 是同义词.一个月中第一天的值为 1.
        return operateDate(date, Calendar.DAY_OF_MONTH, day);
    }

    /**
     * 日期加减星期 (仅仅{@link Calendar#WEEK_OF_YEAR}进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <pre>
     * Example 1:
     * addWeek(2012-06-29 00:45:18,5)
     * return 2012-08-03 00:45:18
     * 
     * Example 2:
     * addWeek(2012-06-29 00:45:18,-5)
     * return 2012-05-25 00:45:18
     * </pre>
     * 
     * @param date
     *            指定时间
     * @param week
     *            需要加减的星期数,<span style="color:red">可以是负数</span>,表示前面多少<br>
     * @return 指定时间加减星期
     * @see #operateDate(Date, int, int)
     * @see Calendar#WEEK_OF_YEAR
     */
    public static Date addWeek(Date date,int week){
        return operateDate(date, Calendar.WEEK_OF_YEAR, week);
    }

    /**
     * 日期加减小时 (仅仅{@link Calendar#HOUR_OF_DAY} 24小时制进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <pre>
     * addHour(2012-06-29 00:46:24,5)
     * return 2012-06-29 05:46:24
     * 
     * addHour(2012-06-29 00:46:24,-5)
     * return 2012-06-28 19:46:24
     * </pre>
     * 
     * <p>
     * {@link Calendar#HOUR}——12小时制的小时数 <br>
     * {@link Calendar#HOUR_OF_DAY}——24小时制的小时数
     * </p>
     * 
     * @param date
     *            the date
     * @param hour
     *            the hour,<span style="color:red">可以是负数</span>,表示前面多少<br>
     * @return the date
     * @see #operateDate(Date, int, int)
     * @see Calendar#HOUR_OF_DAY
     * @see org.apache.commons.lang3.time.DateUtils#addHours(Date, int)
     */
    public static Date addHour(Date date,int hour){
        return operateDate(date, Calendar.HOUR_OF_DAY, hour);
    }

    /**
     * 日期加减分钟 (仅仅{@link Calendar#MINUTE}进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <pre>
     * addMinute(2012-10-16 23:20:33,180)
     * return 2012-10-17 02:20:33.669
     * 
     * addMinute(2012-10-16 23:20:33,-180)
     * return 2012-10-16 20:20:33.669
     * </pre>
     * 
     * @param date
     *            the date
     * @param minute
     *            the minute,<span style="color:red">可以是负数</span>,表示前面多少<br>
     * @return the date
     * @see #operateDate(Date, int, int)
     * @see Calendar#MINUTE
     * @see org.apache.commons.lang3.time.DateUtils#addMinutes(Date, int)
     */
    public static Date addMinute(Date date,int minute){
        return operateDate(date, Calendar.MINUTE, minute);
    }

    /**
     * 日期加减秒 (仅仅{@link java.util.Calendar#SECOND}进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <pre>
     * addSecond(2012-10-16 23:22:02,180)
     * return 2012-10-16 23:25:02.206
     * 
     * addSecond(2012-10-16 23:22:02,-180)
     * return 2012-10-16 23:19:02.206
     * </pre>
     * 
     * @param date
     *            任意时间
     * @param second
     *            加减秒,<span style="color:red">可以是负数</span>,表示前面多少<br>
     * @return the date
     * @see #operateDate(Date, int, int)
     * @see Calendar#SECOND
     * @see org.apache.commons.lang3.time.DateUtils#addSeconds(Date, int)
     */
    public static Date addSecond(Date date,int second){
        return operateDate(date, Calendar.SECOND, second);
    }

    /**
     * 日期加减毫秒 (仅仅{@link java.util.Calendar#MILLISECOND}进行加减,不会操作其他字段),结果会自动跨月,跨年计算.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * addMillisecond(2015-09-07 13:35:02.769,5000)
     * return 2015-09-07 13:35:07.769
     * 
     * addMillisecond(2015-09-07 13:35:02.769,-5000)
     * return 2015-09-07 13:34:57.769
     * </pre>
     * 
     * </blockquote>
     * 
     * @param date
     *            任意时间
     * @param millisecond
     *            加减毫秒,<span style="color:red">可以是负数</span>,表示前面多少<br>
     * @return the date
     * @see #operateDate(Date, int, int)
     * @see Calendar#MILLISECOND
     * @see org.apache.commons.lang3.time.DateUtils#addMilliseconds(Date, int)
     * @since 1.4.1
     */
    public static Date addMillisecond(Date date,int millisecond){
        return operateDate(date, Calendar.MILLISECOND, millisecond);
    }

    /**
     * 底层操作时间的方法, 根据日历的规则,为给定的日历字段添加或减去指定的时间量.
     * 
     * @param currentDate
     *            当前date
     * @param field
     *            日历字段
     * @param amount
     *            为字段添加的日期或时间量,可以为负数
     * @return 底层操作时间的方法 根据日历的规则,为给定的日历字段添加或减去指定的时间量
     * @see #addYear(Date, int)
     * @see #addMonth(Date, int)
     * @see #addWeek(Date, int)
     * @see #addDay(Date, int)
     * @see #addHour(Date, int)
     * @see #addMinute(Date, int)
     * @see #addSecond(Date, int)
     * @see #addMillisecond(Date, int)
     * @see #toCalendar(Date)
     * @see Calendar#add(int, int)
     * @see org.apache.commons.lang3.time.DateUtils#add(Date, int, int)
     */
    public static Date operateDate(Date currentDate,int field,int amount){
        Calendar calendar = toCalendar(currentDate);
        calendar.add(field, amount);
        return CalendarUtil.toDate(calendar);
    }

    // [end]

    // [start]fieldValue获得日期中的某属性字段
    /**
     * 获得任意日期中的年份 {@link java.util.Calendar#YEAR}部分.
     * 
     * <pre>
     * 2012-06-29
     * return 2012
     * </pre>
     * 
     * @param date
     *            the date
     * @return 获得任意日期中的年份部分
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#YEAR
     */
    public static int getYear(Date date){
        return CalendarUtil.getFieldValue(date, Calendar.YEAR);
    }

    /**
     * 获得任意日期中的月份{@link java.util.Calendar#MONTH}<span style="color:red">(已经+1处理)</span>.
     * 
     * <pre>
     * 2012-06-29
     * return 6
     * </pre>
     * 
     * @param date
     *            the date
     * @return 获得任意日期中的月份
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#MONTH
     */
    public static int getMonth(Date date){
        return 1 + CalendarUtil.getFieldValue(date, Calendar.MONTH);
    }

    /**
     * 当前年中的星期数{@link Calendar#WEEK_OF_YEAR},一年中第一个星期的值为 1,一年52(365/7=52.14)个星期.
     * 
     * <p>
     * 注意:<br>
     * 2014年的1-1 1-2 1-3 1-4 得出的WEEK_OF_YEAR 是1; <br>
     * 2014年的12-28 12-29 12-30 12-31 得出的WEEK_OF_YEAR 也是1
     * </p>
     * 
     * <pre>
     * 
     * Example 1:
     * 2014-06-03
     * return 23
     * 
     * Example 2:
     * 2014-01-01
     * return 1
     * 
     * Example 3:
     * 2014-12-29
     * return 23
     * 
     * Example 4:
     * 2014-12-20
     * return 51
     * 
     * Example 5:
     * 2014-12-26
     * return 52
     * </pre>
     * 
     * {@link Calendar#setMinimalDaysInFirstWeek(int)} 可以来修改第一周最小天数,但是如果设置为7的话
     * 
     * <pre>
     * 
     * Example 1:
     * 2014-01-01
     * return 52
     * 
     * Example 3:
     * 2014-12-31
     * return 52
     * </pre>
     * 
     * 可以看出,如果从1月1号算开始第一周的话,这年第一周时间不够我们设置的7天,那么1月1号算上一年的星期
     * 
     * @param date
     *            the date
     * @return 当前年中的星期数
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#WEEK_OF_YEAR
     * @see Calendar#getFirstDayOfWeek()
     * @see Calendar#getMinimalDaysInFirstWeek()
     * @see Calendar#setMinimalDaysInFirstWeek(int)
     * @since 1.0.7
     */
    public static int getWeekOfYear(Date date){
        return CalendarUtil.getFieldValue(date, Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获得任意时间中的天(在当年中).
     * 
     * <pre>
     * 2013-01-01
     * return 1
     * 
     * 2013-01-05
     * return 5
     * </pre>
     * 
     * @param date
     *            the date
     * @return 获得任意时间中的天(在当年中)
     * @see com.feilong.core.date.CalendarUtil#getDayOfYear(int, int, int)
     * @see #getFirstDateOfThisYear(Date)
     * @see #getIntervalDay(Date, Date)
     * @since 1.0.2
     */
    public static int getDayOfYear(Date date){
        Date firstDateOfThisYear = getFirstDateOfThisYear(date);
        return getIntervalDay(date, firstDateOfThisYear) + 1;
    }

    /**
     * 获得任意时间中的天{@link Calendar#DAY_OF_MONTH}.
     * 
     * <pre>
     * 2012-06-29
     * return 29
     * </pre>
     * 
     * @param date
     *            the date
     * @return 获得任意时间中的天
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#DAY_OF_MONTH
     */
    public static int getDayOfMonth(Date date){
        return CalendarUtil.getFieldValue(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期星期几{@link Calendar#DAY_OF_WEEK}.
     * 
     * <p style="color:red">
     * 从星期天开始,并且星期天是1.
     * </p>
     * 
     * <pre>
     * SUNDAY、MONDAY、TUESDAY、WEDNESDAY、THURSDAY、FRIDAY 和 SATURDAY
     * 
     * 分别对应1-7
     * </pre>
     * 
     * <pre>
     * 2012-6-29  是星期5
     * return 6
     * </pre>
     * 
     * @param date
     *            the date
     * @return 当前日期星期几
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#DAY_OF_WEEK
     */
    public static int getDayOfWeek(Date date){
        return CalendarUtil.getFieldValue(date, Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得时间中的小时(24小时制) {@link Calendar#HOUR_OF_DAY}.
     * 
     * <pre>
     * 2012-6-29 00:26:53
     * return 0
     * </pre>
     * 
     * @param date
     *            date
     * @return 获得时间中的小时
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#HOUR_OF_DAY
     */
    public static int getHourOfDay(Date date){
        return CalendarUtil.getFieldValue(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得date 在它一年中的 小时数.
     * <p>
     * max value: 8784.
     * </p>
     * 
     * <pre>
     * 
     * 2013-01-01 00:00:05
     * return 0
     * 
     * 2013-01-01 01:00:05
     * return 1
     * 
     * 2013-01-05 12:00:05
     * return 108
     * 
     * 2013-09-09 17:28
     * return 6041
     * </pre>
     * 
     * @param date
     *            date
     * @return 获得date 在它一年中的 小时数
     * @see #getFirstDateOfThisYear(Date)
     * @see #getIntervalHour(Date, Date)
     * @since 1.0.2
     */
    public static int getHourOfYear(Date date){
        Date firstDateOfThisYear = getFirstDateOfThisYear(date);
        return getIntervalHour(firstDateOfThisYear, date);
    }

    /**
     * 获得时间中的分钟 {@link java.util.Calendar#MINUTE}.
     * 
     * <pre>
     * 2012-6-29 00:26:53
     * return 26
     * </pre>
     * 
     * @param date
     *            date
     * @return 获得时间中的分钟
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#MINUTE
     */
    public static int getMinute(Date date){
        return CalendarUtil.getFieldValue(date, Calendar.MINUTE);
    }

    /**
     * 获得时间中的秒{@link java.util.Calendar#SECOND}.
     * 
     * <pre>
     * 2012-6-29 00:26:53
     * return 53
     * </pre>
     * 
     * @param date
     *            date
     * @return 获得时间中的秒
     * @see CalendarUtil#getFieldValue(Date, int)
     * @see Calendar#SECOND
     */
    public static int getSecond(Date date){
        return CalendarUtil.getFieldValue(date, Calendar.SECOND);
    }

    /**
     * 获得时间在当天中的秒数,最大值86400 {@link TimeInterval#SECONDS_PER_DAY}.
     * 
     * <pre>
     * 2013-09-09 16:42:41
     * return 60161
     * </pre>
     * 
     * @param date
     *            date
     * @return 获得当前时间在当天中的秒数
     * @see TimeInterval#SECONDS_PER_DAY
     * @see TimeInterval#SECONDS_PER_HOUR
     * @see #getSecondOfHour(Date)
     * @since 1.0.2
     */
    public static int getSecondOfDay(Date date){
        int hour = getHourOfDay(date);
        return hour * TimeInterval.SECONDS_PER_HOUR + getSecondOfHour(date);
    }

    /**
     * 获得时间在当前小时中的秒数,最大值3600 {@link TimeInterval#SECONDS_PER_HOUR}.
     * 
     * <pre>
     * 2013-09-15 01:15:23
     * return 923
     * </pre>
     * 
     * @param date
     *            date
     * @return 获得时间在当前小时中的秒数
     * @see TimeInterval#SECONDS_PER_MINUTE
     * @see TimeInterval#SECONDS_PER_HOUR
     * @since 1.0.2
     */
    public static int getSecondOfHour(Date date){
        int minute = getMinute(date);
        int second = getSecond(date);
        return second + minute * TimeInterval.SECONDS_PER_MINUTE;
    }

    /**
     * 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来,此 Date 对象表示的毫秒数.
     * 
     * <pre>
     * 2012-6-29 00:28
     * return 1340900883288
     * </pre>
     * 
     * @param date
     *            date
     * @return date.getTime()
     */
    public static long getTime(Date date){
        return date.getTime();
    }

    // [end]

    // [start]date2String/string2Date 类型转换

    /**
     * 将时间转换成特殊格式的字符串.
     * 
     * <pre>
     * date2String(Tue Oct 16 23:49:21 CST 2012,DatePattern.commonWithMillisecond)
     * return 2012-10-16 23:49:21.525
     * </pre>
     * 
     * @param date
     *            任意时间
     * @param datePattern
     *            模式 {@link DatePattern}
     * @return string
     * @see DateFormatUtil#format(Date, String)
     */
    public static String date2String(Date date,String datePattern){
        return DateFormatUtil.format(date, datePattern);
    }

    /**
     * 将时间string字符串转换成date类型.
     * 
     * @param dateString
     *            时间字符串
     * @param datePattern
     *            模式,时间字符串的模式{@link DatePattern}
     * @return 将string字符串转换成date类型
     * @see DateFormatUtil#parse(String, String)
     */
    public static Date string2Date(String dateString,String datePattern){
        return DateFormatUtil.parse(dateString, datePattern);
    }

    // [end]

    // [start]interval时间间隔

    /**
     * 两个时间相差的分数.
     * 
     * @param spaceMillisecond
     *            间隔毫秒
     * @return 相差的分数
     * @see TimeInterval#MILLISECOND_PER_MINUTE
     */
    public static int getIntervalMinute(long spaceMillisecond){
        return (int) (spaceMillisecond / (TimeInterval.MILLISECOND_PER_MINUTE));
    }

    /**
     * 两个时间相差的秒数.
     * 
     * @param date1
     *            the date1
     * @param date2
     *            the date2
     * @return 相差的秒数
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalSecond(long)
     * @since 1.0.2
     */
    public static int getIntervalSecond(Date date1,Date date2){
        long intervalTime = getIntervalTime(date1, date2);
        return getIntervalSecond(intervalTime);
    }

    /**
     * 两个时间相差的秒数.
     * 
     * @param spaceMillisecond
     *            间隔毫秒
     * @return 相差的秒数
     */
    public static int getIntervalSecond(long spaceMillisecond){
        return (int) (spaceMillisecond / 1000);
    }

    /**
     * 两个时间相差的的小时数.
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return 相差的小时数
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalHour(long)
     */
    public static int getIntervalHour(Date date1,Date date2){
        long intervalTime = getIntervalTime(date1, date2);
        return getIntervalHour(intervalTime);
    }

    /**
     * 两个时间相差的小时数.
     * 
     * @param spaceMillisecond
     *            间隔毫秒
     * @return 相差的小时数
     * @see TimeInterval#MILLISECOND_PER_HOUR
     */
    public static int getIntervalHour(long spaceMillisecond){
        return (int) (spaceMillisecond / (TimeInterval.MILLISECOND_PER_HOUR));
    }

    /**
     * 获得相差的星期数.
     *
     * @param date1
     *            the date1
     * @param date2
     *            the date2
     * @param datePattern
     *            日期pattern {@link DatePattern}
     * @return the interval week
     * @see #getIntervalWeek(Date, Date)
     * @since 1.2.1
     */
    public static int getIntervalWeek(String date1,String date2,String datePattern){
        Date dateOne = string2Date(date1, datePattern);
        Date dateTwo = string2Date(date2, datePattern);
        return getIntervalWeek(dateOne, dateTwo);
    }

    /**
     * 获得相差的星期数.
     *
     * @param date1
     *            the date1
     * @param date2
     *            the date2
     * @return the interval week
     * @see #getIntervalWeek(long)
     * @since 1.2.1
     */
    public static int getIntervalWeek(Date date1,Date date2){
        long intervalTime = getIntervalTime(date1, date2);
        return getIntervalWeek(intervalTime);
    }

    /**
     * 获得相差的星期数.
     *
     * @param spaceTime
     *            the space time
     * @return the interval week
     * @see com.feilong.core.date.TimeInterval#SECONDS_PER_WEEK
     * @since 1.2.1
     */
    public static int getIntervalWeek(long spaceTime){
        return (int) (spaceTime / (TimeInterval.MILLISECOND_PER_WEEK));
    }

    //-******************getIntervalDay***************************************

    /**
     * 计算两个时间相差的的天数.
     * 
     * <p>
     * 不管date1是否早于还是晚于date2,均返回 <span style="color:red">绝对值</span>.
     * </p>
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @param datePattern
     *            时间模式 {@link DatePattern}
     * @return 相差的天数
     * @see #string2Date(String, String)
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalDay(long)
     */
    public static int getIntervalDay(String date1,String date2,String datePattern){
        Date dateOne = string2Date(date1, datePattern);
        Date dateTwo = string2Date(date2, datePattern);
        return getIntervalDay(dateOne, dateTwo);
    }

    /**
     * 计算两个时间相差的的天数.
     * 
     * <p>
     * 不管date1是否早于还是晚于date2,均返回 <span style="color:red">绝对值</span>.
     * </p>
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return 相差的天数
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalDay(long)
     */
    public static int getIntervalDay(Date date1,Date date2){
        long intervalTime = getIntervalTime(date1, date2);
        return getIntervalDay(intervalTime);
    }

    /**
     * 两个时间相差的天数.
     * 
     * @param spaceTime
     *            间隔毫秒
     * @return 相差的天数
     * @see TimeInterval#SECONDS_PER_DAY
     */
    public static int getIntervalDay(long spaceTime){
        return (int) (spaceTime / (TimeInterval.MILLISECOND_PER_DAY));
    }

    /**
     * 两个时间相差的毫秒数.
     * 
     * <p>
     * 不管date1是否早于还是晚于date2,均返回 <span style="color:red">绝对值</span>.
     * </p>
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return 两个时间相差的毫秒数,不管date1是否早于还是晚于date2,均返回绝对值
     * @see #getTime(Date)
     * @see Math#abs(long)
     */
    public static long getIntervalTime(Date date1,Date date2){
        return Math.abs(getTime(date2) - getTime(date1));
    }

    // [end]

    // [start]toCalendar

    /**
     * 将 {@link Date} 转成 {@link Calendar},调用 {@link GregorianCalendar}.
     * 
     * <p>
     * {@link Calendar#getInstance()}方法,返回用默认的地区和时区的当前日期和当前时间所初始化的GregorianCalendar（标准日历）,<br>
     * 最终会调用 java.util.Calendar.createCalendar(TimeZone, Locale) 方法,<br>
     * 该方法会判断Locale(日本和泰国),其他国家最终会调用 {@link GregorianCalendar#GregorianCalendar(java.util.TimeZone, java.util.Locale)} 方法
     * </p>
     * 
     * <h3>{@link GregorianCalendar}</h3>
     * 
     * <blockquote>
     * <p>
     * 标准阳历格列高利历/公历,现在的公历是根据罗马人的"儒略历"改编而
     * </p>
     * </blockquote>
     *
     * @param date
     *            date
     * @return Calendar
     * @see Calendar#getInstance()
     * @see GregorianCalendar
     * @see Calendar#setTime(Date)
     * @see Calendar#setTimeInMillis(long)
     * 
     * @see org.apache.commons.lang3.time.DateUtils#toCalendar(Date)
     */
    public static Calendar toCalendar(Date date){
        if (Validator.isNullOrEmpty(date)){
            throw new NullPointerException("date can't be null/empty!");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    // [end]

    // [start]isBefore 时间早晚

    /**
     * 按照同样格式 <code>datePattern</code>,转成Date类型,判断 <code>dateBefore</code> 是否早于 <code>dateAfter</code>.
     * 
     * <pre>
     * Example 1:
     * 
     * isBefore("2011-05-01","2011-04-01",DateUtil.pattern_onlyDate)
     * return true
     * </pre>
     *
     * @param dateBefore
     *            the date before
     * @param dateAfter
     *            the date after
     * @param datePattern
     *            日期pattern {@link DatePattern}
     * @return 如果dateBefore 早于 dateAfter返回 true
     * @see #string2Date(String, String)
     * @see #isBefore(Date, String, String)
     */
    public static boolean isBefore(String dateBefore,String dateAfter,String datePattern){
        Date before = string2Date(dateBefore, datePattern);
        return isBefore(before, dateAfter, datePattern);
    }

    /**
     * 按照同样格式 <code>datePattern</code>,转成Date类型,判断 <code>dateBefore</code> 是否早于 <code>dateAfter</code>.
     * 
     * <pre>
     * Example 1:
     * 
     * isBefore("2011-05-01","2011-04-01",DateUtil.pattern_onlyDate)
     * return true
     * </pre>
     * 
     * @param dateBefore
     *            dateBefore
     * @param dateAfter
     *            dateAfter
     * @param datePattern
     *            日期pattern {@link DatePattern}
     * @return 如果dateBefore 早于 dateAfter返回 true
     * @see #string2Date(String, String)
     * @see #isBefore(String, String, String)
     * @see Date#before(Date)
     */
    public static boolean isBefore(Date dateBefore,String dateAfter,String datePattern){
        Date after = string2Date(dateAfter, datePattern);
        return isBefore(dateBefore, after);
    }

    /**
     * 判断 <code>before</code>是否 在 <code>when</code>时间之前.
     *
     * @param before
     *            the before
     * @param when
     *            the after
     * @return true, if checks if is before
     * @see java.util.Date#before(Date)
     * @since 1.2.2
     */
    public static boolean isBefore(Date before,Date when){
        return before.before(when);
    }

    /**
     * 判断 <code>after</code>是否 在 <code>when</code>时间之后.
     *
     * @param after
     *            the after
     * @param when
     *            the when
     * @return true, if checks if is after
     * @see java.util.Date#after(Date)
     * @since 1.2.2
     */
    public static boolean isAfter(Date after,Date when){
        return after.after(when);
    }

    // [end]

    // [start]isInTime 时间区间内

    /**
     * 判断当前时间是否在两个时间之间.
     * 
     * <pre>
     * 比如现在是 :2012-10-16 23:00:02
     * 
     * isInTime(date, "2012-10-10 22:59:00", "2012-10-18 22:59:00", DatePattern.commonWithTime)
     * 
     * return true
     * </pre>
     * 
     * @param date
     *            需要判断的日期
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param datePattern
     *            开始时间和结束时间的格式{@link DatePattern}
     * @return {@code 如果 date after beginTimeDate&&当前时间before endTimeDate,返回true}
     * @see #string2Date(String, String)
     * @see #isInTime(Date, Date, Date)
     */
    public static boolean isInTime(Date date,String beginTime,String endTime,String datePattern){
        Date beginTimeDate = string2Date(beginTime, datePattern);
        Date endTimeDate = string2Date(endTime, datePattern);
        return isInTime(date, beginTimeDate, endTimeDate);
    }

    /**
     * 判断当前时间是否在两个时间之间.
     * 
     * <pre>
     * 比如现在是 :2012-10-16 23:00:02
     * 
     * isInTime(date, "2012-10-10 22:59:00", "2012-10-18 22:59:00")
     * 
     * return true
     * </pre>
     * 
     * @param date
     *            需要判断的日期
     * @param beginTimeDate
     *            the begin time date
     * @param endTimeDate
     *            the end time date
     * @return {@code 如果 date after beginTimeDate&&当前时间before endTimeDate,返回true}
     * @see Date#after(Date)
     * @see Date#before(Date)
     */
    public static boolean isInTime(Date date,Date beginTimeDate,Date endTimeDate){
        return date.after(beginTimeDate) && date.before(endTimeDate);
    }

    // [end]

    // [start]isEquals
    /**
     * 在相同格式下 <code>datePattern</code>,将两个日期转成字符串判断是否相等.
     * 
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @param datePattern
     *            格式 {@link DatePattern}
     * @return 相等返回true,不相等则为false
     * @see #date2String(Date, String)
     * @since 1.0.5 change name from isEqual to isEquals
     */
    public static boolean isEquals(Date date1,Date date2,String datePattern){
        return date2String(date1, datePattern).equals(date2String(date2, datePattern));
    }

    // [end]

    // [start]isLeapYear 闰年

    /**
     * 判断某年是否为闰年 .
     * 
     * <p>
     * 规则: {@code (year % 4 == 0 && year % 100 != 0) || year % 400 == 0}
     * </p>
     * 
     * <h3>闰年原因:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 地球绕太阳运行周期为365天5小时48分46秒（合365.24219天）即一回归年（tropical year）.<br>
     * 公历的平年只有365日,比回归年短约0.2422日,所余下的时间约为四年累计一天,故四年于2月加1天,使当年的历年长度为366日,这一年就为闰年.<br>
     * </p>
     * 
     * <p>
     * 现行公历中每400年有97个闰年.按照每四年一个闰年计算,平均每年就要多算出0.0078天,这样经过四百年就会多算出大约3天来,<br>
     * 因此,每四百年中要减少三个闰年.
     * </p>
     * 
     * <p>
     * 所以规定,公历年份是整百数的,必须是400的倍数的才是闰年,不是400的倍数的,虽然是100的倍数,也是平年,<br>
     * 这就是通常所说的：四年一闰,百年不闰,四百年再闰.<br>
     * 
     * 例如,2000年是闰年,1900年则是平年.<br>
     * </p>
     * </blockquote>
     * 
     * @param year
     *            年份
     * @return 四年一闰,百年不闰,四百年再闰
     * @see GregorianCalendar#isLeapYear(int)
     */
    public static boolean isLeapYear(int year){
        return new GregorianCalendar().isLeapYear(year);
    }

    // [end]
}