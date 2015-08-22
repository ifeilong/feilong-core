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

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.test.User;

/**
 * The Class StringUtilTest.
 * 
 * @author feilong
 * @version 1.0 2011-1-7 下午02:41:08
 */
public class StringUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilTest.class);

    /** <code>{@value}</code>. */
    private static final String TEXT   = "jinxin.feilong";

    /**
     * Length.
     */
    @Test
    public void length(){
        String string = "我的新浪微博:http://weibo.com/venusdrogon,关注我哦[url=http://bbs.guqu.net/Query.asp?keyword=%B6%C5%B4%CF%D7%A8%BC%AD&boardid=0&sType=2]sssss[/url][url=http://weibo.com/venusdrogon][img]http://service.t.sina.com.cn/widget/qmd/1903991210/1c853142/5.png[/img][/url]";
        LOGGER.info(string.length() + "");

        LOGGER.info("572367774882343".length() + "");

        // 运单号
        LOGGER.info("1900681807840".length() + "");
        LOGGER.info("3999e85461ce7271dd5292c88f18567e".length() + "");
    }

    /**
     * String add int.
     */
    @Test
    public void stringAddInt(){
        assertEquals("004", StringUtil.stringAddInt("002", 2));
        assertEquals("001202", StringUtil.stringAddInt("000002", 1200));
    }

    /**
     * Test contains.
     */
    @Test
    public void testContains(){
        assertEquals(false, StringUtils.contains(null, ""));
        assertEquals(true, StringUtils.contains("", ""));
        assertEquals(true, StringUtil.contains("jiiiiiinxin.feilong", "xin"));
    }

    /**
     * Search count.
     */
    @Test
    public void testReplace(){
        String source = "jiiiiiinxin.feilong";
        LOGGER.info(StringUtil.replace(source, null) + "");

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("today", DateUtil.date2String(new Date(), DatePattern.COMMON_DATE));
        valuesMap.put("user", new User(1L));
        LOGGER.info(StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap) + "");
    }

    /**
     * Search count.
     */
    @Test
    public void testSearchCount(){
        String source = "jiiiiiinxin.feilong";
        assertEquals(8, StringUtil.searchTimes(source, "i"));
        assertEquals(2, StringUtil.searchTimes(source, "in"));
        assertEquals(3, StringUtil.searchTimes(source, "ii"));
        assertEquals(1, StringUtil.searchTimes(source, "xin"));

        assertEquals(1, StringUtil.searchTimes("xin", "xin"));
        assertEquals(1, StringUtil.searchTimes("xin", "i"));
        assertEquals(2, StringUtil.searchTimes("xiin", "i"));
        assertEquals(2, StringUtil.searchTimes("xiiiin", "ii"));
    }

    /**
     * Compare to.
     */
    @Test
    public void compareTo(){
        LOGGER.info("" + "8".compareTo("13"));
        Integer parseInt = Integer.parseInt("8");
        LOGGER.info("" + parseInt.compareTo(Integer.parseInt("13")));
        LOGGER.info("" + "12".compareTo("13"));
    }

    /**
     * Adds the double quotes.
     */
    @Test
    public void addDoubleQuotes(){
        LOGGER.info(StringUtil.addDoubleQuotes(TEXT));
    }

    /**
     * Checks if is contain ignore case.
     */
    @Test
    public void testContainsIgnoreCase(){
        LOGGER.info(StringUtil.containsIgnoreCase(null, "") + "");
        LOGGER.info(StringUtil.containsIgnoreCase(TEXT, null) + "");
        LOGGER.info(StringUtil.containsIgnoreCase(TEXT, "") + "");
        LOGGER.info(StringUtil.containsIgnoreCase(TEXT, "feilong") + "");
        LOGGER.info(StringUtil.containsIgnoreCase(TEXT, "feilong1") + "");
        LOGGER.info(StringUtil.containsIgnoreCase(TEXT, "feiLong") + "");
        assertEquals(true, StringUtil.containsIgnoreCase("jiiiiiinxin.feilong", "Xin"));
    }

    /**
     * To hex string upper case.
     */
    @Test
    public void toHexStringUpperCase(){
        LOGGER.info(StringUtil.toHexStringUpperCase(TEXT, CharsetType.UTF8));
    }

    /**
     * To original.
     */
    @Test
    public void toOriginal(){
        String hexStringUpperCase = "5B7B2264617465223A313333343037323035323038312C2273696D706C65536B75436F6D6D616E64223A7B22636F6465223A223331373830392D313030222C22666F625069726365223A323139392C226964223A353636372C226C6973745072696365223A323139392C226E616D65223A2241495220464F52434520312048494748204C5558204D4158204149522027303820515320E7A9BAE5869BE4B880E58FB7EFBC88E99990E9878FE58F91E594AEEFBC89227D7D5D";
        hexStringUpperCase = "5B7B22636F6465223A224B3034383031222C226964223A3730302C226E616D65223A22E697B6E5B09AE6ACBEE992A5E58C99E689A3227D2C7B22636F6465223A2231333433363143222C226964223A35362C226E616D65223A22E58AB2E985B7E688B7E5A496436875636B205461796C6F7220416C6C2053746172204261636B205A6970227D5D";
        byte[] hexBytesToBytes = ByteUtil.hexBytesToBytes(hexStringUpperCase.getBytes());
        LOGGER.info(new String(hexBytesToBytes));
        LOGGER.info(StringUtil.toOriginal(hexStringUpperCase, CharsetType.UTF8));
    }

    /**
     * Format.
     */
    @Test
    public void format(){
        LOGGER.info(StringUtil.format("%03d", 1));
        LOGGER.info(StringUtil.format("%s%n%s%h", 1.2d, 2, "feilong"));
        LOGGER.info(StringUtil.format("%+d", -5));
        LOGGER.info(StringUtil.format("%-5d", -5));
        LOGGER.info(StringUtil.format("%05d", -5));
        LOGGER.info(StringUtil.format("% 5d", -5));
        LOGGER.info(StringUtil.format("%,f", 5585458.254f));
        LOGGER.info(StringUtil.format("%(f", -5585458.254f));
        LOGGER.info(StringUtil.format("%#f", -5585458.254f));
        LOGGER.info(StringUtil.format("%f和%<3.3f", 9.45));
        LOGGER.info(StringUtil.format("%2$s,%1$s", 99, "abc"));
        LOGGER.info(StringUtil.format("%1$s,%1$s", 99));
    }

    /**
     * Replace.
     */
    @Test
    public void replace(){
        String target = "/";
        Object replacement = "_";
        LOGGER.info(StringUtil.replace("黑色/黄色/蓝色", target, replacement));
    }

    /**
     * Replace all.
     */
    @Test
    public void replaceAll(){
        String target = "/";
        String replacement = "_";
        LOGGER.info(StringUtil.replaceAll("黑色/黄色/蓝色", target, replacement));
    }

    /**
     * Test sub string.
     */
    @Test
    public void testSubString(){
        LOGGER.info(StringUtil.substring(TEXT, "i", "g"));
        LOGGER.info(StringUtils.substringBetween(TEXT, "i", "g"));
    }

    /**
     * Substring2.
     */
    @Test
    public void substring2(){
        LOGGER.info(StringUtil.substring(null, 6, 8));
        LOGGER.info(StringUtil.substring(TEXT, TEXT.length(), 8));
        LOGGER.info(StringUtil.substring(TEXT, TEXT.length() - 1, 8));
        LOGGER.info(StringUtil.substring(TEXT, 1, 0));
        LOGGER.info(StringUtil.substring(TEXT, 0, 5));
        Assert.assertEquals(".f", StringUtil.substring(TEXT, 6, 2));
        LOGGER.info(StringUtil.substring(TEXT, 6, 20));
    }

    /**
     * Substring3.
     */
    @Test
    public void substring3(){
        LOGGER.info(StringUtil.substring(null, "in", 8));
        LOGGER.info(StringUtil.substring(TEXT, null, 8));
        LOGGER.info(StringUtil.substring(TEXT, "sin", 8));
        LOGGER.info(StringUtil.substring(TEXT, "in", 0));
        LOGGER.info(StringUtil.substring(TEXT, "in", 5));
        // LOGGER.info(StringUtil.substring(text, "in", -2));
        LOGGER.info(StringUtil.substring(TEXT, "in", 20));
        LOGGER.info(StringUtil.substring(TEXT, "j", TEXT.length() - 1));
        LOGGER.info(StringUtil.substring(TEXT, "jinxin.", 1));
    }

    /**
     * Test substring3.
     */
    @Test
    public void testSubstring3(){
        LOGGER.info(StringUtil.substring(null, "in"));
        LOGGER.info(StringUtil.substring(TEXT, null));
        LOGGER.info(StringUtil.substring(TEXT, "sin"));
        LOGGER.info(StringUtil.substring(TEXT, "."));
        LOGGER.info(StringUtil.substring(TEXT, "j"));
        LOGGER.info(StringUtil.substring(TEXT, "jinxin."));
    }

    /**
     * Substring.
     */
    @Test
    public void substring(){
        assertEquals(
                        "src/main/java/com/jumbo/shop/web/command/PageCacheCommand.java",
                        StringUtil.substring("Index: src/main/java/com/jumbo/shop/web/command/PageCacheCommand.java", "Index: ".length()));
        assertEquals(".feilong", StringUtil.substring(TEXT, "jinxin".length()));
        assertEquals(".feilong", StringUtil.substring(TEXT, 6));
        assertEquals("ng", StringUtil.substring(TEXT, -2));
    }

    /**
     * Test substring last.
     */
    @Test
    public void testSubstringLast(){
        assertEquals("ilong", StringUtil.substringLast(TEXT, 5));
    }

    /**
     * Test substring without last.
     */
    @Test
    public void testSubstringWithoutLast(){
        assertEquals("jinxin.fe", StringUtil.substringWithoutLast(TEXT, 5));
    }

    /**
     * Test substring without last.
     */
    @Test
    public void testSubstringWithoutLast2(){
        assertEquals("jinxin.feilo", StringUtil.substringWithoutLast(TEXT, "ng"));
        assertEquals(TEXT, StringUtil.substringWithoutLast(TEXT, ""));
        assertEquals(TEXT, StringUtil.substringWithoutLast(TEXT, null));
        assertEquals("", StringUtil.substringWithoutLast(null, ""));
    }

    /**
     * Test replace all.
     */
    @Test
    public void testReplaceAll(){
        LOGGER.info("SH1265,SH5951,SH6766,SH7235,SH1265,SH5951,SH6766,SH7235".replaceAll("([a-zA-Z]+[0-9]+)", "'$1'"));
    }

    /**
     * 分隔字符串并添加引号.
     */
    @Test
    public void splitAndAddYinHao(){
        String a = "12345,56789,1123456";
        String[] aStrings = a.split(",");
        StringBuilder sb = new StringBuilder();
        int size = aStrings.length;
        for (int i = 0; i < size; i++){
            sb.append("'" + aStrings[i] + "'");
            if (i != size - 1){
                sb.append(",");
            }
        }
        LOGGER.info(sb.toString());
    }

    /**
     * 返回一个随机的字符串。150是基于该程序使用场景的抽样得到的长度。.
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

    /**
     * Tokenize to string array2.
     */
    @Test
    public void tokenizeToStringArray2(){
        String str = "jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong";
        String delimiters = "h";
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray(str, delimiters, false, false);
        LOGGER.info(JsonUtil.format(tokenizeToStringArray));
    }

    /**
     * Tokenize to string array.
     */
    @Test
    public void tokenizeToStringArray(){
        String str = "jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1; ++i){
            sb.append(str);
        }
        str = sb.toString();
        String delimiters = ";, .";
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray(str, delimiters);
        LOGGER.info(JsonUtil.format(tokenizeToStringArray));
        Date beginDate = new Date();

        for (int i = 0; i < 100000; ++i){
            StringUtil.tokenizeToStringArray(str, delimiters);
        }

        Date endDate = new Date();
        LOGGER.info("time:{}", DateUtil.getIntervalTime(beginDate, endDate));
    }

    /**
     * Split to string array.
     */
    @Test
    public void splitToStringArray(){
        String str = "jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1; ++i){
            sb.append(str);
        }
        str = sb.toString();

        String regexSpliter = "[;, \\.]";
        String[] tokenizeToStringArray = StringUtil.split(str, regexSpliter);
        LOGGER.info(JsonUtil.format(tokenizeToStringArray));

        Date beginDate = new Date();

        for (int i = 0; i < 100000; ++i){
            StringUtil.split(str, regexSpliter);
        }

        Date endDate = new Date();
        LOGGER.info("time:{}", DateUtil.getIntervalTime(beginDate, endDate));
    }

    /**
     * 查找子字符串在 字符串中出现的次数.
     * 
     * <pre>
     *  StringUtil.searchTimes("xin", "xin")
     *  return  1
     *  
     * StringUtil.searchTimes("xiiiin", "ii")
     *  return  2
     * 
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
    public static int searchTimes(String source,String target){
        if (null == source){
            throw new IllegalArgumentException("source can't be null!");
        }
        if (null == target){
            throw new IllegalArgumentException("target can't be null!");
        }
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
            if (i != -1){
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
}
