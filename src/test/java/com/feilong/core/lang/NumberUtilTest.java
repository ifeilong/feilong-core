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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
