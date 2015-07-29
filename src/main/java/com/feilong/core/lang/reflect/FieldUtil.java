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
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtil;
import com.feilong.core.util.ArrayUtil;
import com.feilong.core.util.Validator;

/**
 * Utilities for working with Fields by reflection. <br>
 * Adapted and refactored from the dormant [reflect] Commons sandbox component.
 * 
 * The ability is provided to break the scoping restrictions coded by the programmer.<br>
 * This can allow fields to be changed that shouldn't be.
 * This facility should be used with care.
 * 
 * @author feilong
 * @version 1.0.7 2014年7月15日 下午1:08:15
 * @see org.apache.commons.lang3.reflect.FieldUtils
 * @see "org.springframework.util.ReflectionUtils"
 * @since 1.0.7
 */
//TODO 改写这里面的方法
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
     * 获得这个对象 所有字段的值(不是属性),key是 fieldName，value 是值.
     *
     * @param obj
     *            the obj
     * @return the field value map,key是 fieldName，value 是值
     * @see #getDeclaredFields(Object)
     * @see #getFieldValueMap(Object, String[])
     * @see java.lang.reflect.Modifier#isPrivate(int)
     * @see java.lang.reflect.Modifier#isStatic(int)
     */
    public static Map<String, Object> getFieldValueMap(Object obj){
        return getFieldValueMap(obj, null);
    }

    /**
     * 获得这个对象 所有字段的值(不是属性),key是 fieldName，value 是值.
     *
     * @param obj
     *            the obj
     * @param excludeFieldNames
     *            需要排除的field names,如果传递过来是nullOrEmpty 那么不会判断
     * @return the field value map
     */
    public static Map<String, Object> getFieldValueMap(Object obj,String[] excludeFieldNames){

        // 获得一个对象所有的声明字段(包括私有的,继承的)
        Field[] fields = getDeclaredFields(obj);

        Map<String, Object> map = new TreeMap<String, Object>();
        if (Validator.isNotNullOrEmpty(fields)){
            for (Field field : fields){
                String fieldName = field.getName();

                if (Validator.isNotNullOrEmpty(excludeFieldNames) && ArrayUtil.isContain(excludeFieldNames, fieldName)){
                    continue;
                }

                int modifiers = field.getModifiers();
                // 私有并且静态 一般是log
                boolean isPrivateAndStatic = Modifier.isPrivate(modifiers) && Modifier.isStatic(modifiers);
                LOGGER.debug("field name:[{}],modifiers:[{}],isPrivateAndStatic:[{}]", fieldName, modifiers, isPrivateAndStatic);

                if (!isPrivateAndStatic){
                    //TODO see org.apache.commons.lang3.reflect.MemberUtils.setAccessibleWorkaround(AccessibleObject)
                    field.setAccessible(true);
                    try{
                        map.put(fieldName, field.get(obj));
                    }catch (Exception e){
                        LOGGER.error(e.getClass().getName(), e);
                        throw new ReflectException(e);
                    }
                }
            }
        }
        return map;
    }

    /**
     * 获得一个对象所有的声明字段 {@link java.lang.reflect.Field}(包括私有的 private,继承 inherited 的).
     * 
     * @param obj
     *            the obj
     * @return the declared fields
     * @see java.lang.Class#getDeclaredFields()
     * @see java.lang.Class#getSuperclass()
     * @see java.lang.reflect.Field
     * @see org.apache.commons.lang3.ArrayUtils#addAll(boolean[], boolean...)
     */
    private static Field[] getDeclaredFields(Object obj){
        Class<?> klass = obj.getClass();
        Class<?> superClass = klass.getSuperclass();

        //返回Class对象所代表的类或接口中所有成员变量(不限于public)
        Field[] fields = klass.getDeclaredFields();
        do{
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("current class:[{}],super class:[{}]", klass.getName(), superClass.getName());
            }
            fields = ArrayUtils.addAll(fields, superClass.getDeclaredFields());
            superClass = superClass.getSuperclass();

        }while (null != superClass && superClass != Object.class);

        return fields;
    }

    /**
     * 返回 Field 对象的一个数组，这些对象反映此 Class对象所表示的类或接口,声明的所有字段.
     * 
     * <pre>
     * 包括public,protected,默认,private字段，
     * 但不包括继承的字段.
     * 
     * 返回数组中的元素没有排序，也没有任何特定的顺序.
     * 如果该类或接口不声明任何字段，或者此 Class 对象表示一个基本类型、一个数组类或 void，则此方法返回一个长度为 0 的数组.
     * </pre>
     * 
     * @param clz
     *            the clz
     * @return 包括public,protected,默认,private字段，但不包括继承的字段.
     * @see java.lang.Class#getDeclaredFields()
     * @see #getFieldsNames(Field[])
     */
    public static String[] getDeclaredFieldNames(Class<?> clz){
        Field[] declaredFields = clz.getDeclaredFields();
        return getFieldsNames(declaredFields);
    }

    /**
     * 反映此 Class 对象所表示的类或接口的所有可访问公共字段(public属性).<br>
     * 元素没有排序，也没有任何特定的顺序<br>
     * 如果类或接口没有可访问的公共字段，或者表示一个数组类、一个基本类型或 void，则此方法返回长度为 0 的数组. <br>
     * 特别地，如果该 Class 对象表示一个类，则此方法返回该类及其所有超类的公共字段.<br>
     * 如果该 Class 对象表示一个接口，则此方法返回该接口及其所有超接口的公共字段. <br>
     * 
     * @param clz
     *            the clz
     * @return the field names
     * @see Class#getFields()
     * @see #getFieldsNames(Field[])
     */
    public static String[] getFieldNames(Class<?> clz){
        Field[] fields = clz.getFields();
        return getFieldsNames(fields);
    }

    /**
     * 获得Field[] fields,每个field name 拼成数组.
     * 
     * @param fields
     *            the fields
     * @return 如果 fields isNullOrEmpty,返回 null;否则取field name,合为数组返回
     * @see java.lang.reflect.Field#getName()
     */
    private static String[] getFieldsNames(Field[] fields){
        if (Validator.isNullOrEmpty(fields)){
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        String[] fieldNames = new String[fields.length];
        for (int j = 0; j < fields.length; ++j){
            fieldNames[j] = fields[j].getName();
        }
        return fieldNames;
    }

    /**
     * 返回一个 Field 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明字段.
     *
     * @param clz
     *            clz
     * @param name
     *            属性名称
     * @return 返回一个 Field 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明字段
     * @see java.lang.Class#getDeclaredField(String)
     */
    public static Field getDeclaredField(Class<?> clz,String name){
        try{
            return clz.getDeclaredField(name);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new ReflectException(e);
        }
    }

    // [end]

    // [start] Property

    /**
     * 设置属性.
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
     */
    public static void setProperty(Object owner,String fieldName,Object value){
        try{
            Class<?> ownerClass = owner.getClass();
            Field field = ownerClass.getField(fieldName);
            field.set(ownerClass, value);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new ReflectException(e);
        }
    }

    /**
     * 得到某个对象的公共属性.
     *
     * @param <T>
     *            the generic type
     * @param owner
     *            the owner
     * @param fieldName
     *            the field name
     * @return 该属性对象
     * @see java.lang.Object#getClass()
     * @see java.lang.Class#getField(String)
     * @see java.lang.reflect.Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object owner,String fieldName){
        try{
            Class<?> ownerClass = owner.getClass();
            Field field = ownerClass.getField(fieldName);
            return (T) field.get(owner);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new ReflectException(e);
        }
    }

    /**
     * 得到某类的静态公共属性.
     * 
     * <pre>
     * {@code
     * example1 :
     * 获得 IOConstants类的 GB静态属性
     * FieldUtil.getStaticProperty("com.feilong.core.io.IOConstants", "GB")
     * 返回 :1073741824
     * }
     * </pre>
     *
     * @param <T>
     *            the generic type
     * @param className
     *            类名
     * @param fieldName
     *            属性名
     * @return 该属性对象
     * @see com.feilong.core.lang.ClassUtil#loadClass(String)
     * @see java.lang.Class#getField(String)
     * @see java.lang.reflect.Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getStaticProperty(String className,String fieldName){
        try{
            Class<?> ownerClass = ClassUtil.loadClass(className);
            Field field = ownerClass.getField(fieldName);
            return (T) field.get(ownerClass);
        }catch (Exception e){
            LOGGER.error(e.getClass().getName(), e);
            throw new ReflectException(e);
        }
    }

    // [end]
}