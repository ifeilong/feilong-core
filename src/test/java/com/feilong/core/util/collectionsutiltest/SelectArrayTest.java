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

import static com.feilong.core.bean.ConvertUtil.toList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

/**
 * The Class CollectionsUtilSelectArrayTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SelectArrayTest{

    /**
     * Test select value.
     */
    @Test
    public void testSelectValue(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei, guanyu);

        assertThat(
                        CollectionsUtil.select(list, "name", "关羽"),
                        allOf(hasItem(guanyu), hasItem(guanyu), not(hasItem(zhangfei)), not(hasItem(liubei))));
    }

    /**
     * Test select array.
     */
    @Test
    public void testSelectArray(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> select = CollectionsUtil.select(list, "name", "刘备", "关羽");
        assertThat(select, allOf(hasItem(liubei), hasItem(guanyu), not(hasItem(zhangfei))));
    }

    /**
     * Test select null value.
     */
    @Test
    public void testSelectNullValue(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        assertEquals(emptyList(), CollectionsUtil.select(list, "name", (String[]) null));
    }

    /**
     * Test select null element value.
     */
    @Test
    public void testSelectNullElementValue(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        assertEquals(emptyList(), CollectionsUtil.select(list, "name", (String) null));
    }

    //---------------------------------------------------------------

    /**
     * Test select array null collection.
     */
    @Test
    public void testSelectArrayNullCollection(){
        assertEquals(emptyList(), CollectionsUtil.select(null, "name", "刘备", "关羽"));
    }

    /**
     * Test select array empty collection.
     */
    @Test
    public void testSelectArrayEmptyCollection(){
        assertEquals(emptyList(), CollectionsUtil.select(new ArrayList<>(), "name", "刘备", "关羽"));
    }

    //*****************

    /**
     * Test select array null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSelectArrayNullPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.select(list, null, "刘备", "关羽");
    }

    /**
     * Test select array empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSelectArrayEmptyPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.select(list, "", "刘备", "关羽");
    }

    /**
     * Test select array blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSelectArrayBlankPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.select(list, " ", "刘备", "关羽");
    }
}
