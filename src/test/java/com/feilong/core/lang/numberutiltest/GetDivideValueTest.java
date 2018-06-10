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
 * The Class NumberUtilGetDivideValueTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetDivideValueTest{

    /**
     * Gets the divide value 1.
     *
     */
    @Test(expected = NullPointerException.class)
    public void getDivideValue1(){
        NumberUtil.getDivideValue(null, 0, 0);
    }

    /**
     * Gets the divide value 2.
     */
    @Test(expected = NullPointerException.class)
    public void getDivideValue2(){
        NumberUtil.getDivideValue(0, null, 0);
    }

    /**
     * Gets the divide value.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void getDivideValue(){
        NumberUtil.getDivideValue(new BigDecimal(6), 0, 0);
    }
}
