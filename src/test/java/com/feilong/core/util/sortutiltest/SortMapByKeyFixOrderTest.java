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
package com.feilong.core.util.sortutiltest;

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.MapUtil.newHashMap;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static com.feilong.core.util.SortUtil.sortMapByKeyFixOrder;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SortMapByKeyFixOrderTest{

    /**
     * Test sort by key asc.
     */
    @Test
    public void testSortMapByKeyFixOrder(){
        Map<String, Integer> map = newHashMap();

        map.put("DE", 99);
        map.put("L", 3428);
        map.put("O", 13);
        map.put("UN", 17);
        map.put("S", 6);

        //L-上市,S-暂停,DE-终止上市,UN-未上市
        Map<String, Integer> SortMapByKeyFixOrder = sortMapByKeyFixOrder(map, "L", "UN", "S", "DE", "O");

        assertThat(SortMapByKeyFixOrder.keySet(), contains("L", "UN", "S", "DE", "O"));
    }

    //---------------------------------------------------------------

    @Test
    public void testSortMapByKeyFixOrderNullMap(){
        assertEquals(emptyMap(), sortMapByKeyFixOrder(null));
    }

    @Test
    public void testSortMapByKeyFixOrderNullKey(){
        Map<String, Integer> map = toMap("count", 5);
        assertEquals(map, sortMapByKeyFixOrder(map, null));
    }

    @Test
    public void testSortMapByKeyFixOrder2(){
        assertEquals(emptyMap(), sortMapByKeyFixOrder(emptyMap()));
        assertEquals(emptyMap(), sortMapByKeyFixOrder(new HashMap<>()));
    }

    @Test
    public void testSortNoKeys(){
        Map<String, Integer> map = newLinkedHashMap();

        map.put("a8", 8);
        map.put("a13", 123);
        map.put("a2", 345);

        Map<String, Integer> resultMap = sortMapByKeyFixOrder(map);
        assertThat(resultMap.keySet(), contains("a8", "a13", "a2"));
    }

}
