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
package com.feilong.core.text;

import java.math.RoundingMode;

import org.junit.Test;

/**
 * The Class NumberFormatUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class NumberFormatUtilTest{

    //*************NumberFormatUtil.format(Number, String, RoundingMode)***************************************

    /**
     * Test format null value 1.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullValue1(){
        NumberFormatUtil.format(null, "#####", RoundingMode.HALF_UP);
    }

    /**
     * Test format null number pattern 1.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullNumberPattern1(){
        NumberFormatUtil.format(25, null, RoundingMode.HALF_UP);
    }

    /**
     * Test format blank number pattern 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern2(){
        NumberFormatUtil.format(25, "", RoundingMode.HALF_UP);
    }

    /**
     * Test format blank number pattern 21.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern21(){
        NumberFormatUtil.format(25, " ", RoundingMode.HALF_UP);
    }
}
