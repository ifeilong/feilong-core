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

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;

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
 * @version 1.3.0 2015年7月24日 下午7:43:33
 * @see org.apache.commons.beanutils.ConvertUtils
 * @see org.apache.commons.beanutils.converters.AbstractConverter#handleMissing(Class)
 * @see org.apache.commons.beanutils.locale.LocaleConvertUtils
 * @since 1.3.0
 */
public final class ConvertUtil{

    /** Don't let anyone instantiate this class. */
    private ConvertUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * object 类型转换成boolean类型.
     * 
     * @param toBeConvertedValue
     *            object
     * @return boolean
     * @see #convert(Object, Class)
     */
    public static final Boolean toBoolean(Object toBeConvertedValue){
        //        if (null == toBeConvertedValue){
        //            throw new IllegalArgumentException("object can't be null/empty!");
        //        }
        //        return Boolean.parseBoolean(toBeConvertedValue.toString());
        return convert(toBeConvertedValue, Boolean.class);
    }

    /**
     * object转成integer类型.
     *
     * @param toBeConvertedValue
     *            值
     * @return the integer
     */
    public static final Integer toInteger(Object toBeConvertedValue){
        //        if (Validator.isNullOrEmpty(toBeConvertedValue)){
        //            return null;
        //        }
        //
        //        if (toBeConvertedValue instanceof Integer){
        //            return (Integer) toBeConvertedValue;
        //        }
        //
        //        try{
        //            return new Integer(ObjectUtil.trim(toBeConvertedValue));
        //        }catch (Exception e){
        //            throw new IllegalArgumentException("Input param:[\"" + toBeConvertedValue + "\"], convert to integer exception", e);
        //        }
        return convert(toBeConvertedValue, Integer.class);
    }

    /**
     * object类型转换成 {@link java.math.BigDecimal}.
     * 
     * <h3>{@link java.lang.Double} 转成 {@link java.math.BigDecimal}注意点:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <span style="color:red">推荐使用 {@link BigDecimal#valueOf(double)}</span>，不建议使用 {@code new BigDecimal(double)}，参见 JDK API<br>
     * </p>
     * 
     * <ul>
     * <li>{@code new BigDecimal(0.1) ====> 0.1000000000000000055511151231257827021181583404541015625}</li>
     * <li>{@code BigDecimal.valueOf(0.1) ====> 0.1}</li>
     * </ul>
     * 
     * 本方法底层调用的是 {@link org.apache.commons.beanutils.converters.NumberConverter#toNumber(Class, Class, Number)
     * NumberConverter#toNumber(Class, Class, Number)},正确的处理了 {@link java.lang.Double} 转成 {@link java.math.BigDecimal} </blockquote>
     * 
     * @param toBeConvertedValue
     *            值
     * @return BigDecimal
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.NumberConverter#toNumber(Class, Class, Number)
     */
    public static final BigDecimal toBigDecimal(Object toBeConvertedValue){
        //        if (Validator.isNullOrEmpty(toBeConvertedValue)){
        //            return null;
        //        }
        //
        //        if (toBeConvertedValue instanceof BigDecimal){
        //            return (BigDecimal) toBeConvertedValue;
        //        }
        //
        //        //先转成string 就可以了
        //        return new BigDecimal(ObjectUtil.trim(toBeConvertedValue));
        return convert(toBeConvertedValue, BigDecimal.class);
    }

    /**
     * Object to double.
     * 
     * @param toBeConvertedValue
     *            the value
     * @return the double
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Double toDouble(Object toBeConvertedValue){
        //        if (Validator.isNullOrEmpty(toBeConvertedValue)){
        //            return null;
        //        }
        //
        //        if (toBeConvertedValue instanceof Double){
        //            return (Double) toBeConvertedValue;
        //        }
        //        return new Double(toBeConvertedValue.toString());
        return convert(toBeConvertedValue, Double.class);
    }

    /**
     * object to float.
     * 
     * @param toBeConvertedValue
     *            the value
     * @return the float
     * @see org.apache.commons.beanutils.converters.FloatConverter
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Float toFloat(Object toBeConvertedValue){
        //        if (Validator.isNullOrEmpty(toBeConvertedValue)){
        //            return null;
        //        }
        //
        //        if (toBeConvertedValue instanceof Float){
        //            return (Float) toBeConvertedValue;
        //        }
        //        return new Float(toBeConvertedValue.toString());
        return convert(toBeConvertedValue, Float.class);
    }

    /**
     * object to short.
     * 
     * @param toBeConvertedValue
     *            the value
     * @return the short
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final Short toShort(Object toBeConvertedValue){
        //        if (Validator.isNullOrEmpty(toBeConvertedValue)){
        //            return null;
        //        }
        //
        //        if (toBeConvertedValue instanceof Short){
        //            return (Short) toBeConvertedValue;
        //        }
        //        return new Short(toBeConvertedValue.toString());
        return convert(toBeConvertedValue, Short.class);
    }

    /**
     * 把对象转换成字符串.
     *
     * @param toBeConvertedValue
     *            参数值
     * @return the string
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final String toString(Object toBeConvertedValue){
        //        if (null == toBeConvertedValue){
        //            return null;
        //        }
        //        if (toBeConvertedValue instanceof String){
        //            return (String) toBeConvertedValue;
        //        }
        //        if (toBeConvertedValue instanceof Object[]){
        //            return Arrays.toString((Object[]) toBeConvertedValue);
        //        }
        //
        //        //***************************************************************
        //        // primitive ints
        //        if (toBeConvertedValue instanceof int[]){
        //            return Arrays.toString((int[]) toBeConvertedValue);
        //        }
        //
        //        // primitive long
        //        if (toBeConvertedValue instanceof long[]){
        //            return Arrays.toString((long[]) toBeConvertedValue);
        //        }
        //
        //        // primitive float
        //        if (toBeConvertedValue instanceof float[]){
        //            return Arrays.toString((float[]) toBeConvertedValue);
        //        }
        //
        //        // primitive double
        //        if (toBeConvertedValue instanceof double[]){
        //            return Arrays.toString((double[]) toBeConvertedValue);
        //        }
        //
        //        // primitive char
        //        if (toBeConvertedValue instanceof char[]){
        //            return Arrays.toString((char[]) toBeConvertedValue);
        //        }
        //
        //        // primitive boolean
        //        if (toBeConvertedValue instanceof boolean[]){
        //            return Arrays.toString((boolean[]) toBeConvertedValue);
        //        }
        //
        //        // primitive byte
        //        if (toBeConvertedValue instanceof byte[]){
        //            return Arrays.toString((byte[]) toBeConvertedValue);
        //        }
        //
        //        // primitive short
        //        if (toBeConvertedValue instanceof short[]){
        //            return Arrays.toString((short[]) toBeConvertedValue);
        //        }
        //        return toBeConvertedValue.toString();
        return convert(toBeConvertedValue, String.class);
    }

    /**
     * 把对象转换为long类型.
     * 
     * @param toBeConvertedValue
     *            包含数字的对象.
     * @return long 转换后的数值,对不能转换的对象返回null.
     * @see #convert(Object, Class)
     */
    public static final Long toLong(Object toBeConvertedValue){
        //        if (Validator.isNullOrEmpty(toBeConvertedValue)){
        //            return null;
        //        }
        //
        //        if (toBeConvertedValue instanceof Long){
        //            return (Long) toBeConvertedValue;
        //        }
        //        return Long.parseLong(toBeConvertedValue.toString());
        return convert(toBeConvertedValue, Long.class);
    }

    /**
     * 任意的数组转成Integer 数组.
     *
     * @param toBeConvertedValue
     *            the to be converted value
     * @return the integer[]
     * @see #convert(Object, Class)
     */
    public static Integer[] toIntegers(Object toBeConvertedValue){
        return convert(toBeConvertedValue, Integer[].class);
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
        //return (Long[]) ConvertUtils.convert(toBeConvertedValue,);
        return convert(toBeConvertedValue, Long[].class);
    }

    /**
     * Convert.
     *
     * @param <T>
     *            the generic type
     * @param value
     *            the value
     * @param targetType
     *            the target type
     * @return the t
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.AbstractConverter#convert(Class, Object)
     */
    @SuppressWarnings("unchecked")
    public static final <T> T convert(Object value,Class<T> targetType){
        return (T) ConvertUtils.convert(value, targetType);
    }
}