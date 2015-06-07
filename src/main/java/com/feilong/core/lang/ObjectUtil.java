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
package com.feilong.core.lang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.iterators.EnumerationIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.UncheckedIOException;
import com.feilong.core.util.ArrayUtil;
import com.feilong.core.util.Validator;

/**
 * object工具类.
 * 
 * @author 金鑫 2010-4-5 下午11:00:54
 * @since 1.0.0
 */
public final class ObjectUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ObjectUtil.class);

    /** Don't let anyone instantiate this class. */
    private ObjectUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 返回对象内存大小.
     * 
     * <p>
     * <span style="color:red">只有支持 {@link java.io.Serializable Serializable}或 {@link java.io.Externalizable Externalizable} 接口的对象才能被
     * {@link java.io.ObjectInputStream ObjectInputStream}/{@link java.io.ObjectOutputStream ObjectOutputStream}所操作！</span>
     * </p>
     *
     * @param serializable
     *            the object
     * @return the int
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see ByteArrayOutputStream#size()
     * @since 1.0.7
     */
    //XXX 这个需要check下,可能有更好的方案
    public static int size(Serializable serializable) throws UncheckedIOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            return byteArrayOutputStream.size();
        }catch (IOException e){
            log.error("", e);
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 支持将
     * <ul>
     * <li>逗号分隔的字符串</li>
     * <li>数组</li>
     * <li>{@link java.util.Map},将key 转成{@link java.util.Iterator}</li>
     * <li>{@link java.util.Collection}</li>
     * <li>{@link java.util.Iterator}</li>
     * <li>{@link java.util.Enumeration}</li>
     * </ul>
     * 转成Iterator.
     *
     * @param <T>
     *            the generic type
     * @param object
     *            <ul>
     *            <li>逗号分隔的字符串</li>
     *            <li>数组</li>
     *            <li>map,将key 转成Iterator</li>
     *            <li>Collection</li>
     *            <li>Iterator</li>
     *            <li>Enumeration</li>
     *            </ul>
     * @return <ul>
     *         <li>如果 null == object 返回null,</li>
     *         <li>否则转成Iterator</li>
     *         </ul>
     * @see ArrayUtil#toIterator(Object)
     * @see Collection#iterator()
     * @see Iterator
     * @see Map#keySet()
     * @see Set#iterator()
     * @see org.apache.commons.collections.iterators.EnumerationIterator#EnumerationIterator(Enumeration)
     * @since Commons Collections 1.0
     */
    @SuppressWarnings("unchecked")
    public static final <T> Iterator<T> toIterator(Object object){
        if (null == object){
            return null;
        }
        // object 不是空
        // 数组
        if (object.getClass().isArray()){
            return ArrayUtil.toIterator(object);
        }
        // Collection
        else if (object instanceof Collection){
            return ((Collection<T>) object).iterator();
        }
        // Iterator
        else if (object instanceof Iterator){
            return (Iterator<T>) object;
        }
        // Enumeration
        else if (object instanceof Enumeration){
            Enumeration<T> enumeration = (Enumeration<T>) object;
            EnumerationIterator enumerationIterator = new EnumerationIterator(enumeration);
            return enumerationIterator;
        }
        // map
        else if (object instanceof Map){
            Set<T> keySet = ((Map<T, ?>) object).keySet();
            return keySet.iterator();
        }
        // 逗号分隔的字符串
        else if (object instanceof String){
            String[] strings = object.toString().split(",");
            return ArrayUtil.toIterator(strings);
        }else{
            throw new IllegalArgumentException("param object:[" + object + "] don't support convert to Iterator.");
        }
    }

    //****************************************************************************************
    /**
     * 非空判断两个值是否相等 <br>
     * 当两个值都不为空,且object.equals(object2)才返回true
     * 
     * @param object
     *            object
     * @param object2
     *            object2
     * @return 当两个值都不为空,且object.equals(object2)才返回true
     */
    public static final boolean equalsNotNull(Object object,Object object2){
        if (Validator.isNotNullOrEmpty(object) && Validator.isNotNullOrEmpty(object2)){
            return object.equals(object2);
        }
        return false;
    }

    /**
     * 判断两个值是否相等,允许两个值都为null.
     * 
     * @param object
     *            object
     * @param object2
     *            object2
     * @param nullTypeFlag
     *            标识null和""相比的情况,默认值为false 标识不相等
     * @return 判断两个值是否相等
     * @see java.util.Objects#equals(Object, Object)
     * @see org.apache.commons.lang3.ObjectUtils#equals(Object, Object)
     */
    public static final Boolean equals(Object object,Object object2,boolean nullTypeFlag){
        //TODO Either override Object.equals(Object), or totally rename the method to prevent any confusion.
        //Methods named "equals" should override Object.equals(Object)
        if (object == object2){
            return true;
        }
        // object 是空
        if (null == object){
            // 标识null和""相比的情况
            if (nullTypeFlag){
                if ("".equals(trim(object2))){
                    return true;
                }
            }
        }else{
            // 标识null和""相比的情况
            if ("".equals(trim(object))){
                if (null == object2){
                    if (nullTypeFlag){
                        return true;
                    }
                }else{
                    if ("".equals(trim(object2))){
                        return true;
                    }
                }
            }else{
                return object.equals(object2);
            }
        }
        return false;
    }

    /**
     * 判断两个值是否相等,允许两个值都为null.
     * 
     * @param object
     *            object
     * @param object2
     *            object2
     * @return 判断两个值是否相等 标识null和""相比的情况,默认值为false 标识不相等
     * @see java.util.Objects#equals(Object, Object)
     * @see org.apache.commons.lang3.ObjectUtils#equals(Object, Object)
     */
    public static final Boolean equals(Object object,Object object2){
        //TODO Either override Object.equals(Object), or totally rename the method to prevent any confusion.
        //Methods named "equals" should override Object.equals(Object)
        return equals(object, object2, false);
    }

    //****************************************************************************************

    /**
     * 判断对象是不是boolean类型数据.
     * 
     * @param object
     *            对象
     * @return 是返回true
     */
    public static final Boolean isBoolean(Object object){
        return object instanceof Boolean;
    }

    /**
     * 判断对象是不是Integer类型.
     * 
     * @param object
     *            对象
     * @return 是返回true
     */
    public static final Boolean isInteger(Object object){
        return object instanceof Integer;
    }

    /**
     * object 类型转换成boolean类型.
     * 
     * @param object
     *            object
     * @return boolean
     */
    public static final Boolean toBoolean(Object object){
        if (null == object){
            throw new IllegalArgumentException("object can't be null/empty!");
        }
        return Boolean.parseBoolean(object.toString());
    }

    /**
     * object转成integer类型.
     *
     * @param value
     *            值
     * @return <ul>
     *         <li>如果value是null,则返回null</li>
     *         <li>如果value本身是Integer类型,则强制转换成 (Integer) value</li>
     *         <li>否则 new Integer(value.toString().trim())</li>
     *         <li>如果value不能转成integer 会抛出 IllegalArgumentException异常</li>
     *         </ul>
     * @throws IllegalArgumentException
     *             如果 参数不能转成 Integer
     */
    public static final Integer toInteger(Object value) throws IllegalArgumentException{
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Integer){
            return (Integer) value;
        }

        try{
            return new Integer(trim(value));
        }catch (Exception e){
            throw new IllegalArgumentException("Input param:[\"" + value + "\"], convert to integer exception", e);
        }
    }

    /**
     * object类型转换成BigDecimal.
     * 
     * <h3>注意:对于 double 转成 BigDecimal:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * <span style="color:red">推荐使用 BigDecimal.valueOf</span>，不建议使用new BigDecimal(double)，参见 JDK API
     * new BigDecimal(0.1) ====&gt;   0.1000000000000000055511151231257827021181583404541015625
     * BigDecimal.valueOf(0.1) ====&gt;  0.1
     * </pre>
     * 
     * </blockquote>
     * 
     * @param value
     *            值
     * @return BigDecimal
     */
    public static final BigDecimal toBigDecimal(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof BigDecimal){
            return (BigDecimal) value;
        }

        //对于 double 转成 BigDecimal，推荐使用 BigDecimal.valueOf，不建议使用new BigDecimal(double)，参见 JDK API
        //new BigDecimal(0.1) ====>   0.1000000000000000055511151231257827021181583404541015625
        //BigDecimal.valueOf(0.1) ====>  0.1

        //先转成string 就可以了
        return new BigDecimal(trim(value));
    }

    /**
     * 把对象转换为long类型.
     * 
     * @param value
     *            包含数字的对象.
     * @return long 转换后的数值,对不能转换的对象返回null.
     */
    public static final Long toLong(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Long){
            return (Long) value;
        }
        return Long.parseLong(value.toString());
    }

    /**
     * Object to double.
     * 
     * @param value
     *            the value
     * @return the double
     */
    public static final Double toDouble(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Double){
            return (Double) value;
        }
        return new Double(value.toString());
    }

    /**
     * object to float.
     * 
     * @param value
     *            the value
     * @return the float
     */
    public static final Float toFloat(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Float){
            return (Float) value;
        }
        return new Float(value.toString());
    }

    /**
     * object to short.
     * 
     * @param value
     *            the value
     * @return the short
     */
    public static final Short toShort(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Short){
            return (Short) value;
        }
        return new Short(value.toString());
    }

    /**
     * 把对象转换成字符串.
     * 
     * @param value
     *            参数值
     * @return <ul>
     *         <li>{@code (null == value) =====>return null}</li>
     *         <li>{@code (value instanceof String) =====>return (String) value}</li>
     *         <li>{@code return value.toString()}</li>
     *         <li>对于数组，将会调用 {@link java.util.Arrays#toString(Object[])}</li>
     *         </ul>
     * @since 1.0
     */
    public static final String toString(Object value){
        if (null == value){
            return null;
        }
        if (value instanceof String){
            return (String) value;
        }
        if (value instanceof Object[]){
            return Arrays.toString((Object[]) value);
        }

        //***************************************************************
        // primitive ints
        if (value instanceof int[]){
            return Arrays.toString((int[]) value);
        }

        // primitive long
        if (value instanceof long[]){
            return Arrays.toString((long[]) value);
        }

        // primitive float
        if (value instanceof float[]){
            return Arrays.toString((float[]) value);
        }

        // primitive double
        if (value instanceof double[]){
            return Arrays.toString((double[]) value);
        }

        // primitive char
        if (value instanceof char[]){
            return Arrays.toString((char[]) value);
        }

        // primitive boolean
        if (value instanceof boolean[]){
            return Arrays.toString((boolean[]) value);
        }

        // primitive byte
        if (value instanceof byte[]){
            return Arrays.toString((byte[]) value);
        }

        // primitive short
        if (value instanceof short[]){
            return Arrays.toString((short[]) value);
        }
        return value.toString();
    }

    /**
     * object to T.
     * 
     * @param <T>
     *            the generic type
     * @param value
     *            the value
     * @param klass
     *            the class1
     * @return if null==value return null,else to class convert<br>
     *         如果不是内置的class,将使用强制转换 (T) value
     */
    @SuppressWarnings("unchecked")
    // XXX
    public static final <T> T toT(Object value,Class<?> klass){
        if (null == value){
            return null;
        }
        if (klass == String.class){
            return (T) toString(value);
        }else if (klass == Boolean.class){
            return (T) toBoolean(value);
        }else if (klass == Integer.class){
            return (T) toInteger(value);
        }else if (klass == BigDecimal.class){
            return (T) toBigDecimal(value);
        }else if (klass == Long.class){
            return (T) toLong(value);
        }else if (klass == Double.class){
            return (T) toDouble(value);
        }else if (klass == Float.class){
            return (T) toFloat(value);
        }else if (klass == Short.class){
            return (T) toShort(value);
        }
        return (T) value;
    }

    /**
     * 去除空格.
     * 
     * <pre>
     * trim(null) --------&gt; &quot;&quot;
     * trim(&quot;null&quot;) --------&gt; &quot;null&quot;
     * 
     * </pre>
     * 
     * @param obj
     *            obj
     * @return 去除空格
     */
    public static final String trim(Object obj){
        return obj == null ? "" : obj.toString().trim();
    }
}
