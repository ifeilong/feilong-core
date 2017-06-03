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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.core.util.RegexUtil;

/**
 * The Class RegexUtilGroupTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GroupTest{

    /**
     * Test group 1.
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
     * Test group 2.
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
     * Group 22.
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
}
