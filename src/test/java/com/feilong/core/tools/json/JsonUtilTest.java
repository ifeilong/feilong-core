/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.tools.json;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.json.JsonUtil;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;

/**
 * The Class JsonlibTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014-6-25 15:31:11
 */
public class JsonUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilTest.class);

    /**
     * Name.
     * 
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NoSuchMethodException
     *             the no such method exception
     */
    @Test
    public void name() throws IllegalAccessException,InvocationTargetException,NoSuchMethodException{
        String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        Object bean = JSONObject.toBean(jsonObject);

        Assert.assertEquals(jsonObject.get("name"), PropertyUtils.getProperty(bean, "name"));
        Assert.assertEquals(jsonObject.get("bool"), PropertyUtils.getProperty(bean, "bool"));
        Assert.assertEquals(jsonObject.get("int"), PropertyUtils.getProperty(bean, "int"));
        Assert.assertEquals(jsonObject.get("double"), PropertyUtils.getProperty(bean, "double"));
        Assert.assertEquals(jsonObject.get("func"), PropertyUtils.getProperty(bean, "func"));
        List<?> expected = JSONArray.toList(jsonObject.getJSONArray("array"));
        Assert.assertEquals(expected, PropertyUtils.getProperty(bean, "array"));
    }

    /**
     * Name1.
     */
    @Test
    public void format(){
        String json = getJsonString();

        User user = JsonUtil.toBean(json, User.class);
        user.setId(10L);
        json = JsonUtil.format(user);
        LOGGER.info(json);
    }

    /**
     * To bean.
     */
    @Test
    public void toBean(){
        String json = getJsonString();

        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        classMap.put("userAddresseList", UserAddress.class);

        User user = JsonUtil.toBean(json, User.class, classMap);
        LOGGER.info(JsonUtil.format(user));
    }

    /**
     * Test json string.
     */
    @Test
    public void testJsonString(){
        getJsonString();
    }

    /**
     * Gets the json string.
     * 
     * @return the json string
     */
    private String getJsonString(){
        User user = new User();

        user.setId(8L);
        user.setName("feilong");

        String[] loves = { "桔子", "香蕉" };
        user.setLoves(loves);

        UserInfo userInfo = new UserInfo();

        userInfo.setAge(10);
        user.setUserInfo(userInfo);

        UserAddress userAddress1 = new UserAddress();
        userAddress1.setAddress("上海市闸北区万荣路1188号H座109-118室");

        UserAddress userAddress2 = new UserAddress();
        userAddress2.setAddress("上海市闸北区阳城路280弄25号802室(阳城贵都)");

        UserAddress[] userAddresses = { userAddress1, userAddress2 };
        user.setUserAddresses(userAddresses);

        List<UserAddress> userAddresseList = new ArrayList<UserAddress>();
        userAddresseList.add(userAddress1);
        userAddresseList.add(userAddress2);
        user.setUserAddresseList(userAddresseList);

        String json;
        // json= JsonUtil.format(user);

        json = JsonUtil.toJSON(user).toString(4, 4);
        LOGGER.info(json);
        return json;
    }

    /**
     * TestJsonUtilTest.
     */
    @Test
    public void testJsonUtilTest(){

        User user1 = new User("feilong1", 24);
        user1.setId(8L);

        User user2 = new User("feilong2", 240);

        List<User> list = new ArrayList<User>();

        list.add(user1);
        list.add(user2);

        String[] array = { "id", "name" };
        LOGGER.info(JsonUtil.formatWithIncludes(list, array));

        User[] users = { user1, user2 };
        LOGGER.info(JsonUtil.formatWithIncludes(users, array));

        List<String> list3 = new ArrayList<String>();

        list3.add("2,5,8");
        list3.add("2,5,9");

        LOGGER.info(JsonUtil.formatWithIncludes(list3));
        LOGGER.info(JsonUtil.formatWithIncludes(user1));

    }

    /**
     * TestJsonUtilTest.
     */
    @Test
    public void testJsonUtilTest1(){
        Object[][] objects = { { "nike shoe", "500", 1 }, { "nike shoe2", "5000", 1 } };
        LOGGER.info(JsonUtil.formatWithIncludes(objects));
    }

    /**
     * Test vector.
     */
    @Test
    public void testVector(){
        Vector<Integer> vector = new Vector<Integer>();
        vector.add(1);
        vector.add(2222);
        vector.add(3333);
        vector.add(55555);
        LOGGER.info("vector:{}", JsonUtil.format(vector));
        LOGGER.info("" + vector.get(0));
    }

    /**
     * Test hashtable.
     */
    @Test
    public void testHashtable(){
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        hashtable.put("a", "a");
        // hashtable.put("a", null);
        LOGGER.info("hashtable:{}", JsonUtil.format(hashtable));
    }

}
