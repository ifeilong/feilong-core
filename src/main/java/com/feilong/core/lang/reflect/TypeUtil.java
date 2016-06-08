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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.jsonlib.JsonUtil;

/**
 * Utility methods focusing on type inspection, particularly with regard to generics.
 * 
 * <h3>关于获取Class泛型说明:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>{@link Class#getGenericSuperclass()} 返回表示此 Class 所表示的实体(类、接口、基本类型或 void)的直接超类的 Type.
 * <ol>
 * <li>如果超类是参数化类型,则返回的 Type 对象必须准确反映源代码中所使用的实际类型参数.</li>
 * <li>如果以前未曾创建表示超类的参数化类型,则创建这个类型. 有关参数化类型创建过程的语义,请参阅 {@link ParameterizedType} 声明.</li>
 * <li>如果此 Class 表示 Object 类、接口、基本类型或 void,则返回 null.</li>
 * <li>如果此对象表示一个数组类,则返回表示 Object 类的 Class 对象.</li>
 * </ol>
 * </li>
 * <li>{@link Class#getGenericInterfaces()}
 * <ol>
 * <li>返回表示某些接口的 Type,这些接口由此对象所表示的类或接口直接实现.</li>
 * <li>如果超接口是参数化类型,则为它返回的 Type 对象必须准确反映源代码中所使用的实际类型参数.如果以前未曾创建表示每个超接口的参数化类型,则创建这个类型.有关参数化类型创建过程的语义,请参阅 {@link ParameterizedType} 声明.</li>
 * <li>如果此对象表示一个类,则返回一个包含这样一些对象的数组,这些对象表示该类实现的所有接口.数组中接口对象顺序与此对象所表示的类的声明的 implements 子句中接口名顺序一致.对于数组类,接口 Cloneable 和 Serializable 以该顺序返回.
 * </li>
 * <li>如果此对象表示一个接口,则该数组包含表示该接口直接扩展的所有接口的对象.数组中接口对象顺序与此对象所表示的接口的声明的 extends 子句中接口名顺序一致.</li>
 * <li>如果此对象表示一个不实现任何接口的类或接口,则此方法返回一个长度为 0 的数组.</li>
 * <li>如果此对象表示一个基本类型或 void,则此方法返回一个长度为 0 的数组.</li>
 * </ol>
 * </li>
 * </ol>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.reflect.TypeUtils
 * @see "org.springframework.core.GenericTypeResolver"
 * @see "org.springframework.core.GenericTypeResolver#getTypeVariableMap(Class<?>)"
 * @see "org.springframework.core.ParameterizedTypeReference<T>"
 * @see java.lang.reflect.Type
 * @see java.lang.reflect.ParameterizedType
 * @see java.lang.reflect.GenericArrayType
 * @see java.lang.reflect.TypeVariable
 * @see java.lang.reflect.WildcardType
 * @since 1.0.8
 * @since jdk 1.5
 */
public final class TypeUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeUtil.class);

    /** Don't let anyone instantiate this class. */
    private TypeUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得 generic superclass parameterized raw types.
     * 
     * <p>
     * 获得 某个类,对应的某个接口 上面的泛型参数的类型.
     * </p>
     * 
     * <p>
     * Example 1:
     * </p>
     * 
     * <pre class="code">
     * public class SkuItemRepositoryImpl extends BaseSolrRepositoryImpl{@code <SkuItem, Long>} implements SkuItemRepository
     * </pre>
     * 
     * <p>
     * 这样的类,调用 {@link TypeUtil#getGenericSuperclassParameterizedRawTypes(Class)},使用
     * <code>TypeUtil.getGenericSuperclassParameterizedRawTypes(SkuItemRepositoryImpl.class)</code>
     * 取到泛型里面参数 [SkuItem.class,Long.class]
     * </p>
     * 
     * @param klass
     *            the klass
     * @return the generic superclass parameterized raw types
     * @see #getGenericSuperclassParameterizedType(Class)
     * @see #extractActualTypeArgumentClassArray(ParameterizedType)
     * @since 1.1.1
     */
    public static Class<?>[] getGenericSuperclassParameterizedRawTypes(Class<?> klass){
        ParameterizedType parameterizedType = getGenericSuperclassParameterizedType(klass);
        return extractActualTypeArgumentClassArray(parameterizedType);
    }

    /**
     * 获得 generic interfaces parameterized raw types.
     *
     * @param klass
     *            the klass
     * @param extractInterfaceClass
     *            the extract interface class
     * @return the generic interfaces parameterized raw types
     * @see #getGenericInterfacesParameterizedType(Class, Class)
     * @see #extractActualTypeArgumentClassArray(ParameterizedType)
     * @since 1.1.1
     */
    public static Class<?>[] getGenericInterfacesParameterizedRawTypes(Class<?> klass,Class<?> extractInterfaceClass){
        ParameterizedType parameterizedType = getGenericInterfacesParameterizedType(klass, extractInterfaceClass);
        return extractActualTypeArgumentClassArray(parameterizedType);
    }

    /**
     * 获得 generic interfaces parameterized type.
     *
     * @param klass
     *            the klass
     * @param extractInterfaceClass
     *            the extract interface class
     * @return the generic interfaces parameterized type
     * @see java.lang.Class#getGenericInterfaces()
     * @see java.lang.reflect.ParameterizedType#getRawType()
     * @since 1.1.1
     */
    private static ParameterizedType getGenericInterfacesParameterizedType(Class<?> klass,Class<?> extractInterfaceClass){
        Validate.notNull(klass, "klass can't be null/empty!");
        Validate.notNull(extractInterfaceClass, "extractInterfaceClass can't be null/empty!");

        Type[] genericInterfaces = klass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces){
            if (genericInterface instanceof ParameterizedType){
                ParameterizedType genericInterfacesType = (ParameterizedType) genericInterface;
                Type rawType = genericInterfacesType.getRawType();

                if (extractInterfaceClass == rawType){
                    return genericInterfacesType;
                }
            }
        }
        return null;
    }

    /**
     * 获得 superclass parameterized type.
     *
     * @param klass
     *            the klass
     * @return the superclass parameterized type
     * @see java.lang.Class#getGenericSuperclass()
     */
    private static ParameterizedType getGenericSuperclassParameterizedType(Class<?> klass){
        Validate.notNull(klass, "klass can't be null/empty!");

        Class<?> useClass = klass;
        Type type = useClass.getGenericSuperclass(); //com.feilong.core.lang.reflect.res.BaseSolrRepositoryImpl<com.feilong.core.lang.reflect.res.SkuItem, java.lang.Long>

        while (!(type instanceof ParameterizedType) && Object.class != useClass){
            useClass = useClass.getSuperclass();
            type = useClass.getGenericSuperclass();
        }

        return (ParameterizedType) type;
    }

    /**
     * Extract actual type argument class array.
     *
     * @param parameterizedType
     *            the parameterized type
     * @return the class<?>[]
     * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
     * @since 1.1.1
     */
    private static Class<?>[] extractActualTypeArgumentClassArray(ParameterizedType parameterizedType){
        Validate.notNull(parameterizedType, "parameterizedType can't be null/empty!");

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("parameterizedType info:{}", JsonUtil.format(parameterizedType));
        }

        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Validate.notNull(actualTypeArguments, "actualTypeArguments can't be null/empty!");

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("actualTypeArguments:{}", JsonUtil.format(actualTypeArguments));
        }
        int length = actualTypeArguments.length;
        Class<?>[] klasses = new Class<?>[length];
        for (int i = 0, j = length; i < j; ++i){
            klasses[i] = (Class<?>) actualTypeArguments[i];
        }
        return klasses;
    }
}