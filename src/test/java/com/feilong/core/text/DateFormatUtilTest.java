/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

import com.feilong.core.date.DatePattern;
import com.feilong.core.text.DateFormatUtil;

/**
 * The Class DateFormatUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-5-15 上午11:36:29
 */
public class DateFormatUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(DateFormatUtilTest.class);

    /**
     * Format.
     */
    @Test
    public void format(){
        Date now = new Date();
        log.info(now.toString());
        String pattern = DatePattern.COMMON_DATE;
        pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        String nowString = DateFormatUtil.format(now, pattern, Locale.ENGLISH);
        log.info(nowString);
        log.info(now.toString().equals(nowString) + "");
    }

    /**
     * Parses the.
     */
    @Test
    public void parse(){
        Date now = new Date();
        log.info(now.toString());
        Date now1 = DateFormatUtil.parse(now.toString(), "EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        log.info(now1.toString());
    }
}
