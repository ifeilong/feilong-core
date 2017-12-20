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
import static com.feilong.core.date.DateUtil.isToday;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertSame;

import java.util.Date;

import org.junit.Test;

public class IsTodayTest{

    /**
     * Test is equals.
     */
    @Test
    public void testIsToday(){
        assertSame(false, isToday(toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME)));
        assertSame(true, isToday(new Date()));
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testIsTodayNullDate(){
        isToday(null);
    }
}