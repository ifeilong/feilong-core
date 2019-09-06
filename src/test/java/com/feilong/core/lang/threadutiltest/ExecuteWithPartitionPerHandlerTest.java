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

import java.util.Collections;

import org.junit.Test;

import com.feilong.core.lang.ThreadUtil;
import com.feilong.core.lang.thread.PartitionPerHandler;
import com.feilong.core.lang.threadutiltest.entity.EmptyPartitionPerHandler;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.3
 */
public class ExecuteWithPartitionPerHandlerTest extends AbstractExcuteTest{

    @Test
    public void testExecute(){
        ThreadUtil.execute(toList(2, 5, 6, 7), 2, EmptyPartitionPerHandler.INSTANCE);
    }

    //---------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testExecuteNullList(){
        ThreadUtil.execute(null, 100, EmptyPartitionPerHandler.INSTANCE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteEmptyList(){
        ThreadUtil.execute(Collections.<Integer> emptyList(), 100, EmptyPartitionPerHandler.INSTANCE);
    }

    //---------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteInvalidPerSize(){
        ThreadUtil.execute(toList(2), 0, EmptyPartitionPerHandler.INSTANCE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteInvalidPerSize1(){
        ThreadUtil.execute(toList(2), -100, EmptyPartitionPerHandler.INSTANCE);
    }

    //---------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testExecuteNullGroupRunnableBuilder(){
        ThreadUtil.execute(toList(2), 100, (PartitionPerHandler) null);
    }

}
