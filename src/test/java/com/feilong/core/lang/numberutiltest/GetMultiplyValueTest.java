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

import org.junit.Test;

import com.feilong.core.lang.NumberUtil;

/**
 * The Class NumberUtilGetMultiplyValueTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetMultiplyValueTest{

    /**
     * Gets the progress5.
     * 
     */
    @Test(expected = NullPointerException.class)
    public void testGetMultiplyValue1(){
        NumberUtil.getMultiplyValue(null, 1, 2);
    }

    /**
     * Test get multiply value2.
     */
    @Test(expected = NullPointerException.class)
    public void testGetMultiplyValue2(){
        NumberUtil.getMultiplyValue(1, null, 2);
    }

    /**
     * Test get multiply value3.
     */
    @Test(expected = NullPointerException.class)
    public void testGetMultiplyValue3(){
        NumberUtil.getMultiplyValue(null, null, 2);
    }
}
