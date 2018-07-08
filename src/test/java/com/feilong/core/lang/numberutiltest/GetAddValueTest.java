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

import java.math.BigDecimal;

import org.junit.Test;

import com.feilong.core.lang.NumberUtil;

/**
 * The Class NumberUtilGetAddValueTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetAddValueTest{

    /**
     * Test get add value null pointer exception.
     */
    @Test(expected = NullPointerException.class)
    public void testGetAddValueNullPointerException(){
        NumberUtil.getAddValue(null);
    }

    /**
     * Test get add value null pointer exception 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAddValueNullPointerException2(){
        NumberUtil.getAddValue();
    }

    /**
     * Test get add value null pointer exception 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAddValueNullPointerException1(){
        NumberUtil.getAddValue((BigDecimal) null);
    }

    /**
     * Test get add value illegal argument exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAddValueIllegalArgumentException(){
        NumberUtil.getAddValue(null, null);
    }

}
