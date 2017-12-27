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

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.net.ParamUtil;

/**
 * The Class ParamUtilToQueryStringUseSingleValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToQueryStringUseSingleValueMapTest{

    /**
     * Test join single value map.
     */
    @Test
    public void testJoinSingleValueMapNullKey(){
        Map<String, String> map = toMap(null, (String) null);
        assertEquals("=", ParamUtil.toQueryStringUseSingleValueMap(map));
    }

    /**
     * Test join single value map 2.
     */
    @Test
    public void testJoinSingleValueMap2(){
        Map<String, String> map = newLinkedHashMap();
        map.put(null, null);
        map.put("a", "");
        map.put("b", null);
        map.put("c", "jim");

        assertEquals("=&a=&b=&c=jim", ParamUtil.toQueryStringUseSingleValueMap(map));
    }

    /**
     * Test join single value map1.
     */
    @Test
    public void testJoinSingleValueMap1(){
        Map<String, String> singleValueMap = newLinkedHashMap();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        assertEquals("province=江苏省&city=南通市", ParamUtil.toQueryStringUseSingleValueMap(singleValueMap));
    }

    /**
     * Test join single value map null map.
     */
    @Test
    public void testJoinSingleValueMapNullMap(){
        assertEquals(EMPTY, ParamUtil.toQueryStringUseSingleValueMap(null));
    }

    /**
     * Test join single value map empty map.
     */
    @Test
    public void testJoinSingleValueMapEmptyMap(){
        assertEquals(EMPTY, ParamUtil.toQueryStringUseSingleValueMap(new HashMap<String, String>()));
    }

}
