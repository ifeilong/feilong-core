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

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DateUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class StringUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class StringUtilPerformanceTest2{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilPerformanceTest2.class);

    /**
     * Tokenize to string array.
     */
    @Test
    public void tokenizeToStringArray(){
        Date beginDate = new Date();

        for (int i = 0; i < 100000; ++i){
            tokenizeToStringArray1();
        }

        Date endDate = new Date();
        LOGGER.info("time:{}", DateUtil.getIntervalTime(beginDate, endDate));
    }

    /**
     * Tokenize to string array1.
     */
    @Test
    public void tokenizeToStringArray1(){
        String str = "jin.xin  feilong ,jinxin;venusdrogon;jim ";
        String delimiters = ";, .";
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray(str, delimiters);
        LOGGER.info(JsonUtil.format(tokenizeToStringArray));
    }

    /**
     * Split to string array.
     */
    @Test
    public void splitToStringArray(){
        String str = "jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong;jin.xin  h hhaha ,lala;feilong";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; ++i){
            sb.append(str);
        }
        str = sb.toString();

        String regexSpliter = "[;, \\.]";
        String[] tokenizeToStringArray = StringUtil.split(str, regexSpliter);
        LOGGER.info(JsonUtil.format(tokenizeToStringArray));

        Date beginDate = new Date();

        for (int i = 0; i < 1; ++i){
            StringUtil.split(str, regexSpliter);
        }

        Date endDate = new Date();
        LOGGER.info("time:{}", DateUtil.getIntervalTime(beginDate, endDate));
    }
}
