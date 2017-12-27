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
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Properties;

import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * The Class ConvertUtilToMapPropertiesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToMapPropertiesTest{

    //*********************com.feilong.core.bean.ConvertUtil.toMap(Properties)*************

    /**
     * Test to properties 1.
     */
    @Test
    public void testToProperties1(){
        Properties properties = new Properties();

        properties.setProperty("name", "feilong");
        properties.setProperty("age", "18");
        properties.setProperty("country", "china");

        assertThat(
                        toMap(properties),
                        allOf(//
                                        hasEntry("name", "feilong"),
                                        hasEntry("age", "18"),
                                        hasEntry("country", "china")));
    }

    /**
     * Test to map empty properties.
     */
    @Test
    public void testToMapEmptyProperties(){
        assertEquals(emptyMap(), toMap(new Properties()));
    }

    /**
     * Test to map null properties.
     */
    @Test
    public void testToMapNullProperties(){
        toMap((Properties) null);
    }

    /**
     * Test to properties.
     */
    @Test
    public void testToProperties(){
        Map<String, Object> map = newLinkedHashMap();

        map.put("name", "feilong");
        map.put("age", 18);
        map.put("country", "china");

        Properties properties = org.apache.commons.collections4.MapUtils.toProperties(map);

        assertThat(
                        toMap(properties),
                        allOf(//
                                        Matchers.<String, Object> hasEntry("name", "feilong"),
                                        Matchers.<String, Object> hasEntry("age", 18),
                                        Matchers.<String, Object> hasEntry("country", "china")));
    }
}
