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
package com.feilong.core.bean.beanutiltest;

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.MapUtil.newHashMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.BeanUtil;
import com.feilong.store.member.User;

/**
 * The Class BeanUtilPopulateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class PopulateTest{

    /**
     * Test populate.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulate(){
        User user = new User();
        user.setId(5L);

        Map<String, Long> properties = toMap("id", 8L);
        assertThat(BeanUtil.populate(user, properties), allOf(hasProperty("id", is(8L))));
    }

    /**
     * Test populate bean not exists property name.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateBeanNotExistsPropertyName(){
        User user = new User();
        user.setId(5L);

        Map<String, Long> properties = toMap("id1", 8L);
        assertThat(BeanUtil.populate(user, properties), allOf(hasProperty("id", is(5L))));
    }

    /**
     * Test populate bean with space exists property name.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateBeanWithSpaceExistsPropertyName(){
        User user = new User();
        user.setId(5L);

        Map<String, Long> properties = toMap("i d1", 8L);
        assertThat(BeanUtil.populate(user, properties), allOf(hasProperty("id", is(5L))));
    }

    /**
     * Test populate bean with null exists property name.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateBeanWithNullExistsPropertyName(){
        User user = new User();
        user.setId(5L);

        Map<String, Long> properties = toMap(null, 8L);
        assertThat(BeanUtil.populate(user, properties), allOf(hasProperty("id", is(5L))));
    }

    /**
     * Test populate map.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateMap(){
        Map<String, Object> map = newHashMap();
        Map<String, Long> properties = toMap("id", 8L);

        assertThat(BeanUtil.populate(map, properties), allOf(hasEntry("id", (Object) 8L)));
    }

    /**
     * Test populate null bean.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testPopulateNullBean(){
        Map<String, Long> map = toMap("id", 8L);
        BeanUtil.populate(null, map);
    }

    /**
     * Test populate null properties.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testPopulateNullProperties(){
        BeanUtil.populate(new User(), null);
    }
}
