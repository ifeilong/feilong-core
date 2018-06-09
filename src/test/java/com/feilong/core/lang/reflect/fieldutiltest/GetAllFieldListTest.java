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

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.store.member.UserInfo;

/**
 * The Class FieldUtilGetAllFieldListTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetAllFieldListTest{

    /**
     * Test get all field list exclude field names all.
     */
    @Test
    public void testGetAllFieldListExcludeFieldNamesAll(){
        assertEquals(emptyList(), FieldUtil.getAllFieldList(UserInfo.class, "age"));
    }

    /**
     * Test get all field list null exclude field names.
     */
    @Test
    public void testGetAllFieldListNullExcludeFieldNames(){
        assertNotEquals(emptyList(), FieldUtil.getAllFieldList(UserInfo.class, null));
    }

    /**
     * Test get all field list empty exclude field names.
     */
    @Test
    public void testGetAllFieldListEmptyExcludeFieldNames(){
        assertNotEquals(emptyList(), FieldUtil.getAllFieldList(UserInfo.class));
    }

    /**
     * Test get all field list empty exclude field names 1.
     */
    @Test
    public void testGetAllFieldListEmptyExcludeFieldNames1(){
        assertNotEquals(emptyList(), FieldUtil.getAllFieldList(UserInfo.class, new String[0]));
    }

    //************************
    /**
     * Test get all field list no filed.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testGetAllFieldListNoFiled(){
        assertEquals(emptyList(), FieldUtil.getAllFieldList(GetAllFieldListTest.class));
    }

    /**
     * Test get all field list null class.
     */
    @Test(expected = NullPointerException.class)
    public void testGetAllFieldListNullClass(){
        FieldUtil.getAllFieldList(null);
    }
}
