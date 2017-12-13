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

import java.lang.reflect.Array;

import org.apache.commons.lang3.Validate;

/**
 * 数组工具类.
 * 
 * <h3>提供以下方法:</h3>
 * <blockquote>
 * <ol>
 * <li>{@link #getElement(Object, int)},得到数组中的某个元素</li>
 * <li>{@link #newArray(Class, int)},构造一个指定类型以及指定长度的数组.</li>
 * </ol>
 * </blockquote>
 * 
 * 
 * <h3>判断是否包含:</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(boolean[], boolean)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(byte[], byte)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(char[], char)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(double[], double)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(float[], float)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(int[], int)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(long[], long)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(Object[], Object)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(short[], short)}</li>
 * <li>{@link org.apache.commons.lang3.ArrayUtils#contains(double[], double, double)}</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>判断对象是数组:</h3>
 * <blockquote>
 * <ol>
 * <li>如果你想detect所有的 array类型,你必须判断一个object is an instanceof boolean[], byte[], short[], char[], int[], long[], float[], double[], or
 * Object[]</li>
 * <li>Object[] 数组 Integer/String...自定义的对象User.等数组也 instanceof Object[],二维数组不管是primitive 还是包装类型,都instanceof Object[];<br>
 * so depending on how you want to handle nested arrays, it can get complicated.</li>
 * </ol>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.ArrayUtils
 * @since 1.4.0
 */
public final class ArrayUtil{

    /** Don't let anyone instantiate this class. */
    private ArrayUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 得到指定数组对象 <code>array</code> 中指定索引 <code>index</code> 的元素.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>支持 primitive type类型数组</li>
     * <li>
     * Returns the value of the indexed component in the specified array object. <br>
     * The value is automatically wrapped in an object if it has a primitive type.
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ArrayUtil.getElement(new String[] { "jinxin", "feilong", "1" }, 2)   =   1
     * ArrayUtil.getElement(new int[] { 5, 8, 2, 0 }, 2)                    =   2
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param array
     *            数组
     * @param index
     *            索引,从0开始
     * @return 如果指定的参数{@code index}是负数,或者大于等于指定数组 <code>array</code> 的长度,抛出 {@link ArrayIndexOutOfBoundsException}
     * @throws NullPointerException
     *             如果 <code>array</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>array</code> 不是数组
     * @see java.lang.reflect.Array#get(Object, int)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getElement(Object array,int index){
        Validate.notNull(array, "array can't be null!");
        return (T) Array.get(array, index);
    }

    /**
     * 构造一个指定类型 <code>componentType</code> 以及指定长度 <code>length</code>的数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * assertArrayEquals(new Integer[] {}, ArrayUtil.newArray(Integer.class, 0));
     * assertArrayEquals(new Integer[] { null, null, null }, ArrayUtil.newArray(Integer.class, 3));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param componentType
     *            数组的类型
     * @param length
     *            数组的长度
     * @return 如果 <code>componentType</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 {@code length < 0} ,抛出 {@link IllegalArgumentException}<br>
     * @see java.lang.reflect.Array#newInstance(Class, int)
     * @see java.lang.reflect.Array#newInstance(Class, int...)
     * @see "com.google.common.collect#newArray(Class, int)"
     * @since 1.6.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<T> componentType,int length){
        Validate.notNull(componentType, "componentType can't be null!");
        Validate.isTrue(length >= 0, "length:[%s],must >=0", length);
        return (T[]) Array.newInstance(componentType, length);
    }

}