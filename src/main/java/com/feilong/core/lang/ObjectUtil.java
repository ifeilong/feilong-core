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

import org.apache.commons.lang3.Validate;

/**
 * {@link Object} 工具类.
 * 
 * <h3>判断相等</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>{@link org.apache.commons.lang3.ObjectUtils#equals(Object, Object)} 支持两个值都是null的情况</li>
 * </ol>
 * </blockquote>
 *
 * @author feilong
 * @version 1.0.0 2010-4-5 下午11:00:54
 * @see org.apache.commons.lang3.ObjectUtils
 * @since 1.0.0
 */
public final class ObjectUtil{

    /** Don't let anyone instantiate this class. */
    private ObjectUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 判断对象是不是boolean类型数据.
     * 
     * @param object
     *            对象
     * @return 是返回true
     */
    public static Boolean isBoolean(Object object){
        return object instanceof Boolean;
    }

    /**
     * 判断对象是不是Integer类型.
     * 
     * @param object
     *            对象
     * @return 是返回true
     */
    public static Boolean isInteger(Object object){
        return object instanceof Integer;
    }

    /**
     * 是否是数组.
     * 
     * <h3><code>instanceof</code>和 {@link java.lang.Class#isArray()}的区别:</h3>
     * 
     * <blockquote>
     * <p>
     * 
     * In general, use the instanceof operator to test whether an object is an array.<br>
     * 
     * At the JVM level, the instanceof operator translates to a specific "instanceof" byte code, which is highly optimized in most JVM
     * implementations.<br>
     * </p>
     * 
     * <p>
     * The reflective approach (getClass().isArray()) is compiled to two separate "invokevirtual" instructions. The more generic
     * optimizations applied by the JVM to these may not be as fast as the hand-tuned optimizations inherent in the "instanceof"
     * instruction.<br>
     * </p>
     * 
     * 
     * <p>
     * 有两种特殊情况: null references 和 references to primitive arrays.<br>
     * 
     * A null reference will cause instanceof to result false, while the isArray throws a NullPointerException.<br>
     * </p>
     * 
     * <p>
     * Applied to a primitive array, the instanceof results false, but the isArray returns true.
     * </p>
     * </blockquote>
     *
     * @param object
     *            the object
     * @return true, if checks if is array
     * @see <a href="http://stackoverflow.com/questions/219881/java-array-reflection-isarray-vs-instanceof">Java array reflection: isArray
     *      vs. instanceof</a>
     * @see <a href="http://stackoverflow.com/questions/2725533/how-to-see-if-an-object-is-an-array-without-using-reflection">How to see if
     *      an object is an array without using reflection?</a>
     * @since 1.3.0
     */
    public static Boolean isArray(Object object){
        Validate.notNull(object, "object can't be null!");
        return object.getClass().isArray();
    }
}
