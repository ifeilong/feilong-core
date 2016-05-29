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
package com.feilong.core.lang;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.CharsetType;
import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * {@link String}工具类,可以 查询,截取,format,转成16进制码.
 * 
 * <h3>分隔(split)</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link #split(String, String)}</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>分隔(tokenize)</h3> <blockquote>
 * <ul>
 * <li>{@link #tokenizeToStringArray(String, String)}</li>
 * <li>{@link #tokenizeToStringArray(String, String, boolean, boolean)}</li>
 * </ul>
 * 
 * <p>
 * 区别在于,split 使用的是 正则表达式 {@link Pattern#split(CharSequence)} 分隔(特别注意,一些特殊字符 $|()[{^?*+\\ 需要转义才能做分隔符),而 {@link StringTokenizer} 使用索引机制,在性能上
 * StringTokenizer更高<br>
 * 因此,在注重性能的场景,还是建议使用{@link StringTokenizer}
 * </p>
 * </blockquote>
 * 
 * <h3>{@link String#String(byte[] )} 和 {@link String#String(byte[], Charset)} 区别</h3>
 * 
 * <blockquote>
 * <p>
 * {@link String#String(byte[] )} 其实调用了{@link String#String(byte[], Charset)}; 先使用 {@link Charset#defaultCharset()},如果有异常 再用 ISO-8859-1,
 * 具体参见 {@link java.lang.StringCoding#decode(byte[], int, int)}
 * </p>
 * </blockquote>
 * 
 * <h3>{@link StringBuffer} && {@link StringBuilder} && {@link String} 对比</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link StringBuffer} 字符串变量(线程安全)</li>
 * <li>{@link StringBuilder} 字符串变量(非线程安全)</li>
 * <li>{@link String} 字符串常量</li>
 * <li>在大部分情况下 {@link StringBuffer} {@code >} {@link String}</li>
 * <li>在大部分情况下 {@link StringBuilder} {@code >} {@link StringBuffer}</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>String s1 = new String("xyz"); 到底创建几个对象?</h3>
 * 
 * <blockquote>
 * <p>
 * 要看虚拟机的实现.而且要联系上下文<br>
 * 1、假设:HotSpot1.6<br>
 * 之前没有创建过xyz 则创建2个,之前创建过"xyz"则只创建1个<br>
 * 2、假设:HotSpot1.7<br>
 * 之前不管有没有创建过xyz 都创建1个
 * </p>
 * </blockquote>
 * 
 * <h3>String s3 = s1 + s2; <br>
 * System.out.println(s3.intern() == s3); 到底想不相等</h3> <blockquote>
 * <p>
 * 要看虚拟机的实现<br>
 * 1、假设:hotspot1.6<br>
 * 则false不相等<br>
 * 2、假设:hotspot1.7<br>
 * 则在之前没有创建过"abcabc"时,true相等
 * </p>
 * </blockquote>
 * 
 * <h3>{@link StringUtil#replace(CharSequence, CharSequence, CharSequence)} and {@link #replaceAll(CharSequence, String, String)} and
 * 区别:</h3>
 * 
 * <blockquote>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link StringUtil#replace(CharSequence, CharSequence, CharSequence)}</td>
 * <td>将字符串中出现的target替换成replacement</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link #replaceAll(CharSequence, String, String)}</td>
 * <td>regex是一个正则表达式，将字符串中匹配的子字符串替换为replacement</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link String#replaceFirst(String, String)}</td>
 * <td>和{@link StringUtil#replace(CharSequence, CharSequence, CharSequence)}类似，只不过只替换第一个出现的地方。</td>
 * </tr>
 * </table>
 * 
 * <p>
 * 对比以下代码:
 * </p>
 * 
 * <pre class="code">
 * StringUtil.replaceAll("SH1265,SH5951", "([a-zA-Z]+[0-9]+)", "'$1'")  ='SH1265','SH5951'
 * StringUtil.replace("SH1265,SH5951", "([a-zA-Z]+[0-9]+)", "'$1'")     =SH1265,SH5951
 * "SH1265,SH5951".replaceFirst("([a-zA-Z]+[0-9]+)", "'$1'")            ='SH1265',SH5951
 * </pre>
 * 
 * </blockquote>
 *
 * @author feilong
 * @see java.util.StringTokenizer
 * @see "org.springframework.util.StringUtils#tokenizeToStringArray(String, String)"
 * @see "org.springframework.beans.factory.xml.BeanDefinitionParserDelegate#MULTI_VALUE_ATTRIBUTE_DELIMITERS"
 * @see org.apache.commons.lang3.StringUtils
 * @see "com.google.common.base.Strings"
 * @since 1.4.0
 */
public final class StringUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    /** Don't let anyone instantiate this class. */
    private StringUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the given charset.
     *
     * @param bytes
     *            The bytes to be decoded into characters, may be <code>null</code>
     * @param charsetType
     *            {@link CharsetType}
     * @return A new <code>String</code> decoded from the specified array of bytes using the given charset,
     *         or <code>null</code> if the input byte array was <code>null</code>.
     * @see String#String(byte[], String)
     * @see org.apache.commons.lang3.StringUtils#toString(byte[], String)
     * @see org.apache.commons.lang3.StringUtils#toEncodedString(byte[], Charset)
     * @see "org.apache.commons.codec.binary.StringUtils#newString(byte[], String)"
     * @since 1.3.0
     */
    public static String newString(byte[] bytes,String charsetType){
        return StringUtils.toEncodedString(bytes, Charset.forName(charsetType));
    }

    // [start] search

    /**
     * 查找子字符串 (<code>target</code>)在字符串( <code>source</code>)中出现的次数.
     * 
     * <pre class="code">
     *StringUtil.searchTimes("xin", "xin")      = 1
     *StringUtil.searchTimes("xiiiin", "ii")    = 2
     * </pre>
     *
     * @param source
     *            查找的源字符串
     * @param target
     *            目标子串
     * @return count of target string in source
     * @see org.apache.commons.lang3.StringUtils#countMatches(CharSequence, CharSequence)
     * @since 1.0.2
     */
    public static int searchTimes(final CharSequence source,final CharSequence target){
        return StringUtils.countMatches(source, target);
    }

    // [end]
    /**
     * 给一串字符串前后增加两个引号.
     * 
     * <pre class="code">
     * StringUtil.addDoubleQuotes("jinxin.feilong") = "jinxin.feilong"
     * </pre>
     * 
     * @param text
     *            任意的字符串
     * @return "\"" + text + "\""
     * @see "org.springframework.util.StringUtils#quote(String)"
     */
    public static String addDoubleQuotes(String text){
        return "\"" + text + "\"";
    }

    /**
     * 单词首字母大写.
     * 
     * <p>
     * StringUtil.firstCharToUpperCase("jinxin") = "Jinxin"
     * </p>
     * 
     * <pre class="code">
     * StringUtils.capitalize(null)             = null
     * StringUtils.capitalize("")     = ""
     * StringUtils.capitalize("cat")  = "Cat"
     * StringUtils.capitalize("cAt")  = "CAt"
     * </pre>
     * 
     * @param word
     *            单词
     * @return 单词首字母大写
     * @see org.apache.commons.lang3.StringUtils#swapCase(String)
     * @see org.apache.commons.lang3.StringUtils#capitalize(String)
     */
    public static String firstCharToUpperCase(String word){
        return StringUtils.capitalize(word);
    }

    /**
     * 单词首字母小写.
     * 
     * <p>
     * StringUtil.firstCharToUpperCase("Jinxin") = "jinxin"
     * </p>
     * 
     * <pre class="code">
     * StringUtils.capitalize(null)                 = null
     * StringUtils.capitalize("")         = ""
     * StringUtils.capitalize("Jinxin")   = "jinxin"
     * StringUtils.capitalize("CAt")      = "cAt"
     * </pre>
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>和 {@link "IntrospectorUtil#decapitalize(String)"} 的区别.</li>
     * <li>如果要使用一段文字,每个单词首字母小写,可以使用 {@link org.apache.commons.lang3.text.WordUtils#uncapitalize(String, char...)}</li>
     * </ol>
     * </blockquote>
     * 
     * @param word
     *            单词
     * @return 单词首字母小写
     * @see org.apache.commons.lang3.StringUtils#uncapitalize(String)
     */
    public static String firstCharToLowerCase(String word){
        return StringUtils.uncapitalize(word);
    }

    // [start]contains

    /**
     * 判断 <code>seq</code>内是否包含指定的<code>searchSeq</code>.
     * 
     * @param seq
     *            原始字符串 jinxin,the CharSequence to check, may be null
     * @param searchSeq
     *            被包含的字符串 in,the CharSequence to find, may be null
     * @return 包含返回true,如果text 为null 返回false
     * @see org.apache.commons.lang3.StringUtils#contains(CharSequence, CharSequence)
     */
    public static boolean contains(final CharSequence seq,final CharSequence searchSeq){
        return StringUtils.contains(seq, searchSeq);
    }

    /**
     * 忽略大小写,判断是否包含.
     * 
     * <pre class="code">
     * StringUtil.containsIgnoreCase(null, "")            = false
     * StringUtil.containsIgnoreCase(text, null)          = false
     * StringUtil.containsIgnoreCase(text, "")            = true
     * StringUtil.containsIgnoreCase(text, "feilong")     = true
     * StringUtil.containsIgnoreCase(text, "feilong1")    = false
     * StringUtil.containsIgnoreCase(text, "feiLong")     = true
     * </pre>
     * 
     * @param str
     *            the CharSequence to check, may be null
     * @param searchStr
     *            the CharSequence to find, may be null
     * @return true if the CharSequence contains the search CharSequence irrespective of
     *         case or false if not or <code>null</code> string input
     */
    public static boolean containsIgnoreCase(final CharSequence str,final CharSequence searchStr){
        return StringUtils.containsIgnoreCase(str, searchStr);
    }

    // [end] 

    // [start]replace

    // ********************************replace************************************************
    /**
     * 使用指定的字面值替换序列替换此字符串所有匹配字面值目标序列的子字符串.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>该替换从字符串的开头朝末尾执行,例如,用 "b" 替换字符串 "aaa" 中的 "aa" 将生成 "ba" 而不是 "ab".</li>
     * <li>虽然底层调用了{@link java.util.regex.Matcher#replaceAll(String) Matcher.replaceAll()},但是使用了
     * {@link java.util.regex.Matcher#quoteReplacement(String) Matcher.quoteReplacement()} 处理了特殊字符</li>
     * </ol>
     * </blockquote>
     * 
     * @param content
     *            内容
     * @param target
     *            要被替换的 char 值序列
     * @param replacement
     *            char 值的替换序列
     * @return 如果 null==content,return {@link StringUtils#EMPTY} <br>
     *         所有匹配字面值目标序列的子字符串
     * @see java.lang.String#replace(CharSequence, CharSequence)
     * @since jdk 1.5
     */
    public static String replace(CharSequence content,CharSequence target,CharSequence replacement){
        return null == content ? StringUtils.EMPTY
                        : content.toString().replace(target, null == replacement ? StringUtils.EMPTY : replacement);
    }

    /**
     * 使用给定的<code>replacement</code>替换此字符串所有匹配给定的正则表达式 <code>regex</code>的子字符串.
     * 
     * <p>
     * 注意,此方法底层调用的是 {@link java.util.regex.Matcher#replaceAll(String)},same as
     * <code>Pattern.compile(regex).matcher(str).replaceAll(repl)</code>
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * StringUtil.replaceAll("SH1265,SH5951,SH6766,SH7235,SH1265,SH5951,SH6766,SH7235", "([a-zA-Z]+[0-9]+)", "'$1'")
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * 'SH1265','SH5951','SH6766','SH7235','SH1265','SH5951','SH6766','SH7235'
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>请注意:</h3>
     * 
     * <blockquote>
     * <p>
     * 在替代字符串<code>replacement</code>中,使用 backslashes反斜杆(<tt>\</tt>)和 dollar signs美元符号 (<tt>$</tt>)与将其视为字面值替代字符串所得的结果可能不同.
     * <br>
     * 请参阅 {@link java.util.regex.Matcher#replaceAll Matcher.replaceAll};如有需要,可使用 {@link java.util.regex.Matcher#quoteReplacement
     * Matcher.quoteReplacement}取消这些字符的特殊含义
     * <br>
     * Dollar signs may be treated as references to captured subsequences as described above,$这个特殊的字符，因为替换串使用这个引用正则表达式匹配的组，
     * $0代表匹配项，$1代表第1个匹配分组，$1代表第2个匹配分组
     * <br>
     * and backslashes are used to escape literal characters in the replacement string. 
     * </p>
     * </blockquote>
     * 
     * @param content
     *            需要被替换的字符串
     * @param regex
     *            用来匹配此字符串的正则表达式
     * @param replacement
     *            用来替换每个匹配项的字符串
     * @return 所得String,如果传过来的内容是空,则返回 {@link org.apache.commons.lang3.StringUtils#EMPTY}
     * @see <a href="http://stamen.iteye.com/blog/2028256">String字符串替换的一个诡异问题</a>
     * @see java.lang.String#replaceAll(String, String)
     * @since jdk 1.4
     */
    public static String replaceAll(CharSequence content,String regex,String replacement){
        return null == content ? StringUtils.EMPTY : content.toString().replaceAll(regex, replacement);
    }

    /**
     * 使用给定的字符串作为模板,替换所有the occurrences of variables with their matching values from the resolver .
     * 
     * <p>
     * The following example demonstrates this:
     * </p>
     * 
     * <pre class="code">
     * Map valuesMap = HashMap();
     * valuesMap.put("animal", "quick brown fox");
     * valuesMap.put("target", "lazy dog");
     * 
     * StrSubstitutor sub = new StrSubstitutor(valuesMap);
     * String templateString = "The ${animal} jumped over the ${target}.";
     * String resolvedString = sub.replace(templateString);
     * </pre>
     * 
     * yielding:
     * 
     * <pre class="code">
     *      The quick brown fox jumped over the lazy dog.
     * </pre>
     *
     * @param <V>
     *            the value type
     * @param templateString
     *            the template string
     * @param valuesMap
     *            the values map
     * @return the string
     * @see org.apache.commons.lang3.text.StrSubstitutor#replace(String)
     * @since 1.1.1
     */
    public static <V> String replace(String templateString,Map<String, V> valuesMap){
        StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
        return strSubstitutor.replace(templateString);
    }

    // [end]

    // [start]startsWith

    /**
     * 测试此字符串是否以指定的前缀 <code>prefix</code>开始.
     * 
     * @param value
     *            value
     * @param prefix
     *            前缀
     * @return 如果参数表示的字符序列是此字符串表示的字符序列的前缀,则返回 true;否则返回 false.<br>
     *         还要注意,如果参数是空字符串,或者等于此 String对象(用 equals(Object) 方法确定),则返回 true.
     */
    public static boolean startsWith(CharSequence value,String prefix){
        return ConvertUtil.toString(value).startsWith(prefix);
    }

    // [end]
    /**
     * 带有数字格式的数字字符串,与数字相加(一般生成流水号使用).
     * 
     * <pre class="code">
     * stringAddInt("002",2)            =   004
     * stringAddInt("000002",1200)      =   001202
     * </pre>
     * 
     * @param str
     *            带有数字格式的数字字符串 比如 002
     * @param i
     *            数字
     * @return 带有数字格式的数字字符串,与数字相加(一般生成流水号使用).
     * @see NumberUtil#toString(Number, String)
     */
    public static String stringAddInt(String str,int i){
        int length = str.length();
        String pattern = "";
        for (int j = 0; j < length; ++j){
            pattern += "0";
        }
        return NumberUtil.toString(Integer.parseInt(str) + i, pattern);
    }

    // [start]substring

    // ********************************substring************************************************
    /**
     * [截取]从指定索引处(beginIndex)的字符开始,直到此字符串末尾.
     * 
     * <p>
     * Gets a substring from the specified String avoiding exceptions.
     * </p>
     *
     * <p>
     * 如果 beginIndex是负数,那么表示倒过来截取,从结尾开始截取长度,此时等同于 {@link #substringLast(String, int)}
     * </p>
     *
     * <pre class="code">
     * StringUtil.substring(null, *)   = null
     * StringUtil.substring("", *)     = ""
     * StringUtil.substring("abc", 0)  = "abc"
     * StringUtil.substring("abc", 2)  = "c"
     * StringUtil.substring("abc", 4)  = ""
     * StringUtil.substring("abc", -2) = "bc"
     * StringUtil.substring("abc", -4) = "abc"
     * </pre>
     * 
     * <pre class="code">
     * substring("jinxin.feilong",6)    =.feilong
     * </pre>
     * 
     * @param text
     *            内容 the String to get the substring from, may be null
     * @param beginIndex
     *            从指定索引处 the position to start from,negative means count back from the end of the String by this many characters
     * @return A <code>null</code> String 返回 <code>null</code>. <br>
     *         An empty ("") String 返回 "".<br>
     *         substring from start position, <code>null</code> if null String input
     * @see org.apache.commons.lang3.StringUtils#substring(String, int)
     * @see #substringLast(String, int)
     */
    public static String substring(final String text,final int beginIndex){
        return StringUtils.substring(text, beginIndex);
    }

    /**
     * [截取]从开始位置(startIndex),截取固定长度(length)字符串.
     * 
     * <pre class="code">
     * StringUtil.substring("jinxin.feilong", 6, 2)     =   .f
     * </pre>
     *
     * @param textString
     *            被截取文字
     * @param startIndex
     *            索引开始位置,0开始
     * @param length
     *            长度 {@code >=1} 1个 即本身 <br>
     *            正常情况下,即返回出来的字符串长度
     * @return the string
     * @see org.apache.commons.lang3.StringUtils#substring(String, int, int)
     */
    public static String substring(final String textString,int startIndex,int length){
        return StringUtils.substring(textString, startIndex, startIndex + length);
    }

    /**
     * [截取]:从第一次出现字符串位置开始(包含)截取到最后.
     * 
     * <p>
     * 调用{@link #substring(String, String, int)}, 默认 shift=0 包含当前 beginString.
     * </p>
     * 
     * <pre class="code">
     * substring("jinxin.feilong",".")  =.feilong
     * </pre>
     * 
     * @param text
     *            text
     * @param beginString
     *            beginString开始截取的字符串
     * @return 调用{@link #substring(String, String, int)}, 默认 shift=0 包含当前 beginString.
     * @see #substring(String, String, int)
     */
    public static String substring(final String text,String beginString){
        return substring(text, beginString, 0);
    }

    /**
     * [截取]:从第一次出现字符串位置开始(包含)截取到最后,shift表示向前或者向后挪动位数.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>substring("jinxin.feilong",".",0)======&gt;".feilong"</li>
     * <li>substring("jinxin.feilong",".",1)======&gt;"feilong"</li>
     * </ul>
     * </blockquote>
     *
     * @param text
     *            text
     * @param beginString
     *            beginString
     * @param shift
     *            负数表示向前,整数表示向后,0表示依旧从自己的位置开始算起
     * @return
     *         <ul>
     *         <li>如果 isNullOrEmpty(text),return null</li>
     *         <li>如果 isNullOrEmpty(beginString),return null</li>
     *         <li>如果 text.indexOf(beginString)==-1,return null</li>
     *         <li>{@code  beginIndex + shift > text.length()},return null</li>
     *         <li>else,return text.substring(beginIndex + shift)</li>
     *         </ul>
     * @see org.apache.commons.lang3.StringUtils#substringAfter(String, String)
     */
    public static String substring(final String text,String beginString,int shift){
        if (Validator.isNullOrEmpty(text)){
            return StringUtils.EMPTY;
        }

        if (Validator.isNullOrEmpty(beginString)){
            return StringUtils.EMPTY;
        }
        //****************************************************
        int beginIndex = text.indexOf(beginString);
        if (beginIndex == -1){// 查不到指定的字符串
            return StringUtils.EMPTY;
        }
        //****************************************************
        int startIndex = beginIndex + shift;
        int textLength = text.length();

        String message = "beginIndex+shift<0,beginIndex:{},shift:{},text:{},text.length:{}";
        Validate.isTrue(startIndex >= 0, Slf4jUtil.formatMessage(message, beginIndex, shift, text, textLength));

        if (startIndex > textLength){
            message = "beginIndex+shift>text.length(),beginIndex:{},shift:{},text:{},text.length:{}";
            LOGGER.warn(message, beginIndex, shift, text, textLength);
            return StringUtils.EMPTY;
        }
        // 索引从0开始
        return text.substring(startIndex);
    }

    /**
     * [截取]:从开始的字符串到结束的字符串中间的字符串.
     * 
     * <p>
     * 包含开始的字符串startString,不包含结束的endString.
     * </p>
     * 
     * @param text
     *            文字
     * @param startString
     *            开始的字符串,null表示从开头开始截取
     * @param endString
     *            结束的字符串
     * @return
     *         <ul>
     *         <li>Validator.isNullOrEmpty(text),return null;</li>
     *         <li>Validator.isNullOrEmpty(startString),return text.substring(0, text.indexOf(endString))</li>
     *         </ul>
     * @see org.apache.commons.lang3.StringUtils#substringBetween(String, String, String)
     */
    public static String substring(final String text,final String startString,final String endString){
        if (Validator.isNullOrEmpty(text)){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(startString)){
            return text.substring(0, text.indexOf(endString));
        }
        int beginIndex = text.indexOf(startString);
        int endIndex = text.indexOf(endString);
        return text.substring(beginIndex, endIndex);
    }

    /**
     * [截取]:获取文字最后位数的字符串.
     * 
     * <p>
     * 调用了 {@link String#substring(int)}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * StringUtil.substringLast("jinxin.feilong", 5) //ilong
     * </pre>
     * 
     * </blockquote>
     * 
     * @param text
     *            文字
     * @param lastLenth
     *            最后的位数
     * @return 截取文字最后几个字符串
     * @see java.lang.String#substring(int)
     */
    public static String substringLast(final String text,int lastLenth){
        return text.substring(text.length() - lastLenth);
    }

    /**
     * [截取]:去除最后几位.
     * 
     * <p>
     * 调用了 {@link java.lang.String#substring(int, int)}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * StringUtil.substringWithoutLast("jinxin.feilong", 5) //jinxin.fe
     * </pre>
     * 
     * </blockquote>
     * 
     * @param text
     *            文字
     * @param lastLenth
     *            最后的位数
     * @return 去除最后几位,如果text是空,则返回 {@link StringUtils#EMPTY}
     * @see java.lang.String#substring(int, int)
     * @see org.apache.commons.lang3.StringUtils#left(String, int)
     */
    public static String substringWithoutLast(final String text,final int lastLenth){
        return null == text ? StringUtils.EMPTY : text.substring(0, text.length() - lastLenth);
    }

    /**
     * [截取]:去除最后的字符串(<code>text</code>必须以 <code>lastString</code>结尾).
     *
     * @param text
     *            the text
     * @param lastString
     *            the last string
     * @return the string
     * @since 1.4.0
     */
    public static String substringWithoutLast(final CharSequence text,final String lastString){
        if (Validator.isNullOrEmpty(text)){
            return StringUtils.EMPTY;
        }

        //由于上面的循环中,最后一个元素可能是null或者empty,判断加还是不加拼接符有点麻烦,因此,循环中统一拼接,但是循环之后做截取处理
        String returnValue = text.toString();
        if (Validator.isNullOrEmpty(lastString)){
            return returnValue;
        }
        return returnValue.endsWith(lastString) ? substringWithoutLast(returnValue, lastString.length()) : returnValue;
    }

    // [end]

    // [start]toBytes

    // ********************************************************************************
    /**
     * 字符串转换成byte数组.
     * 
     * @param value
     *            字符串
     * @return byte数组
     * @since 1.3.0
     */
    public static byte[] getBytes(String value){
        return value.getBytes();
    }

    /**
     * 字符串转换成byte数组.
     * 
     * @param value
     *            字符串
     * @param charsetName
     *            受支持的 charset 名称,比如 utf-8, {@link CharsetType}
     * @return 所得 byte 数组
     * @see String#getBytes(String)
     * @see CharsetType
     * @since 1.3.0
     */
    public static byte[] getBytes(String value,String charsetName){
        try{
            return value.getBytes(charsetName);
        }catch (UnsupportedEncodingException e){
            LOGGER.error(e.getClass().getName(), e);
        }
        return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    // [end]

    // [start]splitToT

    /**
     * 将字符串分隔成 字符串数组.
     * 
     * <h3>注意</h3>
     * 
     * <blockquote>
     * <p>
     * 注意此处不是简单的分隔符是正则表达式, .$|()[{^?*+\\ 在正则表达式中有特殊的含义,因此我们使用.的时候必须进行转义,<br>
     * <span style="color:red">"\"转义时要写成"\\\\"</span> <br>
     * 最终调用了 {@link java.util.regex.Pattern#split(CharSequence)}
     * </p>
     * </blockquote>
     * 
     * 建议使用 {@link #tokenizeToStringArray(String, String)} 或者 {@link StringUtils#split(String)}
     * 
     * @param value
     *            value
     * @param regexSpliter
     *            分隔符,注意此处不是简单的分隔符是正则表达式, .$|()[{^?*+\\ 在正则表达式中有特殊的含义,因此我们使用.的时候必须进行转义,<br>
     *            <span style="color:red">"\"转义时要写成"\\\\"</span> <br>
     *            最终调用了 {@link java.util.regex.Pattern#split(CharSequence)}
     * @return 如果value 是null,返回null
     * @see String#split(String)
     * @see String#split(String, int)
     * @see java.util.regex.Pattern#split(CharSequence)
     * @see StringUtils#split(String)
     * @see #tokenizeToStringArray(String, String)
     */
    public static String[] split(String value,String regexSpliter){
        return Validator.isNullOrEmpty(value) ? ArrayUtils.EMPTY_STRING_ARRAY : value.split(regexSpliter);
    }

    // [end]

    // [start]tokenizeToStringArray

    /**
     * (此方法借鉴 {@link "org.springframework.util.StringUtils#tokenizeToStringArray"}).
     * 
     * <p>
     * 调用了 {@link #tokenizeToStringArray(String, String, boolean, boolean)},本方法,默认使用参数 trimTokens = true;
     * ignoreEmptyTokens = true;
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String str = "jin.xin  feilong ,jinxin;venusdrogon;jim ";
     * String delimiters = ";, .";
     * String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray(str, delimiters);
     * LOGGER.info(JsonUtil.format(tokenizeToStringArray));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     [
             "jin",
             "xin",
             "feilong",
             "jinxin",
             "venusdrogon",
             "jim"
         ]
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * Tokenize the given String into a String array via a StringTokenizer. <br>
     * Trims tokens and omits empty tokens.
     * </p>
     * 
     * <p>
     * The given delimiters string is supposed to consist of any number of delimiter characters. Each of those characters can be used to
     * separate tokens. A delimiter is always a single character; for multi-character delimiters, consider using
     * <code>delimitedListToStringArray</code>
     * 
     * @param str
     *            the String to tokenize
     * @param delimiters
     *            the delimiter characters, assembled as String<br>
     *            参数中的所有字符都是分隔标记的分隔符,比如这里可以设置成 ";, " ,spring就是使用这样的字符串来分隔数组/集合的
     * @return an array of the tokens
     * @see java.util.StringTokenizer
     * @see String#trim()
     * @see "org.springframework.util.StringUtils#delimitedListToStringArray"
     * @see "org.springframework.util.StringUtils#tokenizeToStringArray"
     * 
     * @see #tokenizeToStringArray(String, String, boolean, boolean)
     * @since 1.0.7
     */
    public static String[] tokenizeToStringArray(String str,String delimiters){
        boolean trimTokens = true;
        boolean ignoreEmptyTokens = true;
        return tokenizeToStringArray(str, delimiters, trimTokens, ignoreEmptyTokens);
    }

    /**
     * (此方法借鉴 {@link "org.springframework.util.StringUtils#tokenizeToStringArray"}).
     * 
     * <p>
     * Tokenize the given String into a String array via a StringTokenizer.
     * </p>
     * 
     * <p>
     * The given delimiters string is supposed to consist of any number of delimiter characters. <br>
     * Each of those characters can be used to separate tokens. <br>
     * A delimiter is always a single character; <br>
     * for multi-character delimiters, consider using <code>delimitedListToStringArray</code>
     * </p>
     * 
     * <h3>about {@link StringTokenizer}:</h3>
     * 
     * <blockquote>
     * 
     * {@link StringTokenizer} implements {@code Enumeration<Object>}<br>
     * 其在 Enumeration接口的基础上,定义了 hasMoreTokens nextToken两个方法<br>
     * 实现的Enumeration接口中的 hasMoreElements nextElement,调用了 hasMoreTokens nextToken<br>
     * 
     * </blockquote>
     * 
     * @param str
     *            the String to tokenize
     * @param delimiters
     *            the delimiter characters, assembled as String<br>
     *            参数中的所有字符都是分隔标记的分隔符,比如这里可以设置成 ";, " ,spring就是使用这样的字符串来分隔数组/集合的
     * @param trimTokens
     *            是否使用 {@link String#trim()}操作token
     * @param ignoreEmptyTokens
     *            是否忽视空白的token,如果为true,那么token必须长度 {@code >} 0;如果为false会包含长度=0 空白的字符<br>
     *            omit empty tokens from the result array
     *            (only applies to tokens that are empty after trimming; StringTokenizer
     *            will not consider subsequent delimiters as token in the first place).
     * @return an array of the tokens (<code>null</code> if the input String was <code>null</code>)
     * @see java.util.StringTokenizer
     * @see String#trim()
     * @see "org.springframework.util.StringUtils#delimitedListToStringArray"
     * @see "org.springframework.util.StringUtils#tokenizeToStringArray"
     * @since 1.0.7
     */
    public static String[] tokenizeToStringArray(String str,String delimiters,boolean trimTokens,boolean ignoreEmptyTokens){
        if (null == str){
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }

        StringTokenizer stringTokenizer = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken();
            if (trimTokens){
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0){
                tokens.add(token);
            }
        }
        return ConvertUtil.toArray(tokens, String.class);
    }

    // [end]

    // [start]format

    /**
     * 格式化字符串.
     * 
     * <ul>
     * <li>StringUtil.format("%03d", 1)不能写成 StringUtil.format("%03d", "1")</li>
     * </ul>
     * 
     * <p>
     * %index$开头,index从1开始取值,表示将第index个参数拿进来进行格式化.<br>
     * 对整数进行格式化:格式化字符串由4部分组成:%[index$][标识][最小宽度]转换方式<br>
     * 对浮点数进行格式化:%[index$][标识][最少宽度][.精度]转换方式<br>
     * </p>
     * 
     * <p>
     * 转换符和标志的说明
     * </p>
     * 
     * <h3>转换符</h3>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">转换符</th>
     * <th align="left">说明</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr valign="top">
     * <td>%s</td>
     * <td>字符串类型</td>
     * <td>"mingrisoft"</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%c</td>
     * <td>字符类型</td>
     * <td>'m'</td>
     * </tr>
     * <tr valign="top">
     * <td>%b</td>
     * <td>布尔类型</td>
     * <td>true</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%d</td>
     * <td>整数类型(十进制)</td>
     * <td>99</td>
     * </tr>
     * <tr valign="top">
     * <td>%x</td>
     * <td>整数类型(十六进制)</td>
     * <td>FF</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%o</td>
     * <td>整数类型(八进制)</td>
     * <td>77</td>
     * </tr>
     * <tr valign="top">
     * <td>%f</td>
     * <td>浮点类型</td>
     * <td>99.99</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%a</td>
     * <td>十六进制浮点类型</td>
     * <td>FF.35AE</td>
     * </tr>
     * <tr valign="top">
     * <td>%e</td>
     * <td>指数类型</td>
     * <td>9.38e+5</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%g</td>
     * <td>通用浮点类型(f和e类型中较短的)</td>
     * <td></td>
     * </tr>
     * <tr valign="top">
     * <td>%h</td>
     * <td>散列码</td>
     * <td></td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%%</td>
     * <td>百分比类型</td>
     * <td>％</td>
     * </tr>
     * <tr valign="top">
     * <td>%n</td>
     * <td>换行符</td>
     * <td></td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%tx</td>
     * <td>日期与时间类型(x代表不同的日期与时间转换符</td>
     * <td></td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * <h3>标志</h3>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">标志</th>
     * <th align="left">说明</th>
     * <th align="left">示例</th>
     * <th align="left">结果</th>
     * </tr>
     * <tr valign="top">
     * <td>+</td>
     * <td>为正数或者负数添加符号</td>
     * <td>("%+d",15)</td>
     * <td>+15</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>-</td>
     * <td>左对齐(不可以与"用0填充"同时使用)</td>
     * <td>("%-5d",15)</td>
     * <td>|15 |</td>
     * </tr>
     * <tr valign="top">
     * <td>0</td>
     * <td>数字前面补0</td>
     * <td>("%04d", 99)</td>
     * <td>0099</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>空格</td>
     * <td>在整数之前添加指定数量的空格</td>
     * <td>("% 4d", 99)</td>
     * <td>| 99|</td>
     * </tr>
     * <tr valign="top">
     * <td>,</td>
     * <td>以","对数字分组</td>
     * <td>("%,f", 9999.99)</td>
     * <td>9,999.990000</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>(</td>
     * <td>使用括号包含负数</td>
     * <td>("%(f", -99.99)</td>
     * <td>(99.990000)</td>
     * </tr>
     * <tr valign="top">
     * <td>#</td>
     * <td>如果是浮点数则包含小数点,如果是16进制或8进制则添加0x或0</td>
     * <td>("%#x", 99) <br>
     * ("%#o", 99)</td>
     * <td>0x63<br>
     * 0143</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@code <}</td>
     * <td>格式化前一个转换符所描述的参数</td>
     * <td>("%f和%{@code <}3.2f", 99.45)</td>
     * <td>99.450000和99.45</td>
     * </tr>
     * <tr valign="top">
     * <td>$</td>
     * <td>被格式化的参数索引</td>
     * <td>("%1$d,%2$s", 99,"abc")</td>
     * <td>99,abc</td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * @param format
     *            the format
     * @param args
     *            the args
     * @return 如果 null ==format,return {@link StringUtils#EMPTY},else return {@link String#format(String, Object...)}
     * @see java.util.Formatter
     * @see String#format(String, Object...)
     * @see String#format(java.util.Locale, String, Object...)
     * @since JDK 1.5
     */
    public static String format(String format,Object...args){
        return null == format ? StringUtils.EMPTY : String.format(format, args);
    }
    // [end]
}