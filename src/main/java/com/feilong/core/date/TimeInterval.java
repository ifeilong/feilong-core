/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

/**
 * 常用时间间隔.
 * 
 * <h3>注意:</h3>
 * 
 * <blockquote>
 * <p>
 * {@link Integer#MAX_VALUE}:2147483647<br>
 * {@link Integer#MIN_VALUE}:-2147483648<br>
 * 一年数据为 {@link #SECONDS_PER_YEAR} 31536000,所以 {@link Integer#MAX_VALUE} 为 68.096259734906 年
 * </p>
 * </blockquote>
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-5-18 下午2:57:14
 * @version 1.0.5 2014-5-4 14:23 change to interface
 * @since 1.0.0
 */
public final class TimeInterval{

    /** 1分钟 60s. */
    public static final Integer SECONDS_PER_MINUTE     = 60;

    /** 1小时 60 * 60=3600. */
    public static final Integer SECONDS_PER_HOUR       = SECONDS_PER_MINUTE * 60;

    /** 1天 60 * 60 * 24=86400. */
    public static final Integer SECONDS_PER_DAY        = SECONDS_PER_HOUR * 24;

    /** 一个星期 60 * 60 * 24 * 7= 604 800. */
    public static final Integer SECONDS_PER_WEEK       = SECONDS_PER_DAY * 7;

    /**
     * 30天 一个月 60 * 60 * 24 * 30= 2592000.
     * <p>
     * 估值,没有精确一个月28/29天 还是30 31天.
     * </p>
     */
    public static final Integer SECONDS_PER_MONTH      = SECONDS_PER_DAY * 30;

    /**
     * 365天 1年 60 * 60 * 24 * 365=31536000.
     * <p>
     * Integer.MAX_VALUE:2147483647<br>
     * Integer.MIN_VALUE-2147483648<br>
     * 一年数据为 31536000,所以 Integer 最大为 68.096259734906 年
     * </p>
     */
    public static final Integer SECONDS_PER_YEAR       = SECONDS_PER_DAY * 365;

    //***********************************************************************************

    /**
     * 每分钟的毫秒数,1分钟 60 000ms.
     * 
     * @since 1.2.1
     * @see com.feilong.core.date.TimeInterval#SECONDS_PER_MINUTE
     */
    public static final Integer MILLISECOND_PER_MINUTE = SECONDS_PER_MINUTE * 1000;

    /**
     * 每小时的毫秒数,1小时 3600 000ms.
     * 
     * @since 1.2.1
     * @see com.feilong.core.date.TimeInterval#SECONDS_PER_HOUR
     */
    public static final Integer MILLISECOND_PER_HOUR   = SECONDS_PER_HOUR * 1000;

    /**
     * 每天的毫秒数,1天 86400 000ms.
     * 
     * @since 1.2.1
     * @see com.feilong.core.date.TimeInterval#SECONDS_PER_DAY
     */
    public static final Integer MILLISECOND_PER_DAY    = SECONDS_PER_DAY * 1000;

    /**
     * 每星期的毫秒数,一个星期 604 800 000ms.
     * 
     * @since 1.2.1
     * @see com.feilong.core.date.TimeInterval#SECONDS_PER_WEEK
     */
    public static final Integer MILLISECOND_PER_WEEK   = SECONDS_PER_WEEK * 1000;

    /**
     * 每月的毫秒数,2592000 000ms,<span style="color:red">大于 {@link Integer#MAX_VALUE}:2147483647</span>.
     * 
     * <p>
     * 30天一个月 ,估值,没有精确一个月28/29天 还是30 31天.
     * </p>
     * 
     * @since 1.2.1
     * @see com.feilong.core.date.TimeInterval#SECONDS_PER_MONTH
     */
    public static final Long    MILLISECOND_PER_MONTH  = 1000L * SECONDS_PER_MONTH;

    /**
     * 每年的毫秒数, 31536000 000ms,<span style="color:red">大于 {@link Integer#MAX_VALUE}:2147483647</span>
     * 
     * 
     * @since 1.2.1
     * @see com.feilong.core.date.TimeInterval#SECONDS_PER_YEAR
     */
    public static final Long    MILLISECOND_PER_YEAR   = 1000L * SECONDS_PER_YEAR;

    //****************************************************************************************

    /** Don't let anyone instantiate this class. */
    private TimeInterval(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
