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

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;

import com.feilong.core.lang.ObjectUtil;
import com.feilong.core.util.Validator;

/**
 * 转换器.
 * 
 * <h3>关于类型转换</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link ConvertUtilsBean#registerPrimitives(boolean) registerPrimitives(boolean throwException)}</li>
 * <li>{@link ConvertUtilsBean#registerStandard(boolean,boolean) registerStandard(boolean throwException, boolean defaultNull);}</li>
 * <li>{@link ConvertUtilsBean#registerOther(boolean) registerOther(boolean throwException);}</li>
 * <li>{@link ConvertUtilsBean#registerArrays(boolean,int) registerArrays(boolean throwException, int defaultArraySize);}</li>
 * </ul>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.2.3 2015年7月24日 下午7:43:33
 * @see org.apache.commons.beanutils.ConvertUtils
 * @since 1.2.3
 */
public final class ConvertUtil{

    /** Don't let anyone instantiate this class. */
    private ConvertUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * To long array.
     *
     * @param toBeConvertedValue
     *            the to be converted value
     * @return the long[]
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object, Class)
     */
    public static final Long[] toLongs(Object toBeConvertedValue){
        //        LongConverter elementConverter = new LongConverter(new Long(0L));
        //        return ConvertUtil.convert(elementConverter, toBeConvertedValue);
        return (Long[]) ConvertUtils.convert(toBeConvertedValue, Long[].class);
    }

    //***************************************************************************************

    /**
     * 把对象转换为long类型.
     * 
     * @param value
     *            包含数字的对象.
     * @return long 转换后的数值,对不能转换的对象返回null.
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Long toLong(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Long){
            return (Long) value;
        }
        return Long.parseLong(value.toString());
    }

    /**
     * 任意的数组转成Integer 数组.
     * 
     * @param objects
     *            objects
     * @return Validator.isNotNullOrEmpty(objects)则返回null<br>
     *         一旦其中有值转换不了integer,则出现参数异常
     * @deprecated 转成泛型
     */
    //TODO 转成泛型
    @Deprecated
    public static Integer[] toIntegers(Object[] objects){
        if (Validator.isNullOrEmpty(objects)){
            return null;
        }

        int length = objects.length;
        Integer[] integers = new Integer[length];
        for (int i = 0; i < length; i++){
            integers[i] = ConvertUtil.toInteger(objects[i]);
        }
        return integers;
    }

    /**
     * Convert.
     *
     * @param <T>
     *            the generic type
     * @param defaultArrayType
     *            默认的数组类型
     * @param individualArrayElementConverter
     *            单个元素的 {@link Converter}
     * @param toBeConvertedValue
     *            需要被转换的值
     * @return the t
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final <T> T convert(Class<T> defaultArrayType,Converter individualArrayElementConverter,Object toBeConvertedValue){
        //char[] allowedChars = new char[] { ',', '-' };
        char delimiter = ',';
        boolean onlyFirstToString = true;

        int defaultSize = 0;

        //**********************************************************
        ArrayConverter arrayConverter = new ArrayConverter(defaultArrayType, individualArrayElementConverter, defaultSize);
        //arrayConverter.setAllowedChars(allowedChars);
        arrayConverter.setDelimiter(delimiter);
        arrayConverter.setOnlyFirstToString(onlyFirstToString);

        T result = arrayConverter.convert(defaultArrayType, toBeConvertedValue);
        return result;
    }

    /**
     * Convert.
     *
     * @param <T>
     *            the generic type
     * @param individualArrayElementConverter
     *            the individual array element converter
     * @param toBeConvertedValue
     *            the to be converted value
     * @return the t
     */
    public static final <T> T convert(Converter individualArrayElementConverter,Object toBeConvertedValue){
        //if null will use default 
        //see org.apache.commons.beanutils.converters.AbstractConverter.convertToDefaultType(Class<T>, Object)
        Class<T> defaultArrayType = null;
        return convert(defaultArrayType, individualArrayElementConverter, toBeConvertedValue);

        //  ConvertUtilsBean convertUtils = beanUtilsBean.getConvertUtils();
        //return ConvertUtils.convert(toBeConvertedValue, targetType);
    }

    /**
     * object 类型转换成boolean类型.
     * 
     * @param object
     *            object
     * @return boolean
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Boolean toBoolean(Object object){
        if (null == object){
            throw new IllegalArgumentException("object can't be null/empty!");
        }
        return Boolean.parseBoolean(object.toString());
    }

    /**
     * object转成integer类型.
     *
     * @param value
     *            值
     * @return <ul>
     *         <li>如果value是null,则返回null</li>
     *         <li>如果value本身是Integer类型,则强制转换成 (Integer) value</li>
     *         <li>否则 new Integer(value.toString().trim())</li>
     *         <li>如果value不能转成integer 会抛出 IllegalArgumentException异常</li>
     *         </ul>
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Integer toInteger(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Integer){
            return (Integer) value;
        }

        try{
            return new Integer(ObjectUtil.trim(value));
        }catch (Exception e){
            throw new IllegalArgumentException("Input param:[\"" + value + "\"], convert to integer exception", e);
        }
    }

    /**
     * object类型转换成BigDecimal.
     * 
     * <h3>注意:对于 double 转成 BigDecimal:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * <span style="color:red">推荐使用 BigDecimal.valueOf</span>，不建议使用new BigDecimal(double)，参见 JDK API
     * new BigDecimal(0.1) ====&gt;   0.1000000000000000055511151231257827021181583404541015625
     * BigDecimal.valueOf(0.1) ====&gt;  0.1
     * </pre>
     * 
     * </blockquote>
     * 
     * @param value
     *            值
     * @return BigDecimal
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final BigDecimal toBigDecimal(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof BigDecimal){
            return (BigDecimal) value;
        }

        //对于 double 转成 BigDecimal，推荐使用 BigDecimal.valueOf，不建议使用new BigDecimal(double)，参见 JDK API
        //new BigDecimal(0.1) ====>   0.1000000000000000055511151231257827021181583404541015625
        //BigDecimal.valueOf(0.1) ====>  0.1

        //先转成string 就可以了
        return new BigDecimal(ObjectUtil.trim(value));
    }

    /**
     * Object to double.
     * 
     * @param value
     *            the value
     * @return the double
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Double toDouble(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Double){
            return (Double) value;
        }
        return new Double(value.toString());
    }

    /**
     * object to float.
     * 
     * @param value
     *            the value
     * @return the float
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Float toFloat(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Float){
            return (Float) value;
        }
        return new Float(value.toString());
    }

    /**
     * object to short.
     * 
     * @param value
     *            the value
     * @return the short
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Short toShort(Object value){
        if (Validator.isNullOrEmpty(value)){
            return null;
        }

        if (value instanceof Short){
            return (Short) value;
        }
        return new Short(value.toString());
    }

    /**
     * 把对象转换成字符串.
     * 
     * @param value
     *            参数值
     * @return <ul>
     *         <li>{@code (null == value) =====>return null}</li>
     *         <li>{@code (value instanceof String) =====>return (String) value}</li>
     *         <li>{@code return value.toString()}</li>
     *         <li>对于数组，将会调用 {@link java.util.Arrays#toString(Object[])}</li>
     *         </ul>
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final String toString(Object value){
        if (null == value){
            return null;
        }
        if (value instanceof String){
            return (String) value;
        }
        if (value instanceof Object[]){
            return Arrays.toString((Object[]) value);
        }

        //***************************************************************
        // primitive ints
        if (value instanceof int[]){
            return Arrays.toString((int[]) value);
        }

        // primitive long
        if (value instanceof long[]){
            return Arrays.toString((long[]) value);
        }

        // primitive float
        if (value instanceof float[]){
            return Arrays.toString((float[]) value);
        }

        // primitive double
        if (value instanceof double[]){
            return Arrays.toString((double[]) value);
        }

        // primitive char
        if (value instanceof char[]){
            return Arrays.toString((char[]) value);
        }

        // primitive boolean
        if (value instanceof boolean[]){
            return Arrays.toString((boolean[]) value);
        }

        // primitive byte
        if (value instanceof byte[]){
            return Arrays.toString((byte[]) value);
        }

        // primitive short
        if (value instanceof short[]){
            return Arrays.toString((short[]) value);
        }
        return value.toString();
    }

    /**
     * 将Object 类型值转成泛型,一般用于配置文件读取数据.
     * 
     * @param <T>
     *            the generic type
     * @param value
     *            the value
     * @param klass
     *            the class1
     * @return if null==value return null,else to class convert<br>
     *         如果不是内置的class,将使用强制转换 (T) value
     * @deprecated will Re-structure
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    // XXX
    public static final <T> T toT(Object value,Class<T> klass){
        if (null == value){
            return null;
        }
        if (klass == String.class){
            return (T) toString(value);
        }else if (klass == Boolean.class){
            return (T) toBoolean(value);
        }else if (klass == Integer.class){
            return (T) toInteger(value);
        }else if (klass == BigDecimal.class){
            return (T) toBigDecimal(value);
        }else if (klass == Long.class){
            return (T) toLong(value);
        }else if (klass == Double.class){
            return (T) toDouble(value);
        }else if (klass == Float.class){
            return (T) toFloat(value);
        }else if (klass == Short.class){
            return (T) toShort(value);
        }
        return (T) value;
    }
}
