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
package com.feilong.core.util.sortutiltest;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.SortUtil.sortListByFixedOrderPropertyValueList;

/**
 * The Class SortUtilSortListByFixedOrderListPropertyValuesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SortListByFixedOrderListPropertyValuesTest{

    /**
     * Test sort by fixed order property value list.
     */
    @Test
    public void testSortByFixedOrderPropertyValueList(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> returnList = CollectionsUtil.select(list, "name", toList("刘备", "关羽"));
        returnList = sortListByFixedOrderPropertyValueList(returnList, "name", toList("刘备", "关羽"));

        assertThat(returnList, contains(liubei, guanyu));
    }

    /**
     * Test sort by fixed order property value list null list.
     */
    @Test
    public void testSortByFixedOrderPropertyValueListNullList(){
        assertEquals(emptyList(), sortListByFixedOrderPropertyValueList(null, "name", toList("刘备", "关羽")));
    }

    /**
     * Test sort by fixed order property value list null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSortByFixedOrderPropertyValueListNullPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 30), new User("刘备", 25));
        sortListByFixedOrderPropertyValueList(list, null, toList("刘备", "关羽"));
    }

    /**
     * Test sort by fixed order property value list empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSortByFixedOrderPropertyValueListEmptyPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 30), new User("刘备", 25));
        sortListByFixedOrderPropertyValueList(list, "", toList("刘备", "关羽"));
    }

    /**
     * Test sort by fixed order property value list blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSortByFixedOrderPropertyValueListBlankPropertyName(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 30), new User("刘备", 25));
        sortListByFixedOrderPropertyValueList(list, " ", toList("刘备", "关羽"));
    }

    //****

    /**
     * Test sort by fixed order property value list null property values.
     */
    @Test(expected = NullPointerException.class)
    public void testSortByFixedOrderPropertyValueListNullPropertyValues(){
        List<User> list = toList(new User("张飞", 23), new User("关羽", 30), new User("刘备", 25));
        sortListByFixedOrderPropertyValueList(list, "name", (List<String>) null);
    }
}
