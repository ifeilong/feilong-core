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
package com.feilong.core.util;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CollectionsUtilRemoveTest{

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

    /**
     * Test remove all collection empty property name 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAllCollectionEmptyPropertyName1(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, " ", toList("刘备"));
    }

    //****************CollectionsUtil.removeAll(Collection<User>, String, String...)************************
    /**
     * Test remove all1.
     */
    @Test
    public void testRemoveAll1(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        assertThat(CollectionsUtil.removeAll(list, "name", "刘备"), contains(zhangfei, guanyu));
        assertThat(list, contains(zhangfei, guanyu, liubei));

        assertThat(CollectionsUtil.removeAll(list, "name", (String) null), contains(zhangfei, guanyu, liubei));
        assertThat(list, contains(zhangfei, guanyu, liubei));

        assertThat(CollectionsUtil.removeAll(list, "name", "刘备", "关羽"), contains(zhangfei));
        assertThat(list, contains(zhangfei, guanyu, liubei));
    }

    /**
     * Test remove all null object collection.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllNullObjectCollection(){
        CollectionsUtil.removeAll(null, "name", "刘备");
    }

    //******

    /**
     * Test remove all null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllNullPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, null, "刘备");
    }

    /**
     * Test remove all empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAllEmptyPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, "", "刘备");
    }

    /**
     * Test remove all empty property name 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAllEmptyPropertyName1(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, " ", "刘备");
    }

    //**************CollectionsUtil.remove(Collection<String>, String)*************************
    /**
     * Test remove.
     */
    @Test
    public void testRemove(){
        List<String> list = new ArrayList<String>(){

            private static final long serialVersionUID = -9002323146501447769L;

            {
                add("xinge");
                add("feilong1");
                add("feilong2");
                add("feilong2");
            }
        };

        List<String> removeList = CollectionsUtil.remove(list, "feilong2");
        assertThat(removeList, hasItems("xinge", "feilong1"));
        assertThat(list, hasItems("xinge", "feilong1", "feilong2", "feilong2"));
    }

    /**
     * Test remove element 1.
     */
    @Test
    public void testRemoveElement1(){
        assertThat(
                        CollectionsUtil.remove(toList("xinge", "feilong1", "feilong2", "feilong2"), null),
                        contains("xinge", "feilong1", "feilong2", "feilong2"));
    }

    /**
     * Test remove element.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveElement(){
        CollectionsUtil.remove(null, "刘备");
    }

    //************CollectionsUtil.removeDuplicate(Collection<O>)*************************
    /**
     * Test remove duplicate.
     */
    @Test
    public void testRemoveDuplicate(){
        List<String> list = toList("feilong1", "feilong2", "feilong2", "feilong3");
        List<String> removeDuplicate = CollectionsUtil.removeDuplicate(list);

        assertSame(3, removeDuplicate.size());
        assertThat(removeDuplicate, contains("feilong1", "feilong2", "feilong3"));

        assertSame(4, list.size());
        assertThat(list, contains("feilong1", "feilong2", "feilong2", "feilong3"));
    }

    /**
     * Test remove duplicate 1.
     */
    @Test
    public void testRemoveDuplicate1(){
        assertEquals(emptyList(), CollectionsUtil.removeDuplicate(null));
    }

    /**
     * Test remove duplicate 2.
     */
    @Test
    public void testRemoveDuplicate2(){
        assertEquals(emptyList(), CollectionsUtil.removeDuplicate(new ArrayList<>()));
    }

    /**
     * Test remove duplicate 3.
     */
    @Test
    public void testRemoveDuplicate3(){
        assertEquals(emptyList(), CollectionsUtil.removeDuplicate(toList()));
    }
}
