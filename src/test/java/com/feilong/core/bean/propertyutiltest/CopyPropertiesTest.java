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
package com.feilong.core.bean.propertyutiltest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.date.DateUtil.now;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.store.member.Person;
import com.feilong.store.member.User;

/**
 * The Class PropertyUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.2
 */
public class CopyPropertiesTest{

    /**
     * Test copy properties.
     */
    @Test
    public void testCopyProperties(){
        Date now = now();
        String[] array = toArray("feilong", "飞天奔月", "venusdrogon");

        User oldUser = new User();
        oldUser.setId(5L);
        oldUser.setMoney(new BigDecimal(500000));
        oldUser.setDate(now);
        oldUser.setNickNames(array);

        User newUser = new User();
        PropertyUtil.copyProperties(newUser, oldUser, "date", "money", "nickNames");

        assertThat(
                        newUser,
                        allOf(//
                                        hasProperty("money", equalTo(new BigDecimal(500000))),
                                        hasProperty("date", is(now)),
                                        hasProperty("nickNames", equalTo(array))
                        //
                        ));
    }

    //---------------------------------------------------------------

    @Test
    public void testCopyPropertiesNullValue(){
        User oldUser = new User();
        oldUser.setId(null);

        User newUser = new User();
        PropertyUtil.copyProperties(newUser, oldUser);

        assertEquals(null, newUser.getId());
    }

    //---------------------------------------------------------------

    /**
     * Test copy properties null to obj.
     */
    @Test(expected = NullPointerException.class)
    public void testCopyPropertiesNullToObj(){
        PropertyUtil.copyProperties(null, new Person());
    }

    /**
     * Test copy properties null from obj.
     */
    @Test(expected = NullPointerException.class)
    public void testCopyPropertiesNullFromObj(){
        PropertyUtil.copyProperties(new Person(), null);
    }

    /**
     * Test copy properties from obj no exist property name.
     */
    @Test(expected = BeanOperationException.class)
    public void testCopyPropertiesFromObjNoExistPropertyName(){
        User oldUser = new User();
        oldUser.setId(5L);

        User newUser = new User();
        PropertyUtil.copyProperties(newUser, oldUser, "date1");
    }

    /**
     * Test copy properties to obj no exist property name.
     */
    @Test(expected = BeanOperationException.class)
    public void testCopyPropertiesToObjNoExistPropertyName(){
        User oldUser = new User();
        oldUser.setId(5L);
        oldUser.setMoney(new BigDecimal(500000));

        PropertyUtil.copyProperties(new Person(), oldUser, "date");
    }
}
