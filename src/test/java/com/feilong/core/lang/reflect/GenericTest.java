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
package com.feilong.core.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 泛型.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0
 */
public class GenericTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericTest.class);

    /**
     * Test generic test.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testGenericTest(){
        LOGGER.debug((String) getValue("jinxin", String.class));
        LOGGER.debug((String) getValue("jinxin", Integer.class));
    }

    /**
     * Gets the value.
     *
     * @param <T>
     *            the generic type
     * @param a
     *            the a
     * @param klass
     *            the klass
     * @return the value
     */
    public static <T> T getValue(String a,Class<?> klass){
        try{
            Method method = GenericTest.class.getMethod("getValue", String.class, Class.class);
            TypeVariable<?> typeVariable = (TypeVariable<?>) method.getGenericReturnType();

            LOGGER.debug(typeVariable.toString());
            LOGGER.debug(typeVariable.getName());
            LOGGER.debug("" + typeVariable.getBounds()[0]);
            LOGGER.debug(typeVariable.getGenericDeclaration().toString());
            LOGGER.debug(method.toGenericString());
            LOGGER.debug(method.toString());
        }catch (SecurityException | NoSuchMethodException e){
            LOGGER.error(e.getClass().getName(), e);
        }
        return null;
    }

}