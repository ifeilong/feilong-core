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
package com.feilong.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.RegexPattern;

/**
 * The Class RegexUtilTest.
 * 
 * @author feilong
 * @version 1.0 Mar 29, 2013 6:54:30 PM
 */
public class RegexUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexUtilTest.class);

    /**
     * Group.
     */
    @Test
    public void group(){
        String regexPattern = "(.*?)@(.*?)";
        String email = "venusdrogon@163.com";
        assertEquals("venusdrogon", RegexUtil.group(regexPattern, email, 1));
        assertEquals("163.com", RegexUtil.group(regexPattern, email, 2));
    }

    /**
     * Group2.
     */
    @Test
    public void group2(){
        String regexPatternTable = "@Table.*name.*\"(.*?)\".*";
        //		regexPattern = "@Table[(]*\"(.*?)*[)]";
        //		regexPattern = "@Table(.*?)";
        String email = "@Table(name = \"T_MEM_MEMBER_ADDRESS\")";
        LOGGER.info(RegexUtil.group(regexPatternTable, email) + "");
    }

    /**
     * Group1.
     */
    @Test
    public void group1(){
        String regexPattern = "(.*?)(?:@)(.*?)";
        regexPattern = "(.*?)@(.*?)";
        String email = "venusdrogon@163.com";
        RegexUtil.group(regexPattern, email);
    }

    /**
     * Group22.
     */
    @Test
    public void group22(){
        String regexPatternColumn = ".*@Column.*name.*\"(.*?)\"((?:.*)|(.*length.*(\\d+).*))";
        //		REGEX_PATTERN_COLUMN = ".*@Column.*name.*\"(.*?)\".*length.*(\\d*).*";
        //		REGEX_PATTERN_COLUMN = ".*@Column.*(\\d+).*";
        regexPatternColumn = ".*@Column.*?name\\s*=\\s*\"(.*?)\"(?:.*?length\\s*=\\s*(\\d+))?";
        regexPatternColumn = ".*@Column.*name.*\"(.*?)\".*length\\s*=\\s*(\\d+).*";
        String email = "@Column(name = \"NAME\", length=80)";
        RegexUtil.group(regexPatternColumn, email);
    }

    /**
     * Test mobilephone.
     */
    @Test
    public void testMOBILEPHONE(){
        assertEquals(true, RegexUtil.matches(RegexPattern.MOBILEPHONE, "18501646315"));
    }

    /**
     * Test is ip.
     */
    @Test
    public void testIsIP(){
        assertEquals(false, RegexUtil.matches(RegexPattern.IP, "venusdrogon@163.com"));
        assertEquals(true, RegexUtil.matches(RegexPattern.IP, "127.0.0.1"));
        assertEquals(false, RegexUtil.matches(RegexPattern.IP, "127.0.0.*"));
        assertEquals(false, RegexUtil.matches(RegexPattern.IP, "327.0.0.1"));
    }

    /**
     * Test match1.
     */
    @Test
    public void testMatch1(){
        assertEquals(false, RegexUtil.matches(RegexPattern.NUMBER, "2000.0"));
        assertEquals(true, RegexUtil.matches(RegexPattern.NUMBER, "02125454"));
    }

    /**
     * Test decima l_ tw o_ digit.
     */
    @Test
    public void testDecimalTwoDigit(){
        assertEquals(false, RegexUtil.matches(RegexPattern.DECIMAL_TWO_DIGIT, "2000阿.00"));
        assertEquals(false, RegexUtil.matches(RegexPattern.DECIMAL_TWO_DIGIT, "2000.0"));
        assertEquals(true, RegexUtil.matches(RegexPattern.DECIMAL_TWO_DIGIT, "2000.99"));
    }

    /**
     * AN.
     */
    @Test
    public void testAN(){
        assertEquals(true, RegexUtil.matches(RegexPattern.AN, "aa02125454"));
        assertEquals(false, RegexUtil.matches(RegexPattern.AN, "0212545.4"));
        assertEquals(true, RegexUtil.matches(RegexPattern.AN, "02125454"));
    }

    /**
     * ANS.
     */
    @Test
    public void testANS(){
        assertEquals(true, RegexUtil.matches(RegexPattern.ANS, "02125 454"));
    }
}
