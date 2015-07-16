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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.core.util.predicate.ObjectPropertyEqualsPredicate;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;

/**
 * The Class CollectionUtilTest.
 * 
 * @author feilong
 * @version 1.0.7 2014-5-22 21:55:38
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
        list.add("feilong5");
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong2");
        list.add("feilong3");
        list.add("feilong4");
        list.add("feilong4");
        list.add("feilong5");

        LOGGER.info("list:{}", JsonUtil.format(CollectionsUtil.remove(list, "feilong2")));
        LOGGER.info("list:{}", JsonUtil.format(list));
    }

    /**
     * Removes the duplicate.
     */
    @Test
    public void removeDuplicate(){
        List<String> list = new ArrayList<String>();
        list.add("xinge");
        list.add("feilong5");
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong2");
        list.add("feilong3");
        list.add("feilong4");
        list.add("feilong4");
        list.add("feilong5");

        LOGGER.info("list:{}", JsonUtil.format(CollectionsUtil.removeDuplicate(list)));
    }

    /**
     * Test group count.
     */
    @Test
    public final void testGroupCount(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User("张飞", 23));
        testList.add(new User("关羽", 24));
        testList.add(new User("刘备", 25));

        Map<String, Integer> map = CollectionsUtil.groupCount(testList, null, "name");
        LOGGER.info(JsonUtil.format(map));

        map = CollectionsUtil.groupCount(testList, new ObjectPropertyEqualsPredicate<User>("name", "刘备"), "name");
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * To array.
     */
    @Test
    public final void toArray(){
        List<String> testList = new ArrayList<String>();
        testList.add("xinge");
        testList.add("feilong");

        String[] array = CollectionsUtil.toArray(testList);
        LOGGER.info(JsonUtil.format(array));
    }

    /**
     * Convert list to string replace brackets.
     * 
     */
    @Test
    public final void testGetFieldValueMap(){
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
    public final void testGetFieldValueList(){
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
    public final void testGetFieldValueSet(){
        List<User> testList = new ArrayList<User>();
        testList.add(new User(2L));
        testList.add(new User(5L));
        testList.add(new User(5L));

        Set<Long> fieldValueCollection = CollectionsUtil.getPropertyValueSet(testList, "id");
        fieldValueCollection.add(7L);
        fieldValueCollection.add(8L);
        fieldValueCollection.add(5L);
        LOGGER.info(JsonUtil.format(fieldValueCollection));
    }

    /**
     * Gets the field value list1.
     * 
     */
    @Test
    public final void testGetFieldValueList1(){
        List<User> testList = new ArrayList<User>();

        User user;
        UserInfo userInfo;

        //*******************************************************
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
        String[] lovesStrings1 = { "sanguo1", "xiaoshuo1" };
        userInfo = new UserInfo();
        userInfo.setAge(28);

        user = new User(2L);
        user.setLoves(lovesStrings1);
        user.setUserInfo(userInfo);
        user.setUserAddresseList(userAddresseList);

        user.setAttrMap(attrMap);
        testList.add(user);

        //*****************************************************
        String[] lovesStrings2 = { "sanguo2", "xiaoshuo2" };
        userInfo = new UserInfo();
        userInfo.setAge(null);

        user = new User(3L);
        user.setLoves(lovesStrings2);
        user.setUserInfo(userInfo);
        user.setUserAddresseList(userAddresseList);
        user.setAttrMap(attrMap);
        testList.add(user);

        //数组
        List<String> fieldValueList1 = CollectionsUtil.getPropertyValueList(testList, "loves[1]");
        LOGGER.info(JsonUtil.format(fieldValueList1));

        //级联对象
        List<Integer> fieldValueList2 = CollectionsUtil.getPropertyValueList(testList, "userInfo.age");
        LOGGER.info(JsonUtil.format(fieldValueList2));

        //Map
        List<Integer> attrList = CollectionsUtil.getPropertyValueList(testList, "attrMap(蜀国)");
        LOGGER.info(JsonUtil.format(attrList));

        //集合
        List<String> addressList = CollectionsUtil.getPropertyValueList(testList, "userAddresseList[0]");
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

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(set));
        }

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

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("" + stack.firstElement());
            LOGGER.debug("" + stack.peek());
            LOGGER.debug("" + stack.pop());
            LOGGER.debug(JsonUtil.format(stack));
        }

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

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(queue));
            LOGGER.debug("" + queue.peek());

        }

    }

    /**
     * Test map to enumeration.
     */
    public void testMapToEnumeration(){
        // Enumeration
        final Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        map.put("jinxin", 1);
        map.put(2, 2);
        map.put("甲", 3);
        map.put(4, 4);
        map.put("jinxin1", 1);
        map.put(21, 2);
        map.put("甲1", 3);
        map.put(41, 4);
        Enumeration<Object> enumeration = CollectionsUtil.toEnumeration(map.keySet());
        while (enumeration.hasMoreElements()){
            LOGGER.info("" + enumeration.nextElement());
        }
    }

    /**
     * 集合转成字符串.
     */
    @Test
    public void testCollectionToString(){
        List<String> list = new ArrayList<String>();
        list.add("2548");
        list.add("");

        ToStringConfig toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);

        assertEquals("2548", CollectionsUtil.toString(list, toStringConfig));
    }

    /**
     * To list.
     */
    @Test
    public void toList(){
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, "a", "b");
        Enumeration<String> enumeration = CollectionsUtil.toEnumeration(list);
        List<String> list2 = CollectionsUtil.toList(enumeration);
        LOGGER.info(JsonUtil.format(list2));

        enumeration = null;
        list2 = CollectionsUtil.toList(enumeration);
        LOGGER.info(JsonUtil.format(list2));
    }

    /**
     * Test.
     */
    @Test
    public void test(){
        //        Class clz = User.class;
        //        Collection collection = CollectionUtils.typedCollection(new ArrayList(), clz);
        //        collection.add(clz.newInstance());
        //
        //        LOGGER.info(collection.size() + "");
        //        for (Object object : collection){
        //            User user = (User) object;
        //            LOGGER.info(user.getName());
        //        }
        //
        //        LOGGER.info("hahahah");
        //
        //        Collection<User> collection2 = collection;
        //        LOGGER.info(collection2.size() + "");
        //        for (Object object : collection2){
        //            User user = (User) object;
        //            LOGGER.info(user.getName());
        //        }
    }
}
