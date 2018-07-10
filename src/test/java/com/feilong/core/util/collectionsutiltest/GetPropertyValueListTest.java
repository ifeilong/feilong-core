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
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

/**
 * The Class GetPropertyValueListTest.
 */
public class GetPropertyValueListTest{

    private static final List<User> list = toList(//
                    new User(2L),
                    new User(5L),
                    new User(5L));

    //---------------------------------------------------------------

    /**
     * Test get property value list.
     */
    @Test
    public void testGetPropertyValueList(){
        List<Long> resultList = CollectionsUtil.getPropertyValueList(list, "id");
        assertThat(resultList, contains(2L, 5L, 5L));

        resultList.add(7L);
        resultList.add(8L);

        assertThat(resultList, contains(2L, 5L, 5L, 7L, 8L));
    }

    //---------------------------------------------------------------

    /**
     * Test get property value list null object collection.
     */
    @Test
    public void testGetPropertyValueListNullObjectCollection(){
        assertEquals(emptyList(), CollectionsUtil.getPropertyValueList(null, "userInfo.age"));
    }

    /**
     * Test get property value list empty object collection.
     */
    @Test
    public void testGetPropertyValueListEmptyObjectCollection(){
        assertEquals(emptyList(), CollectionsUtil.getPropertyValueList(new ArrayList<>(), "userInfo.age"));
    }

    //---------------------------------------------------------------

    /**
     * Test get property value list null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetPropertyValueListNullPropertyName(){
        CollectionsUtil.getPropertyValueList(list, null);
    }

    /**
     * Test get property value list empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueListEmptyPropertyName(){
        CollectionsUtil.getPropertyValueList(list, "");
    }

    /**
     * Test get property value list blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueListBlankPropertyName(){
        CollectionsUtil.getPropertyValueList(list, " ");
    }

}
