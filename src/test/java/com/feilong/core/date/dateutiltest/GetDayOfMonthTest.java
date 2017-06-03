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

import org.junit.Test;

import static com.feilong.core.date.DateUtil.getDayOfMonth;

/**
 * The Class DateUtilGetDayOfMonthTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetDayOfMonthTest{

    //**********com.feilong.core.date.DateUtil.getDayOfMonth(Date)*****************************

    /**
     * Test get day of month null date.
     */
    @Test(expected = NullPointerException.class)
    public void testGetDayOfMonthNullDate(){
        getDayOfMonth(null);
    }

}