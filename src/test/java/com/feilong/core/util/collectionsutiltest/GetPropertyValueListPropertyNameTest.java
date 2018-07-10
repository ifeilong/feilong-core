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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.MapUtil.newHashMap;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;
import com.feilong.store.member.UserAddress;
import com.feilong.store.member.UserInfo;

/**
 * The Class GetPropertyValueListPropertyNameTest.
 */
public class GetPropertyValueListPropertyNameTest{

    /** The user address. */
    private static UserAddress         userAddress;

    /** The user addresse list. */
    private static List<UserAddress>   userAddresseList;

    /** The attr map. */
    private static Map<String, String> attrMap;

    //---------------------------------------------------------------

    /** The Constant userList. */
    private static List<User>          userList;

    static{
        userAddress = new UserAddress();
        userAddress.setAddress("中南海");

        //---------------------------------------------------------------

        userAddresseList = toList(userAddress);

        //---------------------------------------------------------------
        attrMap = newHashMap();
        attrMap.put("蜀国", "赵子龙");
        attrMap.put("魏国", "张文远");
        attrMap.put("吴国", "甘兴霸");

        //---------------------------------------------------------------

        userList = initUserList();
    }

    //---------------------------------------------------------------

    /**
     * Map
     */
    @Test
    public void testGetPropertyValueListMap(){
        List<String> list = CollectionsUtil.getPropertyValueList(userList, "attrMap(蜀国)");
        assertThat(list, contains("赵子龙", "赵子龙"));
    }

    /**
     * 集合.
     */
    @Test
    public void testGetPropertyValueListList(){
        List<UserAddress> list = CollectionsUtil.getPropertyValueList(userList, "userAddresseList[0]");
        assertThat(list, contains(userAddress, userAddress));
    }

    //---------------------------------------------------------------

    /**
     * 数组
     */
    @Test
    public void testGetPropertyValueListArray(){
        List<String> list = CollectionsUtil.getPropertyValueList(userList, "loves[1]");
        assertThat(list, contains("xiaoshuo1", "xiaoshuo2"));
    }

    /**
     * 级联对象.
     */
    @Test
    public void testGetPropertyValueListCascade(){
        List<Integer> list = CollectionsUtil.getPropertyValueList(userList, "userInfo.age");
        assertThat(list, contains(28, null));
    }

    /**
     * Inits the user list.
     *
     * @return the list
     */
    //---------------------------------------------------------------
    private static List<User> initUserList(){
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setAge(28);

        //---------------------------------------------------------------
        User user1 = new User(2L);

        user1.setLoves(toArray("sanguo1", "xiaoshuo1"));
        user1.setUserInfo(userInfo1);
        user1.setAttrMap(attrMap);
        user1.setUserAddresseList(userAddresseList);

        //---------------------------------------------------------------
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setAge(null);

        User user2 = new User(3L);
        user2.setLoves(toArray("sanguo2", "xiaoshuo2"));
        user2.setUserInfo(userInfo2);
        user2.setAttrMap(attrMap);
        user2.setUserAddresseList(userAddresseList);

        return toList(user1, user2);
    }

}
