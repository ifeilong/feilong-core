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
 * The Class ClassUtilIsAssignableFromParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsAssignableFromParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Class<?>, Class<?>, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ClassUtil.isAssignableFrom({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { Comparable.class, new User().getClass(), true },
                                              { null, new User().getClass(), false },

                                              { CharSequence.class, "1234".getClass(), true },
                                              { CharSequence.class, null, false },
                //
        };
        return toList(objects);
    }

    /**
     * Test is assignable from.
     */
    @Test
    public void testIsAssignableFrom(){
        assertEquals(expectedValue, ClassUtil.isAssignableFrom(input1, input2));
    }

}
