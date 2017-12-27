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
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.net.ParamUtil.addParameterSingleValueMap;
import static com.feilong.core.net.URIUtil.encode;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * The Class ParamUtilAddParameterSingleValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AddParameterSingleValueMapTest{

    /** The Constant PATH. */
    private static final String PATH = "www.baidu.com";

    /**
     * Test add parameter single value map.
     */
    @Test
    public void testAddParameterSingleValueMap(){
        Map<String, String> singleValueMap = newLinkedHashMap();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        assertEquals(
                        PATH + "?province=" + encode("江苏省", UTF8) + "&city=" + encode("南通市", UTF8),
                        addParameterSingleValueMap(PATH, singleValueMap, UTF8));
    }

    /**
     * Test add parameter single value map with param.
     */
    @Test
    public void testAddParameterSingleValueMapWithParam(){
        String beforeUrl = PATH + "?a=b";
        Map<String, String> singleValueMap = newLinkedHashMap();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        assertEquals(
                        PATH + "?a=b&province=" + encode("江苏省", UTF8) + "&city=" + encode("南通市", UTF8),
                        addParameterSingleValueMap(beforeUrl, singleValueMap, UTF8));
    }

    /**
     * Test add parameter single value map with param by replace.
     */
    @Test
    public void testAddParameterSingleValueMapWithParamByReplace(){
        String beforeUrl = PATH + "?a=b&city=12345&name=feilong";
        Map<String, String> singleValueMap = newLinkedHashMap();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        String expected = Slf4jUtil.format(PATH + "?a=b&city={}&name=feilong&province={}", encode("南通市", UTF8), encode("江苏省", UTF8));
        assertEquals(expected, addParameterSingleValueMap(beforeUrl, singleValueMap, UTF8));
    }

    /**
     * Test add parameter single value map null map.
     */
    @Test
    public void testAddParameterSingleValueMapNullMap(){
        assertEquals(PATH + "?a=" + encode("中国", UTF8), addParameterSingleValueMap(PATH + "?a=中国", null, UTF8));
    }

    /**
     * Test add parameter single value map empty map.
     */
    @Test
    public void testAddParameterSingleValueMapEmptyMap(){
        assertEquals(
                        PATH + "?a=" + encode("中国", UTF8),
                        addParameterSingleValueMap(PATH + "?a=中国", new LinkedHashMap<String, String>(), UTF8));
    }

    /**
     * Test add parameter single value map empty map and null charset type.
     */
    @Test
    public void testAddParameterSingleValueMapEmptyMapAndNullCharsetType(){
        assertEquals(PATH + "?a=中国", addParameterSingleValueMap(PATH + "?a=中国", new LinkedHashMap<String, String>(), null));
    }

    /**
     * Test add parameter single value map empty map and empty charset type.
     */
    @Test
    public void testAddParameterSingleValueMapEmptyMapAndEmptyCharsetType(){
        assertEquals(PATH + "?a=中国", addParameterSingleValueMap(PATH + "?a=中国", new LinkedHashMap<String, String>(), ""));
    }

    /**
     * Test add parameter single value map empty map and blank charset type.
     */
    @Test
    public void testAddParameterSingleValueMapEmptyMapAndBlankCharsetType(){
        assertEquals(PATH + "?a=中国", addParameterSingleValueMap(PATH + "?a=中国", new LinkedHashMap<String, String>(), " "));
    }

    /**
     * Test add parameter single value map null uri.
     */
    @Test
    public void testAddParameterSingleValueMapNullUri(){
        assertEquals(EMPTY, addParameterSingleValueMap(null, toMap("province", "江苏省"), UTF8));
    }

    /**
     * Test add parameter single value map empty uri.
     */
    @Test
    public void testAddParameterSingleValueMapEmptyUri(){
        assertEquals(EMPTY, addParameterSingleValueMap("", toMap("province", "江苏省"), UTF8));
    }

    /**
     * Test add parameter single value map blank uri.
     */
    @Test
    public void testAddParameterSingleValueMapBlankUri(){
        assertEquals(EMPTY, addParameterSingleValueMap(" ", toMap("province", "江苏省"), UTF8));
    }
}
