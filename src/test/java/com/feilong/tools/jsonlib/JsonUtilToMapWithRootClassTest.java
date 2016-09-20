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
package com.feilong.tools.jsonlib;

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.test.Person;

import net.sf.json.JSONObject;

/**
 * The Class JsonUtilToMapWithRootClassTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToMapWithRootClassTest{

    /**
     * To map.
     */
    @Test
    public void toMap(){
        Map<String, Person> map = JsonUtil.toMap("{'data1':{'name':'get'},'data2':{'name':'set'}}", Person.class);
        assertThat(map, allOf(//
                        hasEntry(is("data1"), hasProperty("name", is("get"))),
                        hasEntry(is("data2"), hasProperty("name", is("set")))));
    }

    /**
     * Test to map.
     */
    @Test
    public void testToMap(){
        JSONObject json1 = JSONObject.fromObject("{'name':'get'}");
        JSONObject json2 = JSONObject.fromObject("{'name':'set'}");

        Map<String, JSONObject> map = JsonUtil.toMap("{'data1':{'name':'get'},'data2':{'name':'set'}}", null);
        assertThat(map, allOf(//
                        hasEntry("data1", json1),
                        hasEntry("data2", json2)));
    }

    //*********************************************

    /**
     * Test to map null json.
     */
    @Test
    public void testToMapNullJson(){
        assertEquals(emptyMap(), JsonUtil.toMap(null, Person.class));
    }

    /**
     * Test to map empty json.
     */
    @Test
    public void testToMapEmptyJson(){
        assertEquals(emptyMap(), JsonUtil.toMap("", Person.class));
    }

    /**
     * Test to map blank json.
     */
    @Test
    public void testToMapBlankJson(){
        assertEquals(emptyMap(), JsonUtil.toMap(" ", Person.class));
    }

    /**
     * Test to map blank json 1.
     */
    @Test
    public void testToMapBlankJson1(){
        assertEquals(emptyMap(), JsonUtil.toMap("{}", Person.class));
    }
}
