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

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.feilong.core.CharsetType.UTF8;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public class ParamExtensionUtilTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamExtensionUtilTest.class);

    /**
     * Removes the parameter.
     */
    @Test
    public void removeParameter(){
        String uriString = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        String pageParamName = "label";
        LOGGER.debug(ParamExtensionUtil.removeParameter(uriString, pageParamName, UTF8));
    }

    /**
     * Removes the parameter list.
     */
    @Test
    public void removeParameterList(){
        String uriString = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        List<String> paramNameList = toList("label", "keyword");
        LOGGER.debug(ParamExtensionUtil.removeParameterList(uriString, paramNameList, UTF8));
    }

    /**
     * Retention param list.
     */
    @Test
    public void retentionParamList(){
        String uriString = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
        List<String> paramNameList = toList("label", "keyword");
        LOGGER.debug(ParamExtensionUtil.retentionParamList(uriString, paramNameList, UTF8));
    }
}
