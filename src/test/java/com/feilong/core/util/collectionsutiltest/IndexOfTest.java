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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionsUtilIndexOfTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IndexOfTest{

    /**
     * Test index of.
     */
    //************CollectionsUtil.indexOf(List<User>, String, String)******************************
    @Test
    public void testIndexOf(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));

        assertEquals(0, CollectionsUtil.indexOf(list, "name", "张飞"));
        assertEquals(1, CollectionsUtil.indexOf(list, "age", 24));
    }

    /**
     * Test index of not find value.
     */
    @Test
    public void testIndexOfNotFindValue(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        assertEquals(-1, CollectionsUtil.indexOf(list, "age", 240));
        assertEquals(-1, CollectionsUtil.indexOf(list, "age", null));
    }

    /**
     * Test index of null list.
     */
    @Test
    public void testIndexOfNullList(){
        assertEquals(-1, CollectionsUtil.indexOf(null, "age", 24));
    }

    /**
     * Test index of empty list.
     */
    @Test
    public void testIndexOfEmptyList(){
        assertEquals(-1, CollectionsUtil.indexOf(new ArrayList<User>(), "age", 24));
    }

    /**
     * Test index of null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testIndexOfNullPropertyName(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        CollectionsUtil.indexOf(list, null, 240);
    }

    /**
     * Test index of empty property name 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexOfEmptyPropertyName1(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        CollectionsUtil.indexOf(list, "", 240);
    }

    /**
     * Test index of empty property name 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexOfEmptyPropertyName2(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        CollectionsUtil.indexOf(list, " ", 240);
    }
}
