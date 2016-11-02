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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.test.Person;

/**
 * The Class JsonUtilToArrayWithJsonToJavaConfigTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToArrayWithJsonToJavaConfigTest{

    /**
     * 把一个json数组串转换成实体数组 void.
     */
    @Test
    public void toArray2(){
        String json = "[{'name':'get'},{'name':'set'}]";
        Person[] persons = JsonUtil.toArray(json, new JsonToJavaConfig(Person.class));

        assertThat(persons[0], allOf(hasProperty("name", is("get"))));
        assertThat(persons[1], allOf(hasProperty("name", is("set"))));
    }

    /**
     * 把一个json数组串转换成实体数组,且数组元素的属性含有另外实例Bean void.
     */
    @Test
    public void toArray3(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("data", Person.class);

        MyBean[] myBeans = JsonUtil.toArray(json, new JsonToJavaConfig(MyBean.class, classMap));

        assertThat(myBeans[0].getData().get(0), allOf(hasProperty("name", is("get"))));
        assertThat(myBeans[1].getData().get(0), allOf(hasProperty("name", is("set"))));
    }

    //-----------------------------------------------------------------------------------

    /**
     * Test to array null json.
     */
    @Test
    public void testToArrayNullJson(){
        Person[] objArr = JsonUtil.toArray(null, new JsonToJavaConfig(Person.class));
        assertArrayEquals(null, objArr);
    }

    /**
     * Test to array null json to java config.
     */
    @Test(expected = NullPointerException.class)
    public void testToArrayNullJsonToJavaConfig(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        JsonUtil.toArray(json, null);
    }

    /**
     * Test to array null json to java config root class.
     */
    @Test(expected = NullPointerException.class)
    public void testToArrayNullJsonToJavaConfigRootClass(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        JsonUtil.toArray(json, new JsonToJavaConfig());
    }

}
