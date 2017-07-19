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
package com.feilong.core.bean.beanutiltest;

import static com.feilong.core.DatePattern.TO_STRING_STYLE;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.bean.BeanUtil;
import com.feilong.store.member.Person;
import com.feilong.store.member.User;

/**
 * The Class BeanUtilCopyPropertiesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CopyPropertiesTest{

    @Test
    public void testCopyPropertiesNullValue(){
        User oldUser = new User();
        oldUser.setId(null);

        User newUser = new User();
        BeanUtil.copyProperties(newUser, oldUser);

        assertEquals(null, newUser.getId());
    }

    /**
     * Test copy properties all.
     */
    @Test
    public void testCopyPropertiesAll(){
        BigDecimal money = new BigDecimal(500000);
        String[] nickNames = toArray("feilong", "飞天奔月", "venusdrogon");
        Date date = new Date();

        User user = new User();
        user.setId(5L);
        user.setMoney(money);
        user.setDate(date);
        user.setNickNames(nickNames);

        DateLocaleConverter dateLocaleConverter = new DateLocaleConverter(Locale.US, TO_STRING_STYLE);
        ConvertUtils.register(dateLocaleConverter, Date.class);

        User user2 = new User();
        BeanUtil.copyProperties(user2, user);

        //*************************************************************************
        assertThat(user2, allOf(//
                        hasProperty("money", is(money)),

                        hasProperty("nickNames", is(nickNames)),
                        //hasProperty("nickNames", is(toArray("feilong"))),

                        // not(hasProperty("date", equalTo(date)))//
                        hasProperty("date", equalTo(date))//
        ));
    }

    /**
     * Test copy properties with array.
     */
    @Test
    public void testCopyPropertiesWithArray(){
        String[] nickNames = toArray("feilong", "飞天奔月", "venusdrogon");

        User user = new User();
        user.setNickNames(nickNames);

        User user2 = new User();
        BeanUtil.copyProperties(user2, user, "nickNames");

        assertThat(user2, allOf(//
                        not(hasProperty("nickNames", is(nickNames))),
                        hasProperty("nickNames", is(toArray("feilong")))));
    }

    /**
     * Test copy properties no date locale converter.
     */
    //*******这个要放最上面, 否则maven来执行的时候  不会出现 异常****************************************************
    @Test(expected = BeanOperationException.class)
    public void testCopyPropertiesNoDateLocaleConverter(){
        User user = new User();
        user.setDate(new Date());

        User user2 = new User();
        BeanUtil.copyProperties(user2, user, "date");
    }

    /**
     * Test copy property from bean not exist properties.
     */
    @Test(expected = BeanOperationException.class)
    public void testCopyPropertyFromBeanNotExistProperties(){
        User user = new User();
        user.setId(5L);

        Person person = new Person();
        BeanUtil.copyProperties(person, user, "name", "dateAttr");
    }

    /**
     * Test copy property to bean not exist properties.
     */
    @Test
    public void testCopyPropertyToBeanNotExistProperties(){
        User user = new User();
        user.setId(5L);

        Person person = new Person();
        BeanUtil.copyProperties(person, user, "age", "name");

        assertThat(person, allOf(hasProperty("name", is("feilong")), hasProperty("dateAttr", is((Object) null))));
    }

    /**
     * Test bean util null to bean.
     */
    @Test(expected = NullPointerException.class)
    public void testBeanUtilNullToBean(){
        BeanUtil.copyProperties(null, new Person());
    }

    /**
     * Test bean util null from bean.
     */
    @Test(expected = NullPointerException.class)
    public void testBeanUtilNullFromBean(){
        BeanUtil.copyProperties(new Person(), null);
    }
}
