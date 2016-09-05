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
package com.feilong.core.net.paramutiltest;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.ParamUtil;

import static com.feilong.core.CharsetType.UTF8;

/**
 * The Class ParamUtilAddParameterSingleValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ParamUtilAddParameterSingleValueMapTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtilAddParameterSingleValueMapTest.class);

    /**
     * Test add parameter single value map.
     */
    @Test
    public void testAddParameterSingleValueMap(){
        String beforeUrl = "www.baidu.com";
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.debug(ParamUtil.addParameterSingleValueMap(beforeUrl, singleValueMap, UTF8));
    }

    /**
     * Test add parameter single value map2.
     */
    @Test
    public void testAddParameterSingleValueMap2(){
        String beforeUrl = "www.baidu.com?a=b";
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.debug(ParamUtil.addParameterSingleValueMap(beforeUrl, singleValueMap, UTF8));
    }

    /**
     * Test add parameter single value map null uri string.
     */
    @Test
    public void testAddParameterSingleValueMapNullUriString(){
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");
        assertEquals(EMPTY, ParamUtil.addParameterSingleValueMap(null, singleValueMap, UTF8));
    }

    /**
     * Test add parameter single value map empty uri string.
     */
    @Test
    public void testAddParameterSingleValueMapEmptyUriString(){
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");
        assertEquals(EMPTY, ParamUtil.addParameterSingleValueMap("", singleValueMap, UTF8));
    }
}
