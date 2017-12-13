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

/**
 * 常用的时间间隔.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.time.DateUtils
 * @since 1.0.0
 */
public final class TimeInterval{

    //由于 annotation 的属性,只能声明成  primitive 类型
    //否则提示 :Invalid type int for the annotation attribute xxx;only "primitive type, String, Class, annotation, enumeration" are permitted or 1-dimensional arrays thereof

    //------------------------------以秒为单位------------------------------
    /**
     * 1秒 1s.
     * 
     * @since 1.2.2
     */
    public static final int  SECONDS_PER_SECOND      = 1;

    /** 1分钟 60s. */
    public static final int  SECONDS_PER_MINUTE      = 60 * SECONDS_PER_SECOND;

    /** 1小时 60 * 60=3600. */
    public static final int  SECONDS_PER_HOUR        = SECONDS_PER_MINUTE * 60;

    /** 1天 60 * 60 * 24=86400. */
    public static final int  SECONDS_PER_DAY         = SECONDS_PER_HOUR * 24;

    /** 1个星期 60 * 60 * 24 * 7= 604 800. */
    public static final int  SECONDS_PER_WEEK        = SECONDS_PER_DAY * 7;

    /**
     * 1个月 60 * 60 * 24 * 30= 2592000.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>30天一个月 ,<b>估值</b>,没有精确一个月28/29天 还是30 31天.</li>
     * </ol>
     * </blockquote>
     */
    public static final int  SECONDS_PER_MONTH       = SECONDS_PER_DAY * 30;

    /**
     * 1年 60 * 60 * 24 * 365=31536000.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>以365天算.</li>
     * <li>
     * {@link Integer#MAX_VALUE}:2147483647, {@link Integer#MIN_VALUE}:-2147483648<br>
     * 1年数据为 {@link #SECONDS_PER_YEAR} 31536000,所以 {@link Integer#MAX_VALUE} 为 68.096259734906 年,注意使用的时候,超过最大值使用long来计算
     * </li>
     * </ol>
     * </blockquote>
     */
    public static final int  SECONDS_PER_YEAR        = SECONDS_PER_DAY * 365;

    //------------------------------以毫秒为单位------------------------------

    /**
     * 1秒 1000ms(毫秒).
     *
     * @see #SECONDS_PER_SECOND
     * @see org.apache.commons.lang3.time.DateUtils#MILLIS_PER_SECOND
     * @since 1.2.2
     */
    public static final int  MILLISECOND_PER_SECONDS = 1000;

    /**
     * 1分钟 60 000ms(毫秒).
     *
     * @see #SECONDS_PER_MINUTE
     * @see org.apache.commons.lang3.time.DateUtils#MILLIS_PER_MINUTE
     * @since 1.2.1
     */
    public static final int  MILLISECOND_PER_MINUTE  = SECONDS_PER_MINUTE * 1000;

    /**
     * 1小时 3600 000ms(毫秒).
     *
     * @see #SECONDS_PER_HOUR
     * @see org.apache.commons.lang3.time.DateUtils#MILLIS_PER_HOUR
     * @since 1.2.1
     */
    public static final int  MILLISECOND_PER_HOUR    = SECONDS_PER_HOUR * 1000;

    /**
     * 1天 86400 000ms(毫秒).
     *
     * @see #SECONDS_PER_DAY
     * @see org.apache.commons.lang3.time.DateUtils#MILLIS_PER_DAY
     * @since 1.2.1
     */
    public static final int  MILLISECOND_PER_DAY     = SECONDS_PER_DAY * 1000;

    /**
     * 1个星期 604 800 000ms(毫秒).
     *
     * @see #SECONDS_PER_WEEK
     * @since 1.2.1
     */
    public static final int  MILLISECOND_PER_WEEK    = SECONDS_PER_WEEK * 1000;

    /**
     * 1个月,2592000 000ms(毫秒),<span style="color:red">大于 {@link Integer#MAX_VALUE}:2147483647</span>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>30天一个月 ,<b>估值</b>,没有精确一个月28/29天 还是30 31天.</li>
     * </ol>
     * </blockquote>
     * 
     * @see #SECONDS_PER_MONTH
     * @since 1.2.1
     */
    public static final long MILLISECOND_PER_MONTH   = 1000L * SECONDS_PER_MONTH;

    /**
     * 1年 31536000 000ms(毫秒).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>以365天算.</li>
     * </ol>
     * </blockquote>
     *
     * @see #SECONDS_PER_YEAR
     * @since 1.2.1
     */
    public static final long MILLISECOND_PER_YEAR    = 1000L * SECONDS_PER_YEAR;

    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private TimeInterval(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
