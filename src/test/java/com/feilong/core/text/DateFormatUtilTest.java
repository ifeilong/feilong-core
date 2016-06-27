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
package com.feilong.core.text;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DateFormatUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateFormatUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatUtilTest.class);

    /**
     * Parses the.
     */
    @Test
    public void parse(){
        Date now = new Date();
        LOGGER.debug(now.toString());
        Date now1 = DateFormatUtilTemp.parse(now.toString(), "EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        LOGGER.debug(now1.toString());

        LOGGER.debug("{}", DateFormatUtilTemp.parse("2016-06-28T01:21:12-0800", "yyyy-MM-dd'T'HH:mm:ssZZ"));
    }
}
