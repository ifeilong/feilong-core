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

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.MapUtil.newHashMap;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

/**
 * The Class MapUtilPutAllIfNotNullTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class PutAllIfNotNullTest{

    /**
     * Test put all if not null null value.
     */
    @Test
    public void testPutAllIfNotNullNullValue(){
        Map<String, Integer> map = newHashMap();
        MapUtil.putAllIfNotNull(map, toMap("age", 18));
        assertThat(map, hasEntry("age", 18));
    }

    /**
     * Test put all if not null.
     */
    @Test
    public void testPutAllIfNotNull(){
        Map<String, Integer> map = newHashMap();
        MapUtil.putAllIfNotNull(map, new HashMap<String, Integer>());
        assertThat(map.keySet(), hasSize(0));
    }

    /**
     * Test put all if not null null map.
     */
    @Test
    public void testPutAllIfNotNullNullMap(){
        MapUtil.putAllIfNotNull(null, newHashMap());
    }

}
