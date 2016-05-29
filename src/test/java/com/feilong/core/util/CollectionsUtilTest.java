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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.feilong.core.util.predicate.BeanPropertyValueEqualsPredicate;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class CollectionUtilTest.
 * 
 * @author feilong
 */
public class CollectionsUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsUtilTest.class);

    /**
     * Test remove.
     */
    @Test
    public void testRemove(){
        List<String> list = new ArrayList<String>();
        list.add("xinge");
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong2");

        LOGGER.info("list:{}", JsonUtil.format(CollectionsUtil.remove(list, "feilong2")));
        LOGGER.info("list:{}", JsonUtil.format(list));
    }

    @Test
    public void testPartition(){
        List<String> list = new ArrayList<String>();
        list.add("xinge");
        list.add("feilong1");
        list.add("feilong2");

        LOGGER.info("list:{}", JsonUtil.format(ListUtils.partition(list, 2)));
    }

    @Test
    public void testCollect(){
        List<String> list = new ArrayList<String>();
        list.add("xinge");
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong2");

        Transformer<String, Object> nullTransformer = TransformerUtils.nullTransformer();
        List<Object> collect = CollectionsUtil.collect(list, nullTransformer);
        LOGGER.info("list:{}", JsonUtil.format(collect, 0, 0));

    }

    @Test
    public void testCollect1(){
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(100L);

        List<String> collect1 = CollectionsUtil.collect(list, TransformerUtils.stringValueTransformer());
        LOGGER.info("list:{}", JsonUtil.format(collect1, 0, 0));
    }

    @Test
    public void testCollect2(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        Transformer<User, String> invokerTransformer = TransformerUtils.invokerTransformer("getName");
        List<String> collect1 = CollectionsUtil.collect(list, invokerTransformer);
        LOGGER.info("list:{}", JsonUtil.format(collect1, 0, 0));
    }

    @Test
    public void testCollect3(){
        List<User> list = new ArrayList<User>();
        list.add(new User("张飞", 23));
        list.add(new User("关羽", 24));
        list.add(new User("刘备", 25));

        List<String> collect1 = CollectionsUtil.collect(list, TransformerUtils.constantTransformer("jintian"));
        LOGGER.info("list:{}", JsonUtil.format(collect1, 0, 0));
    }

    /**
     * Removes the duplicate.
     */
    @Test
    public void removeDuplicate(){
        List<String> list = new ArrayList<String>();
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong2");
        list.add("feilong3");

        LOGGER.info(JsonUtil.format(CollectionsUtil.removeDuplicate(list)));
        LOGGER.info(JsonUtil.format(CollectionsUtil.removeDuplicate(null)));
    }

    /**
     * Test group count.
     */
    @Test
    public void testGroupCount(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User("张飞", 23));
        testList.add(new User("关羽", 24));
        testList.add(new User("刘备", 25));

        Map<String, Integer> map = CollectionsUtil.groupCount(testList, null, "name");
        LOGGER.info(JsonUtil.format(map));

        map = CollectionsUtil.groupCount(testList, new BeanPropertyValueEqualsPredicate<User>("name", "刘备"), "name");
        LOGGER.info(JsonUtil.format(map));
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
        LOGGER.info(JsonUtil.format(map));
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
        LOGGER.info(JsonUtil.format(map));
    }

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
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * Test select.
     */
    @Test
    public void testSelect(){
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));

        List<String> list = new ArrayList<String>();
        list.add("张飞");
        list.add("刘备");
        LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, "name", list)));
    }

    @Test
    public void testFind(){
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));
        objectCollection.add(new User("关羽", 24));

        LOGGER.info(JsonUtil.format(CollectionsUtil.find(objectCollection, "name", "关羽")));
    }

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
        LOGGER.info(JsonUtil.format(user));
    }

    @Test
    public void testSelectValue(){
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));
        objectCollection.add(new User("关羽", 24));

        LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, "name", "关羽")));
    }

    @Test
    public void testSelectArray(){
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));

        String[] array = { "刘备", "关羽" };
        LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, "name", array)));
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
        LOGGER.info(JsonUtil.format(CollectionsUtil.select(list, new EqualPredicate<Long>(1L))));
        LOGGER.info(JsonUtil.format(CollectionsUtil.select(null, new EqualPredicate<Long>(1L))));
    }

    /**
     * Test remove all.
     */
    @Test
    public void testRemoveAll(){
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));

        List<String> list = new ArrayList<String>();
        list.add("张飞");
        list.add("刘备");

        List<User> removeAll = CollectionsUtil.removeAll(objectCollection, "name", list);
        LOGGER.info(JsonUtil.format(removeAll));
    }

    /**
     * Test remove all1.
     */
    @Test
    public void testRemoveAll1(){
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));

        List<User> removeAll = CollectionsUtil.removeAll(objectCollection, "name", "刘备");
        LOGGER.info(JsonUtil.format(removeAll));

        LOGGER.info(JsonUtil.format(CollectionsUtil.selectRejected(objectCollection, "name", "刘备")));
    }

    /**
     * Test select rejected.
     */
    @Test
    public void testSelectRejected(){
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));

        List<String> list = new ArrayList<String>();
        list.add("张飞");
        list.add("刘备");
        LOGGER.info(JsonUtil.format(CollectionsUtil.selectRejected(objectCollection, "name", list)));
    }

    /**
     * To array.
     */

    /**
     * Convert list to string replace brackets.
     * 
     */
    @Test
    public void testGetFieldValueMap(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User("张飞", 23));
        testList.add(new User("关羽", 24));
        testList.add(new User("刘备", 25));

        Map<String, Integer> map = CollectionsUtil.getPropertyValueMap(testList, "name", "age");
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * Test get field value list.
     */
    @Test
    public void testGetFieldValueList(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User(2L));
        testList.add(new User(5L));
        testList.add(new User(5L));

        List<Long> fieldValueCollection = CollectionsUtil.getPropertyValueList(testList, "id");
        fieldValueCollection.add(7L);
        fieldValueCollection.add(8L);
        LOGGER.info(JsonUtil.format(fieldValueCollection));
    }

    /**
     * Test get field value set.
     */
    @Test
    public void testGetFieldValueSet(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User(2L));
        testList.add(new User(5L));
        testList.add(new User(5L));

        Set<Long> fieldValueCollection = CollectionsUtil.getPropertyValueSet(testList, "id");
        LOGGER.info(JsonUtil.format(fieldValueCollection));
    }

    /**
     * Test avg.
     */
    @Test
    public void testAvg(){
        List<User> list = new ArrayList<User>();
        list.add(new User(2L));
        list.add(new User(5L));
        list.add(new User(5L));

        assertEquals(new BigDecimal("4.00"), CollectionsUtil.avg(list, 2, "id"));

    }

    /**
     * Test avg2.
     */
    @Test
    public void testAvg2(){
        List<User> list = new ArrayList<User>();

        User user1 = new User(2L);
        user1.setAge(18);
        list.add(user1);

        User user2 = new User(3L);
        user2.setAge(30);
        list.add(user2);

        Map<String, BigDecimal> map = CollectionsUtil.avg(list, 2, "id", "age");
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * Test sum.
     */
    @Test
    public void testSum(){
        List<User> list = new ArrayList<User>();
        list.add(new User(2L));
        list.add(new User(5L));
        list.add(new User(5L));

        assertEquals(new BigDecimal(12L), CollectionsUtil.sum(list, "id"));
        assertEquals(null, CollectionsUtil.sum(null, "id"));
    }

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
        List<User> list = new ArrayList<User>();

        User user1 = new User(2L);
        user1.setAge(18);
        list.add(user1);

        User user2 = new User(3L);
        user2.setAge(30);
        list.add(user2);

        Map<String, BigDecimal> map = CollectionsUtil.sum(list, "id", "age");
        LOGGER.info("{}", JsonUtil.format(map));
    }

    @Test
    public void testSum3(){
        List<User> list = new ArrayList<User>();

        User user1 = new User(10L);
        user1.setName("刘备");
        user1.setAge(50);
        list.add(user1);

        User user2 = new User(20L);
        user1.setName("关羽");
        user2.setAge(50);
        list.add(user2);

        User user3 = new User(100L);
        user3.setName("张飞");
        user3.setAge(100);
        list.add(user3);

        Map<String, BigDecimal> map = CollectionsUtil.sum(list, new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return !"张飞".equals(user.getName());
            }
        }, "id", "age");
        LOGGER.info("{}", JsonUtil.format(map));
    }

    /**
     * Gets the field value list1.
     * 
     */
    @Test
    public void testGetFieldValueList1(){

        List<UserAddress> userAddresseList = new ArrayList<UserAddress>();
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress("中南海");
        userAddresseList.add(userAddress);

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

        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);

        //数组
        List<String> fieldValueList1 = CollectionsUtil.getPropertyValueList(userList, "loves[1]");
        LOGGER.info(JsonUtil.format(fieldValueList1));

        //级联对象
        List<Integer> fieldValueList2 = CollectionsUtil.getPropertyValueList(userList, "userInfo.age");
        LOGGER.info(JsonUtil.format(fieldValueList2));

        //Map
        List<Integer> attrList = CollectionsUtil.getPropertyValueList(userList, "attrMap(蜀国)");
        LOGGER.info(JsonUtil.format(attrList));

        //集合
        List<String> addressList = CollectionsUtil.getPropertyValueList(userList, "userAddresseList[0]");
        LOGGER.info(JsonUtil.format(addressList));
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
