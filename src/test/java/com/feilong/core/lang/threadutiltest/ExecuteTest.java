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
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.feilong.core.lang.PartitionRunnableBuilder;
import com.feilong.core.lang.PartitionThreadEntity;
import com.feilong.core.lang.ThreadUtil;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.3
 */
public class ExecuteTest{

    @Test
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

    @Test(expected = NullPointerException.class)
    public void testExecuteNullList(){
        ThreadUtil.execute(null, 100, null, build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteEmptyList(){
        ThreadUtil.execute(emptyList(), 100, null, build());
    }

    //---------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteInvalidPerSize(){
        ThreadUtil.execute(toList(2), 0, null, this.<Integer> build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteInvalidPerSize1(){
        ThreadUtil.execute(toList(2), -100, null, this.<Integer> build());
    }

    //---------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testExecuteNullGroupRunnableBuilder(){
        ThreadUtil.execute(toList(2), 100, null, null);
    }

    //---------------------------------------------------------

    private <T> PartitionRunnableBuilder<T> build(){
        return new PartitionRunnableBuilder<T>(){

            @Override
            public Runnable build(List<T> perBatchList,PartitionThreadEntity partitionThreadEntity,Map<String, ?> paramsMap){
                return null;
            }
        };
    }

    //--------------

    private List<Integer> buildList(int start,int end){
        List<Integer> list = new ArrayList<>();
        for (int i = start; i < end; ++i){
            list.add(i);
        }
        return list;
    }
}
