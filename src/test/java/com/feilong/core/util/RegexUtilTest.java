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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

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
     * Group.
     */
    @Test
    public void testGroup(){
        String regexPattern = "(.*?)@(.*?)";
        String email = "venusdrogon@163.com";
        assertEquals("venusdrogon", RegexUtil.group(regexPattern, email, 1));
        assertEquals("163.com", RegexUtil.group(regexPattern, email, 2));
    }

    /**
     * Test group1.
     */
    @Test
    public void testGroup1(){
        String regexPattern = "(.*?)@(.*?)";
        String email = "venusdrogon@163.com";
        assertThat(
                        RegexUtil.group(regexPattern, email),
                        allOf(hasEntry(0, "venusdrogon@163.com"), hasEntry(1, "venusdrogon"), hasEntry(2, "163.com")));
    }

    /**
     * Test group2.
     */
    @Test
    public void testGroup2(){
        String regexPattern = "@Table.*name.*\"(.*?)\".*";
        String input = "@Table(name = \"T_MEM_MEMBER_ADDRESS\")";
        assertThat(
                        RegexUtil.group(regexPattern, input),
                        allOf(hasEntry(0, "@Table(name = \"T_MEM_MEMBER_ADDRESS\")"), hasEntry(1, "T_MEM_MEMBER_ADDRESS")));
    }

    /**
     * Group22.
     */
    @Test
    public void group22(){
        String regexPattern = ".*@Column.*name.*\"(.*?)\"((?:.*)|(.*length.*(\\d+).*))";
        regexPattern = ".*@Column.*?name\\s*=\\s*\"(.*?)\"(?:.*?length\\s*=\\s*(\\d+))?";
        regexPattern = ".*@Column.*name.*\"(.*?)\".*length\\s*=\\s*(\\d+).*";
        String input = "@Column(name = \"NAME\", length=80)";
        assertThat(
                        RegexUtil.group(regexPattern, input),
                        allOf(hasEntry(0, "@Column(name = \"NAME\", length=80)"), hasEntry(1, "NAME"), hasEntry(2, "80")));
    }

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
