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

import org.junit.Test;

import com.feilong.core.lang.thread.PartitionEachSizeThreadConfigBuilder;
import com.feilong.core.lang.thread.PartitionThreadConfig;

public class PartitionEachSizeBuilderTest{

    @Test(expected = IllegalArgumentException.class)
    public void testTotalSizeZero(){
        new PartitionEachSizeThreadConfigBuilder(PartitionThreadConfig.INSTANCE).build(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTotalSizeLSZero(){
        new PartitionEachSizeThreadConfigBuilder(PartitionThreadConfig.INSTANCE).build(-100);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPartitionConfig(){
        new PartitionEachSizeThreadConfigBuilder(null).build(1);
    }

    //---------------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void testNullPartitionConfigMaxThreadCountZero(){
        new PartitionEachSizeThreadConfigBuilder(new PartitionThreadConfig(0, 1)).build(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPartitionConfigMaxThreadCountLSZero(){
        new PartitionEachSizeThreadConfigBuilder(new PartitionThreadConfig(-200, 1)).build(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPartitionConfigminPerThreadHandlerCountZero(){
        new PartitionEachSizeThreadConfigBuilder(new PartitionThreadConfig(1, 0)).build(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPartitionConfigminPerThreadHandlerCountLSZero(){
        new PartitionEachSizeThreadConfigBuilder(new PartitionThreadConfig(1, -1)).build(1);
    }
}
