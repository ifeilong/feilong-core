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
import org.apache.commons.beanutils.converters.BooleanConverter;

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
     * <h3>逻辑或者规则:</h3>
     * 
     * <blockquote>
     * 
     * <ul>
     * <li>if "true", "yes", "y", "on", "1", will return true</li>
     * <li>if "false", "no", "n", "off", "0", will return false</li>
     * <li>else will throw conversionException, but in
     * {@link org.apache.commons.beanutils.converters.AbstractConverter#handleError(Class, Object, Throwable) handleError(Class, Object,
     * Throwable)} method will return default value, {@link BooleanConverter} defaultValue pls see
     * {@link org.apache.commons.beanutils.ConvertUtilsBean#registerStandard(boolean, boolean) registerStandard(boolean, boolean)}</li>
     * </ul>
     * </blockquote>
     * 
     * <p>
     * you also can call {@link org.apache.commons.beanutils.converters.BooleanConverter#BooleanConverter(String[], String[], Object)
     * BooleanConverter(String[], String[], Object)} set trueStrings and falseStrings
     * </p>
     * 
     * <p>
     * {@link java.lang.Boolean#parseBoolean(String)}, only if <code>(String != null) && String.equalsIgnoreCase("true")</code> return true
     * </p>
     * 
     * @param toBeConvertedValue
     *            object
     * @return boolean
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.BooleanConverter
     * @see java.lang.Boolean#parseBoolean(String)
     */
    public static Boolean toBoolean(Object toBeConvertedValue){
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
     * @see org.apache.commons.beanutils.converters.IntegerConverter
     */
    public static Integer toInteger(Object toBeConvertedValue){
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
     * @see org.apache.commons.beanutils.converters.BigDecimalConverter
     */
    public static BigDecimal toBigDecimal(Object toBeConvertedValue){
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
     * 把对象转换成字符串.
     * 
     * <h3>对于 Array 转成 String</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 参见 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToString(Object) ArrayConverter#convertToString(Object)} <br>
     * 
     * 在转换的过程中, 如果发现 object 是数组,将使用 {@link java.lang.reflect.Array#get(Object, int) Array#get(Object, int)} 来获得数据,<br>
     * 如果发现不是数组, 将会将object转成集合 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToCollection(Class, Object)
     * ArrayConverter#convertToCollection(Class, Object)} 再转成 迭代器 {@link java.util.Collection#iterator()}
     * </p>
     * 
     * 
     * <p>
     * 在将object转成集合 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToCollection(Class, Object)
     * ArrayConverter#convertToCollection(Class, Object)}时候,有以下规则:
     * </p>
     * 
     * <ul>
     * <li>The string is expected to be a comma-separated list of values.</li>
     * <li>字符串可以被'{' and '}'分隔符包裹.</li>
     * <li>去除前后空白.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java escape sequences are
     * valid.</li>
     * </ul>
     * 
     * 
     * <p>
     * 默认:
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * <tr valign="top">
     * <td>int defaultSize</td>
     * <td>指定构建的默认数组的大小 or if less than zero indicates that a <code>null</code> default value should be used.</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>char delimiter = ','</td>
     * <td>分隔符,转成的string中的元素分隔符</td>
     * </tr>
     * <tr valign="top">
     * <td>char[] allowedChars = new char[] {'.', '-'}</td>
     * <td>用于{@link java.io.StreamTokenizer}分隔字符串</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>boolean onlyFirstToString = true;</td>
     * <td>只转第一个值</td>
     * </tr>
     * </table>
     * </blockquote>
     * </p>
     * </blockquote>
     *
     * @param toBeConvertedValue
     *            参数值
     * @return the string
     * @see org.apache.commons.beanutils.converters.ArrayConverter#convertToString(Object)
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     * @see org.apache.commons.beanutils.converters.StringConverter
     * @deprecated will Re-structure
     */
    @Deprecated
    public static String toString(Object toBeConvertedValue){
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
     * @see org.apache.commons.beanutils.converters.LongConverter
     */
    public static Long toLong(Object toBeConvertedValue){
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
     * @see org.apache.commons.beanutils.converters.ArrayConverter
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
     * @see org.apache.commons.beanutils.converters.ArrayConverter
     */
    public static Long[] toLongs(Object toBeConvertedValue){
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
    public static <T> T convert(Object value,Class<T> targetType){
        return (T) ConvertUtils.convert(value, targetType);
    }

    /**
     * Convert an array of specified values to an array of objects of the specified class (if possible).
     * 
     * <p>
     * If the specified Java class is itself an array class, this class will be the type of the returned value.<br>
     * Otherwise, an array will be constructed whose component type is the specified class.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param values
     *            the values
     * @param targetType
     *            the target type
     * @return the t[]
     * @see org.apache.commons.beanutils.ConvertUtils#convert(String[], Class)
     * @see org.apache.commons.beanutils.ConvertUtilsBean#convert(String[], Class)
     * @since 1.3.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] convert(String[] values,Class<T> targetType){
        return (T[]) ConvertUtils.convert(values, targetType);
    }
}