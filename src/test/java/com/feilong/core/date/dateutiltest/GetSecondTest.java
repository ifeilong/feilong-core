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
package com.feilong.core.date.dateutiltest;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.date.DateUtil.getSecond;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class DateUtilGetSecondTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetSecondTest{

    /**
     * Test get second null date.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testGetSecondNullDate(){
        getSecond(null);
    }

    /**
     * Gets the second.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testGetSecond(){
        assertEquals(23, getSecond(toDate("2013-09-15 01:15:23", COMMON_DATE_AND_TIME)));
    }
}