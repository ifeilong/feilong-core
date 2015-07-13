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
package com.feilong.core.util;

import org.apache.commons.lang3.SystemUtils;

/**
 * {@link java.lang.StringBuilder}工具类,可以用来快速的拼接字符串.
 * 
 * @author feilong
 * @version 1.0 2012-7-11 下午5:05:56
 * @since 1.0.0
 * @deprecated 作用不大
 */
@Deprecated
public final class StringBuilderUtil{

    /** Don't let anyone instantiate this class. */
    private StringBuilderUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将不定参数,拼接成string
     * 
     * @param strings
     *            不定参数
     * @return 使用stringBuilder.append来拼接
     */
    public static final String append(Object...strings){
        if (Validator.isNotNullOrEmpty(strings)){
            StringBuilder stringBuilder = new StringBuilder();
            for (Object string : strings){
                stringBuilder.append(string);
            }
            return stringBuilder.toString();
        }
        return null;
    }

    /**
     * append 并且换行.
     * 
     * @param stringBuilder
     *            stringBuilder
     * @param text
     *            需要append 的内容
     */
    public static final void appendTextWithLn(StringBuilder stringBuilder,Object text){
        stringBuilder.append(text);
        stringBuilder.append(SystemUtils.LINE_SEPARATOR);
    }

    /**
     * <code>
     * stringBuilder.append(Constants.lineSeparator);
     * </code>
     * 
     * @param stringBuilder
     *            the string builder
     */
    public static final void appendLn(StringBuilder stringBuilder){
        stringBuilder.append(SystemUtils.LINE_SEPARATOR);
    }

    /**
     * append 并且换行,键值对形式输出.
     * 
     * @param stringBuilder
     *            stringBuilder
     * @param key
     *            key
     * @param value
     *            value
     */
    public static final void appendTextWithLn(StringBuilder stringBuilder,String key,Object value){
        stringBuilder.append(key);
        stringBuilder.append(":");
        stringBuilder.append(value);
        stringBuilder.append(SystemUtils.LINE_SEPARATOR);
    }

    /**
     * 文件 以醒目字符分隔,一般用于 文本输出.例如:
     * 
     * <pre>
     * *****************************************
     * </pre>
     * 
     * @param stringBuilder
     *            the string builder
     */
    public static final void appendTextWithSeparated(StringBuilder stringBuilder){
        appendTextWithSeparatedWithTitle(stringBuilder, null);
    }

    /**
     * 文件 以醒目字符分隔,一般用于 文本输出 带标题的. <br>
     * 例如:
     * 
     * <pre>
     * ***************呵呵,下面是销售日报-库存部分*******************
     * </pre>
     * 
     * @param stringBuilder
     *            the string builder
     * @param title
     *            the title
     */
    public static final void appendTextWithSeparatedWithTitle(StringBuilder stringBuilder,String title){
        stringBuilder.append(SystemUtils.LINE_SEPARATOR);
        stringBuilder.append("**************************");
        if (Validator.isNotNullOrEmpty(title)){
            stringBuilder.append(title);
        }
        stringBuilder.append("**************************");
        stringBuilder.append(SystemUtils.LINE_SEPARATOR);
    }

    /**
     * 文件 以醒目字符分隔,一般用于 文本输出.
     * 
     * @return stringBuilder
     */
    public static final StringBuilder appendTextWithSeparated(){
        StringBuilder stringBuilder = new StringBuilder();
        appendTextWithSeparated(stringBuilder);
        return stringBuilder;
    }
}