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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

/**
 * The Class FindTest.
 */
public class FindTest{

    /**
     * Test find.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testFind(){
        User zhangfei = new User("张飞", 23);
        User guanyu24 = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        User guanyu50 = new User("关羽", 50);
        List<User> list = toList(zhangfei, guanyu24, liubei, guanyu50);

        assertThat(CollectionsUtil.find(list, "name", "关羽"), is(equalTo(guanyu24)));
    }

    /**
     * Test find not find.
     */
    @Test
    public void testFindNotFind(){
        User zhangfei = new User("张飞", 23);
        List<User> list = toList(zhangfei);

        assertEquals(null, CollectionsUtil.find(list, "name", "关羽"));
    }

    //---------------------------------------------------------------

    /**
     * Test find null iterable.
     */
    @Test
    public void testFindNullIterable(){
        assertEquals(null, CollectionsUtil.find(null, "name", "关羽"));
    }

    /**
     * Test find null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testFindNullPropertyName(){
        CollectionsUtil.find(new ArrayList<>(), null, "关羽");
    }

    /**
     * Test find empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindEmptyPropertyName(){
        CollectionsUtil.find(new ArrayList<>(), "", "关羽");
    }

    /**
     * Test find blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindBlankPropertyName(){
        CollectionsUtil.find(new ArrayList<>(), " ", "关羽");
    }
}
