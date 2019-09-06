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
import static com.feilong.core.util.MapUtil.newHashMap;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.feilong.core.lang.ThreadUtil;
import com.feilong.core.lang.thread.PartitionRunnableBuilder;
import com.feilong.core.lang.threadutiltest.entity.CalculatePartitionRunnableBuilder;
import com.feilong.core.lang.threadutiltest.entity.EmptyPartitionRunnableBuilder;

/**
 * The Class ExecuteWithParamMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.3
 */
public class ExecuteWithPartitionRunnableBuilderParamMapTest extends AbstractExcuteTest{

    /**
     * Test execute.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testExecute(){
        AtomicInteger atomicInteger = new AtomicInteger(0);

        Map<String, Object> paramsMap = newHashMap(1);
        paramsMap.put("result", atomicInteger);

        ThreadUtil.execute(toList(2, 5, 6, 7), 2, paramsMap, new CalculatePartitionRunnableBuilder());

        AtomicInteger result = (AtomicInteger) paramsMap.get("result");
        assertEquals(20, result.get());

        assertEquals(20, atomicInteger.get());
    }
    //---------------------------------------------------------

    /**
     * Test execute null list.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testExecuteNullList(){
        ThreadUtil.execute(null, 100, null, EmptyPartitionRunnableBuilder.INSTANCE);
    }

    /**
     * Test execute empty list.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testExecuteEmptyList(){
        ThreadUtil.execute(Collections.<Integer> emptyList(), 100, null, EmptyPartitionRunnableBuilder.INSTANCE);
    }

    //---------------------------------------------------------

    /**
     * Test execute invalid per size.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testExecuteInvalidPerSize(){
        ThreadUtil.execute(toList(2), 0, null, EmptyPartitionRunnableBuilder.INSTANCE);
    }

    /**
     * Test execute invalid per size 1.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testExecuteInvalidPerSize1(){
        ThreadUtil.execute(toList(2), -100, null, EmptyPartitionRunnableBuilder.INSTANCE);
    }

    //---------------------------------------------------------

    /**
     * Test execute null group runnable builder.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testExecuteNullGroupRunnableBuilder(){
        ThreadUtil.execute(toList(2), 100, null, (PartitionRunnableBuilder) null);
    }

}
