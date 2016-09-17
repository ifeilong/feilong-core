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

import static com.feilong.core.NumberPattern.PERCENT_WITH_NOPOINT;
import static com.feilong.core.lang.NumberUtil.getProgress;

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

    @Test
    public void formatD(){
        assertEquals("001", StringUtil.format("%03d", 1));
    }

    @Test
    public void formatNull(){
        assertEquals(EMPTY, StringUtil.format(null));
    }

    //*************************************************************************

    /**
     * TestStringUtilTest.
     */
    @Test
    public void testStringUtilTest(){
        String format = "|%1$-20s|%2$-10s|%3$-10s|";

        LOGGER.debug(StringUtil.format(format, "FirstName", "Init.", "LastName"));
        LOGGER.debug(StringUtil.format(format, "Real", "", "Gagnon"));
        LOGGER.debug(StringUtil.format(format, "Real2", "D", "Doe"));
        LOGGER.debug(StringUtil.format(format, "John", "F.", "Kennedy"));
    }

    /**
     * Format3.
     */
    @Test
    public void format3(){
        LOGGER.debug(buildMessageLog(4, 30, "第三十二章 手段", 450));
        LOGGER.debug(buildMessageLog(14, 30, "第三十三章 王允的计谋", 6100));
        LOGGER.debug(buildMessageLog(1, 30, "第二十一章 恰似无情却有情", 60));
    }

    /**
     * Builds the message log.
     *
     * @param writeIndex
     *            the write index
     * @param bookSectionUrlMapSize
     *            the book section url map size
     * @param sectionName
     *            the section name
     * @param contentLength
     *            the content length
     * @return the string
     */
    private static String buildMessageLog(int writeIndex,int bookSectionUrlMapSize,String sectionName,int contentLength){
        //进度,百分比
        String progress = getProgress(writeIndex + 1, bookSectionUrlMapSize, PERCENT_WITH_NOPOINT);
        String format = "%1$-20s %2$-6s %3$-3s";

        return StringUtil.format(format, sectionName, "" + contentLength, progress).replace(" ", "\u3000");
        //String padStr = "-";
        //return StringUtils.rightPad(sectionName, 40, padStr) + StringUtils.rightPad("" + contentLength, 6, padStr) + progress;
    }

    @Test
    public void format1(){
        Date date = new Date();
        LOGGER.debug(String.format("The date: %tY-%tm-%td", date, date, date));
        LOGGER.debug(String.format("The date: %1$tY-%1$tm-%1$td", date));
        LOGGER.debug(String.format("Time with tz: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL%1$tz", date));
    }
}