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
package com.feilong.core.bean;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.Person;
import com.feilong.test.User;
import com.feilong.test.UserInfo;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class PropertyUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.2
 */
public class PropertyUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtilTest.class);

    /**
     * Test bean util test.
     */
    @Test(expected = NullPointerException.class)
    public void testBeanUtilTest(){
        PropertyUtil.copyProperties(null, new Person());
    }

    /**
     * Test bean util test1.
     */
    @Test(expected = NullPointerException.class)
    public void testBeanUtilTest1(){
        PropertyUtil.copyProperties(new Person(), null);
    }

    /**
     * Test copy properties1.
     */
    @Test
    public void testCopyProperties1(){
        User oldUser = new User();
        oldUser.setId(5L);
        oldUser.setMoney(new BigDecimal(500000));
        oldUser.setDate(new Date());
        oldUser.setNickNames(toArray("feilong", "飞天奔月", "venusdrogon"));

        User newUser = new User();
        PropertyUtil.copyProperties(newUser, oldUser, "date", "money", "nickNames");

        assertThat(newUser, allOf(hasProperty("money", equalTo(new BigDecimal(500000)))));
        LOGGER.debug("newUser:{}", JsonUtil.format(newUser));
    }

    /**
     * Test set property.
     */
    @Test
    public void testSetProperty(){
        User user = new User();
        PropertyUtil.setProperty(user, "name", "feilong");
        assertThat(user, hasProperty("name", equalTo("feilong")));
    }

    /**
     * Test set property1.
     */
    @Test(expected = BeanUtilException.class)
    public void testSetProperty1(){
        User user = new User();
        PropertyUtil.setProperty(user, "name1", "feilong");
    }

    /**
     * Describe.
     */
    @Test
    public void testDescribe(){
        Date now = new Date();

        User user = new User();
        user.setId(5L);
        user.setDate(now);

        assertThat(PropertyUtil.describe(user), allOf(hasEntry("id", (Object) 5L), hasEntry("date", (Object) now)));
        assertThat(PropertyUtil.describe(user, "date", "id"), allOf(hasEntry("date", (Object) now), hasEntry("id", (Object) 5L)));
        assertThat(PropertyUtil.describe(user, "date"), hasEntry("date", (Object) now));
    }

    /**
     * Test describe 3.
     */
    @Test(expected = NullPointerException.class)
    public void testDescribe3(){
        PropertyUtil.describe(null);
    }

    /**
     * TestPropertyUtilTest.
     */
    @Test
    public void testPropertyUtilTest(){
        Date now = new Date();
        User user = new User();
        user.setId(5L);
        user.setDate(now);
        List<User> list = toList(user);
        LOGGER.debug(" {}", JsonUtil.format(PropertyUtil.describe(new BigDecimal(5L))));
        LOGGER.debug(" {}", JsonUtil.format(PropertyUtil.describe("123456")));
        LOGGER.debug(" {}", JsonUtil.format(PropertyUtil.describe(list)));
        LOGGER.debug(" {}", JsonUtil.format(PropertyUtil.describe(new HashMap())));
    }

    /**
     * Test describe 1.
     */
    @Test
    @Ignore
    public void testDescribe1(){
        LOGGER.debug("map:{}", JsonUtil.format(PropertyUtil.describe(User.class)));
    }

    /**
     * Test get property.
     */
    @Test
    public void testGetProperty(){
        User user = new User();
        user.setId(5L);
        user.setDate(new Date());

        List<User> list = toList(user, user, user);

        Long id = PropertyUtil.getProperty(list, "[0].id");
        assertThat(id, is(equalTo(5L)));
    }

    /**
     * Test find value of type.
     */
    @Test
    public void testFindValueOfType(){
        User user = new User();
        user.setId(5L);
        user.setDate(new Date());

        user.getUserInfo().setAge(28);

        UserInfo userInfo = PropertyUtil.findValueOfType(user, UserInfo.class);
        assertThat(userInfo, hasProperty("age", is(28)));

        //maven 运行的时候, 会是 序列化的值
        //assertThat(PropertyUtil.findValueOfType(user, Long.class), is(5L));
    }
}
