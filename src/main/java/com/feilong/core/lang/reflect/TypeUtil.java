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

import static com.feilong.core.bean.ConvertUtil.convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.ToStringConfig;

/**
 * Utility methods focusing on type inspection, particularly with regard to generics.
 * 
 * <h3>关于获取Class泛型说明:</h3>
 * 
 * <blockquote>
 * 
 * <ol>
 * 
 * <li>{@link Class#getGenericSuperclass()} 返回此 Class 所表示的实体(类、接口、基本类型或 void)的直接超类的 Type.
 * <ol>
 * <li>如果超类是参数化类型,则返回的 Type 对象,必须准确反映源代码中所使用的实际类型参数.</li>
 * <li>如果以前未曾创建表示超类的参数化类型,则创建这个类型.有关参数化类型创建过程的语义,请参阅 {@link ParameterizedType} 声明.</li>
 * <li>如果此 Class 表示 Object 类、接口、基本类型或 void,则返回 null.</li>
 * <li>如果此对象表示一个数组类,则返回表示 Object 类的 Class 对象.</li>
 * </ol>
 * </li>
 * 
 * <li>{@link Class#getGenericInterfaces()} 返回某些接口的 Type,这些接口由此对象所表示的类或接口直接实现
 * <ol>
 * <li>如果超接口是参数化类型,则为它返回的 Type 对象必须准确反映源代码中所使用的实际类型参数.</li>
 * <li>如果以前未曾创建表示每个超接口的参数化类型,则创建这个类型.有关参数化类型创建过程的语义,请参阅 {@link ParameterizedType} 声明.</li>
 * <li>
 * 如果此对象表示一个类,则返回一个包含这样一些对象的数组,这些对象表示该类实现的所有接口.<br>
 * 数组中接口对象顺序与此对象所表示的类的声明的 implements 子句中接口名顺序一致.<br>
 * 对于数组类,接口 Cloneable 和 Serializable 以该顺序返回.
 * </li>
 * <li>如果此对象表示一个接口,则该数组包含表示该接口直接扩展的所有接口的对象.数组中接口对象顺序与此对象所表示的接口的声明的 extends 子句中接口名顺序一致.</li>
 * <li>如果此对象表示一个不实现任何接口的类或接口,则此方法返回一个长度为 0 的数组.</li>
 * <li>如果此对象表示一个基本类型或 void,则此方法返回一个长度为 0 的数组.</li>
 * </ol>
 * </li>
 * 
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

    //---------------------------------------------------------------

    /**
     * 获得某个类的父类上面的泛型参数的类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * public class SkuItemRepositoryImpl extends BaseSolrRepositoryImpl{@code <SkuItem, Long>} implements SkuItemRepository
     * </pre>
     * 
     * <p>
     * 这样的类,如果想要取到父类的泛型参数 [SkuItem.class,Long.class],可以使用:
     * </p>
     * 
     * <pre class="code">
     * TypeUtil.getGenericSuperclassParameterizedRawTypes(SkuItemRepositoryImpl.class)
     * </pre>
     * 
     * </blockquote>
     * 
     * @param klass
     *            the klass
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>klass</code> 没有父类(除了Object),抛出 {@link NullPointerException}<br>
     *         如果 <code>klass</code> 有父类(除了Object)但是父类没有泛型参数,抛出 {@link NullPointerException}<br>
     * @see #getGenericSuperclassParameterizedType(Class)
     * @see #extractActualTypeArgumentClassArray(ParameterizedType)
     * @since 1.1.1
     */
    public static Class<?>[] getGenericSuperclassParameterizedRawTypes(Class<?> klass){
        Validate.notNull(klass, "klass can't be null/empty!");
        ParameterizedType parameterizedType = getGenericSuperclassParameterizedType(klass);
        return extractActualTypeArgumentClassArray(parameterizedType);
    }

    /**
     * 获得某个类的接口上面的泛型参数的类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>对于以下的场景:</b>
     * </p>
     * 
     * <pre class="code">
     * 
     * public interface BaseSolrRepository{@code <T, PK extends Serializable>} {
     * 
     * }
     * 
     * public class SkuItemRepositoryInterfaceImpl implements BaseSolrRepository{@code <SkuItem, Long>}{
     * 
     * }
     * 
     * </pre>
     * 
     * <p>
     * 如果你需要提取 SkuItemRepositoryInterfaceImpl类 接口 BaseSolrRepository{@code <SkuItem, Long>}中的泛型参数
     * </p>
     * 
     * <b>你可以使用:</b>
     * 
     * <pre class="code">
     * Class{@code <?>}[] rawTypes = TypeUtil
     *                 .getGenericInterfacesParameterizedRawTypes(SkuItemRepositoryInterfaceImpl.class, BaseSolrRepository.class);
     * 
     * assertArrayEquals(toArray(SkuItem.class, Long.class), rawTypes);
     * </pre>
     * 
     * </blockquote>
     *
     * @param klass
     *            the klass
     * @param extractInterfaceClass
     *            待抽取的接口类型
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>extractInterfaceClass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>klass</code> 是没有泛型接口,抛出 {@link NullPointerException}<br>
     *         如果 <code>klass</code> 有泛型接口但是其中没有指定的接口类型<code>extractInterfaceClass</code> ,抛出 {@link NullPointerException}<br>
     * @see #getGenericInterfacesParameterizedType(Class, Class)
     * @see #extractActualTypeArgumentClassArray(ParameterizedType)
     * @since 1.1.1
     */
    public static Class<?>[] getGenericInterfacesParameterizedRawTypes(Class<?> klass,Class<?> extractInterfaceClass){
        Validate.notNull(klass, "klass can't be null/empty!");
        Validate.notNull(extractInterfaceClass, "extractInterfaceClass can't be null/empty!");

        ParameterizedType parameterizedType = getGenericInterfacesParameterizedType(klass, extractInterfaceClass);
        return extractActualTypeArgumentClassArray(parameterizedType);
    }

    //---------------------------------------------------------------

    /**
     * 获得 generic interfaces parameterized type.
     *
     * @param klass
     *            the klass
     * @param extractInterfaceClass
     *            the extract interface class
     * @return 如果 klass没有泛型接口,返回null
     * @see java.lang.Class#getGenericInterfaces()
     * @see java.lang.reflect.ParameterizedType#getRawType()
     * @since 1.1.1
     */
    private static ParameterizedType getGenericInterfacesParameterizedType(Class<?> klass,Class<?> extractInterfaceClass){
        Type[] genericInterfaces = klass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces){
            if (!(genericInterface instanceof ParameterizedType)){
                continue;
            }

            //---------------------------------------------------------------

            ParameterizedType genericInterfacesType = (ParameterizedType) genericInterface;
            Type rawType = genericInterfacesType.getRawType();

            if (extractInterfaceClass == rawType){
                return genericInterfacesType;
            }
        }
        return null;
    }

    /**
     * 获得 superclass parameterized type.
     *
     * @param klass
     *            the klass
     * @return 如果没有父类或者父类没有泛型参数,返回null
     * @see java.lang.Class#getGenericSuperclass()
     */
    private static ParameterizedType getGenericSuperclassParameterizedType(Class<?> klass){
        Class<?> useClass = klass;
        Type type = useClass.getGenericSuperclass(); //com.feilong.....BaseSolrRepositoryImpl<com.feilong.....SkuItem, java.lang.Long>

        while (!(type instanceof ParameterizedType) && Object.class != useClass){
            useClass = useClass.getSuperclass();
            type = useClass.getGenericSuperclass();
        }
        return (ParameterizedType) type;
    }

    /**
     * 提取实际的泛型参数数组.
     *
     * @param parameterizedType
     *            the parameterized type
     * @return 如果 <code>parameterizedType</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>parameterizedType</code> 没有实际的泛型参数 {@link ParameterizedType#getActualTypeArguments()},抛出
     *         {@link NullPointerException}<br>
     * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
     * @since 1.1.1
     */
    private static Class<?>[] extractActualTypeArgumentClassArray(ParameterizedType parameterizedType){
        Validate.notNull(parameterizedType, "parameterizedType can't be null/empty!");
        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("parameterizedType info:[{}]", parameterizedType);
        }

        //---------------------------------------------------------------
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Validate.notNull(actualTypeArguments, "actualTypeArguments can't be null/empty!");

        //---------------------------------------------------------------

        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("actualTypeArguments:[{}]", ConvertUtil.toString(actualTypeArguments, ToStringConfig.DEFAULT_CONNECTOR));
        }
        return convert(actualTypeArguments, Class[].class);
    }
}