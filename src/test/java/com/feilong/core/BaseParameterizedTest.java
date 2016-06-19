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
package com.feilong.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

/**
 * The Class BaseParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <I>
 *            the generic type
 * @param <E>
 *            the element type
 * @since 1.6.4
 */
@RunWith(Parameterized.class)
public abstract class BaseParameterizedTest<I, E> {
    //java.lang.IllegalAccessException: 
    //Class org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParameters can not access a member of class 
    //com.feilong.core.BaseParameterizedTest with modifiers "protected"

    //必须是 public 访问修饰符

    /** The f input. */
    @Parameter
    // first data value (0) is default
    public I input;

    /** The f expected. */
    @Parameter(value = 1)
    public E expectedValue;

    /**
     * To list.
     *
     * @param valids
     *            the valids
     * @param invalids
     *            the invalids
     * @return the list
     * @since 1.6.4
     */
    protected static List<Object[]> toList(Object[] valids,Object[] invalids){
        List<Object[]> list = new ArrayList<Object[]>();
        for (Object valid : valids){
            list.add(new Object[] { valid, true });
        }
        for (Object invalid : invalids){
            list.add(new Object[] { invalid, false });
        }
        return list;
    }
}
