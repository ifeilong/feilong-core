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

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.iterators.EnumerationIterator;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.io.SerializableUtil;
import com.feilong.core.util.ArrayUtil;

/**
 * object工具类.
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
     * Find type value.
     *
     * @param <T>
     *            the generic type
     * @param findValue
     *            the find value
     * @param findedClassType
     *            the finded class type
     * @return the t
     * @since 1.3.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T findTypeValue(Object findValue,Class<T> findedClassType){
        if (ClassUtil.isInstance(findValue, findedClassType)){
            return (T) findValue;
        }else{
            //一般自定义的command 里面 就是些 string int,list map等对象
            //这些我们过滤掉,只取 类型是 findedClassType的
            if (!ClassUtils.isPrimitiveOrWrapper(findedClassType)//
                            && !ClassUtil.isInstance(findValue, CharSequence.class)//
                            && !ClassUtil.isInstance(findValue, Collection.class) //
                            && !ClassUtil.isInstance(findValue, Map.class) //
            ){
                Map<String, Object> describe = PropertyUtil.describe(findValue);

                for (Map.Entry<String, Object> entry : describe.entrySet()){
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    if (null != value && !"class".equals(key)){
                        //级联查询
                        T t = findTypeValue(value, findedClassType);
                        if (null != t){
                            return t;
                        }
                    }
                }
            }
        }
        return null;
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
     * @see ByteArrayOutputStream#size()
     * @see com.feilong.core.io.SerializableUtil#size(Serializable)
     * @since 1.0.7
     */
    public static int size(Serializable serializable){
        return SerializableUtil.size(serializable);
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
    public static <T> Iterator<T> toIterator(Object object){
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
            return new EnumerationIterator<T>(enumeration);
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

    /**
     * <p>
     * Compares two objects for equality, where either one or both objects may be {@code null}.
     * </p>
     *
     * <pre>
     * ObjectUtils.equals(null, null)                  = true
     * ObjectUtils.equals(null, "")                    = false
     * ObjectUtils.equals("", null)                    = false
     * ObjectUtils.equals("", "")                      = true
     * ObjectUtils.equals(Boolean.TRUE, null)          = false
     * ObjectUtils.equals(Boolean.TRUE, "true")        = false
     * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = true
     * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     *
     * @param object1
     *            the first object, may be {@code null}
     * @param object2
     *            the second object, may be {@code null}
     * @return {@code true} if the values of both objects are the same
     * @see "java.util.Objects#equals(Object, Object)"
     * @see org.apache.commons.lang3.ObjectUtils#equals(Object, Object)
     */
    public static Boolean equals(Object object1,Object object2){
        return ObjectUtils.equals(object1, object2);
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
     * 去除空格.
     * 
     * <pre>
     * trim(null) --------&gt; &quot;&quot;
     * trim(&quot;null&quot;) --------&gt; &quot;null&quot;
     * </pre>
     * 
     * @param obj
     *            obj
     * @return 去除空格
     * @see org.apache.commons.lang3.StringUtils#trim(String)
     */
    public static String trim(Object obj){
        return obj == null ? "" : obj.toString().trim();
    }
}
