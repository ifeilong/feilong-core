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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NumberFormatUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.7
 */
public class NumberFormatUtilTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberFormatUtilTest.class);

    /**
     * Test method for {@link com.feilong.core.text.NumberFormatUtil#format(java.lang.Number, java.lang.String)}.
     */
    @Test
    public void testFormat(){
        assertEquals("26", NumberFormatUtil.format(25.5, "#####"));
        assertEquals("RP 26", NumberFormatUtil.format(25.5, "RP #####"));
    }

    /**
     * Convert number to string2.
     */
    @Test
    public void testConvertNumberToString2(){
        DecimalFormat decimalFormat = new DecimalFormat("00");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        // decimalFormat.setMaximumFractionDigits(2);
        // decimalFormat.setMinimumFractionDigits(2);
        // maxFractionDigits

        BigDecimal number = BigDecimal.valueOf(88.50);
        number.setScale(2, BigDecimal.ROUND_HALF_UP);

        LOGGER.info(number.toString());
        LOGGER.info(decimalFormat.format(number));
    }
}
