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
package com.feilong.core.bean.convertutiltest;

import static com.feilong.core.bean.ConvertUtil.toMap;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

/**
 * The Class ConvertUtilToMapKeyValueTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToMapKeyValueTest{

    /**
     * Test to map1.
     */
    @Test
    public void testToMap1(){
        Map<String, String> map = toMap("张飞", "丈八蛇矛");
        assertThat(map, allOf(notNullValue(), hasEntry("张飞", "丈八蛇矛")));
    }

    @Test
    public void testToMap2(){
        Map<String, Object> map = toMap("张飞", (Object) "丈八蛇矛");
        assertThat(map, allOf(notNullValue(), hasEntry("张飞", (Object) "丈八蛇矛")));
    }

    /**
     * Test to map null key.
     */
    @Test
    public void testToMapNullKey(){
        Map<String, String> map = toMap(null, "丈八蛇矛");
        assertThat(map, allOf(notNullValue(), hasEntry(null, "丈八蛇矛")));
    }
}
