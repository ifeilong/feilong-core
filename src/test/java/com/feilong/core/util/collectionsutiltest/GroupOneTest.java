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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
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
 * The Class CollectionsUtilGroupOneTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GroupOneTest{

    /**
     * Test group one.
     */
    @Test
    public void testGroupOne(){
        User zhangfei = new User("张飞", 23);
        User liubei25 = new User("刘备", 25);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei, liubei25, liubei30);
        Map<String, User> map = CollectionsUtil.groupOne(list, "name");

        assertThat(map.keySet(), is(hasSize(2)));
        assertThat(map, allOf(//
                        hasEntry("张飞", zhangfei),
                        hasEntry("刘备", liubei25),
                        not(hasEntry("刘备", liubei30))));
    }

    /**
     * Test group one null collection.
     */
    @Test
    public void testGroupOneNullCollection(){
        assertEquals(emptyMap(), CollectionsUtil.groupOne(null, "name"));
    }

    /**
     * Test group one empty collection.
     */
    @Test
    public void testGroupOneEmptyCollection(){
        assertEquals(emptyMap(), CollectionsUtil.groupOne(new ArrayList<>(), "name"));
    }

    /**
     * Test group one null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testGroupOneNullPropertyName(){
        User zhangfei = new User("张飞", 23);
        User liubei25 = new User("刘备", 25);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei, liubei25, liubei30);
        CollectionsUtil.groupOne(list, null);
    }

    /**
     * Test group one empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupOneEmptyPropertyName(){
        User zhangfei = new User("张飞", 23);
        User liubei25 = new User("刘备", 25);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei, liubei25, liubei30);
        CollectionsUtil.groupOne(list, "");
    }

    /**
     * Test group one blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupOneBlankPropertyName(){
        User zhangfei = new User("张飞", 23);
        User liubei25 = new User("刘备", 25);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei, liubei25, liubei30);
        CollectionsUtil.groupOne(list, " ");
    }

}
