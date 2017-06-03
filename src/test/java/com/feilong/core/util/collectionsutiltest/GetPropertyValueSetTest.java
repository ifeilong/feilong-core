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

import static java.util.Collections.emptySet;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionsUtilGetPropertyValueSetTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetPropertyValueSetTest{

    /**
     * Test get property value set.
     */
    @Test
    public void testGetPropertyValueSet(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        Set<Long> set = CollectionsUtil.getPropertyValueSet(list, "id");
        assertThat(set, contains(2L, 5L));
    }

    /**
     * Test get property value set null collection.
     */
    @Test
    public void testGetPropertyValueSetNullCollection(){
        assertEquals(emptySet(), CollectionsUtil.getPropertyValueSet(null, "id"));
    }

    /**
     * Test get property value set empty collection.
     */
    @Test
    public void testGetPropertyValueSetEmptyCollection(){
        assertEquals(emptySet(), CollectionsUtil.getPropertyValueSet(new ArrayList<>(), "id"));
    }

    /**
     * Test get property value set null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetPropertyValueSetNullPropertyName(){
        List<User> list = toList(new User(2L), new User(5L), new User(5L));
        CollectionsUtil.getPropertyValueSet(list, null);
    }

    /**
     * Test get property value set empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueSetEmptyPropertyName(){
        List<User> list = toList(new User(2L), new User(5L), new User(5L));
        CollectionsUtil.getPropertyValueSet(list, "");
    }

    /**
     * Test get property value set empty property name 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueSetEmptyPropertyName1(){
        List<User> list = toList(new User(2L), new User(5L), new User(5L));
        CollectionsUtil.getPropertyValueSet(list, " ");
    }

}
