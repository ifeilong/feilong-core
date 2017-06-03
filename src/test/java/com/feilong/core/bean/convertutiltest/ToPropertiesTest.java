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

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;

import static com.feilong.core.bean.ConvertUtil.toMap;

/**
 * The Class ConvertUtilToPropertiesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToPropertiesTest{

    /**
     * Test to properties.
     */
    @Test
    public void testToProperties(){
        Map<String, String> map = toMap("name", "feilong");
        Properties properties = ConvertUtil.toProperties(map);

        Map<String, String> map1 = (Map) properties;
        assertThat(map1, allOf(hasEntry("name", "feilong")));
    }

    /**
     * Test to properties null key.
     */
    @Test(expected = NullPointerException.class)
    public void testToPropertiesNullKey(){
        Map<String, String> map = toMap(null, "feilong");
        ConvertUtil.toProperties(map);
    }

    /**
     * Test to properties null value.
     */
    @Test(expected = NullPointerException.class)
    public void testToPropertiesNullValue(){
        Map<String, String> map = toMap("name", null);
        ConvertUtil.toProperties(map);
    }

    /**
     * Test to properties null map.
     */
    @Test
    public void testToPropertiesNullMap(){
        Properties properties = ConvertUtil.toProperties(null);
        assertEquals(emptyMap(), properties);
    }
}
