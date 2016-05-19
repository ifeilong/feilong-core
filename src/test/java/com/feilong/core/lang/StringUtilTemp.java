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

import java.util.Random;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class StringUtilTest.
 * 
 * @author feilong
 * @version 1.0 2011-1-7 下午02:41:08
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
        LOGGER.info(string.length() + "");

        // 运单号
        LOGGER.info("3999e85461ce7271dd5292c88f18567e".length() + "");
    }

    /**
     * 查找子字符串在 字符串中出现的次数.
     * 
     * <pre class="code">
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
}
