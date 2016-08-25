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
package com.feilong.core.net;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.CharsetType.UTF8;

/**
 * The Class ParamUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ParamUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER    = LoggerFactory.getLogger(ParamUtilTest.class);

    /** <code>{@value}</code>. */
    private static String       uriString = "http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=";

    //***************com.feilong.core.net.ParamUtil.toNaturalOrderingQueryString(Map<String, String>)**********

    /**
     * Test to natural ordering string null map.
     */
    @Test
    public void testToNaturalOrderingStringNullMap(){
        assertEquals(EMPTY, ParamUtil.toNaturalOrderingQueryString(null));
    }

    /**
     * Test to natural ordering string empty map.
     */
    @Test
    public void testToNaturalOrderingStringEmptyMap(){
        assertEquals(EMPTY, ParamUtil.toNaturalOrderingQueryString(new HashMap<String, String>()));
    }

    /**
     * Test to natural ordering string.
     */
    @Test
    public void testToNaturalOrderingString(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", "create_salesorder");
        map.put("_input_charset", "gbk");
        map.put("totalActual", "210.00");
        map.put("address", "江苏南通市通州区888组888号");

        assertEquals(
                        "_input_charset=gbk&address=江苏南通市通州区888组888号&service=create_salesorder&totalActual=210.00",
                        ParamUtil.toNaturalOrderingQueryString(map));
    }

    /**
     * Test to natural ordering string 3.
     */
    @Test
    public void testToNaturalOrderingStringNullValue(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", null);
        map.put("totalActual", "210.00");
        map.put("province", "江苏省");
        assertEquals("province=江苏省&service=&totalActual=210.00", ParamUtil.toNaturalOrderingQueryString(map));
    }

    /**
     * Test to natural ordering string null key.
     */
    @Test
    public void testToNaturalOrderingStringNullKey(){
        Map<String, String> map = new HashMap<>();
        map.put("totalActual", null);
        map.put(null, "create_salesorder");
        map.put("province", "江苏省");
        assertEquals("=create_salesorder&province=江苏省&totalActual=", ParamUtil.toNaturalOrderingQueryString(map));
    }

    //***************************************************************************************************
    /**
     * Test join values.
     */
    @Test
    public void testJoinValues(){
        String value = "create_salesorder";
        String value2 = "unionpay_mobile";

        Map<String, String> map = new HashMap<String, String>();
        map.put("service", value);
        map.put("paymentType", value2);

        assertEquals(EMPTY, ParamUtil.joinValuesOrderByIncludeKeys(map, "a", "b"));
        assertEquals(value, ParamUtil.joinValuesOrderByIncludeKeys(map, "service"));
        assertEquals(value + value2, ParamUtil.joinValuesOrderByIncludeKeys(map, "service", "paymentType"));
        assertEquals(value2 + value, ParamUtil.joinValuesOrderByIncludeKeys(map, "paymentType", "service"));
    }

    /**
     * Test join values order by include keys.
     */
    @Test
    public void testJoinValuesOrderByIncludeKeys(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", "create_salesorder");
        map.put("paymentType", "unionpay_mobile");

        LOGGER.debug(ParamUtil.joinValuesOrderByIncludeKeys(map, "service", "paymentType"));
    }

    /**
     * Adds the parameter1.
     */
    @Test
    public void addParameter1(){
        String pageParamName = "page";
        Object prePageNo = "";
        LOGGER.debug(ParamUtil.addParameter(uriString, pageParamName, prePageNo, UTF8));
    }

    /**
     * Adds the parameter.
     */
    @Test
    public void addParameter(){
        String pageParamName = "label";
        String prePageNo = "2-5-8-12";
        LOGGER.debug(ParamUtil.addParameter(uriString, pageParamName, prePageNo, UTF8));
    }

    /**
     * Test join single value map.
     */
    @Test
    public void testJoinSingleValueMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put(null, null);
        //        map.put("a", "");
        //        map.put("b", null);
        //        map.put("c", "jim");
        LOGGER.debug(ParamUtil.toQueryStringUseSingleValueMap(map));
    }

    /**
     * Test join single value map1.
     */
    @Test
    public void testJoinSingleValueMap1(){
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        assertEquals("province=江苏省&city=南通市", ParamUtil.toQueryStringUseSingleValueMap(singleValueMap));
    }

    /**
     * Test join array value map.
     */
    @Test
    public void testJoinArrayValueMap(){
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("province", new String[] { "江苏省", "浙江省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.debug(ParamUtil.toQueryStringUseArrayValueMap(keyAndArrayMap));
    }

    /**
     * Combine query string.
     */
    @Test
    public void combineQueryString(){
        Map<String, String[]> keyAndArrayMap = new HashMap<String, String[]>();
        keyAndArrayMap.put("name", new String[] { "jim", "feilong", "鑫哥" });
        keyAndArrayMap.put("age", new String[] { "18" });
        keyAndArrayMap.put("love", new String[] { "sanguo" });
        LOGGER.debug(ParamUtil.toSafeQueryString(keyAndArrayMap, UTF8));
        LOGGER.debug(ParamUtil.toSafeQueryString(null, UTF8));
        LOGGER.debug(ParamUtil.toSafeQueryString(null, null));
        LOGGER.debug(ParamUtil.toSafeQueryString(keyAndArrayMap, null));
    }

    /**
     * Parses the query to value map.
     */
    @Test
    public void parseQueryToValueMap(){
        LOGGER.debug(JsonUtil.format(ParamUtil.toSingleValueMap("a=1&b=2&a=3", UTF8)));
        LOGGER.debug(JsonUtil.format(ParamUtil.toSingleValueMap("a=", UTF8)));
        LOGGER.debug(JsonUtil.format(ParamUtil.toSingleValueMap("a=1&", UTF8)));
        LOGGER.debug(JsonUtil.format(ParamUtil.toSingleValueMap("", UTF8)));
    }

    /**
     * Parses the query to value map1.
     */
    @Test
    public void testToSafeArrayValueMap(){
        LOGGER.debug(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=1&b=2&a", UTF8)));
        LOGGER.debug(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=&b=2&a", UTF8)));
        LOGGER.debug(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=1&b=2&a=5", UTF8)));
        LOGGER.debug(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=1=2&b=2&a=5", UTF8)));
    }

    /**
     * Test to safe array value map1.
     */
    @Test
    public void testToSafeArrayValueMap1(){
        LOGGER.debug(JsonUtil.format(ParamUtil.toSafeArrayValueMap(" a& &", UTF8)));
    }

    /**
     * Test to safe array value map2.
     */
    @Test
    public void testToSafeArrayValueMap2(){
        LOGGER.debug(JsonUtil.format(ParamUtil.toSafeArrayValueMap(" a", UTF8)));
    }

    /**
     * Test to single value map.
     */
    @Test
    public void testToSingleValueMap(){
        String queryString = "sec_id=MD5&format=xml&sign=cc945983476d615ca66cee41a883f6c1&v=2.0&req_data=%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E&service=alipay.wap.auth.authAndExecute&partner=2088011438559510";
        assertThat(ParamUtil.toSingleValueMap(queryString, UTF8), allOf(//
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

    /**
     * Gets the encoded url by array map.
     * 
     */
    @Test
    public void testGetEncodedUrlByArrayMap(){
        String beforeUrl = "www.baidu.com";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();
        keyAndArrayMap.put("a", new String[] { "aaaa", "bbbb" });
        keyAndArrayMap.put("name", new String[] { "aaaa", "bbbb" });
        keyAndArrayMap.put("pa", new String[] { "aaaa" });

        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, null, UTF8));
        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, null, null));
        beforeUrl = null;
        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, null));
    }

    /**
     * Test get encoded url by array map1.
     */
    @Test
    public void testGetEncodedUrlByArrayMap1(){
        String beforeUrl = "www.baidu.com";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("receiver", new String[] { "鑫哥", "feilong" });
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    /**
     * Test get encoded url by array map2.
     */
    @Test
    public void testGetEncodedUrlByArrayMap2(){
        String beforeUrl = "www.baidu.com?a=b";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.debug(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
    }

    /**
     * Test add parameter single value map.
     */
    @Test
    public void testAddParameterSingleValueMap(){
        String beforeUrl = "www.baidu.com";
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.debug(ParamUtil.addParameterSingleValueMap(beforeUrl, singleValueMap, UTF8));
    }

    /**
     * Test add parameter single value map2.
     */
    @Test
    public void testAddParameterSingleValueMap2(){
        String beforeUrl = "www.baidu.com?a=b";
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.debug(ParamUtil.addParameterSingleValueMap(beforeUrl, singleValueMap, UTF8));
    }
}
