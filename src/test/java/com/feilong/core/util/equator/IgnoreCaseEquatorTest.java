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
package com.feilong.core.util.equator;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class IgnoreCaseEquatorTest.
 */
public class IgnoreCaseEquatorTest extends Abstract2ParamsAndResultParameterizedTest<String, String, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index},IgnoreCaseEquator.INSTANCE.equate({0}, {1})={2}")
    public static Iterable<Object[]> data(){
        return toList(//
                        ConvertUtil.<Object> toArray(null, null, true),
                        toArray(null, "a", false),
                        toArray("Aa", "aa", true)
        //
        );
    }

    /**
     * Test contains.
     */
    @Test
    public void testContains(){
        assertEquals(expectedValue, IgnoreCaseEquator.INSTANCE.equate(input1, input2));
    }
}
