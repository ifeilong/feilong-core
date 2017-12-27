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

import static com.feilong.core.util.MapUtil.newHashMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.net.ParamUtil;

/**
 * The Class ParamUtilToNaturalOrderingQueryStringTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToNaturalOrderingQueryStringTest{
    //***************com.feilong.core.net.ParamUtil.toNaturalOrderingQueryString(Map<String, String>)**********

    /**
     * Test to natural ordering string null map.
     */
    @Test
    public void testToNaturalOrderingStringNullMap(){
        assertEquals(EMPTY, ParamUtil.toNaturalOrderingQueryString(null));
    }

    /**
     * Test to natural ordering string empty map.
     */
    @Test
    public void testToNaturalOrderingStringEmptyMap(){
        assertEquals(EMPTY, ParamUtil.toNaturalOrderingQueryString(new HashMap<String, String>()));
    }

    /**
     * Test to natural ordering string.
     */
    @Test
    public void testToNaturalOrderingString(){
        Map<String, String> map = newHashMap();
        map.put("service", "create_salesorder");
        map.put("_input_charset", "gbk");
        map.put("totalActual", "210.00");
        map.put("address", "江苏南通市通州区888组888号");

        assertEquals(
                        "_input_charset=gbk&address=江苏南通市通州区888组888号&service=create_salesorder&totalActual=210.00",
                        ParamUtil.toNaturalOrderingQueryString(map));
    }

    /**
     * Test to natural ordering string 3.
     */
    @Test
    public void testToNaturalOrderingStringNullValue(){
        Map<String, String> map = newHashMap();
        map.put("service", null);
        map.put("totalActual", "210.00");
        map.put("province", "江苏省");
        assertEquals("province=江苏省&service=&totalActual=210.00", ParamUtil.toNaturalOrderingQueryString(map));
    }

    /**
     * Test to natural ordering string null key.
     */
    @Test
    public void testToNaturalOrderingStringNullKey(){
        Map<String, String> map = newHashMap();
        map.put("totalActual", null);
        map.put(null, "create_salesorder");
        map.put("province", "江苏省");
        assertEquals("=create_salesorder&province=江苏省&totalActual=", ParamUtil.toNaturalOrderingQueryString(map));
    }
}
