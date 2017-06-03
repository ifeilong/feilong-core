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
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;
import com.feilong.store.member.UserAddress;
import com.feilong.store.member.UserInfo;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionsUtilGetPropertyValueListTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetPropertyValueListTest{

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
        Map<String, String> attrMap = new HashMap<>();
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
        //LOGGER.debug(JsonUtil.format(fieldValueList1));

        assertThat(fieldValueList1, contains("xiaoshuo1", "xiaoshuo2"));

        //级联对象
        List<Integer> fieldValueList2 = CollectionsUtil.getPropertyValueList(userList, "userInfo.age");
        // LOGGER.debug(JsonUtil.format(fieldValueList2));
        assertThat(fieldValueList2, contains(28, null));

        //Map
        List<String> attrList = CollectionsUtil.getPropertyValueList(userList, "attrMap(蜀国)");
        //LOGGER.debug(JsonUtil.format(attrList));
        assertThat(attrList, contains("赵子龙", "赵子龙"));

        //集合
        List<UserAddress> addressList = CollectionsUtil.getPropertyValueList(userList, "userAddresseList[0]");
        //LOGGER.debug(JsonUtil.format(addressList));
        assertThat(addressList, contains(userAddress, userAddress));
    }

    /**
     * Test get property value list null object collection.
     */
    //*******************************
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

    /**
     * Test get property value list null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetPropertyValueListNullPropertyName(){
        List<User> list = toList(new User(2L), new User(5L), new User(5L));
        CollectionsUtil.getPropertyValueList(list, null);
    }

    /**
     * Test get property value list empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueListEmptyPropertyName(){
        List<User> list = toList(new User(2L), new User(5L), new User(5L));
        CollectionsUtil.getPropertyValueList(list, "");
    }

    /**
     * Test get property value list blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyValueListBlankPropertyName(){
        List<User> list = toList(new User(2L), new User(5L), new User(5L));
        CollectionsUtil.getPropertyValueList(list, " ");
    }

}
