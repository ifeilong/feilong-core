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

import java.net.HttpURLConnection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.CharsetType;

/**
 * The Class URLConnectionUtilTest.
 *
 * @author feilong
 * @version 1.0 Sep 27, 2013 10:55:18 AM
 */
public class URLConnectionUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLConnectionUtilTest.class);

    /**
     * 获得 response body as string.
     */
    @Test
    public void getResponseBodyAsString(){
        String templateFile = "http://10.8.25.80:6666/template.csv?sign=123456";

        ConnectionConfig connectionConfig = new ConnectionConfig();
        connectionConfig.setContentCharset(CharsetType.UTF8);

        String responseBodyAsString = URLConnectionUtil.getResponseBodyAsString(templateFile, connectionConfig);
        LOGGER.info(responseBodyAsString);
    }

    /**
     * 获得 response body as string1.
     */
    @Test
    public void getResponseBodyAsString1(){
        LOGGER.info("" + (null instanceof HttpURLConnection));
    }
}
