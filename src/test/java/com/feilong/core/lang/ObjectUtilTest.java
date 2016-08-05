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

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.lang.ObjectUtil.defaultIfNullOrEmpty;

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
        assertEquals(1, defaultIfNullOrEmpty(new ArrayList<>(), 1));

        assertEquals("feilong", defaultIfNullOrEmpty("  ", "feilong"));
        assertEquals("  ", defaultIfNull("  ", "feilong"));

        assertEquals("fl", defaultIfNullOrEmpty("fl", "feilong"));
    }

    @Test(expected = NullPointerException.class)
    public void testIsArray1(){
        ObjectUtil.isArray(null);
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