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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.test.Person;
import com.feilong.tools.AbstractJsonTest;

/**
 * The Class JsonUtilToMapWithRootClassAndClassMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToMapWithRootClassAndClassMapTest extends AbstractJsonTest{

    /**
     * To map 3.
     */
    @Test
    public void toMap3(){
        String json = "{'mybean':{'data':[{'name':'get'}]}}";
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        classMap.put("data", Person.class);

        Map<String, MyBean> map = JsonUtil.toMap(json, MyBean.class, classMap);

        assertThat(map.keySet(), contains("mybean"));

        MyBean myBean = map.get("mybean");
        List<Object> data = myBean.getData();

        Object object = data.get(0);
        assertThat(object, hasProperty("name", is("get")));
    }

    //*********************************************

    /**
     * Test to map null json.
     */
    @Test
    public void testToMapNullJson(){
        assertEquals(emptyMap(), JsonUtil.toMap(null, Person.class, new HashMap<String, Class<?>>()));
    }

    /**
     * Test to map empty json.
     */
    @Test
    public void testToMapEmptyJson(){
        assertEquals(emptyMap(), JsonUtil.toMap("", Person.class, new HashMap<String, Class<?>>()));
    }

    /**
     * Test to map blank json.
     */
    @Test
    public void testToMapBlankJson(){
        assertEquals(emptyMap(), JsonUtil.toMap(" ", Person.class, new HashMap<String, Class<?>>()));
    }

    /**
     * Test to map blank json 1.
     */
    @Test
    public void testToMapBlankJson1(){
        assertEquals(emptyMap(), JsonUtil.toMap("{}", Person.class, new HashMap<String, Class<?>>()));
    }
}
