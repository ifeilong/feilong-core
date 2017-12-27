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

public class ToNaturalOrderingKeyJoinValueTest{

    @Test
    public void testToNaturalOrderingKeyJoinValueNullMap(){
        assertEquals(EMPTY, ParamUtil.toNaturalOrderingKeyJoinValue(null));
    }

    @Test
    public void testToNaturalOrderingKeyJoinValueEmptyMap(){
        assertEquals(EMPTY, ParamUtil.toNaturalOrderingKeyJoinValue(new HashMap<String, String>()));
    }

    @Test
    public void testToNaturalOrderingKeyJoinValue(){
        Map<String, String> map = newHashMap();
        map.put("service", "create_salesorder");
        map.put("_input_charset", "gbk");
        map.put("totalActual", "210.00");
        map.put("address", "江苏南通市通州区888组888号");

        assertEquals(
                        "_input_charsetgbkaddress江苏南通市通州区888组888号servicecreate_salesordertotalActual210.00",
                        ParamUtil.toNaturalOrderingKeyJoinValue(map));
    }

    @Test
    public void testToNaturalOrderingKeyJoinValueNullValue(){
        Map<String, String> map = newHashMap();
        map.put("service", null);
        map.put("totalActual", "210.00");
        map.put("province", "江苏省");
        assertEquals("province江苏省servicetotalActual210.00", ParamUtil.toNaturalOrderingKeyJoinValue(map));
    }

    @Test
    public void testToNaturalOrderingKeyJoinValueNullKey(){
        Map<String, String> map = newHashMap();
        map.put("totalActual", null);
        map.put(null, "create_salesorder");
        map.put("province", "江苏省");
        assertEquals("create_salesorderprovince江苏省totalActual", ParamUtil.toNaturalOrderingKeyJoinValue(map));
    }
}
