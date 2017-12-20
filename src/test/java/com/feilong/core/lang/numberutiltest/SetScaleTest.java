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

import static java.math.RoundingMode.HALF_UP;

import org.junit.Test;

import com.feilong.core.lang.NumberUtil;

/**
 * The Class NumberUtilSetScaleTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SetScaleTest{

    /**
     * Test set scale null value.
     */
    @Test(expected = NullPointerException.class)
    public void testSetScaleNullValue(){
        NumberUtil.setScale(null, 5);
    }

    /**
     * Test set scale rounding mode.
     */
    @Test(expected = NullPointerException.class)
    public void testSetScaleRoundingMode(){
        NumberUtil.setScale(null, 5, HALF_UP);
    }
}
