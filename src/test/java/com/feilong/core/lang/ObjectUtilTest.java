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
package com.feilong.core.lang;

import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.User;

/**
 * The Class ObjectUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ObjectUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtilTest.class);

    /**
     * Test default if null or empty.
     */
    @Test
    public void testDefaultIfNullOrEmpty(){
        assertEquals(1, ObjectUtil.defaultIfNullOrEmpty(new ArrayList<>(), 1));

        assertEquals("feilong", ObjectUtil.defaultIfNullOrEmpty("  ", "feilong"));
        assertEquals("  ", ObjectUtils.defaultIfNull("  ", "feilong"));

        assertEquals("fl", ObjectUtil.defaultIfNullOrEmpty("fl", "feilong"));
    }

    /**
     * Checks if is array.
     */
    @Test
    public void testIsArray(){
        int[] i = {};
        assertEquals(true, ObjectUtil.isArray(i));
        assertEquals(true, ObjectUtil.isArray(new Integer[0]));
        assertEquals(true, ObjectUtil.isArray(new String[0]));
        assertEquals(false, ObjectUtil.isArray(1));
    }

    /**
     * Assert equals.
     */
    @Test
    public void assertEquals2(){
        Long a = new Long(1L);
        Long b = new Long(1L);
        assertEquals(false, a == b);
        assertEquals(true, a.equals(b));

        User user = new User(1L);

        List<User> list = toList(//
                        user,
                        new User(1L),
                        new User(new Long(1L)));

        for (User user2 : list){
            LOGGER.debug((user2.getId() == user.getId()) + "");
        }
    }

}