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

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.BeanUtil;
import com.feilong.core.lang.NumberUtil;
import com.feilong.store.member.User;

/**
 * The Class PopulateBigDecimalTest.
 */
public class PopulateBigDecimalTest{

    /**
     * Test populate null.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateNull(){
        User user = new User();

        Map<String, String> properties = toMap("money", null);
        User user1 = BeanUtil.populate(user, properties);

        assertNull(user1.getMoney());
    }

    //---------------------------------------------------------------

    /**
     * Test populate empty.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateEmpty(){
        User user = new User();

        Map<String, String> properties = toMap("money", "");
        User user1 = BeanUtil.populate(user, properties);

        assertNull(user1.getMoney());
    }

    //---------------------------------------------------------------

    /**
     * Test populate.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulate(){
        User user = new User();

        Map<String, String> properties = toMap("money", "8");
        User user1 = BeanUtil.populate(user, properties);

        BigDecimal money = user1.getMoney();

        assertTrue(NumberUtil.isEquals(money, toBigDecimal(8)));
    }
}
