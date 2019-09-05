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

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * {@link java.lang.Enum} 工具类.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
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

    //---------------------------------------------------------------

    /**
     * 通过<code>propertyName</code>的 <code>specifiedValue</code><b>(忽视大小写)</b> 获得枚举(equalsIgnoreCase判断).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>
     * 
     * <p>
     * 适用于这种 <code>HttpMethodType</code> 带自定义属性的枚举类型,
     * </p>
     * 
     * <pre class="code">
     * public enum HttpMethodType{
     * 
     *     GET("get"),
     * 
     *     POST("post");
     * 
     *     private String method;
     * 
     *     private HttpMethodType(String method){
     *         this.method = method;
     *     }
     * 
     *     public String getMethod(){
     *         return method;
     *     }
     * }
     * </pre>
     * 
     * <p>
     * 要取HttpMethodType 里面的 method属性值是 "get"的枚举(忽视大小写),调用方式:
     * </p>
     * 
     * <pre class="code">
     * EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "get")
     * </pre>
     * 
     * </li>
     * 
     * <li>如果你需要值区分大小写,可以调用 {@link #getEnumByPropertyValue(Class, String, Object)}</li>
     * </ol>
     * </blockquote>
     * 
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            枚举类 比如 <code>HttpMethodType</code>
     * @param propertyName
     *            字段名称,比如 <code>HttpMethodType</code> 的method,按照javabean 规范
     * @param specifiedValue
     *            属性值 比如post
     * @return 如果遍历所有枚举值,找不到枚举值的属性<code>propertyName</code>的值是 <code>specifiedValue</code>,那么返回null
     * @throws NullPointerException
     *             如果 <code>enumClass</code> 是null,或者 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @throws BeanOperationException
     *             如果枚举值没有相关 <code>propertyName</code> 属性,比如 <code>HttpMethodType</code> 有 <b>"method"</b> 属性,但是没有 <b>"method2222"</b> 属性
     * @see #getEnumByPropertyValue(Class, String, Object, boolean)
     */
    public static <E extends Enum<?>, T> E getEnumByPropertyValueIgnoreCase(Class<E> enumClass,String propertyName,T specifiedValue){
        return getEnumByPropertyValue(enumClass, propertyName, specifiedValue, true);
    }

    /**
     * 通过<code>propertyName</code>的 <code>specifiedValue</code> 获得枚举(equals判断,<b>区分大小写</b>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>
     * 
     * <p>
     * 适用于这种 <code>HttpMethodType</code> 带自定义属性的枚举类型,
     * </p>
     * 
     * <pre class="code">
     * public enum HttpMethodType{
     * 
     *     GET("get"),
     * 
     *     POST("post");
     * 
     *     private String method;
     * 
     *     private HttpMethodType(String method){
     *         this.method = method;
     *     }
     * 
     *     public String getMethod(){
     *         return method;
     *     }
     * 
     * }
     * </pre>
     * 
     * <p>
     * 要取HttpMethodType 里面的 method属性值是 "get"的枚举(区分大小写),调用方式:
     * </p>
     * 
     * <pre class="code">
     * EnumUtil.getEnumByPropertyValue(HttpMethodType.class, "method", "get")
     * </pre>
     * 
     * </li>
     * 
     * <li>如果你需要值不区分大小写,可以调用 {@link #getEnumByPropertyValueIgnoreCase(Class, String, Object)}</li>
     * </ol>
     * </blockquote>
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            枚举类 比如 <code>HttpMethodType</code>
     * @param propertyName
     *            字段名称,比如 <code>HttpMethodType</code> 的method,按照javabean 规范
     * @param specifiedValue
     *            属性值 比如post
     * @return 如果遍历所有枚举值,找不到枚举值的属性<code>propertyName</code>的值是 <code>specifiedValue</code>,那么返回null
     * @throws NullPointerException
     *             如果 <code>enumClass</code> 是null,或者 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @throws BeanOperationException
     *             如果枚举值没有相关 <code>propertyName</code> 属性,比如 <code>HttpMethodType</code> 有 <b>"method"</b> 属性,但是没有 <b>"method2222"</b> 属性
     * @since 1.0.8
     */
    public static <E extends Enum<?>, T> E getEnumByPropertyValue(Class<E> enumClass,String propertyName,T specifiedValue){
        return getEnumByPropertyValue(enumClass, propertyName, specifiedValue, false);
    }

    //---------------------------------------------------------------

    /**
     * 通过<code>propertyName</code>的 <code>specifiedValue</code> 获得枚举.
     *
     * @param <E>
     *            the element type
     * @param <T>
     *            the generic type
     * @param enumClass
     *            枚举类 比如 <code>HttpMethodType</code>
     * @param propertyName
     *            字段名称,比如 <code>HttpMethodType</code> 的method,按照javabean 规范
     * @param specifiedValue
     *            属性值 比如post
     * @param ignoreCase
     *            是否忽视大小写
     * @return the enum by property value
     * @throws NullPointerException
     *             如果 <code>enumClass</code> 是null,或者 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @throws BeanOperationException
     *             如果枚举值没有相关 <code>propertyName</code> 属性,比如 <code>HttpMethodType</code> 有 <b>"method"</b> 属性,但是没有 <b>"method2222"</b> 属性
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @since 1.0.8
     */
    private static <E extends Enum<?>, T> E getEnumByPropertyValue(
                    Class<E> enumClass,
                    String propertyName,
                    T specifiedValue,
                    boolean ignoreCase){
        Validate.notNull(enumClass, "enumClass can't be null!");
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        //---------------------------------------------------------------

        // An enum is a kind of class
        // An annotation is a kind of interface

        // 如果Class 对象不表示枚举类型,则返回枚举类的元素或 null.
        E[] enumConstants = enumClass.getEnumConstants();

        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("enumClass:[{}],enumConstants:[{}]", enumClass.getCanonicalName(), enumConstants);
        }

        //---------------------------------------------------------------
        for (E e : enumConstants){
            Object propertyValue = PropertyUtil.getProperty(e, propertyName);
            if (isEquals(propertyValue, specifiedValue, ignoreCase)){
                return e;
            }
        }

        //---------------------------------------------------------------
        if (LOGGER.isDebugEnabled()){
            String messagePattern = "[{}],propertyName:[{}],value:[{}],ignoreCase:[{}],constants not found";
            LOGGER.debug(Slf4jUtil.format(messagePattern, enumClass, propertyName, specifiedValue, ignoreCase));
        }
        return null;
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
