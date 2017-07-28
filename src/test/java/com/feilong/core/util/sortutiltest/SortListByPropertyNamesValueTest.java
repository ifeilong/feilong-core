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

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.SortUtil.sortListByPropertyNamesValue;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.store.member.User;

/**
 * The Class SortUtilSortListByPropertyNamesValueTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SortListByPropertyNamesValueTest{

    @Test
    public void testSortByPropertyNamesValueMap(){
        Map<String, Integer> map18_145 = toMap("age", 18, "height", 145);
        Map<String, Integer> map14_120 = toMap("age", 14, "height", 120);
        Map<String, Integer> map5_45 = toMap("age", 5, "height", 45);
        Map<String, Integer> map20_170 = toMap("age", 20, "height", 170);

        List<Map<String, Integer>> list = toList(map18_145, map14_120, map5_45, map20_170);
        sortListByPropertyNamesValue(list, "age");

        assertThat(list, contains(map5_45, map14_120, map18_145, map20_170));

    }

    @Test
    public void testSortByPropertyNamesValueMapDesc(){
        Map<String, Integer> map18_145 = toMap("age", 18, "height", 145);
        Map<String, Integer> map14_120 = toMap("age", 14, "height", 120);
        Map<String, Integer> map5_45 = toMap("age", 5, "height", 45);
        Map<String, Integer> map20_170 = toMap("age", 20, "height", 170);

        List<Map<String, Integer>> list = toList(map18_145, map14_120, map5_45, map20_170);
        sortListByPropertyNamesValue(list, "age desc");

        assertThat(list, contains(map20_170, map18_145, map14_120, map5_45));

    }

    //---------------------------------------------------------------

    @Test
    public void testSortByPropertyNamesValueWithNullPropertyValueDesc(){
        User u_null_id = new User((Long) null);
        User id12 = new User(12L);
        User id2 = new User(2L);
        User u_null = null;
        User id1 = new User(1L);

        List<User> list = toList(u_null_id, id12, id2, u_null, id1, u_null_id);
        sortListByPropertyNamesValue(list, "id desc");
        assertThat(list, contains(u_null, id12, id2, id1, u_null_id, u_null_id));

    }

    @Test
    public void testSortByPropertyNamesValueWithNullPropertyValueAsc(){
        User u_null_id = new User((Long) null);
        User id12 = new User(12L);
        User id2 = new User(2L);
        User u_null = null;
        User id1 = new User(1L);

        List<User> list = toList(u_null_id, id12, id2, u_null, id1, u_null_id);
        sortListByPropertyNamesValue(list, "id asc");
        assertThat(list, contains(u_null_id, u_null_id, id1, id2, id12, u_null));
    }
    //

    /**
     * Test property comparator.
     */
    @Test
    public void testSortByPropertyNamesValueWithNullPropertyValue(){
        User u_null_id = new User((Long) null);
        User id12 = new User(12L);
        User id2 = new User(2L);
        User u_null = null;
        User id1 = new User(1L);

        List<User> list = toList(u_null_id, id12, id2, u_null, id1, u_null_id);
        sortListByPropertyNamesValue(list, "id");
        assertThat(list, contains(u_null_id, u_null_id, id1, id2, id12, u_null));
    }

    /**
     * Test property comparator1.
     */
    @Test
    public void testSortByPropertyNamesValue(){
        User id12 = new User(12L, 18);
        User id2 = new User(2L, 36);
        User id5 = new User(5L, 22);
        User id1 = new User(1L, 8);
        List<User> list = toList(id12, id2, id5, id1);
        sortListByPropertyNamesValue(list, "id");

        assertThat(list, contains(id1, id2, id5, id12));
    }

    /**
     * Test sort by property names value 2 property names.
     */
    @Test
    public void testSortByPropertyNamesValue2PropertyNamesIdDesc(){
        User id12_age18 = new User(12L, 18);
        User id1_age8 = new User(1L, 8);
        User id2_age30 = new User(2L, 30);
        User id2_age2 = new User(2L, 2);
        User id2_age36 = new User(2L, 36);
        List<User> list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);

        sortListByPropertyNamesValue(list, "id desc", "age");
        assertThat(list, contains(id12_age18, id2_age2, id2_age30, id2_age36, id1_age8));
    }

    @Test
    public void testSortByPropertyNamesValue2PropertyNames(){
        User id12_age18 = new User(12L, 18);
        User id1_age8 = new User(1L, 8);
        User id2_age30 = new User(2L, 30);
        User id2_age2 = new User(2L, 2);
        User id2_age36 = new User(2L, 36);
        List<User> list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);

        sortListByPropertyNamesValue(list, "id", "age");
        assertThat(list, contains(id1_age8, id2_age2, id2_age30, id2_age36, id12_age18));
    }

    /**
     * Test sort by property names value age property name.
     */
    @Test
    public void testSortByPropertyNamesValueAgePropertyName(){
        User id12_age18 = new User(12L, 18);
        User id1_age8 = new User(1L, 8);
        User id2_age30 = new User(2L, 30);
        User id2_age2 = new User(2L, 2);
        User id2_age36 = new User(2L, 36);
        List<User> list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);

        sortListByPropertyNamesValue(list, "age");

        assertThat(list, contains(id2_age2, id1_age8, id12_age18, id2_age30, id2_age36));
    }

    /**
     * Test sort by property names value null list 1 property name.
     */
    @Test
    public final void testSortByPropertyNamesValueNullList1PropertyName(){
        assertEquals(emptyList(), sortListByPropertyNamesValue((List) null, "name"));
    }

    /**
     * Test sort by property names value null list.
     */
    @Test
    public final void testSortByPropertyNamesValueNullList(){
        assertEquals(emptyList(), sortListByPropertyNamesValue((List) null, "name", "age"));
    }

    /**
     * Test sort by property names value null property names.
     */
    @Test(expected = NullPointerException.class)
    public void testSortByPropertyNamesValueNullPropertyNames(){
        User id12_age18 = new User(12L, 18);
        User id1_age8 = new User(1L, 8);
        List<User> list = toList(id12_age18, id1_age8);
        sortListByPropertyNamesValue(list, null);
    }

    /**
     * Test sort by property names value empty property names.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSortByPropertyNamesValueEmptyPropertyNames(){
        User id12_age18 = new User(12L, 18);
        User id1_age8 = new User(1L, 8);
        List<User> list = toList(id12_age18, id1_age8);
        sortListByPropertyNamesValue(list, ConvertUtil.<String> toArray());
    }

    //    @Test(expected = IllegalArgumentException.class)
    //    public void testSortByPropertyNamesValueNullElementPropertyNames1(){
    //        User id12_age18 = new User(12L, 18);
    //        User id1_age8 = new User(1L, 8);
    //        List<User> list = toList(id12_age18, id1_age8);
    //        sortByPropertyNamesValue(list, ConvertUtil.<String> toArray("id", (String) null));
    //    }
}
