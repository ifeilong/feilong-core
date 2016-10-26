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

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static com.feilong.core.util.SortUtil.sortMapByValueAsc;

/**
 * The Class SortUtilSortMapByValueAscTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SortUtilSortMapByValueAscTest{

    /**
     * Test sort by value asc.
     */
    @Test
    public void testSortByValueASC(){
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);
        Map<String, Integer> sortByValueAsc = sortMapByValueAsc(map);
        assertThat(sortByValueAsc.keySet(), contains("b", "a", "c"));
    }

    /**
     * Test sort by value ASC null map.
     */
    @Test
    public void testSortByValueASCNullMap(){
        assertEquals(emptyMap(), sortMapByValueAsc(null));
    }

}