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

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.feilong.core.lang.PartitionRunnableBuilder;
import com.feilong.core.lang.PartitionThreadEntity;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.4
 */
public class CalculatePartitionRunnableBuilder implements PartitionRunnableBuilder<Integer>{

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.core.lang.PartitionRunnableBuilder#build(java.util.List, com.feilong.core.lang.PartitionThreadEntity, java.util.Map)
     */
    @Override
    public Runnable build(final List<Integer> perBatchList,PartitionThreadEntity partitionThreadEntity,Map<String, ?> paramsMap){
        final AtomicInteger atomicInteger = (AtomicInteger) paramsMap.get("result");

        return new Runnable(){

            @Override
            public void run(){
                for (Integer value : perBatchList){
                    atomicInteger.addAndGet(value);
                }
            }
        };
    }
}
