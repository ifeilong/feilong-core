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

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.DatePattern;
import com.feilong.core.Validator;
import com.feilong.core.lang.ClassUtil;
import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class ClassUtilIsInterfaceParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsInterfaceParameterizedTest extends Abstract1ParamAndResultParameterizedTest<Class<?>, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ClassUtil.isInterface({0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { null, false },
                                              { DatePattern.class, false },
                                              { Validator.class, false },
                                              { CharSequence.class, true },
                                              { List.class, true },
                                              { Map.class, true },
                //
        };
        return toList(objects);
    }

    /**
     * Test is interface.
     */
    @Test
    public void testIsInterface(){
        assertEquals(expectedValue, ClassUtil.isInterface(input1));
    }

}
