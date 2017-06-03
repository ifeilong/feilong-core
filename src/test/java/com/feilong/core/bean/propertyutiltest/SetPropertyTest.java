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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.store.member.User;

/**
 * The Class PropertyUtilDescribeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SetPropertyTest{

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
     * Test set property not exist property name.
     */
    @Test(expected = BeanOperationException.class)
    public void testSetPropertyNotExistPropertyName(){
        PropertyUtil.setProperty(new User(), "name1", "feilong");
    }

    /**
     * Test set property null bean.
     */
    @Test(expected = NullPointerException.class)
    public void testSetPropertyNullBean(){
        PropertyUtil.setProperty(null, "name1", "feilong");
    }

    /**
     * Test set property null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSetPropertyNullPropertyName(){
        PropertyUtil.setProperty(new User(), null, "feilong");
    }

    /**
     * Test set property empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetPropertyEmptyPropertyName(){
        PropertyUtil.setProperty(new User(), "", "feilong");
    }

    /**
     * Test set property blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetPropertyBlankPropertyName(){
        PropertyUtil.setProperty(new User(), " ", "feilong");
    }

}
