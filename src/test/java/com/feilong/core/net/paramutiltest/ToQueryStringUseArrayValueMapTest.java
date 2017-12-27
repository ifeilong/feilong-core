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

import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.net.ParamUtil;

/**
 * The Class ParamUtilToQueryStringUseArrayValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToQueryStringUseArrayValueMapTest{

    /**
     * Test join array value map.
     */
    @Test
    public void testJoinArrayValueMap(){
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();

        keyAndArrayMap.put("province", new String[] { "江苏省", "浙江省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals("province=江苏省&province=浙江省&city=南通市", ParamUtil.toQueryStringUseArrayValueMap(keyAndArrayMap));
    }

    /**
     * Test join array value map null element.
     */
    @Test
    public void testJoinArrayValueMapNullElement(){
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();

        keyAndArrayMap.put("province", new String[] { "江苏省", null });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals("province=江苏省&province=&city=南通市", ParamUtil.toQueryStringUseArrayValueMap(keyAndArrayMap));
    }

    /**
     * Test join array value map null value.
     */
    @Test
    public void testJoinArrayValueMapNullValue(){
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();

        keyAndArrayMap.put("province", null);
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals("province=&city=南通市", ParamUtil.toQueryStringUseArrayValueMap(keyAndArrayMap));
    }

    /**
     * Test join array value map null param and null value.
     */
    @Test
    public void testJoinArrayValueMapNullParamAndNullValue(){
        Map<String, String[]> keyAndArrayMap = newLinkedHashMap();

        keyAndArrayMap.put(null, null);
        keyAndArrayMap.put("city", new String[] { "南通市" });

        assertEquals("=&city=南通市", ParamUtil.toQueryStringUseArrayValueMap(keyAndArrayMap));
    }

    /**
     * Test join array value map null map.
     */
    @Test
    public void testJoinArrayValueMapNullMap(){
        assertEquals(EMPTY, ParamUtil.toQueryStringUseArrayValueMap(null));
    }

    /**
     * Test join array value map empty map.
     */
    @Test
    public void testJoinArrayValueMapEmptyMap(){
        assertEquals(EMPTY, ParamUtil.toQueryStringUseArrayValueMap(new HashMap<String, String[]>()));
    }

}
