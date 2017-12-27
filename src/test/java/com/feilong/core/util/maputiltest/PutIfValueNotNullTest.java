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
package com.feilong.core.util.maputiltest;

import static com.feilong.core.util.MapUtil.newHashMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

/**
 * The Class MapUtilPutIfValueNotNullTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class PutIfValueNotNullTest{

    /**
     * Test put if value not null.
     */
    @Test
    public void testPutIfValueNotNull(){
        Map<String, Integer> map = newHashMap();
        MapUtil.putIfValueNotNull(map, "1000001", 5);
        assertThat(map, allOf(hasEntry("1000001", 5)));
    }

    /**
     * Test put if value not null empty.
     */
    @Test
    public void testPutIfValueNotNullEmpty(){
        Map<String, String> map = newHashMap();
        MapUtil.putIfValueNotNull(map, "1000001", "");
        assertThat(map, allOf(hasEntry("1000001", "")));
    }

    /**
     * Test put if value not null null value.
     */
    @Test
    public void testPutIfValueNotNullNullValue(){
        Map<String, Integer> map = newHashMap();
        MapUtil.putIfValueNotNull(map, "1000001", null);
        assertThat(map, allOf(not(hasKey("1000001"))));
    }

    /**
     * Test put if value not null null map.
     */
    @Test
    public void testPutIfValueNotNullNullMap(){
        MapUtil.putIfValueNotNull(null, "1000001", 5);
    }

}
