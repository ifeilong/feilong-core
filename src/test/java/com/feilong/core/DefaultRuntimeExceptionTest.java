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

import org.junit.Test;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.11.5
 */
public class DefaultRuntimeExceptionTest{

    @Test(expected = DefaultRuntimeException.class)
    public void test(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new DefaultRuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void test1(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //---------------------------------------------------------------

    @Test(expected = DefaultRuntimeException.class)
    //@Test
    public void testDefaultRuntimeException(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new DefaultRuntimeException("", e);
        }
    }

    @Test(expected = RuntimeException.class)
    //@Test()
    public void testRuntimeException(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new RuntimeException("", e);
        }
    }

    //---------------------------------------------------------------
    @Test(expected = DefaultRuntimeException.class)
    //@Test
    public void testDefaultRuntimeExceptionNull(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new DefaultRuntimeException(null, e);
        }
    }

    @Test(expected = RuntimeException.class)
    //@Test()
    public void testRuntimeExceptionNull(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new RuntimeException(null, e);
        }
    }

    //---------------------------------------------------------------
    @Test(expected = DefaultRuntimeException.class)
    //@Test
    public void testDefaultRuntimeExceptionMessageAppend(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new DefaultRuntimeException("exception", e);
        }
    }

    @Test(expected = RuntimeException.class)
    //@Test()
    public void testRuntimeExceptionMessage(){
        try{
            int i = 1 / 0;
        }catch (Exception e){
            throw new RuntimeException("exception", e);
        }
    }
}
