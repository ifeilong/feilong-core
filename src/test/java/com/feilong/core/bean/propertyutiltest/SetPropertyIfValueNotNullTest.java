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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.store.member.User;

/**
 * The Class PropertyUtilSetPropertyIfValueNotNullTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SetPropertyIfValueNotNullTest{

    /**
     * Test set property if value not null.
     */
    @Test
    public void testSetPropertyIfValueNotNull(){
        User user = new User();
        PropertyUtil.setPropertyIfValueNotNull(user, "name", "feitianbenyue");
        assertThat(user, hasProperty("name", equalTo("feitianbenyue")));
    }

    /**
     * Test set property if value not null null value.
     */
    @Test
    public void testSetPropertyIfValueNotNullNullValue(){
        User user = new User();
        PropertyUtil.setPropertyIfValueNotNull(user, "name", null);
        assertThat(user, hasProperty("name", equalTo("feilong")));//默认值
    }

    //---------------------------------------------------------------

    /**
     * Test set property if value not null null bean.
     */
    @Test(expected = NullPointerException.class)
    public void testSetPropertyIfValueNotNullNullBean(){
        PropertyUtil.setPropertyIfValueNotNull(null, "name", "feilong");
    }

    /**
     * Test set property if value not null null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSetPropertyIfValueNotNullNullPropertyName(){
        PropertyUtil.setPropertyIfValueNotNull(new User(), null, "feilong");
    }

    /**
     * Test set property if value not null empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetPropertyIfValueNotNullEmptyPropertyName(){
        PropertyUtil.setPropertyIfValueNotNull(new User(), "", "feilong");
    }

    /**
     * Test set property if value not null blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetPropertyIfValueNotNullBlankPropertyName(){
        PropertyUtil.setPropertyIfValueNotNull(new User(), " ", "feilong");
    }

    /**
     * Test set property if value not null not exist property name.
     */
    @Test(expected = BeanOperationException.class)
    public void testSetPropertyIfValueNotNullNotExistPropertyName(){
        PropertyUtil.setPropertyIfValueNotNull(new User(), "hahaha", "feilong");
    }
}
