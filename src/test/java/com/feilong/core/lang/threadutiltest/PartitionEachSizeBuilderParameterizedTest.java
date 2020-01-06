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
package com.feilong.core.lang.threadutiltest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.lang.thread.PartitionEachSizeThreadConfigBuilder;
import com.feilong.core.lang.thread.PartitionThreadConfig;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class PartitionEachSizeBuilderParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 2.0.0
 */
public class PartitionEachSizeBuilderParameterizedTest
                extends Abstract2ParamsAndResultParameterizedTest<Integer, PartitionThreadConfig, Integer>{

    @Parameters(name = "index:{index}: new PartitionEachSizeThreadConfigBuilder({1}).build({0})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              //如果 totalSize 小于等于  minPerThreadHandlerCount(每个线程最少处理数量), 那么直接返回totalSize ,也就是说接下来开 1 个线程就足够了
                                              { 100, new PartitionThreadConfig(2, 200), 100 },
                                              { 100, new PartitionThreadConfig(1, 200), 100 },
                                              { 100, new PartitionThreadConfig(10, 200), 100 },
                                              { 100, new PartitionThreadConfig(10, 101), 100 },
                                              { 100, new PartitionThreadConfig(10, 100), 100 },
                                              { 100, new PartitionThreadConfig(2, 100), 100 },

                                              //---------------------------------------------------------------
                                              { 100, new PartitionThreadConfig(2, 50), 50 },

                                              { 100, new PartitionThreadConfig(1, 50), 100 },

                                              { 100, new PartitionThreadConfig(2, 30), 50 },
                                              { 100, new PartitionThreadConfig(3, 30), 34 },
                                              { 100, new PartitionThreadConfig(4, 30), 25 },
                //
        };
        return toList(objects);
    }

    //---------------------------------------------------------------

    @Test
    public void test(){
        assertEquals((Object) expectedValue, new PartitionEachSizeThreadConfigBuilder(input2).build(input1));
    }

}
