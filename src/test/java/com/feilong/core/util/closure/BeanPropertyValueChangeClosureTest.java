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
package com.feilong.core.util.closure;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.store.member.User;

/**
 * The Class BeanPropertyValueChangeClosureTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.7
 */
public class BeanPropertyValueChangeClosureTest{

    /**
     * Test bean property value change closure test 1.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testBeanPropertyValueChangeClosureTest1(){
        User user = new User();

        BeanPropertyValueChangeClosure<User> beanPropertyValueChangeClosure = new BeanPropertyValueChangeClosure("name", "feilong");
        beanPropertyValueChangeClosure.execute(user);

        assertThat(user, allOf(hasProperty("name", is("feilong"))));
    }

    /**
     * Test bean property value change closure test.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testBeanPropertyValueChangeClosureTest(){
        BeanPropertyValueChangeClosure<User> beanPropertyValueChangeClosure = new BeanPropertyValueChangeClosure("name", 1);
        beanPropertyValueChangeClosure.execute(null);
    }

    //---------------------------------------------------------------

    /**
     * Test bean property value change closure test null.
     */
    @SuppressWarnings("static-method")
    @Test(expected = NullPointerException.class)
    public void testBeanPropertyValueChangeClosureTestNull(){
        new BeanPropertyValueChangeClosure<User>(null, 1);
    }

    /**
     * Test bean property value change closure test empty.
     */
    @SuppressWarnings("static-method")
    @Test(expected = IllegalArgumentException.class)
    public void testBeanPropertyValueChangeClosureTestEmpty(){
        new BeanPropertyValueChangeClosure<User>("", 1);
    }

    /**
     * Test bean property value change closure test blank.
     */
    @SuppressWarnings("static-method")
    @Test(expected = IllegalArgumentException.class)
    public void testBeanPropertyValueChangeClosureTestBlank(){
        new BeanPropertyValueChangeClosure<User>(" ", 1);
    }
}
