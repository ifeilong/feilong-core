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
package com.feilong.core.date;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.LunarDateUtil;

/**
 * The Class LunarDateUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-2-19 下午4:21:32
 */
public class LunarDateUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(LunarDateUtilTest.class);

    /**
     * Gets the lunar date string.
     * 
     */
    @Test
    public void testGetLunarDateString(){
        log.info(LunarDateUtil.getLunarDateString(new Date()));
    }

    /**
     * Convert lundar calendar to solar.
     */
    @Test
    public void toSolar(){
        log.info(LunarDateUtil.toSolar(2014, 12, 30));
    }
}