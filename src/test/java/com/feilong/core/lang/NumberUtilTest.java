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

import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;

/**
 * The Class NumberUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class NumberUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtilTest.class);

    /**
     * TestMathTest.
     */
    @Test
    public void testMathTest(){
        LOGGER.debug("" + (0.4 + 0.8)); // = 1.2 ?
        LOGGER.debug("" + (2 - 1.1)); // = 0.9 ?
        LOGGER.debug("" + (0.2 * 3)); // = 0.6 ?
        LOGGER.debug("" + (1.2 / 3)); // = 0.4 ?

        //1.2000000000000002
        //0.8999999999999999
        //0.6000000000000001
        //0.39999999999999997

        //new BigDecimal(0.1) ====>   0.1000000000000000055511151231257827021181583404541015625
        //BigDecimal.valueOf(0.1) ====>  0.1
        LOGGER.debug("" + (new BigDecimal(0.1)));
        LOGGER.debug("" + (BigDecimal.valueOf(0.1)));

        LOGGER.debug("" + (new BigDecimal(0.4).add(new BigDecimal(0.8)))); // = 1.2 ?
        LOGGER.debug("" + (new BigDecimal(2).subtract(new BigDecimal(1.1)))); // = 0.9 ?
        LOGGER.debug("" + (new BigDecimal(0.2).multiply(new BigDecimal(3)))); // = 0.6 ?

    }

    //**************************************************************************

    @Test
    public void compareTo(){
        BigDecimal totalFee = BigDecimal.valueOf(-0.01);
        boolean isLEZero = (totalFee.compareTo(BigDecimal.ZERO) < 0) || (totalFee.compareTo(BigDecimal.ZERO) == 0);
        assertEquals(true, isLEZero);
    }

    /**
     * Testadd.
     */
    @Test
    public void testAdd(){
        BigDecimal a = new BigDecimal(19);
        BigDecimal b = new BigDecimal(20);

        BigDecimal temp = b;

        int j = 5;
        int scale = 8;
        for (int i = 0; i < j; ++i){
            temp = NumberUtil.getDivideValue(a.add(temp), 2, scale);
            LOGGER.debug("{}次:{}", i, temp);
        }

    }

    /**
     * To no scale.
     */
    @Test
    public void toNoScale(){
        assertEquals(new BigDecimal(123), NumberUtil.toNoScale(new BigDecimal(123.02)));
        assertEquals(new BigDecimal(123), NumberUtil.toNoScale(new BigDecimal(123.49)));
        assertEquals(new BigDecimal(124), NumberUtil.toNoScale(new BigDecimal(123.51)));
        assertEquals(new BigDecimal(-124), NumberUtil.toNoScale(new BigDecimal(-123.51)));
    }

    /**
     * To no scale2.
     */
    @Test
    public void toNoScale2(){
        assertEquals(new BigDecimal(88), NumberUtil.toNoScale(88.02));
        assertEquals(new BigDecimal(89), NumberUtil.toNoScale(88.520));
        assertEquals(new BigDecimal(89), NumberUtil.toNoScale(88.820f));
        assertEquals(new BigDecimal(88), NumberUtil.toNoScale(88.4999f));
        assertEquals(new BigDecimal(88), NumberUtil.toNoScale(88.4999d));
        assertEquals(new BigDecimal(-89), NumberUtil.toNoScale(-88.5999d));
        // ***********************************************************************
        assertEquals(ZERO, NumberUtil.toNoScale(0.1));
        assertEquals(BigDecimal.ONE, NumberUtil.toNoScale(0.5));
        //
        assertEquals(new BigDecimal(-1), NumberUtil.toNoScale(-0.5));
        assertEquals(ZERO, NumberUtil.toNoScale(-0.11111111));

    }

    /**
     * To no scale3.
     */
    @Test(expected = NullPointerException.class)
    public void toNoScale3(){
        NumberUtil.toNoScale(null);
    }

    @Test
    public void getDivideNoScaleValue(){
        assertEquals(0, NumberUtil.getDivideValue(0, 2, 0).intValue());
        assertEquals(2, NumberUtil.getDivideValue(6, 4, 0).intValue());

        assertEquals("3.33", NumberUtil.getDivideValue(10, 3, 2).toString());
        assertEquals("1.67", NumberUtil.getDivideValue(5, 3, 2).toString());
    }

    /**
     * 获得 divide value1.
     *
     * @return the divide value 1
     */
    @Test(expected = NullPointerException.class)
    public void getDivideValue1(){
        NumberUtil.getDivideValue(null, 0, 0);
    }

    /**
     * 获得 divide value2.
     *
     * @return the divide value 2
     */
    @Test(expected = NullPointerException.class)
    public void getDivideValue2(){
        NumberUtil.getDivideValue(0, null, 0);
    }

    /**
     * Gets the divide value.
     *
     * @return the divide value
     */
    @Test(expected = IllegalArgumentException.class)
    public void getDivideValue(){
        NumberUtil.getDivideValue(new BigDecimal(6), 0, 0);
    }

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
