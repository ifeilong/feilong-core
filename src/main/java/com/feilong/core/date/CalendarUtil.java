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

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.Validate;

import com.feilong.core.DatePattern;

/**
 * 扩展 {@link DateUtil}类,更多人性化的操作及转换 .
 * 
 * <h3>Calendar各个字段的定义:</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * 我们使用Calendar,无非是就是使用这17个字段,参见 {@link java.util.Calendar#FIELD_COUNT}
 * </p>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td></td>
 * <td></td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#ERA}</td>
 * <td>只能为0 或 1.<br>
 * 0表示BC("before Christ",即公元前),<br>
 * 1表示AD(拉丁语"Anno Domini",即公元)</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#YEAR}</td>
 * <td>年</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#MONTH}</td>
 * <td>月
 * 
 * <p>
 * 取值:可以为,{@link Calendar#JANUARY JANUARY}, {@link Calendar#FEBRUARY FEBRUARY}, {@link Calendar#MARCH MARCH}, {@link Calendar#APRIL APRIL},
 * {@link Calendar#MAY MAY},{@link Calendar#JUNE JUNE}, {@link Calendar#JULY JULY},{@link Calendar#AUGUST AUGUST},{@link Calendar#SEPTEMBER
 * SEPTEMBER}, {@link Calendar#OCTOBER OCTOBER},{@link Calendar#NOVEMBER NOVEMBER}, {@link Calendar#DECEMBER DECEMBER},
 * {@link Calendar#UNDECIMBER UNDECIMBER}. <br>
 * 其中第一个月是 {@link Calendar#JANUARY JANUARY},它为 0
 * </p>
 * 
 * </td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#WEEK_OF_YEAR}</td>
 * <td>当前日期在本年中对应第几个星期.一年中第一个星期的值为 1</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#WEEK_OF_MONTH}</td>
 * <td>当前日期在本月中对应第几个星期.一个月中第一个星期的值为 1</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#DATE}</td>
 * <td>日.一个月中第一天的值为 1</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#DAY_OF_MONTH}</td>
 * <td>同"DATE",表示"日"</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#DAY_OF_YEAR}</td>
 * <td>当前日期在本年中对应第几天.一年中第一天的值为 1.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#DAY_OF_WEEK}</td>
 * <td>星期几.
 * 
 * <p>
 * 取值可以为,{@link Calendar#SUNDAY SUNDAY}、{@link Calendar#MONDAY MONDAY}、{@link Calendar#TUESDAY TUESDAY}、{@link Calendar#WEDNESDAY WEDNESDAY}
 * 、{@link Calendar#THURSDAY THURSDAY}、{@link Calendar#FRIDAY FRIDAY}和 {@link Calendar#SATURDAY SATURDAY}. <br>
 * 其中,{@link Calendar#SUNDAY}为1,{@link Calendar#MONDAY}为2,依次类推.
 * </p>
 * 
 * </td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH}</td>
 * <td>当前月中的第几个星期. <br>
 * 
 * <p>
 * {@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH} 指示当前月中的第几个星期.与 {@link Calendar#DAY_OF_WEEK DAY_OF_WEEK}
 * 字段一起使用时,就可以唯一地指定某月中的某一天. <br>
 * 
 * 与 {@link Calendar#WEEK_OF_MONTH WEEK_OF_MONTH} 和 {@link Calendar#WEEK_OF_YEAR WEEK_OF_YEAR} 不同,该字段的值并不取决于
 * {@link Calendar#getFirstDayOfWeek()} 或{@link Calendar#getMinimalDaysInFirstWeek()}. <br>
 * </p>
 * 
 * <p>
 * 取值:DAY_OF_MONTH 1 到 7 总是对应于 {@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH} 1;8 到 14 总是对应于
 * {@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH} 2,依此类推.{@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH} 0 表示
 * {@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH} 1 之前的那个星期.
 * </p>
 * 
 * <p>
 * 负值是从一个月的末尾开始逆向计数,因此,一个月的最后一个星期天被指定为 DAY_OF_WEEK = SUNDAY, DAY_OF_WEEK_IN_MONTH = -1. <br>
 * 因为负值是逆向计数的,所以它们在月份中的对齐方式通常与正值的不同.
 * </p>
 * 
 * <p>
 * 例如,如果一个月有 31 天,那么 {@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH} -1 将与 {@link Calendar#DAY_OF_WEEK_IN_MONTH
 * DAY_OF_WEEK_IN_MONTH} 5 和 {@link Calendar#DAY_OF_WEEK_IN_MONTH DAY_OF_WEEK_IN_MONTH} 4 的末尾相重叠
 * </p>
 * 
 * </td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#AM_PM}</td>
 * <td>上午 还是 下午<br>
 * 取值:可以是AM 或 PM.AM为0,表示上午;PM为1,表示下午.</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#HOUR}</td>
 * <td>指示一天中的第几小时. {@link Calendar#HOUR} 用于 12 小时制时钟 (0 - 11).<br>
 * 中午和午夜用 0 表示,不用 12 表示</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#HOUR_OF_DAY}</td>
 * <td>指示一天中的第几小时. {@link Calendar#HOUR_OF_DAY} 用于 24 小时制时钟.<br>
 * 例如,在 10:04:15.250 PM 这一时刻,{@link Calendar#HOUR_OF_DAY} 为 22</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#MINUTE}</td>
 * <td>一小时中的第几分钟. <br>
 * 例如,在 10:04:15.250 PM这一时刻,{@link Calendar#MINUTE} 为 4</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#SECOND}</td>
 * <td>一分钟中的第几秒. <br>
 * 例如,在 10:04:15.250 PM 这一时刻,{@link Calendar#SECOND} 为 15</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#MILLISECOND}</td>
 * <td>一秒中的第几毫秒. <br>
 * 例如,在 10:04:15.250 PM 这一时刻,{@link Calendar#MILLISECOND} 为 250.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#ZONE_OFFSET}</td>
 * <td>毫秒为单位指示距 GMT 的大致偏移量</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#DST_OFFSET}</td>
 * <td>毫秒为单位指示夏令时的偏移量.</td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * <h3>{@link Calendar#getActualMaximum(int)} VS {@link Calendar#getMaximum(int)}</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Calendar#getMaximum(int)}</td>
 * <td>获取的"字段最大值",是指在综合所有的日期,在所有这些日期中得出的"字段最大值".
 * 
 * <p>
 * 例如,getMaximum(Calendar.DATE)的目的是"获取‘日的最大值’".<br>
 * 综合所有的日期,得出一个月最多有31天.因此,getMaximum(Calendar.DATE)的返回值是"31"！
 * </p>
 * 
 * </td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Calendar#getActualMaximum(int)}</td>
 * <td>获取的"当前日期时,该字段的最大值".
 * 
 * <p>
 * 例如,当日期为2013-09-01时,getActualMaximum(Calendar.DATE)是获取"日的最大值"是"30".<br>
 * 当前日期是9月份,而9月只有30天.因此,getActualMaximum(Calendar.DATE)的返回值是"30"！
 * </p>
 * 
 * </td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see DateUtil
 * @since 1.0.1
 */
final class CalendarUtil{

    /** Don't let anyone instantiate this class. */
    private CalendarUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    // [start]

    /**
     * 一天开始,<code>00:00:00.000</code>
     *
     * @param calendar
     *            the calendar
     * @return 如果 <code>calendar</code> 是null,抛出 {@link NullPointerException}
     * @see Calendar#set(int, int)
     * @see Calendar#HOUR_OF_DAY
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#MILLISECOND
     * @see org.apache.commons.lang3.time.DateUtils#truncate(Calendar, int)
     * @since 1.3.0
     */
    static Calendar resetDayBegin(Calendar calendar){
        Validate.notNull(calendar, "calendar can't be null!");
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar;
    }

    /**
     * 一天结束,最后的时间 <code>23:59:59.999</code>
     *
     * @param calendar
     *            the calendar
     * @return 如果 <code>calendar</code> 是null,抛出 {@link NullPointerException}
     * @see Calendar#set(int, int)
     * @see Calendar#HOUR_OF_DAY
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#MILLISECOND
     * @since 1.3.0
     */
    static Calendar resetDayEnd(Calendar calendar){
        Validate.notNull(calendar, "calendar can't be null!");
        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(MINUTE, 59);
        calendar.set(SECOND, 59);
        calendar.set(MILLISECOND, 999);
        return calendar;
    }

    /**
     * 一年结束,最后的时间 <code>12月31号 23:59:59.999</code>
     *
     * @param calendar
     *            the calendar
     * @return 如果 <code>calendar</code> 是null,抛出 {@link NullPointerException}
     * @see #resetDayEnd(Calendar)
     * @since 1.3.0
     */
    static Calendar resetYearEnd(Calendar calendar){
        Validate.notNull(calendar, "calendar can't be null!");
        calendar.set(MONTH, DECEMBER);
        calendar.set(DAY_OF_MONTH, 31);
        return resetDayEnd(calendar);
    }

    // [end]

    //---------------------------------------------------------------

    /**
     * 将 {@link Calendar} 转成 {@link Date}.
     * 
     * @param calendar
     *            calendar
     * @return 如果 <code>calendar</code> 是null,抛出 {@link NullPointerException}
     * @see java.util.Calendar#getTime()
     */
    static Date toDate(Calendar calendar){
        Validate.notNull(calendar, "calendar can't be null!");
        return calendar.getTime();
    }

    /**
     * 将{@link Calendar}转成{@link String}.
     * 
     * @param calendar
     *            calendar
     * @param datePattern
     *            日期pattern {@link DatePattern}
     * @return 如果 <code>calendar</code> 是null,抛出 {@link NullPointerException}
     * @see com.feilong.core.date.DateUtil#toString(Date, String)
     */
    static String toString(Calendar calendar,String datePattern){
        Date date = toDate(calendar);
        return DateUtil.toString(date, datePattern);
    }

    //---------------------------------------------------------------

    /**
     * 获得日历字段值.
     *
     * @param date
     *            date
     * @param field
     *            Calendar字段:<br>
     *            月份:{@link Calendar#MONTH}(真实值需要加1处理),<br>
     *            日:{@link Calendar#DAY_OF_MONTH},<br>
     *            年份:{@link Calendar#YEAR}<br>
     *            ...
     * @return 如果 <code>calendar</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #getFieldValue(Calendar, int)
     * @since 1.3.0
     */
    static int getFieldValue(Date date,int field){
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
     *            月份:{@link Calendar#MONTH}(真实值需要加1处理),<br>
     *            日:{@link Calendar#DAY_OF_MONTH},<br>
     *            年份:{@link Calendar#YEAR}<br>
     *            ...
     * @return 如果 <code>calendar</code> 是null,抛出 {@link NullPointerException}<br>
     * @see java.util.Calendar#get(int)
     * @since 1.3.0
     */
    static int getFieldValue(Calendar calendar,int field){
        Validate.notNull(calendar, "calendar can't be null!");
        return calendar.get(field);
    }
}
