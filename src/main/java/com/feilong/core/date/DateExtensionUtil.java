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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.feilong.core.DatePattern;
import com.feilong.core.TimeInterval;

/**
 * 日期扩展工具类.
 * 
 * <h3>和 {@link DateUtil} 的区别:</h3>
 * 
 * <blockquote>
 * <p>
 * {@link DateUtil}是纯操作Date API的工具类,而 {@link DateExtensionUtil}类用于个性化输出结果,针对业务个性化显示.
 * </p>
 * </blockquote>
 * 
 * <h3>获得两个日期间隔:</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link #getIntervalDayList(String, String, String)}</li>
 * <li>{@link #getIntervalForView(long)}</li>
 * <li>{@link #getIntervalForView(Date, Date)}</li>
 * </ul>
 * </blockquote>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * 
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>获得两个日期间隔</td>
 * <td>
 * <ul>
 * <li>{@link #getIntervalDay(long)}</li>
 * <li>{@link #getIntervalDay(Date, Date)}</li>
 * <li>{@link #getIntervalDay(String, String, String)}</li>
 * 
 * <li>{@link #getIntervalWeek(long)}</li>
 * <li>{@link #getIntervalWeek(Date, Date)}</li>
 * <li>{@link #getIntervalWeek(String, String, String)}</li>
 * 
 * <li>{@link #getIntervalHour(long)}</li>
 * <li>{@link #getIntervalHour(Date, Date)}</li>
 * 
 * <li>{@link #getIntervalMinute(long)}</li>
 * <li>{@link #getIntervalSecond(long)}</li>
 * <li>{@link #getIntervalSecond(Date, Date)}</li>
 * 
 * <li>{@link #getIntervalTime(Date, Date)}</li>
 * </ul>
 * </td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.8
 */
public final class DateExtensionUtil{

    /** 天. */
    private static final String DAY         = "天";

    /** 小时. */
    private static final String HOUR        = "小时";

    /** 分钟. */
    private static final String MINUTE      = "分钟";

    /** 秒. */
    private static final String SECOND      = "秒";

    /** 毫秒. */
    private static final String MILLISECOND = "毫秒";

    /** Don't let anyone instantiate this class. */
    private DateExtensionUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // [start] 获得时间 /时间数组,可以用于sql查询
    /**
     * 获得重置清零的今天和明天,当天0:00:00及下一天0:00:00.
     * 
     * <p>
     * 一般用于统计当天数据,between ... and ...
     * </p>
     * 
     * <pre class="code">
     * 比如今天是 2012-10-16 22:18:34
     * 
     * 返回 {2012-10-16 00:00:00.000,2012-10-17 00:00:00.000}
     * </pre>
     * 
     * @return Date数组 第一个为today 第二个为tomorrow
     */
    public static Date[] getResetTodayAndTomorrow(){
        Calendar calendar = CalendarUtil.resetDayBegin(new Date());
        Date today = calendar.getTime();
        return new Date[] { today, DateUtil.addDay(today, 1) };
    }

    /**
     * 获得重置清零的昨天和今天 [yestoday,today].
     * 
     * <p>
     * 第一个为昨天00:00 <br>
     * 第二个为今天00:00 <br>
     * 一般用于sql/hql统计昨天数据,between ... and ...
     * </p>
     * 
     * <pre class="code">
     * 比如现在 :2012-10-16 22:46:42
     * 
     * 返回  {2012-10-15 00:00:00.000,2012-10-16 00:00:00.000}
     * </pre>
     * 
     * @return Date数组 <br>
     *         第一个为昨天00:00 <br>
     *         第二个为今天00:00
     */
    public static Date[] getResetYesterdayAndToday(){
        Calendar calendar = CalendarUtil.resetDayBegin(new Date());
        Date today = calendar.getTime();
        return new Date[] { DateUtil.addDay(today, -1), today };
    }

    // [end]
    /**
     * 获得两个日期时间的日期间隔时间集合(包含最小和最大值),用于统计日报表.
     * 
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalDayList("2011-03-5 23:31:25.456", "2011-03-10 01:30:24.895", DatePattern.commonWithTime)
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * 2011-03-05 00:00:00
     * 2011-03-06 00:00:00
     * 2011-03-07 00:00:00
     * 2011-03-08 00:00:00
     * 2011-03-09 00:00:00
     * 2011-03-10 00:00:00
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>每天的日期会被重置清零 <code>00:00:00.000</code></li>
     * <li>方法自动辨识 <code>fromDateString</code>和 <code>toDateString</code>哪个是开始时间</li>
     * </ol>
     * </blockquote>
     * 
     * @param fromDateString
     *            开始时间
     * @param toDateString
     *            结束时间
     * @param datePattern
     *            时间模式 {@link DatePattern}
     * @return the interval day list<br>
     *         如果 <code>fromDateString</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fromDateString</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>toDateString</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>toDateString</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>datePattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>datePattern</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #getIntervalDayList(Date, Date)
     */
    public static List<Date> getIntervalDayList(String fromDateString,String toDateString,String datePattern){
        Validate.notBlank(fromDateString, "fromDateString can't be null/empty!");
        Validate.notBlank(toDateString, "toDateString can't be null/empty!");
        Validate.notBlank(datePattern, "datePattern can't be null/empty!");

        Date fromDate = DateUtil.toDate(fromDateString, datePattern);
        Date toDate = DateUtil.toDate(toDateString, datePattern);

        return getIntervalDayList(fromDate, toDate);
    }

    /**
     * 获得两个日期时间的日期间隔时间集合(包含最小和最大值),用于统计日报表.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Date fromDate = DateUtil.toDate("2011-03-5 23:31:25.456", DatePattern.COMMON_DATE_AND_TIME);
     * Date toDate = DateUtil.toDate("2011-03-10 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME);
     * LOGGER.debug(JsonUtil.format(DateExtensionUtil.getIntervalDayList(fromDate, toDate)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * ["2011-03-05 00:00:00",
     * "2011-03-06 00:00:00",
     * "2011-03-07 00:00:00",
     * "2011-03-08 00:00:00",
     * "2011-03-09 00:00:00",
     * "2011-03-10 00:00:00"
     * ]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>每天的日期会被重置清零 <code>00:00:00.000</code></li>
     * <li>方法自动辨识 <code>fromDate</code>和 <code>toDate</code>哪个是开始时间</li>
     * </ol>
     * </blockquote>
     * 
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the interval day list <br>
     *         如果 <code>fromDate</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>toDate</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalDay(Date, Date)
     * @see org.apache.commons.lang3.time.DateUtils#iterator(Calendar, int)
     * @since 1.5.4
     */
    public static List<Date> getIntervalDayList(Date fromDate,Date toDate){
        Validate.notNull(fromDate, "fromDate can't be null!");
        Validate.notNull(toDate, "toDate can't be null!");

        Date minDate = fromDate.before(toDate) ? fromDate : toDate;
        Date maxDate = fromDate.before(toDate) ? toDate : fromDate;

        // ******重置时间********
        Date beginDateReset = DateUtil.getFirstDateOfThisDay(minDate);
        Date endDateReset = DateUtil.getLastDateOfThisDay(maxDate);

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(beginDateReset);

        // 相隔的天数
        int intervalDay = getIntervalDay(beginDateReset, endDateReset);
        for (int i = 0; i < intervalDay; ++i){
            dateList.add(DateUtil.addDay(beginDateReset, i + 1));
        }

        return dateList;
    }

    /**
     * 将两日期之间的间隔,转换成直观的表示方式.
     * 
     * <p>
     * 常用于日志输出一段代码执行时长
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Date beginDate = new Date();
     * 
     * // do some logic
     * // balabala logic
     * 
     * LOGGER.info("use time:}{}", DateExtensionUtil.getIntervalForView(beginDate, new Date()));
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalForView(2011-05-19 8:30:40,2011-05-19 11:30:24)             = 2小时59分44秒
     * DateExtensionUtil.getIntervalForView(2011-05-19 11:31:25.456,2011-05-19 11:30:24.895)    = 1分钟1秒
     * 
     * 自动增加 天,小时,分钟,秒,毫秒中文文字
     * </pre>
     * 
     * </blockquote>
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 将两日期之间的间隔,转换成直观的表示方式
     * @see #getIntervalForView(long)
     * @see #getIntervalTime(Date, Date)
     */
    public static String getIntervalForView(Date beginDate,Date endDate){
        long spaceTime = getIntervalTime(beginDate, endDate);
        return getIntervalForView(spaceTime);
    }

    /**
     * 将间隔毫秒数,转换成直观的表示方式.
     * 
     * <p>
     * 常用于日志输出一段代码执行时长
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalForView(13516)    = 13秒516毫秒
     * DateExtensionUtil.getIntervalForView(0)        = 0
     * 
     * 自动增加 天,小时,分钟,秒,毫秒中文文字
     * </pre>
     * 
     * </blockquote>
     * 
     * @param spaceMilliseconds
     *            总共相差的毫秒数
     * @return 将间隔毫秒数,转换成直观的表示方式.<br>
     *         如果 spaceMilliseconds 是0 直接返回0<br>
     *         如果 {@code spaceMilliseconds<0},抛出 {@link IllegalArgumentException}
     * @see #getIntervalDay(long)
     * @see #getIntervalHour(long)
     * @see #getIntervalMinute(long)
     * @see #getIntervalSecond(long)
     * @see org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean)
     */
    public static String getIntervalForView(long spaceMilliseconds){
        Validate.isTrue(spaceMilliseconds >= 0, "spaceMilliseconds can't <0");

        if (0 == spaceMilliseconds){
            return "0";
        }
        // **************************************************************************************
        // 间隔天数
        long spaceDay = getIntervalDay(spaceMilliseconds);
        // 间隔小时 减去间隔天数后,
        long spaceHour = getIntervalHour(spaceMilliseconds) - spaceDay * 24;
        // 间隔分钟 减去间隔天数及间隔小时后,
        long spaceMinute = getIntervalMinute(spaceMilliseconds) - (spaceDay * 24 + spaceHour) * 60;
        // 间隔秒 减去间隔天数及间隔小时,间隔分钟后,
        long spaceSecond = getIntervalSecond(spaceMilliseconds) - ((spaceDay * 24 + spaceHour) * 60 + spaceMinute) * 60;
        // 间隔毫秒 减去间隔天数及间隔小时,间隔分钟,间隔秒后,
        long spaceMillisecond = spaceMilliseconds - (((spaceDay * 24 + spaceHour) * 60 + spaceMinute) * 60 + spaceSecond) * 1000;
        // **************************************************************************************
        StringBuilder sb = new StringBuilder();
        if (0 != spaceDay){
            sb.append(spaceDay + DAY);
        }
        if (0 != spaceHour){
            sb.append(spaceHour + HOUR);
        }
        if (0 != spaceMinute){
            sb.append(spaceMinute + MINUTE);
        }
        if (0 != spaceSecond){
            sb.append(spaceSecond + SECOND);
        }
        if (0 != spaceMillisecond){
            sb.append(spaceMillisecond + MILLISECOND);
        }
        return sb.toString();
    }

    // [start]interval时间间隔

    /**
     * 两个时间相差的分数.
     * 
     * @param spaceMilliseconds
     *            间隔毫秒
     * @return 相差的分数
     * @see TimeInterval#MILLISECOND_PER_MINUTE
     * @since 1.6.0
     */
    public static int getIntervalMinute(long spaceMilliseconds){
        return (int) (spaceMilliseconds / (TimeInterval.MILLISECOND_PER_MINUTE));
    }

    /**
     * 两个时间相差的秒数(<span style="color:red">绝对值</span>).
     * 
     * @param date1
     *            the date1
     * @param date2
     *            the date2
     * @return 相差的秒数 <br>
     *         如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalSecond(long)
     * @since 1.6.0
     */
    public static int getIntervalSecond(Date date1,Date date2){
        long intervalTime = getIntervalTime(date1, date2);
        return getIntervalSecond(intervalTime);
    }

    /**
     * 两个时间相差的秒数.
     * 
     * @param spaceMilliseconds
     *            间隔毫秒
     * @return 相差的秒数
     * @since 1.6.0
     */
    public static int getIntervalSecond(long spaceMilliseconds){
        return (int) (spaceMilliseconds / 1000);
    }

    /**
     * 两个时间相差的的小时数(<span style="color:red">绝对值</span>).
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return 相差的小时数<br>
     *         如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalHour(long)
     * @since 1.6.0
     */
    public static int getIntervalHour(Date date1,Date date2){
        long intervalTime = getIntervalTime(date1, date2);
        return getIntervalHour(intervalTime);
    }

    /**
     * 两个时间相差的小时数.
     * 
     * @param spaceMilliseconds
     *            间隔毫秒
     * @return 相差的小时数
     * @see TimeInterval#MILLISECOND_PER_HOUR
     * @since 1.6.0
     */
    public static int getIntervalHour(long spaceMilliseconds){
        return (int) (spaceMilliseconds / (TimeInterval.MILLISECOND_PER_HOUR));
    }

    /**
     * 获得相差的星期数(<span style="color:red">绝对值</span>).
     *
     * @param date1
     *            the date1
     * @param date2
     *            the date2
     * @param datePattern
     *            日期pattern {@link DatePattern}
     * @return the interval week
     * @see #getIntervalWeek(Date, Date)
     * @since 1.6.0
     */
    public static int getIntervalWeek(String date1,String date2,String datePattern){
        Date dateOne = DateUtil.toDate(date1, datePattern);
        Date dateTwo = DateUtil.toDate(date2, datePattern);
        return getIntervalWeek(dateOne, dateTwo);
    }

    /**
     * 获得相差的星期数(<span style="color:red">绝对值</span>).
     *
     * @param date1
     *            the date1
     * @param date2
     *            the date2
     * @return the interval week<br>
     *         如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalWeek(long)
     * @since 1.6.0
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
     * @see com.feilong.core.TimeInterval#SECONDS_PER_WEEK
     * @since 1.6.0
     */
    public static int getIntervalWeek(long spaceTime){
        return (int) (spaceTime / (TimeInterval.MILLISECOND_PER_WEEK));
    }

    //-******************getIntervalDay***************************************

    /**
     * 计算两个时间相差的的天数 (<span style="color:red">绝对值</span>).
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @param datePattern
     *            时间模式 {@link DatePattern}
     * @return 相差的天数
     * @see DateUtil#toDate(String, String)
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalDay(long)
     * @since 1.6.0
     */
    public static int getIntervalDay(String date1,String date2,String datePattern){
        Date dateOne = DateUtil.toDate(date1, datePattern);
        Date dateTwo = DateUtil.toDate(date2, datePattern);
        return getIntervalDay(dateOne, dateTwo);
    }

    /**
     * 计算两个时间相差的的天数(<span style="color:red">绝对值</span>).
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return 相差的天数<br>
     *         如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalDay(long)
     * @since 1.6.0
     */
    public static int getIntervalDay(Date date1,Date date2){
        long intervalTime = getIntervalTime(date1, date2);
        return getIntervalDay(intervalTime);
    }

    /**
     * 两个时间相差的天数.
     * 
     * @param spaceMilliseconds
     *            间隔毫秒
     * @return 相差的天数
     * @see TimeInterval#SECONDS_PER_DAY
     * @since 1.6.0
     */
    public static int getIntervalDay(long spaceMilliseconds){
        return (int) (spaceMilliseconds / (TimeInterval.MILLISECOND_PER_DAY));
    }

    /**
     * 两个时间相差的毫秒数(<span style="color:red">绝对值</span>).
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return 两个时间相差的毫秒数,不管date1是否早于还是晚于date2,均返回绝对值<br>
     *         如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see DateUtil#getTime(Date)
     * @see Math#abs(long)
     * @since 1.6.0
     */
    public static long getIntervalTime(Date date1,Date date2){
        Validate.notNull(date1, "date1 can't be null!");
        Validate.notNull(date2, "date2 can't be null!");
        return Math.abs(DateUtil.getTime(date2) - DateUtil.getTime(date1));
    }

    // [end]

}