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
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.NumberConverter;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.iterators.EnumerationIterator;

/**
 * 转换器.
 * 
 * <h3>关于类型转换:</h3>
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
 * <h3>{@link ConvertUtils} 几个方法的区别:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link ConvertUtils#convert(Object)}</td>
 * <td>将指定的value转成string.<br>
 * 如果value是array, 将会返回数组第一个元素转成string. 将会使用注册的 <code>java.lang.String</code> {@link Converter},允许应用 定制 Object->String conversions (
 * 默认使用简单的使用 toString()) <br>
 * see {@link ConvertUtilsBean#convert(Object)}</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link ConvertUtils#convert(String, Class)}</td>
 * <td>将String value转成 指定Class 类型的对象 (如果可能),否则返回string. <br>
 * see {@link ConvertUtilsBean#convert(String, Class)}</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link ConvertUtils#convert(String[], Class)}</td>
 * <td>将数组转成指定class类型的对象. <br>
 * 如果指定的Class类型是数组类型, 那么返回值的类型将是数组的类型. 否则将会构造一个指定类型的数组返回. <br>
 * see {@link ConvertUtilsBean#convert(String[], Class)} <br>
 * see {@link #convert(String[], Class)}</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link ConvertUtils#convert(Object, Class)}</td>
 * <td>将value转成指定Class类型的对象,如果Class的转换器没有注册, 那么传入的value原样返回.<br>
 * see {@link ConvertUtilsBean#convert(Object, Class)}<br>
 * see {@link #convert(Object, Class)}</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <p>
 * standard {@link Converter} instances are provided for all of the following destination Classes:
 * </p>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>java.lang.BigDecimal</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.lang.BigInteger</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>boolean and java.lang.Boolean</td>
 * <td>default to false</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>byte and java.lang.Byte</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top">
 * <td>char and java.lang.Character</td>
 * <td>default to a space</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.lang.Class</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>double and java.lang.Double</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>float and java.lang.Float</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top">
 * <td>int and java.lang.Integer</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>long and java.lang.Long</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top">
 * <td>short and java.lang.Short</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.lang.String</td>
 * <td>default to null</td>
 * </tr>
 * <tr valign="top">
 * <td>java.io.File</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.net.URL</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>java.sql.Date</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.sql.Time</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>java.sql.Timestamp</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * </table>
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
     * 
     * <p>
     * you also can call {@link org.apache.commons.beanutils.converters.BooleanConverter#BooleanConverter(String[], String[], Object)
     * BooleanConverter(String[], String[], Object)} set trueStrings and falseStrings
     * </p>
     * </blockquote>
     * 
     * <h3>和 {@link Boolean#parseBoolean(String)}的区别:</h3>
     * 
     * <blockquote>
     * <p>
     * {@link Boolean#parseBoolean(String)}, 仅当 <code>(String != null) && String.equalsIgnoreCase("true")</code> 返回 true
     * </p>
     * </blockquote>
     * 
     * @param toBeConvertedValue
     *            object
     * @return boolean
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.BooleanConverter
     * @see java.lang.Boolean#parseBoolean(String)
     */
    public static Boolean toBoolean(Object toBeConvertedValue){
        return convert(toBeConvertedValue, Boolean.class);
    }

    /**
     * object转成integer类型.
     * 
     * <p>
     * converted is missing or an error occurs converting the value,<span style="color:red">return null</span>
     * </p>
     *
     * @param toBeConvertedValue
     *            值
     * @return the integer
     * @see org.apache.commons.beanutils.converters.IntegerConverter
     */
    public static Integer toInteger(Object toBeConvertedValue){
        IntegerConverter integerConverter = new IntegerConverter(null);
        return integerConverter.convert(Integer.class, toBeConvertedValue);
    }

    /**
     * object类型转换成 {@link java.math.BigDecimal}.
     * 
     * <p>
     * converted is missing or an error occurs converting the value,<span style="color:red">return null</span>
     * </p>
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
     * 本方法底层调用的是 {@link NumberConverter#toNumber(Class, Class, Number)
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
        BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
        return bigDecimalConverter.convert(BigDecimal.class, toBeConvertedValue);
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
     * <p>
     * 在将object转成集合 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToCollection(Class, Object)
     * ArrayConverter#convertToCollection(Class, Object)}时候,有以下规则:
     * </p>
     * </blockquote>
     * 
     * <ul>
     * <li>The string is expected to be a comma-separated list of values.</li>
     * <li>字符串可以被'{' and '}'分隔符包裹.</li>
     * <li>去除前后空白.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java escape sequences are
     * valid.</li>
     * </ul>
     * 
     * <p>
     * 默认:
     * </p>
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
     *
     * @param toBeConvertedValue
     *            参数值
     * @return the string
     * @see org.apache.commons.beanutils.converters.ArrayConverter#convertToString(Object)
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     * @see org.apache.commons.beanutils.ConvertUtilsBean#convert(Object)
     * @see org.apache.commons.beanutils.converters.StringConverter
     * 
     * @see java.util.Arrays#toString(Object[])
     */
    public static String toString(Object toBeConvertedValue){
        return convert(toBeConvertedValue, String.class);
    }

    /**
     * 把对象转换为long类型.
     * 
     * <p>
     * converted is missing or an error occurs converting the value,<span style="color:red">return null</span>
     * </p>
     *
     * @param toBeConvertedValue
     *            包含数字的对象.
     * @return the long
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.LongConverter
     */
    public static Long toLong(Object toBeConvertedValue){
        LongConverter longConverter = new LongConverter(null);
        return longConverter.convert(Long.class, toBeConvertedValue);
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
        return convert(toBeConvertedValue, Long[].class);
    }

    /**
     * 转成string数组.
     * 
     * <p>
     * Parse an incoming String of the form similar to an array initializer in the Java language into a <code>List</code> individual Strings
     * for each element, according to the following rules.
     * </p>
     * <ul>
     * <li>The string is expected to be a comma-separated list of values.</li>
     * <li>The string may optionally have matching '{' and '}' delimiters around the list.</li>
     * <li>Whitespace before and after each element is stripped.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java escape sequences are
     * valid.</li>
     * </ul>
     *
     * @param toBeConvertedValue
     *            the to be converted value
     * @return the string[]
     * @see org.apache.commons.beanutils.converters.AbstractArrayConverter#parseElements(String)
     * @since 1.3.1
     */
    public static String[] toStrings(Object toBeConvertedValue){
        return convert(toBeConvertedValue, String[].class);
    }

    /**
     * 支持将对象转成Iterator.
     * 
     * <h3>支持以下类型:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>逗号分隔的字符串,{@link ConvertUtil#toStrings(Object)} 转成数组</li>
     * <li>数组</li>
     * <li>{@link java.util.Map},将 {@link java.util.Map#values()} 转成{@link java.util.Iterator}</li>
     * <li>{@link java.util.Collection}</li>
     * <li>{@link java.util.Iterator}</li>
     * <li>{@link java.util.Enumeration}</li>
     * <li>{@link java.util.Dictionary}</li>
     * <li>{@link org.w3c.dom.Node}</li>
     * <li>{@link org.w3c.dom.NodeList}</li>
     * </ul>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param object
     *            <ul>
     *            <li>逗号分隔的字符串,{@link ConvertUtil#toStrings(Object)} 转成数组</li>
     *            <li>数组</li>
     *            <li>{@link java.util.Map},将 {@link java.util.Map#values()} 转成{@link java.util.Iterator}</li>
     *            <li>{@link java.util.Collection}</li>
     *            <li>{@link java.util.Iterator}</li>
     *            <li>{@link java.util.Enumeration}</li>
     *            <li>{@link java.util.Dictionary}</li>
     *            <li>{@link org.w3c.dom.Node}</li>
     *            <li>{@link org.w3c.dom.NodeList}</li>
     *            </ul>
     * @return <ul>
     *         <li>如果 null == object 返回null,</li>
     *         <li>否则转成 {@link IteratorUtils#getIterator(Object)}</li>
     *         </ul>
     * @see Collection#iterator()
     * @see EnumerationIterator#EnumerationIterator(Enumeration)
     * @see IteratorUtils#getIterator(Object)
     * @since Commons Collections4.0
     */
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> toIterator(Object object){
        if (null == object){
            return null;
        }
        // 逗号分隔的字符串
        if (object instanceof String){
            return toIterator(ConvertUtil.toStrings(object));
        }
        return (Iterator<T>) IteratorUtils.getIterator(object);
    }

    /**
     * 将value转成指定Class类型的对象,如果Class的转换器没有注册,那么传入的value原样返回..
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * 
     * <ul>
     * <li>如果传的 value是 <code>value.getClass().isArray()</code> 或者 {@link Collection},那么<span style="color:red">会取第一个元素</span>进行转换
     * {@link AbstractConverter#convert(Class, Object)} ,调用的 {@link AbstractConverter#convertArray(Object)} 方法</li>
     * </ul>
     * 
     * </blockquote>
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
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] convert(String[] values,Class<T> targetType){
        return (T[]) ConvertUtils.convert(values, targetType);
    }
}