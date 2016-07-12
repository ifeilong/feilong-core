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

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.HttpMethodTestType;
import com.feilong.core.TimeInterval;
import com.feilong.core.lang.ClassUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

import static com.feilong.core.TimeInterval.SECONDS_PER_WEEK;
import static com.feilong.core.Validator.isNullOrEmpty;

/**
 * The Class FieldUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.7
 */
public class FieldUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtilTemp.class);

    /**
     * Test get static property1.
     */
    @Test
    public void testGetStaticProperty1(){
        assertEquals(HttpMethodTestType.POST, getStaticFieldValue(HttpMethodTestType.class.getName(), "POST"));
        assertEquals(SECONDS_PER_WEEK, getStaticFieldValue(TimeInterval.class.getName(), "SECONDS_PER_WEEK"));
    }

    /**
     * Test get static property2.
     */
    @Test(expected = NullPointerException.class)
    public void testGetStaticProperty2(){
        getStaticFieldValue(null, "POST");
    }

    /**
     * Test get static property3.
     */
    @Test(expected = NullPointerException.class)
    public void testGetStaticProperty3(){
        getStaticFieldValue(HttpMethodTestType.class.getName(), null);
    }

    /**
     * Test get static property4.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetStaticProperty4(){
        getStaticFieldValue(HttpMethodTestType.class.getName(), "  ");
    }

    /**
     * 获得Field[] fields,每个field name 拼成数组.
     * 
     * @param fields
     *            the fields
     * @return 如果 <code>fields</code> 是null或者empty,返回 {@link ArrayUtils#EMPTY_STRING_ARRAY}<br>
     * @see java.lang.reflect.Field#getName()
     */
    public static String[] getFieldsNames(Field[] fields){
        if (isNullOrEmpty(fields)){
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        String[] fieldNames = new String[fields.length];
        for (int j = 0; j < fields.length; ++j){
            fieldNames[j] = fields[j].getName();
        }
        return fieldNames;
    }

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

    /**
     * 返回一个 {@link Field}对象,该对象反映此 Class对象所表示的类或接口的指定已声明字段.
     *
     * @param klass
     *            the klass
     * @param fieldName
     *            字段名称
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.lang.Class#getDeclaredField(String)
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

        Class<?> ownerClass = ClassUtil.getClass(className);
        return getFieldValue(ownerClass, fieldName, ownerClass);
    }

    /**
     * 获得指定类 <code>ownerClass</code> 指定字段 <code>fieldName</code>的 值.
     *
     * @param <T>
     *            the generic type
     * @param ownerClass
     *            the owner class
     * @param fieldName
     *            the field name
     * @param ownerObj
     *            object from which the represented field's value is to be extracted
     * @return the class field value
     * @see org.apache.commons.lang3.reflect.FieldUtils#getField(Class, String, boolean)
     * @since 1.7.1
     */
    @SuppressWarnings("unchecked")
    private static <T> T getFieldValue(Class<?> ownerClass,String fieldName,Object ownerObj){
        try{
            //注意, 不能直接用 Class#getField(String), 他只能取 public的字段
            Field field = FieldUtils.getField(ownerClass, fieldName, true);
            return (T) field.get(ownerObj);
        }catch (Exception e){
            String message = Slf4jUtil.format("ownerClass:[{}],fieldName:[{}],ownerObj:[{}]", ownerClass.getName(), fieldName, ownerObj);
            LOGGER.error(message, e);
            throw new ReflectException(message, e);
        }
    }
}
