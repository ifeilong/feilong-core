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

import java.lang.reflect.Method;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 使用反射的方式执行调用bean中的方法.
 * 
 * <h3>方法介绍:</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * 这两个是调用常规方法的
 * </p>
 * 
 * <ol>
 * <li>{@link #invokeMethod(Object, String, Object...)}</li>
 * <li>{@link #invokeMethod(Object, String, Object[], Class[])}</li>
 * </ol>
 * 
 * <br>
 * 注意,底层调用的是 {@link org.apache.commons.lang3.reflect.MethodUtils#invokeMethod(Object, String, Object[], Class[]) MethodUtils.invokeMethod}
 * ,这个方法会调用
 * {@link org.apache.commons.lang3.reflect.MethodUtils#getMatchingAccessibleMethod(Class, String, Class...)
 * MethodUtils.getMatchingAccessibleMethod}获得最佳匹配方法
 * 
 * <p>
 * 下面两个是调用静态方法的:
 * </p>
 * 
 * <ol>
 * <li>{@link #invokeStaticMethod(Class, String, Object...)}</li>
 * <li>{@link #invokeStaticMethod(Class, String, Object[], Class[])}</li>
 * </ol>
 * 
 * </blockquote>
 * 
 * <h3>关于 {@link org.apache.commons.lang3.reflect.MethodUtils MethodUtils}:</h3>
 * <blockquote>
 * 
 * <p>
 * 如果你要调用精准的方法,可以使用 {@link org.apache.commons.lang3.reflect.MethodUtils MethodUtils}原生方法:
 * </p>
 * 
 * <ol>
 * <li>{@link MethodUtils#invokeExactMethod(Object, String)}</li>
 * <li>{@link MethodUtils#invokeExactMethod(Object, String, Object...)}</li>
 * <li>{@link MethodUtils#invokeExactMethod(Object, String, Object[], Class[])}</li>
 * <li>{@link MethodUtils#invokeExactStaticMethod(Class, String, Object...)}</li>
 * <li>{@link MethodUtils#invokeExactStaticMethod(Class, String, Object[], Class[])}</li>
 * </ol>
 * 
 * <p>
 * 当然,你还可以调用 {@link org.apache.commons.lang3.reflect.MethodUtils MethodUtils}其他方法:
 * </p>
 * 
 * <ol>
 * <li>{@link MethodUtils#getAccessibleMethod(java.lang.reflect.Method)}</li>
 * <li>{@link MethodUtils#getAccessibleMethod(Class, String, Class...)}</li>
 * <li>{@link MethodUtils#getMatchingAccessibleMethod(Class, String, Class...)}</li>
 * <li>{@link MethodUtils#getMethodsListWithAnnotation(Class, Class)} 获得一个类中,指定泛型的方法(集合形式),比较实用</li>
 * <li>{@link MethodUtils#getMethodsWithAnnotation(Class, Class)} 获得一个类中,指定泛型的方法(数组形式),比较实用</li>
 * <li>{@link MethodUtils#getOverrideHierarchy(java.lang.reflect.Method, org.apache.commons.lang3.ClassUtils.Interfaces)}</li>
 * </ol>
 * 
 * </blockquote>
 * 
 * <p>
 * 当然,您也可以调用 java 原生态, 需要注意的是:
 * </p>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">方法</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Class#getMethods()}</td>
 * <td>
 * 返回一个包含某些 Method 对象的数组,
 * 
 * <p>
 * 反映此 Class 所表示的类或接口(包括由该类或接口声明的以及继承的类或接口)的<span style="color:green"><b>公共(public) member</b>
 * </span>方法.<br>
 * </p>
 * 
 * <b>返回数组中的元素没有排序.</b><br>
 * 
 * 如果类声明了带有相同参数类型的多个公共成员方法,则它们都会包含在返回的数组中.<br>
 * 如果此 Class 对象表示没有公共成员方法的类或接口,或者表示一个基本类型或 void,则此方法返回长度为 0 的数组. <br>
 * 类初始化方法<code>&lt;clinit&gt;</code> 不包含在返回的数组中.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Class#getDeclaredMethods()}</td>
 * <td>
 * 返回 Method 对象的一个数组,
 * 
 * <p>
 * 反映此 Class 对象表示的类或接口声明的所有方法,<br>
 * 包括<span style="color:green"><b>公共(public)</b>
 * 、<b>保护(protected)</b>
 * 、<b>默认(包)访问(default (package) access)</b>
 * 和<b>私有方法(private)</b>
 * </span>,<br>
 * 但 <span style="color:red"><b>不包括继承(inherited)</b></span>的方法.
 * </p>
 * 如果该类声明带有相同参数类型的多个公共成员方法,则它们都包含在返回的数组中.
 * <p>
 * 
 * <b>返回数组中的元素没有排序.</b>
 * 
 * </p>
 * 如果该类或接口不声明任何方法,或者此 Class 对象表示一个基本类型、一个数组类或 void,则此方法返回一个长度为 0 的数组.<br>
 * 类初始化方法<code>&lt;clinit&gt;</code> 包含在返回数组中.<br>
 * </td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.reflect.MethodUtils
 * @see org.apache.commons.lang3.ClassUtils#getPublicMethod(Class, String, Class...)
 * @see java.util.ServiceLoader jdk1.6 ServiceLoader
 * @since 1.0.7
 */
public final class MethodUtil{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodUtil.class);

    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private MethodUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    // [start]

    /**
     * 执行指定对象 <code>object</code> 的指定方法 <code>methodName</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>使用场景:适合比如上传下载 service有很多相同类型的方法,比如 importXX1,importXX2,对于这种,可以使用调用此方法来快速调用方法</li>
     * <li>支持调用对象父类方法</li>
     * <li>也支持调用自己对象或者父类的静态方法,结果相似于 {@link #invokeStaticMethod(Class, String, Object...)},除了参数不同,一个是对象,一个是class,不过通常不建议这么使用</li>
     * <li>调用的是 {@link MethodUtils#invokeMethod(Object, String, Object...)},内部调用的是 {@link java.lang.Class#getMethods()}来处理,<b>不支持</b>
     * 调用private 方法</li>
     * <li>params将会转成<b>包装类型</b>来寻找method
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 如有以下的
     * </p>
     * 
     * <pre class="code">
     * 
     * public class OverloadMethod{
     * 
     *     public String age(int age){
     *         return "age int:" + age;
     *     }
     * 
     *     public String age(Integer age){
     *         return "age Integer:" + age;
     *     }
     * }
     * 
     * </pre>
     * 
     * 测试调用
     *
     * <pre class="code">
     * 
     * public void testInvokeMethod(){
     *     LOGGER.debug("" + MethodUtil.invokeMethod(new OverloadMethod(), "age", 5));
     *     LOGGER.debug("" + MethodUtil.invokeMethod(new OverloadMethod(), "age", Integer.parseInt("5")));
     * }
     * </pre>
     * 
     * <b>结果都是:</b>
     * 
     * <pre class="code">
     * age Integer:5
     * age Integer:5
     * </pre>
     * 
     * </blockquote>
     * </li>
     * <li>如果你要精准调用,请使用 {@link #invokeMethod(Object, String, Object[], Class[])}
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * Class{@code <?>}[] parameterTypes1 = { Integer.TYPE };
     * assertEquals("age int:5", MethodUtil.invokeMethod(new OverloadMethod(), "age", toArray(5), parameterTypes1));
     * 
     * </pre>
     * 
     * </blockquote>
     * </ol>
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
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>methodName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>methodName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>obj</code>没有指定的<code>methodName</code>方法,抛出 {@link ReflectException}<br>
     *         如果 <code>params</code> 是null,系统内部会使用 empty 的class 数组<br>
     *         如果 <code>params</code> 是empty,表示不需要参数<br>
     * @see java.lang.reflect.Method#invoke(Object, Object...)
     * @see com.feilong.core.lang.ClassUtil#toClass(Object...)
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeMethod(Object, String, Object...)
     * @see org.apache.commons.lang3.reflect.MethodUtils#getMatchingAccessibleMethod(Class, String, Class...)
     */
    public static <T> T invokeMethod(Object obj,String methodName,Object...params){
        Validate.notNull(obj, "obj can't be null!");
        Validate.notBlank(methodName, "methodName can't be blank!");

        final Class<?>[] parameterTypes = ClassUtil.toClass(params);
        return invokeMethod(obj, methodName, params, parameterTypes);
    }

    //---------------------------------------------------------------

    /**
     * 执行指定对象 <code>object</code> 的指定方法 <code>methodName</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>使用场景:适合比如上传下载 service有很多相同类型的方法,比如 importXX1,importXX2,对于这种,可以使用调用此方法来快速调用方法</li>
     * <li>支持调用对象父类方法</li>
     * <li>也支持调用自己对象或者父类的静态方法,结果相似于 {@link #invokeStaticMethod(Class, String, Object[], Class[])},除了参数不同,一个是对象,一个是class,不过通常不建议这么使用</li>
     * <li>调用的是 {@link MethodUtils#invokeMethod(Object, String, Object...)},内部调用的是 {@link java.lang.Class#getMethods()}来处理,<b>不支持</b>
     * 调用private 方法</li>
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 如有以下的
     * </p>
     * 
     * <pre class="code">
     * 
     * public class OverloadMethod{
     * 
     *     public String age(int age){
     *         return "age int:" + age;
     *     }
     * 
     *     public String age(Integer age){
     *         return "age Integer:" + age;
     *     }
     * }
     * 
     * </pre>
     * 
     * <p>
     * 测试调用
     * </p>
     *
     * <pre class="code">
     * 
     * Class{@code <?>}[] parameterTypes1 = { Integer.TYPE };
     * assertEquals("age int:5", MethodUtil.invokeMethod(new OverloadMethod(), "age", toArray(5), parameterTypes1));
     * 
     * Class{@code <?>}[] parameterTypes2 = { Integer.class };
     * assertEquals("age Integer:5", MethodUtil.invokeMethod(new OverloadMethod(), "age", toArray(Integer.parseInt("5")), parameterTypes2));
     * 
     * </pre>
     * 
     * </blockquote>
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
     * @return 如果 <code>object</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>methodName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>methodName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>obj</code>没有指定的<code>methodName</code>方法,抛出 {@link ReflectException}<br>
     *         如果 <code>parameterTypes</code> 是null,系统内部会使用 empty 的class 数组<br>
     *         如果 <code>parameterTypes</code> 是empty,表示不需要参数<br>
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeMethod(Object, String, Object[], Class[])
     * @see org.apache.commons.lang3.reflect.MethodUtils#getMatchingAccessibleMethod(Class, String, Class...)
     * @since 1.1.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(final Object object,final String methodName,Object[] args,Class<?>[] parameterTypes){
        Validate.notNull(object, "object can't be null!");
        Validate.notBlank(methodName, "methodName can't be blank!");

        //---------------------------------------------------------------
        try{
            return (T) MethodUtils.invokeMethod(object, methodName, args, parameterTypes);
        }catch (Exception e){
            String pattern = "invokeMethod Exception,object:[{}],methodName:[{}],args:[{}],parameterTypes:[{}]";
            String message = Slf4jUtil.format(pattern, object, methodName, args, parameterTypes);
            throw new ReflectException(message, e);
        }
    }

    //---------------------------------------------------------------

    /**
     * 执行指定类型 <code>klass</code> 的指定静态方法 <code>staticMethodName</code> (同时支持 私有静态方法).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>支持调用对象父类静态方法</li>
     * <li>不可以调用非静态的方法</li>
     * <li>支持调用私有的静态的方法(since 1.11.5 )</li>
     * <li>params将会转成<b>包装类型</b>来寻找method</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>注意 :</h3>
     * <blockquote>
     * <ol>
     * <li>{@link MethodUtils#invokeStaticMethod(Class, String, Object[], Class[])} 内部调用的是 {@link java.lang.Class#getMethods()}来处理,
     * 直接使用的话,<span style="color:red"><b>不支持</b></span> 调用 private 方法</li>
     * 
     * <li>该方法进行容错处理,如果{@link MethodUtils#invokeStaticMethod(Class, String, Object[], Class[])} 找不到对应的 公共的静态方法, 将会去尝试找私有额静态方法</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 如有以下的
     * </p>
     * 
     * <pre class="code">
     * 
     * public class OverloadStaticMethod{
     * 
     *     public static String age(int age){
     *         return "static age int:" + age;
     *     }
     * 
     *     public static String age(Integer age){
     *         return "static age Integer:" + age;
     *     }
     * }
     * 
     * </pre>
     * 
     * 测试调用
     *
     * <pre class="code">
     * MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", 5);
     * MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", Integer.parseInt("5"));
     * </pre>
     * 
     * <b>结果都是:</b>
     * 
     * <pre class="code">
     * static age Integer:5
     * static age Integer:5
     * </pre>
     * 
     * </blockquote>
     * 
     * 
     * <p>
     * 如果你要精准调用,请使用 {@link #invokeStaticMethod(Class, String, Object[], Class[])}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Class{@code <?>}[] parameterTypes1 = { Integer.TYPE };
     * assertEquals("static age int:5", MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", toArray(5), parameterTypes1));
     * 
     * Class{@code <?>}[] parameterTypes2 = { Integer.class };
     * assertEquals(
     *                 "static age Integer:5",
     *                 MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", toArray(Integer.parseInt("5")), parameterTypes2));
     * </pre>
     * 
     * </blockquote>
     *
     * 
     * @param <T>
     *            the generic type
     * @param klass
     *            the klass
     * @param staticMethodName
     *            静态方法名
     * @param params
     *            动态参数
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>staticMethodName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>staticMethodName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>staticMethodName</code> 是实例方法,非静态方法,抛出 {@link ReflectException}<br>
     *         如果 <code>klass</code>没有指定的<code>staticMethodName</code>方法,抛出 {@link ReflectException}<br>
     *         如果 <code>parameterTypes</code> 是null,系统内部会使用 empty 的class 数组<br>
     *         如果 <code>parameterTypes</code> 是empty,表示不需要参数<br>
     * @see java.lang.reflect.Method#invoke(Object, Object...)
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeStaticMethod(Class, String, Object...)
     */
    public static <T> T invokeStaticMethod(Class<?> klass,String staticMethodName,Object...params){
        final Class<?>[] parameterTypes = ClassUtil.toClass(params);
        return invokeStaticMethod(klass, staticMethodName, params, parameterTypes);
    }

    /**
     * 执行指定类型 <code>klass</code> 的指定静态方法 <code>staticMethodName</code> (同时支持 私有静态方法).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>支持调用对象父类静态方法</li>
     * <li>不可以调用非静态的方法</li>
     * <li>支持调用私有的静态的方法(since 1.11.5 )</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>注意 :</h3>
     * <blockquote>
     * <ol>
     * <li>{@link MethodUtils#invokeStaticMethod(Class, String, Object[], Class[])} 内部调用的是 {@link java.lang.Class#getMethods()}来处理,
     * 直接使用的话,<span style="color:red"><b>不支持</b></span> 调用 private 方法</li>
     * 
     * <li>该方法进行容错处理,如果{@link MethodUtils#invokeStaticMethod(Class, String, Object[], Class[])} 找不到对应的 公共的静态方法, 将会去尝试找私有额静态方法</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 如有以下的
     * </p>
     * 
     * <pre class="code">
     * 
     * public class OverloadStaticMethod{
     * 
     *     public static String age(int age){
     *         return "static age int:" + age;
     *     }
     * 
     *     public static String age(Integer age){
     *         return "static age Integer:" + age;
     *     }
     * }
     * 
     * </pre>
     * 
     * 测试调用
     *
     * <pre class="code">
     * Class{@code <?>}[] parameterTypes1 = { Integer.TYPE };
     * assertEquals("static age int:5", MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", toArray(5), parameterTypes1));
     * 
     * Class{@code <?>}[] parameterTypes2 = { Integer.class };
     * assertEquals(
     *                 "static age Integer:5",
     *                 MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", toArray(Integer.parseInt("5")), parameterTypes2));
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param klass
     *            the klass
     * @param staticMethodName
     *            静态方法名
     * @param args
     *            the args
     * @param parameterTypes
     *            the parameter types
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>staticMethodName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>staticMethodName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>staticMethodName</code> 是实例方法,非静态方法,抛出 {@link ReflectException}<br>
     *         如果 <code>klass</code>没有指定的<code>staticMethodName</code>方法,抛出 {@link ReflectException}<br>
     *         如果 <code>parameterTypes</code> 是null,系统内部会使用 empty 的class 数组<br>
     *         如果 <code>parameterTypes</code> 是empty,表示不需要参数<br>
     * @see org.apache.commons.lang3.reflect.MethodUtils#invokeStaticMethod(Class, String, Object[], Class[])
     * @since 1.1.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeStaticMethod(final Class<?> klass,final String staticMethodName,Object[] args,Class<?>[] parameterTypes){
        Validate.notNull(klass, "klass can't be null!");
        Validate.notBlank(staticMethodName, "staticMethodName can't be blank!");
        //---------------------------------------------------------------
        try{
            return (T) MethodUtils.invokeStaticMethod(klass, staticMethodName, args, parameterTypes);
        }catch (NoSuchMethodException e){
            LOGGER.trace(
                            "from class:[{}],can't find [public static {}()] method,cause exception: [{}],will try to find [private static] method",
                            klass.getSimpleName(),
                            staticMethodName,
                            e);
            //---------------------------------------------------------------
            return doWithNoSuchMethodException(klass, staticMethodName, args, parameterTypes);
        }catch (Exception e){
            throw new ReflectException(buildMessage(klass, staticMethodName, args, parameterTypes), e);
        }
    }

    //---------------------------------------------------------------

    /**
     * Do with no such method exception.
     *
     * @param <T>
     *            the generic type
     * @param klass
     *            the klass
     * @param staticMethodName
     *            the static method name
     * @param args
     *            the args
     * @param parameterTypes
     *            the parameter types
     * @return the t
     * @throws ReflectException
     *             the reflect exception
     * @since 1.11.5
     */
    @SuppressWarnings("unchecked")
    private static <T> T doWithNoSuchMethodException(Class<?> klass,String staticMethodName,Object[] args,Class<?>[] parameterTypes){
        try{
            Method matchingMethod = MethodUtils.getMatchingMethod(klass, staticMethodName, parameterTypes);
            if (null == matchingMethod){
                throw new NoSuchMethodException("No such method:[" + staticMethodName + "()] on class: " + klass.getName());
            }

            //---------------------------------------------------------------

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("bingo,from class:[{}],find name [{}] method", klass.getSimpleName(), staticMethodName);
            }

            //---------------------------------------------------------------
            matchingMethod.setAccessible(true);
            return (T) matchingMethod.invoke(null, args);
        }catch (Exception e){
            throw new ReflectException(buildMessage(klass, staticMethodName, args, parameterTypes), e);
        }
    }

    //---------------------------------------------------------------

    /**
     * Builds the message.
     *
     * @param klass
     *            the klass
     * @param staticMethodName
     *            the static method name
     * @param args
     *            the args
     * @param parameterTypes
     *            the parameter types
     * @return the string
     */
    private static String buildMessage(Class<?> klass,String staticMethodName,Object[] args,Class<?>[] parameterTypes){
        String pattern = "invokeStaticMethod Exception,class:[{}],staticMethodName:[{}],args:[{}],parameterTypes:[{}]";
        return Slf4jUtil.format(pattern, klass.getName(), staticMethodName, args, parameterTypes);
    }
}
