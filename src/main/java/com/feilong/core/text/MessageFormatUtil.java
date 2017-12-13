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

    //---------------------------------------------------------------

    /**
     * 调用 {@link java.text.MessageFormat#format(String, Object...)} 格式化.
     * 
     * <h3>用法:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * MessageFormatUtil.format("name=张三{0}a{1}", "jin", "xin")                                             =   "name=张三jinaxin"
     * MessageFormatUtil.format("name=张三{0,number}a{1}", 5, "xin")                                          =   "name=张三5axin"
     * MessageFormatUtil.format("name=张三{0,date,yyyy-MM-dd}a{1}", DateUtil.toDate("2000", DatePattern.yyyy), "xin")    =   "name=张三2000-01-01axin"
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>关于 参数pattern(模式参数):</h3>
     * 
     * <blockquote>
     * <p>
     * 格式： ArgumentIndex[,FormatType[,FormatStyle]]
     * </p>
     * 
     * <p>
     * 占位符有三种方式书写方式:
     * </p>
     * 
     * <ul>
     * <li>{argumentIndex}: 0-9之间的数字,表示要格式化对象数据在参数数组中的索引号</li>
     * <li>{argumentIndex,formatType}:参数的格式化类型</li>
     * <li>{argumentIndex,formatType,FormatStyle}: 格式化的样式,它的值必须是与格式化类型相匹配的合法模式、或表示合法模式的字符串.</li>
     * </ul>
     * 
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * 
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * 
     * <tr valign="top">
     * <td>ArgumentIndex</td>
     * <td>是从0开始的入参位置索引</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>FormatType</td>
     * <td>指定使用不同的Format子类对入参进行格式化处理。值范围如下：
     * 
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * 
     * <tr valign="top">
     * <td>number</td>
     * <td>调用{@link java.text.NumberFormat}进行格式化</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>date</td>
     * <td>调用{@link java.text.DateFormat}进行格式化</td>
     * </tr>
     * <tr valign="top">
     * <td>time</td>
     * <td>调用{@link java.text.DateFormat}进行格式化</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>choice</td>
     * <td>调用{@link java.text.ChoiceFormat}进行格式化</td>
     * </tr>
     * 
     * </table>
     * 
     * </td>
     * 
     * </tr>
     * <tr valign="top">
     * <td>FormatStyle</td>
     * <td>设置FormatType中使用的格式化样式。<br>
     * 值范围如下：
     * short,medium,long,full,integer,currency,percent,SubformPattern(子格式模式,形如#.##)</td>
     * </tr>
     * </table>
     * 
     * <p>
     * 注意： FormatType 和 FormatStyle 主要用于对日期时间、数字、百分比等进行格式化。
     * </p>
     * 
     * <pre>
     * 示例——将数字1.23格式为1.2：
     * 
     * double num = 1.23;
     * String str = MessageFormatUtil.format("{0,number,#.#}", num);
     * </pre>
     * 
     * </blockquote>
     * 
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>两个单引号才表示一个单引号,仅写一个单引号将被忽略。</li>
     * <li>单引号会使其后面的占位符均失效,导致直接输出占位符。
     * 
     * <pre>
     * MessageFormatUtil.format("{0}{1}", 1, 2); // 结果12
     * MessageFormatUtil.format("'{0}{1}", 1, 2); // 结果{0}{1}
     * MessageFormatUtil.format("'{0}'{1}", 1, 2); // 结果{0}
     * 因此可以用于输出左花括号(单写左花括号会报错,而单写右花括号将正常输出)
     * 
     * MessageFormatUtil.format("'{'{0}}", 2); // 结果{2
     * </pre>
     * 
     * </li>
     * </ol>
     * </blockquote>
     * 
     * @param pattern
     *            模式参数
     * @param arguments
     *            动态参数
     * @return 如果 <code>pattern</code> 是null,抛出 {@link NullPointerException}<br>
     */
    public static String format(String pattern,Object...arguments){
        Validate.notNull(pattern, "pattern can't be null!");
        return MessageFormat.format(pattern, arguments);
    }
}
