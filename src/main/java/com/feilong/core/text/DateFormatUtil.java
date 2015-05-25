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
package com.feilong.core.text;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.feilong.core.util.Validator;

/**
 * 
 * {@link DateFormat}是日期/时间格式化子类的抽象类.
 * <p>
 * 直接已知子类：{@link SimpleDateFormat}.
 * </p>
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-3-27 上午1:39:38
 * @see Format
 * @see DateFormat
 * @see SimpleDateFormat
 * @since 1.0.0
 */
public class DateFormatUtil{

    /** Don't let anyone instantiate this class. */
    private DateFormatUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // [start] format

    /**
     * format 日期类型 格式化成字符串类型,调用的是 {@link #format(Date, String, Locale)},locale使用 {@link Locale#getDefault()}.<br>
     * {@link SimpleDateFormat#SimpleDateFormat()} 默认使用的locale就是 {@link Locale#getDefault()}
     * 
     * @param date
     *            the date
     * @param pattern
     *            the pattern
     * @return the string
     * @see #format(Date, String, Locale)
     */
    public static String format(Date date,String pattern){
        return format(date, pattern, Locale.getDefault());
    }

    /**
     * format 日期类型 格式化成字符串类型.
     * 
     * @param date
     *            the date
     * @param pattern
     *            the pattern
     * @param locale
     *            the locale
     * @return the string
     * @see SimpleDateFormat#format(Date)
     */
    public static String format(Date date,String pattern,Locale locale){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        String format = simpleDateFormat.format(date);
        return format;
    }

    // [end]
    //*************************************************************************************************

    // [start]parse

    /**
     * parse 字符串类型转成日期类型,调用的是 {@link #parse(String, String, Locale)},locale使用 {@link Locale#getDefault()},<br>
     * {@link SimpleDateFormat#SimpleDateFormat()} 默认使用的locale就是 {@link Locale#getDefault()}
     * 
     * @param dateString
     *            the date string
     * @param pattern
     *            the pattern
     * @return the date
     * @throws NullPointerException
     *             isNullOrEmpty(dateString)
     * @see SimpleDateFormat
     * @see #parse(String, String, Locale)
     */
    public static Date parse(String dateString,String pattern) throws NullPointerException{
        return parse(dateString, pattern, Locale.getDefault());
    }

    /**
     * 字符串类型转成日期类型.
     * 
     * @param dateString
     *            the date string
     * @param pattern
     *            the pattern
     * @param locale
     *            the locale
     * @return the date
     * @throws NullPointerException
     *             isNullOrEmpty(dateString)
     * @see SimpleDateFormat
     * @see SimpleDateFormat#parse(String)
     * @see SimpleDateFormat#parse(String, ParsePosition)
     */
    public static Date parse(String dateString,String pattern,Locale locale) throws NullPointerException{
        if (Validator.isNullOrEmpty(dateString)){
            throw new NullPointerException("param dateString can not NullOrEmpty");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        ParsePosition parsePosition = new ParsePosition(0);
        Date date = simpleDateFormat.parse(dateString, parsePosition);
        return date;
    }

    // [end]
}
