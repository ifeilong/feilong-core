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
package com.feilong.core;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常用的日期模式.
 * 
 * <h3>以下是常用的字段说明:</h3>
 * 
 * <blockquote>
 * 
 * <table border=1 cellspacing=0 cellpadding=4 summary="Chart shows pattern letters, date/time component, presentation, and examples.">
 * <tr style="background-color:#ccccff">
 * <th align="left">Letter</th>
 * <th align="left">Date or Time Component</th>
 * <th align="left">Presentation</th>
 * <th align="left">示例</th>
 * </tr>
 * 
 * <tr>
 * <td><code>G</code></td>
 * <td>Era designator</td>
 * <td><a href="#text">Text</a></td>
 * <td><code>AD,公元,公元前</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>y</code></td>
 * <td>Year</td>
 * <td><a href="#year">Year</a></td>
 * <td><code>1996</code>; <code>96</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>M</code></td>
 * <td>Month in year</td>
 * <td><a href="#month">Month</a></td>
 * <td><code>July</code>; <code>Jul</code>; <code>07</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>w</code></td>
 * <td>Week in year</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>27</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>W</code></td>
 * <td>Week in month</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>2</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>D</code></td>
 * <td>Day in year</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>189</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>d</code></td>
 * <td>Day in month</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>10</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>F</code></td>
 * <td>Day of week in month</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>2</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>E</code></td>
 * <td>Day in week</td>
 * <td><a href="#text">Text</a></td>
 * <td><code>Tuesday</code>; <code>Tue</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>EEEE</code></td>
 * <td>星期</td>
 * <td></td>
 * <td><code>星期二</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>a</code></td>
 * <td>Am/pm marker</td>
 * <td><a href="#text">Text</a></td>
 * <td><code>PM</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>H</code></td>
 * <td>Hour in day (0-23)</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>0</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>k</code></td>
 * <td>Hour in day (1-24)</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>24</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>K</code></td>
 * <td>Hour in am/pm (0-11)</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>0</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>h</code></td>
 * <td>Hour in am/pm (1-12)</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>12</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>m</code></td>
 * <td>Minute in hour</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>30</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>s</code></td>
 * <td>Second in minute</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>55</code></td>
 * <tr>
 * 
 * <td><code>S</code></td>
 * <td>Millisecond</td>
 * <td><a href="#number">Number</a></td>
 * <td><code>978</code></td>
 * </tr>
 * 
 * <tr style="background-color:#eeeeff">
 * <td><code>z</code></td>
 * <td>Time zone</td>
 * <td><a href="#timezone">General time zone</a></td>
 * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code></td>
 * </tr>
 * 
 * <tr>
 * <td><code>Z</code></td>
 * <td>Time zone</td>
 * <td><a href="#rfc822timezone">RFC 822 time zone</a></td>
 * <td><code>-0800</code></td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>关于yyyy 和 YYYY:</h3>
 * 
 * <blockquote>
 * <p>
 * 如果使用了YYYY的格式符来格式化日期,那么就有可能用错格式了. <br>
 * 那么日期为什么忽然变得不对了？原因是开发人员误用的格式符代表的是一种不同的日历系统.<br>
 * <br>
 * 
 * 现行的公历通常被称为格里高利历(Gregorian calendar),它以400年为一个周期,在这个周期中,一共有97个闰日,在这种历法的设计中,闰日尽可能均匀地分布在各个年份中,所以一年的长度有两种可能:365天或366天.
 * 
 * <br>
 * 而本文提到的被错误使用的历法格式(YYYY),是国际标准ISO 8601所指定的历法.<br>
 * <br>
 * 这种历法采用周来纪日,样子看起来是这样的 :2009-W53-7.对于格里高利历中的闰日,它也采用"闰周"来表示,所以一年的长度是364或371天.<br>
 * <br>
 * 并且它规定,公历一年中第一个周四所在的那个星期,作为一年的第一个星期.
 * 
 * <br>
 * 这导致了一些很有意思的结果,公历每年元旦前后的几天,年份会和ISO 8601纪年法差一年.<br>
 * <br>
 * 比如,2015年的第一个周四是1月1日,所以1月1日所在的那周,就变成了2015年的第一周.代表ISO 8601的格式符是YYYY,注意是大写的,而格里高利历的格式符是小写的yyyy,如果不小心把这两者搞混了,时间就瞬间推移了一年！维基百科上也有词条专门解释ISO
 * 8601.
 * </p>
 * 
 * <p>
 * <span style="color:red">注意:YYYY是 jdk1.7+支持的格式,jdk1.7-的版本,{@link SimpleDateFormat}会抛异常</span>
 * </p>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see SimpleDateFormat
 * @see org.apache.commons.lang3.time.DateFormatUtils
 * @see "org.springframework.format.annotation.DateTimeFormat"
 * @see "java.time.format.DateTimeFormatter"
 * @see <a
 *      href=
 *      "http://www.infoq.com/cn/news/2015/01/java-date-format-with-caution?utm_campaign=infoq_content&utm_source=infoq&utm_medium=feed&utm_term=global">
 *      慎用Java日期格式化</a>
 * @see <a href="https://en.wikipedia.org/wiki/Date_format_by_country">各国的时间格式</a>
 * @see <a href="https://en.wikipedia.org/wiki/Chinese_numerals#Usage">Dates in Chinese</a>
 * @since 1.0.2
 */
public final class DatePattern{

    //---------------------------------------------------------------

    /**
     * 只有日期 年月日<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">2012-01-22</span>
     * </p>
     * 
     * @see org.apache.commons.lang3.time.DateFormatUtils#ISO_8601_EXTENDED_DATE_FORMAT
     * @see "java.time.format.DateTimeFormatter#ISO_LOCAL_DATE"
     * @see <a href="https://www.iso.org/iso-8601-date-and-time-format.html">Date and time format - ISO 8601</a>
     */
    public static final String COMMON_DATE                                  = "yyyy-MM-dd";

    /**
     * 不带秒 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">2013-12-27 22:13</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME_WITHOUT_SECOND          = "yyyy-MM-dd HH:mm";

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">2013-12-27 22:13:55</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME                         = "yyyy-MM-dd HH:mm:ss";

    /**
     * 带毫秒的时间格式 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">2013-12-27 22:13:55.453</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME_WITH_MILLISECOND        = "yyyy-MM-dd HH:mm:ss.SSS";

    //---------------------------------------------------------------

    /**
     * (点号格式的) 只有日期 年月日<span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example:<span style="color:green">2018.01.02</span>
     * </p>
     * 
     * @since 1.10.7
     */
    public static final String DOTS_DATE                                    = "yyyy.MM.dd";

    /**
     * (点号格式的)不带秒 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">2018.01.02 22:13</span>
     * </p>
     * 
     * @since 1.10.7
     */
    public static final String DOTS_DATE_AND_TIME_WITHOUT_SECOND            = "yyyy.MM.dd HH:mm";

    /**
     * (点号格式的)<span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example:<span style="color:green">2018.01.02 22:13:55</span>
     * </p>
     * 
     * @since 1.10.7
     */
    public static final String DOTS_DATE_AND_TIME                           = "yyyy.MM.dd HH:mm:ss";

    /**
     * (点号格式的)带毫秒的时间格式 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">2018.01.02 22:13:55.453</span>
     * </p>
     * 
     * @since 1.10.7
     */
    public static final String DOTS_DATE_AND_TIME_WITH_MILLISECOND          = "yyyy.MM.dd HH:mm:ss.SSS";

    //---------------------------------------------------------------

    /**
     * 只有时间且不带秒 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example:<span style="color:green">21:57</span>
     * </p>
     */
    public static final String COMMON_TIME_WITHOUT_SECOND                   = "HH:mm";

    /**
     * 只有时间<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">21:57:36</span>
     * </p>
     * 
     * @see org.apache.commons.lang3.time.DateFormatUtils#ISO_8601_EXTENDED_TIME_FORMAT
     * @see "java.time.format.DateTimeFormatter#ISO_LOCAL_TIME"
     */
    public static final String COMMON_TIME                                  = "HH:mm:ss";

    /**
     * 不带年 不带秒 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">12-27 22:13</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND = "MM-dd HH:mm";

    //---------------------------------------------------------------
    /**
     * 时间戳, 一般用于拼接文件名称<span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">20131227215816</span>
     * </p>
     */
    public static final String TIMESTAMP                                    = "yyyyMMddHHmmss";

    /**
     * 带毫秒的时间戳,<span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">20131227215758437</span>
     * </p>
     */
    public static final String TIMESTAMP_WITH_MILLISECOND                   = "yyyyMMddHHmmssSSS";

    //---------------------------------------------------------------

    /**
     * 年月 带水平线,一般用于分类日志,将众多日志按月分类 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">2012-01</span>
     * </p>
     */
    public static final String YEAR_AND_MONTH                               = "yyyy-MM";

    /**
     * 月日 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">01-22</span>
     * </p>
     */
    public static final String MONTH_AND_DAY                                = "MM-dd";

    /**
     * 月日带星期 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">01-22(星期四)</span>
     * </p>
     */
    public static final String MONTH_AND_DAY_WITH_WEEK                      = "MM-dd(E)";

    //---------------------------------------------------------------

    /**
     * 印尼日期格式 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">31/03/2014 14:53:39</span>
     * </p>
     * 
     * <p>
     * 各国的时间格式参见 <a href="https://en.wikipedia.org/wiki/Date_format_by_country">Date format by country</a>
     * </p>
     * 
     * @see <a href="https://en.wikipedia.org/wiki/Date_format_by_country">各国的时间格式</a>
     * 
     * @since 1.11.0
     */
    public static final String INDONESIA_DATE                               = "dd/MM/yyyy";

    /**
     * 印尼 带时分秒的时间格式 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">31/03/2014 14:53:39</span>
     * </p>
     * 
     * <p>
     * 各国的时间格式参见 <a href="https://en.wikipedia.org/wiki/Date_format_by_country">Date format by country</a>
     * </p>
     * 
     * @see <a href="https://en.wikipedia.org/wiki/Date_format_by_country">各国的时间格式</a>
     * 
     * @since 1.11.0 change name from ddMMyyyyHHmmss to INDONESIA_DATE_AND_TIME
     */
    public static final String INDONESIA_DATE_AND_TIME                      = "dd/MM/yyyy HH:mm:ss";

    //---------------------------------------------------------------

    /**
     * MM月份 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">12</span>
     * </p>
     */
    public static final String MM                                           = "MM";

    /**
     * The ISO date formatter that formats or parses a date without an offset, such as '20111203' ,
     * <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">20131227</span>
     * </p>
     * 
     * @see "java.time.format.DateTimeFormatter#BASIC_ISO_DATE"
     * 
     * @since 1.11.0 change name from "yyyyMMdd" to BASIC_ISO_DATE
     */
    public static final String BASIC_ISO_DATE                               = "yyyyMMdd";

    /**
     * 二十四小时制小时 <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">21</span>
     * </p>
     */
    public static final String HH                                           = "HH";

    //---------------------------------------------------------------

    /**
     * 系统 {@link Date#toString()}使用的格式,并且{@link java.util.Locale#US} <span style="color:green"><code>{@value}</code></span>.
     * 
     * <p>
     * example: <span style="color:green">星期五 十二月 27 22:13:55 CST 2013</span>
     * </p>
     * 
     * @see Date#toString()
     * @see org.apache.commons.lang3.time.DateFormatUtils#SMTP_DATETIME_FORMAT
     */
    public static final String TO_STRING_STYLE                              = "EEE MMM dd HH:mm:ss zzz yyyy";

    //---------------------------------------------------------------

    /**
     * 中文日期格式,年月日<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">2015年07月17日</span>
     * </p>
     * 
     * @since 1.2.2
     * @since 1.11.0 rename from CHINESE_COMMON_DATE to CHINESE_DATE
     */
    public static final String CHINESE_DATE                                 = "yyyy年MM月dd日";

    /**
     * 中文日期+时间格式 <span style="color:green"><span style="color:green"><code>{@value}</code></span>.</span>.
     * <p>
     * example: <span style="color:green">2015年07月17日 15:33:00</span>
     * </p>
     * 
     * @since 1.2.2
     * @since 1.11.0 rename from CHINESE_COMMON_DATE_AND_TIME to CHINESE_DATE_AND_TIME
     */
    public static final String CHINESE_DATE_AND_TIME                        = "yyyy年MM月dd日 HH:mm:ss";

    //---------------------------------------------------------------
    /** Don't let anyone instantiate this class. */
    private DatePattern(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
