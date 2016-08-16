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
package com.feilong.test;

import org.junit.runners.Parameterized.Parameter;

/**
 * 两个参数 和一个返回结果的 ParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <I>
 *            the generic type
 * @param <T>
 *            the generic type
 * @param <E>
 *            the element type
 * @since 1.8.5
 */
public abstract class AbstractTwoParamsAndOneResultParameterizedTest<I, T, E> extends AbstractParameterizedTest{

    //必须是 public 访问修饰符

    /** 第一个参数. */
    @Parameter(value = 0)
    public I input1;

    /** 第二个参数. */
    @Parameter(value = 1)
    public T input2;

    /** 期望值. */
    @Parameter(value = 2)
    public E expectedValue;

}
