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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.CharsetType;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class ParamUtilTest.
 *
 * @author feilong
 * @version 1.0 2012-3-15 下午3:48:51
 */
public class ParamUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtilTest.class);

    /** <code>{@value}</code> */
    private static String       uri    = "http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=";

    /**
     * Test to natural ordering string.
     */
    @Test
    public void testToNaturalOrderingString(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", "create_salesorder");
        map.put("_input_charset", "gbk");
        map.put("totalActual", "210.00");
        map.put("receiver", "鑫哥");
        map.put("province", "江苏省");
        map.put("city", "南通市");
        map.put("district", "通州区");
        map.put("address", "江苏南通市通州区888组888号");
        map.put(
                        "lines_data",
                        "[{\"extentionCode\":\"00887224869169\",\"count\":\"2\",\"unitPrice\":\"400.00\"},{\"extentionCode\":\"00887224869170\",\"count\":\"1\",\"unitPrice\":\"500.00\"}]");
        LOGGER.info(ParamUtil.toNaturalOrderingQueryString(map));
    }

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

        assertEquals(StringUtils.EMPTY, ParamUtil.joinValuesOrderByIncludeKeys(map, "a", "b"));
        assertEquals(value, ParamUtil.joinValuesOrderByIncludeKeys(map, "service"));
        assertEquals(value + value2, ParamUtil.joinValuesOrderByIncludeKeys(map, "service", "paymentType"));
        assertEquals(value2 + value, ParamUtil.joinValuesOrderByIncludeKeys(map, "paymentType", "service"));
    }

    @Test
    public void testJoinValuesOrderByIncludeKeys(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", "create_salesorder");
        map.put("paymentType", "unionpay_mobile");

        LOGGER.info(ParamUtil.joinValuesOrderByIncludeKeys(map, "service", "paymentType"));
    }

    /**
     * Adds the parameter1.
     */
    @Test
    public void addParameter1(){
        String pageParamName = "page";
        Object prePageNo = "";
        LOGGER.info(ParamUtil.addParameter(uri, pageParamName, prePageNo, CharsetType.UTF8));
    }

    /**
     * Adds the parameter.
     */
    @Test
    public void addParameter(){
        String pageParamName = "label";
        String prePageNo = "2-5-8-12";
        LOGGER.info(ParamUtil.addParameter(uri, pageParamName, prePageNo, CharsetType.UTF8));
    }

    /**
     * Removes the parameter.
     */
    @Test
    public void removeParameter(){
        uri = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        String pageParamName = "label";
        LOGGER.info(ParamUtil.removeParameter(uri, pageParamName, CharsetType.ISO_8859_1));
    }

    /**
     * Removes the parameter list.
     */
    @Test
    public void removeParameterList(){
        uri = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        List<String> paramNameList = new ArrayList<String>();
        paramNameList.add("label");
        paramNameList.add("keyword");
        LOGGER.info(ParamUtil.removeParameterList(uri, paramNameList, CharsetType.UTF8));
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
        LOGGER.info(ParamUtil.toQueryStringUseSingleValueMap(map));
    }

    @Test
    public void testJoinSingleValueMap1(){
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.info(ParamUtil.toQueryStringUseSingleValueMap(singleValueMap));
    }

    @Test
    public void testJoinArrayValueMap(){

        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("province", new String[] { "江苏省", "浙江省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.info(ParamUtil.toQueryStringUseArrayValueMap(keyAndArrayMap));
    }

    /**
     * Retention param list.
     */
    @Test
    public void retentionParamList(){
        uri = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        List<String> paramNameList = new ArrayList<String>();
        paramNameList.add("label");
        paramNameList.add("keyword");
        LOGGER.info(ParamUtil.retentionParamList(uri, paramNameList, CharsetType.UTF8));
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
        LOGGER.info(ParamUtil.toSafeQueryString(keyAndArrayMap, CharsetType.UTF8));
        LOGGER.info(ParamUtil.toSafeQueryString(null, CharsetType.UTF8));
        LOGGER.info(ParamUtil.toSafeQueryString(null, null));
        LOGGER.info(ParamUtil.toSafeQueryString(keyAndArrayMap, null));
    }

    /**
     * Parses the query to value map.
     */
    @Test
    public void parseQueryToValueMap(){
        LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap("a=1&b=2&a=3", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap("a=", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap("a=1&", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap("", CharsetType.UTF8)));

    }

    /**
     * Parses the query to value map.
     */
    @Test
    public void parseQueryToValueMap12(){
        String queryString = "subject=%E4%B8%8A%E6%B5%B7%E5%AE%9D%E5%B0%8A%E7%94%B5%E5%95%86&sign_type=MD5&notify_url=http%3A%2F%2Fstage.gymboshop.com%2Fpay%2FdoNotify%2F1.htm&out_trade_no=2015090210099910&return_url=http%3A%2F%2Fstage.gymboshop.com%2Fpay%2FdoReturn%2F1.htm&sign=309d124e35d574c5b5f230dac93e8221&_input_charset=UTF-8&it_b_pay=120m&total_fee=0.01&error_notify_url=http%3A%2F%2Fstage.gymboshop.com%2Fpay%2FnotifyError.htm%3Ftype%3D1&service=create_direct_pay_by_user&paymethod=directPay&partner=2088511258288082&anti_phishing_key=KP3B51bszcIOjOoNpw%3D%3D&seller_email=pay%40gymboree.com.cn&payment_type=1";
        queryString = "subject=CalvinKlein&sign_type=MD5&notify_url=http%3A%2F%2Fstaging-cn.puma.com%2Fpayment%2Falipay%2FaSynReturn.htm&out_trade_no=2015091410000044&return_url=http%3A%2F%2Fstaging-cn.puma.com%2Fpayment%2Falipay%2FsynReturn.htm&sign=c7703845019c2e0bce63cf4b0282f293&_input_charset=UTF-8&it_b_pay=24m&total_fee=0.01&error_notify_url=http%3A%2F%2Fstaging-cn.puma.com%2Fpayment%2Falipay%2FsynReturn.htm&service=create_direct_pay_by_user&paymethod=directPay&partner=2088201564862550&anti_phishing_key=KP3B5KV254mjRM_m-Q%3D%3D&seller_email=alipay-test14%40alipay.com&payment_type=1";
        LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap(queryString, CharsetType.UTF8)));
    }

    /**
     * Parses the query to value map1.
     */
    @Test
    public void testToSafeArrayValueMap(){
        LOGGER.info(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=1&b=2&a", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=&b=2&a", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=1&b=2&a=5", CharsetType.UTF8)));
    }

    /**
     * Test to single value map.
     */
    @Test
    public void testToSingleValueMap(){
        String queryString = "";
        //queryString = "_input_charset=UTF-8&out_order_no=2015080310000132&partner=2088201564809153&service=close_trade&sign=dc5a40d1d554b2ef115461f0ed6c49fc&sign_type=MD5&trade_role=S";
        queryString = "sec_id=MD5&format=xml&sign=cc945983476d615ca66cee41a883f6c1&v=2.0&req_data=%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E&service=alipay.wap.auth.authAndExecute&partner=2088011438559510";
        LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap(queryString, CharsetType.UTF8)));
    }

    @Test
    public void testToSingleValueMap2(){
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("province", new String[] { "浙江省", "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap(keyAndArrayMap)));
    }

    @Test
    public void testToArrayValueMap(){
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.info(JsonUtil.format(ParamUtil.toArrayValueMap(singleValueMap)));
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

        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, CharsetType.UTF8));
        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, null, CharsetType.UTF8));
        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, null, null));
        beforeUrl = null;
        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, null));
    }

    @Test
    public void testGetEncodedUrlByArrayMap1(){
        String beforeUrl = "www.baidu.com";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("receiver", new String[] { "鑫哥", "feilong" });
        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, CharsetType.UTF8));
    }

    @Test
    public void testGetEncodedUrlByArrayMap2(){
        String beforeUrl = "www.baidu.com?a=b";
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("province", new String[] { "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, CharsetType.UTF8));
    }

    @Test
    public void testAddParameterSingleValueMap(){
        String beforeUrl = "www.baidu.com";
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.info(ParamUtil.addParameterSingleValueMap(beforeUrl, singleValueMap, CharsetType.UTF8));
    }

    @Test
    public void testAddParameterSingleValueMap2(){
        String beforeUrl = "www.baidu.com?a=b";
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();

        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        LOGGER.info(ParamUtil.addParameterSingleValueMap(beforeUrl, singleValueMap, CharsetType.UTF8));
    }

}
