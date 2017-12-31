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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 判断给定的对象是否是null或者empty.
 * 
 * <p>
 * 更多说明,参见 wiki <a href="https://github.com/venusdrogon/feilong-core/wiki/Validator">Validator wiki</a>
 * </p>
 * 
 * <ol>
 * <li>{@link #isNullOrEmpty(Object)} 判断对象是否是null或者empty</li>
 * <li>{@link #isNotNullOrEmpty(Object)} 判断对象是否不是null或者不是empty</li>
 * </ol>
 * 
 * <h3>对于empty的判断,使用以下逻辑:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>{@link CharSequence},支持子类有 {@link String},{@link StringBuffer},{@link StringBuilder},使用
 * {@link org.apache.commons.lang3.StringUtils#isBlank(CharSequence)};</li>
 * <li>{@link Collection},使用其 {@link Collection#isEmpty()};</li>
 * <li>{@link Map},使用其 {@link Map#isEmpty()};</li>
 * <li>{@link Enumeration},使用 {@link Enumeration#hasMoreElements()};</li>
 * <li>{@link Iterator},使用 {@link Iterator#hasNext()};</li>
 * <li><code>数组</code>{@link java.lang.Class#isArray()},判断 {@link Array#getLength(Object)} ==0</li>
 * </ol>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see String#trim()
 * @see Map#isEmpty()
 * @see Collection#isEmpty()
 * @see Enumeration#hasMoreElements()
 * @see Iterator#hasNext()
 * @see org.apache.commons.lang3.Validate
 * @see org.apache.commons.lang3.StringUtils#isBlank(CharSequence)
 * @see org.apache.commons.lang3.StringUtils#isEmpty(CharSequence)
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(byte[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(boolean[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(char[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(int[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(long[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(short[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(float[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(double[])
 * @see org.apache.commons.lang3.ArrayUtils#isEmpty(Object[])
 * @see org.apache.commons.collections4.CollectionUtils#sizeIsEmpty(Object)
 * @see org.apache.commons.collections4.CollectionUtils#isEmpty(Collection)
 * @see org.apache.commons.collections4.CollectionUtils#isNotEmpty(Collection)
 * @see org.apache.commons.collections4.MapUtils#isEmpty(Map)
 * @see org.apache.commons.collections4.MapUtils#isNotEmpty(Map)
 * @since 1.0.0
 */
public final class Validator{

    /** Don't let anyone instantiate this class. */
    private Validator(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 判断对象 <code>value</code> 是不是 null或者empty.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// null</span>
     * Validator.isNullOrEmpty(null)                                            = true
     * 
     * <span style="color:green">//CharSequence</span>
     * Validator.isNullOrEmpty("")                                              = true
     * Validator.isNullOrEmpty("   ")                                           = true
     * 
     * Validator.isNullOrEmpty(new StringBuffer())                              = true
     * Validator.isNullOrEmpty(new StringBuffer(""))                            = true
     * Validator.isNullOrEmpty(new StringBuffer(" "))                           = true
     * 
     * Validator.isNullOrEmpty(new StringBuilder())                             = true
     * Validator.isNullOrEmpty(new StringBuilder(""))                           = true
     * Validator.isNullOrEmpty(new StringBuilder(" "))                          = true
     * 
     * <span style="color:green">//Collection</span>
     * Validator.isNullOrEmpty(new ArrayList{@code <String>}())                 = true
     * 
     * <span style="color:green">//Map</span>
     * Validator.isNullOrEmpty(new LinkedHashMap{@code <String, String>}())     = true
     * 
     * <span style="color:green">//Iterator</span>
     * Validator.isNullOrEmpty(new ArrayList{@code <String>}().iterator())      = true
     * 
     * <span style="color:green">//Enumeration</span>
     * Validator.isNullOrEmpty(toEnumeration(new ArrayList{@code <String>}()))  = true
     * 
     * <span style="color:green">//Array</span>
     * Validator.isNullOrEmpty(new String[] {})                                 = true
     * Validator.isNullOrEmpty(new Integer[][] {})                              = true
     * Validator.isNullOrEmpty(new User[] {})                                   = true
     * 
     * <span style="color:green">//Primitive Array</span>
     * Validator.isNullOrEmpty(new double[] {})                                 = true
     * Validator.isNullOrEmpty(new float[] {})                                  = true
     * Validator.isNullOrEmpty(new long[] {})                                   = true
     * Validator.isNullOrEmpty(new int[] {})                                    = true
     * Validator.isNullOrEmpty(new short[] {})                                  = true
     * Validator.isNullOrEmpty(new char[] {})                                   = true
     * Validator.isNullOrEmpty(new byte[] {})                                   = true
     * Validator.isNullOrEmpty(new boolean[] {})                                = true
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>对于empty的判断,使用以下逻辑:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>{@link CharSequence},支持子类有 {@link String},{@link StringBuffer},{@link StringBuilder},使用
     * {@link org.apache.commons.lang3.StringUtils#isBlank(CharSequence)};</li>
     * <li>{@link Collection},使用其 {@link Collection#isEmpty()};</li>
     * <li>{@link Map},使用其 {@link Map#isEmpty()};</li>
     * <li>{@link Enumeration},使用 {@link Enumeration#hasMoreElements()};</li>
     * <li>{@link Iterator},使用 {@link Iterator#hasNext()};</li>
     * <li><code>数组</code>{@link java.lang.Class#isArray()},判断 {@link Array#getLength(Object)} ==0</li>
     * </ol>
     * </blockquote>
     * 
     * @param value
     *            可以是{@link Collection},{@link Map},{@link Enumeration},{@link Iterator},{@link Iterable},{@link CharSequence},
     *            以及所有数组类型(包括原始类型数组)
     * @return 如果是null,返回true<br>
     *         如果是empty也返回true<br>
     *         其他情况返回false<br>
     * @see org.apache.commons.collections4.CollectionUtils#sizeIsEmpty(Object)
     * @see org.apache.commons.lang3.StringUtils#isBlank(CharSequence)
     */
    public static boolean isNullOrEmpty(Object value){
        if (null == value){
            return true;
        }
        // 字符串
        if (value instanceof CharSequence){
            return StringUtils.isBlank((CharSequence) value);
        }

        //---------------------------------------------------------------

        // collections 支持的类型
        if (isCollectionsSupportType(value)){
            return CollectionUtils.sizeIsEmpty(value);
        }
        return false;
    }

    //---------------------------------------------------------------

    /**
     * 判断对象 <code>value</code> 是否不是null或者empty,调用 !{@link #isNullOrEmpty(Object)} 方法 .
     * 
     * <p>
     * 示例参考 {@link #isNullOrEmpty(Object)} 方法
     * </p>
     * 
     * <h3>对于empty的判断,使用以下逻辑:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>{@link CharSequence},支持子类有 {@link String},{@link StringBuffer},{@link StringBuilder},使用
     * {@link org.apache.commons.lang3.StringUtils#isBlank(CharSequence)};</li>
     * <li>{@link Collection},使用其 {@link Collection#isEmpty()};</li>
     * <li>{@link Map},使用其 {@link Map#isEmpty()};</li>
     * <li>{@link Enumeration},使用 {@link Enumeration#hasMoreElements()};</li>
     * <li>{@link Iterator},使用 {@link Iterator#hasNext()};</li>
     * <li><code>数组</code>{@link java.lang.Class#isArray()},判断 {@link Array#getLength(Object)} ==0</li>
     * </ol>
     * </blockquote>
     * 
     * @param value
     *            可以是{@link Collection},{@link Map},{@link Enumeration},{@link Iterator},{@link Iterable},{@link CharSequence}
     *            ,以及所有数组类型(包括原始类型数组)
     * @return 如果是null,返回false<br>
     *         如果是空也返回false<br>
     *         其他情况返回true<br>
     * @see #isNullOrEmpty(Object)
     */
    public static boolean isNotNullOrEmpty(Object value){
        return !isNullOrEmpty(value);
    }

    //---------------------------------------------------------------

    /**
     * Checks if is collections support type.
     *
     * @param value
     *            the value
     * @return true, if checks if is collections support type
     * @since 1.5.2
     */
    private static boolean isCollectionsSupportType(Object value){
        // 集合或者map
        boolean isCollectionOrMap = value instanceof Collection || value instanceof Map;

        // 枚举 或者是 Iterator迭代器
        boolean isEnumerationOrIterator = value instanceof Enumeration || value instanceof Iterator;

        return isCollectionOrMap//集合或者map
                        || isEnumerationOrIterator//枚举 或者是 Iterator迭代器
                        || value.getClass().isArray()//判断数组
        ;
    }
}