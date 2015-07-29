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

import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.feilong.core.util.Validator;

/**
 * 内省 {@link java.beans.Introspector}工具类.
 *
 * @author feilong
 * @version 1.0.9 2015年3月24日 下午1:21:31
 * 
 * @see java.beans.BeanInfo
 * @see java.beans.Introspector
 * @see java.beans.MethodDescriptor
 * @see java.beans.PropertyDescriptor
 * @see java.beans.EventSetDescriptor
 * @since 1.0.9
 */
public final class IntrospectorUtil{

    /** Don't let anyone instantiate this class. */
    private IntrospectorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得一个字符串并将它转换成普通 Java 变量名称大写形式的实用工具方法.
     * <p>
     * 这通常意味着将首字符从大写转换成小写，<b>但在（不平常的）特殊情况下，当有多个字符且第一个和第二个字符都是大写字符时，不执行任何操作。</b><br>
     * 因此 "FooBah" 变成 "fooBah"，"X" 变成 "x"，但 "URL" 仍然是 "URL"。<br>
     * </p>
     *
     * @param name
     *            要取消大写化的字符串
     * @return {@link java.beans.Introspector#decapitalize(String)}
     * @since 1.0.9
     */
    public static String decapitalize(String name){
        return Introspector.decapitalize(name);
    }

    /**
     * 获得 map for LOGGER.
     *
     * @param beanClass
     *            the bean class
     * @return the map for log
     * @throws IntrospectionException
     *             the introspection exception
     * @see java.beans.Introspector#getBeanInfo(Class, Class)
     * @see java.beans.BeanInfo
     * @since 1.0.9
     */
    public static Map<String, Object> getIntrospectorInfoMapForLog(Class<?> beanClass) throws IntrospectionException{
        //依据Bean产生一个相关的BeanInfo类   
        Class<?> stopClass = beanClass.getSuperclass();
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, stopClass);

        if (null == beanInfo){
            return Collections.emptyMap();
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        //内省成员属性
        List<String> propertyDescriptorList = getPropertyDescriptorListForLog(beanInfo);
        map.put("beanInfo propertyDescriptor", propertyDescriptorList);

        //内省成员方法
        List<String> methodDescriptorList = getMethodDescriptorForLog(beanInfo);
        map.put("beanInfo methodDescriptor", methodDescriptorList);

        //内省绑定事件
        List<String> eventSetDescriptorList = getEventSetDescriptorListForLog(beanInfo);
        map.put("beanInfo eventSetDescriptor", eventSetDescriptorList);

        map.put("beanInfo.getDefaultEventIndex()", beanInfo.getDefaultEventIndex());
        map.put("beanInfo.getDefaultPropertyIndex()", beanInfo.getDefaultPropertyIndex());
        return map;
    }

    /**
     * 获得 {@link java.beans.BeanInfo#getPropertyDescriptors()}for LOGGER.
     *
     * @param beanInfo
     *            the bean info
     * @return the list< string>
     * @since 1.0.9
     */
    private static List<String> getPropertyDescriptorListForLog(BeanInfo beanInfo){
        //用于获取该Bean中的所有允许公开的成员属性，以PropertyDescriptor数组的形式返回
        PropertyDescriptor[] propertyDescriptorArray = beanInfo.getPropertyDescriptors();
        if (Validator.isNullOrEmpty(propertyDescriptorArray)){
            return Collections.emptyList();
        }

        List<String> propertyDescriptorList = new ArrayList<String>(propertyDescriptorArray.length);

        //PropertyDescriptor类 用于描述一个成员属性
        for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray){
            //获得该属性的名字
            String propertyDescriptorName = propertyDescriptor.getName();

            // 获得该属性的数据类型，以Class的形式给出
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            propertyDescriptorList.add(propertyType.getName() + " " + propertyDescriptorName);
        }

        Collections.sort(propertyDescriptorList);
        return propertyDescriptorList;
    }

    /**
     * 获得 {@link java.beans.BeanInfo#getMethodDescriptors()} for LOGGER.
     * 
     * @param beanInfo
     *            the bean info
     * @return the list< string>
     * @since 1.0.9
     */
    private static List<String> getMethodDescriptorForLog(BeanInfo beanInfo){
        //用于获取该Bean中的所有允许公开的成员方法，以MethodDescriptor数组的形式返回
        MethodDescriptor[] methodDescriptorArray = beanInfo.getMethodDescriptors();
        if (Validator.isNullOrEmpty(methodDescriptorArray)){
            return Collections.emptyList();
        }

        List<String> methodDescriptorList = new ArrayList<String>(methodDescriptorArray.length);
        //MethodDescriptor类 用于记载一个成员方法的所有信息
        for (MethodDescriptor methodDescriptor : methodDescriptorArray){

            //获得一个成员方法描述器所代表的方法的名字   
            String methodName = methodDescriptor.getName();

            //获得该方法对象   
            Method method = methodDescriptor.getMethod();

            //通过方法对象获得该方法的所有参数，以Class数组的形式返回   
            Class<?>[] parameterTypes = method.getParameterTypes();

            if (Validator.isNotNullOrEmpty(parameterTypes)){
                StringBuilder sb = new StringBuilder();
                for (int i = 0, j = parameterTypes.length; i < j; ++i){
                    //获得参数的类型的名字   
                    Class<?> parameterType = parameterTypes[i];

                    String methodParams = parameterType.getName();
                    sb.append(methodParams);

                    // 不是最后一个 拼接
                    if (i != j - 1){
                        sb.append(",");
                    }
                }
                methodDescriptorList.add(methodName + "(" + sb.toString() + ")");
            }else{
                methodDescriptorList.add(methodName + "()");
            }
        }

        Collections.sort(methodDescriptorList);
        return methodDescriptorList;
    }

    /**
     * 获得 {@link java.beans.BeanInfo#getEventSetDescriptors()} for LOGGER.
     *
     * @param beanInfo
     *            the bean info
     * @return the list< string>
     * @since 1.0.9
     */
    private static List<String> getEventSetDescriptorListForLog(BeanInfo beanInfo){
        //用于获取该Bean中的所有允许公开的成员事件，以EventSetDescriptor数组的形式返回
        EventSetDescriptor[] eventSetDescriptorArray = beanInfo.getEventSetDescriptors();
        if (Validator.isNullOrEmpty(eventSetDescriptorArray)){
            return Collections.emptyList();
        }

        List<String> eventSetDescriptorList = new ArrayList<String>(eventSetDescriptorArray.length);

        //eventSetDescriptor 用于描述一个成员事件
        for (EventSetDescriptor eventSetDescriptor : eventSetDescriptorArray){
            //获得该事件的名字
            String eventName = eventSetDescriptor.getName();
            //获得该事件所依赖的事件监听器，以Class的形式给出
            Class<?> listenerType = eventSetDescriptor.getListenerType();
            eventSetDescriptorList.add(eventName + "(" + listenerType.getName() + ")");
        }

        Collections.sort(eventSetDescriptorList);
        return eventSetDescriptorList;
    }
}
