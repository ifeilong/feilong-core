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
import java.util.WeakHashMap;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.util.Validator;

/**
 * 数组工具类.
 * 
 * <h3>判断是否包含</h3>
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
     * <code>
     * <pre>
     * 
     * Example 1:
     * if Integer[] array = { 1, 1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8 };
     * 
     * will return 
     *      {
     *         "1":         [
     *             1,
     *             1,
     *             1
     *         ],
     *         "2":         [
     *             2,
     *             2
     *         ],
     *         "3": [3],
     *         "4": [4],
     *         "5":         [
     *             5,
     *             5
     *         ],
     *         "6": [6],
     *         "7": [7],
     *         "8":         [
     *             8,
     *             8
     *         ]
     *     }
     * }
     * </pre></code>
     *
     * @param <T>
     *            the generic type
     * @param array
     *            the array
     * @return the map< t, list< t>>
     * @since 1.0.8
     */
    public static <T> Map<T, List<T>> group(T[] array){
        if (null == array){
            return Collections.emptyMap();
        }
        //视需求  可以换成 HashMap 或者TreeMap
        Map<T, List<T>> map = new WeakHashMap<T, List<T>>(array.length);
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
     * Group 对象.
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param objectArray
     *            对象数组
     * @param propertyName
     *            对面里面属性的名称
     * @return the map< t, list< o>>
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @see com.feilong.core.util.CollectionsUtil#group(java.util.Collection, String)
     * @since 1.0.8
     */
    public static <O, T> Map<T, List<O>> group(O[] objectArray,String propertyName){
        if (null == objectArray){
            return Collections.emptyMap();
        }

        if (Validator.isNullOrEmpty(propertyName)){
            throw new NullPointerException("the propertyName is null or empty!");
        }
        //视需求  可以换成 HashMap 或者TreeMap
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