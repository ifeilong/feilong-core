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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.NumberConverter;
import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.iterators.EnumerationIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import com.feilong.core.Validator;
import com.feilong.core.lang.ArrayUtil;
import com.feilong.core.lang.StringUtil;
import com.feilong.core.util.MapUtil;

/**
 * 常用类型转换处理.
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
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">方法</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link ConvertUtils#convert(Object)}</td>
 * <td>将指定的value转成string.<br>
 * 如果value是array,将会返回数组第一个元素转成string.<br>
 * 将会使用注册的 <code>java.lang.String</code>{@link Converter},<br>
 * 允许应用定制 Object{@code ->}String conversions(默认使用简单的使用 toString())<br>
 * see {@link ConvertUtilsBean#convert(Object)}</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link ConvertUtils#convert(String, Class)}</td>
 * <td>将String value转成 指定Class 类型的对象 (如果可能),否则返回string.<br>
 * see {@link ConvertUtilsBean#convert(String, Class)}</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link ConvertUtils#convert(String[], Class)}</td>
 * <td>将数组转成指定class类型的对象. <br>
 * 如果指定的Class类型是数组类型,那么返回值的类型将是数组的类型.否则将会构造一个指定类型的数组返回.<br>
 * see {@link ConvertUtilsBean#convert(String[], Class)} <br>
 * see {@link #toArray(String[], Class)}</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link ConvertUtils#convert(Object, Class)}</td>
 * <td>将value转成指定Class类型的对象,如果Class的转换器没有注册,那么传入的value原样返回.<br>
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
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.math.BigDecimal}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.math.BigInteger}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>boolean and {@link java.lang.Boolean}</td>
 * <td>default to false</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>byte and {@link java.lang.Byte}</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top">
 * <td>char and {@link java.lang.Character}</td>
 * <td>default to a space</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.lang.Class}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>double and {@link java.lang.Double}</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>float and {@link java.lang.Float}</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top">
 * <td>int and {@link java.lang.Integer}</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>long and {@link java.lang.Long}</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top">
 * <td>short and {@link java.lang.Short}</td>
 * <td>default to zero</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.lang.String}</td>
 * <td>default to null</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.io.File}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.net.URL}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.sql.Date}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.sql.Time}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.sql.Timestamp}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
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
     * 将 <code>toBeConvertedValue</code> 转换成 {@link Boolean}类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * ConvertUtil.toBoolean(1L)        =   true
     * ConvertUtil.toBoolean("1")       =   true
     * ConvertUtil.toBoolean("9")       =   false
     * ConvertUtil.toBoolean(null)      =   null
     * ConvertUtil.toBoolean("1,2,3")   =   false
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>逻辑或者规则:</h3>
     * 
     * <blockquote>
     * 
     * <ul>
     * <li>如果 "true", "yes", "y", "on", "1", 返回 true</li>
     * <li>如果 "false", "no", "n", "off", "0", 返回 false</li>
     * <li>其他抛出 conversionException, 但是在
     * {@link org.apache.commons.beanutils.converters.AbstractConverter#handleError(Class, Object, Throwable) handleError(Class, Object,
     * Throwable)} 方法里面返回默认值, {@link BooleanConverter} 的默认值,参见
     * {@link org.apache.commons.beanutils.ConvertUtilsBean#registerStandard(boolean, boolean) registerStandard(boolean, boolean)}</li>
     * </ul>
     * 
     * <p>
     * 你也可以调用 {@link org.apache.commons.beanutils.converters.BooleanConverter#BooleanConverter(String[], String[], Object)
     * BooleanConverter(String[], String[], Object)} 设置 trueStrings 和 falseStrings
     * </p>
     * </blockquote>
     * 
     * <h3>和 {@link Boolean#parseBoolean(String)}的区别:</h3>
     * 
     * <blockquote>
     * <p>
     * {@link Boolean#parseBoolean(String)},仅当 <code>(String != null) 并且 String.equalsIgnoreCase("true")</code> 返回 true
     * </p>
     * </blockquote>
     * 
     * @param toBeConvertedValue
     *            object
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回null<br>
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.BooleanConverter
     * @see org.apache.commons.lang3.BooleanUtils
     * @see java.lang.Boolean#parseBoolean(String)
     */
    public static Boolean toBoolean(Object toBeConvertedValue){
        return convert(toBeConvertedValue, Boolean.class);
    }

    /**
     * 将 <code>toBeConvertedValue</code> 转换成 {@link Integer}类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * ConvertUtil.toInteger(null)                  = null
     * ConvertUtil.toInteger("aaaa")                = null
     * ConvertUtil.toInteger(8L)                    = 8
     * ConvertUtil.toInteger("8")                   = 8
     * ConvertUtil.toInteger(new BigDecimal("8"))   = 8
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * 该方法非常适用于获取请求的分页参数
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * 原来的写法:
     * 
     * <pre class="code">
     * 
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     try{
     *         int pageNo = Integer.parseInt(pageNoString);
     *         return pageNo;
     *     }catch (Exception e){
     *         LOGGER.error(e.getClass().getName(), e);
     *     }
     *     return 1; // 不带这个参数 或者转换异常 返回1
     * }
     * 
     * </pre>
     * 
     * 现在可以更改成:
     * 
     * <pre class="code">
     * 
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     Integer pageNo = ConvertUtil.toInteger(pageNoString);
     *     return null == pageNo ? 1 : pageNo;
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param toBeConvertedValue
     *            值
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     *         如果找不到转换器或者转换的时候出现了异常,返回 null
     * @see org.apache.commons.beanutils.converters.IntegerConverter
     * @see org.apache.commons.lang3.math.NumberUtils#toInt(String)
     * @see #toInteger(Object, Integer)
     */
    public static Integer toInteger(Object toBeConvertedValue){
        return toInteger(toBeConvertedValue, null);
    }

    /**
     * 将 <code>toBeConvertedValue</code> 转换成 {@link Integer}类型,如果转换不了返回默认值 <code>defaultValue</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * ConvertUtil.toInteger(null,1)                  = 1
     * ConvertUtil.toInteger("aaaa",1)                = 1
     * ConvertUtil.toInteger(8L,1)                    = 8
     * ConvertUtil.toInteger("8",1)                   = 8
     * ConvertUtil.toInteger(new BigDecimal("8"),1)   = 8
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * 该方法非常适用于获取请求的分页参数
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * 原来的写法:
     * 
     * <pre class="code">
     * 
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     try{
     *         int pageNo = Integer.parseInt(pageNoString);
     *         return pageNo;
     *     }catch (Exception e){
     *         LOGGER.error(e.getClass().getName(), e);
     *     }
     *     return 1; // 不带这个参数 或者转换异常 返回1
     * }
     * 
     * </pre>
     * 
     * 现在可以更改成:
     * 
     * <pre class="code">
     * 
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     return ConvertUtil.toInteger(pageNoString, 1);
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param toBeConvertedValue
     *            值
     * @param defaultValue
     *            默认值
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 <code>defaultValue</code> <br>
     *         如果找不到转换器或者转换的时候出现了异常,返回 <code>defaultValue</code>
     * @see org.apache.commons.beanutils.converters.IntegerConverter
     * @see org.apache.commons.lang3.ObjectUtils#defaultIfNull(Object, Object)
     * @since 1.6.1
     */
    public static Integer toInteger(Object toBeConvertedValue,Integer defaultValue){
        return new IntegerConverter(defaultValue).convert(Integer.class, toBeConvertedValue);
    }

    /**
     * 将 <code>toBeConvertedValue</code> 转换成 {@link java.math.BigDecimal}.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toBigDecimal(1111) = BigDecimal.valueOf(1111)
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>{@link java.lang.Double} 转成 {@link java.math.BigDecimal}注意点:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <span style="color:red">推荐使用 {@link BigDecimal#valueOf(double)}</span>,不建议使用 <code>new BigDecimal(double)</code>,参见 JDK API<br>
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
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     *         如果找不到转换器或者转换的时候出现了异常,返回 null
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.NumberConverter#toNumber(Class, Class, Number)
     * @see org.apache.commons.beanutils.converters.BigDecimalConverter
     */
    public static BigDecimal toBigDecimal(Object toBeConvertedValue){
        return new BigDecimalConverter(null).convert(BigDecimal.class, toBeConvertedValue);
    }

    /**
     * 把对象转换为{@link Long}类型.
     * 
     * <p>
     * converted is missing or an error occurs converting the value,返回<span style="color:red"> null</span>
     * </p>
     *
     * @param toBeConvertedValue
     *            包含数字的对象.
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     *         如果找不到转换器或者转换的时候出现了异常,返回 null
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.LongConverter
     * @see org.apache.commons.lang3.math.NumberUtils#toLong(String)
     */
    public static Long toLong(Object toBeConvertedValue){
        return new LongConverter(null).convert(Long.class, toBeConvertedValue);
    }

    /**
     * 任意的数组转成{@link Integer} 数组.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toIntegers("1,2,3")                              = [1,2,3]
     * ConvertUtil.toIntegers(new String[] { "1", "2", "3" })       = [1,2,3]
     * ConvertUtil.toIntegers(ConvertUtil.toList("1", "2", "3"))    = [1,2,3]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>如果传的 <code>toBeConvertedValue</code>是 <code>value.getClass().isArray()</code> 或者 {@link Collection}</h3>
     * <blockquote>
     * 
     * <p>
     * 参见 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToType(Class, Object) ArrayConverter#convertToType(Class,
     * Object)} 会基于targetType 构造一个<code>Integer</code>数组对象, 大小长度就是 <code>toBeConvertedValue</code>的大小或者长度, 然后迭代
     * <code>toBeConvertedValue</code>
     * 依次进行转换
     * </p>
     * 
     * </blockquote>
     * 
     * @param toBeConvertedValue
     *            the to be converted value
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.ArrayConverter
     */
    public static Integer[] toIntegers(Object toBeConvertedValue){
        return convert(toBeConvertedValue, Integer[].class);
    }

    /**
     * 将 <code>toBeConvertedValue</code> 转成Long 数组.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toLongs("1,2,3")                             = [1,2,3]
     * ConvertUtil.toLongs(new String[] { "1", "2", "3" })      = [1,2,3]
     * ConvertUtil.toLongs(ConvertUtil.toList("1", "2", "3"))   = [1,2,3]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>特别适合以下形式的代码:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * protected long[] getOrderIdLongs(String orderIds){
     *     // 确认交易时候插入数据库的时候,不应该会出现 空的情况
     *     String[] orderIdArray = orderIds.split(",");
     *     int orderLength = orderIdArray.length;
     *     long[] ids = new long[orderLength];
     *     for (int i = 0, j = orderLength; i {@code <} j; ++i){
     *         ids[i] = Long.parseLong(orderIdArray[i]);
     *     }
     *     return ids;
     * }
     * 
     * </pre>
     * 
     * 可以重构成:
     * 
     * <pre class="code">
     * 
     * protected long[] getOrderIdLongs(String orderIds){
     *     return ConvertUtil.toLongs(orderIds);
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>如果传的 <code>toBeConvertedValue</code>是 <code>value.getClass().isArray()</code> 或者 {@link Collection}</h3>
     * <blockquote>
     * 
     * <p>
     * 参见 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToType(Class, Object) ArrayConverter#convertToType(Class,
     * Object)} 会基于targetType 构造一个<code>Long</code>数组对象, 大小长度就是 <code>toBeConvertedValue</code>的大小或者长度, 然后迭代 <code>toBeConvertedValue</code>
     * 依次进行转换
     * </p>
     * 
     * </blockquote>
     *
     * @param toBeConvertedValue
     *            the to be converted value
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.ArrayConverter
     * @see #convert(Object, Class)
     */
    public static Long[] toLongs(Object toBeConvertedValue){
        return convert(toBeConvertedValue, Long[].class);
    }
    //*************************************************************************************************

    /**
     * 把对象 <code>toBeConvertedValue</code> 转换成字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * assertEquals("1", ConvertUtil.toString(1));
     * assertEquals("1.0", ConvertUtil.toString(toBigDecimal(1.0)));
     * assertEquals("8", ConvertUtil.toString(toLong(8L)));
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 该方法<b>不适合</b> list转换成字符串,请使用 {@link #toString(ToStringConfig, Collection)}
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toString(toList("张飞", "关羽", "", "赵云")) = "张飞"
     * </pre>
     * 
     * <p>
     * 该方法也<b>不适合</b> array 转换成字符串,请使用 {@link #toString(ToStringConfig, Object...)}
     * </p>
     * 
     * <pre class="code">
     * Integer[] int1 = { 2, null, 1, null };
     * LOGGER.debug(ConvertUtil.toString(int1));        = 2
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>对于 Array 转成 String</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 参见 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToString(Object) ArrayConverter#convertToString(Object)} <br>
     * 
     * 在转换的过程中, 如果发现 object是数组,将使用 {@link java.lang.reflect.Array#get(Object, int) Array#get(Object, int)} 来获得数据,<br>
     * 如果发现不是数组, 将会将object转成集合 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToCollection(Class, Object)
     * ArrayConverter#convertToCollection(Class, Object)} 再转成 迭代器 {@link java.util.Collection#iterator() Collection.iterator()}
     * </p>
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
     * <p>
     * 默认:
     * </p>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
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
     * 将集合 <code>collection</code>使用连接符号链接成字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("feilong");
     * list.add("");
     * list.add("xinge");
     * 
     * ToStringConfig toStringConfig = new ToStringConfig(",");
     * toStringConfig.setIsJoinNullOrEmpty(false);
     * 
     * ConvertUtil.toString(toStringConfig, list);
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * feilong,xinge
     * </pre>
     * 
     * </blockquote>
     * 
     * @param toStringConfig
     *            连接字符串 实体
     * @param collection
     *            集合, 建议基本类型泛型的结合,因为这个方法是直接循环collection 进行拼接
     * @return 如果 <code>collection</code> isNullOrEmpty,返回null<br>
     *         如果 <code>toStringConfig</code> 是null,默认使用 {@link ToStringConfig#DEFAULT_CONNECTOR} 进行连接<br>
     *         都不是null,会循环,拼接toStringConfig.getConnector()
     * @see #toString(ToStringConfig, Object...)
     * @see "org.springframework.util.StringUtils#collectionToDelimitedString(Collection, String, String, String)"
     * @since 1.4.0
     */
    public static String toString(ToStringConfig toStringConfig,final Collection<?> collection){
        return Validator.isNullOrEmpty(collection) ? StringUtils.EMPTY : toString(toStringConfig, collection.toArray());
    }

    /**
     * 将数组通过{@link ToStringConfig} 拼接成字符串.
     * 
     * <p style="color:green">
     * 支持包装类型以及原始类型,比如 Integer [] arrays 或者 int []arrays
     * </p>
     * 
     * <pre class="code">
     * Example 1:
     * ConvertUtil.toString(new ToStringConfig(),"a","b")       =   "a,b"
     * 
     * Example 2:
     * ToStringConfig toStringConfig=new ToStringConfig(",");
     * toStringConfig.setIsJoinNullOrEmpty(false);
     * ConvertUtil.toString(new ToStringConfig(),"a","b",null)  =   "a,b"
     * 
     * Example 3:
     * int[] ints = { 2, 1 };
     * ConvertUtil.toString(new ToStringConfig(),ints)          =   "2,1"
     * </pre>
     *
     * @param toStringConfig
     *            the join string entity
     * @param arrays
     *            <span style="color:red">支持包装类型以及原始类型,比如 Integer []arrays 以及 int []arrays</span>
     * @return 如果 <code>arrays</code> 是null 或者Empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则循环,拼接 {@link ToStringConfig#getConnector()}
     * @see org.apache.commons.lang3.builder.ToStringStyle
     * @see org.apache.commons.lang3.StringUtils#join(Iterable, String)
     * @see org.apache.commons.lang3.StringUtils#join(Object[], String)
     * @since 1.4.0
     */
    public static String toString(ToStringConfig toStringConfig,Object...arrays){
        if (Validator.isNullOrEmpty(arrays)){
            return StringUtils.EMPTY;
        }
        Object[] operateArray = toObjects(arrays);
        ToStringConfig useToStringConfig = ObjectUtils.defaultIfNull(toStringConfig, new ToStringConfig());
        return join(operateArray, useToStringConfig.getConnector(), useToStringConfig.getIsJoinNullOrEmpty());
    }

    /**
     * Join.
     *
     * @param operateArray
     *            the operate array
     * @param connector
     *            the connector
     * @param isJoinNullOrEmpty
     *            the is join null或者empty
     * @return the string
     * @see org.apache.commons.lang3.StringUtils#join(Iterable, String)
     * @see org.apache.commons.lang3.StringUtils#join(Object[], String)
     * @since 1.6.3
     */
    private static String join(Object[] operateArray,String connector,boolean isJoinNullOrEmpty){
        StringBuilder sb = new StringBuilder();
        for (Object obj : operateArray){
            //如果是null或者empty,但是参数值是不拼接,那么跳过,继续循环
            if (Validator.isNullOrEmpty(obj) && !isJoinNullOrEmpty){
                continue;
            }

            //value转换,注意:如果 value是null,StringBuilder将拼接 "null" 字符串,详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(ObjectUtils.defaultIfNull(obj, StringUtils.EMPTY)); //see StringUtils.defaultString(t)

            if (null != connector){//注意可能传过来的是换行符 不能使用Validator.isNullOrEmpty来判断
                sb.append(connector);//放心大胆的拼接 connector, 不判断是否是最后一个,最后会截取
            }
        }
        return StringUtil.substringWithoutLast(sb, connector);
    }

    //**********************************************************************************************

    /**
     * 将集合 <code>collection</code> 转成枚举.
     * 
     * @param <T>
     *            the generic type
     * @param collection
     *            集合
     * @return 如果 <code>collection</code> 是null,返回 {@link Collections#emptyEnumeration()}<br>
     *         否则返回{@link Collections#enumeration(Collection)}
     * @see Collections#enumeration(Collection)
     * @since 1.4.0
     */
    public static <T> Enumeration<T> toEnumeration(final Collection<T> collection){
        return null == collection ? Collections.<T> emptyEnumeration() : Collections.enumeration(collection);
    }

    /**
     * 将 <code>mapEntryCollection</code> 转成map ({@link LinkedHashMap}).
     * 
     * <p>
     * 注意,返回是的是 {@link LinkedHashMap},顺序依照参数 <code>mapEntryCollection</code>,key是 {@link java.util.Map.Entry#getKey()},value 是
     * {@link java.util.Map.Entry#getValue()}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = ConvertUtil.toMap(
     *                 ConvertUtil.toList(
     *                                 new SimpleEntry{@code <>}("张飞", "丈八蛇矛"),
     *                                 new SimpleEntry{@code <>}("关羽", "青龙偃月刀"),
     *                                 new SimpleEntry{@code <>}("赵云", "龙胆枪"),
     *                                 new SimpleEntry{@code <>}("刘备", "双股剑")));
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "张飞": "丈八蛇矛",
     * "关羽": "青龙偃月刀",
     * "赵云": "龙胆枪",
     * "刘备": "双股剑"
     * }
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <V>
     *            the value type
     * @param <K>
     *            the key type
     * @param <E>
     *            the element type
     * @param mapEntryCollection
     *            the map entry collection
     * @return 如果 <code>mapEntryCollection</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see org.apache.commons.lang3.ArrayUtils#toMap(Object[])
     * @since 1.7.1
     */
    public static <V, K, E extends Map.Entry<K, V>> Map<K, V> toMap(Collection<E> mapEntryCollection){
        if (null == mapEntryCollection){
            return Collections.emptyMap();
        }
        Map<K, V> map = MapUtil.newLinkedHashMap(mapEntryCollection.size());
        for (Map.Entry<K, V> entry : mapEntryCollection){
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * 将 {@link java.util.Map.Entry}数组转成map ({@link LinkedHashMap}).
     * 
     * <p>
     * 注意,返回是的是 {@link LinkedHashMap},顺序依照参数 {@link java.util.Map.Entry}数组顺序,key是 {@link java.util.Map.Entry#getKey()},value 是
     * {@link java.util.Map.Entry#getValue()}
     * </p>
     * 
     * <h3>{@link java.util.Map.Entry} 已知实现类</h3>
     * 
     * <blockquote>
     * <p>
     * 你可以使用 {@link Pair},或者 {@link java.util.AbstractMap.SimpleEntry}
     * </p>
     * </blockquote>
     * 
     * <h3>{@link Pair} 示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = ConvertUtil.toMap(
     * 
     *                 Pair.of("张飞", "丈八蛇矛"),
     *                 Pair.of("关羽", "青龙偃月刀"),
     *                 Pair.of("赵云", "龙胆枪"),
     *                 Pair.of("刘备", "双股剑"));
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "张飞": "丈八蛇矛",
     * "关羽": "青龙偃月刀",
     * "赵云": "龙胆枪",
     * "刘备": "双股剑"
     * }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>{@link java.util.AbstractMap.SimpleEntry} 示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = ConvertUtil.toMap(
     *                 new SimpleEntry{@code <>}("张飞", "丈八蛇矛"),
     *                 new SimpleEntry{@code <>}("关羽", "青龙偃月刀"),
     *                 new SimpleEntry{@code <>}("赵云", "龙胆枪"),
     *                 new SimpleEntry{@code <>}("刘备", "双股剑"));
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "张飞": "丈八蛇矛",
     * "关羽": "青龙偃月刀",
     * "赵云": "龙胆枪",
     * "刘备": "双股剑"
     * }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * 
     * <h3>重构:</h3>
     * 
     * 以前初始化全局map的时候,你可能会这么写
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * // 除数和单位的map,必须是有顺序的 从大到小.
     * private static final Map{@code <Long, String>} DIVISOR_AND_UNIT_MAP = new LinkedHashMap{@code <>}();
     * 
     * static{
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_TB, "TB");//(Terabyte，太字节，或百万兆字节)=1024GB，其中1024=2^10 ( 2 的10次方)。 
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_GB, "GB");//(Gigabyte，吉字节，又称“千兆”)=1024MB， 
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_MB, "MB");//(Megabyte，兆字节，简称“兆”)=1024KB， 
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_KB, "KB");//(Kilobyte 千字节)=1024B
     * }
     * 
     * </pre>
     * 
     * 现在你可以写成:
     * 
     * <pre class="code">
     * 
     * // 除数和单位的map,必须是有顺序的 从大到小.
     * private static final Map{@code <Long, String>} DIVISOR_AND_UNIT_MAP = ConvertUtil.toMap(
     *                 Pair.of(FileUtils.ONE_TB, "TB"), //(Terabyte，太字节，或百万兆字节)=1024GB，其中1024=2^10 ( 2 的10次方)。 
     *                 Pair.of(FileUtils.ONE_GB, "GB"), //(Gigabyte，吉字节，又称“千兆”)=1024MB， 
     *                 Pair.of(FileUtils.ONE_MB, "MB"), //(Megabyte，兆字节，简称“兆”)=1024KB， 
     *                 Pair.of(FileUtils.ONE_KB, "KB")); //(Kilobyte 千字节)=1024B
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <V>
     *            the value type
     * @param <K>
     *            the key type
     * @param mapEntrys
     *            the entrys
     * @return 如果 <code>entrys</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see org.apache.commons.lang3.tuple.ImmutablePair#ImmutablePair(Object, Object)
     * @see org.apache.commons.lang3.tuple.Pair#of(Object, Object)
     * @since 1.7.1
     */
    @SafeVarargs
    public static <V, K> Map<K, V> toMap(Map.Entry<K, V>...mapEntrys){
        if (null == mapEntrys){
            return Collections.emptyMap();
        }
        Map<K, V> map = MapUtil.newLinkedHashMap(mapEntrys.length);
        for (Map.Entry<K, V> entry : mapEntrys){
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * 将 <code>key</code> 和 <code>value</code> 直接转成map.
     * 
     * <p>
     * 注意,返回是的是 {@link LinkedHashMap}}
     * </p>
     * 
     * <p>
     * 非常适合单key的场景,比如
     * </p>
     * 
     * <pre class="code">
     * 
     * private List{@code <ShopCommand>} loadShopCommandList(){
     *     Map{@code <String, Object>} paraMap = new HashMap{@code <String, Object>}();
     *     paraMap.put("orgTypeId", OrgType.ID_SHOP_TYPE);
     * 
     *     Sort[] sorts = Sort.parse("s.id asc");
     *     return shopCommandDao.findShopListByOrgaTypeId(paraMap, sorts);
     * }
     * </pre>
     * 
     * 可以改写成 :
     * 
     * <pre class="code">
     * 
     * private List{@code <ShopCommand>} loadShopCommandList(){
     *     return shopCommandDao
     *                     .findShopListByOrgaTypeId(ConvertUtil.toMap("orgTypeId", (Object) OrgType.ID_SHOP_TYPE), Sort.parse("s.id asc"));
     * }
     * </pre>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * LOGGER.debug(JsonUtil.format(ConvertUtil.toMap("张飞", "丈八蛇矛")));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {"张飞": "丈八蛇矛"}
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param key
     *            the key
     * @param value
     *            the value
     * @return 将 <code>key</code> 和 <code>value</code> 直接转成map
     * @see org.apache.commons.lang3.ArrayUtils#toMap(Object[])
     * @since 1.7.1
     */
    public static <K, V> Map<K, V> toMap(K key,V value){
        Map<K, V> map = new LinkedHashMap<K, V>();//不设置初始值 ,可能调用再PUT 这样浪费性能
        map.put(key, value);
        return map;
    }

    /**
     * 转换成map.
     * 
     * <p>
     * Create a new HashMap and pass an instance of Properties.<br>
     * Properties is an implementation of a Map which keys and values stored as in a string.
     * </p>
     * 
     * @param properties
     *            the properties
     * @return 如果 <code>properties</code> 是null,抛出 {@link NullPointerException}<br>
     * @see org.apache.commons.collections4.MapUtils#toProperties(Map)
     * @since 1.7.1
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, String> toMap(Properties properties){
        Validate.notEmpty(properties, "properties can't be null/empty!");
        return new HashMap<String, String>((Map) properties);
    }

    /**
     * 读取配置文件,将k/v 统统转成map.
     * 
     * <p>
     * 注意:JDK实现{@link java.util.PropertyResourceBundle},内部是使用 hashmap来存储数据的,<br>
     * 本方法出于log以及使用方便,返回的是<span style="color:red"> TreeMap</span>
     * </p>
     *
     * @param resourceBundle
     *            the resource bundle
     * @return 如果 <code>resourceBundle</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>resourceBundle</code> 没有key,则返回{@link java.util.Collections#emptyMap()}<br>
     *         否则,解析所有的key和value转成 {@link TreeMap}<br>
     * @see MapUtils#toMap(ResourceBundle)
     * @since 1.7.3
     */
    public static Map<String, String> toMap(ResourceBundle resourceBundle){
        Validate.notNull(resourceBundle, "resourceBundle can't be null!");

        Enumeration<String> keysEnumeration = resourceBundle.getKeys();
        if (Validator.isNullOrEmpty(keysEnumeration)){
            return Collections.emptyMap();
        }

        Map<String, String> map = new TreeMap<String, String>();
        while (keysEnumeration.hasMoreElements()){
            String key = keysEnumeration.nextElement();
            map.put(key, resourceBundle.getString(key));
        }
        return map;
    }

    /**
     * 将map转成 {@link Properties}.
     * 
     * <p>
     * 由于 Properties 只能保存 非空的 key和value,因此如果map 有key或者 value是null,将会抛出 {@link NullPointerException}
     * </p>
     * 
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 empty Properties<br>
     * @see org.apache.commons.collections4.MapUtils#toProperties(Map)
     * @since 1.7.3
     */
    public static Properties toProperties(final Map<String, String> map){
        return MapUtils.toProperties(map);
    }

    //*************************************toList*********************************************************
    /**
     * 将枚举 <code>enumeration</code> 转成集合.
     * 
     * @param <T>
     *            the generic type
     * @param enumeration
     *            the enumeration
     * @return 如果 <code>enumeration</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         否则返回 {@link Collections#list(Enumeration)}
     * @see Collections#emptyList()
     * @see Collections#EMPTY_LIST
     * @see Collections#list(Enumeration)
     * @see EnumerationUtils#toList(Enumeration)
     * @since 1.0.7
     */
    public static <T> List<T> toList(final Enumeration<T> enumeration){
        return null == enumeration ? Collections.<T> emptyList() : Collections.list(enumeration);
    }

    /**
     * 将 集合 <code>collection</code> 转成 list.
     * 
     * <p>
     * 此方法很适合 快速的将 set转成list这样的操作
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Set{@code <String>} set = new HashSet{@code <String>}();
     * Collections.addAll(set, "a", "a", "b", "b");
     * LOGGER.debug("{}", ConvertUtil.toList(set));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [b, a]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param collection
     *            the collection
     * @return 如果 <code>collection</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>collection instanceof List</code>,那么强转成 list返回<br>
     *         否则返回 <code>new ArrayList(collection)</code>
     * @since 1.6.1
     */
    public static <T> List<T> toList(final Collection<T> collection){
        return null == collection ? Collections.<T> emptyList()
                        : (collection instanceof List ? (List<T>) collection : new ArrayList<T>(collection));
    }

    /**
     * 数组转成 ({@link java.util.ArrayList ArrayList}),此方法返回的list可以进行add等操作.
     * 
     * <h3>特别适合:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 如果你要通过以下方式来构造list:
     * </p>
     * 
     * <pre class="code">
     * 
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("feilong1");
     * list.add("feilong2");
     * list.add("feilong2");
     * list.add("feilong3");
     * 
     * </pre>
     * 
     * 此时你可以使用:
     * 
     * <pre class="code">
     * 
     * List{@code <String>} list = ConvertUtil.toList("feilong1", "feilong2", "feilong2", "feilong3");
     * </pre>
     * 
     * <p>
     * 代码会更简洁
     * </p>
     * </blockquote>
     * 
     * <h3>甚至于:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 有很多时候,参数需要一个对象list,构造的时候,你需要这样
     * </p>
     * 
     * <pre class="code">
     * 
     * List{@code <UserAddress>} userAddresseList = new ArrayList{@code <UserAddress>}();
     * UserAddress userAddress = new UserAddress();
     * userAddress.setAddress("上海");
     * userAddresseList.add(userAddress);
     * 
     * </pre>
     * 
     * 你可以重构成:
     * 
     * <pre class="code">
     * UserAddress userAddress = new UserAddress();
     * userAddress.setAddress("上海");
     * List{@code <UserAddress>} userAddresseList = toList(userAddress);
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意 :</h3>
     * 
     * <blockquote>
     * <p>
     * 如果直接使用{@link java.util.Arrays#asList(Object...) Arrays#asList(Object...)}返回的list没有实现 {@link java.util.Collection#add(Object)
     * Collection#add(Object)}等方法,执行<code>list.add("c")</code>;操作的话会导致异常!<br>
     * 
     * 而本方法使用 {@link ArrayList#ArrayList(java.util.Collection)} 来进行重新封装返回,可以执行正常的list操作
     * </p>
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            T数组
     * @return 如果 <code>arrays</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         否则返回 {@code new ArrayList<T>(Arrays.asList(arrays));}
     * @see java.util.Arrays#asList(Object...)
     * @see java.util.Collections#addAll(Collection, Object...)
     * @see java.util.Collections#singletonList(Object)
     * @see "org.springframework.util.CollectionUtils#arrayToList(Object)"
     */
    @SafeVarargs
    public static <T> List<T> toList(T...arrays){
        return Validator.isNullOrEmpty(arrays) ? Collections.<T> emptyList() : new ArrayList<T>(Arrays.asList(arrays));
    }

    //*************************************toArray*********************************************************
    /**
     * To array.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * String[] array = ConvertUtil.toArray("1", "2");                  =["1", "2"]
     * String[] emptyArray = ConvertUtil.{@code <String>}toArray();     =[]
     * String[] nullArray = ConvertUtil.toArray(null)                   = null
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @return 如果 <code>arrays</code> 是null,返回null<br>
     * @see org.apache.commons.lang3.ArrayUtils#toArray(Object...)
     * @since 1.6.0
     */
    @SafeVarargs
    public static <T> T[] toArray(T...arrays){
        return ArrayUtils.toArray(arrays);
    }

    /**
     * 将集合 <code>collection</code> 转成数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <String>} testList = new ArrayList{@code <String>}();
     * testList.add("xinge");
     * testList.add("feilong");
     * 
     * String[] array = ConvertUtil.toArray(testList, String.class);
     * LOGGER.info(JsonUtil.format(array));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [
     * "xinge",
     * "feilong"
     * ]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param collection
     *            collection
     * @param arrayComponentType
     *            数组组件类型的 Class
     * @return 如果 <code>collection</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>arrayComponentType</code> 是null,抛出 {@link NullPointerException}<br>
     * @see java.lang.reflect.Array#newInstance(Class, int)
     * @see java.lang.reflect.Array#newInstance(Class, int...)
     * @see java.util.Collection#toArray()
     * @see java.util.Collection#toArray(Object[])
     * @see java.util.List#toArray()
     * @see java.util.List#toArray(Object[])
     * @see java.util.Vector#toArray()
     * @see java.util.Vector#toArray(Object[])
     * @see java.util.LinkedList#toArray()
     * @see java.util.LinkedList#toArray(Object[])
     * @see java.util.ArrayList#toArray()
     * @see java.util.ArrayList#toArray(Object[])
     * @see org.apache.commons.collections4.IteratorUtils#toArray(Iterator,Class)
     * @since 1.2.2
     */
    public static <T> T[] toArray(Collection<T> collection,Class<T> arrayComponentType){
        Validate.notNull(collection, "collection must not be null");
        Validate.notNull(arrayComponentType, "arrayComponentType must not be null");

        // 如果采用大家常用的把a的length设为0,就需要反射API来创建一个大小为size的数组,而这对性能有一定的影响.
        // 所以最好的方式就是直接把a的length设为Collection的size从而避免调用反射API来达到一定的性能优化.
        T[] array = ArrayUtil.newArray(arrayComponentType, collection.size());

        //注意,toArray(new Object[0]) 和 toArray() 在功能上是相同的. 
        return collection.toArray(array);
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
     * @param toBeConvertedValue
     *            the values
     * @param targetType
     *            要被转换的目标类型
     * @return 如果 <code>toBeConvertedValue</code> 是null,那么返回null<br>
     *         如果 <code>targetType</code> 是null,抛出 {@link NullPointerException}<br>
     *         否则调用 {@link ConvertUtils#convert(String[], Class)}
     * @see org.apache.commons.beanutils.ConvertUtils#convert(String[], Class)
     * @see org.apache.commons.beanutils.ConvertUtilsBean#convert(String[], Class)
     * @since 1.6.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(String[] toBeConvertedValue,Class<T> targetType){
        return null == toBeConvertedValue ? null : (T[]) ConvertUtils.convert(toBeConvertedValue, targetType);
    }
    //********************************************************************************

    /**
     * 将数组转成对象型数组.
     * 
     * <p>
     * 如果 <code>arrays</code>是原始型的,那么会进行转换.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @return the object[]
     * @since 1.4.0
     */
    @SafeVarargs
    private static <T> Object[] toObjects(T...arrays){
        if (Validator.isNullOrEmpty(arrays)){
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (arrays.length > 1){
            return arrays;
        }

        Object o = arrays[0];
        return isPrimitiveArray(o) ? primitiveArrayToObjectArray(o) : arrays;
    }

    /**
     * 判断是否是 Primitive型数组.
     *
     * @param o
     *            the o
     * @return true, if checks if is primitive array
     * 
     * @since 1.4.0
     */
    private static boolean isPrimitiveArray(Object o){
        // Allocate a new Array
        Class<? extends Object> klass = o.getClass();
        return !klass.isArray() ? false : klass.getComponentType().isPrimitive();//原始型的
    }

    /**
     * To objects.
     *
     * @param primitiveArray
     *            the o
     * @return the object[]
     * @since 1.4.0
     */
    private static Object[] primitiveArrayToObjectArray(Object primitiveArray){
        int length = ArrayUtils.getLength(primitiveArray);

        Object[] returnStringArray = new Object[length];
        for (int i = 0; i < length; ++i){
            returnStringArray[i] = ArrayUtil.getElement(primitiveArray, i);
        }
        return returnStringArray;
    }

    /**
     * 转成{@link String}数组.
     * 
     * <h3>规则:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 解析传入的字符串成数组 in the Java language into a <code>List</code> individual Strings for each element, 根据以下规则:
     * </p>
     * 
     * <ul>
     * <li>The string is expected to be a comma-separated list of values.</li>
     * <li>自动去除开头的 '{' 和 结束的'}'.</li>
     * <li>每个元素前后的空格将会去除.</li>
     * <li>Elements in the list may be delimited by single or double quotes.
     * Within a quoted elements, the normal Java escape sequences are valid.</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings("{5,4, 8,2;8 9_5@3`a}")));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * ["5","4","8","2","8","9","5","3","a"]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>也可以解析其他的数组成字符串数组哦:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * URL[] urls = {
     *                URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing0/"),
     *                URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing1/"),
     *                URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing2/"),
     *                null };
     * 
     * LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(urls)));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [
     * "http://www.exiaoshuo.com/jinyiyexing0/",
     * "http://www.exiaoshuo.com/jinyiyexing1/",
     * "http://www.exiaoshuo.com/jinyiyexing2/",
     * null
     * ]
     * </pre>
     * 
     * </blockquote>
     *
     * @param toBeConvertedValue
     *            the to be converted value
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回null<br>
     * @see org.apache.commons.beanutils.converters.ArrayConverter#convertToType(Class, Object)
     * @see org.apache.commons.beanutils.converters.ArrayConverter#parseElements(Class, String)
     * @see #convert(Object, Class)
     * @since 1.4.0
     */
    public static String[] toStrings(Object toBeConvertedValue){
        return convert(toBeConvertedValue, String[].class);
    }

    /**
     * 转成Iterator类型.
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
     * @param toBeConvertedValue
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
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回null<br>
     *         如果 <code>toBeConvertedValue</code> 是字符串,先转成数组,再转成迭代器<br>
     *         否则转成 {@link IteratorUtils#getIterator(Object)}
     * @see Collection#iterator()
     * @see EnumerationIterator#EnumerationIterator(Enumeration)
     * @see IteratorUtils#getIterator(Object)
     * @see "org.apache.taglibs.standard.tag.common.core.ForEachSupport#supportedTypeForEachIterator(Object)"
     * @since Commons Collections4.0
     */
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> toIterator(Object toBeConvertedValue){
        if (null == toBeConvertedValue){
            return null;
        }
        // 逗号分隔的字符串
        if (toBeConvertedValue instanceof String){
            return toIterator(toStrings(toBeConvertedValue));
        }
        return (Iterator<T>) IteratorUtils.getIterator(toBeConvertedValue);
    }

    /**
     * 将 <code>toBeConvertedValue</code> 转成指定 <code>targetType</code> 类型的对象.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.convert("1", Integer.class)      =1
     * ConvertUtil.convert("", Integer.class)       =0
     * ConvertUtil.convert("1", Long.class)         =1
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>此外,该方法特别适合数组类型的转换,比如 Type[] 转成 Class []:</h3>
     * 
     * <blockquote>
     * 
     * 原来的写法:
     * 
     * <pre class="code">
     * 
     * Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
     * int length = actualTypeArguments.length;
     * Class{@code <?>}[] klasses = new Class{@code <?>}[length];
     * for (int i = 0, j = length; i {@code <} j; ++i){
     *     klasses[i] = (Class{@code <?>}) actualTypeArguments[i];
     * }
     * 
     * return klasses;
     * 
     * </pre>
     * 
     * 现在可以重构成:
     * 
     * <pre class="code">
     * Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
     * return convert(actualTypeArguments, Class[].class);
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * 
     * <li>如果<code>targetType</code>的转换器没有注册,<b>那么传入的value原样返回</b>,<br>
     * 比如<code>ConvertUtil.convert("zh_CN", Locale.class)</code> 由于找不到converter,那么返回"zh_CN".
     * </li>
     * 
     * <li>如果转换不了,会使用默认值</li>
     * 
     * <li>如果传的 <code>toBeConvertedValue</code>是 <code>toBeConvertedValue.getClass().isArray()</code> 或者 {@link Collection}
     * <blockquote>
     * 
     * <dl>
     * <dt>如果 <code>targetType</code> 不是数组</dt>
     * <dd>
     * <p>
     * 那么<span style="color:red">会取第一个元素</span>进行转换,<br>
     * 参见{@link AbstractConverter#convert(Class, Object)},调用的 {@link AbstractConverter#convertArray(Object)} 方法
     * </p>
     * </dd>
     * 
     * <dt>如果 <code>targetType</code> 是数组</dt>
     * <dd>参见 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToType(Class, Object) ArrayConverter#convertToType(Class,
     * Object)} 会基于targetType 构造一个数组对象,大小长度就是 <code>toBeConvertedValue</code>的大小或者长度, 然后迭代 <code>toBeConvertedValue</code> 依次进行转换</dd>
     * 
     * </dl>
     * 
     * </blockquote>
     * </li>
     * </ol>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param toBeConvertedValue
     *            需要被转换的对象/值
     * @param targetType
     *            要被转换的目标类型
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回null<br>
     *         如果 <code>targetType</code> 是null,抛出 {@link NullPointerException}<br>
     *         否则返回 {@link org.apache.commons.beanutils.ConvertUtils#convert(Object, Class)}
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.AbstractConverter#convert(Class, Object)
     * @see org.apache.commons.beanutils.converters.ArrayConverter#convertToType(Class, Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Object toBeConvertedValue,Class<T> targetType){
        Validate.notNull(targetType, "targetType can't be null!");
        return null == toBeConvertedValue ? null : (T) ConvertUtils.convert(toBeConvertedValue, targetType);
    }

    /**
     * 转成 {@link Locale}.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toLocale(null)       = null
     * ConvertUtil.toLocale("zh_CN")    = Locale.CHINA
     * </pre>
     * 
     * </blockquote>
     *
     * @param locale
     *            可以是null,字符串或者直接的 {@link Locale}对象
     * @return 如果 <code>locale</code> 是null,返回 null<br>
     *         如果 <code>locale instanceof Locale</code>,返回 <code>(Locale) locale</code><br>
     *         如果 <code>locale instanceof String</code>,返回 {@link LocaleUtils#toLocale(String)}<br>
     *         其他情况,抛出 {@link UnsupportedOperationException}
     * @see org.apache.commons.lang3.LocaleUtils#toLocale(String)
     * @since 1.7.2
     */
    public static Locale toLocale(Object locale){
        if (null == locale){
            return null;
        }
        if (locale instanceof Locale){
            return (Locale) locale;
        }
        if (locale instanceof String){
            return LocaleUtils.toLocale((String) locale);
        }
        throw new UnsupportedOperationException("locale2 not support!");
    }
}