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

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

import static com.feilong.core.Validator.isNullOrEmpty;

/**
 * The Class StringUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class StringUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilTemp.class);

    /**
     * 如何计算出所有左右对称的三位数,如232,反过来还是232.
     */
    @Test
    public void testStringUtilTest(){
        for (int i = 100; i <= 999; ++i){
            if (i / 100 == i % 10){
                System.out.println(i);
            }
        }
    }

    /**
     * Length.
     */
    @Test
    public void length(){
        String string = "我的新浪微博:http://weibo.com/venusdrogon,关注我哦[url=http://bbs.guqu.net/Query.asp?keyword=%B6%C5%B4%CF%D7%A8%BC%AD&boardid=0&sType=2]sssss[/url][url=http://weibo.com/venusdrogon][img]http://service.t.sina.com.cn/widget/qmd/1903991210/1c853142/5.png[/img][/url]";
        LOGGER.debug(string.length() + "");

        // 运单号
        LOGGER.debug("3999e85461ce7271dd5292c88f18567e".length() + "");
    }

    /**
     * 查找子字符串在 字符串中出现的次数.
     * 
     * <pre class="code">
     * StringUtil.searchTimes("xin", "xin")     = 1
     * StringUtil.searchTimes("xiiiin", "ii")   = 2
     * </pre>
     *
     * @param source
     *            查找的源字符串
     * @param target
     *            目标子串
     * @return count of target string in source
     * @see org.apache.commons.lang3.StringUtils#countMatches(CharSequence, CharSequence)
     * @since 1.0.2
     * @deprecated 使用 {@link org.apache.commons.lang3.StringUtils#countMatches(CharSequence, CharSequence)}
     */
    @Deprecated
    private static int searchTimes(String source,String target){
        Validate.notNull(source, "source can't be null!");
        Validate.notNull(target, "target can't be null!");
        // times 计数器
        int count = 0;
        // while 循环 点
        int j = 0;
        // 开始搜索的索引位置
        int fromIndex = 0;
        int sourceLength = source.length();
        // 刚开始从 0的地方查找起
        while (j != sourceLength){
            // 从指定的索引开始 返回索引位置
            int i = source.indexOf(target, fromIndex);
            if (i != StringUtils.INDEX_NOT_FOUND){
                int targetLength = target.length();
                // 一旦发现 查找到,下次 循环从找到的地方开始循环
                // 查找 从 找到的地方 开始查找
                j = i + targetLength;
                fromIndex = i + targetLength;
                count++;
            }else{
                // 如果发现找不到了 就退出循环
                break;
            }
        }
        return count;
    }

    /**
     * 返回一个随机的字符串.150是基于该程序使用场景的抽样得到的长度..
     * 
     * @return the random string
     */
    private static String getRandomString(){
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int length = 150 + r.nextInt(50);
        for (int i = 0; i < length; i++){
            sb.append('a' + r.nextInt(26));
        }
        return sb.toString();
    }

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
     * 
     * <pre class="code">
     * StringUtil.substring("jinxin.feilong",".",0)     =   ".feilong"
     * StringUtil.substring("jinxin.feilong",".",1)     =   "feilong"
     * </pre>
     * 
     * </blockquote>
     *
     * @param text
     *            text
     * @param beginString
     *            beginString
     * @param shift
     *            负数表示向前,整数表示向后,0表示依旧从自己的位置开始算起
     * @return 如果 <code>text</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>beginString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 text.indexOf(beginString)==-1,返回 {@link StringUtils#EMPTY}<br>
     *         如果{@code  beginIndex + shift < 0},抛出 {@link IllegalArgumentException}<br>
     *         如果{@code  beginIndex + shift > text.length()},返回 {@link StringUtils#EMPTY}<br>
     *         否则返回 text.substring(beginIndex + shift)<br>
     * @see org.apache.commons.lang3.StringUtils#substringAfter(String, String)
     */
    public static String substring(final String text,String beginString,int shift){
        if (isNullOrEmpty(text) || isNullOrEmpty(beginString)){
            return EMPTY;
        }

        int beginIndex = text.indexOf(beginString);
        if (beginIndex == StringUtils.INDEX_NOT_FOUND){// 查不到指定的字符串
            return EMPTY;
        }
        //****************************************************
        int startIndex = beginIndex + shift;
        Validate.isTrue(startIndex >= 0, Slf4jUtil.format("[{}] index[{}]+shift[{}]<0,text[{}]", beginString, beginIndex, shift, text));

        int textLength = text.length();
        if (startIndex > textLength){
            LOGGER.warn("beginString [{}] index[{}]+shift[{}]>text[{}].length()[{}]", beginString, beginIndex, shift, text, textLength);
            return EMPTY;
        }
        return text.substring(startIndex);// 索引从0开始
    }

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
     * 带有数字格式的数字字符串,与数字相加(一般生成流水号使用).
     * 
     * <pre class="code">
     * StringUtil.stringAddInt("002",2)            =   004
     * StringUtil.stringAddInt("000002",1200)      =   001202
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
        String pattern = "";
        for (int j = 0, z = str.length(); j < z; ++j){
            pattern += "0";
        }
        return NumberUtil.toString(Integer.parseInt(str) + i, pattern);
    }

    /**
     * String add int.
     */
    @Test
    public void stringAddInt(){
        assertEquals("004", stringAddInt("002", 2));
        assertEquals("001202", stringAddInt("000002", 1200));
    }

    /**
     * Adds the double quotes.
     */
    @Test
    public void addDoubleQuotes(){
        assertEquals("\"" + "jinxin.feilong" + "\"", addDoubleQuotes("jinxin.feilong"));
    }
}
