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

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.date.DateUtil.isAfter;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class DateUtilIsAfterTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsAfterTest{

    @Test
    public void testIsAfter(){
        assertEquals(true, isAfter(toDate("2011-03-10", COMMON_DATE), toDate("2011-03-05", COMMON_DATE)));
        assertEquals(false, isAfter(toDate("2011-04-01", COMMON_DATE), toDate("2011-05-01", COMMON_DATE)));
    }

    /**
     * Test is after null.
     */
    @Test(expected = NullPointerException.class)
    public void testIsAfterNull(){
        isAfter(null, null);
    }

    /**
     * Test is after null when date.
     */
    @Test(expected = NullPointerException.class)
    public void testIsAfterNullWhenDate(){
        isAfter(toDate("2011-05-01", COMMON_DATE), null);
    }

    /**
     * Test is after null date.
     */
    @Test
    public void testIsAfterNullDate(){
        assertEquals(false, isAfter(null, toDate("2011-04-01", COMMON_DATE)));
    }
}