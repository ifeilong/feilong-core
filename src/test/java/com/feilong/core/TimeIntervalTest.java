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
package com.feilong.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TimeIntervalTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.1
 */
public class TimeIntervalTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeIntervalTest.class);

    /**
     * Test.
     */
    @Test
    public void test(){
        LOGGER.info("" + TimeInterval.MILLISECOND_PER_YEAR);
        LOGGER.info("" + TimeInterval.MILLISECOND_PER_MONTH);
        LOGGER.info("" + TimeInterval.MILLISECOND_PER_WEEK);
        LOGGER.info("" + TimeInterval.MILLISECOND_PER_SECONDS);
    }
}