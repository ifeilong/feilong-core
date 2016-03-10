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
 * <h3>Calendar各个字段的定义:</h3>
 * 
 * <blockquote>
 * 我们使用Calendar,无非是就是使用这17个字段,参见 {@link java.util.Calendar#FIELD_COUNT}
 * 
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td></td>
 * <td></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#ERA}</td>
 * <td>只能为0 或 1。0表示BC(“before Christ”,即公元前),1表示AD(拉丁语“Anno Domini”,即公元)</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#YEAR}</td>
 * <td>年</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#MONTH}</td>
 * <td>月 取值：可以为,JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER, UNDECIMBER。 其中第一个月是
 * JANUARY,它为 0</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#WEEK_OF_YEAR}</td>
 * <td>当前日期在本年中对应第几个星期。一年中第一个星期的值为 1</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#WEEK_OF_MONTH}</td>
 * <td>当前日期在本月中对应第几个星期。一个月中第一个星期的值为 1</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#DATE}</td>
 * <td>日。一个月中第一天的值为 1</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#DAY_OF_MONTH}</td>
 * <td>同“DATE”,表示“日”</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#DAY_OF_YEAR}</td>
 * <td>当前日期在本年中对应第几天。一年中第一天的值为 1。</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#DAY_OF_WEEK}</td>
 * <td>星期几。 取值：可以为,SUNDAY、MONDAY、TUESDAY、WEDNESDAY、THURSDAY、FRIDAY 和 SATURDAY。 其中,SUNDAY为1,MONDAY为2,依次类推。</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</td>
 * <td>当前月中的第几个星期。 取值：DAY_OF_MONTH 1 到 7 总是对应于 DAY_OF_WEEK_IN_MONTH 1；8 到 14 总是对应于 DAY_OF_WEEK_IN_MONTH 2,依此类推。 <br>
 * DAY_OF_WEEK_IN_MONTH 指示当前月中的第几个星期。与 DAY_OF_WEEK 字段一起使用时,就可以唯一地指定某月中的某一天。 <br>
 * 与 WEEK_OF_MONTH 和 WEEK_OF_YEAR 不同,该字段的值并不 取决于 getFirstDayOfWeek() 或 getMinimalDaysInFirstWeek()。 <br>
 * DAY_OF_MONTH 1 到 7 总是对应于 DAY_OF_WEEK_IN_MONTH 1； <br>
 * 8 到 14 总是对应于 DAY_OF_WEEK_IN_MONTH 2,依此类推。 <br>
 * DAY_OF_WEEK_IN_MONTH 0 表示 DAY_OF_WEEK_IN_MONTH 1 之前的那个星期。 <br>
 * 负值是从一个月的末尾开始逆向计数,因此,一个月的最后一个星期天被指定为 DAY_OF_WEEK = SUNDAY, DAY_OF_WEEK_IN_MONTH = -1。 <br>
 * 因为负值是逆向计数的,所以它们在月份中的对齐方式通常与正值的不同。 <br>
 * 例如,如果一个月有 31 天,那么 DAY_OF_WEEK_IN_MONTH -1 将与 DAY_OF_WEEK_IN_MONTH 5 和 DAY_OF_WEEK_IN_MONTH 4 的末尾相重叠</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#AM_PM}</td>
 * <td>上午 还是 下午 取值：可以是AM 或 PM。AM为0,表示上午；PM为1,表示下午。</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#HOUR}</td>
 * <td>指示一天中的第几小时。 HOUR 用于 12 小时制时钟 (0 - 11)。中午和午夜用 0 表示,不用 12 表示</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#HOUR_OF_DAY}</td>
 * <td>指示一天中的第几小时。 HOUR_OF_DAY 用于 24 小时制时钟。例如,在 10:04:15.250 PM 这一时刻,HOUR_OF_DAY 为 22</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#MINUTE}</td>
 * <td>一小时中的第几分钟。 例如,在 10:04:15.250 PM这一时刻,MINUTE 为 4</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#SECOND}</td>
 * <td>一分钟中的第几秒。 例如,在 10:04:15.250 PM 这一时刻,SECOND 为 15</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#MILLISECOND}</td>
 * <td>一秒中的第几毫秒。 例如,在 10:04:15.250 PM 这一时刻,MILLISECOND 为 250。</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#ZONE_OFFSET}</td>
 * <td>毫秒为单位指示距 GMT 的大致偏移量</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#DST_OFFSET}</td>
 * <td>毫秒为单位指示夏令时的偏移量。</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <h3>{@link Calendar#getActualMaximum(int)} VS {@link Calendar#getMaximum(int)}</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link Calendar#getMaximum(int)}</td>
 * <td>获取的“字段最大值”,是指在综合所有的日期,在所有这些日期中得出的“字段最大值”。<br>
 * 例如,getMaximum(Calendar.DATE)的目的是“获取‘日的最大值’”。综合所有的日期,得出一个月最多有31天。因此,getMaximum(Calendar.DATE)的返回值是“31”！</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#getActualMaximum(int)}</td>
 * <td>获取的“当前日期时,该字段的最大值”。<br>
 * 例如,当日期为2013-09-01时,getActualMaximum(Calendar.DATE)是获取“日的最大值”是“30”。当前日期是9月份,而9月只有30天。因此,getActualMaximum(Calendar.DATE)的返回值是“30”！</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.1 Aug 4, 2010 9:06:54 PM
 * @see DateUtil
 * @since 1.0.1
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
     * 例如: {@code 2011-01-01 10:20:20 return 2011-01-01 00:00:00}.
     * </p>
     * 
     * @param date
     *            the date
     * @return 获得任意日期的00:00:00
     */
    public static Calendar resetDayBegin(Date date){
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

    /**
     * 一年结束,最后的时间 <code>12月31号 23:59:59.999</code>
     *
     * @param calendar
     *            the calendar
     * @return the calendar
     * @since 1.3.0
     */
    public static Calendar resetYearEnd(Calendar calendar){
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        return resetDayEnd(calendar);
    }

    // [end]

    //*********************************************************************************************

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
        return getFieldValue(calendar, Calendar.DAY_OF_YEAR);// - 1
    }

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
     * @since 1.3.0
     */
    public static int getFieldValue(Date date,int field){
        Calendar calendar = DateUtil.toCalendar(date);
        return getFieldValue(calendar, field);
    }

    /**
     * 获得日历字段值.
     *
     * @param calendar
     *            the calendar
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
     * @since 1.3.0
     */
    public static int getFieldValue(Calendar calendar,int field){
        return calendar.get(field);
    }

    //**************************************************************************************

    /**
     * 将calendar转成Date.
     * 
     * @param calendar
     *            calendar
     * @return Date
     */
    public static Date toDate(Calendar calendar){
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
        return DateUtil.toCalendar(date);
    }

    /**
     * 设置日历字段 YEAR、MONTH 和 DAY_OF_MONTH 的值.
     * <p>
     * 保留其他日历字段以前的值.如果不需要这样做,则先调用 {@link java.util.Calendar#clear()}
     * </p>
     * 
     * @param year
     *            用来设置 YEAR 日历字段的值
     * @param month
     *            用来设置 MONTH 日历字段的值.此处传递是我们口头意义上的月份, 内部自动进行-1的操作<br>
     *            比如 8月就传递 8 ; 9月就传9 <br>
     *            注:Java 的date Month 值是基于 0 的.例如,0 表示 January.
     * @param day
     *            用来设置 DAY_OF_MONTH 日历字段的值.
     * @return the calendar
     * @see Calendar#clear()
     */
    public static Calendar toCalendar(int year,int month,int day){
        Calendar calendar = new GregorianCalendar();

        // 在使用set方法之前,必须先clear一下,否则很多信息会继承自系统当前时间
        calendar.clear();

        calendar.set(year, month - 1, day);
        return calendar;
    }
}
