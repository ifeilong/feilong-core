/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.tools.json.JsonUtil;
import com.feilong.test.User;

/**
 * The Class StringUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-1-7 下午02:41:08
 */
public class StringUtilTest{

    /** The Constant log. */
    private static final Logger log  = LoggerFactory.getLogger(StringUtilTest.class);

    /** <code>{@value}</code>. */
    private static final String TEXT = "jinxin.feilong";

    /**
     * Search count.
     */
    @Test
    public void testReplace(){
        String source = "jiiiiiinxin.feilong";
        log.info(StringUtil.replace(source, null) + "");

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("today", DateUtil.date2String(new Date(), DatePattern.COMMON_DATE));
        valuesMap.put("user", new User(1L));
        log.info(StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap) + "");
    }

    /**
     * Search count.
     */
    @Test
    public void testSearchCount(){
        String source = "jiiiiiinxin.feilong";
        log.info(StringUtil.searchTimes(source, "i") + "");
        log.info(StringUtils.countMatches(source, "i") + "");
        log.info(StringUtil.searchTimes(source, "in") + "");
        log.info(StringUtil.searchTimes(source, "ii") + "");
        log.info(StringUtil.searchTimes(source, "xin") + "");
        Assert.assertEquals(1, StringUtil.searchTimes("xin", "xin"));
        Assert.assertEquals(1, StringUtil.searchTimes("xin", "i"));
        Assert.assertEquals(2, StringUtil.searchTimes("xiin", "i"));
        Assert.assertEquals(2, StringUtil.searchTimes("xiiiin", "ii"));
    }

    /**
     * Compare to.
     */
    @Test
    public void compareTo(){
        log.info("" + "8".compareTo("13"));

        final Integer parseInt = Integer.parseInt("8");

        log.info("" + parseInt.compareTo(Integer.parseInt("13")));
        log.info("" + "12".compareTo("13"));
    }

    /**
     * Adds the double quotes.
     */
    @Test
    public void addDoubleQuotes(){
        log.info(StringUtil.addDoubleQuotes(TEXT));
    }

    /**
     * Checks if is contain ignore case.
     */
    @Test
    public void isContainIgnoreCase(){
        log.info(StringUtil.isContainIgnoreCase(null, "") + "");
        log.info(StringUtil.isContainIgnoreCase(TEXT, null) + "");
        log.info(StringUtil.isContainIgnoreCase(TEXT, "") + "");
        log.info(StringUtil.isContainIgnoreCase(TEXT, "feilong") + "");
        log.info(StringUtil.isContainIgnoreCase(TEXT, "feilong1") + "");
        log.info(StringUtil.isContainIgnoreCase(TEXT, "feiLong") + "");
    }

    /**
     * To hex string upper case.
     */
    @Test
    public void toHexStringUpperCase(){
        log.info(StringUtil.toHexStringUpperCase(TEXT));
    }

    /**
     * To original.
     */
    @Test
    public void toOriginal(){
        String hexStringUpperCase = "5B7B2264617465223A313333343037323035323038312C2273696D706C65536B75436F6D6D616E64223A7B22636F6465223A223331373830392D313030222C22666F625069726365223A323139392C226964223A353636372C226C6973745072696365223A323139392C226E616D65223A2241495220464F52434520312048494748204C5558204D4158204149522027303820515320E7A9BAE5869BE4B880E58FB7EFBC88E99990E9878FE58F91E594AEEFBC89227D7D5D";
        hexStringUpperCase = "5B7B22636F6465223A224B3034383031222C226964223A3730302C226E616D65223A22E697B6E5B09AE6ACBEE992A5E58C99E689A3227D2C7B22636F6465223A2231333433363143222C226964223A35362C226E616D65223A22E58AB2E985B7E688B7E5A496436875636B205461796C6F7220416C6C2053746172204261636B205A6970227D5D";
        byte[] hexBytesToBytes = ByteUtil.hexBytesToBytes(hexStringUpperCase.getBytes());
        String msg = new String(hexBytesToBytes);
        log.info(msg);
        msg = StringUtil.toOriginal(hexStringUpperCase);
        log.info(msg);
    }

    /**
     * Length.
     */
    @Test
    public void length(){
        String string = "我的新浪微博:http://weibo.com/venusdrogon,关注我哦[url=http://bbs.guqu.net/Query.asp?keyword=%B6%C5%B4%CF%D7%A8%BC%AD&boardid=0&sType=2]sssss[/url][url=http://weibo.com/venusdrogon][img]http://service.t.sina.com.cn/widget/qmd/1903991210/1c853142/5.png[/img][/url]";
        log.info(string.length() + "");

        log.info("572367774882343".length() + "");

        // 运单号
        log.info("1900681807840".length() + "");
    }

    /**
     * Format.
     */
    @Test
    public void format(){
        log.info(StringUtil.format("%03d", 1));
        log.info(StringUtil.format("%s%n%s%h", 1.2d, 2, "feilong"));
        log.info(StringUtil.format("%+d", -5));
        log.info(StringUtil.format("%-5d", -5));
        log.info(StringUtil.format("%05d", -5));
        log.info(StringUtil.format("% 5d", -5));
        log.info(StringUtil.format("%,f", 5585458.254f));
        log.info(StringUtil.format("%(f", -5585458.254f));
        log.info(StringUtil.format("%#f", -5585458.254f));
        log.info(StringUtil.format("%f和%<3.3f", 9.45));
        log.info(StringUtil.format("%2$s,%1$s", 99, "abc"));
        log.info(StringUtil.format("%1$s,%1$s", 99));
    }

    /**
     * Replace.
     */
    @Test
    public void replace(){
        Object content = "黑色/黄色/蓝色";
        String target = "/";
        Object replacement = "_";
        log.info(StringUtil.replace(content, target, replacement));
    }

    /**
     * Replace all.
     */
    @Test
    public void replaceAll(){
        Object content = "黑色/黄色/蓝色";
        String target = "/";
        String replacement = "_";
        log.info(StringUtil.replaceAll(content, target, replacement));
    }

    /**
     * Substring1.
     */
    @Test
    public void substring1(){
        log.info("3999e85461ce7271dd5292c88f18567e".length() + "");
        log.info(StringUtil.substring(TEXT, 6));
    }

    /**
     * Substring2.
     */
    @Test
    public void substring2(){
        log.info(StringUtil.substring(null, 6, 8));
        log.info(StringUtil.substring(TEXT, TEXT.length(), 8));
        log.info(StringUtil.substring(TEXT, TEXT.length() - 1, 8));
        log.info(StringUtil.substring(TEXT, 1, 0));
        log.info(StringUtil.substring(TEXT, 0, 5));
        Assert.assertEquals(".f", StringUtil.substring(TEXT, 6, 2));
        log.info(StringUtil.substring(TEXT, 6, 20));
    }

    /**
     * Substring3.
     */
    @Test
    public void substring3(){
        log.info(StringUtil.substring(null, "in", 8));
        log.info(StringUtil.substring(TEXT, null, 8));
        log.info(StringUtil.substring(TEXT, "sin", 8));
        log.info(StringUtil.substring(TEXT, "in", 0));
        log.info(StringUtil.substring(TEXT, "in", 5));
        // log.info(StringUtil.substring(text, "in", -2));
        log.info(StringUtil.substring(TEXT, "in", 20));
        log.info(StringUtil.substring(TEXT, "j", TEXT.length() - 1));
    }

    /**
     * Substring.
     */
    @Test
    public void substring(){
        log.info(StringUtil.substring(TEXT, "jinxin".length()));
        String text1 = "Index: src/main/java/com/jumbo/shop/web/command/PageCacheCommand.java";
        log.info(StringUtil.substring(text1, "Index: ".length()));
    }

    /**
     * Substring6.
     */
    @Test
    public void substring6(){
        log.info(StringUtil.substring(TEXT, "jinxin.", 1));
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

    // @Test
    /**
     * Test a.
     */
    public void testA(){
        String a = "SH1265,SH5951,SH6766,SH7235,SH1265,SH5951,SH6766,SH7235";
        log.info(a.replaceAll("([a-zA-Z]+[0-9]+)", "'$1'"));
    }

    /**
     * 分隔字符串并添加引号.
     */
    @Test
    @Ignore
    public void splitAndAddYinHao(){
        String a = "12345,56789,1123456";
        String[] aStrings = a.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        int size = aStrings.length;
        for (int i = 0; i < size; i++){
            stringBuilder.append("'" + aStrings[i] + "'");
            if (i != size - 1){
                stringBuilder.append(",");
            }
        }
        log.info(stringBuilder.toString());
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
        if (log.isInfoEnabled()){
            log.info(JsonUtil.format(tokenizeToStringArray));
        }
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
        if (log.isInfoEnabled()){
            log.info(JsonUtil.format(tokenizeToStringArray));
        }
        Date beginDate = new Date();

        for (int i = 0; i < 100000; ++i){
            StringUtil.tokenizeToStringArray(str, delimiters);
        }

        Date endDate = new Date();
        log.info("time:{}", DateUtil.getIntervalTime(beginDate, endDate));
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
        String[] tokenizeToStringArray = StringUtil.splitToStringArray(str, regexSpliter);
        if (log.isInfoEnabled()){
            log.info(JsonUtil.format(tokenizeToStringArray));
        }

        Date beginDate = new Date();

        for (int i = 0; i < 100000; ++i){
            StringUtil.splitToStringArray(str, regexSpliter);
        }

        Date endDate = new Date();
        log.info("time:{}", DateUtil.getIntervalTime(beginDate, endDate));
    }
}
