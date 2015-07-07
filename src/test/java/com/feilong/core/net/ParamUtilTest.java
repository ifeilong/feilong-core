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
import com.feilong.core.net.ParamUtil;

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
    private String              uri = "http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=";

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

        LOGGER.info(ParamUtil.toNaturalOrderingString(object));
    }

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
}
