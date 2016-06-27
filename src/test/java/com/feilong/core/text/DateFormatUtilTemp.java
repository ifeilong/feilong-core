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
package com.feilong.core.text;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

/**
 * {@link DateFormat}是日期/时间格式化子类的抽象类.
 * 
 * <h3>关于{@link SimpleDateFormat}非线程安全:</h3>
 * 
 * <blockquote>
 * see <a href="http://newslxw.iteye.com/blog/1114851">SimpleDateFormat在多线程下不安全</a>
 * <br>
 * 也可以查看 {@link SimpleDateFormat} Synchronization部分注释内容
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.text.Format
 * @see java.text.DateFormat
 * @see java.text.SimpleDateFormat
 * @see org.apache.commons.beanutils.converters.DateConverter
 * @see org.apache.commons.beanutils.locale.converters.DateLocaleConverter
 * @see <a href="http://newslxw.iteye.com/blog/1114851">SimpleDateFormat在多线程下不安全</a>
 * @since 1.0.1
 * @deprecated parse 的功能可以直接使用{@link org.apache.commons.lang3.time.DateUtils#parseDate(String, String...)}
 */
@Deprecated
public final class DateFormatUtilTemp{

    /** Don't let anyone instantiate this class. */
    private DateFormatUtilTemp(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * parse字符串类型转成日期类型.
     * 
     * <p style="color:red">
     * 不建议直接调用此方法,建议使用 {@link com.feilong.core.date.DateUtil#toDate(String, String)}替代
     * </p>
     * 
     * <p>
     * 调用的是 {@link #parse(String, String, Locale)},locale使用 {@link Locale#getDefault()}
     * </p>
     * 
     * @param dateString
     *            the date string
     * @param pattern
     *            建议使用 {@link com.feilong.core.DatePattern} 内置的时间格式
     * @return 如果 <code>dateString</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>dateString</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>pattern</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>pattern</code> 是 blank,抛出 {@link IllegalArgumentException}<br>
     *         否则使用 {@link Locale#getDefault()},调用 {@link #parse(String, String, Locale)}
     * @see #parse(String, String, Locale)
     */
    public static Date parse(String dateString,String pattern){
        return parse(dateString, pattern, Locale.getDefault());
    }

    /**
     * 时间字符串 <code>dateString</code> 转成日期类型.
     * 
     * @param dateString
     *            the date string
     * @param pattern
     *            建议使用 {@link com.feilong.core.DatePattern} 内置的时间格式
     * @param locale
     *            语言,如果是 null,那么使用 系统默认的{@link Locale#getDefault()}
     * @return 如果 <code>dateString</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>dateString</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>pattern</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>pattern</code> 是 blank,抛出 {@link IllegalArgumentException}<br>
     *         否则调用 {@link java.text.SimpleDateFormat#parse(String, ParsePosition)}
     * @see SimpleDateFormat#parse(String)
     * @see SimpleDateFormat#parse(String, ParsePosition)
     */
    public static Date parse(String dateString,String pattern,Locale locale){
        Validate.notBlank(dateString, "dateString can't be null/empty!");
        Validate.notBlank(pattern, "pattern can't be null/empty!");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, ObjectUtils.defaultIfNull(locale, Locale.getDefault()));
        //虽然可以直接调用 java.text.DateFormat#parse(String) 但是他会抛出 ParseException 是checked Exception
        return simpleDateFormat.parse(dateString, new ParsePosition(0));//如果发生错误，则返回 null
    }
}
