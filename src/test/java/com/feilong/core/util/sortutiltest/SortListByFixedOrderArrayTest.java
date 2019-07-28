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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.SortUtil.sortListByFixedOrderArray;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class SortListByFixedOrderArrayTest{

    private static final List<String> TEST_LIST = toList("张飞", "关羽", "刘备");

    //---------------------------------------------------------------
    //fixedOrderItemList 少
    @Test
    public void testSortByFixedOrderArray1222(){
        List<String> returnList = sortListByFixedOrderArray(TEST_LIST, toArray("刘备"));
        assertThat(returnList, contains("刘备", "张飞", "关羽"));
    }

    //fixedOrderItemList 少
    @Test
    public void testSortByFixedOrderArray(){
        List<String> returnList = sortListByFixedOrderArray(TEST_LIST, toArray("刘备", "关羽"));
        assertThat(returnList, contains("刘备", "关羽", "张飞"));
    }

    //fixedOrderItemList 多
    @Test
    public void testSortByFixedOrderArray12(){
        assertThat(
                        sortListByFixedOrderArray(toList("张飞", "关羽", "刘备"), toArray("刘备", "吕布", "张飞", "关羽")), //
                        contains("刘备", "张飞", "关羽"));
    }

    //相符
    @Test
    public void testSortByFixedOrderArray1(){
        assertThat(
                        sortListByFixedOrderArray(toList("张飞", "关羽", "刘备"), toArray("刘备", "张飞", "关羽")), //
                        contains("刘备", "张飞", "关羽"));
    }

    //---------------------------------------------------------------

    @Test
    public void testSortByFixedOrderArrayNullList(){
        assertEquals(emptyList(), sortListByFixedOrderArray(null, toArray("刘备", "关羽")));
    }

    @Test
    public void testSortByFixedOrderArrayEmptyList(){
        List<String> list = emptyList();
        assertEquals(list, sortListByFixedOrderArray(list, toArray("刘备", "关羽")));
    }

    //---------------------------------------------------------------

    @Test
    public void testSortByFixedOrderArrayNullPropertyValues(){
        assertEquals(TEST_LIST, sortListByFixedOrderArray(TEST_LIST, null));
    }

    @Test
    public void testSortByFixedOrderArrayNullEmpty(){
        assertEquals(TEST_LIST, sortListByFixedOrderArray(TEST_LIST, EMPTY_STRING_ARRAY));
    }
}
