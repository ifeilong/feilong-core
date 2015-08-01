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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.CharsetType;
import com.feilong.core.tools.jsonlib.JsonUtil;

/**
 * The Class ParamUtilTest.
 *
 * @author feilong
 * @version 1.0 2012-3-15 下午3:48:51
 */
public class ParamUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtilTest.class);

    /** The uri. */
    private String              uri    = "http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=";

    /**
     * Adds the parameter1.
     */
    @Test
    public void addParameter1(){
        String pageParamName = "page";
        Object prePageNo = "";
        String addParameter = ParamUtil.addParameter(uri, pageParamName, prePageNo, CharsetType.UTF8);
        LOGGER.info(addParameter);
    }

    /**
     * Adds the parameter.
     */
    @Test
    public void addParameter(){
        String pageParamName = "label";
        Object prePageNo = "2-5-8-12";
        String addParameter = ParamUtil.addParameter(uri, pageParamName, prePageNo, CharsetType.UTF8);
        LOGGER.info(addParameter);
    }

    /**
     * Removes the parameter.
     */
    @Test
    public void removeParameter(){
        uri = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        String pageParamName = "label";
        String removeParameter = ParamUtil.removeParameter(uri, pageParamName, CharsetType.ISO_8859_1);
        LOGGER.info(removeParameter);
    }

    /**
     * Removes the parameter list.
     */
    @Test
    public void removeParameterList(){
        uri = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        String pageParamName = "label";
        List<String> paramNameList = new ArrayList<String>();
        paramNameList.add(pageParamName);
        paramNameList.add("keyword");

        String charsetType = CharsetType.UTF8;
        String removeParameter = ParamUtil.removeParameterList(uri, paramNameList, charsetType);
        LOGGER.info(removeParameter);
    }

    /**
     * Retention param list.
     */
    @Test
    public void retentionParamList(){
        uri = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        String pageParamName = "label";
        List<String> paramNameList = new ArrayList<String>();
        paramNameList.add(pageParamName);
        paramNameList.add("keyword");

        String charsetType = CharsetType.UTF8;
        String removeParameter = ParamUtil.retentionParamList(uri, paramNameList, charsetType);
        LOGGER.info(removeParameter);
    }

    /**
     * Combine query string.
     */
    @Test
    public void combineQueryString(){
        Map<String, String[]> keyAndArrayMap = new HashMap<String, String[]>();
        keyAndArrayMap.put("a", new String[] { "aaaa", "bbbb" });
        String charsetType = CharsetType.UTF8;
        LOGGER.info(ParamUtil.parseArrayValueMapToQueryString(keyAndArrayMap, charsetType));
        LOGGER.info(ParamUtil.parseArrayValueMapToQueryString(null, charsetType));
        LOGGER.info(ParamUtil.parseArrayValueMapToQueryString(null, null));
        LOGGER.info(ParamUtil.parseArrayValueMapToQueryString(keyAndArrayMap, null));
    }

    /**
     * Parses the query to value map.
     */
    @Test
    public void parseQueryToValueMap(){
        LOGGER.info(JsonUtil.format(ParamUtil.parseQueryStringToSingleValueMap("a=1&b=2&a=3", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.parseQueryStringToSingleValueMap("a=", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.parseQueryStringToSingleValueMap("a=1&", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.parseQueryStringToSingleValueMap("", CharsetType.UTF8)));

    }

    /**
     * Parses the query to value map.
     */
    @Test
    public void parseQueryToValueMap12(){
        LOGGER.info(JsonUtil.format(ParamUtil
                        .parseQueryStringToSingleValueMap(
                                        "subject=%E4%B8%8A%E6%B5%B7%E5%AE%9D%E5%B0%8A%E7%94%B5%E5%95%86&sign_type=MD5&notify_url=http%3A%2F%2Fwww.gymboshop.com%2Fpay%2FdoNotify%2F1.htm&out_trade_no=2014072210034383&return_url=http%3A%2F%2Fwww.gymboshop.com%2Fpay%2FdoReturn%2F1.htm&sign=a6e7dfc7b6dd54a5cd5e8ca91302f934&_input_charset=UTF-8&it_b_pay=50m&total_fee=171.00&error_notify_url=http%3A%2F%2Fwww.gymboshop.com%2Fpay%2FnotifyError.htm%3Ftype%3D1&service=create_direct_pay_by_user&paymethod=directPay&partner=2088511258288082&anti_phishing_key=KP3FUWbOTF63CIcXqg%3D%3D&seller_email=pay%40gymboree.com.cn&payment_type=1",
                                        CharsetType.UTF8)));
    }

    /**
     * Parses the query to value map1.
     */
    @Test
    public void parseQueryToValueMap1(){
        LOGGER.info(JsonUtil.format(ParamUtil.parseQueryStringToArrayValueMap("a=&b=2&a", CharsetType.UTF8)));
        LOGGER.info(JsonUtil.format(ParamUtil.parseQueryStringToArrayValueMap("a=1&b=2&a", CharsetType.UTF8)));
    }

    /**
     * Gets the encoded url by array map.
     * 
     */
    @Test
    public void testGetEncodedUrlByArrayMap(){
        String beforeUrl = "www.baidu.com";
        Map<String, String[]> keyAndArrayMap = new HashMap<String, String[]>();
        keyAndArrayMap.put("a", new String[] { "aaaa", "bbbb" });
        String charsetType = CharsetType.UTF8;

        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, charsetType));
        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, null, charsetType));
        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, null, null));
        beforeUrl = null;
        LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, null));
    }
}
