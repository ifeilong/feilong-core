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
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.store.member.User;

/**
 * The Class PropertyUtilDescribeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DescribeTest{

    /**
     * Test describe.
     */
    @Test
    public void testDescribe(){
        Date now = now();

        User user = new User();
        user.setId(5L);
        user.setDate(now);

        Map<String, Object> map = PropertyUtil.describe(user);
        assertThat(map, allOf(hasEntry("id", (Object) 5L), hasEntry("date", (Object) now)));
    }

    /**
     * Test describe null property names.
     */
    @Test
    public void testDescribeNullPropertyNames(){
        Date now = now();

        User user = new User();
        user.setId(5L);
        user.setDate(now);

        Map<String, Object> map = PropertyUtil.describe(user, null);

        assertThat(map.keySet().size(), greaterThan(4));
        assertThat(map, allOf(hasEntry("date", (Object) now), hasEntry("id", (Object) 5L)));
    }

    /**
     * Test describe null property names 1.
     */
    @Test
    public void testDescribeNullPropertyNames1(){
        Date now = now();

        User user = new User();
        user.setId(5L);
        user.setDate(now);

        Map<String, Object> map = PropertyUtil.describe(user, ConvertUtil.<String> toArray(null));
        assertThat(map.keySet().size(), greaterThan(4));
        assertThat(map, allOf(hasEntry("date", (Object) now), hasEntry("id", (Object) 5L)));
    }

    /**
     * Test describe null element property names.
     */
    @Test(expected = NullPointerException.class)
    public void testDescribeNullElementPropertyNames(){
        User user = new User();
        user.setId(5L);
        PropertyUtil.describe(user, ConvertUtil.<String> toArray((String) null));
    }

    /**
     * Test describe empty element property names.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescribeEmptyElementPropertyNames(){
        User user = new User();
        user.setId(5L);
        PropertyUtil.describe(user, ConvertUtil.<String> toArray(""));
    }

    /**
     * Test describe blank element property names.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescribeBlankElementPropertyNames(){
        User user = new User();
        user.setId(5L);
        PropertyUtil.describe(user, ConvertUtil.<String> toArray(" "));
    }

    /**
     * Test describe empty property names.
     */
    @Test
    public void testDescribeEmptyPropertyNames(){
        Date now = now();

        User user = new User();
        user.setId(5L);
        user.setDate(now);

        Map<String, Object> map = PropertyUtil.describe(user, ConvertUtil.<String> toArray());

        assertThat(map.keySet().size(), greaterThan(4));
        assertThat(map, allOf(hasEntry("date", (Object) now), hasEntry("id", (Object) 5L)));
    }

    /**
     * Test describe with property names.
     */
    @Test
    public void testDescribeWithPropertyNames(){
        Date now = now();

        User user = new User();
        user.setId(5L);
        user.setDate(now);

        Map<String, Object> map = PropertyUtil.describe(user, "date", "id");

        assertThat(map.keySet(), contains("date", "id"));
        assertThat(map, allOf(hasEntry("date", (Object) now), hasEntry("id", (Object) 5L)));
    }

    /**
     * Test describe with property names 1.
     */
    @Test
    public void testDescribeWithPropertyNames1(){
        Date now = now();

        User user = new User();
        user.setId(5L);
        user.setDate(now);

        Map<String, Object> map = PropertyUtil.describe(user, "date");
        assertThat(map.keySet(), contains("date"));
        assertThat(map, allOf(hasEntry("date", (Object) now)));
    }

    //---------------------------------------------------------------

    /**
     * Test describe null bean.
     */
    @Test(expected = NullPointerException.class)
    public void testDescribeNullBean(){
        PropertyUtil.describe(null);
    }
}
