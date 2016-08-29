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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.ParamUtil;

import static com.feilong.core.CharsetType.UTF8;

/**
 * The Class ParamUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ParamUtilAddParameterTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtilAddParameterTest.class);

    /**
     * Adds the parameter1.
     */
    @Test
    public void addParameter1(){
        String uriString = "http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=";
        String pageParamName = "page";
        Object prePageNo = "";
        LOGGER.debug(ParamUtil.addParameter(uriString, pageParamName, prePageNo, UTF8));
    }

    /**
     * Adds the parameter.
     */
    @Test
    public void addParameter(){
        String uriString = "http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=";
        String pageParamName = "label";
        String prePageNo = "2-5-8-12";
        LOGGER.debug(ParamUtil.addParameter(uriString, pageParamName, prePageNo, UTF8));
    }
}
