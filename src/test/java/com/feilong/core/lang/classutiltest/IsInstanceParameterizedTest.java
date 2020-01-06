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

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class ClassUtilIsInstanceParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsInstanceParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Object, Class<?>, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ClassUtil.isInstance({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { new User(), null, false },
                                              { new User(), Comparable.class, true },
                                              { "1234", CharSequence.class, true },
                                              { "1234", Integer.class, false },

                                              { null, CharSequence.class, false },
                                              { null, Integer.class, false },
                //
        };
        return toList(objects);
    }

    /**
     * Test is instance.
     */
    @Test
    public void testIsInstance(){
        assertEquals(expectedValue, ClassUtil.isInstance(input1, input2));
    }

}
