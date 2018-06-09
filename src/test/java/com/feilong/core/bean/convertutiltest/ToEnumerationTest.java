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

import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;

/**
 * The Class ConvertUtilToEnumerationTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToEnumerationTest{

    /**
     * Test to enumeration.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testToEnumerationNullCollection(){
        assertEquals(Collections.emptyEnumeration(), ConvertUtil.toEnumeration(null));
    }

    /**
     * Test to enumeration.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testToEnumeration(){
        // Enumeration
        Map<Object, Object> map = newLinkedHashMap();
        map.put("jinxin", 1);
        map.put(2, 2);
        map.put("甲", 3);
        map.put(4, 4);
        map.put("jinxin1", 1);
        Enumeration<Object> enumeration = ConvertUtil.toEnumeration(map.keySet());

        List<Object> list = ConvertUtil.toList(enumeration);
        assertThat(list, contains((Object) "jinxin", 2, "甲", 4, "jinxin1"));
    }
}
