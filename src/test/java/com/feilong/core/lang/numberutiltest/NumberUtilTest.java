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
package com.feilong.core.lang.numberutiltest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.lang.NumberUtil;

/**
 */
public class NumberUtilTest{

    @Test
    public void testIsEqualsTestNull1(){
        assertEquals(1, NumberUtil.ONE);
        assertEquals(10, NumberUtil.TEN);
        assertEquals(100, NumberUtil.HUNDRED);
        assertEquals(1000, NumberUtil.THOUSAND);

        assertEquals(1_0000, NumberUtil.TEN_THOUSAND);
        assertEquals(10_0000, NumberUtil.HUNDRED_THOUSAND);
        assertEquals(100_0000, NumberUtil.MILLION);
        assertEquals(1000_0000, NumberUtil.TEN_MILLION);

        assertEquals(1_0000_0000, NumberUtil.HUNDRED_MILLION);
        assertEquals(10_0000_0000, NumberUtil.BILLION);
        assertEquals(100_0000_0000L, NumberUtil.TEN_BILLION);
    }

}
