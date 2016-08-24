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
package com.feilong.core.util.collectionsutiltest;

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toList;

public class CollectionsUtilGetPropertyValueMapTest{

    @Test
    public void testGetPropertyValueMap(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        Map<String, Integer> map = CollectionsUtil.getPropertyValueMap(list, "name", "age");

        assertThat(map, allOf(//
                        hasEntry("张飞", 23),
                        hasEntry("关羽", 24),
                        hasEntry("刘备", 25)));
    }

    @Test
    public void testGetPropertyValueMapSameKey(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User zhangfei1 = new User("张飞", 25);
        List<User> list = toList(zhangfei, guanyu, zhangfei1);

        Map<String, Integer> map = CollectionsUtil.getPropertyValueMap(list, "name", "age");

        assertThat(map.keySet(), hasSize(2));
        assertThat(map, allOf(hasEntry("张飞", 25), hasEntry("关羽", 24)));
    }

    //****************************************************************************************

    @Test
    public void testGetPropertyValueMapNullCollection(){
        assertEquals(emptyMap(), CollectionsUtil.getPropertyValueMap(null, "name", "age"));
    }

    @Test
    public void testGetPropertyValueMapEmptyCollection(){
        assertEquals(emptyMap(), CollectionsUtil.getPropertyValueMap(new ArrayList<>(), "name", "age"));
    }

    //*****************
    @Test(expected = NullPointerException.class)
    public void testGetPropertyValueMapNullKeyPropertyName(){
        List<User> list = toList(new User("张飞", 23));
        CollectionsUtil.getPropertyValueMap(list, null, "age");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueMapEmptyKeyPropertyName(){
        List<User> list = toList(new User("张飞", 23));
        CollectionsUtil.getPropertyValueMap(list, "", "age");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueMapBlankKeyPropertyName(){
        List<User> list = toList(new User("张飞", 23));
        CollectionsUtil.getPropertyValueMap(list, " ", "age");
    }

    //*****************

    @Test(expected = NullPointerException.class)
    public void testGetPropertyValueMapNullValuePropertyName(){
        List<User> list = toList(new User("张飞", 23));
        CollectionsUtil.getPropertyValueMap(list, "name", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueMapEmptyValuePropertyName(){
        List<User> list = toList(new User("张飞", 23));
        CollectionsUtil.getPropertyValueMap(list, "name", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueMapBlankValuePropertyName(){
        List<User> list = toList(new User("张飞", 23));
        CollectionsUtil.getPropertyValueMap(list, "name", " ");
    }
}
