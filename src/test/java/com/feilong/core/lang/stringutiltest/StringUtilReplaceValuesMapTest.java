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
package com.feilong.core.lang.stringutiltest;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DateUtil;
import com.feilong.core.lang.StringUtil;
import com.feilong.test.User;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.TIMESTAMP;
import static com.feilong.core.DatePattern.YEAR_AND_MONTH;

/**
 * The Class StringUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class StringUtilReplaceValuesMapTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilReplaceValuesMapTest.class);

    /**
     * Search count.
     */
    @Test
    public void testReplace(){
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("today", DateUtil.toString(new Date(), COMMON_DATE));
        valuesMap.put("user", new User(1L));
        LOGGER.debug(StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap) + "");
    }

    /**
     * Test replace 3.
     */
    @Test
    public void testReplace3(){
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("today", DateUtil.toString(new Date(), COMMON_DATE));
        valuesMap.put("user", 1L);
        LOGGER.debug(StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap) + "");
    }

    /**
     * Test replace22.
     */
    @Test
    public void testReplace22(){
        String source = "jiiiiiinxin.feilong";
        assertEquals(source, StringUtil.replace(source, null));
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
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("yearMonth", DateUtil.toString(date, YEAR_AND_MONTH));
        valuesMap.put("expressDeliveryType", "sf");
        valuesMap.put("fileName", DateUtil.toString(date, TIMESTAMP));
        LOGGER.debug(StringUtil.replace(template, valuesMap));

        assertEquals(template, StringUtil.replace(template, null));
    }
}