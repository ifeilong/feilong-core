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
package com.feilong.core.lang.arrayutiltest;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.feilong.core.lang.ArrayUtil;

/**
 * The Class ArrayUtilNewArrayTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class NewArrayTest{
    //************com.feilong.core.lang.ArrayUtil.newArray(Class<Object>, int)*************************************

    /**
     * Test new array.
     */
    @Test(expected = NullPointerException.class)
    public void testNewArray(){
        ArrayUtil.newArray(null, 5);
    }

    /**
     * Test new array1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNewArray1(){
        ArrayUtil.newArray(Integer.class, -5);
    }

    /**
     * Test new array4.
     */
    @Test
    public void testNewArray4(){
        assertArrayEquals(new Integer[] {}, ArrayUtil.newArray(Integer.class, 0));
        assertArrayEquals(new Integer[] { null, null, null }, ArrayUtil.newArray(Integer.class, 3));
    }
}
