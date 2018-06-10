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

import static com.feilong.core.CharsetType.UTF8;
import static com.feilong.core.net.ParamUtil.addParameterArrayValueMap;
import static com.feilong.core.net.URIUtil.encode;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * The Class ParamUtilAddParameterArrayValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AddParameterArrayValueMapTest{

    /** The Constant PATH. */
    private static final String PATH = "www.baidu.com";

    /**
     * Test get encoded url by array map1.
     */
    @Test
    public void testGetEncodedUrlByArrayMapAdd(){
        String beforeUrl = PATH;
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();

        keyAndArrayMap.put("receiver", new String[] { "鑫哥", "feilong" });
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals(
                        Slf4jUtil.format(
                                        PATH + "?receiver={}&receiver={}&province={}&city={}",
                                        encode("鑫哥", UTF8),
                                        encode("feilong", UTF8),
                                        encode("江苏省", UTF8),
                                        encode("南通市", UTF8)),
                        addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map 2.
     */
    @Test
    public void testGetEncodedUrlByArrayMap2(){
        String beforeUrl = PATH + "?a=b";

        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals(
                        Slf4jUtil.format(PATH + "?a=b&province={}&city={}", encode("江苏省", UTF8), encode("南通市", UTF8)),
                        addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map replace.
     */
    @Test
    public void testGetEncodedUrlByArrayMapReplace(){
        String beforeUrl = PATH + "?a=b&city=上海";

        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市", "无锡" });

        assertEquals(
                        Slf4jUtil.format(
                                        PATH + "?a=b&city={}&city={}&province={}",
                                        encode("南通市", UTF8),
                                        encode("无锡", UTF8),
                                        encode("江苏省", UTF8)),
                        addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    //---------------------------------------------------------------

    /**
     * Test get encoded url by array map null uri.
     */
    @Test
    public void testGetEncodedUrlByArrayMapNullUri(){
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals(EMPTY, addParameterArrayValueMap(null, keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map empty uri.
     */
    @Test
    public void testGetEncodedUrlByArrayMapEmptyUri(){
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals(EMPTY, addParameterArrayValueMap("", keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map blank uri.
     */
    @Test
    public void testGetEncodedUrlByArrayMapBlankUri(){
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals(EMPTY, addParameterArrayValueMap(" ", keyAndArrayMap, UTF8));
    }

    //---------------------------------------------------------------

    /**
     * Test get encoded url by array map null map.
     */
    @Test
    public void testGetEncodedUrlByArrayMapNullMap(){
        assertEquals(PATH + "?a=b", addParameterArrayValueMap(PATH + "?a=b", null, UTF8));
    }

    /**
     * Test get encoded url by array map empty map.
     */
    @Test
    public void testGetEncodedUrlByArrayMapEmptyMap(){
        String beforeUrl = PATH + "?a=b";
        assertEquals(beforeUrl, addParameterArrayValueMap(beforeUrl, new HashMap<String, String[]>(), UTF8));
    }
}
