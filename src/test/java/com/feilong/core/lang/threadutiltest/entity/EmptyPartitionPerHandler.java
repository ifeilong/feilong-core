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
package com.feilong.core.lang.threadutiltest.entity;

import java.util.List;
import java.util.Map;

import com.feilong.core.lang.thread.PartitionPerHandler;
import com.feilong.core.lang.thread.PartitionThreadEntity;

public class EmptyPartitionPerHandler implements PartitionPerHandler<Integer>{

    /** Static instance. */
    // the static instance works for all types
    public static final EmptyPartitionPerHandler INSTANCE = new EmptyPartitionPerHandler();

    @Override
    public void handle(List<Integer> perBatchList,PartitionThreadEntity partitionThreadEntity,Map<String, ?> paramsMap){

    }

}
