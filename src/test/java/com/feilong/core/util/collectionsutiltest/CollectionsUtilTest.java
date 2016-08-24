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

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CollectionsUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsUtilTest.class);

    /**
     * Test index of.
     */
    //************CollectionsUtil.indexOf(List<User>, String, String)******************************
    @Test
    public void testIndexOf(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));

        assertEquals(0, CollectionsUtil.indexOf(list, "name", "张飞"));
        assertEquals(1, CollectionsUtil.indexOf(list, "age", 24));
    }

    /**
     * Test index of not find value.
     */
    @Test
    public void testIndexOfNotFindValue(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        assertEquals(-1, CollectionsUtil.indexOf(list, "age", 240));
        assertEquals(-1, CollectionsUtil.indexOf(list, "age", null));
    }

    /**
     * Test index of null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testIndexOfNullPropertyName(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        CollectionsUtil.indexOf(list, null, 240);
    }

    /**
     * Test index of empty property name 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexOfEmptyPropertyName1(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        CollectionsUtil.indexOf(list, "", 240);
    }

    /**
     * Test index of empty property name 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexOfEmptyPropertyName2(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        CollectionsUtil.indexOf(list, " ", 240);
    }

    /**
     * Test index of null list.
     */
    @Test
    public void testIndexOfNullList(){
        assertEquals(-1, CollectionsUtil.indexOf(null, "age", 24));
    }

    /**
     * Test index of empty list.
     */
    @Test
    public void testIndexOfEmptyList(){
        assertEquals(-1, CollectionsUtil.indexOf(new ArrayList<User>(), "age", 24));
    }

    //*************************************************************************************

    /**
     * Test collect.
     */
    @Test
    public void testCollect(){
        List<String> list = toList("xinge", "feilong1", "feilong2", "feilong2");

        Transformer<String, Object> nullTransformer = TransformerUtils.nullTransformer();
        List<Object> collect = CollectionsUtil.collect(list, nullTransformer);

        Object[] objects = { null, null, null, null };
        assertThat(collect, hasItems(objects));
    }

    /**
     * Test collect1.
     */
    @Test
    public void testCollect1(){
        List<String> collect1 = CollectionsUtil.collect((List<Long>) null, TransformerUtils.stringValueTransformer());
        assertEquals(emptyList(), collect1);
    }

    /**
     * Test collect5.
     */
    @Test
    public void testCollect5(){
        List<String> collect1 = CollectionsUtil.collect(new ArrayList<Long>(), TransformerUtils.stringValueTransformer());
        assertEquals(emptyList(), collect1);
    }

    /**
     * Test collect2.
     */
    @Test
    public void testCollect2(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));

        Transformer<User, String> invokerTransformer = TransformerUtils.invokerTransformer("getName");
        List<String> collect1 = CollectionsUtil.collect(list, invokerTransformer);

        assertThat(collect1, hasItems("张飞", "关羽", "刘备"));
    }

    /**
     * Test collect3.
     */
    @Test
    public void testCollect3(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));

        List<String> collect1 = CollectionsUtil.collect(list, TransformerUtils.constantTransformer("jintian"));
        assertThat(collect1, hasItems("jintian", "jintian", "jintian"));
    }

    /**
     * Test group one.
     */
    @Test
    public void testGroupOne(){
        User zhangfei = new User("张飞", 23);
        User liubei25 = new User("刘备", 25);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei, liubei25, liubei30);
        Map<String, User> map = CollectionsUtil.groupOne(list, "name");

        assertThat(map.keySet(), is(hasSize(2)));
        assertThat(map, allOf(//
                        hasEntry("张飞", zhangfei),
                        hasEntry("刘备", liubei25),
                        not(hasEntry("刘备", liubei30))));
    }

    /**
     * Test group.
     */
    @Test
    public void testGroup(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("刘备", 25),
                        new User("刘备", 25));

        Map<String, List<User>> map = CollectionsUtil.group(list, "name");
        LOGGER.debug(JsonUtil.format(map));
    }

    /**
     * Test group2.
     */
    @Test
    public void testGroup2(){
        User zhangfei28 = new User("张飞", 28);
        User liubei32 = new User("刘备", 32);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(//
                        new User("张飞", 10),
                        zhangfei28,
                        liubei32,
                        liubei30,
                        new User("刘备", 10));

        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
        Map<String, List<User>> map = CollectionsUtil.group(list, "name", comparatorPredicate);
        LOGGER.debug(JsonUtil.format(map));

        assertThat(map, allOf(//
                        hasKey("张飞"),
                        hasKey("刘备"),
                        hasEntry(is("张飞"), hasItem(zhangfei28)),
                        hasEntry(is("刘备"), hasItems(liubei32, liubei30))));
        assertSame(2, map.size());

    }

    /**
     * Test select.
     */
    @Test
    public void testSelect(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);
        assertThat(
                        CollectionsUtil.select(list, "name", toList("张飞", "刘备")),
                        allOf(hasItem(zhangfei), hasItem(liubei), not(hasItem(guanyu))));
    }

    /**
     * Test select 1.
     */
    @Test
    public void testSelect1(){
        //查询 >10 的元素
        Predicate<Integer> predicate = new ComparatorPredicate<Integer>(10, ComparatorUtils.<Integer> naturalComparator(), Criterion.LESS);

        List<Integer> result = CollectionsUtil.select(toList(1, 5, 10, 30, 55, 88, 1, 12, 3), predicate);
        LOGGER.debug(JsonUtil.format(result, 0, 0));
    }

    /**
     * Test find.
     */
    @Test
    public void testFind(){
        User zhangfei = new User("张飞", 23);
        User guanyu24 = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        User guanyu50 = new User("关羽", 50);
        List<User> list = toList(zhangfei, guanyu24, liubei, guanyu50);

        assertThat(CollectionsUtil.find(list, "name", "关羽"), is(equalTo(guanyu24)));
    }

    /**
     * Test find2.
     */
    @Test
    public void testFind2(){
        User guanyu30 = new User("关羽", 30);
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25),
                        guanyu30);
        Predicate<User> predicate = PredicateUtils
                        .andPredicate(BeanPredicateUtil.equalPredicate("name", "关羽"), BeanPredicateUtil.equalPredicate("age", 30));

        assertEquals(guanyu30, CollectionsUtil.find(list, predicate));
    }

    /**
     * Test select value.
     */
    @Test
    public void testSelectValue(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei, guanyu);

        assertThat(
                        CollectionsUtil.select(list, "name", "关羽"),
                        allOf(hasItem(guanyu), hasItem(guanyu), not(hasItem(zhangfei)), not(hasItem(liubei))));
    }

    /**
     * Test select array.
     */
    @Test
    public void testSelectArray(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> select = CollectionsUtil.select(list, "name", "刘备", "关羽");
        assertThat(select, allOf(hasItem(liubei), hasItem(guanyu), not(hasItem(zhangfei))));
    }

    /**
     * Test select predicate 1.
     */
    @Test
    public void testSelectPredicate1(){
        List<Long> list = toList(1L, 1L, 2L, 3L);
        assertThat(CollectionsUtil.select(list, new EqualPredicate<Long>(1L)), contains(1L, 1L));
        assertEquals(emptyList(), CollectionsUtil.select(null, new EqualPredicate<Long>(1L)));
    }

    /**
     * Test select rejected1.
     */
    @Test
    public void testSelectRejected1(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> selectRejected = CollectionsUtil.selectRejected(list, "name", "刘备", "张飞");
        assertSame(1, selectRejected.size());
        assertThat(selectRejected.get(0), hasProperty("name", equalTo("关羽")));
    }

    /**
     * Test select rejected.
     */
    @Test
    public void testSelectRejected(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        List<User> selectRejected = CollectionsUtil.selectRejected(list, "name", toList("张飞", "刘备"));
        assertThat(selectRejected, hasSize(1));
        assertThat(selectRejected, allOf(//
                        hasItem(guanyu),
                        not(hasItem(zhangfei)),
                        not(hasItem(liubei))));
    }

    //******************************************************************************************************

    /**
     * Test get property value map.
     */
    @Test
    public void testGetPropertyValueMap(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 24);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        Map<String, Integer> map = CollectionsUtil.getPropertyValueMap(list, "name", "age");

        assertThat(map, allOf(//
                        hasEntry("张飞", 23),
                        hasEntry("关羽", 24),
                        hasEntry("刘备", 25)));
    }

    /**
     * Test get property value list.
     */
    @Test
    public void testGetPropertyValueList(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        List<Long> resultList = CollectionsUtil.getPropertyValueList(list, "id");
        assertThat(resultList, contains(2L, 5L, 5L));

        resultList.add(7L);
        resultList.add(8L);

        assertThat(resultList, contains(2L, 5L, 5L, 7L, 8L));
    }

    /**
     * Test get property value list1.
     */
    @Test
    public void testGetPropertyValueList1(){
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress("中南海");
        List<UserAddress> userAddresseList = toList(userAddress);

        //*******************************************************
        Map<String, String> attrMap = new HashMap<String, String>();
        attrMap.put("蜀国", "赵子龙");
        attrMap.put("魏国", "张文远");
        attrMap.put("吴国", "甘兴霸");

        //*******************************************************
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setAge(28);

        //*******************************************************

        User user1 = new User(2L);

        user1.setLoves(toArray("sanguo1", "xiaoshuo1"));
        user1.setUserInfo(userInfo1);
        user1.setAttrMap(attrMap);
        user1.setUserAddresseList(userAddresseList);

        //*****************************************************
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setAge(null);

        User user2 = new User(3L);
        user2.setLoves(toArray("sanguo2", "xiaoshuo2"));
        user2.setUserInfo(userInfo2);
        user2.setAttrMap(attrMap);
        user2.setUserAddresseList(userAddresseList);

        List<User> userList = toList(user1, user2);

        //数组
        List<String> fieldValueList1 = CollectionsUtil.getPropertyValueList(userList, "loves[1]");
        LOGGER.debug(JsonUtil.format(fieldValueList1));

        //级联对象
        List<Integer> fieldValueList2 = CollectionsUtil.getPropertyValueList(userList, "userInfo.age");
        LOGGER.debug(JsonUtil.format(fieldValueList2));

        //Map
        List<Integer> attrList = CollectionsUtil.getPropertyValueList(userList, "attrMap(蜀国)");
        LOGGER.debug(JsonUtil.format(attrList));

        //集合
        List<String> addressList = CollectionsUtil.getPropertyValueList(userList, "userAddresseList[0]");
        LOGGER.debug(JsonUtil.format(addressList));
    }

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
}
