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
package com.feilong.core.util.regexutiltest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.util.RegexUtil;

import static com.feilong.core.RegexPattern.AN;
import static com.feilong.core.RegexPattern.ANS;
import static com.feilong.core.RegexPattern.DECIMAL_TWO_DIGIT;
import static com.feilong.core.RegexPattern.NUMBER;

/**
 * The Class RegexUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class RegexUtilTest{

    /**
     * Test match1.
     */
    @Test
    public void testMatch1(){
        assertEquals(false, RegexUtil.matches(NUMBER, "2000.0"));
        assertEquals(true, RegexUtil.matches(NUMBER, "02125454"));
    }

    /**
     * Test decima l_ tw o_ digit.
     */
    @Test
    public void testDecimalTwoDigit(){
        assertEquals(false, RegexUtil.matches(DECIMAL_TWO_DIGIT, "2000阿.00"));
        assertEquals(false, RegexUtil.matches(DECIMAL_TWO_DIGIT, "2000.0"));
        assertEquals(true, RegexUtil.matches(DECIMAL_TWO_DIGIT, "2000.99"));
    }

    /**
     * AN.
     */
    @Test
    public void testAN(){
        assertEquals(true, RegexUtil.matches(AN, "aa02125454"));
        assertEquals(false, RegexUtil.matches(AN, "0212545.4"));
        assertEquals(false, RegexUtil.matches(AN, "0212545$4"));
        assertEquals(false, org.apache.commons.lang3.StringUtils.isAlphanumeric("0212545$4"));
        assertEquals(true, RegexUtil.matches(AN, "02125454"));
    }

    /**
     * ANS.
     */
    @Test
    public void testANS(){
        assertEquals(true, RegexUtil.matches(ANS, "02125 454"));
    }
}
