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
package com.feilong.core.net.paramutiltest;

import static com.feilong.core.CharsetType.UTF8;
import static com.feilong.core.net.ParamUtil.toSingleValueMap;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * The Class ParamUtilToSingleValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToSingleValueMapTest{

    /**
     * Parses the query to value map.
     */
    @Test
    public void parseQueryToValueMap(){
        assertThat(
                        toSingleValueMap("a=1&b=2", UTF8),
                        allOf(//
                                        hasEntry("a", "1"),
                                        hasEntry("b", "2")));

        assertThat(toSingleValueMap("a=", UTF8), allOf(hasEntry("a", "")));
        assertThat(toSingleValueMap("a=1&", UTF8), allOf(hasEntry("a", "1")));
    }

    /**
     * Test to single value map duplicate param.
     */
    @Test
    public void testToSingleValueMapDuplicateParam(){
        assertThat(
                        toSingleValueMap("a=1&b=2&a=3", UTF8),
                        allOf(//
                                        hasEntry("a", "1"),
                                        hasEntry("b", "2")));
    }

    /**
     * Test to single value map decode.
     */
    @Test
    public void testToSingleValueMapDecode(){
        assertThat(
                        toSingleValueMap("city=上海&province=江苏省", null),
                        allOf(//
                                        hasEntry("city", "上海"),
                                        hasEntry("province", "江苏省")));
    }

    /**
     * Test to single value map.
     */
    @Test
    public void testToSingleValueMap(){
        String queryString = "sec_id=MD5&format=xml&sign=cc945983476d615ca66cee41a883f6c1&v=2.0&req_data=%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E&service=alipay.wap.auth.authAndExecute&partner=2088011438559510";
        assertThat(
                        toSingleValueMap(queryString, UTF8),
                        allOf(//
                                        hasEntry("sec_id", "MD5"),
                                        hasEntry("format", "xml"),
                                        hasEntry("sign", "cc945983476d615ca66cee41a883f6c1"),
                                        hasEntry("v", "2.0"),
                                        hasEntry(
                                                        "req_data",
                                                        "%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E"),
                                        hasEntry("service", "alipay.wap.auth.authAndExecute"),
                                        hasEntry("partner", "2088011438559510")));
    }

    //---------------------------------------------------------------

    /**
     * Test to single value map null query string.
     */
    @Test
    public void testToSingleValueMapNullQueryString(){
        assertEquals(emptyMap(), toSingleValueMap(null, UTF8));
    }

    /**
     * Test to single value map empty query string.
     */
    @Test
    public void testToSingleValueMapEmptyQueryString(){
        assertEquals(emptyMap(), toSingleValueMap("", UTF8));
    }

    /**
     * Test to single value map blank query string.
     */
    @Test
    public void testToSingleValueMapBlankQueryString(){
        assertEquals(emptyMap(), toSingleValueMap(" ", UTF8));
    }
}
