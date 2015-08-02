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
package com.feilong.core.tools.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Slf4jUtilTest.
 * 
 * @author feilong
 * @version 1.0 Dec 30, 2013 2:26:38 AM
 */
public class Slf4jUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jUtilTest.class);

    /**
     * Test.
     */
    @Test
    public final void test(){
        LOGGER.info(Slf4jUtil.formatMessage("{},{}", "今天", "aaaa"));
        LOGGER.info(Slf4jUtil.formatMessage(null, "今天", "aaaa"));
        LOGGER.info(Slf4jUtil.formatMessage("", "今天", "aaaa"));
        Object arg = null;
        LOGGER.info(Slf4jUtil.formatMessage("", arg));
        LOGGER.info(Slf4jUtil.formatMessage("", ""));
    }
}
