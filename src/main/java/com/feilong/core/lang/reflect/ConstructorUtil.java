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

import java.lang.reflect.Constructor;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 使用反射方法请求构造函数创建新实例的工具类,可以简化程序中使用反射方式创建对象的代码,focused on constructors.
 * 
 * <h3>方法介绍:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>{@link #newInstance(Class, Object...)} 获得实例</li>
 * <li>{@link #newInstance(String, Object...)} 获得实例</li>
 * <li>{@link #newInstance(Class, Object[], Class[])} 获得实例</li>
 * </ol>
 * 
 * 上面三个方法,底层均调用了 {@link ConstructorUtils#invokeConstructor(Class, Object[], Class[])} ,此方法 会
 * {@link ConstructorUtils#getMatchingAccessibleConstructor(Class, Class...)} 自动根据类型活动最匹配的构造函数,并且将异常转成了 {@link ReflectException} 以便调用的时候使用
 * 
 * <p>
 * 因此,不用担心 int-->Integer 原始类型参数和包装类型参数,以及父类/子类 这样的参数不匹配带来的问题
 * </p>
 * </blockquote>
 * 
 * <h3>如果不想使用自动匹配的特性</h3>
 * 
 * <blockquote>
 * <p>
 * 您可以使用 原生方法,下面两个是 获得精准的构造函数并实例,如果参数类型不匹配,那么就会抛异常
 * </p>
 * 
 * <ol>
 * <li>{@link ConstructorUtils#invokeExactConstructor(Class, Object...)} 获得实例</li>
 * <li>{@link ConstructorUtils#invokeExactConstructor(Class, Object[], Class[])} 获得实例</li>
 * </ol>
 * 
 * 下面还有三个方法是,获得构造函数而不实例
 * <ol>
 * <li>{@link ConstructorUtils#getAccessibleConstructor(Constructor)}</li>
 * <li>{@link ConstructorUtils#getAccessibleConstructor(Class, Class...)}</li>
 * <li>{@link ConstructorUtils#getMatchingAccessibleConstructor(Class, Class...)}</li>
 * </ol>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.7 2014年7月15日 下午1:08:15
 * @version 1.1.0 2015-4-14 18:59
 * @see org.apache.commons.lang3.reflect.ConstructorUtils
 * 
 * @see "org.springframework.beans.BeanUtils.instantiateClass"
 * @since 1.0.7
 */
public final class ConstructorUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstructorUtil.class);

    /** Don't let anyone instantiate this class. */
    private ConstructorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // [start] newInstance

    /**
     * 新建实例,Returns a new instance of the specified class choosing the right constructor from the list of parameter types.<br>
     * 示例:
     * 
     * <pre class="code">
     * {@code
     * User user = ConstructorUtil.newInstance("com.feilong.test.User") 将返回user 对象
     * 
     * 你还可以 
     * User user1 = ConstructorUtil.newInstance("com.feilong.test.User", 100L); 返回 id 是100的构造函数
     * }
     * </pre>
     *
     * @param <T>
     *            t
     * @param className
     *            类得名称,比如 com.feilong.test.User
     * @param parameterValues
     *            构造函数的参数
     * @return 新建的实例,如果结果不能转成T 会抛出异常
     * @see ClassUtil#loadClass(String)
     * @see #newInstance(Class, Object...)
     * @see "org.springframework.beans.BeanUtils.instantiateClass(Constructor<T>, Object...)"
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className,Object...parameterValues){
        Validate.notEmpty(className, "className can't be null/empty!");

        try{
            // 装载连接初始化类
            Class<?> klass = ClassUtil.loadClass(className);
            return (T) newInstance(klass, parameterValues);
        }catch (ClassNotFoundException e){
            LOGGER.error(e.getClass().getName(), e);
            throw new ReflectException(e);
        }

    }

    /**
     * 新建实例,Returns a new instance of the specified class choosing the right constructor from the list of parameter types.
     *
     * @param <T>
     *            the generic type
     * @param klass
     *            类
     * @param parameterValues
     *            构造函数的参数值, 比如100L
     * @return the t
     * @see com.feilong.core.lang.ClassUtil#toClass(Object...)
     * @see java.lang.Class#getConstructor(Class...)
     * @see java.lang.reflect.Constructor#newInstance(Object...)
     * @see org.apache.commons.lang3.reflect.ConstructorUtils#invokeConstructor(Class, Object...)
     * @see "org.springframework.beans.BeanUtils.instantiateClass(Constructor<T>, Object...)"
     */
    public static <T> T newInstance(Class<T> klass,Object...parameterValues){
        Class<?>[] parameterTypes = ClassUtil.toClass(parameterValues);
        return newInstance(klass, parameterValues, parameterTypes);
    }

    /**
     * Returns a new instance of the specified class choosing the right constructor from the list of parameter types.
     *
     * @param <T>
     *            the generic type
     * @param klass
     *            the cls
     * @param args
     *            the args
     * @param parameterTypes
     *            the parameter types
     * @return the t
     * @see org.apache.commons.lang3.reflect.ConstructorUtils#invokeConstructor(Class, Object[], Class[])
     * @see "org.springframework.beans.BeanUtils.instantiateClass(Constructor<T>, Object...)"
     */
    public static <T> T newInstance(Class<T> klass,Object[] args,Class<?>[] parameterTypes){
        try{
            return ConstructorUtils.invokeConstructor(klass, args, parameterTypes);
        }catch (Exception e){
            String message = Slf4jUtil.formatMessage(
                            "invokeConstructor Exception,input params info: class:[{}].args:[{}],parameterTypes:[{}]",
                            klass,
                            args,
                            parameterTypes);
            throw new ReflectException(message, e);
        }
    }
}