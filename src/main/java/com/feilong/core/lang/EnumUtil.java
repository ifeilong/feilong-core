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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.BeanUtilException;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.net.HttpMethodType;
import com.feilong.core.tools.json.JsonUtil;
import com.feilong.core.util.Validator;

/**
 * {@link java.lang.Enum} 工具类.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.6 2014年5月8日 上午3:30:51
 * @version 1.0.8 2014-7-22 13:43 add {@link EnumUtil#getEnumByPropertyValueIgnoreCase(Class, String, Object)}
 * @see org.apache.commons.lang.enums.EnumUtils
 * @see org.apache.commons.lang3.EnumUtils
 * @since 1.0.6
 */
public final class EnumUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnumUtil.class);

    /** Don't let anyone instantiate this class. */
    private EnumUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 通过fieldName的 value(忽视大小写) 获得枚举(equalsIgnoreCase判断)<br>
     * 
     * <pre>
     * 
     * 适用于这种{@link HttpMethodType} 待自定义属性的枚举类型,调用方式:
     * 
     * {@code
     * 	EnumUtil.getEnumByField(HttpMethodType.class, "method", "get")
     * }
     * </pre>
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            the enum class 比如 {@link HttpMethodType}
     * @param propertyName
     *            字段名称,比如 {@link HttpMethodType}的method,按照javabean 规范
     * @param value
     *            属性值 比如post
     * @return 获得 enum constant
     * @throws IllegalArgumentException
     *             if Validator.isNullOrEmpty(enumClass) or Validator.isNullOrEmpty(propertyName)
     * @throws NoSuchFieldException
     *             找不到匹配的枚举
     * @throws BeanUtilException
     *             the bean util exception
     */
    public static <E extends Enum<?>, T> E getEnumByPropertyValueIgnoreCase(Class<E> enumClass,String propertyName,T value)
                    throws IllegalArgumentException,NoSuchFieldException,BeanUtilException{
        boolean ignoreCase = true;
        return getEnumByPropertyValue(enumClass, propertyName, value, ignoreCase);
    }

    /**
     * 通过fieldName的 value 获得枚举(equals判断)<br>
     * 
     * <pre>
     * 
     * 适用于这种{@link HttpMethodType} 待自定义属性的枚举类型,调用方式:
     * 
     * {@code
     * 	EnumUtil.getEnumByField(HttpMethodType.class, "method", "get")
     * }
     * </pre>
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            the enum class 比如 {@link HttpMethodType}
     * @param propertyName
     *            字段名称,比如 {@link HttpMethodType}的method,按照javabean 规范
     * @param value
     *            属性值 比如post
     * @return 获得 enum constant
     * @throws IllegalArgumentException
     *             if Validator.isNullOrEmpty(enumClass) or Validator.isNullOrEmpty(propertyName)
     * @throws NoSuchFieldException
     *             找不到匹配的枚举
     * @throws BeanUtilException
     *             the bean util exception
     * @since 1.0.8
     */
    public static <E extends Enum<?>, T> E getEnumByPropertyValue(Class<E> enumClass,String propertyName,T value)
                    throws IllegalArgumentException,NoSuchFieldException,BeanUtilException{
        boolean ignoreCase = false;
        return getEnumByPropertyValue(enumClass, propertyName, value, ignoreCase);
    }

    /**
     * 通过fieldName的 value 获得枚举<br>
     * 
     * <pre>
     * 
     * 适用于这种{@link HttpMethodType} 待自定义属性的枚举类型,调用方式:
     * 
     * {@code
     * 	EnumUtil.getEnumByField(HttpMethodType.class, "method", "get")
     * }
     * </pre>
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            the enum class 比如 {@link HttpMethodType}
     * @param propertyName
     *            字段名称,比如 {@link HttpMethodType}的method,按照javabean 规范
     * @param value
     *            属性值 比如post
     * @param ignoreCase
     *            是否忽视大小写
     * @return 获得 enum constant
     * @throws IllegalArgumentException
     *             if Validator.isNullOrEmpty(enumClass) or Validator.isNullOrEmpty(propertyName)
     * @throws NoSuchFieldException
     *             找不到匹配的枚举
     * @throws BeanUtilException
     *             the bean util exception
     * @see com.feilong.core.bean.BeanUtil#getProperty(Object, String)
     * @since 1.0.8
     */
    private static <E extends Enum<?>, T> E getEnumByPropertyValue(Class<E> enumClass,String propertyName,T value,boolean ignoreCase)
                    throws IllegalArgumentException,NoSuchFieldException,BeanUtilException{

        if (Validator.isNullOrEmpty(enumClass)){
            throw new IllegalArgumentException("enumClass is null or empty!");
        }

        if (Validator.isNullOrEmpty(propertyName)){
            throw new IllegalArgumentException("the fieldName is null or empty!");
        }

        // An enum is a kind of class
        // and an annotation is a kind of interface
        // 如果此 Class 对象不表示枚举类型，则返回枚举类的元素或 null.
        E[] enumConstants = enumClass.getEnumConstants();

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("enumClass:[{}],enumConstants:{}", enumClass.getCanonicalName(), JsonUtil.format(enumConstants));
        }
        for (E e : enumConstants){

            Object propertyValue = PropertyUtil.getProperty(e, propertyName);

            if (null == propertyValue && null == value){
                return e;
            }
            if (null != propertyValue && null != value){
                if (ignoreCase && propertyValue.toString().equalsIgnoreCase(value.toString())){
                    return e;
                }else if (propertyValue.equals(value)){
                    return e;
                }
            }
        }

        throw new NoSuchFieldException("can not found the enum constants,enumClass:[" + enumClass + "],propertyName:[" + propertyName
                        + "],value:[" + value + "],ignoreCase:[" + ignoreCase + "]");
    }
}
