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

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.DatePattern;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.date.DateUtil;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class StringUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class StringUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilTest.class);

    /** <code>{@value}</code>. */
    private static final String TEXT   = "jinxin.feilong";

    /**
     * String add int.
     */
    @Test
    public void stringAddInt(){
        assertEquals("004", StringUtil.stringAddInt("002", 2));
        assertEquals("001202", StringUtil.stringAddInt("000002", 1200));
    }

    /**
     * Test join null.
     */
    @Test
    public void testJoinNull(){
        // assertEquals("", "" + null);
    }

    /**
     * Test contains.
     */
    @Test
    public void testContains(){
        assertEquals(false, StringUtils.contains(null, ""));
        assertEquals(true, StringUtils.contains("", ""));
        assertEquals(true, StringUtils.contains("jiiiiiinxin.feilong", "xin"));
    }

    /**
     * Search count.
     */
    @Test
    public void testReplace(){
        String source = "jiiiiiinxin.feilong";
        LOGGER.debug(StringUtil.replace(source, null) + "");

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("today", DateUtil.toString(new Date(), DatePattern.COMMON_DATE));
        valuesMap.put("user", new User(1L));
        LOGGER.debug(StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap) + "");
    }

    /**
     * Test replace1.
     */
    @Test
    public void testReplace1(){
        assertEquals("", StringUtil.replace(null, null));
    }

    /**
     * Test replace2.
     */
    @Test
    public void testReplace2(){
        String template = "/home/webuser/expressdelivery/${yearMonth}/${expressDeliveryType}/vipQuery_${fileName}.log";

        Date date = new Date();
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("yearMonth", DateUtil.toString(date, DatePattern.YEAR_AND_MONTH));
        valuesMap.put("expressDeliveryType", "sf");
        valuesMap.put("fileName", DateUtil.toString(date, DatePattern.TIMESTAMP));
        LOGGER.debug(StringUtil.replace(template, valuesMap));

        assertEquals(template, StringUtil.replace(template, null));

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
     * Search count.
     */
    @Test
    public void testSearchCount(){
        String source = "jiiiiiinxin.feilong";
        assertEquals(8, StringUtils.countMatches(source, "i"));
        assertEquals(2, StringUtils.countMatches(source, "in"));
        assertEquals(3, StringUtils.countMatches(source, "ii"));
        assertEquals(1, StringUtils.countMatches(source, "xin"));
        assertEquals(1, StringUtils.countMatches("xin", "xin"));
        assertEquals(1, StringUtils.countMatches("xin", "i"));
        assertEquals(2, StringUtils.countMatches("xiin", "i"));
        assertEquals(2, StringUtils.countMatches("xiiiin", "ii"));
    }

    /**
     * Compare to.
     */
    @Test
    public void compareTo(){
        LOGGER.debug("" + "8".compareTo("13"));
        Integer parseInt = Integer.parseInt("8");
        LOGGER.debug("" + parseInt.compareTo(Integer.parseInt("13")));
        LOGGER.debug("" + "12".compareTo("13"));
    }

    /**
     * Adds the double quotes.
     */
    @Test
    public void addDoubleQuotes(){
        assertEquals("\"" + "jinxin.feilong" + "\"", StringUtil.addDoubleQuotes(TEXT));
    }

    /**
     * Checks if is contain ignore case.
     */
    @Test
    public void testContainsIgnoreCase(){
        assertEquals(false, StringUtil.containsIgnoreCase(null, ""));
        LOGGER.debug(StringUtil.containsIgnoreCase(TEXT, null) + "");
        LOGGER.debug(StringUtil.containsIgnoreCase(TEXT, "") + "");
        LOGGER.debug(StringUtil.containsIgnoreCase(TEXT, "feilong") + "");
        LOGGER.debug(StringUtil.containsIgnoreCase(TEXT, "feilong1") + "");
        LOGGER.debug(StringUtil.containsIgnoreCase(TEXT, "feiLong") + "");
        assertEquals(true, StringUtil.containsIgnoreCase("jiiiiiinxin.feilong", "Xin"));
    }

    /**
     * Format.
     */
    @Test
    public void format(){
        LOGGER.debug(StringUtil.format("%03d", 1));
        LOGGER.debug(StringUtil.format("%s%n%s%h", 1.2d, 2, "feilong"));
        LOGGER.debug(StringUtil.format("%+d", -5));
        LOGGER.debug(StringUtil.format("%-5d", -5));
        LOGGER.debug(StringUtil.format("%05d", -5));
        LOGGER.debug(StringUtil.format("% 5d", -5));
        LOGGER.debug(StringUtil.format("%,f", 5585458.254f));
        LOGGER.debug(StringUtil.format("%(f", -5585458.254f));
        LOGGER.debug(StringUtil.format("%#f", -5585458.254f));
        LOGGER.debug(StringUtil.format("%f和%<3.3f", 9.45));
        LOGGER.debug(StringUtil.format("%2$s,%1$s", 99, "abc"));
        LOGGER.debug(StringUtil.format("%1$s,%1$s", 99));
    }

    /**
     * Format1.
     */
    @Test
    public void format1(){
        Date date = new Date();
        LOGGER.debug(String.format("The date: %tY-%tm-%td", date, date, date));
        LOGGER.debug(String.format("The date: %1$tY-%1$tm-%1$td", date));
        LOGGER.debug(String.format("Time with tz: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL%1$tz", date));
    }

    /**
     * Replace.
     */
    @Test
    public void replace(){
        assertEquals("黑色_黄色_蓝色", StringUtil.replace("黑色/黄色/蓝色", "/", "_"));
    }

    /**
     * Replace all.
     */
    @Test
    public void replaceAll(){
        assertEquals("黑色_黄色_蓝色", StringUtil.replaceAll("黑色/黄色/蓝色", "/", "_"));
        LOGGER.debug(StringUtil.replaceAll("SH1265,SH5951", "([a-zA-Z]+[0-9]+)", "'$1'"));
        LOGGER.debug(StringUtil.replace("SH1265,SH5951", "([a-zA-Z]+[0-9]+)", "'$1'"));
        LOGGER.debug("SH1265,SH5951".replaceFirst("([a-zA-Z]+[0-9]+)", "'$1'"));
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
        LOGGER.debug(sb.toString());
    }

    /**
     * Test sub string.
     */
    @Test
    public void testSubString(){
        LOGGER.debug(StringUtil.substring(TEXT, "i", "g"));
        LOGGER.debug(StringUtils.substringBetween(TEXT, "i", "g"));
    }

    /**
     * Substring2.
     */
    @Test
    public void substring2(){
        assertEquals(null, StringUtil.substring(null, 6, 8));
        LOGGER.debug(StringUtil.substring(TEXT, TEXT.length(), 8));
        LOGGER.debug(StringUtil.substring(TEXT, TEXT.length() - 1, 8));
        LOGGER.debug(StringUtil.substring(TEXT, 1, 0));
        LOGGER.debug(StringUtil.substring(TEXT, 0, 5));
        assertEquals(".f", StringUtil.substring(TEXT, 6, 2));
        LOGGER.debug(StringUtil.substring(TEXT, 6, 20));
    }

    /**
     * Substring3.
     */
    @Test
    public void substring3(){
        LOGGER.debug(StringUtil.substring(null, "in", 8));
        LOGGER.debug(StringUtil.substring(TEXT, null, 8));
        LOGGER.debug(StringUtil.substring(TEXT, "sin", 8));
        LOGGER.debug(StringUtil.substring(TEXT, "in", 0));
        LOGGER.debug(StringUtil.substring(TEXT, "in", 5));
        // LOGGER.debug(StringUtil.substring(text, "in", -2));
        LOGGER.debug(StringUtil.substring(TEXT, "j", TEXT.length() - 1));
        LOGGER.debug(StringUtil.substring(TEXT, "jinxin.", 1));
    }

    /**
     * Substring33.
     */
    @Test
    public void substring33(){
        LOGGER.debug(StringUtil.substring(TEXT, "in", 20));
    }

    /**
     * Substring333.
     */
    @Test(expected = IllegalArgumentException.class)
    public void substring333(){
        StringUtil.substring(TEXT, "in", -200);
    }

    /**
     * Test substring3.
     */
    @Test
    public void testSubstring3(){
        LOGGER.debug(StringUtil.substring(null, "in"));
        LOGGER.debug(StringUtil.substring(TEXT, null));
        LOGGER.debug(StringUtil.substring(TEXT, "sin"));
        LOGGER.debug(StringUtil.substring(TEXT, "."));
        LOGGER.debug(StringUtil.substring(TEXT, "j"));
        LOGGER.debug(StringUtil.substring(TEXT, "jinxin."));
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
        assertEquals("jinxin.feilong", StringUtil.substringWithoutLast("jinxin.feilong     ", "     "));
        assertEquals(TEXT, StringUtil.substringWithoutLast(TEXT, ""));
        assertEquals(TEXT, StringUtil.substringWithoutLast(TEXT, null));
        assertEquals("", StringUtil.substringWithoutLast(null, "222"));
    }

    /**
     * Tokenize to string array2.
     */
    @Test
    public void tokenizeToStringArray2(){
        String str = "jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong";
        String delimiters = "h";
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray(str, delimiters, false, false);
        LOGGER.debug(JsonUtil.format(tokenizeToStringArray));
    }

    /**
     * Tokenize to string array1.
     */
    @Test
    public void tokenizeToStringArray1(){
        String str = "jin.xin  feilong ,jinxin;venusdrogon;jim ";
        String delimiters = ";, .";
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray(str, delimiters);
        LOGGER.debug(JsonUtil.format(tokenizeToStringArray));
    }
}