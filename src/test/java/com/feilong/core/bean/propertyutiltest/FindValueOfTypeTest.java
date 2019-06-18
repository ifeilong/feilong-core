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

import static com.feilong.core.date.DateUtil.now;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.store.member.User;
import com.feilong.store.member.UserInfo;

/**
 * The Class PropertyUtilDescribeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class FindValueOfTypeTest{

    /**
     * Test find value of type.
     */
    @Test
    public void testFindValueOfType(){
        User user = new User();
        user.setId(5L);
        user.setDate(now());

        user.getUserInfo().setAge(28);

        UserInfo userInfo = PropertyUtil.findValueOfType(user, UserInfo.class);
        assertThat(userInfo, hasProperty("age", is(28)));

        //maven 运行的时候, 会是 序列化的值
        //assertThat(PropertyUtil.findValueOfType(user, Long.class), is(5L));
    }

    /**
     * Test find value of type null obj.
     */
    @Test
    public void testFindValueOfTypeNullObj(){
        assertEquals(null, PropertyUtil.findValueOfType(null, UserInfo.class));
    }

    /**
     * Test find value of type null to be finded class type.
     */
    @Test(expected = NullPointerException.class)
    public void testFindValueOfTypeNullToBeFindedClassType(){
        PropertyUtil.findValueOfType(new User(), null);
    }

    /**
     * Test find value of type null is instance type.
     */
    @Test
    public void testFindValueOfTypeNullIsInstanceType(){
        User user = new User();
        assertEquals(user, PropertyUtil.findValueOfType(user, User.class));
    }
}
