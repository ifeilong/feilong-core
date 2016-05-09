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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.BeanUtilException;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.tools.jsonlib.JsonUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * {@link java.lang.Enum} 工具类.
 * 
 * @author feilong
 * @version 1.0.6 2014年5月8日 上午3:30:51
 * @version 1.0.8 2014-7-22 13:43 add {@link EnumUtil#getEnumByPropertyValueIgnoreCase(Class, String, Object)}
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
     * 通过propertyName的 value(忽视大小写) 获得枚举(equalsIgnoreCase判断).
     * 
     * <p>
     * 适用于这种{@link com.feilong.core.HttpMethodType}带自定义属性的枚举类型,调用方式:
     * 
     * <pre class="code">
     * EnumUtil.getEnumByField(HttpMethodType.class, "method", "get")
     * </pre>
     * 
     * </p>
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            the enum class 比如 {@link com.feilong.core.HttpMethodType}
     * @param propertyName
     *            字段名称,比如 {@link com.feilong.core.HttpMethodType}的method,按照javabean 规范
     * @param specifiedValue
     *            属性值 比如post
     * @return 获得 enum constant
     * @see #getEnumByPropertyValue(Class, String, Object, boolean)
     */
    public static <E extends Enum<?>, T> E getEnumByPropertyValueIgnoreCase(Class<E> enumClass,String propertyName,T specifiedValue){
        boolean ignoreCase = true;
        return getEnumByPropertyValue(enumClass, propertyName, specifiedValue, ignoreCase);
    }

    /**
     * 通过propertyName的 value 获得枚举(equals判断,区分大小写).
     * 
     * <p>
     * 适用于这种{@link com.feilong.core.HttpMethodType}带自定义属性的枚举类型,调用方式:
     * 
     * <pre class="code">
     * EnumUtil.getEnumByField(HttpMethodType.class, "method", "get")
     * </pre>
     * 
     * </p>
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            the enum class 比如 {@link com.feilong.core.HttpMethodType}
     * @param propertyName
     *            字段名称,比如 {@link com.feilong.core.HttpMethodType}的method,按照javabean 规范
     * @param specifiedValue
     *            属性值 比如post
     * @return 获得 enum constant
     * @since 1.0.8
     */
    public static <E extends Enum<?>, T> E getEnumByPropertyValue(Class<E> enumClass,String propertyName,T specifiedValue){
        boolean ignoreCase = false;
        return getEnumByPropertyValue(enumClass, propertyName, specifiedValue, ignoreCase);
    }

    /**
     * 通过propertyName的 value 获得枚举.
     * 
     * <p>
     * 适用于这种{@link com.feilong.core.HttpMethodType} 带自定义属性的枚举类型,调用方式:
     * 
     * <pre class="code">
     * EnumUtil.getEnumByField(HttpMethodType.class, "method", "get")
     * </pre>
     * </p>
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            the enum class 比如 {@link com.feilong.core.HttpMethodType}
     * @param propertyName
     *            字段名称,比如 {@link com.feilong.core.HttpMethodType}的method,按照javabean 规范
     * @param specifiedValue
     *            属性值 比如post
     * @param ignoreCase
     *            是否忽视大小写
     * @return 获得 enum constant
     * @see com.feilong.core.bean.BeanUtil#getProperty(Object, String)
     * @since 1.0.8
     */
    private static <E extends Enum<?>, T> E getEnumByPropertyValue(
                    Class<E> enumClass,
                    String propertyName,
                    T specifiedValue,
                    boolean ignoreCase){

        Validate.notNull(enumClass, "enumClass can't be null!");
        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        // An enum is a kind of class
        // An annotation is a kind of interface
        // 如果此 Class 对象不表示枚举类型,则返回枚举类的元素或 null.
        E[] enumConstants = enumClass.getEnumConstants();

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("enumClass:[{}],enumConstants:{}", enumClass.getCanonicalName(), JsonUtil.format(enumConstants));
        }
        for (E e : enumConstants){
            Object propertyValue = PropertyUtil.getProperty(e, propertyName);
            if (isEquals(propertyValue, specifiedValue, ignoreCase)){
                return e;
            }
        }
        String messagePattern = "can not found the enum constants,enumClass:[{}],propertyName:[{}],value:[{}],ignoreCase:[{}]";
        throw new BeanUtilException(Slf4jUtil.formatMessage(messagePattern, enumClass, propertyName, specifiedValue, ignoreCase));
    }

    /**
     * Checks if is equals.
     *
     * @param <T>
     *            the generic type
     * @param propertyValue
     *            the property value
     * @param specifiedValue
     *            指定的值
     * @param ignoreCase
     *            the ignore case
     * @return true, if checked
     * @since 1.4.0
     */
    private static <T> boolean isEquals(Object propertyValue,T specifiedValue,boolean ignoreCase){
        if (propertyValue == null || specifiedValue == null){//很好的设计 赞一个 
            return propertyValue == specifiedValue;
        }else if (propertyValue == specifiedValue){
            return true;
        }
        //到这里  propertyValue 和 specifiedValue, 肯定 不是null了 
        String propertyValueString = propertyValue.toString();
        String specifiedValueString = specifiedValue.toString();
        return ignoreCase ? StringUtils.equalsIgnoreCase(propertyValueString, specifiedValueString)
                        : StringUtils.equals(propertyValueString, specifiedValueString);
    }
}
