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

import java.text.Format;
import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;

/**
 * {@link MessageFormat}工具类,常用于国际化 .
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see MessageFormat
 * @see Format
 * @since 1.0.2
 */
public final class MessageFormatUtil{

    /** Don't let anyone instantiate this class. */
    private MessageFormatUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 格式化.
     * 
     * <h3>用法:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * MessageFormatUtil.format("name=张三{0}a{1}", "jin", "xin")                                             =   "name=张三jinaxin"
     * MessageFormatUtil.format("name=张三{0,number}a{1}", 5, "xin")                                          =   "name=张三5axin"
     * MessageFormatUtil.format("name=张三{0,date}a{1}", DateUtil.toDate("2000", DatePattern.yyyy), "xin")    =   "name=张三2000-1-1axin"
     * </pre>
     * 
     * </blockquote>
     * 
     * @param pattern
     *            占位符有三种方式书写方式:
     *            <ul>
     *            <li>{argumentIndex}: 0-9 之间的数字,表示要格式化对象数据在参数数组中的索引号</li>
     *            <li>{argumentIndex,formatType}: 参数的格式化类型</li>
     *            <li>{argumentIndex,formatType,FormatStyle}: 格式化的样式,它的值必须是与格式化类型相匹配的合法模式、或表示合法模式的字符串.</li>
     *            </ul>
     * @param arguments
     *            动态参数
     * @return 如果 <code>pattern</code> 是null,抛出 {@link NullPointerException}<br>
     */
    public static String format(String pattern,Object...arguments){
        Validate.notNull(pattern, "pattern can't be null!");
        return MessageFormat.format(pattern, arguments);
    }
}
