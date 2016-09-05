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
 * The Class ParamUtilAddParameterArrayValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ParamUtilAddParameterArrayValueMapTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtilAddParameterArrayValueMapTest.class);

    /**
     * Gets the encoded url by array map.
     * 
     */
    @Test
    public void testGetEncodedUrlByArrayMap(){
        String beforeUrl = "www.baidu.com";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();
        keyAndArrayMap.put("a", new String[] { "aaaa", "bbbb" });
        keyAndArrayMap.put("name", new String[] { "aaaa", "bbbb" });
        keyAndArrayMap.put("pa", new String[] { "aaaa" });

        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map1.
     */
    @Test
    public void testGetEncodedUrlByArrayMap1(){
        String beforeUrl = "www.baidu.com";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("receiver", new String[] { "鑫哥", "feilong" });
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map2.
     */
    @Test
    public void testGetEncodedUrlByArrayMap2(){
        String beforeUrl = "www.baidu.com?a=b";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    //*******************************************************************************

    /**
     * Test get encoded url by array map null uri.
     */
    @Test
    public void testGetEncodedUrlByArrayMapNullUri(){
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals(EMPTY, ParamUtil.addParameterArrayValueMap(null, keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map empty uri.
     */
    @Test
    public void testGetEncodedUrlByArrayMapEmptyUri(){
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals(EMPTY, ParamUtil.addParameterArrayValueMap("", keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map null map.
     */
    @Test
    public void testGetEncodedUrlByArrayMapNullMap(){
        String beforeUrl = "www.baidu.com?a=b";
        assertEquals(beforeUrl, ParamUtil.addParameterArrayValueMap(beforeUrl, null, UTF8));
    }

    /**
     * Test get encoded url by array map empty map.
     */
    @Test
    public void testGetEncodedUrlByArrayMapEmptyMap(){
        String beforeUrl = "www.baidu.com?a=b";
        assertEquals(beforeUrl, ParamUtil.addParameterArrayValueMap(beforeUrl, new HashMap<String, String[]>(), UTF8));
    }
}
