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

import static com.feilong.core.util.MapUtil.newHashMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.lang.StringUtil;
import com.feilong.store.member.User;

public class ReplaceValuesMapTest{

    @Test
    public void testReplace(){
        User value = new User(1L);
        Map<String, Object> valuesMap = newHashMap();
        valuesMap.put("today", "2016-11-15");
        valuesMap.put("user", value);

        assertEquals(
                        "2016-11-15${today1}${user.id}" + value.toString(),
                        StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap));
    }

    /**
     * Test replace 3.
     */
    @Test
    public void testReplace3(){
        Map<String, Object> valuesMap = newHashMap();
        valuesMap.put("today", "2016-11-15");
        valuesMap.put("user", 1L);

        assertEquals("2016-11-15${today1}${user.id}1", StringUtil.replace("${today}${today1}${user.id}${user}", valuesMap));
    }

    /**
     * Test replace2.
     */
    @Test
    public void testReplace2(){
        String template = "/home/webuser/expressdelivery/${yearMonth}/${expressDeliveryType}/vipQuery_${fileName}.log";

        Map<String, String> valuesMap = newHashMap();
        valuesMap.put("yearMonth", "2016-11");
        valuesMap.put("expressDeliveryType", "sf");
        valuesMap.put("fileName", "221215151215");

        assertEquals("/home/webuser/expressdelivery/2016-11/sf/vipQuery_221215151215.log", StringUtil.replace(template, valuesMap));
        assertEquals(template, StringUtil.replace(template, null));
    }

    //---------------------------------------------------------------

    @Test
    public void testReplaceNullValuesMap(){
        String source = "jiiiiiinxin.${yearMonth}feilong";
        assertEquals(source, StringUtil.replace(source, null));
    }

    @Test
    public void testReplaceEmptyValuesMap(){
        String source = "jiiiiiinxin.${yearMonth}feilong";
        assertEquals(source, StringUtil.replace(source, new HashMap<String, String>()));
    }

    /**
     * Test replace1.
     */
    @Test
    public void testReplace1(){
        assertEquals(EMPTY, StringUtil.replace(null, null));
    }

    @Test
    public void testReplaceNullTemplateString(){
        assertEquals(EMPTY, StringUtil.replace(null, new HashMap<String, String>()));
    }

    @Test
    public void testReplaceEmptyTemplateString(){
        assertEquals(EMPTY, StringUtil.replace("", new HashMap<String, String>()));
    }
}