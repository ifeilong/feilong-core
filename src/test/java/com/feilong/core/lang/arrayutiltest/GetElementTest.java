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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.lang.ArrayUtil;

/**
 * The Class ArrayUtilGetElementTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetElementTest{

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
    @SuppressWarnings("static-method")
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
}
