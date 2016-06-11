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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Validator;
import com.feilong.core.lang.ClassUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * Utilities for working with Fields by reflection.
 * 
 * <p>
 * Adapted and refactored from the dormant [reflect] Commons sandbox component.
 * </p>
 * 
 * The ability is provided to break the scoping restrictions coded by the programmer.<br>
 * This can allow fields to be changed that shouldn't be.
 * This facility should be used with care.
 * 
 * <h3>和 {@link Field} 相关的几个方法的区别:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link Class#getDeclaredFields()}</td>
 * <td>返回 Field 对象的一个数组,这些对象反映此 Class 对象所表示的类或接口所声明的<span style="color:green">所有字段</span>.
 * <p>
 * 包括<span style="color:green">公共、保护、默认(包)访问和私有字段</span>, <span style="color:red">但不包括继承的字段</span>.
 * </p>
 * <b>返回数组中的元素没有排序,也没有任何特定的顺序.</b><br>
 * 如果该类或接口不声明任何字段,或者此 Class 对象表示一个基本类型、一个数组类或 void,则此方法返回一个长度为 0 的数组.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Class#getFields()}</td>
 * <td>返回一个包含某些 Field 对象的数组,这些对象反映此 Class 对象所表示的类或接口的所有 <span style="color:red">可访问公共字段</span>.
 * 
 * <p>
 * 特别地,如果该 Class 对象表示一个类,则此方法返回<span style="color:red">该类及其所有超类的公共字段</span>.<br>
 * 如果该 Class 对象表示一个接口,则此方法返回<span style="color:red">该接口及其所有超接口的公共字段</span>.
 * </p>
 * 
 * <b>返回数组中的元素没有排序,也没有任何特定的顺序.</b><br>
 * 如果类或接口没有可访问的公共字段,或者表示一个数组类、一个基本类型或 void,则此方法返回长度为 0 的数组. <br>
 * 该方法不反映数组类的隐式长度字段.用户代码应使用 Array 类的方法来操作数组.</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link Class#getDeclaredField(String)}</td>
 * <td>返回一个 Field 对象,该对象反映此 Class 对象所表示的类或接口的指定已声明字段.<br>
 * name 参数是一个 String,它指定所需字段的简称.<br>
 * 注意,此方法不反映数组类的 length 字段.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Class#getField(String)}</td>
 * <td>返回一个 Field 对象,它反映此 Class 对象所表示的类或接口的指定公共成员字段.name 参数是一个 String,用于指定所需字段的简称. <br>
 * 要反映的字段由下面的算法确定.设 C 为此对象所表示的类: <br>
 * 如果 C 声明一个带有指定名的公共字段,则它就是要反映的字段. <br>
 * 如果在第 1 步中没有找到任何字段,则该算法被递归地应用于 C 的每一个直接超接口.直接超接口按其声明顺序进行搜索. <br>
 * 如果在第 1、2 两步没有找到任何字段,且 C 有一个超类 S,则在 S 上递归调用该算法.如果 C 没有超类,则抛出 NoSuchFieldException.<br>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.reflect.FieldUtils
 * @see "org.springframework.util.ReflectionUtils"
 * @since 1.0.7
 */
public final class FieldUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtil.class);

    /** Don't let anyone instantiate this class. */
    private FieldUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // [start] Field

    /**
     * 获得这个对象 <code>obj</code> 所有字段的值(不是属性),key是 fieldName,value 是值.
     *
     * @param obj
     *            the obj
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldList</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see #getAllFields(Object)
     * @see #getAllFieldNameAndValueMap(Object, String[])
     * @see java.lang.reflect.Modifier#isPrivate(int)
     * @see java.lang.reflect.Modifier#isStatic(int)
     */
    public static Map<String, Object> getAllFieldNameAndValueMap(Object obj){
        return getAllFieldNameAndValueMap(obj, null);
    }

    /**
     * 获得这个对象 所有字段的值(不是属性),key是 fieldName,value 是值.
     *
     * @param obj
     *            the obj
     * @param excludeFieldNames
     *            需要排除的field names,如果传递过来是nullOrEmpty 那么不会判断
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldList</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see #getAllFieldList(Object, String[])
     * @see "org.apache.commons.lang3.reflect.MemberUtils#setAccessibleWorkaround(java.lang.reflect.AccessibleObject)"
     */
    public static Map<String, Object> getAllFieldNameAndValueMap(Object obj,String[] excludeFieldNames){
        List<Field> fieldList = getAllFieldList(obj, excludeFieldNames);

        if (Validator.isNullOrEmpty(fieldList)){
            return Collections.emptyMap();
        }
        Map<String, Object> map = new TreeMap<String, Object>();
        for (Field field : fieldList){
            //XXX see org.apache.commons.lang3.reflect.MemberUtils.setAccessibleWorkaround(AccessibleObject)
            field.setAccessible(true);
            try{
                map.put(field.getName(), field.get(obj));
            }catch (Exception e){
                throw new ReflectException(e);
            }
        }
        return map;
    }

    /**
     * 获得这个对象 <code>obj</code> 排除某些 <code>excludeFieldNames</code> 之后的字段list.
     * 
     * @param obj
     *            the obj
     * @param excludeFieldNames
     *            需要排除的field names,如果传递过来是nullOrEmpty 那么不会判断
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>getAllFields(obj)</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     * @see #getAllFields(Object)
     * @since 1.4.0
     */
    public static List<Field> getAllFieldList(Object obj,String[] excludeFieldNames){
        Validate.notNull(obj, "obj can't be null!");
        // 获得一个对象所有的声明字段(包括私有的,继承的)
        Field[] fields = getAllFields(obj);

        if (Validator.isNullOrEmpty(fields)){
            return Collections.emptyList();
        }

        List<Field> fieldList = new ArrayList<Field>();
        for (Field field : fields){
            String fieldName = field.getName();
            if (Validator.isNotNullOrEmpty(excludeFieldNames) && ArrayUtils.contains(excludeFieldNames, fieldName)){
                continue;
            }

            int modifiers = field.getModifiers();
            // 私有并且静态 一般是log
            boolean isPrivateAndStatic = Modifier.isPrivate(modifiers) && Modifier.isStatic(modifiers);
            LOGGER.debug("field name:[{}],modifiers:[{}],isPrivateAndStatic:[{}]", fieldName, modifiers, isPrivateAndStatic);

            if (!isPrivateAndStatic){
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    /**
     * 获得一个对象所有的声明字段 {@link java.lang.reflect.Field}(包括私有的 private,继承 inherited 的).
     * 
     * @param obj
     *            the obj
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     * @see java.lang.Class#getDeclaredFields()
     * @see java.lang.Class#getSuperclass()
     * @see java.lang.reflect.Field
     * @see org.apache.commons.lang3.ArrayUtils#addAll(boolean[], boolean...)
     * @see #getAllFields(Class)
     */
    private static Field[] getAllFields(Object obj){
        Validate.notNull(obj, "obj can't be null!");
        return getAllFields(obj.getClass());
    }

    /**
     * Gets all fields of the given class and its parents (if any)..
     *
     * @param klass
     *            the klass
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     * @see org.apache.commons.lang3.reflect.FieldUtils#getAllFields(Class)
     * @since 1.4.0
     */
    private static Field[] getAllFields(Class<?> klass){
        Validate.notNull(klass, "klass can't be null!");
        return org.apache.commons.lang3.reflect.FieldUtils.getAllFields(klass);
    }

    /**
     * 返回一个 Field对象,该对象反映此 Class对象所表示的类或接口的指定已声明字段.
     *
     * @param klass
     *            the klass
     * @param fieldName
     *            字段名称
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.lang.Class#getDeclaredField(String)
     * @see org.apache.commons.lang3.reflect.FieldUtils#getDeclaredField(Class, String)
     * @see org.apache.commons.lang3.reflect.FieldUtils#getDeclaredField(Class, String, boolean)
     */
    public static Field getDeclaredField(Class<?> klass,String fieldName){
        Validate.notNull(klass, "klass can't be null!");
        Validate.notEmpty(fieldName, "fieldName can't be null/empty!");
        try{
            return klass.getDeclaredField(fieldName);
        }catch (Exception e){
            throw new ReflectException(e);
        }
    }

    // [end]
    //**********************************************************************************************
    // [start] Property

    /**
     * 设置字段值.
     * 
     * <p>
     * 如果 <code>owner</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>fieldName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>fieldName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * </p>
     *
     * @param owner
     *            the owner
     * @param fieldName
     *            字段
     * @param value
     *            值
     * @see java.lang.Object#getClass()
     * @see java.lang.Class#getField(String)
     * @see java.lang.reflect.Field#set(Object, Object)
     * 
     * @see org.apache.commons.lang3.reflect.FieldUtils#writeField(Field, Object, Object, boolean)
     * @since 1.4.0
     */
    public static void setFieldValue(Object owner,String fieldName,Object value){
        Validate.notNull(owner, "owner can't be null!");
        Validate.notBlank(fieldName, "fieldName can't be blank!");
        try{
            Class<?> ownerClass = owner.getClass();
            Field field = ownerClass.getField(fieldName);
            field.set(ownerClass, value);
        }catch (Exception e){
            throw new ReflectException(e);
        }
    }

    //**********************************************************************************************
    /**
     * 得到某个对象 <code>owner</code> 的公共字段 <code>fieldName</code> 值.
     *
     * @param <T>
     *            the generic type
     * @param owner
     *            the owner
     * @param fieldName
     *            the field name
     * @return 如果 <code>owner</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.lang.Object#getClass()
     * @see #getClassFieldValue(Class, String, Object)
     * @since 1.4.0
     */
    public static <T> T getFieldValue(Object owner,String fieldName){
        Validate.notNull(owner, "owner can't be null!");
        Validate.notBlank(fieldName, "fieldName can't be blank!");
        Class<?> ownerClass = owner.getClass();
        return getClassFieldValue(ownerClass, fieldName, owner);
    }

    /**
     * 得到指定类 <code>className</code> 的静态公共字段 <code>fieldName</code> 值.
     * 
     * <pre class="code">
     * FieldUtil.getStaticFieldValue("com.feilong.core.HttpMethodType", "POST") = HttpMethodType.POST
     * </pre>
     * 
     * @param <T>
     *            the generic type
     * @param className
     *            类名,e.g com.feilong.io.ImageType
     * @param fieldName
     *            字段名
     * @return 如果 <code>className</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see ClassUtil#loadClass(String)
     * @see java.lang.Class#getField(String)
     * @see java.lang.reflect.Field#get(Object)
     * @see org.apache.commons.lang3.reflect.FieldUtils#getField(Class, String)
     * @since 1.4.0
     */
    public static <T> T getStaticFieldValue(String className,String fieldName){
        Validate.notNull(className, "className can't be null!");
        Validate.notBlank(fieldName, "fieldName can't be blank!");
        Class<?> ownerClass = ClassUtil.loadClass(className);
        return getClassFieldValue(ownerClass, fieldName, ownerClass);
    }
    // [end]

    /**
     * 获得指定类 <code>ownerClass</code> 指定字段 <code>fieldName</code>的 值.
     *
     * @param <T>
     *            the generic type
     * @param ownerClass
     *            the owner class
     * @param fieldName
     *            the field name
     * @param object
     *            the object
     * @return the class field value
     * @see java.lang.Class#getField(String)
     * @see java.lang.reflect.Field#get(Object)
     * @since 1.6.2
     */
    @SuppressWarnings("unchecked")
    private static <T> T getClassFieldValue(Class<?> ownerClass,String fieldName,Object object){
        try{
            Field field = ownerClass.getField(fieldName);
            return (T) field.get(object);
        }catch (Exception e){
            throw new ReflectException(Slf4jUtil.format("className:[{}],fieldName:[{}]", ownerClass.getName(), fieldName), e);
        }
    }
}