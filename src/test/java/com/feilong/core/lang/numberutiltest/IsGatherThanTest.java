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
 * The Class IsGatherThanTest.
 */
public class IsGatherThanTest{

    /**
     * Test is gather than test null 1.
     */
    @Test(expected = NullPointerException.class)
    public void testIsGatherThanTestNull1(){
        NumberUtil.isGatherThan(null, 1);
    }

    /**
     * Test is gather than test null 2.
     */
    @Test(expected = NullPointerException.class)
    public void testIsGatherThanTestNull2(){
        NumberUtil.isGatherThan(1, null);
    }

    /**
     * Test is gather than test null 3.
     */
    @Test(expected = NullPointerException.class)
    public void testIsGatherThanTestNull3(){
        NumberUtil.isGatherThan(null, null);
    }

}
