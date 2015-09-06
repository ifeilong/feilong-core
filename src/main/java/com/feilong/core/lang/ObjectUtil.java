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

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.util.Validator;

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
     * 从指定的 <code>Object obj</code>中,查找指定类型的值.
     * 
     * <h3>代码流程:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>{@code if ClassUtil.isInstance(findValue, toBeFindedClassType) 直接返回 findValue}</li>
     * <li>自动过滤{@code isPrimitiveOrWrapper},{@code CharSequence},{@code Collection},{@code Map}类型</li>
     * <li>调用 {@link PropertyUtil#describe(Object)} 再递归查找</li>
     * </ol>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param obj
     *            要被查找的对象
     * @param toBeFindedClassType
     *            the to be finded class type
     * @return a value of the given type found if there is a clear match,or {@code null} if none such value found
     * @see "org.springframework.util.CollectionUtils#findValueOfType(Collection, Class)"
     * @since 1.4.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T findValueOfType(Object obj,Class<T> toBeFindedClassType){
        if (Validator.isNullOrEmpty(obj)){
            throw new NullPointerException("findValue can't be null/empty!");
        }

        if (null == toBeFindedClassType){
            throw new NullPointerException("toBeFindedClassType can't be null/empty!");
        }

        if (ClassUtil.isInstance(obj, toBeFindedClassType)){
            return (T) obj;
        }

        //一般自定义的command 里面 就是些 string int,list map等对象
        //这些我们过滤掉,只取类型是 findedClassType的
        boolean canFindType = !ClassUtils.isPrimitiveOrWrapper(toBeFindedClassType)//
                        && !ClassUtil.isInstance(obj, CharSequence.class)//
                        && !ClassUtil.isInstance(obj, Collection.class) //
                        && !ClassUtil.isInstance(obj, Map.class) //
        ;

        if (!canFindType){
            return null;
        }

        //******************************************************************************
        Map<String, Object> describe = PropertyUtil.describe(obj);

        for (Map.Entry<String, Object> entry : describe.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if (null != value && !"class".equals(key)){
                //级联查询
                T t = findValueOfType(value, toBeFindedClassType);
                if (null != t){
                    return t;
                }
            }
        }
        return null;
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
}
