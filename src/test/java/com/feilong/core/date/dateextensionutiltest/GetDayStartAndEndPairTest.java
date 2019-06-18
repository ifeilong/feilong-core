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

import static com.feilong.core.date.DateExtensionUtil.getDayStartAndEndPair;
import static com.feilong.core.date.DateExtensionUtil.getTodayStartAndEndPair;
import static com.feilong.core.date.DateExtensionUtil.getYesterdayStartAndEndPair;
import static com.feilong.core.date.DateUtil.addDay;
import static com.feilong.core.date.DateUtil.getFirstDateOfThisDay;
import static com.feilong.core.date.DateUtil.getLastDateOfThisDay;
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
public class GetDayStartAndEndPairTest{

    @Test
    public void testGetDayStartAndEndPair(){
        Date NOW = now();
        Pair<Date, Date> pair = getTodayStartAndEndPair();

        assertEquals(getFirstDateOfThisDay(NOW), pair.getLeft());
        assertEquals(getLastDateOfThisDay(NOW), pair.getRight());
    }

    @Test
    public void testGetYesterdayStartAndEndPair(){
        Date date = now();
        Date yesteday = addDay(date, -1);
        Pair<Date, Date> pair = getYesterdayStartAndEndPair();

        assertEquals(getFirstDateOfThisDay(yesteday), pair.getLeft());
        assertEquals(getLastDateOfThisDay(yesteday), pair.getRight());
    }

    //---------------------------------------------------------------

    @Test
    public void testGetDayStartAndEndPair1(){
        Date NOW = now();
        Pair<Date, Date> pair = getDayStartAndEndPair(NOW);

        assertEquals(getFirstDateOfThisDay(NOW), pair.getLeft());
        assertEquals(getLastDateOfThisDay(NOW), pair.getRight());
    }

    @Test(expected = NullPointerException.class)
    public void testGetDayStartAndEndPairNullDate(){
        getDayStartAndEndPair(null);
    }
}
