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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The Class TimeIntervalTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.1
 */
public class TimeIntervalTest{

    /**
     * Test.
     */
    @Test
    public void test(){
        assertTrue(31536000 == TimeInterval.SECONDS_PER_YEAR);
        assertTrue(2592000 == TimeInterval.SECONDS_PER_MONTH);
        assertTrue(604800 == TimeInterval.SECONDS_PER_WEEK);
        assertTrue(86400 == TimeInterval.SECONDS_PER_DAY);
        assertTrue(3600 == TimeInterval.SECONDS_PER_HOUR);
        assertTrue(60 == TimeInterval.SECONDS_PER_MINUTE);
        assertTrue(1 == TimeInterval.SECONDS_PER_SECOND);
    }

    /**
     * Test1.
     */
    @Test
    public void test1(){
        assertTrue(31536000000L == TimeInterval.MILLISECOND_PER_YEAR);
        assertTrue(2592000000L == TimeInterval.MILLISECOND_PER_MONTH);
        assertTrue(604800000 == TimeInterval.MILLISECOND_PER_WEEK);
        assertTrue(86400000 == TimeInterval.MILLISECOND_PER_DAY);
        assertTrue(3600000 == TimeInterval.MILLISECOND_PER_HOUR);
        assertTrue(60000 == TimeInterval.MILLISECOND_PER_MINUTE);
        assertTrue(1000 == TimeInterval.MILLISECOND_PER_SECONDS);
    }
}