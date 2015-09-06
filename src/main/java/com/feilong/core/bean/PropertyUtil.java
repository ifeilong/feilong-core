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
package com.feilong.core.bean;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtil;
import com.feilong.core.util.Validator;

/**
 * 封装了 {@link PropertyUtils}.
 * 
 * <h3>{@link PropertyUtils}与 {@link BeanUtils}:</h3>
 * 
 * <blockquote>
 * <p>
 * {@link PropertyUtils}类和 {@link BeanUtils}类很多的方法在参数上都是相同的,但返回值不同。 <br>
 * BeanUtils着重于"Bean",返回值通常是String,<br>
 * 而PropertyUtils着重于属性,它的返回值通常是Object。 
 * </p>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.8 2014-7-21 17:45:30
 * @see org.apache.commons.beanutils.PropertyUtils
 * @see com.feilong.core.bean.BeanUtil
 */
public final class PropertyUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

    /** Don't let anyone instantiate this class. */
    private PropertyUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 返回一个<code>bean</code>中所有的 <span style="color:green">可读属性</span>,并将属性名/属性值放入一个Map中.
     * 
     * <p>
     * 另外还有一个名为class的属性,属性值是Object的类名,事实上class是java.lang.Object的一个属性
     * </p>
     * 
     * <h3>原理:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>取到bean class的 {@link java.beans.PropertyDescriptor}数组</li>
     * <li>循环,找到 {@link java.beans.PropertyDescriptor#getReadMethod()}</li>
     * <li>将 name and {@link org.apache.commons.beanutils.PropertyUtilsBean#getProperty(Object, String)} 设置到map中</li>
     * </ol>
     * </blockquote>
     *
     * @param bean
     *            Bean whose properties are to be extracted
     * @return The set of properties for the bean
     * @see org.apache.commons.beanutils.BeanUtils#describe(Object)
     * @see org.apache.commons.beanutils.PropertyUtils#describe(Object)
     * @see com.feilong.core.bean.BeanUtil#describe(Object)
     */
    public static Map<String, Object> describe(Object bean){
        try{
            //Return the entire set of properties for which the specified bean provides a read method.
            return PropertyUtils.describe(bean);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    /**
     * 使用 {@link PropertyUtils#setProperty(Object, String, Object)} 来设置属性值(<b>不会进行类型转换</b>).
     *
     * @param bean
     *            Bean whose property is to be modified
     * @param propertyName
     *            Possibly indexed and/or nested name of the property to be modified
     * @param value
     *            Value to which this property is to be set
     * @see org.apache.commons.beanutils.BeanUtils#setProperty(Object, String, Object)
     * @see org.apache.commons.beanutils.PropertyUtils#setProperty(Object, String, Object)
     * @see com.feilong.core.bean.BeanUtil#setProperty(Object, String, Object)
     */
    public static void setProperty(Object bean,String propertyName,Object value){
        try{
            //Set the value of the specified property of the specified bean, no matter which property reference format is used, with no type conversions.
            // PropertyUtils的功能类似于BeanUtils,但在底层不会对传递的数据做转换处理
            PropertyUtils.setProperty(bean, propertyName, value);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [start] getProperty

    /**
     * 使用 {@link PropertyUtils#getProperty(Object, String)} 类从对象中取得属性值.
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            Bean whose property is to be extracted
     * @param propertyName
     *            Possibly indexed and/or nested name of the property to be extracted
     * @return 使用{@link PropertyUtils#getProperty(Object, String)} 从对象中取得属性值
     * @see com.feilong.core.bean.BeanUtil#getProperty(Object, String)
     * @see org.apache.commons.beanutils.BeanUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtilsBean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object bean,String propertyName){
        //Return the value of the specified property of the specified bean, no matter which property reference format is used, with no type conversions.
        //For more details see PropertyUtilsBean.
        try{
            return (T) PropertyUtils.getProperty(bean, propertyName);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [end]

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
        Map<String, Object> describe = describe(obj);

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
}