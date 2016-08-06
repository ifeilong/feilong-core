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

import org.junit.Test;

import static com.feilong.core.lang.ObjectUtil.defaultIfNullOrEmpty;

/**
 * The Class ObjectUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ObjectUtilTest{

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

    /**
     * Test is array 1.
     */
    @Test(expected = NullPointerException.class)
    public void testIsArray1(){
        ObjectUtil.isArray(null);
    }

    //***************************************************************************

    /**
     * Test is primitive array.
     */
    @Test(expected = NullPointerException.class)
    public void testIsPrimitiveArray(){
        ObjectUtil.isPrimitiveArray(null);
    }

    /**
     * Test is primitive array 1.
     */
    @Test
    public void testIsPrimitiveArray1(){
        assertEquals(false, ObjectUtil.isPrimitiveArray(1));
        assertEquals(false, ObjectUtil.isPrimitiveArray(1L));
        assertEquals(false, ObjectUtil.isPrimitiveArray("1"));

        assertEquals(true, ObjectUtil.isPrimitiveArray(new int[] {}));
        assertEquals(true, ObjectUtil.isPrimitiveArray(new int[] { 1, 2 }));
        assertEquals(true, ObjectUtil.isPrimitiveArray(new byte[] { 1, 2 }));

        assertEquals(false, ObjectUtil.isPrimitiveArray(new String[] { "1", "2" }));
    }
}