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

import static org.junit.Assert.assertEquals;

import java.math.RoundingMode;

import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;

/**
 * The Class NumberFormatUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class NumberFormatUtilTest{

    /**
     * Test format.
     */
    //***************NumberFormatUtil.format(Number, String)************************************
    @Test
    public void testFormat(){
        assertEquals("26", NumberFormatUtil.format(25.5, "#####"));
        assertEquals("RP 26", NumberFormatUtil.format(25.5, "RP #####"));
    }

    /**
     * Test format null value.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullValue(){
        NumberFormatUtil.format(null, "#####");
    }

    /**
     * Test format null number pattern.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullNumberPattern(){
        NumberFormatUtil.format(25, null);
    }

    /**
     * Test format blank number pattern.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern(){
        NumberFormatUtil.format(25, "");
    }

    /**
     * Test format blank number pattern 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern1(){
        NumberFormatUtil.format(25, " ");
    }

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

    /**
     * Test format 2.
     */
    @Test
    public void testFormat2(){
        assertEquals("26", NumberFormatUtil.format(25.5, "#####", RoundingMode.HALF_UP));
        assertEquals("RP 26", NumberFormatUtil.format(25.5, "RP #####", RoundingMode.HALF_UP));
    }

    /**
     * Test format 32.
     */
    @Test
    public void testFormat32(){
        assertEquals("1.2", NumberFormatUtil.format(toBigDecimal(1.15), "#####.#", RoundingMode.HALF_EVEN));
        assertEquals("1.2", NumberFormatUtil.format(toBigDecimal(1.25), "#####.#", RoundingMode.HALF_EVEN));
        assertEquals("1.3", NumberFormatUtil.format(toBigDecimal(1.251), "#####.#", RoundingMode.HALF_EVEN));

        assertEquals("-1.2", NumberFormatUtil.format(toBigDecimal(-1.15), "#####.#", RoundingMode.HALF_EVEN));
        assertEquals("-1.2", NumberFormatUtil.format(toBigDecimal(-1.25), "#####.#", RoundingMode.HALF_EVEN));
        assertEquals("-1.3", NumberFormatUtil.format(toBigDecimal(-1.251), "#####.#", RoundingMode.HALF_EVEN));
    }

    /**
     * Test format 321.
     */
    @Test
    public void testFormat321(){
        assertEquals("1.2", NumberFormatUtil.format(toBigDecimal(1.15), "#####.#", null));
        assertEquals("1.3", NumberFormatUtil.format(toBigDecimal(1.25), "#####.#", null));
        assertEquals("1.3", NumberFormatUtil.format(toBigDecimal(1.251), "#####.#", null));

        assertEquals("-1.2", NumberFormatUtil.format(toBigDecimal(-1.15), "#####.#", null));
        assertEquals("-1.3", NumberFormatUtil.format(toBigDecimal(-1.25), "#####.#", null));
        assertEquals("-1.3", NumberFormatUtil.format(toBigDecimal(-1.251), "#####.#", null));
    }

    /**
     * Test format 111.
     */
    @Test
    public void testFormat111(){
        assertEquals("1.2", NumberFormatUtil.format(toBigDecimal(1.15), "#####.#"));
        assertEquals("1.3", NumberFormatUtil.format(toBigDecimal(1.25), "#####.#"));
        assertEquals("1.3", NumberFormatUtil.format(toBigDecimal(1.251), "#####.#"));

        assertEquals("-1.2", NumberFormatUtil.format(toBigDecimal(-1.15), "#####.#"));
        assertEquals("-1.3", NumberFormatUtil.format(toBigDecimal(-1.25), "#####.#"));
        assertEquals("-1.3", NumberFormatUtil.format(toBigDecimal(-1.251), "#####.#"));
    }

}
