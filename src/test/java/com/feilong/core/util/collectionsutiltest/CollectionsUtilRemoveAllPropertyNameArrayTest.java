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

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toList;

public class CollectionsUtilRemoveAllPropertyNameArrayTest{

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

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAllBlankPropertyName1(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 24), new User("刘备", 25));
        CollectionsUtil.removeAll(list, " ", "刘备");
    }

}
