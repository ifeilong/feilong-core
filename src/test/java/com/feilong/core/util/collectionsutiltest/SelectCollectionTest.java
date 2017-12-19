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
 * The Class CollectionsUtilSelectCollectionTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SelectCollectionTest{

    /**
     * Test select.
     */
    @Test
    public void testSelect(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        assertThat(
                        CollectionsUtil.select(list, "name", toList("张飞", "刘备")),
                        allOf(hasItem(zhangfei), hasItem(liubei), not(hasItem(guanyu))));
    }

    //---------------------------------------------------------------
    /**
     * Test select null value.
     */
    @Test
    public void testSelectNullValue(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        assertEquals(emptyList(), CollectionsUtil.select(list, "name", (List<String>) null));
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

        assertEquals(emptyList(), CollectionsUtil.select(list, "name", toList((String) null)));
    }

    /**
     * Test select array null collection.
     */
    @Test
    public void testSelectCollectionNullCollection(){
        assertEquals(emptyList(), CollectionsUtil.select(null, "name", toList("张飞", "刘备")));
    }

    /**
     * Test select array empty collection.
     */
    @Test
    public void testSelectCollectionEmptyCollection(){
        assertEquals(emptyList(), CollectionsUtil.select(new ArrayList<>(), "name", toList("张飞", "刘备")));
    }
    //*****************

    /**
     * Test select array null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSelectCollectionNullPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.select(list, null, toList("张飞", "刘备"));
    }

    /**
     * Test select array empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSelectCollectionEmptyPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.select(list, "", toList("张飞", "刘备"));
    }

    /**
     * Test select array blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSelectCollectionBlankPropertyName(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        CollectionsUtil.select(list, " ", toList("张飞", "刘备"));
    }
}
