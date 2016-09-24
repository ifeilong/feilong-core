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
package com.feilong.core.lang.stringutiltest;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.StringUtil;

/**
 * The Class StringUtilFormatTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class StringUtilFormatTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilFormatTest.class);

    /**
     * Format.
     */
    @Test
    public void format(){
        LOGGER.debug(StringUtil.format("%s%n%s%h", 1.2d, 2, "feilong"));
        LOGGER.debug(StringUtil.format("%+d", -5));
        LOGGER.debug(StringUtil.format("%-5d", -5));
        LOGGER.debug(StringUtil.format("%05d", -5));
        LOGGER.debug(StringUtil.format("% 5d", -5));
        LOGGER.debug(StringUtil.format("%,f", 5585458.254f));
        LOGGER.debug(StringUtil.format("%(f", -5585458.254f));
        LOGGER.debug(StringUtil.format("%#f", -5585458.254f));
        LOGGER.debug(StringUtil.format("%f和%<3.3f", 9.45));
        LOGGER.debug(StringUtil.format("%2$s,%1$s", 99, "abc"));
        LOGGER.debug(StringUtil.format("%1$s,%1$s", 99));
    }

    /**
     * Format D.
     */
    @Test
    public void formatD(){
        assertEquals("001", StringUtil.format("%03d", 1));
    }

    /**
     * Format null.
     */
    @Test
    public void formatNull(){
        assertEquals(EMPTY, StringUtil.format(null));
    }

    //********************************************************************

    /**
     * Format 1.
     */
    @Test
    public void format1(){
        Date date = new Date();
        LOGGER.debug(String.format("The date: %tY-%tm-%td", date, date, date));
        LOGGER.debug(String.format("The date: %1$tY-%1$tm-%1$td", date));
        LOGGER.debug(String.format("Time with tz: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL%1$tz", date));
    }
}