/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.Validator;

/**
 * 封装了 org.apache.commons.beanutils包下面的类.
 * 
 * 
 * <h3>关于类型转换:</h3>
 * 
 * <blockquote>
 * <p>
 * 这里使用偷懒的做法,调用了 {@link org.apache.commons.beanutils.ConvertUtilsBean#register(boolean, boolean, int) ConvertUtilsBean.register(boolean,
 * boolean, int)}方法<br>
 * 但是有后遗症,这是beanUtils核心公共的方法,可能会影响其他框架或者其他作者开发的代码<br>
 * 最正确的做法, 自定义的类,自己单独写 {@link org.apache.commons.beanutils.Converter},<br>
 * 而 公共的类 比如 下面方法里面的类型:
 * </p>
 * 
 * <ul>
 * <li>{@link ConvertUtilsBean#registerPrimitives(boolean) registerPrimitives(boolean throwException)}</li>
 * <li>{@link ConvertUtilsBean#registerStandard(boolean,boolean) registerStandard(boolean throwException, boolean defaultNull);}</li>
 * <li>{@link ConvertUtilsBean#registerOther(boolean) registerOther(boolean throwException);}</li>
 * <li>{@link ConvertUtilsBean#registerArrays(boolean,int) registerArrays(boolean throwException, int defaultArraySize);}</li>
 * </ul>
 * 
 * 最好在用的时候 自行register,{@link org.apache.commons.beanutils.ConvertUtilsBean#deregister(Class) ConvertUtilsBean.deregister(Class)}
 * 
 * <p>
 * Example 1:
 * </p>
 * 
 * <pre>
 * 
 * MyObject myObject = new MyObject();
 * myObject.setId(3l);
 * myObject.setName(&quot;My Name&quot;);
 * 
 * ConvertUtilsBean cub = new ConvertUtilsBean();
 * cub.deregister(Long.class);
 * cub.register(new MyLongConverter(), Long.class);
 * 
 * log.debug(cub.lookup(Long.class));
 * 
 * BeanUtilsBean bub = new BeanUtilsBean(cub, new PropertyUtilsBean());
 * 
 * String name = bub.getProperty(myObject, &quot;name&quot;);
 * log.debug(name);
 * String id = bub.getProperty(myObject, &quot;id&quot;);
 * log.debug(id);
 * 
 * </pre>
 * 
 * </blockquote>
 * 
 * 
 * <h3>{@link PropertyUtils}与 {@link BeanUtils}:</h3>
 * 
 * <blockquote>
 * <p>
 * {@link PropertyUtils}类和 {@link BeanUtils}类很多的方法在参数上都是相同的，但返回值不同。 <br>
 * BeanUtils着重于"Bean"，返回值通常是String,<br>
 * 而PropertyUtils着重于属性，它的返回值通常是Object。 
 * </p>
 * </blockquote>
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.0 2010-7-9 下午02:44:36
 * @version 1.0.2 2012-5-15 15:07
 * @version 1.0.7 2014年5月21日 下午12:24:53 move to om.feilong.commons.core.bean package
 * @version 1.0.8 2014-7-22 12:37 将异常转成 BeanUtilException 抛出
 * 
 * @see com.feilong.core.bean.PropertyUtil
 * 
 * @see java.beans.BeanInfo
 * @see java.beans.PropertyDescriptor
 * @see java.beans.MethodDescriptor
 * 
 * @see org.apache.commons.beanutils.BeanUtils
 * @see org.apache.commons.beanutils.Converter
 * @see org.apache.commons.beanutils.converters.DateConverter
 * @see org.apache.commons.beanutils.converters.DateTimeConverter
 * @see org.apache.commons.beanutils.converters.AbstractConverter
 * @see org.apache.commons.beanutils.ConvertUtils#register(org.apache.commons.beanutils.Converter, Class)
 * 
 * @see org.apache.commons.beanutils.ConvertUtilsBean#registerPrimitives(boolean)
 * @see org.apache.commons.beanutils.ConvertUtilsBean#registerStandard(boolean, boolean)
 * @see org.apache.commons.beanutils.ConvertUtilsBean#registerOther(boolean)
 * @see org.apache.commons.beanutils.ConvertUtilsBean#registerArrays(boolean, int)
 * 
 * @see "org.springframework.beans.BeanUtils"
 * 
 * @since 1.0.0
 */
public final class BeanUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

    /** Don't let anyone instantiate this class. */
    private BeanUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    static{
        //ConvertUtils.register(new DatePatternConverter(DatePattern.commonWithMillisecond), java.util.Date.class);
        //ConvertUtils.register(new DatePatternConverter(DatePattern.commonWithMillisecond), java.sql.Date.class);
        //ConvertUtils.register(new DatePatternConverter(DatePattern.commonWithMillisecond), java.sql.Timestamp.class);
        //ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);

        boolean throwException = false;
        boolean defaultNull = true;
        int defaultArraySize = 10;

        //XXX 
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        ConvertUtilsBean convertUtils = beanUtilsBean.getConvertUtils();

        convertUtils.register(throwException, defaultNull, defaultArraySize);
    }

    // [start] cloneBean

    /**
     * 调用 {@link BeanUtils#cloneBean(Object)}.<br>
     * 这个方法通过默认构造函数建立一个bean的新实例,然后拷贝每一个属性到这个新的bean中<br>
     * 
     * <p>
     * {@link BeanUtils#cloneBean(Object)} 在源码上看是调用了 getPropertyUtils().copyProperties(newBean, bean);<br>
     * 最后实际上还是<b>复制的引用 ，无法实现深clone</b><br>
     * </p>
     * 
     * <p>
     * 但还是可以帮助我们减少工作量的，假如类的属性不是基础类型的话（即自定义类），可以先clone出那个自定义类，在把他付给新的类，覆盖原来类的引用,<br>
     * 是为那些本身没有实现clone方法的类准备的 
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            Bean to be cloned
     * @return the cloned bean
     *         (复制的引用 ，无法实现深clone)
     * @throws BeanUtilException
     *             if IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException
     * @see org.apache.commons.beanutils.BeanUtils#cloneBean(Object)
     * @see org.apache.commons.beanutils.PropertyUtilsBean#copyProperties(Object, Object)
     * @since 1.0
     */
    public static <T> T cloneBean(T bean) throws BeanUtilException{
        try{
            //Clone a bean based on the available property getters and setters, even if the bean class itself does not implement Cloneable.
            @SuppressWarnings("unchecked")
            T cloneBean = (T) BeanUtils.cloneBean(bean);
            return cloneBean;
        }catch (Exception e){
            log.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [end]

    // [start] describe 把Bean的属性值放入到一个Map里面

    /**
     * 返回一个<code>bean</code>中所有的可读属性，并将属性名/属性值放入一个Map中.
     * 
     * <p>
     * 另外还有一个名为class的属性，属性值是Object的类名，事实上class是java.lang.Object的一个属性.
     * </p>
     * <p>
     * <span style="color:red">缺陷:<br>
     * 自己手工注册的ConvertUtils.register(dateTimeConverter, java.util.Date.class)不会生效</span><br>
     * 
     * 在赋值的时候,虽然调用了 {@link org.apache.commons.beanutils.BeanUtilsBean#getNestedProperty(Object, String)}, 虽然也调用了 ConvertUtilsBean来转换 <br>
     * 但是 {@link org.apache.commons.beanutils.ConvertUtilsBean#ConvertUtilsBean()} 默认的构造函数 是使用标准的转换
     * 
     * </p>
     * 
     * @param bean
     *            Bean whose properties are to be extracted
     * 
     * @return Map of property descriptors
     * 
     * @throws BeanUtilException
     *             if IllegalAccessException | InvocationTargetException | NoSuchMethodException
     * @see org.apache.commons.beanutils.BeanUtils#describe(Object)
     * @see org.apache.commons.beanutils.PropertyUtils#describe(Object)
     * @see PropertyUtil#describe(Object)
     * @see PropertyDescriptor
     * @see #populate(Object, Map)
     */
    public static Map<String, String> describe(Object bean) throws BeanUtilException{
        try{
            //Return the entire set of properties for which the specified bean provides a read method.
            Map<String, String> propertyMap = BeanUtils.describe(bean);
            return propertyMap;
        }catch (Exception e){
            log.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [end]

    // [start] populate 把properties/map里面的值放入bean中

    /**
     * 把properties/map里面的值放入bean中.
     * 
     * @param bean
     *            JavaBean whose properties are being populated
     * 
     * @param properties
     *            Map keyed by property name, with the corresponding (String or String[]) value(s) to be set
     * 
     * @throws BeanUtilException
     *             the bean util exception
     * @see org.apache.commons.beanutils.BeanUtils#populate(Object, Map)
     */
    public static void populate(Object bean,Map<String, ?> properties) throws BeanUtilException{
        try{
            BeanUtils.populate(bean, properties);
        }catch (Exception e){
            log.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [end]

    // [start] copyProperties
    /**
     * 对象Properties的复制,调用了 {@link BeanUtils#copyProperties(Object, Object)}.
     * 
     * <p>
     * 注意:这种copy都是浅拷贝，复制后的2个Bean的同一个属性可能拥有同一个对象的ref，<br>
     * 这个在使用时要小心，特别是对于属性为自定义类的情况 .
     * </p>
     * 
     * <h3>{@link BeanUtils#copyProperties(Object, Object)}与 {@link PropertyUtils#copyProperties(Object, Object)}区别</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>{@link BeanUtils#copyProperties(Object, Object)}能给不同的两个成员变量相同的，但类名不同的两个类之间相互赋值</li>
     * <li>{@link PropertyUtils#copyProperties(Object, Object)} 提供类型转换功能，即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换，而前者不支持这个功能，但是速度会更快一些.</li>
     * <li>commons-beanutils v1.9.0以前的版本 BeanUtils 不允许对象的属性值为 null，PropertyUtils 可以拷贝属性值 null 的对象.<br>
     * (<b>注:</b>commons-beanutils v1.9.0+修复了这个情况,BeanUtilsBean.copyProperties() no longer throws a ConversionException for null properties
     * of certain data types),具体信息,可以参阅commons-beanutils的
     * {@link <a href="http://commons.apache.org/proper/commons-beanutils/javadocs/v1.9.2/RELEASE-NOTES.txt">RELEASE-NOTES.txt</a>}</li>
     * </ul>
     * </blockquote>
     * 
     * @param toObj
     *            目标对象
     * @param fromObj
     *            原始对象
     * @throws BeanUtilException
     *             the bean util exception
     * @see org.apache.commons.beanutils.BeanUtils#copyProperties(Object, Object)
     * @see org.apache.commons.beanutils.BeanUtils#copyProperty(Object, String, Object)
     */
    public static void copyProperties(Object toObj,Object fromObj) throws BeanUtilException{
        if (null == toObj){
            throw new IllegalArgumentException("No destination bean/toObj specified");
        }
        if (null == fromObj){
            throw new IllegalArgumentException("No origin bean/fromObj specified");
        }
        try{
            BeanUtils.copyProperties(toObj, fromObj);
        }catch (Exception e){
            log.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    /**
     * 对象值的复制 {@code fromObj-->toObj}.
     * 
     * <pre>
     * 如果有java.util.Date 类型的 需要copy,那么 需要先这么着
     * DateConverter converter = new DateConverter(DatePattern.forToString, Locale.US);
     * ConvertUtils.register(converter, Date.class);
     * 或者 使用 内置的
     * ConvertUtils.register(new DateLocaleConverter(Locale.US, DatePattern.forToString), Date.class); *
     * 
     * BeanUtil.copyProperty(b, a, &quot;date&quot;);
     * </pre>
     * 
     * <pre>
     * 例如两个pojo:enterpriseSales和enterpriseSales_form 都含有字段&quot;enterpriseName&quot;,&quot;linkMan&quot;,&quot;phone&quot;
     * 通常写法
     * enterpriseSales.setEnterpriseName(enterpriseSales_form.getEnterpriseName());
     * enterpriseSales.setLinkMan(enterpriseSales_form.getLinkMan());
     * enterpriseSales.setPhone(enterpriseSales_form.getPhone());
     * 此时,可以使用
     * BeanUtil.copyProperties(enterpriseSales,enterpriseSales_form,new
     * String[]{&quot;enterpriseName&quot;,&quot;linkMan&quot;,&quot;phone&quot;});
     * </pre>
     * 
     * @param toObj
     *            目标对象
     * @param fromObj
     *            原始对象
     * @param filedNames
     *            字段数组, can't be null/empty!
     * @throws BeanUtilException
     *             the bean util exception
     * @see #copyProperty(Object, Object, String)
     */
    public static void copyProperties(Object toObj,Object fromObj,String[] filedNames) throws BeanUtilException{
        if (Validator.isNullOrEmpty(filedNames)){
            throw new NullPointerException("filedNames can't be null/empty!");
        }

        int length = filedNames.length;
        for (int i = 0; i < length; ++i){
            String filedName = filedNames[i];
            copyProperty(toObj, fromObj, filedName);
        }
    }

    // [end]

    // [start] copyProperty
    /**
     * 对象值的复制 {@code fromObj-->toObj}.
     * 
     * <pre>
     * 如果有java.util.Date 类型的 需要copy,那么 需要先这么着
     * DateConverter converter = new DateConverter(DatePattern.forToString, Locale.US);
     * ConvertUtils.register(converter, Date.class);
     * 或者 使用 内置的
     * ConvertUtils.register(new DateLocaleConverter(Locale.US, DatePattern.forToString), Date.class); *
     * 
     * BeanUtil.copyProperty(b, a, &quot;date&quot;);
     * </pre>
     * 
     * <pre>
     * 例如两个pojo:enterpriseSales和enterpriseSales_form 都含有字段&quot;enterpriseName&quot;
     * 通常写法
     * enterpriseSales.setEnterpriseName(enterpriseSales_form.getEnterpriseName());
     * 
     * 此时,可以使用
     * BeanUtil.copyProperty(enterpriseSales,enterpriseSales_form,&quot;enterpriseName&quot;);
     * </pre>
     * 
     * @param toObj
     *            目标对象
     * @param fromObj
     *            原始对象
     * @param filedName
     *            字段名称
     * @throws BeanUtilException
     *             the bean util exception
     * @see #getProperty(Object, String)
     * @see #copyProperty(Object, String, Object)
     */
    public static void copyProperty(Object toObj,Object fromObj,String filedName) throws BeanUtilException{
        Object value = getProperty(fromObj, filedName);
        copyProperty(toObj, filedName, value);
    }

    /**
     * bean中的成员变量name赋值为value.
     * 
     * <pre>
     * 如果有java.util.Date 类型的 需要copy,那么 需要先这么着
     * DateConverter converter = new DateConverter(DatePattern.forToString, Locale.US);
     * ConvertUtils.register(converter, Date.class);
     * BeanUtil.copyProperty(b, a, &quot;date&quot;);
     * </pre>
     * 
     * <pre>
     * 嵌套赋值: BeanUtils.copyProperty(a, &quot;sample.display&quot;, &quot;second one&quot;);
     * 
     * 功能和setProperty一样
     * 
     * 如果我们只是为bean的属性赋值的话,使用copyProperty()就可以了;
     * 而setProperty()方法是实现BeanUtils.populate()(后面会说到)机制的基础,也就是说如果我们需要自定义实现populate()方法,那么我们可以override setProperty()方法.
     * 所以,做为一般的日常使用,setProperty()方法是不推荐使用的.
     * 
     * </pre>
     * 
     * @param bean
     *            bean
     * @param propertyName
     *            成员Property name (can be nested/indexed/mapped/combo)
     * @param value
     *            赋值为value
     * @throws BeanUtilException
     *             the bean util exception
     * @see org.apache.commons.beanutils.BeanUtils#copyProperty(Object, String, Object)
     */
    public static void copyProperty(Object bean,String propertyName,Object value) throws BeanUtilException{
        try{
            BeanUtils.copyProperty(bean, propertyName, value);
        }catch (Exception e){
            log.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [end]

    // [start] setProperty

    /**
     * 使用 {@link BeanUtils#setProperty(Object, String, Object)} 来设置属性值(<b>会进行类型转换</b>).
     * 
     * <pre>
     * 
     * BeanUtils.setProperty(pt1, &quot;x&quot;, &quot;9&quot;); // 这里的9是String类型
     * PropertyUtils.setProperty(pt1, &quot;x&quot;, 9); // 这里的是int类型
     * // 这两个类BeanUtils和PropertyUtils,前者能自动将int类型转化，后者不能
     * </pre>
     * 
     * 
     * <pre>
     * {@code
     * getProperty和setProperty,它们都只有2个参数，第一个是JavaBean对象，第二个是要操作的属性名.
     * Company c = new Company();
     * c.setName("Simple");
     *  
     * 对于Simple类型，参数二直接是属性名即可
     * //Simple
     * log.debug(BeanUtils.getProperty(c, "name"));
     *  
     * 对于Map类型，则需要以“属性名（key值）”的形式
     * //Map
     *     log.debug(BeanUtils.getProperty(c, "address (A2)"));
     *     HashMap am = new HashMap();
     *     am.put("1","234-222-1222211");
     *     am.put("2","021-086-1232323");
     *     BeanUtils.setProperty(c,"telephone",am);
     * log.debug(BeanUtils.getProperty(c, "telephone (2)"));
     *  
     * 对于Indexed，则为“属性名[索引值]”，注意这里对于ArrayList和数组都可以用一样的方式进行操作.
     * //index
     *     log.debug(BeanUtils.getProperty(c, "otherInfo[2]"));
     *     BeanUtils.setProperty(c, "product[1]", "NOTES SERVER");
     *     log.debug(BeanUtils.getProperty(c, "product[1]"));
     *  
     * 当然这3种类也可以组合使用啦！
     * //nest
     *     log.debug(BeanUtils.getProperty(c, "employee[1].name"));
     * 
     * }
     * </pre>
     * 
     * @param bean
     *            Bean on which setting is to be performed
     * @param name
     *            Property name (can be nested/indexed/mapped/combo)
     * @param value
     *            Value to be set
     * @throws BeanUtilException
     *             if IllegalAccessException | InvocationTargetException
     * @see org.apache.commons.beanutils.BeanUtils#setProperty(Object, String, Object)
     * @see org.apache.commons.beanutils.PropertyUtils#setProperty(Object, String, Object)
     * @see com.feilong.core.bean.PropertyUtil#setProperty(Object, String, Object)
     */
    public static void setProperty(Object bean,String name,Object value) throws BeanUtilException{
        try{
            // BeanUtils支持把所有类型的属性都作为字符串处理
            // 在后台自动进行类型转换(字符串和真实类型的转换)
            BeanUtils.setProperty(bean, name, value);
        }catch (Exception e){
            log.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }

    // [end]

    // [start] getProperty

    /**
     * 使用 {@link BeanUtils#getProperty(Object, String)} 类从对象中取得属性值.
     * 
     * <h3>{@link BeanUtils#getProperty(Object, String) BeanUtils.getProperty}&{@link PropertyUtils#getProperty(Object, String)
     * PropertyUtils.getProperty}的区别:</h3>
     * 
     * <blockquote>
     * <p>
     * {@link BeanUtils#getProperty(Object, String)} 会将结果转成String返回,<br>
     * {@link PropertyUtils#getProperty(Object, String)} 结果是Object类型,不会做类型转换
     * </p>
     * </blockquote>
     * 
     * <h3>具体实现:</h3>
     * 
     * <pre>
     * {@code
     * getProperty和setProperty,它们都只有2个参数，第一个是JavaBean对象，第二个是要操作的属性名.
     * Company c = new Company();
     * c.setName("Simple");
     *  
     * 对于Simple类型，参数二直接是属性名即可
     * //Simple
     * log.debug(BeanUtils.getProperty(c, "name"));
     *  
     * 对于Map类型，则需要以“属性名（key值）”的形式
     * //Map
     *     log.debug(BeanUtils.getProperty(c, "address (A2)"));
     *     HashMap am = new HashMap();
     *     am.put("1","234-222-1222211");
     *     am.put("2","021-086-1232323");
     *     BeanUtils.setProperty(c,"telephone",am);
     * log.debug(BeanUtils.getProperty(c, "telephone (2)"));
     *  
     * 对于Indexed，则为“属性名[索引值]”，注意这里对于ArrayList和数组都可以用一样的方式进行操作.
     * //index
     *     log.debug(BeanUtils.getProperty(c, "otherInfo[2]"));
     *     BeanUtils.setProperty(c, "product[1]", "NOTES SERVER");
     *     log.debug(BeanUtils.getProperty(c, "product[1]"));
     *  
     * 当然这3种类也可以组合使用啦！
     * //nest
     *     log.debug(BeanUtils.getProperty(c, "employee[1].name"));
     * 
     * }
     * </pre>
     * 
     * 
     * @param bean
     *            bean
     * @param name
     *            属性名称
     * @return 使用BeanUtils类从对象中取得属性值
     * @throws BeanUtilException
     *             if IllegalAccessException | InvocationTargetException | NoSuchMethodException
     * @see org.apache.commons.beanutils.BeanUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtils#getProperty(Object, String)
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     */
    public static String getProperty(Object bean,String name) throws BeanUtilException{
        // Return the value of the specified property of the specified bean,
        // no matter which property reference format is used, as a String.
        try{
            String propertyValue = BeanUtils.getProperty(bean, name);
            return propertyValue;
        }catch (Exception e){
            log.error(e.getClass().getName(), e);
            throw new BeanUtilException(e);
        }
    }
    // [end]
}