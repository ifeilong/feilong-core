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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.StringUtil;

/**
 * The Class StringUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class StringUtilSubstringStartIndexAndLengthTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilSubstringStartIndexAndLengthTest.class);

    /** <code>{@value}</code>. */
    private static final String TEXT   = "jinxin.feilong";

    /**
     * Substring2.
     */
    @Test
    public void substring2(){
        assertEquals(null, StringUtil.substring(null, 6, 8));
        LOGGER.debug(StringUtil.substring(TEXT, TEXT.length(), 8));
        LOGGER.debug(StringUtil.substring(TEXT, TEXT.length() - 1, 8));
        LOGGER.debug(StringUtil.substring(TEXT, 1, 0));
        assertEquals("jinxi", StringUtil.substring(TEXT, 0, 5));
        assertEquals(".f", StringUtil.substring(TEXT, 6, 2));
        LOGGER.debug(StringUtil.substring(TEXT, 6, 20));
    }
}