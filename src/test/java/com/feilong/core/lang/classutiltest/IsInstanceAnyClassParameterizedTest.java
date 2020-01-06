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
package com.feilong.core.lang.classutiltest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.lang.ClassUtil;
import com.feilong.store.member.User;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class ClassUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsInstanceAnyClassParameterizedTest
                extends Abstract2ParamsAndResultParameterizedTest<Object, Class<?>[], Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ClassUtil.isInstanceAnyClass({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { null, toArray(Integer.class, CharSequence.class), false },
                                              { "1234", toArray(Comparable.class, CharSequence.class), true },

                                              { new User(), null, false },
                                              { new User(), toArray(Comparable.class, CharSequence.class), true },
                                              { new User(), toArray(Integer.class, CharSequence.class), false },
                //
        };
        return toList(objects);
    }

    /**
     * Test is instance any class.
     */
    @Test
    public void testIsInstanceAnyClass(){
        assertEquals(expectedValue, ClassUtil.isInstanceAnyClass(input1, input2));
    }

}
