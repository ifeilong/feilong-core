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

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.predicate.BeanPropertyValueEqualsPredicate;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class CollectionUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CollectionsUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsUtilTest.class);

    /**
     * Test remove.
     */
    @Test
    public void testRemove(){
        List<String> list = new ArrayList<String>(){

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
     * Test add all ignore null.
     */
    @Test
    public void testAddAllIgnoreNull(){
        List<String> list = ConvertUtil.toList("xinge", "feilong1");
        assertEquals(false, CollectionsUtil.addAllIgnoreNull(list, null));
    }

    /**
     * Test add all ignore null2.
     */
    @Test
    public void testAddAllIgnoreNull2(){
        List<String> list = ConvertUtil.toList("xinge", "feilong1");
        boolean addAllIgnoreNull = CollectionsUtil.addAllIgnoreNull(list, ConvertUtil.toList("xinge", "feilong1"));
        assertEquals(true, addAllIgnoreNull);
        assertThat(list, hasItems("xinge", "feilong1", "xinge", "feilong1"));
    }

    /**
     * Test add all ignore null1.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllIgnoreNull1(){
        CollectionsUtil.addAllIgnoreNull(null, null);
    }

    /**
     * Test partition.
     */
    @Test
    public void testPartition(){
        List<String> list = ConvertUtil.toList("xinge", "feilong1", "feilong2");
        LOGGER.debug("list:{}", JsonUtil.format(ListUtils.partition(list, 2)));
    }

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
        assertEquals(Collections.emptyList(), collect1);
    }

    /**
     * Test collect5.
     */
    @Test
    public void testCollect5(){
        List<Long> list = new ArrayList<Long>();
        List<String> collect1 = CollectionsUtil.collect(list, TransformerUtils.stringValueTransformer());
        assertEquals(Collections.emptyList(), collect1);
    }

    /**
     * Test collect2.
     */
    @Test
    public void testCollect2(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        Transformer<User, String> invokerTransformer = TransformerUtils.invokerTransformer("getName");
        List<String> collect1 = CollectionsUtil.collect(list, invokerTransformer);

        assertThat(collect1, hasItems("张飞", "关羽", "刘备"));
    }

    /**
     * Test collect3.
     */
    @Test
    public void testCollect3(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        List<String> collect1 = CollectionsUtil.collect(list, TransformerUtils.constantTransformer("jintian"));
        assertThat(collect1, hasItems("jintian", "jintian", "jintian"));
    }

    /**
     * Removes the duplicate.
     */
    @Test
    public void testRemoveDuplicate(){
        List<String> list = ConvertUtil.toList("feilong1", "feilong2", "feilong2", "feilong3");

        List<String> removeDuplicate = CollectionsUtil.removeDuplicate(list);

        assertSame(3, removeDuplicate.size());
        assertThat(removeDuplicate, hasItems("feilong1", "feilong2", "feilong3"));

        assertSame(4, list.size());
        assertThat(list, hasItems("feilong1", "feilong2", "feilong2", "feilong3"));

        assertEquals(Collections.emptyList(), CollectionsUtil.removeDuplicate(null));
    }

    /**
     * Test group count.
     */
    @Test
    public void testGroupCount(){
        List<User> list = ConvertUtil.toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("刘备", 40),
                        new User("赵云", 50));

        Map<String, Integer> map = CollectionsUtil.groupCount(list, "name", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getAge() > 30;
            }
        });

        assertThat(map, allOf(hasEntry("刘备", 1), hasEntry("赵云", 1)));
    }

    /**
     * Test group count1.
     */
    @Test
    public void testGroupCount1(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞"));
        list.add(new User("关羽"));
        list.add(new User("刘备"));
        list.add(new User("刘备"));

        Map<String, Integer> map = CollectionsUtil.groupCount(list, "name");
        assertThat(map, allOf(hasEntry("刘备", 2), hasEntry("张飞", 1), hasEntry("关羽", 1)));
    }

    /**
     * Test remove.
     */
    @Test
    public void testIndexOf(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        assertEquals(0, CollectionsUtil.indexOf(list, "name", "张飞"));
        assertEquals(1, CollectionsUtil.indexOf(list, "age", 24));
        assertEquals(-1, CollectionsUtil.indexOf(null, "age", 24));
        assertEquals(-1, CollectionsUtil.indexOf(new ArrayList<User>(), "age", 24));
    }

    /**
     * Test group one.
     */
    @Test
    public void testGroupOne(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User("张飞", 23));
        testList.add(new User("刘备", 25));
        testList.add(new User("刘备", 25));

        Map<String, User> map = CollectionsUtil.groupOne(testList, "name");
        LOGGER.debug(JsonUtil.format(map));
    }

    /**
     * Test group.
     */
    @Test
    public void testGroup(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User("张飞", 23));
        testList.add(new User("刘备", 25));
        testList.add(new User("刘备", 25));

        Map<String, List<User>> map = CollectionsUtil.group(testList, "name");
        LOGGER.debug(JsonUtil.format(map));
    }

    /**
     * Test group2.
     */
    @Test
    public void testGroup2(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 10));
        list.add(new User("张飞", 28));
        list.add(new User("刘备", 32));
        list.add(new User("刘备", 30));
        list.add(new User("刘备", 10));

        Map<String, List<User>> map = CollectionsUtil.group(list, "name", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getAge() > 20;
            }
        });
        LOGGER.debug(JsonUtil.format(map));

        assertSame(2, map.size());
    }

    /**
     * Test select.
     */
    @Test
    public void testSelect(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        LOGGER.debug(JsonUtil.format(CollectionsUtil.select(list, "name", toList("张飞", "刘备"))));
    }

    /**
     * Test find.
     */
    @Test
    public void testFind(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));
        list.add(new User("关羽", 24));

        LOGGER.debug(JsonUtil.format(CollectionsUtil.find(list, "name", "关羽")));
    }

    /**
     * Test find2.
     */
    @Test
    public void testFind2(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));
        list.add(new User("关羽", 24));

        User user = CollectionsUtil.find(
                        list,
                        PredicateUtils.andPredicate(
                                        new BeanPropertyValueEqualsPredicate<User>("name", "刘备"),
                                        new BeanPropertyValueEqualsPredicate<User>("age", 25)));
        LOGGER.debug(JsonUtil.format(user));
    }

    /**
     * Test select value.
     */
    @Test
    public void testSelectValue(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));
        list.add(new User("关羽", 24));

        LOGGER.debug(JsonUtil.format(CollectionsUtil.select(list, "name", "关羽")));
    }

    /**
     * Test select array.
     */
    @Test
    public void testSelectArray(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        String[] array = { "刘备", "关羽" };
        LOGGER.debug(JsonUtil.format(CollectionsUtil.select(list, "name", array)));
    }

    /**
     * Test select1.
     */
    @Test
    public void testSelectPredicate(){
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(1L);
        list.add(2L);
        list.add(3L);
        LOGGER.debug(JsonUtil.format(CollectionsUtil.select(list, new EqualPredicate<Long>(1L))));
        LOGGER.debug(JsonUtil.format(CollectionsUtil.select(null, new EqualPredicate<Long>(1L))));
    }

    /**
     * Test remove all.
     */
    @Test
    public void testRemoveAll(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));

        List<User> removeAll = CollectionsUtil.removeAll(list, "name", toList("张飞", "刘备"));

        assertThat(removeAll, hasSize(1));
        assertThat(removeAll.get(0), hasProperty("name", equalTo("关羽")));
    }

    /**
     * Test remove all1.
     */
    @Test
    public void testRemoveAll1(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        LOGGER.debug(JsonUtil.format(CollectionsUtil.removeAll(list, "name", "刘备")));
        LOGGER.debug(JsonUtil.format(CollectionsUtil.removeAll(list, "name", "刘备", "关羽")));
    }

    /**
     * Test select rejected1.
     */
    @Test
    public void testSelectRejected1(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));
        List<User> selectRejected = CollectionsUtil.selectRejected(list, "name", "刘备", "张飞");
        assertSame(1, selectRejected.size());
        assertThat(selectRejected.get(0), hasProperty("name", equalTo("关羽")));
    }

    /**
     * Test select rejected.
     */
    @Test
    public void testSelectRejected(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        LOGGER.debug(JsonUtil.format(CollectionsUtil.selectRejected(list, "name", toList("张飞", "刘备"))));
    }

    /**
     * Test get field value map.
     */
    @Test
    public void testGetFieldValueMap(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        Map<String, Integer> map = CollectionsUtil.getPropertyValueMap(list, "name", "age");
        LOGGER.debug(JsonUtil.format(map));

        assertSame(list.size(), map.size());
    }

    /**
     * Test get field value list.
     */
    @Test
    public void testGetFieldValueList(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        List<Long> fieldValueCollection = CollectionsUtil.getPropertyValueList(list, "id");
        fieldValueCollection.add(7L);
        fieldValueCollection.add(8L);

        assertThat(fieldValueCollection, contains(2L, 5L, 5L, 7L, 8L));
    }

    /**
     * Test get field value set.
     */
    @Test
    public void testGetFieldValueSet(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        Set<Long> set = CollectionsUtil.getPropertyValueSet(list, "id");
        assertThat(set, contains(2L, 5L));
    }

    /**
     * Test avg.
     */
    @Test
    public void testAvg(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        assertEquals(new BigDecimal("4.00"), CollectionsUtil.avg(list, "id", 2));
    }

    /**
     * Test avg2.
     */
    @Test
    public void testAvg2(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        Map<String, BigDecimal> map = CollectionsUtil.avg(toList(user1, user2), ConvertUtil.toArray("id", "age"), 2);
        assertThat(map, allOf(hasEntry("id", toBigDecimal("2.50")), hasEntry("age", toBigDecimal("24.00"))));
    }

    /**
     * Test sum.
     */
    @Test
    public void testSum(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        assertEquals(new BigDecimal(12L), CollectionsUtil.sum(list, "id"));
        assertEquals(null, CollectionsUtil.sum(null, "id"));
    }

    /**
     * Test sum4.
     */
    @Test
    public void testSum4(){
        List<User> list = new ArrayList<User>();
        list.add(new User(2L));
        list.add(new User(50L));
        list.add(new User(50L));

        assertEquals(new BigDecimal(100L), CollectionsUtil.sum(list, "id", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));
    }

    /**
     * Test sum2.
     */
    @Test
    public void testSum2(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        Map<String, BigDecimal> map = CollectionsUtil.sum(toList(user1, user2), "id", "age");
        assertThat(map, allOf(hasEntry("id", toBigDecimal(5)), hasEntry("age", toBigDecimal(48))));
    }

    /**
     * Test sum3.
     */
    @Test
    public void testSum3(){
        User user1 = new User(10L);
        user1.setName("刘备");
        user1.setAge(50);

        User user2 = new User(20L);
        user1.setName("关羽");
        user2.setAge(50);

        User user3 = new User(100L);
        user3.setName("张飞");
        user3.setAge(null);

        User user4 = new User((Long) null);
        user4.setName("赵云");
        user4.setAge(100);

        List<User> list = ConvertUtil.toList(user1, user2, user3, user4);
        Map<String, BigDecimal> map = CollectionsUtil.sum(list, ConvertUtil.toArray("id", "age"), new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return !"张飞".equals(user.getName());
            }
        });
        LOGGER.debug(JsonUtil.format(map));
    }

    /**
     * Gets the field value list1.
     * 
     */
    @Test
    public void testGetFieldValueList1(){
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

        User user1 = new User(2L);
        user1.setLoves(new String[] { "sanguo1", "xiaoshuo1" });
        user1.setUserInfo(userInfo1);
        user1.setAttrMap(attrMap);
        user1.setUserAddresseList(userAddresseList);

        //*****************************************************
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setAge(null);

        User user2 = new User(3L);
        user2.setLoves(new String[] { "sanguo2", "xiaoshuo2" });
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
     * TestCollectionsUtilTest.
     */
    @Test
    public void testCollectionsUtilTest(){
        Set<String> set = new LinkedHashSet<String>();

        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        set.add("5");
        set.add("1");

        LOGGER.debug(JsonUtil.format(set));
    }

    /**
     * TestCollectionsUtilTest.
     */
    @Test
    public void testCollectionsUtilTest2(){
        Stack<Object> stack = new Stack<Object>();

        stack.add("1");
        stack.add("2");
        stack.add("3");
        stack.add("4");
        LOGGER.debug("" + stack.firstElement());
        LOGGER.debug("" + stack.peek());
        LOGGER.debug("" + stack.pop());
        LOGGER.debug(JsonUtil.format(stack));
    }

    /**
     * TestCollectionsUtilTest.
     */
    @Test
    public void testCollectionsUtilTest33(){
        Queue<Object> queue = new PriorityQueue<Object>();

        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);

        LOGGER.debug(JsonUtil.format(queue));
        LOGGER.debug("" + queue.peek());
    }
}
