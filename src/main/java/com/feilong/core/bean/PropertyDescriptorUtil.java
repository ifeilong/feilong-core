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

import static com.feilong.core.bean.SpringBeanUtilsHelper.getSpringBeanUtilsClass;
import static com.feilong.core.bean.SpringBeanUtilsHelper.hasSpringBeanUtilsClass;
import static com.feilong.core.lang.reflect.MethodUtil.invokeStaticMethod;
import static com.feilong.core.util.MapUtil.newConcurrentHashMap;

import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * The Class PropertyDescriptorUtil.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.12.0
 */
public class PropertyDescriptorUtil{

    /** Don't let anyone instantiate this class. */
    private PropertyDescriptorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /** <code>{@value}</code>. */
    private static final String              TYPE_SPRING            = "spring";

    /** The Constant TYPE_COMMONS_BEANUTILS. */
    private static final String              TYPE_COMMONS_BEANUTILS = "commons.beanutils";

    //---------------------------------------------------------------

    /** key 和对应的操作方式. */
    private static final Map<String, String> KEY_AND_TYPE_MAP       = newConcurrentHashMap(200);

    //---------------------------------------------------------------

    /**
     * 是否用 spring 来操作.
     *
     * @param klass
     *            the klass
     * @param propertyName
     *            the property name
     * @return 如果解析出来的类型是 {@link #TYPE_SPRING}, 表示可以使用spring来解析
     */
    static boolean isUseSpringOperate(Class<?> klass,String propertyName){
        String type = getType(klass, propertyName);
        return TYPE_SPRING.equals(type);
    }

    //---------------------------------------------------------------

    /**
     * Builds the type.
     *
     * @param klass
     *            the klass
     * @param propertyName
     *            the property name
     * @return the string
     */
    private static String getType(Class<?> klass,String propertyName){
        String key = buildKey(klass, propertyName);

        //---------------------------------------------------------------
        if (KEY_AND_TYPE_MAP.containsKey(key)){
            return KEY_AND_TYPE_MAP.get(key);
        }

        String type = buildType(klass, propertyName);

        //---------------------------------------------------------------
        KEY_AND_TYPE_MAP.put(key, type);
        return type;
    }

    //---------------------------------------------------------------

    /**
     * Builds the type.
     *
     * @param klass
     *            the klass
     * @param propertyName
     *            the property name
     * @return the string
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/760">PropertyUtil.getProperty(Object, String) 排序异常 #760</a>
     * @since 1.12.1
     */
    private static String buildType(Class<?> klass,String propertyName){
        try{
            PropertyDescriptor springPropertyDescriptor = getSpringPropertyDescriptor(klass, propertyName);
            if (null != springPropertyDescriptor){
                return TYPE_SPRING;
            }
        }catch (Exception e){
            // nothing to do 
        }
        return TYPE_COMMONS_BEANUTILS;
    }

    //---------------------------------------------------------------

    /**
     * Builds the key.
     *
     * @param klass
     *            the klass
     * @param propertyName
     *            the property name
     * @return the string
     */
    private static String buildKey(Class<? extends Object> klass,String propertyName){
        return klass.getName() + "@@" + propertyName;
    }

    //---------------------------------------------------------------

    /**
     * 获得 spring 的 PropertyDescriptor.
     *
     * @param klass
     *            the klass
     * @param propertyName
     *            the property name
     * @return the corresponding PropertyDescriptor, or {@code null} if none
     */
    static PropertyDescriptor getSpringPropertyDescriptor(Class<?> klass,String propertyName){
        if (hasSpringBeanUtilsClass()){
            return invokeStaticMethod(getSpringBeanUtilsClass(), "getPropertyDescriptor", klass, propertyName);
        }
        return null;
    }
}
