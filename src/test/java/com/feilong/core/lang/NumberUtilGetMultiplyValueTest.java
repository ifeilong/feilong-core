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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;

public class NumberUtilGetMultiplyValueTest{

    /**
     * Test get multiply value.
     */
    @Test
    public void testGetMultiplyValue(){
        assertEquals(toBigDecimal("7.31250"), NumberUtil.getMultiplyValue(new BigDecimal(6.25), 1.17, 5));
        assertEquals(toBigDecimal("10.00000"), NumberUtil.getMultiplyValue(5, 2, 5));
        assertEquals(toBigDecimal("10"), NumberUtil.getMultiplyValue(5, 2, 0));
    }

    /**
     * Gets the progress5.
     * 
     */
    @Test(expected = NullPointerException.class)
    public void testGetMultiplyValue1(){
        NumberUtil.getMultiplyValue(null, 1, 2);
    }

    /**
     * Test get multiply value2.
     */
    @Test(expected = NullPointerException.class)
    public void testGetMultiplyValue2(){
        NumberUtil.getMultiplyValue(1, null, 2);
    }

    /**
     * Test get multiply value3.
     */
    @Test(expected = NullPointerException.class)
    public void testGetMultiplyValue3(){
        NumberUtil.getMultiplyValue(null, null, 2);
    }
}
