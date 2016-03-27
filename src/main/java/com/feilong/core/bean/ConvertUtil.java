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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

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
import org.apache.commons.collections4.iterators.EnumerationIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.feilong.core.Validator;
import com.feilong.core.lang.ArrayUtil;
import com.feilong.core.lang.StringUtil;

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
 * <th align="left">方法</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link ConvertUtils#convert(Object)}</td>
 * <td>将指定的value转成string.<br>
 * 如果value是array,将会返回数组第一个元素转成string.<br>
 * 将会使用注册的 <code>java.lang.String</code>{@link Converter},<br>
 * 允许应用定制 Object->String conversions(默认使用简单的使用 toString())<br>
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
 * see {@link #convert(String[], Class)}</td>
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
     * <span style="color:red">推荐使用 {@link BigDecimal#valueOf(double)}</span>,不建议使用 {@code new BigDecimal(double)},参见 JDK API<br>
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

    //*************************************************************************************************

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
     * 将集合 <code>collection</code>使用连接符号链接成字符串.
     * 
     * <h3>使用示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * List&lt;String&gt; list = new ArrayList&lt;String&gt;();
     * list.add(&quot;feilong&quot;);
     * list.add(&quot;&quot;);
     * list.add(&quot;xinge&quot;);
     * 
     * ToStringConfig toStringConfig = new ToStringConfig(&quot;,&quot;);
     * toStringConfig.setIsJoinNullOrEmpty(false);
     * 
     * assertEquals(&quot;feilong,xinge&quot;, ConvertUtil.toString(toStringConfig, list));
     * </pre>
     * 
     * </blockquote>
     * 
     * @param toStringConfig
     *            连接字符串 实体
     * @param collection
     *            集合, 建议基本类型泛型的结合,因为这个方法是直接循环collection 进行拼接
     * @return 如果 collection isNullOrEmpty,返回null<br>
     *         如果 toStringConfig 是null,默认使用 {@link ToStringConfig#DEFAULT_CONNECTOR} 进行连接<br>
     *         都不是null,会循环,拼接toStringConfig.getConnector()
     * @see #toString(ToStringConfig, Object...)
     * @see "org.springframework.util.StringUtils#collectionToDelimitedString(Collection, String, String, String)"
     * @since 1.4.0
     */
    public static String toString(ToStringConfig toStringConfig,final Collection<?> collection){
        if (Validator.isNullOrEmpty(collection)){
            return StringUtils.EMPTY;
        }
        Object[] array = collection.toArray();
        return toString(toStringConfig, array);
    }

    /**
     * 将数组 通过 {@link ToStringConfig} 拼接成 字符串.
     * 
     * <p style="color:green">
     * 支持包装类型以及原始类型,比如 Integer [] arrays 以及 int []arrays
     * </p>
     * 
     * <code>
     * <pre>
     * Example 1:
     * ArrayUtil.toString(new ToStringConfig(),"a","b")  return "a,b"
     * 
     * Example 2:
     * ToStringConfig toStringConfig=new ToStringConfig(",");
     * toStringConfig.setIsJoinNullOrEmpty(false);
     * ArrayUtil.toString(new ToStringConfig(),"a","b",null)  return "a,b"
     * 
     * Example 3:
     * int[] ints = { 2, 1 };
     * ArrayUtil.toString(new ToStringConfig(),ints) return "2,1"
     * </pre>
     * </code>
     *
     * @param toStringConfig
     *            the join string entity
     * @param arrays
     *            <span style="color:red">支持包装类型以及原始类型,比如 Integer []arrays 以及 int []arrays</span>
     * @return
     *         <ul>
     *         <li>如果 arrays 是null 或者Empty ,返回null</li>
     *         <li>否则循环,拼接 {@link ToStringConfig#getConnector()}</li>
     *         </ul>
     * @see org.apache.commons.lang3.builder.ToStringStyle
     * @since 1.4.0
     */
    public static String toString(ToStringConfig toStringConfig,Object...arrays){
        if (Validator.isNullOrEmpty(arrays)){
            return StringUtils.EMPTY;
        }

        //************************************************************************
        Object[] operateArray = toObjects(arrays);

        ToStringConfig useToStringConfig = null == toStringConfig ? new ToStringConfig() : toStringConfig;
        String connector = useToStringConfig.getConnector();
        //************************************************************************
        StringBuilder sb = new StringBuilder();

        for (Object obj : operateArray){
            //如果是null或者empty,但是参数值是不拼接,那么跳过,继续循环
            if (Validator.isNullOrEmpty(obj) && !useToStringConfig.getIsJoinNullOrEmpty()){
                continue;
            }

            //value转换,注意:如果 value是null,StringBuilder将拼接 "null" 字符串,详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(null == obj ? StringUtils.EMPTY : "" + obj); //see StringUtils.defaultString(t)

            if (null != connector){//注意可能传过来的是换行符 不能使用Validator.isNullOrEmpty来判断
                sb.append(connector);//放心大胆的拼接 connector, 不判断是否是最后一个,最后会截取
            }
        }
        return StringUtil.substringWithoutLast(sb, connector);
    }

    //**********************************************************************************************

    /**
     * 将集合转成枚举.
     * 
     * @param <T>
     *            the generic type
     * @param collection
     *            集合
     * @return Enumeration
     * @see Collections#enumeration(Collection)
     * @since 1.4.0
     */
    public static <T> Enumeration<T> toEnumeration(final Collection<T> collection){
        return Collections.enumeration(collection);
    }

    /**
     * 将枚举转成集合.
     * 
     * @param <T>
     *            the generic type
     * @param enumeration
     *            the enumeration
     * @return if Validator.isNullOrEmpty(enumeration), return {@link Collections#emptyList()},该emptyList不可以操作<br>
     *         else return {@link Collections#list(Enumeration)}
     * @see Collections#emptyList()
     * @see Collections#EMPTY_LIST
     * @see Collections#list(Enumeration)
     * @see EnumerationUtils#toList(Enumeration)
     * @since 1.0.7
     */
    public static <T> List<T> toList(final Enumeration<T> enumeration){
        if (Validator.isNullOrEmpty(enumeration)){
            return Collections.emptyList();
        }
        return Collections.list(enumeration);
    }

    /**
     * 数组转成 ({@link java.util.ArrayList ArrayList}),此方法返回的list可以进行add等操作.
     * <p>
     * 注意 :{@link java.util.Arrays#asList(Object...) Arrays#asList(Object...)}返回的list,没有实现 {@link java.util.Collection#add(Object)
     * Collection#add(Object)}等方法<br>
     * 因此,使用 {@link ArrayList#ArrayList(java.util.Collection)} 来进行重新封装返回
     * </p>
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            T数组
     * @return 数组转成 List(ArrayList)<br>
     *         if Validator.isNullOrEmpty(arrays), return null,else return {@code new ArrayList<T>(Arrays.asList(arrays));}
     * @see java.util.Arrays#asList(Object...)
     */
    public static <T> List<T> toList(T[] arrays){
        if (Validator.isNullOrEmpty(arrays)){
            return Collections.emptyList();
        }
        //如果直接使用 Arrays.asList(arrays)方法 返回的是Arrays类的内部类的对象ArrayList,没有实现AbstractList类的add方法,如果 strList.add("c");导致抛异常! 
        return new ArrayList<T>(Arrays.asList(arrays));
    }

    /**
     * 集合转成数组.
     *
     * @param <T>
     *            the generic type
     * @param collection
     *            collection
     * @param arrayComponentType
     *            数组组件类型的 Class
     * @return 数组,if null == collection or arrayClass == null,return NullPointerException
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
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(arrayComponentType, collection.size());

        //注意,toArray(new Object[0]) 和 toArray() 在功能上是相同的. 
        return collection.toArray(array);
    }

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
        if (isPrimitiveArray(o)){
            return primitiveArrayToObjectArray(o);
        }
        return arrays;
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

        if (!klass.isArray()){
            return false;
        }

        Class<?> componentType = klass.getComponentType();
        //原始型的
        return componentType.isPrimitive();
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
            Object element = ArrayUtil.getElement(primitiveArray, i);
            returnStringArray[i] = element;
        }
        return returnStringArray;
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
     * @see #convert(Object, Class)
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
     * @see #convert(Object, Class)
     * @since 1.4.0
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
     * @return
     *         <ul>
     *         <li>如果 null == object 返回null,</li>
     *         <li>否则转成 {@link IteratorUtils#getIterator(Object)}</li>
     *         </ul>
     * @see Collection#iterator()
     * @see EnumerationIterator#EnumerationIterator(Enumeration)
     * @see IteratorUtils#getIterator(Object)
     * @see "org.apache.taglibs.standard.tag.common.core.ForEachSupport#supportedTypeForEachIterator(Object)"
     * @since Commons Collections4.0
     */
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> toIterator(Object object){
        if (null == object){
            return null;
        }
        // 逗号分隔的字符串
        if (object instanceof String){
            return toIterator(toStrings(object));
        }
        return (Iterator<T>) IteratorUtils.getIterator(object);
    }

    /**
     * 将value转成指定Class类型的对象,如果Class的转换器没有注册,那么传入的value原样返回.
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>如果传的 value是 <code>value.getClass().isArray()</code> 或者 {@link Collection},那么<span style="color:red">会取第一个元素</span>进行转换
     * {@link AbstractConverter#convert(Class, Object)} ,调用的 {@link AbstractConverter#convertArray(Object)} 方法</li>
     * <li>如果传的 value是 <code>null</code> ,那么返回null</li>
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
        if (null == value){
            return null;
        }
        return (T) ConvertUtils.convert(value, targetType);
    }

    /**
     * Convert an array of specified values to an array of objects of the specified class (if possible).
     * 
     * <p>
     * 如果传的 value是 <code>null</code> ,那么返回null
     * </p>
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
        if (null == values){
            return null;
        }
        return (T[]) ConvertUtils.convert(values, targetType);
    }
}