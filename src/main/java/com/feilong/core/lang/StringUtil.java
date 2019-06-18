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

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.util.CollectionsUtil.newArrayList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrSubstitutor;

import com.feilong.core.CharsetType;
import com.feilong.core.UncheckedIOException;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * {@link String}工具类,可以查询,截取,format.
 * 
 * <h3>分隔(split)</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link #split(String, String)}</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>分隔(tokenize)</h3>
 * 
 * <blockquote>
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
 * 
 * </blockquote>
 * 
 * <h3>查询(search)</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link StringUtils#countMatches(CharSequence, CharSequence)} 查询出现的次数</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>其他</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link StringUtils#capitalize(String)} 首字母大写</li>
 * <li>{@link StringUtils#uncapitalize(String)} 单词首字母小写</li>
 * <li>{@link org.apache.commons.lang3.text.WordUtils#uncapitalize(String, char...)} 如果要使用一段文字,每个单词首字母小写</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>{@link String#String(byte[] )} 和 {@link String#String(byte[], Charset)} 区别</h3>
 * 
 * <blockquote>
 * <p>
 * {@link String#String(byte[] )} 其实调用了{@link String#String(byte[], Charset)};<br>
 * 先使用 {@link Charset#defaultCharset()},如果有异常再用 ISO-8859-1, 具体参见 java.lang.StringCoding#decode(byte[], int, int)
 * </p>
 * </blockquote>
 * 
 * <h3>{@link StringBuffer} 和 {@link StringBuilder} 和 {@link String} 对比</h3>
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
 * 要看虚拟机的实现.而且要联系上下文
 * </p>
 * 
 * <ul>
 * <li>假设:HotSpot1.6,之前没有创建过"xyz" 则创建2个,之前创建过"xyz"则只创建1个</li>
 * <li>假设:HotSpot1.7,之前不管有没有创建过"xyz" 都创建1个</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>String s3 = s1 + s2; <br>
 * System.out.println(s3.intern() == s3); 到底相不相等</h3> <blockquote>
 * <p>
 * 要看虚拟机的实现
 * </p>
 * 
 * <ul>
 * <li>假设:HotSpot1.6,则false不相等</li>
 * <li>假设:HotSpot1.7,则在之前没有创建过"abcabc"时,true相等</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>{@link StringUtil#replace(String, String, String) replace} 和 {@link #replaceAll(CharSequence, String, String) replaceAll} 和
 * {@link String#replaceFirst(String, String) replaceFirst}区别:</h3>
 * 
 * <blockquote>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link StringUtil#replace(String, String, String)}</td>
 * <td>将字符串中出现的target替换成replacement</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link #replaceAll(CharSequence, String, String)}</td>
 * <td>regex是一个正则表达式,将字符串中匹配的子字符串替换为replacement</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link String#replaceFirst(String, String)}</td>
 * <td>和{@link StringUtil#replace(String, String, String)}类似,只不过只替换第一个出现的地方。</td>
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
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.util.StringTokenizer
 * @see "org.springframework.util.StringUtils#tokenizeToStringArray(String, String)"
 * @see "org.springframework.beans.factory.xml.BeanDefinitionParserDelegate#MULTI_VALUE_ATTRIBUTE_DELIMITERS"
 * @see org.apache.commons.lang3.StringUtils
 * @see "com.google.common.base.Strings"
 * @since 1.4.0
 */
public final class StringUtil{

    /** Don't let anyone instantiate this class. */
    private StringUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the given charset.
     *
     * @param bytes
     *            The bytes to be decoded into characters, may be <code>null</code>
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量
     * @return A new <code>String</code> decoded from the specified array of bytes using the given charset,
     *         or <code>null</code> if the input byte array was <code>null</code>.
     * @see String#String(byte[], String)
     * @see "org.apache.commons.lang3.StringUtils#toString(byte[], String)"
     * @see org.apache.commons.lang3.StringUtils#toEncodedString(byte[], Charset)
     * @see "org.apache.commons.codec.binary.StringUtils#newString(byte[], String)"
     * @since 1.3.0
     */
    public static String newString(byte[] bytes,String charsetType){
        return StringUtils.toEncodedString(bytes, Charset.forName(charsetType));
    }
    // [start]toBytes

    //---------------------------------------------------------------
    /**
     * 字符串转换成byte数组.
     *
     * @param value
     *            字符串
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     * @see String#getBytes()
     * @since 1.3.0
     */
    public static byte[] getBytes(String value){
        Validate.notNull(value, "value can't be null!");
        return value.getBytes();
    }

    /**
     * 字符串 <code>value</code> 转换成byte数组.
     * 
     * @param value
     *            字符串
     * @param charsetName
     *            受支持的 charset 名称,比如 utf-8, {@link CharsetType}
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>charsetName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>charsetName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see String#getBytes(String)
     * @since 1.3.0
     */
    public static byte[] getBytes(String value,String charsetName){
        Validate.notNull(value, "value can't be null!");
        Validate.notBlank(charsetName, "charsetName can't be blank!");

        //---------------------------------------------------------------
        try{
            return value.getBytes(charsetName);
        }catch (UnsupportedEncodingException e){
            String pattern = "value:[{}],charsetName:[{}],suggest you use [{}] constants";
            String message = Slf4jUtil.format(pattern, value, charsetName, CharsetType.class.getCanonicalName());
            throw new UncheckedIOException(message, e);
        }
    }

    // [end]
    // [start]replace

    //---------------------------------------------------------------
    /**
     * 将 <code>text</code> 中的 <code>searchString</code> 替换成 <code>replacement</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * A {@code null} reference passed to this method is a no-op.
     * </p>
     *
     * <pre>
     * StringUtil.replace(null, *, *) = null
     * StringUtil.replace("", *, *) = ""
     * StringUtil.replace("any", null, *) = "any"
     * StringUtil.replace("any", *, null) = "any"
     * StringUtil.replace("any", "", *) = "any"
     * StringUtil.replace("aba", "a", null) = "aba"
     * StringUtil.replace("aba", "a", "") = "b"
     * StringUtil.replace("aba", "a", "z") = "zbz"
     * 
     * StringUtil.replace("黑色/黄色/蓝色", "/", "_")         =   "黑色_黄色_蓝色"
     * StringUtil.replace(null, "/", "_")               =   null
     * StringUtil.replace("黑色/黄色/蓝色", "/", null)        =   "黑色/黄色/蓝色"
     * </pre>
     * 
     * 此外注意的是:
     * 
     * <pre class="code">
     * StringUtil.replace("SH1265,SH5951", "([a-zA-Z]+[0-9]+)", "'$1'") = SH1265,SH5951
     * </pre>
     * 
     * (注意和 {@link #replaceAll(CharSequence, String, String)} 的区别)
     * 
     * </blockquote>
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
     * @param text
     *            text to search and replace in, may be null
     * @param searchString
     *            the String to search for, may be null
     * @param replacement
     *            the String to replace it with, may be null
     * @return 如果 <code>text</code> 是null,返回 null<br>
     *         如果 <code>searchString</code> 是null,原样返回 <code>text</code><br>
     *         如果 <code>replacement</code> 是null,原样返回 <code>text</code><br>
     * @see java.lang.String#replace(CharSequence, CharSequence)
     * @see org.apache.commons.lang3.StringUtils#replace(String, String, String)
     * @since jdk 1.5
     */
    public static String replace(final String text,final String searchString,final String replacement){
        return StringUtils.replace(text, searchString, replacement);
    }

    /**
     * 使用给定的<code>replacement</code>替换 <code>content</code> 此字符串所有匹配给定的正则表达式 <code>regex</code>的子字符串.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 此方法底层调用的是 {@link java.util.regex.Matcher#replaceAll(String)},相同于
     * <code>Pattern.compile(regex).matcher(str).replaceAll(repl)</code>
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * StringUtil.replaceAll("SH1265,SH5951,SH6766,SH7235,SH1265,SH5951,SH6766,SH7235", "([a-zA-Z]+[0-9]+)", "'$1'")
     * </pre>
     * 
     * <b>返回:</b>
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
     * 
     * <p>
     * 在替代字符串<code>replacement</code>中,使用 backslashes反斜杆(<tt>\</tt>)和 dollar signs美元符号 (<tt>$</tt>)与将其视为字面值替代字符串所得的结果可能不同.
     * <br>
     * 请参阅 {@link java.util.regex.Matcher#replaceAll Matcher.replaceAll};如有需要,可使用 {@link java.util.regex.Matcher#quoteReplacement
     * Matcher.quoteReplacement}取消这些字符的特殊含义
     * </p>
     * 
     * <p>
     * <tt>$</tt> may be treated as references to captured subsequences as described above,<tt>$</tt>这个特殊的字符,因为替换串使用这个引用正则表达式匹配的组,
     * $0代表匹配项,$1代表第1个匹配分组,$1代表第2个匹配分组
     * <br>
     * 并且 <tt>\</tt> are used to escape literal characters in the replacement string. 
     * </p>
     * </blockquote>
     * 
     * <h3>对于以下代码:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">//分隔字符串并添加引号.</span>
     * public void splitAndAddYinHao(){
     *     String a = "12345,56789,1123456";
     *     String[] aStrings = a.split(",");
     *     StringBuilder sb = new StringBuilder();
     *     int size = aStrings.length;
     *     for (int i = 0; i {@code <} size; i++){
     *         sb.append("'" + aStrings[i] + "'");
     *         if (i != size - 1){
     *             sb.append(",");
     *         }
     *     }
     *     LOGGER.debug(sb.toString());
     * }
     * 
     * </pre>
     * 
     * 可以重构成:
     * 
     * <pre class="code">
     * StringUtil.replaceAll("12345,56789,1123456", "([0-9]+)", "'$1'")
     * </pre>
     * 
     * 结果都是:
     * 
     * <pre class="code">
     * '12345','56789','1123456'
     * </pre>
     * 
     * </blockquote>
     * 
     * @param content
     *            需要被替换的字符串
     * @param regex
     *            用来匹配此字符串的正则表达式,规则参见 {@link "RegexPattern"}注释
     * @param replacement
     *            用来替换每个匹配项的字符串
     * @return 如果 <code>content</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>content</code> 中,没有 regex匹配的字符串或者格式,返回<code>content</code><br>
     * @see <a href="http://stamen.iteye.com/blog/2028256">String字符串替换的一个诡异问题</a>
     * @see java.lang.String#replaceAll(String, String)
     * @since jdk 1.4
     */
    public static String replaceAll(CharSequence content,String regex,String replacement){
        return null == content ? EMPTY : content.toString().replaceAll(regex, replacement);
    }

    /**
     * 使用给定的字符串 <code>templateString</code> 作为模板,解析匹配的变量 .
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * String template = "/home/webuser/expressdelivery/${yearMonth}/${expressDeliveryType}/vipQuery_${fileName}.log";
     * Date date = now();
     * 
     * Map{@code <String, String>} valuesMap = newHashMap();
     * valuesMap.put("yearMonth", DateUtil.toString(date, DatePattern.YEAR_AND_MONTH));
     * valuesMap.put("expressDeliveryType", "sf");
     * valuesMap.put("fileName", DateUtil.toString(date, DatePattern.TIMESTAMP));
     * LOGGER.debug(StringUtil.replace(template, valuesMap));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * /home/webuser/expressdelivery/2016-06/sf/vipQuery_20160608214846.log
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * <span style="color:red">注意:此方法只能替换字符串,而不能像el表达式一样使用对象属性之类的来替换</span>
     * </p>
     * 
     * <h3>比如:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Object>} valuesMap = newHashMap();
     * valuesMap.put("today", DateUtil.toString(now(), COMMON_DATE));
     * valuesMap.put("user", new User(1L));
     * LOGGER.debug(StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap) + "");
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * 2016-07-16${today1}${user.id}com.feilong.test.User@16f9a31
     * </pre>
     * 
     * </blockquote>
     *
     * @param <V>
     *            the value type
     * @param templateString
     *            the template string
     * @param valuesMap
     *            the values map
     * @return 如果 <code>templateString</code> 是 <code>StringUtils.isEmpty(templateString)</code>,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>valuesMap</code> 是null或者empty,原样返回 <code>templateString</code><br>
     * @see org.apache.commons.lang3.text.StrSubstitutor#replace(String)
     * @see org.apache.commons.lang3.text.StrSubstitutor#replace(Object, Map)
     * @since 1.1.1
     */
    public static <V> String replace(CharSequence templateString,Map<String, V> valuesMap){
        return StringUtils.isEmpty(templateString) ? EMPTY : StrSubstitutor.replace(templateString, valuesMap);
    }

    // [end]

    // [start]substring

    //---------------------------------------------------------------
    /**
     * [截取]从指定索引处(<code>beginIndex</code>)的字符开始,直到此字符串末尾.
     * 
     * <p>
     * 如果 <code>beginIndex</code>是负数,那么表示倒过来截取,从结尾开始截取长度,此时等同于 {@link #substringLast(String, int)}
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
     * StringUtil.substring("jinxin.feilong",6)    =.feilong
     * </pre>
     * 
     * @param text
     *            内容 the String to get the substring from, may be null
     * @param beginIndex
     *            从指定索引处 the position to start from,negative means count back from the end of the String by this many characters
     * @return 如果 <code>text</code> 是null,返回 null<br>
     *         An empty ("") String 返回 "".<br>
     * @see org.apache.commons.lang3.StringUtils#substring(String, int)
     * @see #substringLast(String, int)
     */
    public static String substring(final String text,final int beginIndex){
        return StringUtils.substring(text, beginIndex);
    }

    /**
     * [截取]从开始位置(<code>startIndex</code>),截取固定长度(<code>length</code>)字符串.
     * 
     * <pre class="code">
     * StringUtil.substring(null, 6, 8)                 =   null
     * StringUtil.substring("jinxin.feilong", 6, 2)     =   .f
     * </pre>
     *
     * @param text
     *            被截取文字
     * @param startIndex
     *            索引开始位置,0开始
     * @param length
     *            长度 {@code >=1}
     * @return 如果 <code>text</code> 是null,返回 null<br>
     *         如果 <code>startIndex + length</code> {@code >} <code>text.length</code>,那么截取 从 startIndex 开始截取,截取到最后
     * @see org.apache.commons.lang3.StringUtils#substring(String, int, int)
     */
    public static String substring(final String text,int startIndex,int length){
        return StringUtils.substring(text, startIndex, startIndex + length);
    }

    /**
     * [截取]:获取文字最后位数 <code>lastLenth</code> 的字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * StringUtil.substringLast("jinxin.feilong", 5) = ilong
     * </pre>
     * 
     * </blockquote>
     * 
     * @param text
     *            文字
     * @param lastLenth
     *            最后的位数
     * @return 如果 <code>text</code> 是null,返回 null<br>
     *         如果 {@code lastLenth<0},返回 {@link StringUtils#EMPTY}<br>
     *         如果 {@code text.length() <= lastLenth},返回text<br>
     *         否则返回<code> text.substring(text.length() - lastLenth)</code>
     * @see org.apache.commons.lang3.StringUtils#right(String, int)
     */
    public static String substringLast(final String text,int lastLenth){
        return StringUtils.right(text, lastLenth);
    }

    /**
     * [截取]:去除最后几位 <code>lastLenth</code> .
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
     * @return 如果 <code>text</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see java.lang.String#substring(int, int)
     * @see org.apache.commons.lang3.StringUtils#left(String, int)
     */
    public static String substringWithoutLast(final String text,final int lastLenth){
        return null == text ? EMPTY : text.substring(0, text.length() - lastLenth);
    }

    /**
     * [截取]:去除最后的字符串 <code>lastString</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * StringUtil.substringWithoutLast(null, "222")                     = ""
     * StringUtil.substringWithoutLast("jinxin.feilong", "ng")          = "jinxin.feilo"
     * StringUtil.substringWithoutLast("jinxin.feilong     ", "     ")  = "jinxin.feilong"
     * </pre>
     * 
     * </blockquote>
     *
     * @param text
     *            the text
     * @param lastString
     *            the last string
     * @return 如果 <code>text</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>lastString</code> 是null,返回 <code>text.toString()</code><br>
     * @since 1.4.0
     */
    public static String substringWithoutLast(final CharSequence text,final String lastString){
        if (null == text){
            return EMPTY;
        }
        String textString = text.toString();
        if (null == lastString){
            return textString;
        }
        return textString.endsWith(lastString) ? substringWithoutLast(textString, lastString.length()) : textString;
    }

    // [end]

    // [start]splitToT

    /**
     * 将字符串 <code>value</code> 使用分隔符 <code>regexSpliter</code> 分隔成 字符串数组.
     * 
     * <p>
     * 建议使用 {@link #tokenizeToStringArray(String, String)} 或者 {@link StringUtils#split(String)}
     * </p>
     *
     * @param value
     *            value
     * @param regexSpliter
     *            此处不是简单的分隔符,是正则表达式,<b>.$|()[{^?*+\\</b> 有特殊的含义,因此我们使用.的时候必须进行转义,<span style="color:red">"\"转义时要写成"\\\\"</span> <br>
     *            最终调用了 {@link java.util.regex.Pattern#split(CharSequence)}
     * @return 如果 <code>value</code> 是null或者empty,返回 {@link ArrayUtils#EMPTY_STRING_ARRAY}<br>
     * @see String#split(String)
     * @see String#split(String, int)
     * @see StringUtils#split(String)
     * @see java.util.regex.Pattern#split(CharSequence)
     */
    public static String[] split(String value,String regexSpliter){
        return isNullOrEmpty(value) ? ArrayUtils.EMPTY_STRING_ARRAY : value.split(regexSpliter);
    }

    // [end]

    // [start]tokenizeToStringArray

    /**
     * 使用StringTokenizer分隔给定的字符串到字符串数组,去除 tokens的空格并且忽略empty tokens.
     * 
     * <p>
     * (此方法借鉴 "org.springframework.util.StringUtils#tokenizeToStringArray").
     * </p>
     * 
     * <p>
     * 调用了 {@link #tokenizeToStringArray(String, String, boolean, boolean)},本方法默认使用参数 trimTokens = true;
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
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [
     * "jin",
     * "xin",
     * "feilong",
     * "jinxin",
     * "venusdrogon",
     * "jim"
     * ]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * <p>
     * 给定的delimiters字符串支持任意数量的分隔字符characters. <br>
     * 每一个characters可以用来分隔tokens.一个delimiter分隔符常常是一个single字符;<br>
     * 如果你要使用多字符 multi-character delimiters分隔, 你可以考虑使用<code>delimitedListToStringArray</code>
     * </p>
     * </blockquote>
     * 
     * @param str
     *            需要被分隔的字符串
     * @param delimiters
     *            delimiter characters, assembled as String<br>
     *            参数中的所有字符都是分隔标记的分隔符,比如这里可以设置成 ";, " ,spring就是使用这样的字符串来分隔数组/集合的
     * @return 如果 <code>str</code> 是null,返回 {@link ArrayUtils#EMPTY_STRING_ARRAY}<br>
     *         如果 <code>str</code> 是blank,返回 {@link ArrayUtils#EMPTY_STRING_ARRAY}<br>
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
     * 使用StringTokenizer分隔给定的字符串到字符串数组.
     * 
     * <p>
     * (此方法借鉴 "org.springframework.util.StringUtils#tokenizeToStringArray").
     * </p>
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * <p>
     * 给定的delimiters字符串支持任意数量的分隔字符characters. <br>
     * 每一个characters可以用来分隔tokens.一个delimiter分隔符常常是一个single字符;<br>
     * 如果你要使用多字符 multi-character delimiters分隔, 你可以考虑使用<code>delimitedListToStringArray</code>
     * </p>
     * </blockquote>
     * 
     * <h3>关于 {@link StringTokenizer}:</h3>
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
     *            需要被分隔的字符串
     * @param delimiters
     *            delimiter characters, assembled as String<br>
     *            参数中的所有字符都是分隔标记的分隔符,比如这里可以设置成 ";, " ,spring就是使用这样的字符串来分隔数组/集合的
     * @param trimTokens
     *            是否使用 {@link String#trim()}操作token
     * @param ignoreEmptyTokens
     *            是否忽视空白的token,如果为true,那么token必须长度 {@code >} 0;如果为false会包含长度=0 空白的字符<br>
     *            (仅仅用于那些 trim之后是empty的tokens,StringTokenizer不会考虑subsequent delimiters as token in the first place).
     * @return 如果 <code>str</code> 是null,返回 {@link ArrayUtils#EMPTY_STRING_ARRAY}<br>
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

        List<String> tokens = newArrayList();

        StringTokenizer stringTokenizer = new StringTokenizer(str, delimiters);
        while (stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken();
            token = trimTokens ? token.trim() : token;//去空

            if (!ignoreEmptyTokens || token.length() > 0){
                tokens.add(token);
            }
        }
        return toArray(tokens, String.class);
    }

    // [end]

    // [start]format

    /**
     * 将各类数据,使用{@link String#format(String, Object...)}格式化为字符串.
     * 
     * <h3>规则:</h3>
     * <blockquote>
     * 
     * <ol>
     * 
     * <li>
     * 
     * <p>
     * <b>对整数进行格式化:</b>
     * </p>
     * 
     * <p>
     * 由4部分组成:<span style="color:green">%[index$][标识][最小宽度]转换方式</span>
     * </p>
     * 
     * <p>
     * 此外,StringUtil.format("%03d", 1) 不能写成 StringUtil.format("%03d", "1")
     * </p>
     * </li>
     * 
     * <li>
     * <p>
     * <b>对浮点数进行格式化:</b>
     * </p>
     * 
     * <p>
     * <span style="color:green">%[index$][标识][最少宽度][.精度]转换方式</span>
     * </p>
     * 
     * </li>
     * 
     * <li>%index$开头,index从1开始取值,表示将第index个参数拿进来进行格式化.</li>
     * 
     * </ol>
     * 
     * </blockquote>
     * 
     * <p>
     * 转换符和标志的说明:
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
     * 
     * <tr valign="top">
     * <td>%s</td>
     * <td>字符串类型</td>
     * <td>"mingrisoft"</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%c</td>
     * <td>字符类型</td>
     * <td>'m'</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>%b</td>
     * <td>布尔类型</td>
     * <td>true</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%d</td>
     * <td>整数类型(十进制)</td>
     * <td>99</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>%x</td>
     * <td>整数类型(十六进制)</td>
     * <td>FF</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%o</td>
     * <td>整数类型(八进制)</td>
     * <td>77</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>%f</td>
     * <td>浮点类型</td>
     * <td>99.99</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%a</td>
     * <td>十六进制浮点类型</td>
     * <td>FF.35AE</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>%e</td>
     * <td>指数类型</td>
     * <td>9.38e+5</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%g</td>
     * <td>通用浮点类型(f和e类型中较短的)</td>
     * <td></td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>%h</td>
     * <td>散列码</td>
     * <td></td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>%%</td>
     * <td>百分比类型</td>
     * <td>％</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>%n</td>
     * <td>换行符</td>
     * <td></td>
     * </tr>
     * 
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
     * 
     * <tr valign="top">
     * <td>+</td>
     * <td>为正数或者负数添加符号</td>
     * <td>("%+d",15)</td>
     * <td>+15</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>-</td>
     * <td>左对齐(不可以与"用0填充"同时使用)</td>
     * <td>("%-5d",15)</td>
     * <td>|15 |</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>0</td>
     * <td>数字前面补0</td>
     * <td>("%04d", 99)</td>
     * <td>0099</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>空格</td>
     * <td>在整数之前添加指定数量的空格</td>
     * <td>("% 4d", 99)</td>
     * <td>| 99|</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>,</td>
     * <td>以","对数字分组</td>
     * <td>("%,f", 9999.99)</td>
     * <td>9,999.990000</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>(</td>
     * <td>使用括号包含负数</td>
     * <td>("%(f", -99.99)</td>
     * <td>(99.990000)</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>#</td>
     * <td>如果是浮点数则包含小数点,如果是16进制或8进制则添加0x或0</td>
     * <td>("%#x", 99) <br>
     * ("%#o", 99)</td>
     * <td>0x63<br>
     * 0143</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@code <}</td>
     * <td>格式化前一个转换符所描述的参数</td>
     * <td>("%f和%{@code <}3.2f", 99.45)</td>
     * <td>99.450000和99.45</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>$</td>
     * <td>被格式化的参数索引</td>
     * <td>("%1$d,%2$s", 99,"abc")</td>
     * <td>99,abc</td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     * 
     * @param format
     *            the format
     * @param args
     *            the args
     * @return 如果 <code>format</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>format</code> 包含不需要转化的字符串,这些字符串是你写什么,最终就输出什么<br>
     *         否则返回 {@link String#format(String, Object...)}
     * @see java.util.Formatter
     * @see String#format(String, Object...)
     * @see String#format(java.util.Locale, String, Object...)
     * @since JDK 1.5
     */
    public static String format(String format,Object...args){
        return null == format ? EMPTY : String.format(format, args);
    }
    // [end]
}