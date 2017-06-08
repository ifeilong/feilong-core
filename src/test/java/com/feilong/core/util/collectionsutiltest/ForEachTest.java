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
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

/**
 * The Class ForEachTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.2
 */
public class ForEachTest{

    /**
     * Test.
     */
    @Test
    public void test(){
        User zhangfei28 = new User("张飞", 28);
        User liubei32 = new User("刘备", 32);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei28, liubei32, liubei30);

        CollectionsUtil.forEach(list, "age", 88);

        assertThat(zhangfei28, hasProperty("age", is(88)));
        assertThat(liubei32, hasProperty("age", is(88)));
        assertThat(liubei30, hasProperty("age", is(88)));
    }

    /**
     * Test null element.
     */
    @Test
    public void testNullElement(){
        User zhangfei28 = new User("张飞", 28);
        User liubei30 = new User("刘备", 30);

        User userNull = null;
        List<User> list = toList(zhangfei28, userNull, liubei30);

        CollectionsUtil.forEach(list, "age", 88);

        assertThat(zhangfei28, hasProperty("age", is(88)));
        assertThat(list, hasItem(userNull));
        assertThat(liubei30, hasProperty("age", is(88)));
    }

    /**
     * Test null iterable.
     */
    //----------------
    @Test
    public void testNullIterable(){
        CollectionsUtil.forEach(null, null, 88);
    }

    /**
     * Test empty iterable.
     */
    @Test
    public void testEmptyIterable(){
        CollectionsUtil.forEach(emptyList(), null, 88);
    }

    /**
     * Test null property name.
     */
    //----------------
    @Test(expected = NullPointerException.class)
    public void testNullPropertyName(){
        List<User> list = toList(new User("张飞", 28), new User("刘备", 32), new User("刘备", 30));
        CollectionsUtil.forEach(list, null, 88);
    }

    /**
     * Test empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyPropertyName(){
        List<User> list = toList(new User("张飞", 28), new User("刘备", 32), new User("刘备", 30));
        CollectionsUtil.forEach(list, "", 88);
    }

    /**
     * Test blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBlankPropertyName(){
        List<User> list = toList(new User("张飞", 28), new User("刘备", 32), new User("刘备", 30));
        CollectionsUtil.forEach(list, "    ", 88);
    }
}
