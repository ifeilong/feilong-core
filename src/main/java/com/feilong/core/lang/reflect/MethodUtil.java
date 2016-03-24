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
 * 使用反射的方式执行调用bean中的方法.
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
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">方法</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link Class#getMethods()}</td>
 * <td>
 * 
 * <p>
 * 返回一个包含某些 Method 对象的数组,这些对象反映此 Class 对象所表示的类或接口(包括那些由该类或接口声明的以及从超类和超接口继承的那些的类或接口)的<span style="color:green">公共(public)
 * member</span>方法。(return all the (public) member methods inherited from the Object class)
 * </p>
 * 
 * <p>
 * 数组类返回从 Object 类继承的所有(公共)member 方法。
 * </p>
 * 
 * <b>返回数组中的元素没有排序,也没有任何特定的顺序。</b><br>
 * 
 * 如果类声明了带有相同参数类型的多个公共成员方法,则它们都会包含在返回的数组中。<br>
 * 如果此 Class 对象表示没有公共成员方法的类或接口,或者表示一个基本类型或 void,则此方法返回长度为 0 的数组。 <br>
 * 类初始化方法<code>&lt;clinit&gt;</code> 不包含在返回的数组中。</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Class#getDeclaredMethods()}</td>
 * <td>
 * 返回 Method 对象的一个数组,
 * <p>
 * 这些对象反映此 Class 对象表示的类或接口声明的所有方法,包括<span style="color:green">公共(public)、保护(protected)、默认(包)访问(default (package)
 * access)和私有方法(private)</span>,但 <span style="color:red">不包括继承(inherited)</span>的方法。
 * </p>
 * 如果该类声明带有相同参数类型的多个公共成员方法,则它们都包含在返回的数组中。
 * <p>
 * <b>返回数组中的元素没有排序,也没有任何特定的顺序。</b>
 * </p>
 * 如果该类或接口不声明任何方法,或者此 Class 对象表示一个基本类型、一个数组类或 void,则此方法返回一个长度为 0 的数组。<br>
 * 类初始化方法<code>&lt;clinit&gt;</code> 包含在返回数组中。<br>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
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
            return (T) MethodUtils.invokeMethod(object, methodName, args, parameterTypes);
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
            return (T) MethodUtils.invokeStaticMethod(cls, methodName, args, parameterTypes);
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
