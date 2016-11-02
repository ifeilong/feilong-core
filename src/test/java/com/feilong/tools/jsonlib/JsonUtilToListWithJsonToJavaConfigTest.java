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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.test.Person;

/**
 * The Class JsonUtilToListWithJsonToJavaConfigTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToListWithJsonToJavaConfigTest{

    /**
     * 把一个json数组串转换成集合,且集合里的对象的属性含有另外实例Bean void.
     */
    @Test
    public void toList3(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("data", Person.class);

        List<MyBean> list = JsonUtil.toList(json, new JsonToJavaConfig(MyBean.class, classMap));

        assertThat(list.get(0).getData().get(0), allOf(hasProperty("name", is("get"))));
        assertThat(list.get(1).getData().get(0), allOf(hasProperty("name", is("set"))));
    }

    //---------------------------------------------------------------------

    @Test
    public void testToListNullJson(){
        assertEquals(null, JsonUtil.toList(null, new JsonToJavaConfig(Person.class)));
    }

    /**
     * Test to array null json to java config.
     */
    @Test(expected = NullPointerException.class)
    public void testToListNullJsonToJavaConfig(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        JsonUtil.toList(json, (JsonToJavaConfig) null);
    }

    /**
     * Test to array null json to java config root class.
     */
    @Test(expected = NullPointerException.class)
    public void testToListNullJsonToJavaConfigRootClass(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        JsonUtil.toList(json, new JsonToJavaConfig());
    }
}
