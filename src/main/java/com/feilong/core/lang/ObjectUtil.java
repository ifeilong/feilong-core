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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.feilong.core.bean.PropertyUtil;

/**
 * {@link Object} 工具类.
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
        }
        //一般自定义的command 里面 就是些 string int,list map等对象
        //这些我们过滤掉,只取 类型是 findedClassType的
        boolean canFindType = !ClassUtils.isPrimitiveOrWrapper(findedClassType)//
                        && !ClassUtil.isInstance(findValue, CharSequence.class)//
                        && !ClassUtil.isInstance(findValue, Collection.class) //
                        && !ClassUtil.isInstance(findValue, Map.class) //
        ;

        if (!canFindType){
            return null;
        }

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
        return null;
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
     * 是否是数组.
     *
     * @param object
     *            the object
     * @return true, if checks if is array
     * @since 1.3.0
     */
    public static Boolean isArray(Object object){
        return object.getClass().isArray();
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
