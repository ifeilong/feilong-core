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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.date.DateUtil;

public class ToStringOldNewPatternTest{

    /** */
    private final String dateString = "2020-01-06";

    /**
     * Test to string null date.
     */
    @Test
    public void testToStringNullDate(){
        assertEquals(null, DateUtil.toString(null, "yyyy-MM-dd", "yyyy-MM-dd"));
    }

    @Test
    public void testToStringEmpty(){
        assertEquals(null, DateUtil.toString("", "yyyy-MM-dd", "yyyy-MM-dd"));
    }

    @Test
    public void testToStringBlank(){
        assertEquals(null, DateUtil.toString(" ", "yyyy-MM-dd", "yyyy-MM-dd"));
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testToStringOldNewPatternTestNull(){
        DateUtil.toString(dateString, null, "yyyy-MM-dd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToStringOldNewPatternTestEmpty(){
        DateUtil.toString(dateString, "", "yyyy-MM-dd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToStringOldNewPatternTestBlank(){
        DateUtil.toString(dateString, "  ", "yyyy-MM-dd");
    }

    //---------------------------------------------------------------
    @Test(expected = NullPointerException.class)
    public void testToStringNewPatternTestNull(){
        DateUtil.toString(dateString, "yyyy-MM-dd", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToStringNewPatternTestEmpty(){
        DateUtil.toString(dateString, "yyyy-MM-dd", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToStringNewPatternTestBlank(){
        DateUtil.toString(dateString, "yyyy-MM-dd", "  ");
    }

}