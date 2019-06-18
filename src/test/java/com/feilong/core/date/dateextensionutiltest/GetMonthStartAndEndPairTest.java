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
package com.feilong.core.date.dateextensionutiltest;

import static com.feilong.core.date.DateExtensionUtil.getMonthStartAndEndPair;
import static com.feilong.core.date.DateUtil.getFirstDateOfThisMonth;
import static com.feilong.core.date.DateUtil.getLastDateOfThisMonth;
import static com.feilong.core.date.DateUtil.now;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.6
 */
public class GetMonthStartAndEndPairTest{

    @Test
    public void testGetMonthStartAndEndPair(){
        Date NOW = now();
        Pair<Date, Date> MonthStartAndEndPair = getMonthStartAndEndPair();

        assertEquals(getFirstDateOfThisMonth(NOW), MonthStartAndEndPair.getLeft());
        assertEquals(getLastDateOfThisMonth(NOW), MonthStartAndEndPair.getRight());
    }

    //---------------------------------------------------------------

    @Test
    public void testGetMonthStartAndEndPair1(){
        Date NOW = now();
        Pair<Date, Date> MonthStartAndEndPair = getMonthStartAndEndPair(NOW);

        assertEquals(getFirstDateOfThisMonth(NOW), MonthStartAndEndPair.getLeft());
        assertEquals(getLastDateOfThisMonth(NOW), MonthStartAndEndPair.getRight());
    }

    @Test(expected = NullPointerException.class)
    public void testGetMonthStartAndEndPairNullDate(){
        getMonthStartAndEndPair(null);
    }
}
