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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.ParamUtil;

import static com.feilong.core.CharsetType.UTF8;

/**
 * The Class ParamUtilToSafeQueryStringTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ParamUtilToSafeQueryStringTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtilToSafeQueryStringTest.class);

    /**
     * Combine query string.
     */
    @Test
    public void testToSafeQueryString(){
        Map<String, String[]> keyAndArrayMap = new HashMap<>();
        keyAndArrayMap.put("name", new String[] { "jim", "feilong", "鑫哥" });
        keyAndArrayMap.put("age", new String[] { "18" });
        keyAndArrayMap.put("love", new String[] { "sanguo" });
        LOGGER.debug(ParamUtil.toSafeQueryString(keyAndArrayMap, UTF8));
        LOGGER.debug(ParamUtil.toSafeQueryString(keyAndArrayMap, null));
    }

    /**
     * Test to safe query string null charset type.
     */
    @Test
    public void testToSafeQueryStringNullCharsetType(){
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<>();
        keyAndArrayMap.put("name", new String[] { "jim", "feilong", "鑫哥" });
        keyAndArrayMap.put("age", new String[] { "18" });
        keyAndArrayMap.put("love", new String[] { "sanguo" });

        assertEquals("name=jim&name=feilong&name=鑫哥&age=18&love=sanguo", ParamUtil.toSafeQueryString(keyAndArrayMap, null));
    }

    /**
     * Test to safe query string null map.
     */
    @Test
    public void testToSafeQueryStringNullMap(){
        assertEquals(EMPTY, ParamUtil.toSafeQueryString(null, UTF8));
        assertEquals(EMPTY, ParamUtil.toSafeQueryString(null, null));
    }

    /**
     * Test to safe query string empty map.
     */
    @Test
    public void testToSafeQueryStringEmptyMap(){
        assertEquals(EMPTY, ParamUtil.toSafeQueryString(new HashMap<String, String[]>(), UTF8));
    }
}
