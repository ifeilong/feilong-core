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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.CharsetType;
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
     * Test to natural ordering string.
     */
    @Test
    public void testToNaturalOrderingString(){
        String[] parameters = {
                "service=create_salesorder",
                "partner=3088101011913539",
                "_input_charset=gbk",
                "code=137214341849121",
                "memberID=325465",
                "createTime=20130912150636",
                "paymentType=unionpay_mobile",
                "isNeededInvoice=true",
                "invoiceTitle=上海宝尊电子商务有限公司",
                "totalActual=210.00",
                "receiver=王小二",
                "receiverPhone=15001241318",
                "receiverMobile=0513-86651522",
                "zipCode=216000",
                "province=江苏省",
                "city=南通市",
                "district=通州区",
                "address=江苏南通市通州区平东镇甸北村1组188号",
                "lines_data=[{\"extentionCode\":\"00887224869169\",\"count\":\"2\",\"unitPrice\":\"400.00\"},{\"extentionCode\":\"00887224869170\",\"count\":\"1\",\"unitPrice\":\"500.00\"}]" };
        Map<String, String> object = new HashMap<String, String>();
        for (String string : parameters){
            String[] keyAndValue = string.split("=");
            object.put(keyAndValue[0], keyAndValue[1]);
        }
        LOGGER.info(ParamUtil.toNaturalOrderingQueryString(object));
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

        assertEquals(StringUtils.EMPTY, ParamUtil.joinValues(map, "a", "b"));
        assertEquals(value, ParamUtil.joinValues(map, "service"));
        assertEquals(value + value2, ParamUtil.joinValues(map, "service", "paymentType"));
        assertEquals(value2 + value, ParamUtil.joinValues(map, "paymentType", "service"));
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
        Object prePageNo = "2-5-8-12";
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

        LOGGER.info(ParamUtil.joinSingleValueMap(map));
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
        keyAndArrayMap.put("a", new String[] { "aaaa", "bbbb" });
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
        LOGGER.info(JsonUtil.format(ParamUtil
                        .toSingleValueMap(
                                        "subject=%E4%B8%8A%E6%B5%B7%E5%AE%9D%E5%B0%8A%E7%94%B5%E5%95%86&sign_type=MD5&notify_url=http%3A%2F%2Fwww.gymboshop.com%2Fpay%2FdoNotify%2F1.htm&out_trade_no=2014072210034383&return_url=http%3A%2F%2Fwww.gymboshop.com%2Fpay%2FdoReturn%2F1.htm&sign=a6e7dfc7b6dd54a5cd5e8ca91302f934&_input_charset=UTF-8&it_b_pay=50m&total_fee=171.00&error_notify_url=http%3A%2F%2Fwww.gymboshop.com%2Fpay%2FnotifyError.htm%3Ftype%3D1&service=create_direct_pay_by_user&paymethod=directPay&partner=2088511258288082&anti_phishing_key=KP3FUWbOTF63CIcXqg%3D%3D&seller_email=pay%40gymboree.com.cn&payment_type=1",
                                        CharsetType.UTF8)));
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

    @Test
    public void testToSingleValueMap(){
        LOGGER.info(JsonUtil.format(ParamUtil
                        .toSingleValueMap(
                                        "_input_charset=UTF-8&out_order_no=2015080310000132&partner=2088201564809153&service=close_trade&sign=dc5a40d1d554b2ef115461f0ed6c49fc&sign_type=MD5&trade_role=S",
                                        CharsetType.UTF8)));
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
