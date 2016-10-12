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
package com.feilong.tools.jsonlib;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.Person;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.tools.AbstractJsonTest;

/**
 * The Class JsonUtilToBeanWithClassMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToBeanWithClassMapTest extends AbstractJsonTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilToBeanWithClassMapTest.class);

    /**
     * Test to bean.
     */
    @Test
    public void testToBean(){
        String json = "{'data':[{'name':'get'},{'name':'set'}],'id':5}";
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("data", Person.class);

        MyBean myBean = JsonUtil.toBean(json, MyBean.class, classMap);
        LOGGER.debug(JsonUtil.format(myBean));
    }

    /**
     * Test to bean 3.
     */
    @Test
    public void testToBean3(){
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("userAddresseList", UserAddress.class);

        User user = JsonUtil.toBean(USER_JSON_STRING, User.class, classMap);
        LOGGER.debug(JsonUtil.format(user));
    }

    /**
     * Test to bean null json.
     */
    @Test
    public void testToBeanNullJson(){
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("userAddresseList", UserAddress.class);

        User user = JsonUtil.toBean(null, User.class, classMap);
        assertEquals(null, user);
    }
}
