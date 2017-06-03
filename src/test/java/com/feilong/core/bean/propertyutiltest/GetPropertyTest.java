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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class PropertyUtilGetPropertyTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetPropertyTest{

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
     * Test get property null bean.
     */
    @Test(expected = NullPointerException.class)
    public void testGetPropertyNullBean(){
        PropertyUtil.getProperty(null, "name");
    }

    /**
     * Test get property null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetPropertyNullPropertyName(){
        PropertyUtil.getProperty(new User(), null);
    }

    /**
     * Test get property empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyEmptyPropertyName(){
        PropertyUtil.getProperty(new User(), "");
    }

    /**
     * Test get property blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPropertyBlankPropertyName(){
        PropertyUtil.getProperty(new User(), " ");
    }
}
