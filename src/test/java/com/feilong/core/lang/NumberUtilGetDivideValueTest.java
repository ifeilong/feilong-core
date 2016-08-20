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

public class NumberUtilGetDivideValueTest{

    @Test
    public void getDivideNoScaleValue(){
        assertEquals(0, NumberUtil.getDivideValue(0, 2, 0).intValue());
        assertEquals(2, NumberUtil.getDivideValue(6, 4, 0).intValue());

        assertEquals("3.33", NumberUtil.getDivideValue(10, 3, 2).toString());
        assertEquals("1.67", NumberUtil.getDivideValue(5, 3, 2).toString());
    }

    @Test(expected = NullPointerException.class)
    public void getDivideValue1(){
        NumberUtil.getDivideValue(null, 0, 0);
    }

    @Test(expected = NullPointerException.class)
    public void getDivideValue2(){
        NumberUtil.getDivideValue(0, null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDivideValue(){
        NumberUtil.getDivideValue(new BigDecimal(6), 0, 0);
    }
}
