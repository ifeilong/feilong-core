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

import java.util.List;

import org.junit.Test;

import com.feilong.test.Person;

/**
 * The Class JsonUtilToListTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToListTest{

    /**
     * 把一个json数组串转换成集合,且集合里存放的为实例Bean void.
     */
    @Test
    public void testToList(){
        String json = "[{'name':'get'},{'name':'set'}]";
        List<Person> list = JsonUtil.toList(json, Person.class);

        assertThat(list.get(0), allOf(hasProperty("name", is("get"))));
        assertThat(list.get(1), allOf(hasProperty("name", is("set"))));
    }

    //----------------------------------------------------------------------------------------
    @Test
    public void testToListNullJson(){
        assertEquals(null, JsonUtil.toList(null, Person.class));
    }

    @Test(expected = NullPointerException.class)
    public void testToListNullRootClass(){
        String json = "[{'name':'get'},{'name':'set'}]";
        JsonUtil.toList(json, (Class) null);
    }
}
