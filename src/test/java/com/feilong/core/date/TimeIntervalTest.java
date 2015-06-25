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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TimeIntervalTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.2.1 2015年6月25日 上午11:04:59
 * @since 1.2.1
 */
public class TimeIntervalTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(TimeIntervalTest.class);

    /**
     * Test.
     */
    @Test
    public final void test(){
        log.info("" + TimeInterval.MILLISECOND_PER_YEAR);
        log.info("" + TimeInterval.MILLISECOND_PER_MONTH);
        log.info("" + TimeInterval.MILLISECOND_PER_WEEK);
    }
}