/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.lang.annotation;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AnnotationTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014-5-30 0:25:27
 */
@MyAnnotation(name = "feilong",sex = 0,loveStrings = { "胡伟立", "三国" })
public class AnnotationTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(AnnotationTest.class);

    /**
     * Feilong.
     */
    @MyAnnotation(name = "feilong",sex = 0,loveStrings = { "王菲" })
    public void feilong(){
        throw new NotImplementedException("feilong is not implemented!");
    }

    /**
     * Jinxin.
     */
    @MyAnnotation(name = "金鑫",sex = 1,loveStrings = { "胡伟立", "三国" })
    public void jinxin(){
        throw new NotImplementedException("jinxin is not implemented!");
    }

    /**
     * TestAnnotationTest.
     */
    @Test
    public void testAnnotationTest(){
        log.info("" + AnnotationTest.class.isAnnotationPresent(MyAnnotation.class));

        MyAnnotation myAnnotation = AnnotationTest.class.getAnnotation(MyAnnotation.class);
        log.info(myAnnotation.name());

        // *************************************************************
        Method[] methods = AnnotationTest.class.getDeclaredMethods();
        for (Method method : methods){
            if (method.isAnnotationPresent(MyAnnotation.class)){
                log.info("[Test." + method.getName() + "].annotation:");
                MyAnnotation fieldAnnotation = method.getAnnotation(MyAnnotation.class);
                log.info(ArrayUtils.toString(fieldAnnotation.loveStrings()));
            }
        }
    }
}
