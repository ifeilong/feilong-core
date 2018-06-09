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

import com.feilong.core.lang.ThreadUtil;

/**
 * The Class ExecuteCountTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.3
 */
public class ExecuteCountTest{

    /**
     * Test execute.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testExecute(){
        ThreadUtil.execute(new Runnable(){

            @Override
            public void run(){

            }
        }, 1);
    }
    //---------------------------------------------------------

    /**
     * Test execute null list.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testExecuteNullList(){
        ThreadUtil.execute(null, 100);
    }

    //---------------------------------------------------------

    /**
     * Test execute invalid thread count.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testExecuteInvalidThreadCount(){
        ThreadUtil.execute(new Runnable(){

            @Override
            public void run(){

            }
        }, 0);
    }

    /**
     * Test execute invalid thread count 1.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testExecuteInvalidThreadCount1(){
        ThreadUtil.execute(new Runnable(){

            @Override
            public void run(){

            }
        }, -100);
    }

}
