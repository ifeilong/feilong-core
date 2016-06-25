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

import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.RegexPattern;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * 正则表达式工具类.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see RegexPattern
 * @see java.util.regex.Pattern
 * @see java.util.regex.Matcher
 * @see java.lang.String#matches(String)
 * @since 1.0.0
 * @since jdk1.4
 */
public final class RegexUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexUtil.class);

    /** Don't let anyone instantiate this class. */
    private RegexUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 编译给定正则表达式 <code>regexPattern</code> 并尝试将给定输入 <code>input</code> 与其匹配.
     * 
     * <p>
     * {@link Pattern#matches(String, CharSequence)} 等价于{@link #getMatcher(String, CharSequence)}.matches();
     * </p>
     *
     * @param regexPattern
     *            正则表达式字符串,pls use {@link RegexPattern}
     * @param input
     *            The character sequence to be matched,support {@link String},{@link StringBuffer},{@link StringBuilder}... and so on
     * @return 如果input 符合 regex的正则表达式格式,返回true, 否则返回 false;<br>
     *         如果 <code>regexPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>input</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #getMatcher(String, CharSequence)
     * @see Matcher#matches()
     * @see Pattern#matches(String, CharSequence)
     * @since 1.0.7
     */
    public static boolean matches(String regexPattern,CharSequence input){
        return getMatcher(regexPattern, input).matches();
    }

    /**
     * 返回在以前匹配操作期间由给定组捕获的输入子序列.
     * 
     * <p>
     * 对于匹配器 m、输入序列 s 和组索引 g,表达式 m.group(g) 和 s.substring(m.start(g), m.end(g))是等效的.<br>
     * 捕获组是从 1开始从左到右的索引.组0表示整个模式,因此表达式 m.group(0)等效于 m.group().
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * String regexPattern = "(.*?)@(.*?)";
     * String email = "venusdrogon@163.com";
     * RegexUtil.group(regexPattern, email);
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     *    0 venusdrogon@163.com
     *    1 venusdrogon
     *    2 163.com
     * </pre>
     * 
     * </blockquote>
     * 
     * @param regexPattern
     *            正则表达式字符串,pls use {@link RegexPattern}
     * @param input
     *            The character sequence to be matched,support {@link String},{@link StringBuffer},{@link StringBuilder}... and so on
     * @return 如果 <code>regexPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>input</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 匹配不了,返回 {@link java.util.Collections#emptyMap()}
     * @see #getMatcher(String, CharSequence)
     * @see Matcher#group(int)
     * @since 1.0.7
     */
    public static Map<Integer, String> group(String regexPattern,CharSequence input){
        Matcher matcher = getMatcher(regexPattern, input);
        if (!matcher.matches()){
            LOGGER.debug("[not matches] ,\n\tregexPattern:[{}] \n\tinput:[{}]", regexPattern, input);
            return Collections.emptyMap();
        }
        int groupCount = matcher.groupCount();

        Map<Integer, String> map = MapUtil.newLinkedHashMap(groupCount + 1);
        LOGGER.debug("\n\tregexPattern:[{}],\n\tinput:[{}]", regexPattern, input);

        map.put(0, matcher.group());// 捕获组是从 1 开始从左到右的索引.组0表示整个模式,因此表达式 m.group(0) 等效于 m.group().

        //匹配的索引
        LOGGER.debug("matcher.start({}):[{}],matcher.end({}):[{}]", 0, matcher.start(0), 0, matcher.end(0));

        for (int i = 1; i <= groupCount; ++i){
            //匹配的索引
            LOGGER.debug("matcher.start({}):[{}],matcher.end({}):[{}]", i, matcher.start(i), i, matcher.end(i));
            map.put(i, matcher.group(i));//groupValue
        }

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("groupMap:{}", JsonUtil.format(map));
        }
        return map;
    }

    /**
     * 返回在以前匹配操作期间由给定组捕获的输入子序列.
     * 
     * <p>
     * 对于匹配器 m、输入序列 s 和组索引 g,表达式 m.group(g) 和 s.substring(m.start(g), m.end(g))是等效的.<br>
     * 捕获组是从 1开始从左到右的索引.组0表示整个模式,因此表达式 m.group(0)等效于 m.group().
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * String regexPattern = "(.*?)@(.*?)";
     * String email = "venusdrogon@163.com";
     * LOGGER.info(RegexUtil.group(regexPattern, email, 1) + "");//venusdrogon
     * LOGGER.info(RegexUtil.group(regexPattern, email, 2) + "");//163.com
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param regexPattern
     *            正则表达式字符串,pls use {@link RegexPattern}
     * @param input
     *            The character sequence to be matched,support {@link String},{@link StringBuffer},{@link StringBuilder}... and so on
     * @param group
     *            the group
     * @return 如果 <code>regexPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>input</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #getMatcher(String, CharSequence)
     * @see Matcher#group(int)
     * @since 1.0.7
     */
    public static String group(String regexPattern,CharSequence input,int group){
        Map<Integer, String> map = group(regexPattern, input);
        return map.get(group);
    }

    //********************************************************************************************
    /**
     * Gets the matcher.
     *
     * @param regexPattern
     *            正则表达式字符串,pls use {@link RegexPattern}
     * @param input
     *            The character sequence to be matched,support {@link String},{@link StringBuffer},{@link StringBuilder}... and so on
     * @return 如果 <code>regexPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>input</code> 是null,抛出 {@link NullPointerException}<br>
     * @see Pattern#compile(String)
     * @since 1.0.7
     */
    private static Matcher getMatcher(String regexPattern,CharSequence input){
        return getMatcher(regexPattern, input, 0);
    }

    /**
     * Gets the matcher.
     *
     * @param regexPattern
     *            正则表达式字符串,pls use {@link RegexPattern}
     * @param input
     *            The character sequence to be matched,support {@link String},{@link StringBuffer},{@link StringBuilder}... and so on
     * @param flags
     *            如果需要多个组合,可以使用罗辑或
     *            <blockquote><code>Pattern.compile(regex, CASE_INSENSITIVE | DOTALL);</code></blockquote>
     *            <ul>
     *            <li>{@link Pattern#CASE_INSENSITIVE} 匹配字符时与大小写无关,该标志默认只考虑US ASCII字符.</li>
     *            <li>{@link Pattern#MULTILINE} ^和$匹配一行的开始和结尾,而不是整个输入</li>
     *            <li>{@link Pattern#UNICODE_CASE} 当与CASE_INSENSITIVE结合时,使用Unicode字母匹配</li>
     *            <li>{@link Pattern#CANON_EQ} 考虑Unicode字符的规范等价</li>
     *            <li>{@link Pattern#DOTALL} 当使用此标志时,.符号匹配包括行终止符在内的所有字符</li>
     *            <li>{@link Pattern#UNIX_LINES} 当在多行模式下匹配^和$时,只将'\n'看作行终止符</li>
     *            <li>{@link Pattern#LITERAL} 启用模式的字面值解析.</li>
     *            <li>{@link Pattern#COMMENTS} 模式中允许空白和注释. <br>
     *            此模式将忽略空白和在结束行之前以 # 开头的嵌入式注释. <br>
     *            通过嵌入式标志表达式 (?x) 也可以启用注释模式. <br>
     *            </li>
     *            </ul>
     * @return 如果 <code>regexPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>input</code> 是null,抛出 {@link NullPointerException}<br>
     * @see Pattern#compile(String, int)
     * @since 1.5.3
     */
    private static Matcher getMatcher(String regexPattern,CharSequence input,int flags){
        Validate.notNull(regexPattern, "regexPattern can't be null!");
        Validate.notNull(input, "input can't be null!");
        Pattern pattern = Pattern.compile(regexPattern, flags);
        return pattern.matcher(input);
    }
}