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

import static com.feilong.core.TimeInterval.MILLISECOND_PER_DAY;
import static com.feilong.core.TimeInterval.MILLISECOND_PER_HOUR;
import static com.feilong.core.TimeInterval.MILLISECOND_PER_MINUTE;
import static com.feilong.core.TimeInterval.MILLISECOND_PER_SECONDS;
import static com.feilong.core.TimeInterval.MILLISECOND_PER_WEEK;
import static com.feilong.core.date.DateUtil.addDay;
import static com.feilong.core.date.DateUtil.getFirstDateOfThisDay;
import static com.feilong.core.date.DateUtil.getFirstDateOfThisMonth;
import static com.feilong.core.date.DateUtil.getFirstDateOfThisYear;
import static com.feilong.core.date.DateUtil.getLastDateOfThisDay;
import static com.feilong.core.date.DateUtil.getLastDateOfThisMonth;
import static com.feilong.core.date.DateUtil.getLastDateOfThisYear;
import static com.feilong.core.date.DateUtil.getTime;
import static com.feilong.core.date.DateUtil.now;

import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

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
 * <h3>格式化日期间隔字符串:</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link #formatDuration(long)}</li>
 * <li>{@link #formatDuration(Date)}</li>
 * <li>{@link #formatDuration(Date, Date)}</li>
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
 * <li>{@link #getIntervalDay(Date, Date)}</li>
 * <li>{@link #getIntervalWeek(Date, Date)}</li>
 * <li>{@link #getIntervalHour(Date, Date)}</li>
 * <li>{@link #getIntervalSecond(Date, Date)}</li>
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

    //---------------------------------------------------------------

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

    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private DateExtensionUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    // [start] 获得时间 /时间数组,可以用于sql查询

    /**
     * 获得 今天的开始时间 <code>00:00:00.000</code> 及今天的结束时间 <code>23:59:59.999</code>.
     * 
     * <p>
     * 一般用于统计当天数据,between ... and ...
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如今天的是 2012-10-16 22:18:34
     * </p>
     * 
     * <b>返回:</b> 2012-10-16 00:00:00.000, 2012-10-16 23:59:59.999
     * 
     * </blockquote>
     *
     * @return 左边,今天的开始时间 <code>00:00:00.000</code> <br>
     *         右边,今天的结束时间 <code>23:59:59.999</code> <br>
     * @see "java.time.LocalDate#atStartOfDay()"
     * @see "java.time.LocalDate#atStartOfDay()"
     * @since 1.10.6
     */
    public static Pair<Date, Date> getTodayStartAndEndPair(){
        Date date = now();
        return getDayStartAndEndPair(date);
    }

    /**
     * 获得 昨天的开始时间 <code>00:00:00.000</code> 及昨天的结束时间 <code>23:59:59.999</code>.
     * 
     * <p>
     * 一般用于统计昨天数据,between ... and ...
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如今天的是 2012-10-16 22:18:34
     * </p>
     * 
     * <b>返回:</b> 2012-10-15 00:00:00.000, 2012-10-15 23:59:59.999
     * 
     * </blockquote>
     *
     * @return 左边,昨天的开始时间 <code>00:00:00.000</code> <br>
     *         右边,昨天的结束时间 <code>23:59:59.999</code> <br>
     * @since 1.10.6
     */
    public static Pair<Date, Date> getYesterdayStartAndEndPair(){
        Date date = now();
        Date yesteday = addDay(date, -1);
        return getDayStartAndEndPair(yesteday);
    }

    /**
     * 获得 指定日的开始时间 <code>00:00:00.000</code> 及指定日的结束时间 <code>23:59:59.999</code>.
     * 
     * <p>
     * 一般用于统计指定日期数据,between ... and ...
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如传入的是 2012-10-16 22:18:34
     * </p>
     * 
     * <b>返回:</b> 2012-10-16 00:00:00.000, 2012-10-16 23:59:59.999
     * 
     * </blockquote>
     * 
     * @param date
     *            the date
     * @return 如果 <code>date</code> 是null,抛出 {@link NullPointerException}<br>
     *         左边,指定日的开始时间 <code>00:00:00.000</code> <br>
     *         右边,指定日的结束时间 <code>23:59:59.999</code> <br>
     * @since 1.10.6
     */
    public static Pair<Date, Date> getDayStartAndEndPair(Date date){
        Validate.notNull(date, "date can't be null!");
        return Pair.of(getFirstDateOfThisDay(date), getLastDateOfThisDay(date));
    }

    //---------------------------------------------------------------

    /**
     * 获得 当前月的开始时间 <code>00:00:00.000</code> 及当前月结束时间 <code>23:59:59.999</code>.
     * 
     * <p>
     * 一般用于统计当前月的数据,between ... and ...
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如传入的是 2012-10-16 22:18:34
     * </p>
     * 
     * <b>返回:</b> 2012-10-01 00:00:00.000, 2012-10-31 23:59:59.999
     * 
     * </blockquote>
     * 
     * @return 左边,当前月的第一天 <code>00:00:00.000</code> <br>
     *         右边,当前月最后一天 <code>23:59:59.999</code> <br>
     * @since 1.10.6
     */
    public static Pair<Date, Date> getMonthStartAndEndPair(){
        return getMonthStartAndEndPair(now());
    }

    /**
     * 获得 指定月的开始时间 <code>00:00:00.000</code> 及指定月的结束时间 <code>23:59:59.999</code>.
     * 
     * <p>
     * 一般用于统计指定月数据,between ... and ...
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如传入的是 2012-10-16 22:18:34
     * </p>
     * 
     * <b>返回:</b> 2012-10-01 00:00:00.000, 2012-10-31 23:59:59.999
     * 
     * </blockquote>
     * 
     * @param date
     *            the date
     * @return 如果 <code>date</code> 是null,抛出 {@link NullPointerException}<br>
     *         左边,当前月的第一天 <code>00:00:00.000</code> <br>
     *         右边,当前月最后一天 <code>23:59:59.999</code> <br>
     * @since 1.10.6
     */
    public static Pair<Date, Date> getMonthStartAndEndPair(Date date){
        Validate.notNull(date, "date can't be null!");
        return Pair.of(getFirstDateOfThisMonth(date), getLastDateOfThisMonth(date));
    }

    /**
     * 获得 当前年的开始时间 <code>00:00:00.000</code> 及当前年的结束时间 <code>23:59:59.999</code>.
     * 
     * <p>
     * 一般用于统计当前年数据,between ... and ...
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如传入的是 2012-10-16 22:18:34
     * </p>
     * 
     * <b>返回:</b> 2012-01-01 00:00:00.000, 2012-12-31 23:59:59.999
     * 
     * </blockquote>
     * 
     * @return 左边,当前年的第一天 <code>00:00:00.000</code> <br>
     *         右边,当前年最后一天 <code>23:59:59.999</code> <br>
     * @since 1.10.6
     */
    public static Pair<Date, Date> getYearStartAndEndPair(){
        return getYearStartAndEndPair(now());
    }

    /**
     * 获得 指定年的开始时间 <code>00:00:00.000</code> 及指定年的结束时间 <code>23:59:59.999</code>.
     * 
     * <p>
     * 一般用于统计指定年数据,between ... and ...
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如传入的是 2012-10-16 22:18:34
     * </p>
     * 
     * <b>返回:</b> 2012-01-01 00:00:00.000, 2012-12-31 23:59:59.999
     * 
     * </blockquote>
     * 
     * @param date
     *            the date
     * @return 如果 <code>date</code> 是null,抛出 {@link NullPointerException}<br>
     *         左边,当前年的第一天 <code>00:00:00.000</code> <br>
     *         右边,当前年最后一天 <code>23:59:59.999</code> <br>
     * @since 1.10.6
     */
    public static Pair<Date, Date> getYearStartAndEndPair(Date date){
        Validate.notNull(date, "date can't be null!");

        return Pair.of(getFirstDateOfThisYear(date), getLastDateOfThisYear(date));
    }

    // [end]

    //---------------------------------------------------------------

    /**
     * 将开始时间 <code>beginDate</code> 到当前时间 <code>now()</code>,两日期之间的<span style="color:red">绝对值</span>间隔,格式化成直观的表示方式.
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>常用于日志输出一段代码执行时长</li>
     * <li>计算的是开始时间 <code>beginDate</code> 到当前时间 <code>now()</code> 绝对值间隔时间,也就是说不care 时间先后顺序</li>
     * <li>间隔时间转成 <b>天,小时,分钟,秒,毫秒</b> 中文文字,月和较大的格式不使用</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Date beginDate = now();
     * 
     * <span style="color:green">// do some logic</span>
     * <span style="color:green">// balabala logic</span>
     * 
     * LOGGER.info("use time: [{}]", DateExtensionUtil.getIntervalForView(beginDate));
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * <p>
     * 如果当前时间是 2016-07-09 13:03:53.259
     * </p>
     * 
     * <pre class="code">
     * Date date = toDate("2016-07-03 00:00:00", COMMON_DATE_AND_TIME);
     * LOGGER.debug(getIntervalForView(date));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * 6天13小时3分钟53秒259毫秒
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>和 {@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 的区别:
     * </h3>
     * <blockquote>
     * <ol>
     * <li>{@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 显示的是英文,该方法显示的是中文</li>
     * <li>{@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 最小单位是秒,该方法最小单位是毫秒</li>
     * </ol>
     * </blockquote>
     * 
     * @param beginDate
     *            开始日期
     * @return 如果 <code>beginDate</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #formatDuration(Date, Date)
     * @see org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean)
     * @see <a href="http://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss">how-to-format-a-duration-
     *      in-java-e-g-format-hmmss</a>
     * @since 1.8.4 change name from getIntervalForView
     */
    public static String formatDuration(Date beginDate){
        return formatDuration(beginDate, now());
    }

    /**
     * 将<code>beginDate</code>和 <code>endDate</code> 两日期之间的<span style="color:red">绝对值</span>间隔,格式化成直观的表示方式.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>常用于日志输出一段代码执行时长</li>
     * <li>计算的是开始时间 <code>beginDate</code> 到结束时间 <code>endDate</code> 绝对值间隔时间,也就是说不care 时间先后顺序</li>
     * <li>间隔时间转成 <b>天,小时,分钟,秒,毫秒</b> 中文文字,月和较大的格式不使用</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Date beginDate = now();
     * 
     * <span style="color:green">// do some logic</span>
     * <span style="color:green">// balabala logic</span>
     * 
     * LOGGER.info("use time: [{}]", DateExtensionUtil.getIntervalForView(beginDate, now()));
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
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>和 {@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 的区别:
     * </h3>
     * <blockquote>
     * <ol>
     * <li>{@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 显示的是英文,该方法显示的是中文</li>
     * <li>{@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 最小单位是秒,该方法最小单位是毫秒</li>
     * </ol>
     * </blockquote>
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 如果 <code>beginDate</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>endDate</code> 是null,抛出 {@link NullPointerException}
     * @see #formatDuration(long)
     * @see #getIntervalTime(Date, Date)
     * @see org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean)
     * @see <a href="http://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss">how-to-format-a-duration-
     *      in-java-e-g-format-hmmss</a>
     * @since 1.8.4 change name from getIntervalForView
     */
    public static String formatDuration(Date beginDate,Date endDate){
        return formatDuration(getIntervalTime(beginDate, endDate));
    }

    /**
     * 将间隔毫秒数 <code>spaceMilliseconds</code>,格式化成直观的表示方式.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>常用于日志输出一段代码执行时长</li>
     * <li>间隔时间转成 <b>天,小时,分钟,秒,毫秒</b> 中文文字,月和较大的格式不使用</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalForView(13516)    = 13秒516毫秒
     * DateExtensionUtil.getIntervalForView(0)        = 0
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>和 {@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 的区别:
     * </h3>
     * <blockquote>
     * <ol>
     * <li>{@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 显示的是英文,该方法显示的是中文</li>
     * <li>{@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean) DurationFormatUtils
     * formatDurationWords} 最小单位是秒,该方法最小单位是毫秒</li>
     * </ol>
     * </blockquote>
     * 
     * @param spaceMilliseconds
     *            总共相差的毫秒数
     * @return 如果 spaceMilliseconds 是0 直接返回0<br>
     *         如果 {@code spaceMilliseconds < 0},抛出 {@link IllegalArgumentException}
     * @see #getIntervalDay(long)
     * @see #getIntervalHour(long)
     * @see #getIntervalMinute(long)
     * @see #getIntervalSecond(long)
     * @see org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean)
     * @see <a href="http://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss">how-to-format-a-duration-
     *      in-java-e-g-format-hmmss</a>
     * @since 1.8.4 change name from getIntervalForView
     */
    public static String formatDuration(long spaceMilliseconds){
        Validate.isTrue(spaceMilliseconds >= 0, "spaceMilliseconds can't <0");

        if (0 == spaceMilliseconds){
            return "0";
        }
        //---------------------------------------------------------------

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

        //---------------------------------------------------------------
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

    //---------------------------------------------------------------
    // [start]interval时间间隔

    /**
     * 获得相差的星期数(<span style="color:red">绝对值</span>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <ul>
     * <li>值=两个时间相差毫秒的绝对值/{@link TimeInterval#MILLISECOND_PER_WEEK}</li>
     * <li>当两者时间差不足1星期,返回0</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalWeek(
     *      toDate("2014-01-01 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2014-02-01 00:00:00",COMMON_DATE_AND_TIME)) = 4
     * 
     * DateExtensionUtil.getIntervalWeek(
     *      toDate("2016-08-01",COMMON_DATE),
     *      toDate("2016-08-07",COMMON_DATE)) = 0
     * 
     * DateExtensionUtil.getIntervalWeek(
     *      toDate("2016-08-01",COMMON_DATE),
     *      toDate("2016-08-08",COMMON_DATE)) = 1
     *      
     * DateExtensionUtil.getIntervalWeek(
     *      toDate("2016-08-21",COMMON_DATE),
     *      toDate("2016-08-21",COMMON_DATE)) = 0
     * </pre>
     * 
     * </blockquote>
     * 
     * @param date1
     *            第一个时间
     * @param date2
     *            第二个时间
     * @return 如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalWeek(long)
     * @since 1.6.0
     */
    public static int getIntervalWeek(Date date1,Date date2){
        return getIntervalWeek(getIntervalTime(date1, date2));
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
    private static int getIntervalWeek(long spaceTime){
        return (int) (spaceTime / (MILLISECOND_PER_WEEK));
    }

    //---------------------------------------------------------------

    /**
     * 计算两个时间相差的的天数(<span style="color:red">绝对值</span>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <ul>
     * <li>值=两个时间相差毫秒的绝对值/{@link TimeInterval#MILLISECOND_PER_DAY}</li>
     * <li>当两者时间差不足1天,返回0</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalDay(
     *      toDate("2008-08-24",COMMON_DATE),
     *      toDate("2008-08-27",COMMON_DATE)) = 3
     * 
     * DateExtensionUtil.getIntervalDay(
     *      toDate("2016-08-21 12:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2016-08-22 11:00:00",COMMON_DATE_AND_TIME)) = 0
     * 
     * DateExtensionUtil.getIntervalDay(
     *      toDate("2016-08-21",COMMON_DATE),
     *      toDate("2016-08-22",COMMON_DATE)) = 1
     *      
     * DateExtensionUtil.getIntervalDay(
     *      toDate("2016-02-28",COMMON_DATE),
     *      toDate("2016-03-02",COMMON_DATE)) = 3
     * 
     * DateExtensionUtil.getIntervalDay(
     *      toDate("2016-08-31",COMMON_DATE),
     *      toDate("2016-09-02",COMMON_DATE)) = 2
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @param date1
     *            第一个时间
     * @param date2
     *            第二个时间
     * @return 如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalDay(long)
     * @since 1.6.0
     */
    public static int getIntervalDay(Date date1,Date date2){
        return getIntervalDay(getIntervalTime(date1, date2));
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
    static int getIntervalDay(long spaceMilliseconds){
        return (int) (spaceMilliseconds / (MILLISECOND_PER_DAY));
    }

    //---------------------------------------------------------------

    /**
     * 两个时间相差的的小时数(<span style="color:red">绝对值</span>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <ul>
     * <li>值=两个时间相差毫秒的绝对值/{@link TimeInterval#MILLISECOND_PER_HOUR}</li>
     * <li>当两者时间差不足1小时,返回0</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalHour(
     *      toDate("2014-01-01 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2014-01-01 01:00:00",COMMON_DATE_AND_TIME)) = 1
     * 
     * DateExtensionUtil.getIntervalHour(
     *      toDate("2014-01-01 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2014-01-01 00:59:00",COMMON_DATE_AND_TIME)) = 0
     * 
     * DateExtensionUtil.getIntervalHour(
     *      toDate("2014-01-01 00:59:00",COMMON_DATE_AND_TIME),
     *      toDate("2014-01-01 00:00:00",COMMON_DATE_AND_TIME)) = 0
     *      
     * DateExtensionUtil.getIntervalHour(
     *      toDate("2016-08-21 23:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2016-08-22 01:00:00",COMMON_DATE_AND_TIME)) = 2
     * </pre>
     * 
     * </blockquote>
     * 
     * @param date1
     *            第一个时间
     * @param date2
     *            第二个时间
     * @return 如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalHour(long)
     * @since 1.6.0
     */
    public static int getIntervalHour(Date date1,Date date2){
        return getIntervalHour(getIntervalTime(date1, date2));
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
    static int getIntervalHour(long spaceMilliseconds){
        return (int) (spaceMilliseconds / (MILLISECOND_PER_HOUR));
    }

    //---------------------------------------------------------------

    /**
     * 两个时间相差的分钟(<span style="color:red">绝对值</span>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <ul>
     * <li>值=两个时间相差毫秒的绝对值/{@link TimeInterval#MILLISECOND_PER_MINUTE}</li>
     * <li>当两者时间差不足1分钟,返回0</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalMinute(
     *      toDate("2008-08-24 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2008-08-24 01:00:00",COMMON_DATE_AND_TIME)) = 60
     * 
     * DateExtensionUtil.getIntervalMinute(
     *      toDate("2008-08-24 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2008-08-24 00:00:00",COMMON_DATE_AND_TIME)) = 0
     * 
     * DateExtensionUtil.getIntervalMinute(
     *      toDate("2008-08-24 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2008-08-24 00:00:50",COMMON_DATE_AND_TIME)) = 0
     * 
     * DateExtensionUtil.getIntervalMinute(
     *      toDate("2008-08-24 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2008-08-23 00:00:00",COMMON_DATE_AND_TIME)) = SECONDS_PER_DAY / 60
     * </pre>
     * 
     * </blockquote>
     * 
     * @param date1
     *            第一个时间
     * @param date2
     *            第二个时间
     * @return 如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalMinute(long)
     * @since 1.8.6
     */
    public static int getIntervalMinute(Date date1,Date date2){
        return getIntervalMinute(getIntervalTime(date1, date2));
    }

    /**
     * 两个时间相差的分钟.
     * 
     * @param spaceMilliseconds
     *            间隔毫秒
     * @return 相差的分钟
     * @see TimeInterval#MILLISECOND_PER_MINUTE
     * @since 1.6.0
     */
    static int getIntervalMinute(long spaceMilliseconds){
        return (int) (spaceMilliseconds / (MILLISECOND_PER_MINUTE));
    }

    //---------------------------------------------------------------

    /**
     * 两个时间相差的秒数(<span style="color:red">绝对值</span>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <ul>
     * <li>值=两个时间相差毫秒的绝对值/{@link TimeInterval#MILLISECOND_PER_SECONDS}</li>
     * <li>不足1秒返回0</li>
     * </ul>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalSecond(
     *      toDate("2016-08-22 00:00:00",COMMON_DATE_AND_TIME),
     *      toDate("2016-08-22 00:00:08",COMMON_DATE_AND_TIME)) = 8
     * 
     * DateExtensionUtil.getIntervalSecond(
     *      toDate("2016-08-21 23:59:20",COMMON_DATE_AND_TIME),
     *      toDate("2016-08-22 00:00:20",COMMON_DATE_AND_TIME)) = 60
     * </pre>
     * 
     * </blockquote>
     * 
     * @param date1
     *            第一个时间
     * @param date2
     *            第二个时间
     * @return 如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalTime(Date, Date)
     * @see #getIntervalSecond(long)
     * @since 1.6.0
     */
    public static int getIntervalSecond(Date date1,Date date2){
        return getIntervalSecond(getIntervalTime(date1, date2));
    }

    /**
     * 两个时间相差的秒数.
     * 
     * <p>
     * 不足1秒返回0
     * </p>
     * 
     * @param spaceMilliseconds
     *            间隔毫秒
     * @return 相差的秒数
     * @since 1.6.0
     */
    static int getIntervalSecond(long spaceMilliseconds){
        return (int) (spaceMilliseconds / MILLISECOND_PER_SECONDS);
    }

    //---------------------------------------------------------------

    /**
     * 两个时间相差的<b>毫秒数</b> (<span style="color:red">绝对值</span>).
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getIntervalTime(
     *      toDate("2016-07-16 15:21:00",COMMON_DATE_AND_TIME),
     *      toDate("2016-07-16 15:21:01",COMMON_DATE_AND_TIME)) = 1000
     * 
     * DateExtensionUtil.getIntervalTime(
     *      toDate("2016-07-16 15:21:00",COMMON_DATE_AND_TIME),
     *      toDate("2016-07-16 15:22:00",COMMON_DATE_AND_TIME)) = 60000
     * </pre>
     * 
     * </blockquote>
     * 
     * @param date1
     *            第一个时间
     * @param date2
     *            第二个时间
     * @return 如果 <code>date1</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>date2</code> 是null,抛出 {@link NullPointerException}
     * @see DateUtil#getTime(Date)
     * @see Math#abs(long)
     * @since 1.6.0
     */
    public static long getIntervalTime(Date date1,Date date2){
        Validate.notNull(date1, "date1 can't be null!");
        Validate.notNull(date2, "date2 can't be null!");
        return Math.abs(getTime(date2) - getTime(date1));
    }

    // [end]
}