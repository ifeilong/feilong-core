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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.feilong.core.bean.ConvertUtil.toArray;

/**
 * The Class ArrayUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0
 */
public class ArrayUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayUtilTest.class);

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

    //***************com.feilong.core.lang.ArrayUtil.getElement(Object, int)**************************************

    /**
     * Test get by array.
     */
    @Test
    public void testGetElement(){
        assertEquals("1", ArrayUtil.getElement(toArray("jinxin", "feilong", "1"), 2));
    }

    /**
     * Test get element primitive type.
     */
    @Test
    public void testGetElementPrimitiveType(){
        assertEquals(2, ArrayUtil.getElement(new int[] { 5, 8, 2, 0 }, 2));
    }

    /**
     * Test get element null array.
     */
    @Test(expected = NullPointerException.class)
    public void testGetElementNullArray(){
        ArrayUtil.getElement(null, 2);
    }

    /**
     * Test get element error index.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetElementErrorIndex(){
        ArrayUtil.getElement(toArray("jinxin", "feilong", "1"), -2);
    }

    /**
     * Test get element error index 1.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetElementErrorIndex1(){
        ArrayUtil.getElement(toArray("jinxin", "feilong", "1"), 5);
    }

    /**
     * Test get element not array.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetElementNotArray(){
        ArrayUtil.getElement("jinxin", 5);
    }

    //**************************************************************************************************
    /**
     * Convert list to string replace brackets.
     */
    @Test
    public void convertListToStringReplaceBrackets(){
        String[] array = toArray("1", "223");
        //Use "Arrays.toString(array)" instead.
        LOGGER.debug(array.toString());
        LOGGER.debug(Arrays.toString(array));
        LOGGER.debug(StringUtils.join(array, ","));
    }

    /**
     * Test contains.
     */
    @Test
    public void testContains(){
        assertEquals(true, ArrayUtils.contains(new Integer[] { 1, 223 }, 1));
        assertEquals(true, ArrayUtils.contains(new Long[] { 1L, 223L }, 1L));

        assertEquals(false, ArrayUtils.contains(toArray("1", "223"), "2"));

        int[] intarray = { 1, 223 };
        assertEquals(true, ArrayUtils.contains(intarray, 1));
    }

    /**
     * TestArrayUtilTest.
     */
    @Test
    public void testArrayUtilTest(){
        String[] strs = new String[10];
        assertSame(10, strs.length);
    }
}
