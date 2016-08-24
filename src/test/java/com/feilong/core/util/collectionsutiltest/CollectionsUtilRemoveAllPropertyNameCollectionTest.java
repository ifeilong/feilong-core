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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toList;

public class CollectionsUtilRemoveAllPropertyNameCollectionTest{

    /**
     * Test remove all collection.
     */
    //************CollectionsUtil.removeAll(Collection<User>, String, Collection<String>)*************
    @Test
    public void testRemoveAllCollection(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> removeAll = CollectionsUtil.removeAll(list, "name", toList("张飞", "刘备"));

        assertThat(removeAll, allOf(hasItem(guanyu), not(hasItem(zhangfei)), not(hasItem(liubei))));
        assertThat(list, allOf(hasItem(zhangfei), hasItem(liubei), hasItem(guanyu)));
    }

    /**
     * Test remove all collection 1.
     */
    @Test
    public void testRemoveAllCollection1(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> removeAll = CollectionsUtil.removeAll(list, "name", (List<String>) null);
        assertThat(removeAll, contains(zhangfei, guanyu, liubei));
    }

    /**
     * Test remove all collection null object collection.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllCollectionNullObjectCollection(){
        CollectionsUtil.removeAll(null, "name", toList("刘备"));
    }

    //******

    /**
     * Test remove all collection null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllCollectionNullPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, null, toList("刘备"));
    }

    /**
     * Test remove all collection empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAllCollectionEmptyPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, "", toList("刘备"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAllCollectionBlankPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, " ", toList("刘备"));
    }

}
