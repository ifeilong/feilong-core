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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

/**
 * The Class CollectionsUtilSelectRejectedArrayTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SelectRejectedArrayTest{

    /**
     * Test select rejected1.
     */
    @Test
    public void testSelectRejected1(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> selectRejected = CollectionsUtil.selectRejected(list, "name", "刘备", "张飞");
        assertSame(1, selectRejected.size());
        assertThat(selectRejected.get(0), hasProperty("name", equalTo("关羽")));
    }
    ///******************

    /**
     * Test select null value.
     */
    @Test
    public void testSelectNullValue(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        assertEquals(list, CollectionsUtil.selectRejected(list, "name", (String[]) null));
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
        assertEquals(list, CollectionsUtil.selectRejected(list, "name", (String) null));
    }

    /**
     * Test select array null collection.
     */
    @Test
    public void testSelectRejectedArrayNullCollection(){
        assertEquals(emptyList(), CollectionsUtil.selectRejected(null, "name", "刘备", "关羽"));
    }

    /**
     * Test select array empty collection.
     */
    @Test
    public void testSelectRejectedArrayEmptyCollection(){
        assertEquals(emptyList(), CollectionsUtil.selectRejected(new ArrayList<>(), "name", "刘备", "关羽"));
    }

    //*****************

    /**
     * Test select array null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSelectRejectedArrayNullPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.selectRejected(list, null, "刘备", "关羽");
    }

    /**
     * Test select array empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSelectRejectedArrayEmptyPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.selectRejected(list, "", "刘备", "关羽");
    }

    /**
     * Test select array blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSelectRejectedArrayBlankPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.selectRejected(list, " ", "刘备", "关羽");
    }

}
