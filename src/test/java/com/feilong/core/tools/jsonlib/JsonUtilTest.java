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
package com.feilong.core.tools.jsonlib;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.tools.BaseJsonTest;
import com.feilong.test.User;
import com.feilong.test.UserAddress;

/**
 * The Class JsonlibTest.
 * 
 * @author feilong
 * @version 1.0.7 2014-6-25 15:31:11
 * @deprecated
 */
@Deprecated
public class JsonUtilTest extends BaseJsonTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilTest.class);

    /**
     * Test json string.
     */
    @Test
    public void testJsonString(){
        String json = JsonUtil.format(getUserForJsonTest());
        LOGGER.info(json);
    }

    @Test
    public void testJsonString1(){
        BigDecimal[] aBigDecimals = { new BigDecimal("9999.00") };
        String json = JsonUtil.format(aBigDecimals);
        LOGGER.info(json);
    }

    /**
     * Name.
     */
    @Test
    public void name(){
        JSONObject jsonObject = JSONObject.fromObject(json);
        Object bean = JSONObject.toBean(jsonObject);

        Assert.assertEquals(jsonObject.get("name"), PropertyUtil.getProperty(bean, "name"));
        Assert.assertEquals(jsonObject.get("bool"), PropertyUtil.getProperty(bean, "bool"));
        Assert.assertEquals(jsonObject.get("int"), PropertyUtil.getProperty(bean, "int"));
        Assert.assertEquals(jsonObject.get("double"), PropertyUtil.getProperty(bean, "double"));
        Assert.assertEquals(jsonObject.get("func"), PropertyUtil.getProperty(bean, "func"));
        List<?> expected = JSONArray.toList(jsonObject.getJSONArray("array"));
        Assert.assertEquals(expected, PropertyUtil.getProperty(bean, "array"));
    }

    /**
     * Name1.
     */
    @Test
    public void format(){
        String json = JsonUtil.format(getUserForJsonTest());
        LOGGER.info(json);

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
        String json = JsonUtil.format(getUserForJsonTest());
        LOGGER.info(json);

        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        classMap.put("userAddresseList", UserAddress.class);

        User user = JsonUtil.toBean(json, User.class, classMap);
        LOGGER.info(JsonUtil.format(user));
    }

    /**
     * TestJsonUtilTest.
     */
    @Test
    public void testFormatWithIncludes1(){

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
    public void testFormatWithIncludes(){
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
