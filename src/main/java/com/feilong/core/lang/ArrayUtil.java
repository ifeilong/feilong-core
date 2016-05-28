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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.feilong.core.Validator;
import com.feilong.core.bean.PropertyUtil;

/**
 * 数组工具类.
 * 
 * <p>
 * 如果你想detect所有的 array类型,你必须判断一个object is an instanceof boolean[], byte[], short[], char[], int[], long[], float[], double[], or
 * Object[],
 * 
 * <br>
 * 注:Object[] 数组 Integer/String...自定义的对象User.等数组也 instanceof Object[],二维数组不管是primitive 还是包装类型,都instanceof Object[];<br>
 * so depending on how you want to handle nested arrays, it can get complicated.
 * </p>
 * 
 * <h3>提供以下方法:</h3>
 * <blockquote>
 * <ol>
 * <li>{@link #getElement(Object, int)},得到数组中的某个元素</li>
 * <li>{@link #group(Object[])},将array 分组</li>
 * <li>{@link #group(Object[], String)},将对象数组,按照指定属性的值进行分组</li>
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
 * @author feilong
 * @version 1.4.0 2015年8月3日 上午3:06:20
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

    /**
     * 得到数组中的某个元素.
     * 
     * <p>
     * (Returns the value of the indexed component in the specified array object. <br>
     * The value is automatically wrapped in an object if it has a primitive type.)
     * </p>
     * 
     * <pre class="code">
     * 
     * Example 1:
     * 
     * Object array = new String[] { "jinxin", "feilong", "1" };
     * LOGGER.info("" + ArrayUtil.getElement(array, 2));
     * 
     * 结果:1
     * </pre>
     *
     * @param <T>
     *            the generic type
     * @param array
     *            数组
     * @param index
     *            索引
     * @return 返回指定数组对象中索引组件的值,the (possibly wrapped) value of the indexed component in the specified array
     * @throws ArrayIndexOutOfBoundsException
     *             If the specified {@code index} argument is negative, or if it is greater than or equal to the length of the specified
     *             array
     * @see java.lang.reflect.Array#get(Object, int)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getElement(Object array,int index) throws ArrayIndexOutOfBoundsException{
        return (T) Array.get(array, index);
    }

    /**
     * 将array 分组.
     * 
     * <p>
     * 返回的 {@link LinkedHashMap} ,key是分组中的值,value是分组值的列表;key的顺序是依照 <code>array</code>元素不同值的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Integer[] array = { 1, 1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8 };
     * Map{@code <Integer, List<Integer>>} group = ArrayUtil.group(array);
     * LOGGER.debug(JsonUtil.format(group));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     {
         "1":         [
             1,
             1,
             1
         ],
         "2":         [
             2,
             2
         ],
         "3": [3],
         "4": [4],
         "5":         [
             5,
             5
         ],
         "6": [6],
         "7": [7],
         "8":         [
             8,
             8
         ]
     }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param array
     *            the array
     * @return 如果 null==array,返回 {@link java.util.Collections#emptyMap()}
     * @since 1.0.8
     */
    public static <T> Map<T, List<T>> group(T[] array){
        if (null == array){
            return Collections.emptyMap();
        }
        Map<T, List<T>> map = new LinkedHashMap<T, List<T>>(array.length);
        for (T t : array){
            List<T> valueList = map.get(t);
            if (null == valueList){
                valueList = new ArrayList<T>();
            }
            valueList.add(t);
            map.put(t, valueList);
        }
        return map;
    }

    /**
     * 将对象数组,按照指定属性的值进行分组.
     * 
     * <p>
     * 返回的map是 {@link LinkedHashMap},key是指定属性名称的值,value是指定名称值所属对象list,顺序是依照属性名称值顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User[] users = {
     *                  new User("张三", 18),
     *                  new User("李四", 28),
     *                  new User("王五", 38),
     *                  new User("陈二", 18),
     *                  new User("孔六", 28),
     *                  new User("飞飞", 58) };
     * 
     * Map{@code <Integer, List<User>>} group = ArrayUtil.group(users, "age");
     * LOGGER.debug(JsonUtil.format(group));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     {
         "18":         [
                         {
                 "age": 18,
                 "name": "张三"
             },
                         {
                 
                 "age": 18,
                 "name": "陈二"
             }
         ],
         "28":         [
                         {
                 
                 "age": 28,
                 "name": "李四"
             },
                         {
                 "age": 28,
                 "name": "孔六"
             }
         ],
         "38": [        {
             "age": 38,
             "name": "王五"
         }],
         "58": [        {
             "age": 58,
             "name": "飞飞"
         }]
     }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param objectArray
     *            对象数组
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return 如果 <code>objectArray</code> 是 null或者empty,返回 {@link java.util.Collections#emptyMap()} <br>
     *         如果 <code>propertyName</code>是 null 抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code>是 blank 抛出 {@link IllegalArgumentException}<br>
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @see com.feilong.core.util.CollectionsUtil#group(java.util.Collection, String)
     * @since 1.0.8
     */
    public static <O, T> Map<T, List<O>> group(O[] objectArray,String propertyName){
        if (Validator.isNullOrEmpty(objectArray)){
            return Collections.emptyMap();
        }

        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        Map<T, List<O>> map = new LinkedHashMap<T, List<O>>(objectArray.length);
        for (O o : objectArray){
            T t = PropertyUtil.getProperty(o, propertyName);
            List<O> valueList = map.get(t);
            if (null == valueList){
                valueList = new ArrayList<O>();
            }
            valueList.add(o);
            map.put(t, valueList);
        }
        return map;
    }
}