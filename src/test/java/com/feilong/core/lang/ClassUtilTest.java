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
package com.feilong.core.lang;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class ClassUtilTest.
 *
 * @author feilong
 * @version 1.0.8 2014年7月21日 下午3:01:52
 * @since 1.0.8
 */
public class ClassUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtilTest.class);

    /**
     * Test is instance.
     */
    @Test
    public void testIsInstance(){
        assertEquals(true, ClassUtil.isInstance(new User(), Comparable.class));
        assertEquals(true, ClassUtil.isInstance("1234", CharSequence.class));
        assertEquals(true, new User() instanceof Comparable);
        assertEquals(true, "1234" instanceof CharSequence);
    }

    /**
     * Test is instance2.
     */
    @Test
    public void testIsInstance2(){
        assertEquals(true, ClassUtil.isInstance(new User(), new Class<?>[] { Comparable.class, CharSequence.class }));
        assertEquals(false, ClassUtil.isInstance(new User(), new Class<?>[] { Integer.class, CharSequence.class }));
        assertEquals(true, ClassUtil.isInstance("1234", new Class<?>[] { Comparable.class, CharSequence.class }));
    }

    /**
     * Test is assignable from.
     */
    @Test
    public void testIsAssignableFrom(){

        Class<?>[] klsClasses = { "1234".getClass(), "55555".getClass() };
        assertEquals(true, ClassUtils.isAssignable(klsClasses, CharSequence.class));

        assertEquals(true, ClassUtil.isAssignableFrom(Comparable.class, new User().getClass()));
        assertEquals(true, ClassUtil.isAssignableFrom(CharSequence.class, "1234".getClass()));
        assertEquals(true, ClassUtils.isAssignable("1234".getClass(), CharSequence.class));
    }

    /**
     * Test to class.
     */
    @Test
    public void testToClass(){
        LOGGER.info("" + JsonUtil.format(ClassUtil.toClass("a", "a")));
        LOGGER.info("" + JsonUtil.format(ClassUtil.toClass(1, true)));
    }

    /**
     * Test is interface.
     */
    @Test
    public void testIsInterface(){
        LOGGER.info("" + ClassUtil.isInterface(this.getClass()));
        LOGGER.info("" + ClassUtil.isInterface(DatePattern.class));
    }

    /**
     * Test get class info map for LOGGER.
     */
    @Test
    public void testGetClassInfoMapForLog(){
        LOGGER.debug(JsonUtil.format(ClassUtil.getClassInfoMapForLog(this.getClass())));
        LOGGER.debug(JsonUtil.format(ClassUtil.getClassInfoMapForLog(DatePattern.class)));
    }
}
