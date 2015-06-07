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
package com.feilong.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.BeanUtilException;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.ObjectUtil;

/**
 * 数组工具类.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2010-4-16 下午01:00:27
 * @since 1.0.0
 */
public final class ArrayUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ArrayUtil.class);

    /** Don't let anyone instantiate this class. */
    private ArrayUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 得到数组中的某个元素.
     * (Returns the value of the indexed component in the specified array object.
     * The value is automatically wrapped in an object if it has a primitive type.)
     *
     * @param <T>
     *            the generic type
     * @param array
     *            数组
     * @param index
     *            索引
     * @return 返回指定数组对象中索引组件的值,the (possibly wrapped) value of the indexed component in the specified array
     * @throws IllegalArgumentException
     *             If the specified object is not an array
     * @throws ArrayIndexOutOfBoundsException
     *             If the specified {@code index} argument is negative, or if it is greater than or equal to the length of the specified
     *             array
     * @see java.lang.reflect.Array#get(Object, int)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getElement(Object array,int index) throws IllegalArgumentException,ArrayIndexOutOfBoundsException{
        return (T) Array.get(array, index);
    }

    /**
     * 将数组转成转成 {@link java.util.Iterator}.
     * <p>
     * 如果我们幸运的话，它是一个对象数组,我们可以遍历并with no copying<br>
     * 否则,异常 ClassCastException 中 ,Rats -- 它是一个基本类型数组,循环放入arrayList 转成arrayList.iterator()
     * </p>
     * <p>
     * <b>注:</b>{@link Arrays#asList(Object...)} 转的list是 {@link Array} 的内部类 ArrayList,这个类没有实现
     * {@link java.util.AbstractList#add(int, Object)} 这个方法,<br>
     * 如果拿这个list进行add操作,会出现 {@link java.lang.UnsupportedOperationException}
     * </p>
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            数组,可以是 对象数组,或者是 基本类型数组
     * @return if (null == arrays) return null;<br>
     *         否则会先将arrays转成Object[]数组,调用 {@link Arrays#asList(Object...)}转成list,再调用 {@link List#iterator()
     *         t}<br>
     *         对于基本类型的数组,由于不是 Object[],会有类型转换异常,此时先通过 {@link Array#getLength(Object)}取到数组长度,循环调用 {@link Array#get(Object, int)}设置到 list中
     * @see Arrays#asList(Object...)
     * @see Array#getLength(Object)
     * @see Array#get(Object, int)
     * @see List#iterator()
     */
    @SuppressWarnings({ "unchecked" })
    public static <T> Iterator<T> toIterator(Object arrays){
        if (null == arrays){
            return null;
        }
        List<T> list = null;
        try{
            // 如果我们幸运的话，它是一个对象数组,我们可以遍历并with no copying
            Object[] objArrays = (Object[]) arrays;
            list = (List<T>) toList(objArrays);
        }catch (ClassCastException e){
            if (log.isDebugEnabled()){
                log.debug("arrays can not cast to Object[],maybe primitive type,values is:{},{}", arrays, e.getMessage());
            }
            // Rats -- 它是一个基本类型数组
            int length = Array.getLength(arrays);
            list = new ArrayList<T>(length);
            for (int i = 0; i < length; ++i){
                Object object = Array.get(arrays, i);
                list.add((T) object);
            }
        }
        return list.iterator();
    }

    /**
     * 数组转成 ({@link java.util.ArrayList ArrayList})，此方法返回的list可以进行add等操作.
     * <p>
     * 注意 :{@link java.util.Arrays#asList(Object...) Arrays#asList(Object...)}返回的list,没有实现 {@link java.util.Collection#add(Object)
     * Collection#add(Object)}方法<br>
     * 因此,会使用 {@link ArrayList#ArrayList(java.util.Collection)} 来进行重新封装返回
     * </p>
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            T数组
     * @return 数组转成 List(ArrayList)<br>
     *         if Validator.isNullOrEmpty(arrays), return null,else return {@code new ArrayList<T>(Arrays.asList(arrays));}
     * @see java.util.Arrays#asList(Object...)
     */
    public static <T> List<T> toList(T[] arrays){
        if (Validator.isNullOrEmpty(arrays)){
            return null;
        }
        //Arrays.asList(arrays)方法 返回的是Arrays类的内部类的对象，
        //而Arrays类里的内部类ArrayList没有实现AbstractList类的add方法，导致抛异常! strList.add("c");

        List<T> list = new ArrayList<T>(Arrays.asList(arrays));
        return list;
    }

    /**
     * 任意的数组转成Integer 数组.
     * 
     * @param objects
     *            objects
     * @return Validator.isNotNullOrEmpty(objects)则返回null<br>
     *         一旦其中有值转换不了integer,则出现参数异常
     * @deprecated 转成泛型
     */
    //TODO 转成泛型
    @Deprecated
    public static Integer[] toIntegers(Object[] objects){
        if (Validator.isNullOrEmpty(objects)){
            return null;
        }

        int length = objects.length;
        Integer[] integers = new Integer[length];
        for (int i = 0; i < length; i++){
            integers[i] = ObjectUtil.toInteger(objects[i]);
        }
        return integers;
    }

    /**
     * 判断 一个数组中,是否包含某个特定的值.<br>
     * 使用equals 来比较,所以如果是 对象类型 需要自己实现equals方法.<br>
     * 支持 null的判断
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            数组
     * @param value
     *            特定值
     * @return 如果 Validator.isNotNullOrEmpty(arrays) 返回false <br>
     *         否则，循环arrays，调用 {@link ObjectUtil#equals(Object, Object, boolean)} 方法,如果为true，则返回true<br>
     * @see ObjectUtil#equals(Object, Object, boolean)
     */
    public static <T> boolean isContain(T[] arrays,T value){
        if (Validator.isNullOrEmpty(arrays)){
            return false;
        }

        for (T arr : arrays){
            if (ObjectUtil.equals(arr, value, true)){
                return true;
            }
        }
        return false;
    }

    /**
     * 将数组 通过 {@link ToStringConfig} 拼接成 字符串.
     * 
     * 
     * <pre>
     * Example 1:
     * ArrayUtil.toString(new ToStringConfig(),"a","b")---->"a,b"
     * 
     * Example 2:
     * ToStringConfig toStringConfig=new ToStringConfig(",");
     * toStringConfig.setIsJoinNullOrEmpty(false);
     * ArrayUtil.toString(new ToStringConfig(),"a","b",null)---->"a,b"
     * </pre>
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            请使用包装类型,比如 Integer []arrays,而不是 int []arrays //TODO
     * @param toStringConfig
     *            the join string entity
     * @return <ul>
     *         <li>如果 arrays 是null 或者Empty ,返回null</li>
     *         <li>否则循环,拼接 {@link ToStringConfig#getConnector()}</li>
     *         </ul>
     */
    public static <T> String toString(ToStringConfig toStringConfig,T...arrays){
        if (Validator.isNullOrEmpty(arrays)){
            return null;
        }

        if (Validator.isNullOrEmpty(toStringConfig)){
            toStringConfig = new ToStringConfig();
        }

        String connector = toStringConfig.getConnector();

        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = arrays.length; i < j; ++i){
            T t = arrays[i];

            //如果是null 或者 empty，但是参数值是不拼接，那么继续循环
            if (Validator.isNullOrEmpty(t)){
                if (!toStringConfig.getIsJoinNullOrEmpty()){
                    continue;
                }
            }
            sb.append(t);
            if (Validator.isNotNullOrEmpty(connector)){
                sb.append(connector);
            }
        }

        //由于上面的循环中，最后一个元素可能是null或者empty，判断加还是不加拼接符有点麻烦，因此，循环中统一拼接，但是循环之后做截取处理
        String returnValue = sb.toString();

        if (Validator.isNotNullOrEmpty(connector)){
            if (returnValue.endsWith(connector)){
                //去掉最后的拼接符
                return StringUtil.substringWithoutLast(returnValue, connector.length());
            }
        }

        return returnValue;
    }

    /**
     * 将array 分组.
     * 
     * <pre>
     * 
     * Example 1:
     * if Integer[] array = { 1, 1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8 };
     * 
     * will return 
     * 
     * {@code
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
     * </pre>
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
            return null;
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
     * @throws BeanUtilException
     *             the bean util exception
     * @throws NullPointerException
     *             if Validator.isNullOrEmpty(propertyName)
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @see com.feilong.core.util.CollectionsUtil#group(java.util.Collection, String)
     * @since 1.0.8
     */
    public static <O, T> Map<T, List<O>> group(O[] objectArray,String propertyName) throws BeanUtilException,NullPointerException{
        if (null == objectArray){
            return null;
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