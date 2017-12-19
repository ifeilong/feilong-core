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
package com.feilong.core.lang.reflect.fieldutiltest;

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.store.member.User;
import com.feilong.store.member.UserInfo;

/**
 * The Class FieldUtilGetAllFieldNameAndValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetAllFieldNameAndValueMapTest{

    /**
     * Test get all field name and value map.
     */
    @Test
    public void testGetAllFieldNameAndValueMap(){
        Map<String, Object> map = FieldUtil.getAllFieldNameAndValueMap(new User(12L), "date");
        assertThat(map, allOf(hasEntry("id", (Object) 12L), hasKey("nickNames"), not(hasKey("date"))));
    }

    /**
     * Test get all field name and value map exclude field names all.
     */
    @Test
    public void testGetAllFieldNameAndValueMapExcludeFieldNamesAll(){
        UserInfo user = new UserInfo(18);
        Map<String, Object> map = FieldUtil.getAllFieldNameAndValueMap(user, "age");
        assertEquals(emptyMap(), map);
    }

    /**
     * Test get all field name and value map null exclude field names.
     */
    @Test
    public void testGetAllFieldNameAndValueMapNullExcludeFieldNames(){
        Map<String, Object> map = FieldUtil.getAllFieldNameAndValueMap(new UserInfo(18), null);
        assertThat(map, allOf(hasEntry("age", (Object) 18)));
    }

    /**
     * Test get all field name and value map empty exclude field names.
     */
    @Test
    public void testGetAllFieldNameAndValueMapEmptyExcludeFieldNames(){
        Map<String, Object> map = FieldUtil.getAllFieldNameAndValueMap(new UserInfo(18));
        assertThat(map, allOf(hasEntry("age", (Object) 18)));
    }

    /**
     * Test get all field name and value map empty exclude field names 1.
     */
    @Test
    public void testGetAllFieldNameAndValueMapEmptyExcludeFieldNames1(){
        Map<String, Object> map = FieldUtil.getAllFieldNameAndValueMap(new UserInfo(18), new String[0]);
        assertThat(map, allOf(hasEntry("age", (Object) 18)));
    }

    /**
     * Test get all field name and value map no filed.
     */
    @Test
    public void testGetAllFieldNameAndValueMapNoFiled(){
        assertEquals(emptyMap(), FieldUtil.getAllFieldNameAndValueMap(this));
    }

    /**
     * Test get all field name and value map null obj.
     */
    @Test(expected = NullPointerException.class)
    public void testGetAllFieldNameAndValueMapNullObj(){
        FieldUtil.getAllFieldNameAndValueMap(null, "date");
    }
}
