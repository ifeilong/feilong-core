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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Validator;
import com.feilong.core.lang.ClassUtil;

/**
 * 封装了 {@link PropertyUtils}.
 * 
 * <h3>{@link PropertyUtils}与 {@link BeanUtils}:</h3>
 * 
 * <blockquote>
 * <p>
 * {@link PropertyUtils}类和 {@link BeanUtils}类很多的方法在参数上都是相同的,但返回值不同. <br>
 * BeanUtils着重于"Bean",返回值通常是String,<br>
 * 而PropertyUtils着重于属性,它的返回值通常是Object. 
 * </p>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.8 2014-7-21 17:45:30
 * @see org.apache.commons.beanutils.PropertyUtils
 * @see com.feilong.core.bean.BeanUtil
 */
public final class PropertyUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

    /** Don't let anyone instantiate this class. */
    private PropertyUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将{@code fromObj}中的属性或者一组属性的值的复制到 {@code toObj}对象中.
     * 
     * <p>
     * 注意:这种copy都是 <span style="color:red">浅拷贝</span>,复制后的2个Bean的同一个属性可能拥有同一个对象的ref,这个在使用时要小心,特别是对于属性为自定义类的情况 .
     * </p>
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>如果传入的<code>includePropertyNames</code>,含有 <code>fromObj</code>没有的属性名字,将会抛出异常</li>
     * <li>如果传入的<code>includePropertyNames</code>,含有 <code>fromObj</code>有,但是 <code>toObj</code>没有的属性名字,可以正常运行,see
     * {@link org.apache.commons.beanutils.BeanUtilsBean#copyProperty(Object, String, Object)} Line391</li>
     * <li>对于Date类型,<span style="color:red">不需要先注册converter</span></li>
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>使用示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * 
        User oldUser = new User();
        oldUser.setId(5L);
        oldUser.setMoney(new BigDecimal(500000));
        oldUser.setDate(new Date());
        String[] nickName = { "feilong", "飞天奔月", "venusdrogon" };
        oldUser.setNickName(nickName);
        
        
        User newUser = new User();
    
        String[] strs = { "date", "money", "nickName" };
        PropertyUtil.copyProperties(newUser, oldUser, strs);
        
        LOGGER.info(JsonUtil.format(newUser));
     * 
     * 返回 :
     * {
        "userAddresseList": [],
        "userAddresses": [],
        "date": "2015-09-06 13:27:43",
        "password": "",
        "id": 0,
        "nickName":         [
            "feilong",
            "飞天奔月",
            "venusdrogon"
        ],
        "age": 0,
        "name": "feilong",
        "money": 500000,
        "attrMap": null,
        "userInfo": {"age": 0},
        "loves": []
    }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>{@link BeanUtils#copyProperties(Object, Object)}与 {@link PropertyUtils#copyProperties(Object, Object)}区别</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>{@link BeanUtils#copyProperties(Object, Object)} 提供类型转换功能,即发现两个JavaBean的同名属性为不同类型时,在支持的数据类型范围内进行转换,而
     * {@link PropertyUtils#copyProperties(Object, Object)}不支持这个功能,但是速度会更快一些.</li>
     * <li>commons-beanutils v1.9.0以前的版本 BeanUtils不允许对象的属性值为 null,PropertyUtils可以拷贝属性值 null的对象.<br>
     * (<b>注:</b>commons-beanutils v1.9.0+修复了这个情况,BeanUtilsBean.copyProperties() no longer throws a ConversionException for null properties
     * of certain data types),具体信息,可以参阅commons-beanutils的
     * {@link <a href="http://commons.apache.org/proper/commons-beanutils/javadocs/v1.9.2/RELEASE-NOTES.txt">RELEASE-NOTES.txt</a>}</li>
     * </ul>
     * </blockquote>
     *
     * @param toObj
     *            目标对象
     * @param fromObj
     *            原始对象
     * @param includePropertyNames
     *            包含的属性数组名字数组,(can be nested/indexed/mapped/combo)<br>
     *            如果 是null or empty ,将会调用 {@link PropertyUtils#copyProperties(Object, Object)}
     * @see #setProperty(Object, String, Object)
     * 
     * @see com.feilong.core.bean.BeanUtil#copyProperties(Object, Object, String...)
     * 
     * @see org.apache.commons.beanutils.PropertyUtilsBean#copyProperties(Object, Object)
     * @see <a href="http://www.cnblogs.com/kaka/archive/2013/03/06/2945514.html">Bean复制的几种框架性能比较(Apache BeanUtils、PropertyUtils,Spring
     *      BeanUtils,Cglib BeanCopier)</a>
     * @since 1.4.1
     */
    public static void copyProperties(Object toObj,Object fromObj,String...includePropertyNames){
        Validate.notNull(toObj, "No destination bean/toObj specified");
        Validate.notNull(fromObj, "No origin bean/fromObj specified");

        if (Validator.isNullOrEmpty(includePropertyNames)){
            try{
                PropertyUtils.copyProperties(toObj, fromObj);
                return;
            }catch (Exception e){
                LOGGER.error(e.getClass().getName(), e);
                throw new BeanUtilException(e);
            }
        }
        for (String propertyName : includePropertyNames){
            Object value = getProperty(fromObj, propertyName);
            setProperty(toObj, propertyName, value);
        }
    }

    /**
     * 返回一个<code>bean</code>中所有的 <span style="color:green">可读属性</span>,并将属性名/属性值放入一个Map中.
     * 
     * <p>
     * Return the entire set of properties for which the specified bean provides a read method.
     * </p>
     * 
     * <p>
     * 另外还有一个名为class的属性,属性值是Object的类名,事实上class是java.lang.Object的一个属性
     * </p>
     * 
     * <h3>原理:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>取到bean class的 {@link java.beans.PropertyDescriptor}数组</li>
     * <li>循环,找到 {@link java.beans.PropertyDescriptor#getReadMethod()}</li>
     * <li>将 name and {@link org.apache.commons.beanutils.PropertyUtilsBean#getProperty(Object, String)} 设置到map中</li>
     * </ol>
     * </blockquote>
     *
     * @param bean
     *            Bean whose properties are to be extracted
     * @return The set of properties for the bean
     * @see org.apache.commons.beanutils.BeanUtils#describe(Object)
     * @see org.apache.commons.beanutils.PropertyUtils#describe(Object)
     * @see com.feilong.core.bean.BeanUtil#describe(Object)
     */
    public static Map<String, Object> describe(Object bean){
        try{
            return PropertyUtils.describe(bean);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    /**
     * 使用 {@link PropertyUtils#setProperty(Object, String, Object)} 来设置指定bean对象中的指定属性的值(<b>不会进行类型转换</b>).
     * 
     * <p>
     * no matter which property reference format is used, with no type conversions.
     * </p>
     * 
     * <p>
     * PropertyUtils的功能类似于BeanUtils,但在底层不会对传递的数据做转换处理
     * </p>
     *
     * @param bean
     *            Bean whose property is to be modified
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 {@link <a href="../BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            Value to which this property is to be set
     * @see org.apache.commons.beanutils.BeanUtils#setProperty(Object, String, Object)
     * @see org.apache.commons.beanutils.PropertyUtils#setProperty(Object, String, Object)
     * @see com.feilong.core.bean.BeanUtil#setProperty(Object, String, Object)
     */
    public static void setProperty(Object bean,String propertyName,Object value){
        try{
            PropertyUtils.setProperty(bean, propertyName, value);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    /**
     * 如果 <code>value</code>isNotNullOrEmpty,那么才调用 {@link #setProperty(Object, String, Object)}.
     *
     * @param bean
     *            Bean whose property is to be modified
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 {@link <a href="../BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            Value to which this property is to be set
     * @since 1.5.3
     */
    public static void setPropertyIfValueNotNullOrEmpty(Object bean,String propertyName,Object value){
        if (Validator.isNotNullOrEmpty(value)){
            setProperty(bean, propertyName, value);
        }
    }

    /**
     * 如果 <code>null != value</code>,那么才调用 {@link #setProperty(Object, String, Object)}.
     *
     * @param bean
     *            Bean whose property is to be modified
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 {@link <a href="../BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            Value to which this property is to be set
     * @since 1.5.3
     * @see #setProperty(Object, String, Object)
     */
    public static void setPropertyIfValueNotNull(Object bean,String propertyName,Object value){
        if (null != value){
            setProperty(bean, propertyName, value);
        }
    }

    // [start] getProperty

    /**
     * 使用 {@link PropertyUtils#getProperty(Object, String)} 从指定bean对象中取得指定属性名称的值.
     * 
     * <p>
     * no matter which property reference format is used, with no type conversions.<br>
     * For more details see {@link PropertyUtilsBean}.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            Bean whose property is to be extracted
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 {@link <a href="../BeanUtil.html#propertyName">propertyName</a>}
     * @return 使用{@link PropertyUtils#getProperty(Object, String)} 从对象中取得属性值
     * @see com.feilong.core.bean.BeanUtil#getProperty(Object, String)
     * @see org.apache.commons.beanutils.BeanUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtilsBean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object bean,String propertyName){
        try{
            return (T) PropertyUtils.getProperty(bean, propertyName);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [end]

    /**
     * 从指定的 <code>Object obj</code>中,查找指定类型的值.
     * 
     * <p>
     * PS:目前暂不支持从集合里面找到指定类型的值,参见 {@link #isCannotFindType(Object)},如果你有相关需求,可以调用 {@link
     * "org.springframework.util.CollectionUtils#findValueOfType(Collection, Class)"}
     * </p>
     * 
     * <h3>代码流程:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>{@code if ClassUtil.isInstance(findValue, toBeFindedClassType) 直接返回 findValue}</li>
     * <li>自动过滤{@code isPrimitiveOrWrapper},{@code CharSequence},{@code Collection},{@code Map}类型</li>
     * <li>调用 {@link PropertyUtil#describe(Object)} 再递归查找</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            User user = new User();
            user.setId(5L);
            Date now = new Date();
            user.setDate(now);
    
            user.getUserInfo().setAge(28);
    
            LOGGER.info(JsonUtil.format(PropertyUtil.findValueOfType(user, UserInfo.class)));
    }
    
    返回:
    {"age": 28}
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param obj
     *            要被查找的对象
     * @param toBeFindedClassType
     *            the to be finded class type
     * @return a value of the given type found if there is a clear match,or {@code null} if none such value found
     * @see "org.springframework.util.CollectionUtils#findValueOfType(Collection, Class)"
     * @since 1.4.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T findValueOfType(Object obj,Class<T> toBeFindedClassType){
        if (Validator.isNullOrEmpty(obj)){
            return null;
        }

        Validate.notNull(toBeFindedClassType, "toBeFindedClassType can't be null/empty!");

        if (ClassUtil.isInstance(obj, toBeFindedClassType)){
            return (T) obj;
        }

        if (isCannotFindType(obj)){
            LOGGER.debug("obj:[{}] not support find toBeFindedClassType:[{}]", obj.getClass().getName(), toBeFindedClassType.getName());
            return null;
        }

        //******************************************************************************
        Map<String, Object> describe = describe(obj);

        for (Map.Entry<String, Object> entry : describe.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if (null != value && !"class".equals(key)){
                //级联查询
                T t = findValueOfType(value, toBeFindedClassType);
                if (null != t){
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * 一般自定义的command 里面 就是些 string int,list map等对象.
     * 
     * <p>
     * 这些我们过滤掉,只取类型是 findedClassType的
     * </p>
     * 
     * @param obj
     *            the obj
     * @return true, if checks if is can find type
     * @since 1.5.3
     */
    private static boolean isCannotFindType(Object obj){
        //一般自定义的command 里面 就是些 string int,list map等对象
        //这些我们过滤掉,只取类型是 findedClassType的
        return ClassUtils.isPrimitiveOrWrapper(obj.getClass())//
                        || ClassUtil.isInstance(obj, CharSequence.class)//
                        || ClassUtil.isInstance(obj, Collection.class) //
                        || ClassUtil.isInstance(obj, Map.class);
    }
}