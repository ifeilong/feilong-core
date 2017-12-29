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
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static com.feilong.core.util.ResourceBundleUtil.getResourceBundle;
import static com.feilong.core.util.ResourceBundleUtil.toMap;
import static com.feilong.core.util.SortUtil.sortMapByValueDesc;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * The Class SortUtilSortMapByValueDescTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SortMapByValueDescTest{

    private static final Map<String, String> SORTMAP_BYVALUEDESC_MAP = toMap(getResourceBundle("messages/sortMapByValueDesc"));

    /**
     * Test sort by value desc.
     */
    @Test
    public void testSortByValueDesc(){
        Map<String, Integer> map = newLinkedHashMap();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        assertThat(sortMapByValueDesc(map).keySet(), contains("c", "a", "b"));
    }

    @Test
    public void testSortByValueDesc1(){
        sortMapByValueDesc(SORTMAP_BYVALUEDESC_MAP);
    }

    @Test
    public void testSortByValueDesc12(){
        Map<String, Integer> map = toMap(SORTMAP_BYVALUEDESC_MAP, Integer.class);
        sortMapByValueDesc(map);
    }

    @Test
    public void testSortByValueDescSameValue(){
        Map<String, Integer> map = newLinkedHashMap();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);
        map.put("f", 8);
        map.put("g", 123);
        map.put("d", 123);

        Set<String> keySet = sortMapByValueDesc(map).keySet();
        assertThat(
                        keySet,
                        contains(
                                        "c",

                                        "d",
                                        "g",
                                        "a",

                                        "f",
                                        "b"));//c, d, g, a, f, b
    }

    //---------------------------------------------------------------

    /**
     * Test sort by value desc null map.
     */
    @Test
    public void testSortByValueDescNullMap(){
        assertEquals(emptyMap(), sortMapByValueDesc(null));
    }

    @Test
    public void testSortByValueDescEmptyMap(){
        Map<String, Integer> map = emptyMap();
        assertEquals(emptyMap(), sortMapByValueDesc(map));
    }
}
