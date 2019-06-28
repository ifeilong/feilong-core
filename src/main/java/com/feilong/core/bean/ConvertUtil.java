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

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static com.feilong.core.util.SortUtil.sortMapByKeyAsc;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.NumberConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.iterators.EnumerationIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import com.feilong.core.lang.ArrayUtil;
import com.feilong.core.lang.StringUtil;
import com.feilong.core.util.SortUtil;
import com.feilong.core.util.transformer.SimpleClassTransformer;

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
 * 
 * <tr valign="top">
 * <td>{@link ConvertUtils#convert(Object)}</td>
 * <td>将指定的value转成string.<br>
 * 如果value是array,将会返回数组第一个元素转成string.<br>
 * 将会使用注册的 <code>java.lang.String</code>{@link Converter},<br>
 * 允许应用定制 Object{@code ->}String conversions(默认使用简单的使用 toString())<br>
 * see {@link ConvertUtilsBean#convert(Object)}</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link ConvertUtils#convert(String, Class)}</td>
 * <td>将String value转成 指定Class 类型的对象 (如果可能),否则返回string.<br>
 * see {@link ConvertUtilsBean#convert(String, Class)}</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link ConvertUtils#convert(String[], Class)}</td>
 * <td>将数组转成指定class类型的对象. <br>
 * 如果指定的Class类型是数组类型,那么返回值的类型将是数组的类型.否则将会构造一个指定类型的数组返回.<br>
 * see {@link ConvertUtilsBean#convert(String[], Class)} <br>
 * see {@link #toArray(String[], Class)}</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link ConvertUtils#convert(Object, Class)}</td>
 * <td>将value转成指定Class类型的对象,如果Class的转换器没有注册,那么传入的value原样返回.<br>
 * see {@link ConvertUtilsBean#convert(Object, Class)}<br>
 * see {@link #convert(Object, Class)}</td>
 * </tr>
 * 
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
 * 
 * <tr valign="top">
 * <td>{@link java.math.BigDecimal}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.math.BigInteger}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>boolean and {@link java.lang.Boolean}</td>
 * <td>default to false</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>byte and {@link java.lang.Byte}</td>
 * <td>default to zero</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>char and {@link java.lang.Character}</td>
 * <td>default to a space</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.lang.Class}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>double and {@link java.lang.Double}</td>
 * <td>default to zero</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>float and {@link java.lang.Float}</td>
 * <td>default to zero</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>int and {@link java.lang.Integer}</td>
 * <td>default to zero</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>long and {@link java.lang.Long}</td>
 * <td>default to zero</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>short and {@link java.lang.Short}</td>
 * <td>default to zero</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.lang.String}</td>
 * <td>default to null</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.io.File}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.net.URL}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.sql.Date}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.sql.Time}</td>
 * <td><span style="color:red">no default value</span></td>
 * </tr>
 * 
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

    //---------------------------------------------------------------

    static{
        //初始化注册器.
        ConvertUtil.registerStandardDefaultNull();
    }

    //---------------------------------------------------------------

    /**
     * Register standard default null.
     * 
     * @see ConvertUtilsBean#registerPrimitives(boolean) registerPrimitives(boolean throwException)
     * @see ConvertUtilsBean#registerStandard(boolean,boolean) registerStandard(boolean throwException, boolean defaultNull)
     * @see ConvertUtilsBean#registerOther(boolean) registerOther(boolean throwException)
     * @see ConvertUtilsBean#registerArrays(boolean,int) registerArrays(boolean throwException, int defaultArraySize)
     * @see ConvertUtilsBean#deregister(Class) ConvertUtilsBean.deregister(Class)
     * @since 1.11.2
     */
    public static void registerStandardDefaultNull(){
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
        ConvertUtils.register(new BooleanConverter(null), Boolean.class);
        ConvertUtils.register(new ByteConverter(null), Byte.class);
        ConvertUtils.register(new CharacterConverter(null), Character.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new StringConverter(null), String.class);
    }

    //---------------------------------------------------------------

    /**
     * Register simple date locale converter.
     *
     * @param pattern
     *            the pattern
     * @since 1.11.2
     */
    public static void registerSimpleDateLocaleConverter(String pattern){
        DateLocaleConverter dateLocaleConverter = new DateLocaleConverter(null, Locale.getDefault(), pattern);
        ConvertUtils.register(dateLocaleConverter, Date.class);
    }

    //---------------------toBoolean------------------------------------------

    /**
     * 将 <code>toBeConvertedValue</code> 转换成 {@link Boolean}类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * ConvertUtil.toBoolean(null)      =   null
     * 
     * ConvertUtil.toBoolean(1L)        =   true
     * ConvertUtil.toBoolean("1")       =   true
     * ConvertUtil.toBoolean("9")       =   null
     * ConvertUtil.toBoolean("1,2,3")   =   null
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>逻辑及规则:</h3>
     * 
     * <blockquote>
     * 
     * <ul>
     * <li>如果 "true", "yes", "y", "on", "1" <span style="color:green">(忽视大小写)</span>, 返回 true</li>
     * <li>如果 "false", "no", "n", "off", "0" <span style="color:green">(忽视大小写)</span>, 返回 false</li>
     * <li>其他抛出 conversionException, 但是在
     * {@link org.apache.commons.beanutils.converters.AbstractConverter#handleError(Class, Object, Throwable) handleError(Class, Object,
     * Throwable)} 方法里面返回默认值 是 null
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
        return new BooleanConverter(null).convert(Boolean.class, toBeConvertedValue);
    }

    //----------------------toInteger-----------------------------------------

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
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} L227:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toInteger(new String[] { "1", "2", "3" }) = 1
     * </pre>
     * 
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} Line234:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toInteger(toList("1", "2")) = 1
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * 该方法非常适用 获取request请求的分页参数
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 原来的写法:
     * </p>
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
     *     return 1; <span style="color:green">// 不带这个参数或者转换异常返回1</span>
     * }
     * 
     * </pre>
     * 
     * <p>
     * 现在可以更改成:
     * </p>
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
     * <p>
     * 当然对于这种场景,最快捷的:调用支持默认值的 {@link #toInteger(Object, Integer)} 方法
     * </p>
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
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
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
     * ConvertUtil.toInteger(null,null)               = null
     * ConvertUtil.toInteger(null,1)                  = 1
     * ConvertUtil.toInteger("aaaa",1)                = 1
     * ConvertUtil.toInteger(8L,1)                    = 8
     * ConvertUtil.toInteger("8",1)                   = 8
     * ConvertUtil.toInteger(new BigDecimal("8"),1)   = 8
     * </pre>
     * 
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} L227:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toInteger(new String[] { "1", "2", "3" }, 8) = 1
     * </pre>
     * 
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} Line234:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toInteger(toList("1", "2"), 8) = 1
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * 该方法非常适用 获取request请求的分页参数
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
     *     return 1; <span style="color:green">// 不带这个参数或者转换异常返回1</span>
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
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     *         如果找不到转换器或者转换的时候出现了异常,返回 <code>defaultValue</code>
     * @see org.apache.commons.beanutils.converters.IntegerConverter
     * @see org.apache.commons.lang3.ObjectUtils#defaultIfNull(Object, Object)
     * @since 1.6.1
     */
    public static Integer toInteger(Object toBeConvertedValue,Integer defaultValue){
        return new IntegerConverter(defaultValue).convert(Integer.class, toBeConvertedValue);
    }

    //------------------------toLong---------------------------------------

    /**
     * 将 <code>toBeConvertedValue</code> 转换成 {@link Long}类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toLong(null)                     = null
     * ConvertUtil.toLong("aaaa")                   = null
     * ConvertUtil.toLong(8)                        = 8L
     * ConvertUtil.toLong("8")                      = 8L
     * ConvertUtil.toLong(new BigDecimal("8"))      = 8L
     * </pre>
     * 
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} L227:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toLong(new String[] { "1", "2", "3" }) = 1L
     * </pre>
     * 
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} Line234:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toLong(toList("1", "2")) = 1L
     * </pre>
     * 
     * </blockquote>
     * 
     * @param toBeConvertedValue
     *            包含数字的对象.
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     *         如果找不到转换器或者转换的时候出现了异常,返回 null
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.LongConverter
     * @see org.apache.commons.lang3.math.NumberUtils#toLong(String)
     */
    public static Long toLong(Object toBeConvertedValue){
        return new LongConverter(null).convert(Long.class, toBeConvertedValue);
    }

    //------------------------toBigDecimal---------------------------------------

    /**
     * 将 <code>toBeConvertedValue</code> 转换成 {@link java.math.BigDecimal}.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toBigDecimal(null)                     = null
     * ConvertUtil.toBigDecimal("aaaa")                   = null
     * ConvertUtil.toBigDecimal(8)                        = BigDecimal.valueOf(8)
     * ConvertUtil.toBigDecimal("8")                      = BigDecimal.valueOf(8)
     * ConvertUtil.toBigDecimal(new BigDecimal("8"))      = BigDecimal.valueOf(8)
     * </pre>
     * 
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} L227:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toBigDecimal(new String[] { "1", "2", "3" }) = BigDecimal.valueOf(1)
     * </pre>
     * 
     * <p>
     * 如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换,参见 {@link AbstractConverter#convertArray(Object)} Line234:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toBigDecimal(toList("1", "2")) = BigDecimal.valueOf(1)
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
     * <p>
     * 本方法底层调用的是 {@link NumberConverter#toNumber(Class, Class, Number)
     * NumberConverter#toNumber(Class, Class, Number)},正确的处理了 {@link java.lang.Double} 转成 {@link java.math.BigDecimal}
     * </p>
     * </blockquote>
     * 
     * @param toBeConvertedValue
     *            值
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     *         如果传入的参数 <code>toBeConvertedValue</code> 是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     *         如果找不到转换器或者转换的时候出现了异常,返回 null
     * @see #convert(Object, Class)
     * @see org.apache.commons.beanutils.converters.NumberConverter#toNumber(Class, Class, Number)
     * @see org.apache.commons.beanutils.converters.BigDecimalConverter
     */
    public static BigDecimal toBigDecimal(Object toBeConvertedValue){
        return new BigDecimalConverter(null).convert(BigDecimal.class, toBeConvertedValue);
    }

    //---------------------------------------------------------------
    //数组

    /**
     * 将 <code>toBeConvertedValue</code> 转成{@link Integer} 数组.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <p>
     * 核心实现,参见 {@link ArrayConverter#convertToType(Class, Object)}
     * </p>
     * 
     * <dl>
     * <dt>如果参数 <code>toBeConvertedValue</code>是 <code>数组</code> 或者 {@link Collection}</dt>
     * 
     * <dd>
     * <p>
     * 参见{@link org.apache.commons.beanutils.converters.ArrayConverter#convertToType(Class, Object)
     * ArrayConverter#convertToType(Class,Object)}<br>
     * 
     * 会构造一个<code>Integer</code>数组,长度就是 <code>toBeConvertedValue</code>的大小或者长度,然后迭代<code>toBeConvertedValue</code>依次逐个进行转换
     * </p>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * ConvertUtil.toIntegers(new String[] { "1", "2", "3" })       = [1,2,3]
     * ConvertUtil.toIntegers(toList("1", "2", "3"))    = [1,2,3]
     * </pre>
     * 
     * </dd>
     * 
     * <dt>如果参数 <code>toBeConvertedValue</code>不是<code>数组</code>也不是{@link Collection}</dt>
     * 
     * <dd>
     * <p>
     * 那么首先会调用 {@link ArrayConverter#convertToCollection(Class, Object)} 将 <code>toBeConvertedValue</code>转成集合,转换逻辑参见
     * {@link ArrayConverter#convertToCollection(Class, Object)}:
     * </p>
     * 
     * <ol>
     * <li>如果 <code>toBeConvertedValue</code>是{@link Number}, {@link Boolean} 或者 {@link java.util.Date} ,那么构造只有一个
     * <code>toBeConvertedValue</code> 元素的 List返回.</li>
     * <li>其他类型将转成字符串,然后调用 {@link ArrayConverter#parseElements(Class, String)}转成list.
     * 
     * <p>
     * 具体转换逻辑为:
     * </p>
     * 
     * <ul>
     * <li>字符串期望是一个逗号分隔的字符串.</li>
     * <li>字符串可以被'{' 开头 和 '}'结尾的分隔符包裹,程序内部会自动截取.</li>
     * <li>会去除前后空白.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java escape sequences are
     * valid.</li>
     * </ul>
     * 
     * </li>
     * </ol>
     * 
     * <p>
     * 得到list之后,会构造一个<code>Integer</code>数组,长度就是 <code>toBeConvertedValue</code>的大小或者长度,然后迭代<code>toBeConvertedValue</code>依次逐个进行转换
     * </p>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * ConvertUtil.toIntegers("1,2,3")                  = new Integer[] { 1, 2, 3 }
     * ConvertUtil.toIntegers("{1,2,3}")                = new Integer[] { 1, 2, 3 }
     * ConvertUtil.toIntegers("{ 1 ,2,3}")              = new Integer[] { 1, 2, 3 }
     * ConvertUtil.toIntegers("1,2, 3")                 = new Integer[] { 1, 2, 3 }
     * ConvertUtil.toIntegers("1,2 , 3")                = new Integer[] { 1, 2, 3 }
     * </pre>
     * 
     * </dd>
     * 
     * </dl>
     * 
     * <p>
     * 每个元素转换成 Integer的时候,会调用
     * {@link org.apache.commons.beanutils.converters.NumberConverter#convertToType(Class, Object)},具体的规则是:
     * </p>
     * 
     * <blockquote>
     * 
     * <dl>
     * <dt>1.如果 元素是 Number类型</dt>
     * <dd>那么会调用 {@link org.apache.commons.beanutils.converters.NumberConverter#toNumber(Class, Class, Number)}</dd>
     * 
     * <dt>2.如果 元素是 Boolean类型</dt>
     * <dd>那么 true被转成1,false 转成 0</dd>
     * 
     * <dt>3.其他情况</dt>
     * <dd>将元素转成字符串,并trim,再进行转换</dd>
     * 
     * <dt>4.元素是null的情况</dt>
     * <dd>如果有元素是null,那么会调用 {@link org.apache.commons.beanutils.converters.AbstractConverter#convert(Class, Object)},会调用
     * {@link org.apache.commons.beanutils.converters.AbstractConverter#handleMissing(Class)} 方法,没有默认值的话,会抛出异常,然后catch之后返回 empty Integer 数组
     * </dd>
     * </dl>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * ConvertUtil.toIntegers(toList("1", "2", <span style="color:red">" 3"</span>))        = new Integer[] { 1, 2, 3 }
     * ConvertUtil.toIntegers(toArray(true, false, false))                                  = new Integer[] { 1, 0, 0 }
     * ConvertUtil.toIntegers(new String[] { "1", null, "2", "3" })                         = new Integer[] {}
     * </pre>
     * 
     * </blockquote>
     * 
     * </blockquote>
     * 
     * @param toBeConvertedValue
     *            需要被转换的值
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
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <p>
     * 核心实现,参见 {@link ArrayConverter#convertToType(Class, Object)}
     * </p>
     * 
     * <dl>
     * <dt>如果参数 <code>toBeConvertedValue</code>是 <code>数组</code> 或者 {@link Collection}</dt>
     * 
     * <dd>
     * <p>
     * 参见{@link org.apache.commons.beanutils.converters.ArrayConverter#convertToType(Class, Object)
     * ArrayConverter#convertToType(Class,Object)}<br>
     * 
     * 会构造一个<code>Long</code>数组,长度就是 <code>toBeConvertedValue</code>的大小或者长度,然后迭代<code>toBeConvertedValue</code>依次逐个进行转换
     * </p>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * ConvertUtil.toLongs(new String[] { "1", "2", "3" }       = [1L,2L,3L]
     * ConvertUtil.toLongs(toList("1", "2", "3"))               = [1L,2L,3L]
     * </pre>
     * 
     * </dd>
     * 
     * <dt>如果参数 <code>toBeConvertedValue</code>不是<code>数组</code>也不是{@link Collection}</dt>
     * 
     * <dd>
     * <p>
     * 那么首先会调用 {@link ArrayConverter#convertToCollection(Class, Object)} 将 <code>toBeConvertedValue</code>转成集合,转换逻辑参见
     * {@link ArrayConverter#convertToCollection(Class, Object)}:
     * </p>
     * 
     * <ol>
     * <li>如果 <code>toBeConvertedValue</code>是{@link Number}, {@link Boolean} 或者 {@link java.util.Date} ,那么构造只有一个
     * <code>toBeConvertedValue</code> 元素的 List返回.</li>
     * <li>其他类型将转成字符串,然后调用 {@link ArrayConverter#parseElements(Class, String)}转成list.
     * 
     * <p>
     * 具体转换逻辑为:
     * </p>
     * 
     * <ul>
     * <li>字符串期望是一个逗号分隔的字符串.</li>
     * <li>字符串可以被'{' 开头 和 '}'结尾的分隔符包裹,程序内部会自动截取.</li>
     * <li>会去除前后空白.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java escape sequences are
     * valid.</li>
     * </ul>
     * 
     * </li>
     * </ol>
     * 
     * <p>
     * 得到list之后,会构造一个<code>Long</code>数组,长度就是 <code>toBeConvertedValue</code>的大小或者长度,然后迭代<code>toBeConvertedValue</code>依次逐个进行转换
     * </p>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * ConvertUtil.toLongs("1,2,3")                  = new Long[] { 1L, 2L, 3L }
     * ConvertUtil.toLongs("{1,2,3}")                = new Long[] { 1L, 2L, 3L }
     * ConvertUtil.toLongs("{ 1 ,2,3}")              = new Long[] { 1L, 2L, 3L }
     * ConvertUtil.toLongs("1,2, 3")                 = new Long[] { 1L, 2L, 3L }
     * ConvertUtil.toLongs("1,2 , 3")                = new Long[] { 1L, 2L, 3L }
     * </pre>
     * 
     * </dd>
     * 
     * </dl>
     * 
     * <p>
     * 每个元素转换成 Integer的时候,会调用
     * {@link org.apache.commons.beanutils.converters.NumberConverter#convertToType(Class, Object)},具体的规则是:
     * </p>
     * 
     * <blockquote>
     * 
     * <dl>
     * <dt>1.如果 元素是 Number类型</dt>
     * <dd>那么会调用 {@link org.apache.commons.beanutils.converters.NumberConverter#toNumber(Class, Class, Number)}</dd>
     * 
     * <dt>2.如果 元素是 Boolean类型</dt>
     * <dd>那么 true被转成1L,false 转成 0L</dd>
     * 
     * <dt>3.其他情况</dt>
     * <dd>将元素转成字符串,并trim,再进行转换</dd>
     * 
     * <dt>4.元素是null的情况</dt>
     * <dd>如果有元素是null,那么会调用 {@link org.apache.commons.beanutils.converters.AbstractConverter#convert(Class, Object)},会调用
     * {@link org.apache.commons.beanutils.converters.AbstractConverter#handleMissing(Class)} 方法,没有默认值的话,会抛出异常,然后catch之后返回 empty Integer 数组
     * </dd>
     * </dl>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * ConvertUtil.toLongs(toList("1", "2", <span style="color:red">" 3"</span>))        = new Long[] { 1L, 2L, 3L }
     * ConvertUtil.toLongs(toArray(true, false, false))                                  = new Long[] { 1L, 0L, 0L }
     * ConvertUtil.toLongs(new String[] { "1", null, "2", "3" })                         = new Long[] {}
     * </pre>
     * 
     * </blockquote>
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
     *     <span style="color:green">// 确认交易时候插入数据库的时候,不应该会出现空的情况</span>
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
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * 
     * protected long[] getOrderIdLongs(String orderIds){
     *     return toLongs(orderIds);
     * }
     * </pre>
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

    //---------------------------------------------------------------

    /**
     * 把对象 <code>toBeConvertedValue</code> 转换成字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toString(1)                  =   "1"
     * ConvertUtil.toString(toLong(8L))         =   "8"
     * ConvertUtil.toString(toBigDecimal(1.0))  =   "1.00"
     * ConvertUtil.toString(new Double(1.0))  =   "1.00"
     * ConvertUtil.toString(new Float(1.0))  =   "1.00"
     * 
     * ConvertUtil.toString(toList("张飞", "关羽", "", "赵云"))  =   "张飞,关羽,,赵云"
     * ConvertUtil.toString(toArray("张飞", "关羽", "", "赵云"))  =   "张飞,关羽,,赵云"
     * ConvertUtil.toString(toArray(null, "关羽", "", "赵云"))  =   ",关羽,,赵云"
     * </pre>
     * 
     * </blockquote>
     * 
     * @param toBeConvertedValue
     *            参数值
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     *         如果 <code>toBeConvertedValue</code> 是 {@link CharSequence},直接 toString返回<br>
     *         如果 <code>toBeConvertedValue</code> 是 数组,那么调用 {@link ConvertUtil#toString(Object[], String)}<br>
     *         如果 <code>toBeConvertedValue</code> 是 {@link Collection},那么调用 {@link ConvertUtil#toString(Object[], String)}<br>
     *         如果 <code>toBeConvertedValue</code> 是 {@link Date},那么返回 {@link com.feilong.core.DatePattern#COMMON_DATE_AND_TIME} 格式字符串<br>
     *         如果 <code>toBeConvertedValue</code> 是 {@link BigDecimal}或者是{@link Float}或者是 {@link Double},那么返回
     *         {@link com.feilong.core.NumberPattern#TWO_DECIMAL_POINTS} 2 位小数点格式字符串<br>
     *         其他调用 {@link com.feilong.core.bean.ConvertUtil#convert(Object, Class)}
     * @see org.apache.commons.beanutils.converters.ArrayConverter#convertToString(Object)
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     * @see org.apache.commons.beanutils.ConvertUtilsBean#convert(Object)
     * @see org.apache.commons.beanutils.converters.StringConverter
     * 
     * @see java.util.Arrays#toString(Object[])
     * @since 1.14.0 call {@link com.feilong.core.bean.ToStringHandler#toStringValue(Object)}
     */
    public static String toString(Object toBeConvertedValue){
        return ToStringHandler.toStringValue(toBeConvertedValue);
    }

    //---------------------------------------------------------------

    /**
     * 将集合 <code>collection</code> 使用拼接 <code>connector</code> 拼接成字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <String>} list = com.feilong.core.bean.ConvertUtil.toList("feilong", "", "xinge");
     * ConvertUtil.toString(list,",");
     * </pre>
     * 
     * <b>输出:</b>
     * 
     * <pre class="code">
     * feilong,,xinge
     * </pre>
     * 
     * <hr>
     * 
     * 你还可以使用这个方法来将集合<b>换行输出</b>,比如:
     * 
     * <pre class="code">
     * List{@code <String>} list = toList("飞龙", "小金", "四金", "金金金金");
     * 
     * LOGGER.debug(ConvertUtil.toString(list, System.lineSeparator()));
     * </pre>
     * 
     * <b>输出:</b>
     * 
     * <pre class="code">
     * 飞龙
     * 小金
     * 四金
     * 金金金金
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明</h3>
     * <blockquote>
     * 
     * <ol>
     * <li>如果有元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>
     *
     * @param collection
     *            集合,建议元素泛型不要使用自定义的对象(比如UserCommand等),因为这个方法是迭代collection,拿每个元素的字符串格式 进行拼接
     * @param connector
     *            the connector
     * @return 如果 <code>collection</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     * @see "org.springframework.util.StringUtils#collectionToDelimitedString(Collection, String, String, String)"
     * @see org.apache.commons.collections4.IteratorUtils#toString(Iterator)
     * @see org.apache.commons.lang3.StringUtils#join(Iterable, String)
     * @since 1.11.0
     */
    public static String toString(final Collection<?> collection,String connector){
        return isNullOrEmpty(collection) ? EMPTY : //
                        toString(collection.toArray(), new ToStringConfig(connector));
    }

    /**
     * 将数组 <code>arrays</code> 通过 <code>connector</code> 拼接成字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toString(null,",")               =   ""
     * ConvertUtil.toString(toArray(),",")          =   ""
     * 
     * ConvertUtil.toString(toArray("a","b"),",")   =   "a,b"
     * 
     * Integer[] array3 = { 2, null, 1, null };
     * ConvertUtil.toString(array3,",")             =   "2,,1,"
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>如果原始类型数组需要转换:</h3>
     * <blockquote>
     * 
     * <p>
     * 需要先使用下列的方式先转成包装类型数组
     * </p>
     * 
     * <ol>
     * <li>{@link ArrayUtils#toObject(boolean[])}</li>
     * <li>{@link ArrayUtils#toObject(byte[])}</li>
     * <li>{@link ArrayUtils#toObject(char[])}</li>
     * <li>{@link ArrayUtils#toObject(double[])}</li>
     * <li>{@link ArrayUtils#toObject(float[])}</li>
     * <li>{@link ArrayUtils#toObject(int[])}</li>
     * <li>{@link ArrayUtils#toObject(long[])}</li>
     * <li>{@link ArrayUtils#toObject(short[])}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>说明</h3>
     * <blockquote>
     * 
     * <ol>
     * <li>如果有元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>
     *
     * @param arrays
     *            支持包装类型,<b>不直接支持</b>原始类型
     * @param connector
     *            the connector
     * @return 如果 <code>arrays</code> 是null 或者Empty,返回 {@link StringUtils#EMPTY}<br>
     * @see org.apache.commons.lang3.builder.ToStringStyle
     * @see org.apache.commons.lang3.StringUtils#join(Object[], String)
     * @since 1.11.0
     */
    public static String toString(Object[] arrays,String connector){
        return isNullOrEmpty(arrays) ? EMPTY : toString(arrays, new ToStringConfig(connector));
    }

    //---------------------------------------------------------------

    // toString use ToStringConfig

    /**
     * 将集合 <code>collection</code> 使用拼接配置 toStringConfig 拼接成字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <String>} list = new ArrayList{@code <>}();
     * list.add("feilong");
     * list.add("");
     * list.add("xinge");
     * 
     * ToStringConfig toStringConfig = new ToStringConfig(",");
     * toStringConfig.setIsJoinNullOrEmpty(false);
     * 
     * ConvertUtil.toString(list,toStringConfig);
     * </pre>
     * 
     * <b>输出:</b>
     * 
     * <pre class="code">
     * feilong,xinge
     * </pre>
     * 
     * <hr>
     * 
     * 你还可以使用这个方法来将集合<b>换行输出</b>,比如:
     * 
     * <pre class="code">
     * List{@code <String>} list = toList("飞龙", "小金", "四金", "金金金金");
     * 
     * ToStringConfig toStringConfig = new ToStringConfig(System.lineSeparator());
     * LOGGER.debug(ConvertUtil.toString(list, toStringConfig));
     * </pre>
     * 
     * <b>输出:</b>
     * 
     * <pre class="code">
     * 飞龙
     * 小金
     * 四金
     * 金金金金
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>关于 default {@link ToStringConfig}:</h3>
     * <blockquote>
     * 
     * <p>
     * 如果参数 <code>toStringConfig</code> 是null,则使用默认的规则:<br>
     * </p>
     * 
     * <ol>
     * <li>连接符使用{@link ToStringConfig#DEFAULT_CONNECTOR}</li>
     * <li>拼接null或者empty元素</li>
     * <li>如果元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>关于 {@link ToStringConfig} prefix(since 1.12.9):</h3>
     * <blockquote>
     * 
     * <p>
     * 如果你需要在拼接每个元素的时候,给每个元素加上前缀, 比如 solr 查询, 运营人员配置了一些商品code ,<code>1533312,1533292,1785442</code>, 此时你需要生成
     * <code>code:1533312 OR code:1533292 OR code:1785442</code> 字符串去solr 中查询
     * </p>
     * 
     * <p>
     * 此时你可以这么调用
     * </p>
     * 
     * <pre>
     * 
     * String configItemCodes = "1533312,1533292,1785442";
     * String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray(configItemCodes, ",");
     * List{@code <String>} list = toList(tokenizeToStringArray);
     * ToStringConfig toStringConfig = new ToStringConfig(" OR ", false, "code:");
     * String string = ConvertUtil.toString(list, toStringConfig);
     * </pre>
     * 
     * <b>返回: </b>
     * 
     * <p>
     * code:1533312 OR code:1533292 OR code:1785442
     * </p>
     * 
     * </blockquote>
     * 
     * @param collection
     *            集合,建议元素泛型不要使用自定义的对象(比如UserCommand等),因为这个方法是迭代collection,拿每个元素的字符串格式 进行拼接
     * @param toStringConfig
     *            连接字符串 实体
     * @return 如果 <code>collection</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>toStringConfig</code> 是null,使用默认 {@link ToStringConfig#DEFAULT_CONNECTOR}以及 joinNullOrEmpty 进行连接<br>
     *         都不是null,会循环,拼接toStringConfig.getConnector()
     * @see "org.springframework.util.StringUtils#collectionToDelimitedString(Collection, String, String, String)"
     * @see org.apache.commons.collections4.IteratorUtils#toString(Iterator)
     * @see org.apache.commons.lang3.StringUtils#join(Iterable, String)
     * @since 1.8.4 change param order
     */
    public static String toString(final Collection<?> collection,ToStringConfig toStringConfig){
        return isNullOrEmpty(collection) ? EMPTY : toString(collection.toArray(), toStringConfig);
    }

    /**
     * 将数组 <code>arrays</code> 通过{@link ToStringConfig} 拼接成字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toString(toArray("a","b"),new ToStringConfig())       =   "a,b"
     * 
     * ToStringConfig toStringConfig=new ToStringConfig(",");
     * toStringConfig.setIsJoinNullOrEmpty(false);
     * ConvertUtil.toString(toArray("a","b",null),new ToStringConfig())  =   "a,b"
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>如果原始类型数组需要转换:</h3>
     * <blockquote>
     * 
     * <p>
     * 需要先使用下列的方式先转成包装类型数组
     * </p>
     * 
     * <ol>
     * <li>{@link ArrayUtils#toObject(boolean[])}</li>
     * <li>{@link ArrayUtils#toObject(byte[])}</li>
     * <li>{@link ArrayUtils#toObject(char[])}</li>
     * <li>{@link ArrayUtils#toObject(double[])}</li>
     * <li>{@link ArrayUtils#toObject(float[])}</li>
     * <li>{@link ArrayUtils#toObject(int[])}</li>
     * <li>{@link ArrayUtils#toObject(long[])}</li>
     * <li>{@link ArrayUtils#toObject(short[])}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>关于 default {@link ToStringConfig}:</h3>
     * <blockquote>
     * 
     * <p>
     * 如果参数 <code>toStringConfig</code> 是null,则使用默认的规则:<br>
     * </p>
     * 
     * <ol>
     * <li>连接符使用{@link ToStringConfig#DEFAULT_CONNECTOR}</li>
     * <li>拼接null或者empty元素</li>
     * <li>如果元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>关于 {@link ToStringConfig} prefix(since 1.12.9):</h3>
     * <blockquote>
     * 
     * <p>
     * 如果你需要在拼接每个元素的时候,给每个元素加上前缀, 比如 solr 查询, 运营人员配置了一些商品code ,<code>1533312,1533292,1785442</code>, 此时你需要生成
     * <code>code:1533312 OR code:1533292 OR code:1785442</code> 字符串去solr 中查询
     * </p>
     * 
     * <p>
     * 此时你可以这么调用
     * </p>
     * 
     * <pre>
     * 
     * String configItemCodes = "1533312,1533292,1785442";
     * String{@code []} tokenizeToStringArray = StringUtil.tokenizeToStringArray(configItemCodes, ",");
     * ToStringConfig toStringConfig = new ToStringConfig(" OR ", false, "code:");
     * String string = ConvertUtil.toString(tokenizeToStringArray, toStringConfig);
     * </pre>
     * 
     * <b>返回: </b>
     * 
     * <p>
     * code:1533312 OR code:1533292 OR code:1785442
     * </p>
     * 
     * </blockquote>
     * 
     * @param arrays
     *            支持包装类型,<b>不直接支持</b>原始类型
     * @param toStringConfig
     *            the to string config
     * @return 如果 <code>arrays</code> 是null 或者Empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>toStringConfig</code> 是null,使用默认 {@link ToStringConfig#DEFAULT_CONNECTOR}以及 joinNullOrEmpty 进行连接<br>
     *         否则循环,拼接 {@link ToStringConfig#getConnector()}
     * @see org.apache.commons.lang3.builder.ToStringStyle
     * @see org.apache.commons.lang3.StringUtils#join(Object[], String)
     * @see org.apache.commons.lang3.StringUtils#join(Iterable, String)
     * @since 1.8.4 change param order
     */
    public static String toString(Object[] arrays,ToStringConfig toStringConfig){
        if (isNullOrEmpty(arrays)){
            return EMPTY;
        }

        //---------------------------------------------------------------
        ToStringConfig useToStringConfig = defaultIfNull(toStringConfig, new ToStringConfig());
        String connector = useToStringConfig.getConnector();
        boolean isJoinNullOrEmpty = useToStringConfig.getIsJoinNullOrEmpty();
        String prefix = useToStringConfig.getPrefix();

        //---------------------------------------------------------------
        StringBuilder sb = new StringBuilder();
        for (Object element : arrays){
            //如果是null或者empty,但是参数值是不拼接,那么跳过,继续循环
            if (isNullOrEmpty(element) && !isJoinNullOrEmpty){
                continue;
            }
            //---------------------------------------------------------------
            //since 1.12.9 support prefix
            sb.append(defaultIfNull(prefix, EMPTY));

            //value转换
            //注意:如果value是null,StringBuilder 将拼接 "null" 字符串,详见 java.lang.AbstractStringBuilder#append(String)
            sb.append(defaultIfNull(element, EMPTY)); //see StringUtils.defaultString(t)

            //---------------------------------------------------------------
            if (null != connector){//注意可能传过来的是换行符,不能使用Validator.isNullOrEmpty来判断
                sb.append(connector);//放心大胆的拼接 connector, 不判断是否是最后一个,最后会截取
            }
        }
        return StringUtil.substringWithoutLast(sb, connector);
    }

    //---------------------------------------------------------------

    /**
     * 将集合 <code>collection</code> 转成<code>Enumeration</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>一般情况,你可能不需要这个方法,不过在一些API的时候,需要<code>Enumeration</code>参数,此时调用这个方法来进行转换会比较方便</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toEnumeration(null) = Collections.emptyEnumeration()
     * </pre>
     * 
     * </blockquote>
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

    //---------------------------------------------------------------
    //toMap

    /**
     * 将 <code>key</code> 和 <code>value</code> 直接转成map.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回是的是 {@link LinkedHashMap}</li>
     * 
     * <li>
     * <p>
     * 非常适合单key的场景,比如
     * </p>
     * 
     * <pre class="code">
     * Map{@code <String, String>} paramMap = newHashMap();
     * paramMap.put("name", "jinxin");
     * request.setParamMap(paramMap);
     * </pre>
     * 
     * 上面的3行代码可以重写成
     * 
     * <pre class="code">
     * request.setParamMap(toMap("name", "jinxin"));
     * </pre>
     * 
     * <p>
     * 一行代码就搞定了,很简洁,有木有~~
     * </p>
     * 
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(ConvertUtil.toMap("张飞", "丈八蛇矛")));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {"张飞": "丈八蛇矛"}
     * </pre>
     * 
     * </blockquote>
     * 
     * 
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * <p>
     * 对于以下代码:
     * </p>
     * 
     * <pre class="code">
     * private List{@code <ShopCommand>} loadShopCommandList(){
     *     Map{@code <String, Object>} paraMap = newHashMap();
     *     paraMap.put("orgTypeId", OrgType.ID_SHOP_TYPE);
     * 
     *     return shopCommandDao.findShopListByOrgaTypeId(paraMap);
     * }
     * </pre>
     * 
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * private List{@code <ShopCommand>} loadShopCommandList(){
     *   Map{@code <String, Object>} paraMap = ConvertUtil.toMap("orgTypeId", <span style="color:red">(Object)</span> OrgType.ID_SHOP_TYPE);
     *     return shopCommandDao.findShopListByOrgaTypeId(paraMap);
     * }
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
     * @see java.util.Collections#singletonMap(Object, Object)
     * @see "com.google.common.collect.ImmutableMap#of(K, V)"
     * @since 1.7.1
     */
    public static <K, V> Map<K, V> toMap(K key,V value){
        Map<K, V> map = new LinkedHashMap<>();//不设置初始值 ,可能调用再PUT 这样浪费性能
        map.put(key, value);
        return map;
    }

    /**
     * 将 <code>key1</code> 和 <code>value1</code>/<code>key2</code> 和 <code>value2</code> 直接转成map.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回是的是 {@link LinkedHashMap}</li>
     * 
     * <li>
     * <p>
     * 非常适合2个key的场景,比如
     * </p>
     * 
     * <pre class="code">
     * Map{@code <String, String>} paramMap =newHashMap();
     * paramMap.put("name", "jinxin");
     * paramMap.put("age", "18");
     * request.setParamMap(paramMap);
     * </pre>
     * 
     * 上面的3行代码可以重写成
     * 
     * <pre class="code">
     * request.setParamMap(toMap("name", "jinxin", "age", "18"));
     * </pre>
     * 
     * <p>
     * 一行代码就搞定了,很简洁,有木有~~
     * </p>
     * 
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * <p>
     * 对于以下代码:
     * </p>
     * 
     * <pre class="code">
     * Map{@code <String, Long>} map = newHashMap();
     * map.put("itemId", itemId);
     * map.put("memberId", memberId);
     * memberFavoritesDao.findMemberFavoritesByMemberIdAndItemId(map);
     * </pre>
     * 
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * Map{@code <String, Long>} map = ConvertUtil.toMap("itemId", itemId, "memberId", memberId);
     * memberFavoritesDao.findMemberFavoritesByMemberIdAndItemId(map);
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param key1
     *            the key 1
     * @param value1
     *            the value 1
     * @param key2
     *            the key 2
     * @param value2
     *            the value 2
     * @return 将 <code>key1</code> 和 <code>value1</code>/<code>key2</code> 和 <code>value2</code> 直接转成map
     * @see org.apache.commons.lang3.ArrayUtils#toMap(Object[])
     * @see java.util.Collections#singletonMap(Object, Object)
     * @see "com.google.common.collect.ImmutableMap#of(K, V)"
     * @since 1.9.5
     */
    public static <K, V> Map<K, V> toMap(K key1,V value1,K key2,V value2){
        Map<K, V> map = toMap(key1, value1);
        map.put(key2, value2);
        return map;
    }

    /**
     * 将 <code>properties</code> 转换成map.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Properties properties = new Properties();
     * 
     * properties.setProperty("name", "feilong");
     * properties.setProperty("age", "18");
     * properties.setProperty("country", "china");
     * 
     * LOGGER.debug(JsonUtil.format(toMap(properties)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "age": "18",
     * "country": "china",
     * "name": "feilong"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的map 经过了 {@link SortUtil#sortMapByKeyAsc(Map)}排序处理,方便输出日志</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>关于 <code>Properties</code></h3>
     * 
     * <blockquote>
     * <p>
     * Properties 是 Map的实现类 ,其 key和value 是String 类型.
     * </p>
     * 
     * <p>
     * 因此Properties 可以强制转换成map
     * </p>
     * </blockquote>
     * 
     * @param properties
     *            the properties
     * @return 如果 <code>properties</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see org.apache.commons.collections4.MapUtils#toProperties(Map)
     * @since 1.7.1
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, String> toMap(Properties properties){
        if (isNullOrEmpty(properties)){
            return emptyMap();
        }
        return sortMapByKeyAsc((Map) properties);//为了log方便,使用 treeMap
    }

    /**
     * 将诸如 Map{@code <String, String>} 类型转成 Map{@code <String, Integer>} 类型.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>适合只是简单的将key value类型转换,而不需要自己再构建Transformer,再去调用 {@link #toMap(Map, Transformer, Transformer)} ,简化操作</li>
     * <li>返回的是 {@link LinkedHashMap},顺序依照入参 inputMap</li>
     * <li>返回的是新的map,原来的<code>toMap</code>参数不受影响</li>
     * <li>也支持诸如 Map{@code <String, Integer>} 转 Map{@code <String, String>} (key和value 使用不同的转换器)</li>
     * <li>也支持诸如 Map{@code <String, String>} 转 Map{@code <String, Integer[]>} (单值转数组)</li>
     * <li>也支持诸如 Map{@code <String[], String[]>} 转 Map{@code <String[], Long[]>} (数组转数组)</li>
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景1:</b> 将Map{@code <String, String>} 转 Map{@code <String, Integer>} 类型
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = toMap("1", "2");
     * Map{@code <String, Integer>} returnMap = toMap(map, Integer.class);
     * 
     * <span style="color:green">// 输出测试</span>
     * for (Map.Entry{@code <String, Integer>} entry : returnMap.entrySet()){
     *     String key = entry.getKey();
     *     Integer value = entry.getValue();
     *     LOGGER.debug("key:[{}],value:[{}]", key, value);
     * }
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key:["1"],value:[2]
     * </pre>
     * 
     * <hr>
     * 
     * <p>
     * <b>场景2:</b> Map{@code <String, String>} 转 Map{@code <String, Integer[]>}
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = toMap("1", "2,2");
     * 
     * <span style="color:green">//key和value转成不同的类型</span>
     * Map{@code <String, Integer[]>} returnMap = toMap(map,  Integer[].class);
     * 
     * <span style="color:green">// 输出测试</span>
     * for (Map.Entry{@code <String, Integer[]>} entry : returnMap.entrySet()){
     *     String key = entry.getKey();
     *     Integer[] value = entry.getValue();
     * 
     *     LOGGER.debug("key:[{}],value:[{}]", key, value);
     * }
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key:["1"],value:[[2, 2]]
     * </pre>
     * 
     * <hr>
     * 
     * <p>
     * <b>场景3:</b> Map{@code <String[], String[]>} 转 Map{@code <String[], Long[]>}
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String[], String[]>} map = toMap(toArray("1"), toArray("2", "8"));
     * 
     * <span style="color:green">//key和value转成不同的类型</span>
     * Map{@code <String[], Long[]>} returnMap = toMap(map, Long[].class);
     * 
     * assertThat(returnMap, allOf(hasEntry(toArray("1"), toArray(2L, 8L))));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param <J>
     *            返回的map ,value的类型
     * @param inputMap
     *            the input map
     * @param valueTargetType
     *            value 需要转换成什么类型,类型可以和原map的类型相同或者可以设置为null,均表示返回的map使用<code>inputMap</code>原样的<code>value</code>,不会进行类型转换
     * @return 如果 <code>inputMap</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>valueTargetType</code> 是null,那么value 直接使用<code>inputMap</code>的 value<br>
     * @see #toMap(Map, Class, Class)
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/661">issues661</a>
     * @since 1.10.5
     */
    public static <K, V, J> Map<K, J> toMap(Map<K, V> inputMap,final Class<J> valueTargetType){
        return toMap(inputMap, null, valueTargetType);
    }

    /**
     * 将诸如 Map{@code <String, String>} 类型转成 Map{@code <Integer, Integer>} 类型.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>适合只是简单的将key value类型转换,而不需要自己再构建Transformer,再去调用 {@link #toMap(Map, Transformer, Transformer)} ,简化操作</li>
     * <li>返回的是 {@link LinkedHashMap},顺序依照入参 inputMap</li>
     * <li>返回的是新的map,原来的<code>toMap</code>参数不受影响</li>
     * <li>也支持诸如 Map{@code <String, Integer>} 转 Map{@code <Integer, String>} (key和value 使用不同的转换器)</li>
     * <li>也支持诸如 Map{@code <String, String>} 转 Map{@code <Integer, Integer[]>} (单值转数组)</li>
     * <li>也支持诸如 Map{@code <String[], String[]>} 转 Map{@code <Integer[], Long[]>} (数组转数组)</li>
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景1:</b> 将Map{@code <String, String>} 转 Map{@code <Integer, Integer>} 类型
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = toMap("1", "2");
     * Map{@code <Integer, Integer>} returnMap = toMap(map, Integer.class, Integer.class);
     * 
     * <span style="color:green">// 输出测试</span>
     * for (Map.Entry{@code <Integer, Integer>} entry : returnMap.entrySet()){
     *     Integer key = entry.getKey();
     *     Integer value = entry.getValue();
     *     LOGGER.debug("key:[{}],value:[{}]", key, value);
     * }
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key:[1],value:[2]
     * </pre>
     * 
     * <hr>
     * 
     * <p>
     * <b>场景2:</b> Map{@code <String, String>} 转 Map{@code <Integer, Integer[]>}
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = toMap("1", "2,2");
     * 
     * <span style="color:green">//key和value转成不同的类型</span>
     * Map{@code <Integer, Integer[]>} returnMap = toMap(map, Integer.class, Integer[].class);
     * 
     * <span style="color:green">// 输出测试</span>
     * for (Map.Entry{@code <Integer, Integer[]>} entry : returnMap.entrySet()){
     *     Integer key = entry.getKey();
     *     Integer[] value = entry.getValue();
     * 
     *     LOGGER.debug("key:[{}],value:[{}]", key, value);
     * }
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key:[1],value:[[2, 2]]
     * </pre>
     * 
     * <hr>
     * 
     * <p>
     * <b>场景3:</b> Map{@code <String[], String[]>} 转 Map{@code <Integer[], Long[]>}
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String[], String[]>} map = toMap(toArray("1"), toArray("2", "8"));
     * 
     * <span style="color:green">//key和value转成不同的类型</span>
     * Map{@code <Integer[], Long[]>} returnMap = toMap(map, Integer[].class, Long[].class);
     * 
     * assertThat(returnMap, allOf(hasEntry(toArray(1), toArray(2L, 8L))));
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param <I>
     *            返回的map ,key的类型
     * @param <J>
     *            返回的map ,value的类型
     * @param inputMap
     *            the input map
     * @param keyTargetType
     *            key需要转换成什么类型,类型可以和原map的类型相同或者可以设置为null,均表示返回的map使用<code>inputMap</code>原样的<code>key</code>,不会进行类型转换
     * @param valueTargetType
     *            value 需要转换成什么类型,类型可以和原map的类型相同或者可以设置为null,均表示返回的map使用<code>inputMap</code>原样的<code>value</code>,不会进行类型转换
     * @return 如果 <code>inputMap</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keyTargetType</code> 是null,那么key直接使用<code>inputMap</code>的key<br>
     *         如果 <code>valueTargetType</code> 是null,那么value 直接使用<code>inputMap</code>的 value<br>
     * @see #toMap(Map, Transformer, Transformer)
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/497">issues497</a>
     * @since 1.9.2
     */
    public static <K, V, I, J> Map<I, J> toMap(Map<K, V> inputMap,final Class<I> keyTargetType,final Class<J> valueTargetType){
        if (isNullOrEmpty(inputMap)){
            return emptyMap();
        }

        Transformer<K, I> keyTransformer = null == keyTargetType ? null : new SimpleClassTransformer<K, I>(keyTargetType);
        Transformer<V, J> valueTransformer = null == valueTargetType ? null : new SimpleClassTransformer<V, J>(valueTargetType);

        return toMap(inputMap, keyTransformer, valueTransformer);
    }

    /**
     * 将诸如 Map{@code <String, String>} 类型转成 Map{@code <Integer, Integer>} 类型.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>适合复杂的类型转换场景,如果只是简单的类型转换,你可以直接调用 {@link #toMap(Map, Class, Class)}</li>
     * <li>返回的是 {@link LinkedHashMap},顺序依照入参 inputMap</li>
     * <li>返回的是新的map,原来的<code>toMap</code>参数不受影响</li>
     * <li>也支持诸如 Map{@code <String, Integer>} 转 Map{@code <Integer, String>} (key和value 使用不同的转换器)</li>
     * <li>也支持诸如 Map{@code <String, String>} 转 Map{@code <Integer, Integer[]>} (单值转数组)</li>
     * <li>也支持诸如 Map{@code <String[], String[]>} 转 Map{@code <Integer[], Long[]>} (数组转数组)</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景1:</b> 将Map{@code <String, String>} 转 Map{@code <Integer, Integer>} 类型
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = toMap("1", "2");
     * 
     * <span style="color:green">//key和value 都转成integer 使用相同的转换器</span>
     * Transformer{@code <String, Integer>} transformer = new SimpleClassTransformer{@code <>}(Integer.class);
     * 
     * Map{@code <Integer, Integer>} returnMap = toMap(map, transformer, transformer);
     * 
     * <span style="color:green">// 输出测试</span>
     * for (Map.Entry{@code <Integer, Integer>} entry : returnMap.entrySet()){
     *     Integer key = entry.getKey();
     *     Integer value = entry.getValue();
     *     LOGGER.debug("key:[{}],value:[{}]", key, value);
     * }
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key:[1],value:[2]
     * </pre>
     * 
     * <hr>
     * 
     * <p>
     * <b>场景2:</b> Map{@code <String, String>} 转 Map{@code <Integer, Integer[]>}
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = toMap("1", "2,2");
     * 
     * Transformer{@code <String, Integer>} keyTransformer = new SimpleClassTransformer{@code <>}(Integer.class);
     * Transformer{@code <String, Integer[]>} valueTransformer = new SimpleClassTransformer{@code <>}(Integer[].class);
     * 
     * <span style="color:green">//key和value转成不同的类型</span>
     * Map{@code <Integer, Integer[]>} returnMap = toMap(map, keyTransformer, valueTransformer);
     * 
     * <span style="color:green">// 输出测试</span>
     * for (Map.Entry{@code <Integer, Integer[]>} entry : returnMap.entrySet()){
     *     Integer key = entry.getKey();
     *     Integer[] value = entry.getValue();
     * 
     *     LOGGER.debug("key:[{}],value:[{}]", key, value);
     * }
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key:[1],value:[[2, 2]]
     * </pre>
     * 
     * <hr>
     * 
     * <p>
     * <b>场景3:</b> Map{@code <String[], String[]>} 转 Map{@code <Integer[], Long[]>}
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String[], String[]>} map = toMap(toArray("1"), toArray("2", "8"));
     * 
     * Transformer{@code <String[], Integer[]>} keyTransformer = new SimpleClassTransformer{@code <>}(Integer[].class);
     * Transformer{@code <String[], Long[]>} valueTransformer = new SimpleClassTransformer{@code <>}(Long[].class);
     * 
     * <span style="color:green">//key和value转成不同的类型</span>
     * Map{@code <Integer[], Long[]>} returnMap = toMap(map, keyTransformer, valueTransformer);
     * 
     * assertThat(returnMap, allOf(hasEntry(toArray(1), toArray(2L, 8L))));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param <I>
     *            返回的map ,key的类型
     * @param <J>
     *            返回的map ,value的类型
     * @param inputMap
     *            the input map
     * @param keyTransformer
     *            key 转换器,如果是null,那么key直接使用<code>inputMap</code>的key<br>
     * @param valueTransformer
     *            value 转换器,如果是null,那么value直接使用<code>inputMap</code>的value<br>
     * @return 如果 <code>inputMap</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keyTransformer</code> 是null,那么key直接使用<code>inputMap</code>的key<br>
     *         如果 <code>valueTransformer</code> 是null,那么value 直接使用<code>inputMap</code>的 value<br>
     * @see org.apache.commons.collections4.MapUtils#transformedMap(Map, Transformer, Transformer)
     * @see org.apache.commons.collections4.map.TransformedMap#transformedMap(Map, Transformer, Transformer)
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/497">issues497</a>
     * @since 1.9.2
     */
    @SuppressWarnings("unchecked")
    public static <K, V, I, J> Map<I, J> toMap(
                    Map<K, V> inputMap,
                    final Transformer<K, I> keyTransformer,
                    final Transformer<V, J> valueTransformer){
        if (isNullOrEmpty(inputMap)){
            return emptyMap();
        }

        //---------------------------------------------------------------

        Map<I, J> returnMap = new LinkedHashMap<>(inputMap.size());

        for (Map.Entry<K, V> entry : inputMap.entrySet()){
            K key = entry.getKey();
            V value = entry.getValue();

            returnMap.put(
                            null == keyTransformer ? (I) key : keyTransformer.transform(key),
                            null == valueTransformer ? (J) value : valueTransformer.transform(value));
        }
        return returnMap;
    }

    /**
     * 将 <code>mapEntryCollection</code> 转成map ({@link LinkedHashMap}).
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>返回是的是 {@link LinkedHashMap},顺序依照参数 <code>mapEntryCollection</code>,key是 {@link java.util.Map.Entry#getKey()},value 是
     * {@link java.util.Map.Entry#getValue()}</li>
     * <li>{@link java.util.Map.Entry} 已知实现类,你可以使用 {@link Pair},或者 {@link java.util.AbstractMap.SimpleEntry}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>{@link Pair} 示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = toMap(toList(//
     *                 Pair.of("张飞", "丈八蛇矛"),
     *                 Pair.of("关羽", "青龙偃月刀"),
     *                 Pair.of("赵云", "龙胆枪"),
     *                 Pair.of("刘备", "双股剑")));
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "张飞": "丈八蛇矛",
     * "关羽": "青龙偃月刀",
     * "赵云": "龙胆枪",
     * "刘备": "双股剑"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>{@link java.util.AbstractMap.SimpleEntry} 示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = ConvertUtil.toMap(
     *                 toList(
     *                                 new SimpleEntry{@code <>}("张飞", "丈八蛇矛"),
     *                                 new SimpleEntry{@code <>}("关羽", "青龙偃月刀"),
     *                                 new SimpleEntry{@code <>}("赵云", "龙胆枪"),
     *                                 new SimpleEntry{@code <>}("刘备", "双股剑")));
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "张飞": "丈八蛇矛",
     * "关羽": "青龙偃月刀",
     * "赵云": "龙胆枪",
     * "刘备": "双股剑"
     * }
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
     *         如果 <code>mapEntryCollection</code> 有元素是null,将会抛出异常 {@link IllegalArgumentException}
     * @see org.apache.commons.lang3.ArrayUtils#toMap(Object[])
     * @since 1.7.1
     */
    public static <V, K, E extends Map.Entry<K, V>> Map<K, V> toMap(Collection<E> mapEntryCollection){
        if (null == mapEntryCollection){
            return emptyMap();
        }

        Validate.noNullElements(mapEntryCollection, "mapEntryCollection can't has null elememt!");

        Map<K, V> map = newLinkedHashMap(mapEntryCollection.size());
        for (Map.Entry<K, V> entry : mapEntryCollection){
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * 将 {@link java.util.Map.Entry}数组转成map ({@link LinkedHashMap}).
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>返回是的是 {@link LinkedHashMap},顺序依照参数 {@link java.util.Map.Entry}数组顺序,key是 {@link java.util.Map.Entry#getKey()},value 是
     * {@link java.util.Map.Entry#getValue()}</li>
     * <li>{@link java.util.Map.Entry} 已知实现类,你可以使用 {@link Pair},或者 {@link java.util.AbstractMap.SimpleEntry}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>{@link Pair} 示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = ConvertUtil.toMapUseEntrys(
     *                 Pair.of("张飞", "丈八蛇矛"),
     *                 Pair.of("关羽", "青龙偃月刀"),
     *                 Pair.of("赵云", "龙胆枪"),
     *                 Pair.of("刘备", "双股剑"));
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
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
     * Map{@code <String, String>} map = ConvertUtil.toMapUseEntrys(
     *                 new SimpleEntry{@code <>}("张飞", "丈八蛇矛"),
     *                 new SimpleEntry{@code <>}("关羽", "青龙偃月刀"),
     *                 new SimpleEntry{@code <>}("赵云", "龙胆枪"),
     *                 new SimpleEntry{@code <>}("刘备", "双股剑"));
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * <b>返回:</b>
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
     * <blockquote>
     * <p>
     * 以前初始化全局map的时候,你可能会这么写
     * </p>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// 除数和单位的map,必须是有顺序的 从大到小.</span>
     * private static final Map{@code <Long, String>} DIVISOR_AND_UNIT_MAP = new LinkedHashMap{@code <>}();
     * 
     * static{
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_TB, "TB");<span style=
    "color:green">//(Terabyte,太字节,或百万兆字节)=1024GB,其中1024=2^10(2的10次方)</span>
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_GB, "GB");<span style="color:green">//(Gigabyte,吉字节,又称“千兆”)=1024MB</span>
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_MB, "MB");<span style="color:green">//(Megabyte,兆字节,简称“兆”)=1024KB</span>
     *     DIVISOR_AND_UNIT_MAP.put(FileUtils.ONE_KB, "KB");<span style="color:green">//(Kilobyte 千字节)=1024B</span>
     * }
     * 
     * </pre>
     * 
     * <b>现在你可以重构成:</b>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// 除数和单位的map,必须是有顺序的 从大到小.</span>
     * private static final Map{@code <Long, String>} DIVISOR_AND_UNIT_MAP = ConvertUtil.toMapUseEntrys(
     *                 Pair.of(FileUtils.ONE_TB, "TB"), <span style="color:green">//(Terabyte,太字节,或百万兆字节)=1024GB,其中1024=2^10(2的10次方) </span>
     *                 Pair.of(FileUtils.ONE_GB, "GB"), <span style="color:green">//(Gigabyte,吉字节,又称“千兆”)=1024MB</span>
     *                 Pair.of(FileUtils.ONE_MB, "MB"), <span style="color:green">//(Megabyte,兆字节,简称“兆”)=1024KB</span>
     *                 Pair.of(FileUtils.ONE_KB, "KB")); <span style="color:green">//(Kilobyte 千字节)=1024B</span>
     * 
     * </pre>
     * 
     * <p>
     * 代码更加简洁
     * </p>
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
     *         如果 <code>entrys</code> 有元素是null,将会抛出异常 {@link IllegalArgumentException}
     * @see org.apache.commons.lang3.tuple.ImmutablePair#ImmutablePair(Object, Object)
     * @see org.apache.commons.lang3.tuple.Pair#of(Object, Object)
     * @since 1.7.1
     * @since 1.9.5 change name
     */
    @SafeVarargs
    public static <V, K> Map<K, V> toMapUseEntrys(Map.Entry<K, V>...mapEntrys){
        if (null == mapEntrys){
            return emptyMap();
        }
        Validate.noNullElements(mapEntrys, "mapEntrys can't has null elememt!");

        Map<K, V> map = newLinkedHashMap(mapEntrys.length);
        for (Map.Entry<K, V> entry : mapEntrys){
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    //-------------------------toProperties--------------------------------------

    /**
     * 将map转成 {@link Properties}.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>由于 Properties 只能保存非空的key和value,因此如果map 有key或者value是null,将会抛出{@link NullPointerException}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = toMap("name", "feilong");
     * Properties properties = ConvertUtil.toProperties(map);
     * 
     * LOGGER.debug(JsonUtil.format(properties));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {"name": "feilong"}
     * </pre>
     * 
     * </blockquote>
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

    //--------------------------toList-------------------------------------

    /**
     * 将枚举 <code>enumeration</code> 转成 {@link List}.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * toList((Enumeration{@code <String>}) null) = emptyList()
     * </pre>
     * 
     * </blockquote>
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
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>此方法很适合快速的将set转成list这样的操作</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Set{@code <String>} set = new LinkedHashSet{@code <>}();
     * Collections.addAll(set, "a", "a", "b", "b");
     * LOGGER.debug("{}", toList(set));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [a,b]
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
     * @see org.apache.commons.collections4.IterableUtils#toList(Iterable)
     * @see org.apache.commons.collections4.IteratorUtils#toList(Iterator)
     * @since 1.6.1
     */
    public static <T> List<T> toList(final Collection<T> collection){
        if (null == collection){
            return Collections.<T> emptyList();
        }
        return collection instanceof List ? (List<T>) collection : new ArrayList<T>(collection);
    }

    /**
     * 数组转成 ({@link java.util.ArrayList ArrayList}).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>此方法返回的list可以进行add等操作</li>
     * <li>如果直接使用{@link java.util.Arrays#asList(Object...) Arrays#asList(Object...)}返回的list没有实现 {@link java.util.Collection#add(Object)
     * Collection#add(Object)}等方法,执行<code>list.add("c")</code>;操作的话会导致异常!</li>
     * <li>而本方法使用 {@link ArrayList#ArrayList(java.util.Collection)} 来进行重新封装返回,可以执行正常的list操作</li>
     * </ol>
     * </blockquote>
     * 
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
     * List{@code <String>} list = new ArrayList{@code <>}();
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
     * List{@code <String>} list = toList("feilong1", "feilong2", "feilong2", "feilong3");
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
     * List{@code <UserAddress>} userAddresseList = new ArrayList{@code <>}();
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
     * @param <T>
     *            the generic type
     * @param arrays
     *            T数组
     * @return 如果 <code>arrays</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     * 
     *         <span style="color:red">提别提醒:注意动态数组(Varargs)的陷阱:</span>
     * 
     *         <pre class="code">
     *         assertEquals(emptyList(), <span style="color:red">toList((User[]) null)</span>);
     *         
     *         <span style="color:green">//-------------------------------------------------</span>
     * 
     *         List{@code <User>} list = newArrayList();
     *         list.add(null);
     * 
     *         assertEquals(list, <span style="color:red">toList((User) null)</span>);
     *         </pre>
     * 
     *         否则返回 {@code new ArrayList<T>(Arrays.asList(arrays));}
     * @see java.util.Arrays#asList(Object...)
     * @see java.util.Collections#singleton(Object)
     * @see java.util.Collections#addAll(Collection, Object...)
     * @see java.util.Collections#singletonList(Object)
     * @see "org.springframework.util.CollectionUtils#arrayToList(Object)"
     */
    @SafeVarargs
    public static <T> List<T> toList(T...arrays){
        return isNullOrEmpty(arrays) ? Collections.<T> emptyList() : new ArrayList<>(Arrays.asList(arrays));
    }

    //---------------------------toSet------------------------------------

    /**
     * 数组转成 Set ({@link java.util.LinkedHashSet LinkedHashSet}).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>此方法返回的是{@link java.util.LinkedHashSet LinkedHashSet}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>特别适合:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 如果你要通过以下方式来构造Set:
     * </p>
     * 
     * <pre class="code">
     * 
     * Set{@code <String>} set = new LinkedHashSet{@code <>}();
     * set.add("feilong1");
     * set.add("feilong2");
     * set.add("feilong2");
     * set.add("feilong3");
     * 
     * </pre>
     * 
     * 此时你可以使用:
     * 
     * <pre class="code">
     * Set{@code <String>} set = toSet("feilong1", "feilong2", "feilong2", "feilong3");
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
     * 有很多时候,参数需要一个对象Set,构造的时候,你需要这样
     * </p>
     * 
     * <pre class="code">
     * Set{@code <UserAddress>} userAddresseSet = new LinkedHashSet{@code <>}();
     * UserAddress userAddress = new UserAddress();
     * userAddress.setAddress("上海");
     * userAddresseSet.add(userAddress);
     * </pre>
     * 
     * 你可以重构成:
     * 
     * <pre class="code">
     * UserAddress userAddress = new UserAddress();
     * userAddress.setAddress("上海");
     * Set{@code <UserAddress>} userAddresseSet = toSet(userAddress);
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @return 如果 <code>arrays</code> 是null或者empty,返回 {@link Collections#emptySet()}<br>
     * 
     *         <span style="color:red">提别提醒:注意动态数组(Varargs)的陷阱:</span>
     * 
     *         <pre class="code">
     *         assertEquals(emptySet(), toSet(<span style="color:red">(User[]) null</span>));
     *         
     *         <span style="color:green">//-------------------------------------------------</span>
     * 
     *         Set{@code <User>} set = new LinkedHashSet<>();
     *         set.add(null);
     * 
     *         assertEquals(list, toSet(<span style="color:red">(User) null</span>));
     *         </pre>
     * 
     *         否则返回 {@code new LinkedHashSet<T>(Arrays.asList(arrays));}
     * @see "com.google.common.collect.Sets#newHashSet(E...)"
     * @since 1.9.6
     */
    @SafeVarargs
    public static <T> Set<T> toSet(T...arrays){
        return isNullOrEmpty(arrays) ? Collections.<T> emptySet() : new LinkedHashSet<>(Arrays.asList(arrays));
    }

    //----------------------------toArray-----------------------------------

    /**
     * 将动态数组转成数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * String[] array = ConvertUtil.toArray("1", "2");                  =   ["1", "2"];
     * 
     * String[] emptyArray = ConvertUtil.{@code <String>}toArray();     =   [] ; //= new String[] {};
     * Integer[] emptyArray = ConvertUtil.{@code <Integer>}toArray();   =   [] ; //= new Integer[] {};
     * 
     * <span style="color:red">//注意</span>
     * String[] nullArray = ConvertUtil.toArray(null)                   =   null;
     * ConvertUtil.toArray((String) null)                               =   new String[] { null }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <p>
     * 数组是具体化的(reified),而泛型在运行时是被擦除的(erasure)。<br>
     * 数组是在运行时才去判断数组元素的类型约束,而泛型正好相反,在运行时,泛型的类型信息是会被擦除的,只有编译的时候才会对类型进行强化。
     * </p>
     * 
     * <b>泛型擦除的规则:</b>
     * 
     * <ol>
     * <li>所有参数化容器类都被擦除成非参数化的(raw type); 如 List{@code <E>}、List{@code <List<E>>}都被擦除成 List</li>
     * <li>所有参数化数组都被擦除成非参数化的数组;如 List{@code <E>}[],被擦除成 List[]</li>
     * <li>Raw type 的容器类,被擦除成其自身,如 List{@code <E>}被擦 除成 List</li>
     * <li>原生类型(int,String 还有 wrapper 类)都擦除成他们的自身</li>
     * <li>参数类型 E,如果没有上限,则被擦除成 Object</li>
     * <li>所有约束参数如{@code <? Extends E>}、{@code <X extends E>}都被擦 除成 E</li>
     * <li>如果有多个约束,擦除成第一个,如{@code <T extends Object & E>},则擦除成 Object</li>
     * </ol>
     * 
     * <p>
     * 这将会导致下面的代码:
     * </p>
     * 
     * <pre class="code">
     * 
     * public static {@code <K, V>} Map{@code <K, V[]>} toArrayValueMap(Map{@code <K, V>} singleValueMap){
     *     Map{@code <K, V[]>} arrayValueMap = newLinkedHashMap(singleValueMap.size());//保证顺序和参数singleValueMap顺序相同
     *     for (Map.Entry{@code <K, V>} entry : singleValueMap.entrySet()){
     *         arrayValueMap.put(entry.getKey(), toArray(entry.getValue()));//注意此处的Value不要声明成V,否则会变成Object数组
     *     }
     *     return arrayValueMap;
     * }
     * </pre>
     * 
     * 调用的时候,
     * 
     * <pre class="code">
     * Map{@code <String, String>} singleValueMap = MapUtil.newLinkedHashMap(2);
     * singleValueMap.put("province", "江苏省");
     * singleValueMap.put("city", "南通市");
     * 
     * Map{@code <String, String[]>} arrayValueMap = MapUtil.toArrayValueMap(singleValueMap);
     * String[] strings = arrayValueMap.get("province");//此时返回的是 Object[]
     * </pre>
     * 
     * 会出现异常
     * 
     * <pre class="code">
     * java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
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
     * @since commons-lang 3
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
     * List{@code <String>} list = new ArrayList{@code <>}();
     * list.add("xinge");
     * list.add("feilong");
     * </pre>
     * 
     * 以前你需要写成:
     * 
     * <pre class="code">
     * list.toArray(new String[list.size()]);
     * </pre>
     * 
     * 现在你只需要写成:
     * 
     * <pre class="code">
     * String[] array = ConvertUtil.toArray(list, String.class);
     * LOGGER.info(JsonUtil.format(array));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * ["xinge","feilong"]
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
     * @return 如果 <code>collection</code> 是null,直接返回null<br>
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
     * 
     * @see org.apache.commons.collections4.IteratorUtils#toArray(Iterator,Class)
     * @see "org.springframework.util.StringUtils#toStringArray(Collection)"
     * @since 1.2.2
     */
    public static <T> T[] toArray(Collection<T> collection,Class<T> arrayComponentType){
        if (null == collection){ // since 1.8.6
            return null;
        }

        Validate.notNull(arrayComponentType, "arrayComponentType must not be null");

        // 如果采用大家常用的把a的length设为0,就需要反射API来创建一个大小为size的数组,而这对性能有一定的影响.
        // 所以最好的方式就是直接把a的length设为Collection的size从而避免调用反射API来达到一定的性能优化.
        T[] array = ArrayUtil.newArray(arrayComponentType, collection.size());

        //注意,toArray(new Object[0]) 和 toArray() 在功能上是相同的. 
        return collection.toArray(array);
    }

    /**
     * 将字符串数组 <code>toBeConvertedValue</code> 转成指定类型 <code>targetType</code> 的数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * String[] ss = { "2", "1" };
     * toArray(ss, Long.class);                                     =   new Long[] { 2L, 1L }
     * 
     * ConvertUtil.toArray((String[]) null, Serializable.class)     =   null
     * </pre>
     * 
     * </blockquote>
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
        //如果指定的类型 本身就是数组类型的class,那么返回的类型就是该数组类型,否则将基于指定类型构造数组.
        return null == toBeConvertedValue ? null : (T[]) ConvertUtils.convert(toBeConvertedValue, targetType);
    }

    //---------------------------------------------------------------

    /**
     * 将 <code>toBeConvertedValue</code> 转成{@link String}数组.
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * 
     * <li>
     * 
     * <p>
     * 该方法很适合将 <b>非字符串数组的数组</b> 转换成 <b>字符串数组</b>,比如
     * </p>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * URL[] urls = {
     *                URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing0/"),
     *                URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing1/"),
     *                URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing2/"),
     *                null };
     * 
     * LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(urls)));
     * </pre>
     * 
     * <b>返回:</b>
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
     * <p>
     * 还有诸如 Integer[] 转成 String[]
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toStrings(new Integer[] { 1, 2, 5 })     =   [ "1", "2", "5" ]
     * </pre>
     * 
     * </blockquote>
     * 
     * </li>
     * 
     * <li>
     * <p>
     * 也可以将字符串 解析成数组 in the Java language into a <code>List</code> individual Strings for each element, 根据以下规则:
     * </p>
     * 
     * <h3>字符串转成数组的规则:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 参见 {@link org.apache.commons.beanutils.converters.AbstractArrayConverter#parseElements(String) parseElements}
     * </p>
     * 
     * <ul>
     * <li>The string is expected to be a comma-separated list of values.</li>
     * <li>自动去除开头的 <b>'{'</b> 和 结束的<b>'}'</b>.</li>
     * <li>每个元素前后的<b>空格将会去除</b>.</li>
     * <li>Elements in the list may be delimited by single or double quotes.
     * Within a quoted elements, the normal Java escape sequences are valid.</li>
     * </ul>
     * 
     * <p>
     * 示例:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toStrings("{5,4, 8,2;8 9_5@3`a}"); =  ["5","4","8","2","8","9","5","3","a"]
     * </pre>
     * 
     * </blockquote>
     * 
     * </li>
     * </ol>
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
     * 将 <code>toBeConvertedValue</code>转成{@link Iterator}类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * <span style="color:green">// null</span>
     * toIterator(null) = null
     * 
     * <span style="color:green">//PrimitiveArray</span>
     * int[] i2 = { 1, 2 };
     * Iterator{@code <Integer>} iterator = toIterator(i2);
     * 
     * <span style="color:green">//逗号分隔的字符串</span>
     * Iterator{@code <String>} iterator = toIterator("1,2");
     * 
     * <span style="color:green">//collection</span>
     * List{@code <String>} list = new ArrayList{@code <>}();
     * list.add("aaaa");
     * list.add("nnnnn");
     * 
     * Iterator{@code <String>} iterator = toIterator(list);
     * 
     * <span style="color:green">//Enumeration</span>
     * Enumeration{@code <Object>} enumeration = new StringTokenizer("this is a test");
     * Iterator{@code <String>} iterator = toIterator(enumeration);
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>支持以下类型:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>逗号分隔的字符串,先使用{@link ConvertUtil#toStrings(Object)} 转成数组</li>
     * <li>数组(包括 包装类型数组 以及 原始类型数组)</li>
     * <li>如果是{@link java.util.Map},将 {@link java.util.Map#values()} 转成{@link java.util.Iterator}</li>
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
     *            the to be converted value
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回null<br>
     *         如果 <code>toBeConvertedValue</code> 是字符串,先转成数组,再转成迭代器<br>
     *         否则转成 {@link IteratorUtils#getIterator(Object)}
     * @see Collection#iterator()
     * @see EnumerationIterator#EnumerationIterator(Enumeration)
     * @see IteratorUtils#asIterator(Enumeration)
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
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 该方法<b>不适合</b> list转换成字符串,比如:
     * </p>
     * 
     * <pre class="code">
     * ConvertUtil.toString(toList("张飞", "关羽", "", "赵云"), String.class) = "张飞"
     * </pre>
     * 
     * <p>
     * ,请使用 {@link #toString(Collection, ToStringConfig)}
     * </p>
     * 
     * <hr>
     * 
     * <p>
     * 该方法也<b>不适合</b> array 转换成字符串,比如:
     * </p>
     * 
     * <pre class="code">
     * Integer[] int1 = { 2, null, 1, null };
     * LOGGER.debug(ConvertUtil.toString(int1),String.class);        = 2
     * </pre>
     * 
     * <p>
     * 请使用 {@link #toString(Object[], ToStringConfig)}
     * </p>
     * 
     * </blockquote>
     * 
     * <h3>对于 Array 转成 String:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 参见 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToString(Object) ArrayConverter#convertToString(Object)} <br>
     * 
     * 在转换的过程中,如果发现object是数组,将使用 {@link java.lang.reflect.Array#get(Object, int) Array#get(Object, int)}来获得数据,<br>
     * 如果发现不是数组,将会将object转成集合 {@link org.apache.commons.beanutils.converters.ArrayConverter#convertToCollection(Class, Object)
     * ArrayConverter#convertToCollection(Class, Object)}再转成迭代器 {@link java.util.Collection#iterator() Collection.iterator()}
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
     * 
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * 
     * <tr valign="top">
     * <td>int defaultSize</td>
     * <td>指定构建的默认数组的大小 or if less than zero indicates that a <code>null</code> default value should be used.</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>char delimiter = ','</td>
     * <td>分隔符,转成的string中的元素分隔符</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>char[] allowedChars = new char[] {'.', '-'}</td>
     * <td>用于{@link java.io.StreamTokenizer}分隔字符串</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>boolean onlyFirstToString = true;</td>
     * <td>只转第一个值</td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param toBeConvertedValue
     *            需要被转换的对象/值
     * @param targetType
     *            要转成什么类型
     * @return 如果 <code>targetType</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>toBeConvertedValue</code> 是null,那么直接返回null<br>
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

    //---------------------------------------------------------------

    /**
     * 将对象转成 {@link Locale}.
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
     *            可以是 <b>null</b> ,<b>字符串</b> 或者 直接的 {@link Locale}对象
     * @return 如果 <code>locale</code> 是null,返回 null<br>
     *         如果 <code>locale instanceof <span style="color:green">Locale</span></code>,返回 <code>(Locale) locale</code><br>
     *         如果 <code>locale instanceof <span style="color:green">String</span></code>,返回 {@link LocaleUtils#toLocale(String)}<br>
     *         其他的类型,将抛出 {@link UnsupportedOperationException}
     * @see org.apache.commons.lang3.LocaleUtils#toLocale(String)
     * @since 1.7.2
     */
    public static Locale toLocale(Object locale){
        if (null == locale){
            return null;
        }

        //---------------------------------------------------------------
        if (locale instanceof Locale){
            return (Locale) locale;
        }
        if (locale instanceof String){
            return LocaleUtils.toLocale((String) locale);
        }

        //---------------------------------------------------------------
        throw new UnsupportedOperationException("input param [locale] type is:[" + locale.getClass().getName() + "] not support!");
    }
}