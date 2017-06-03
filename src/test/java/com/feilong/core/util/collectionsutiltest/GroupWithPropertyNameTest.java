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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionsUtilGroupTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GroupWithPropertyNameTest{

    /**
     * Test group.
     */
    @Test
    public void testGroup(){
        User zhangfei = new User("张飞", 23);
        User liubei25 = new User("刘备", 25);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei, liubei25, liubei30);

        Map<String, List<User>> map = CollectionsUtil.group(list, "name");

        assertEquals(2, map.size());
        assertThat(map, allOf(hasEntry("张飞", toList(zhangfei)), hasEntry("刘备", toList(liubei25, liubei30))));
    }

    @Test
    public void testGroupInt(){
        User zhangfei = new User("张飞");
        zhangfei.setAgeInt(25);

        User hanxiangdi18 = new User("汉献帝");
        hanxiangdi18.setAgeInt(18);

        User zhugeliang18 = new User("诸葛亮");
        zhugeliang18.setAgeInt(18);

        List<User> list = toList(zhangfei, hanxiangdi18, zhugeliang18);

        Map<Integer, List<User>> map = CollectionsUtil.group(list, "ageInt");

        assertEquals(2, map.size());
        assertThat(map, allOf(hasEntry(25, toList(zhangfei)), hasEntry(18, toList(hanxiangdi18, zhugeliang18))));
    }

    /**
     * Test group null collection.
     */
    @Test
    public void testGroupNullCollection(){
        assertEquals(emptyMap(), CollectionsUtil.group(null, "name"));
    }

    /**
     * Test group empty collection.
     */
    @Test
    public void testGroupEmptyCollection(){
        assertEquals(emptyMap(), CollectionsUtil.group(new ArrayList<>(), "name"));
    }

    /**
     * Test group null property name.
     */
    //*****
    @Test(expected = NullPointerException.class)
    public void testGroupNullPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("刘备", 25), new User("刘备", 25));
        CollectionsUtil.group(list, (String) null);
    }

    /**
     * Test group empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupEmptyPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("刘备", 25), new User("刘备", 25));
        CollectionsUtil.group(list, "");
    }

    /**
     * Test group blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupBlankPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("刘备", 25), new User("刘备", 25));
        CollectionsUtil.group(list, " ");
    }
}
