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
package com.feilong.core.lang.reflect;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtil;
import com.feilong.core.tools.slf4j.Slf4jUtil;

/**
 * 使用反射的方式执行调用bean中的方法,原先是自己写的,但是发现未尽全面,因此调用 {@link org.apache.commons.lang3.reflect.MethodUtils}相关方法 .
 * 
 * <h3>方法介绍:</h3>
 * 
 * <blockquote>
 * <p>
 * 这两个是调用常规方法的
 * </p>
 * <ol>
 * <li>{@link #invokeMethod(Object, String, Object...)}</li>
 * <li>{@link #invokeMethod(Object, String, Object[], Class[])}</li>
 * </ol>
 * <br>
 * 注意,底层调用的是 {@link org.apache.commons.lang3.reflect.MethodUtils#invokeMethod(Object, String, Object[], Class[])},这个方法会调用
 * {@link org.apache.commons.lang3.reflect.MethodUtils#getMatchingAccessibleMethod(Class, String, Class...)}获得最佳匹配方法
 * <p>
 * 下面两个是调用静态方法的:
 * </p>
 * <ol>
 * <li>{@link #invokeStaticMethod(Class, String, Object...)}</li>
 * <li>{@link #invokeStaticMethod(Class, String, Object[], Class[])}</li>
 * </ol>
 * 
 * 
 * <p>
 * 如果你要调用精准的方法,可以使用 {@link org.apache.commons.lang3.reflect.MethodUtils}原生方法:
 * </p>
 * <ol>
 * <li>{@link MethodUtils#invokeExactMethod(Object, String)}</li>
 * <li>{@link MethodUtils#invokeExactMethod(Object, String, Object...)}</li>
 * <li>{@link MethodUtils#invokeExactMethod(Object, String, Object[], Class[])}</li>
 * <li>{@link MethodUtils#invokeExactStaticMethod(Class, String, Object...)}</li>
 * <li>{@link MethodUtils#invokeExactStaticMethod(Class, String, Object[], Class[])}</li>
 * </ol>
 * 
 * <p>
 * 当然,你还可以调用 {@link org.apache.commons.lang3.reflect.MethodUtils}其他方法:
 * </p>
 * <ol>
 * <li>{@link MethodUtils#getAccessibleMethod(java.lang.reflect.Method)}</li>
 * <li>{@link MethodUtils#getAccessibleMethod(Class, String, Class...)}</li>
 * <li>{@link MethodUtils#getMatchingAccessibleMethod(Class, String, Class...)}</li>
 * <li>{@link MethodUtils#getMethodsListWithAnnotation(Class, Class)} 获得一个类中,指定泛型的方法(集合形式),比较实用</li>
 * <li>{@link MethodUtils#getMethodsWithAnnotation(Class, Class)} 获得一个类中,指定泛型的方法(数组形式),比较实用</li>
 * <li>{@link MethodUtils#getOverrideHierarchy(java.lang.reflect.Method, org.apache.commons.lang3.ClassUtils.Interfaces)}</li>
 * </ol>
 * </blockquote>
 * 
 * <p>
 * 当然,您也可以调用 java 原生态, 需要注意的是:
 * </p>
 * <ol>
 * <li>{@link java.lang.Class#getMethods()} 获取全部的public的函数(包括从基类继承的、从接口实现的所有public函数) (return all the (public) member methods inherited from
 * the Object class)</li>
 * <li>{@link java.lang.Class#getDeclaredMethods()} 获得类的所有方法 (This includes public, protected, default (package) access, and private
 * methods, but excludes inherited methods)</li>
 * </ol>
 * 
 * @author feilong
 * @version 1.0.7 2014年7月15日 下午1:08:15
 * @see org.apache.commons.lang3.reflect.MethodUtils
 * @see org.apache.commons.lang3.ClassUtils#getPublicMethod(Class, String, Class...)
 * @see java.util.ServiceLoader jdk1.6 ServiceLoader
 * @since 1.0.7
 */
public final class MethodUtil{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodUtil.class);

    /** Don't let anyone instantiate this class. */
    private MethodUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // [start]

    /**
     * 执行某对象的某个方法.
     * 
     * <h3>使用场景:</h3>
     * 
     * <blockquote>
     * <p>
     * 比如 上传下载 service 有很多相同类型的方法,比如 importXX1,importXX2,对于这种,可以使用调用此方法来快速调用方法
     * </p>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param obj
     *            对象
     * @param methodName
     *            方法名
     * @param params
     *            参数
     * @return 方法执行之后的结果值
     * @see java.lang.reflect.Method#invoke(Object, Object...)
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeMethod(Object, String, Object...)
     * @see com.feilong.core.lang.ClassUtil#toClass(Object...)
     */
    public static <T> T invokeMethod(Object obj,String methodName,Object...params){
        final Class<?>[] parameterTypes = ClassUtil.toClass(params);
        return invokeMethod(obj, methodName, params, parameterTypes);
    }

    /**
     * Invoke method.
     *
     * @param <T>
     *            the generic type
     * @param object
     *            the object
     * @param methodName
     *            the method name
     * @param args
     *            the args
     * @param parameterTypes
     *            the parameter types
     * @return the t
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeMethod(Object, String, Object[], Class[])
     * @since 1.1.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(final Object object,final String methodName,Object[] args,Class<?>[] parameterTypes){
        try{
            return (T) org.apache.commons.lang3.reflect.MethodUtils.invokeMethod(object, methodName, args, parameterTypes);
        }catch (Exception e){
            String message = Slf4jUtil.formatMessage(
                            "invokeMethod Exception,object:[{}],methodName:[{}],args:[{}],parameterTypes:[{}]",
                            object,
                            methodName,
                            args,
                            parameterTypes);
            LOGGER.error(message, e);
            throw new ReflectException(message, e);
        }
    }

    /**
     * 执行静态方法.
     *
     * @param <T>
     *            the generic type
     * @param klass
     *            the klass
     * @param methodName
     *            方法名
     * @param params
     *            动态参数
     * @return 方法执行之后的结果值
     * @see java.lang.reflect.Method#invoke(Object, Object...)
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeStaticMethod(Class, String, Object...)
     */
    public static <T> T invokeStaticMethod(Class<?> klass,String methodName,Object...params){
        final Class<?>[] parameterTypes = ClassUtil.toClass(params);
        return invokeStaticMethod(klass, methodName, params, parameterTypes);
    }

    /**
     * Invoke static method.
     *
     * @param <T>
     *            the generic type
     * @param cls
     *            the cls
     * @param methodName
     *            the method name
     * @param args
     *            the args
     * @param parameterTypes
     *            the parameter types
     * @return the t
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeStaticMethod(Class, String, Object[], Class[])
     * @since 1.1.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeStaticMethod(final Class<?> cls,final String methodName,Object[] args,Class<?>[] parameterTypes){
        try{
            return (T) org.apache.commons.lang3.reflect.MethodUtils.invokeStaticMethod(cls, methodName, args, parameterTypes);
        }catch (Exception e){
            String message = Slf4jUtil.formatMessage(
                            "invoke Static Method Exception,cls:[{}],methodName:[{}],args:[{}],parameterTypes:[{}]",
                            cls.getName(),
                            methodName,
                            args,
                            parameterTypes);
            LOGGER.error(message, e);
            throw new ReflectException(message, e);
        }
    }
}
