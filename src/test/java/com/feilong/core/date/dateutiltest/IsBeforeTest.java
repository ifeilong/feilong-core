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
import static com.feilong.core.date.DateUtil.isBefore;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class DateUtilIsBeforeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsBeforeTest{

    @Test
    @SuppressWarnings("static-method")
    public void testIsBefore(){
        assertEquals(true, isBefore(toDate("2011-03-05", COMMON_DATE), toDate("2011-03-10", COMMON_DATE)));
        assertEquals(false, isBefore(toDate("2011-05-01", COMMON_DATE), toDate("2011-04-01", COMMON_DATE)));
    }

    /**
     * Test is before null.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testIsBeforeNull(){
        isBefore(null, null);
    }

    /**
     * Test is before null when date.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testIsBeforeNullWhenDate(){
        isBefore(toDate("2011-05-01", COMMON_DATE), null);
    }

    /**
     * Test is before null date.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testIsBeforeNullDate(){
        assertEquals(false, isBefore(null, toDate("2011-04-01", COMMON_DATE)));
    }
}