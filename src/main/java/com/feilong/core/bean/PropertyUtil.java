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

import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 对 {@link org.apache.commons.beanutils.PropertyUtils}的再次封装.
 * 
 * <h3>说明:</h3>
 * <blockquote>
 * <ol>
 * <li>目的是将原来的 checkedException 异常 转换成 {@link BeanOperationException}</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>{@link PropertyUtils}与 {@link BeanUtils}:</h3>
 * 
 * <blockquote>
 * <p>
 * {@link PropertyUtils}类和{@link BeanUtils}类很多的方法在参数上都是相同的,但返回值不同.<br>
 * {@link BeanUtils}着重于"Bean",返回值通常是{@link String},<br>
 * 而{@link PropertyUtils}着重于属性,它的返回值通常是{@link Object}. 
 * </p>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.beanutils.PropertyUtils
 * @see com.feilong.core.bean.BeanUtil
 * @since 1.0.0
 */
public final class PropertyUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER                        = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * 传入的bean 是null 的消息提示.
     * 
     * @since 2.1.0
     */
    private static final String MESSAGE_BEAN_IS_NULL          = "bean can't be null!";

    /**
     * 传入的propertyName 是blank 的消息提示.
     * 
     * @since 2.1.0
     */
    private static final String MESSAGE_PROPERTYNAME_IS_BLANK = "propertyName can't be blank!";
    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private PropertyUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 将 <code>fromObj</code> 中的全部或者一组属性的值,复制到 <code>toObj</code> 对象中.
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>如果 <code>toObj</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>如果 <code>fromObj</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>对于Date类型,<span style="color:red">不需要先注册converter</span></li>
     * <li>这种copy都是 <span style="color:red">浅拷贝</span>,复制后的2个Bean的同一个属性可能拥有同一个对象的ref,这个在使用时要小心,特别是对于属性为自定义类的情况 .</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>使用示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * User oldUser = new User();
     * oldUser.setId(5L);
     * oldUser.setMoney(new BigDecimal(500000));
     * oldUser.setDate(now());
     * oldUser.setNickName(ConvertUtil.toArray("feilong", "飞天奔月", "venusdrogon"));
     * 
     * User newUser = new User();
     * PropertyUtil.copyProperties(newUser, oldUser, "date", "money", "nickName");
     * LOGGER.debug(JsonUtil.format(newUser));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     *         "date": "2015-09-06 13:27:43",
     *         "id": 0,
     *         "nickName":         [
     *             "feilong",
     *             "飞天奔月",
     *             "venusdrogon"
     *         ],
     *         "age": 0,
     *         "name": "feilong",
     *         "money": 500000,
     *         "userInfo": {"age": 0}
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 对于以下代码:
     * </p>
     * 
     * <pre class="code">
     * 
     * private ContactCommand toContactCommand(ShippingInfoSubForm shippingInfoSubForm){
     *     ContactCommand contactCommand = new ContactCommand();
     *     contactCommand.setCountryId(shippingInfoSubForm.getCountryId());
     *     contactCommand.setProvinceId(shippingInfoSubForm.getProvinceId());
     *     contactCommand.setCityId(shippingInfoSubForm.getCityId());
     *     contactCommand.setAreaId(shippingInfoSubForm.getAreaId());
     *     contactCommand.setTownId(shippingInfoSubForm.getTownId());
     *     return contactCommand;
     * }
     * 
     * </pre>
     * 
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * 
     * private ContactCommand toContactCommand(ShippingInfoSubForm shippingInfoSubForm){
     *     ContactCommand contactCommand = new ContactCommand();
     *     PropertyUtil.copyProperties(contactCommand, shippingInfoSubForm, "countryId", "provinceId", "cityId", "areaId", "townId");
     *     return contactCommand;
     * }
     * </pre>
     * 
     * <p>
     * 可以看出,代码更精简,目的性更明确
     * </p>
     * 
     * </blockquote>
     * 
     * <h3>{@link BeanUtils#copyProperties(Object, Object)}与 {@link PropertyUtils#copyProperties(Object, Object)}区别</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>{@link BeanUtils#copyProperties(Object, Object) BeanUtils} 提供类型转换功能,即发现两个JavaBean的同名属性为不同类型时,在支持的数据类型范围内进行转换,<br>
     * 而 {@link PropertyUtils#copyProperties(Object, Object) PropertyUtils}不支持这个功能,但是速度会更快一些.</li>
     * <li>commons-beanutils v1.9.0以前的版本 BeanUtils不允许对象的属性值为 null,PropertyUtils可以拷贝属性值 null的对象.<br>
     * (<b>注:</b>commons-beanutils v1.9.0+修复了这个情况,BeanUtilsBean.copyProperties() no longer throws a ConversionException for null properties
     * of certain data types),具体参阅commons-beanutils的
     * <a href="http://commons.apache.org/proper/commons-beanutils/javadocs/v1.9.2/RELEASE-NOTES.txt">RELEASE-NOTES.txt</a></li>
     * </ul>
     * </blockquote>
     * 
     * <h3>相比较直接调用 {@link PropertyUtils#copyProperties(Object, Object)}的优点:</h3>
     * <blockquote>
     * <ol>
     * <li>将 checkedException 异常转成了 {@link BeanOperationException} RuntimeException,因为通常copy的时候出现了checkedException,也是普普通通记录下log,没有更好的处理方式
     * </li>
     * <li>支持 includePropertyNames 参数,允许针对性copy 个别属性</li>
     * <li>更多,更容易理解的的javadoc</li>
     * </ol>
     * </blockquote>
     *
     * @param toObj
     *            目标对象
     * @param fromObj
     *            原始对象
     * @param includePropertyNames
     *            包含的属性数组名字数组,(can be nested/indexed/mapped/combo)<br>
     *            如果是null或者empty,将会调用 {@link PropertyUtils#copyProperties(Object, Object)}<br>
     *            <ol>
     *            <li>如果没有传入<code>includePropertyNames</code>参数,那么直接调用{@link PropertyUtils#copyProperties(Object, Object)},否则循环调用
     *            {@link #getProperty(Object, String)}再{@link #setProperty(Object, String, Object)}到<code>toObj</code>对象中</li>
     *            <li>如果传入的<code>includePropertyNames</code>,含有 <code>fromObj</code>没有的属性名字,将会抛出异常</li>
     *            <li>如果传入的<code>includePropertyNames</code>,含有 <code>fromObj</code>有,但是 <code>toObj</code>没有的属性名字,会抛出异常,see
     *            {@link PropertyUtilsBean#setSimpleProperty(Object, String, Object) copyProperties} Line2078</li>
     *            </ol>
     * @throws NullPointerException
     *             如果 <code>toObj</code> 是null,或者 <code>fromObj</code> 是null
     * @throws BeanOperationException
     *             如果在copy的过程中,有任何的checkedException,将会被转成该异常返回
     * @see #setProperty(Object, String, Object)
     * @see BeanUtil#copyProperties(Object, Object, String...)
     * @see org.apache.commons.beanutils.PropertyUtilsBean#copyProperties(Object, Object)
     * @see <a href="http://www.cnblogs.com/kaka/archive/2013/03/06/2945514.html">Bean复制的几种框架性能比较(Apache BeanUtils、PropertyUtils,Spring
     *      BeanUtils,Cglib BeanCopier)</a>
     * @since 1.4.1
     */
    public static void copyProperties(Object toObj,Object fromObj,String...includePropertyNames){
        Validate.notNull(toObj, "toObj [destination bean] not specified!");
        Validate.notNull(fromObj, "fromObj [origin bean] not specified!");

        //---------------------------------------------------------------

        if (isNullOrEmpty(includePropertyNames)){
            try{
                PropertyUtils.copyProperties(toObj, fromObj);
                return;
            }catch (Exception e){
                String pattern = "copyProperties exception,toObj:[{}],fromObj:[{}],includePropertyNames:[{}]";
                throw new BeanOperationException(Slf4jUtil.format(pattern, toObj, fromObj, includePropertyNames), e);
            }
        }

        //---------------------------------------------------------------
        for (String propertyName : includePropertyNames){
            Object value = getProperty(fromObj, propertyName);
            setProperty(toObj, propertyName, value);
        }
    }

    //---------------------------------------------------------------

    /**
     * 返回一个 <code>bean</code>中指定属性 <code>propertyNames</code><span style="color:green">可读属性</span>,并将属性名/属性值放入一个
     * {@link java.util.LinkedHashMap LinkedHashMap} 中.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 取到user bean里面所有的属性成map
     * </p>
     * 
     * <pre class="code">
     * User user = new User();
     * user.setId(5L);
     * user.setDate(now());
     * 
     * LOGGER.debug(JsonUtil.format(PropertyUtil.describe(user));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "id": 5,
     * "name": "feilong",
     * "age": null,
     * "date": "2016-07-13 22:18:26"
     * }
     * </pre>
     * 
     * <hr>
     * 
     * <p>
     * <b>场景:</b> 提取user bean "date"和 "id"属性:
     * </p>
     * 
     * <pre class="code">
     * User user = new User();
     * user.setId(5L);
     * user.setDate(now());
     * 
     * LOGGER.debug(JsonUtil.format(PropertyUtil.describe(user, "date", "id"));
     * </pre>
     * 
     * 返回的结果,按照指定参数名称顺序:
     * 
     * <pre class="code">
     * {
     * "date": "2016-07-13 22:21:24",
     * "id": 5
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>另外还有一个名为class的属性,属性值是Object的类名,事实上class是java.lang.Object的一个属性</li>
     * <li>如果 <code>propertyNames</code>是null或者 empty,那么获取所有属性的值</li>
     * <li>map的key按照 <code>propertyNames</code> 的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>原理:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>取到bean class的 {@link java.beans.PropertyDescriptor}数组</li>
     * <li>循环,找到 {@link java.beans.PropertyDescriptor#getReadMethod()}</li>
     * <li>将 name and {@link org.apache.commons.beanutils.PropertyUtilsBean#getProperty(Object, String)} 设置到map中</li>
     * </ol>
     * </blockquote>
     *
     * @param bean
     *            Bean whose properties are to be extracted
     * @param propertyNames
     *            属性名称 (can be nested/indexed/mapped/combo),参见 <a href="../BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>propertyNames</code> 是null或者empty,返回 {@link PropertyUtils#describe(Object)}<br>
     * @throws NullPointerException
     *             如果 <code>bean</code> 是null,或者<code>propertyNames</code> 包含 null的元素
     * @throws IllegalArgumentException
     *             如果 <code>propertyNames</code> 包含 blank的元素
     * @see org.apache.commons.beanutils.BeanUtils#describe(Object)
     * @see org.apache.commons.beanutils.PropertyUtils#describe(Object)
     * @since 1.8.0
     */
    public static Map<String, Object> describe(Object bean,String...propertyNames){
        Validate.notNull(bean, MESSAGE_BEAN_IS_NULL);

        //---------------------------------------------------------------
        if (isNullOrEmpty(propertyNames)){
            try{
                return PropertyUtils.describe(bean);
            }catch (Exception e){
                String pattern = "describe exception,bean:[{}],propertyNames:[{}]";
                throw new BeanOperationException(Slf4jUtil.format(pattern, bean, propertyNames), e);
            }
        }

        //---------------------------------------------------------------
        Map<String, Object> map = newLinkedHashMap(propertyNames.length);
        for (String propertyName : propertyNames){
            map.put(propertyName, getProperty(bean, propertyName));
        }
        return map;
    }

    //---------------------------------------------------------------

    /**
     * 使用 {@link PropertyUtils#setProperty(Object, String, Object)} 来设置指定bean对象中的指定属性的值.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>不会进行类型转换</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * User newUser = new User();
     * PropertyUtil.setProperty(newUser, "name", "feilong");
     * LOGGER.info(JsonUtil.format(newUser));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "age": 0,
     * "name": "feilong"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>如果 <code>bean</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}</li>
     * <li>如果<code>bean</code>没有传入的 <code>propertyName</code>属性名字,会抛出异常,see
     * {@link PropertyUtilsBean#setSimpleProperty(Object, String, Object) setSimpleProperty} Line2078,转成 {@link BeanOperationException}</li>
     * <li>对于Date类型,<span style="color:red">不需要先注册converter</span></li>
     * </ol>
     * </blockquote>
     *
     * @param bean
     *            Bean whose property is to be modified
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 <a href="../BeanUtil.html#propertyName">propertyName</a>
     * @param value
     *            Value to which this property is to be set
     * @see org.apache.commons.beanutils.BeanUtils#setProperty(Object, String, Object)
     * @see org.apache.commons.beanutils.PropertyUtils#setProperty(Object, String, Object)
     * @see BeanUtil#setProperty(Object, String, Object)
     */
    public static void setProperty(Object bean,String propertyName,Object value){
        Validate.notNull(bean, MESSAGE_BEAN_IS_NULL);
        Validate.notBlank(propertyName, MESSAGE_PROPERTYNAME_IS_BLANK);

        //---------------------------------------------------------------
        try{
            PropertyUtils.setProperty(bean, propertyName, value);
        }catch (Exception e){
            String pattern = "setProperty exception,bean:[{}],propertyName:[{}],value:[{}]";
            throw new BeanOperationException(Slf4jUtil.format(pattern, bean, propertyName, value), e);
        }
    }

    //---------------------------------------------------------------

    /**
     * 如果 <code>value</code> isNotNullOrEmpty,那么才调用 {@link #setProperty(Object, String, Object)}.
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>如果 <code>bean</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}</li>
     * <li>如果<code>bean</code>没有传入的 <code>propertyName</code>属性名字,会抛出异常,see
     * {@link PropertyUtilsBean#setSimpleProperty(Object, String, Object)} Line2078</li>
     * <li>对于Date类型,<span style="color:red">不需要先注册converter</span></li>
     * </ol>
     * </blockquote>
     *
     * @param bean
     *            Bean whose property is to be modified
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 <a href="../BeanUtil.html#propertyName">propertyName</a>
     * @param value
     *            Value to which this property is to be set
     * @since 1.5.3
     */
    public static void setPropertyIfValueNotNullOrEmpty(Object bean,String propertyName,Object value){
        if (isNotNullOrEmpty(value)){
            setProperty(bean, propertyName, value);
        }
    }

    /**
     * 如果 <code>null != value</code>,那么才调用 {@link #setProperty(Object, String, Object)}.
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>如果 <code>bean</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}</li>
     * <li>如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}</li>
     * <li>如果<code>bean</code>没有传入的 <code>propertyName</code>属性名字,会抛出异常,see
     * {@link PropertyUtilsBean#setSimpleProperty(Object, String, Object)} Line2078</li>
     * <li>对于Date类型,<span style="color:red">不需要先注册converter</span></li>
     * </ol>
     * </blockquote>
     *
     * @param bean
     *            Bean whose property is to be modified
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 <a href="../BeanUtil.html#propertyName">propertyName</a>
     * @param value
     *            Value to which this property is to be set
     * @see #setProperty(Object, String, Object)
     * @since 1.5.3
     */
    public static void setPropertyIfValueNotNull(Object bean,String propertyName,Object value){
        if (null != value){
            setProperty(bean, propertyName, value);
        }
    }

    //---------------------------------------------------------------

    // [start] getProperty

    /**
     * 使用 {@link PropertyUtils#getProperty(Object, String)} 从指定bean对象中取得指定属性名称的值.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>原样取出值,不会进行类型转换.</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * <p>
     * <b>场景:</b> 取list中第一个元素的id
     * </p>
     * 
     * <pre class="code">
     * User user = new User();
     * user.setId(5L);
     * user.setDate(now());
     * 
     * List{@code <User>} list = toList(user, user, user);
     * 
     * Long id = PropertyUtil.getProperty(list, "[0].id");
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * 5
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param bean
     *            Bean whose property is to be extracted
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 <a href="../BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>bean</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         否则 使用{@link PropertyUtils#getProperty(Object, String)} 从对象中取得属性值
     * @see BeanUtil#getProperty(Object, String)
     * @see org.apache.commons.beanutils.BeanUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtilsBean
     */
    public static <T> T getProperty(Object bean,String propertyName){
        Validate.notNull(bean, MESSAGE_BEAN_IS_NULL);
        Validate.notBlank(propertyName, MESSAGE_PROPERTYNAME_IS_BLANK);

        return PropertyValueObtainer.obtain(bean, propertyName);
    }

    //---------------------------------------------------------------

    // [end]

    /**
     * 从指定的 <code>obj</code>中,查找指定类型 <code>toBeFindedClassType</code> 的值.
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>如果 <code>ClassUtil.isInstance(obj, toBeFindedClassType)</code> 直接返回 findValue</li>
     * <li>不支持obj是<code>isPrimitiveOrWrapper</code>,<code>CharSequence</code>,<code>Collection</code>,<code>Map</code>类型,自动过滤</li>
     * <li>调用 {@link PropertyUtil#describe(Object, String...)} 再递归查找</li>
     * <li>目前暂不支持从集合里面找到指定类型的值,如果你有相关需求,可以调用 "org.springframework.util.CollectionUtils#findValueOfType(Collection, Class)"</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从User中找到UserInfo类型的值
     * </p>
     * 
     * <pre class="code">
     * User user = new User();
     * user.setId(5L);
     * user.setDate(now());
     * user.getUserInfo().setAge(28);
     * 
     * LOGGER.info(JsonUtil.format(PropertyUtil.findValueOfType(user, UserInfo.class)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {"age": 28}
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param obj
     *            要被查找的对象
     * @param toBeFindedClassType
     *            the to be finded class type
     * @return 如果 <code>obj</code> 是null或者是empty,返回null<br>
     *         如果 <code>toBeFindedClassType</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>ClassUtil.isInstance(obj, toBeFindedClassType)</code>,直接返回 <code>obj</code><br>
     *         从对象中查找匹配的类型,如果找不到返回 <code>null</code><br>
     * @see "org.springframework.util.CollectionUtils#findValueOfType(Collection, Class)"
     * @since 1.4.1
     */
    @SuppressWarnings("unchecked")
    public static <T> T findValueOfType(Object obj,Class<T> toBeFindedClassType){
        if (isNullOrEmpty(obj)){
            return null;
        }
        Validate.notNull(toBeFindedClassType, "toBeFindedClassType can't be null/empty!");
        //---------------------------------------------------------------

        if (ClassUtil.isInstance(obj, toBeFindedClassType)){
            return (T) obj;
        }

        //---------------------------------------------------------------
        if (isDonotSupportFindType(obj)){
            LOGGER.trace("obj:[{}] not support find toBeFindedClassType:[{}]", obj.getClass().getName(), toBeFindedClassType.getName());
            return null;
        }

        //---------------------------------------------------------------
        Map<String, Object> describe = describe(obj);

        for (Map.Entry<String, Object> entry : describe.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if (null != value && !"class".equals(key)){
                //级联查询
                T t = findValueOfType(value, toBeFindedClassType);
                if (null != t){
                    return t;
                }
            }
        }
        return null;
    }

    //---------------------------------------------------------------

    /**
     * 一般自定义的command 里面 就是些 string int,list map等对象.
     * 
     * <p>
     * 这些我们过滤掉,只取类型是 findedClassType的
     * </p>
     * 
     * @param obj
     *            the obj
     * @return true, if checks if is can find type
     * @since 1.6.3
     */
    private static boolean isDonotSupportFindType(Object obj){
        //一般自定义的command 里面 就是些 string int,list map等对象
        //这些我们过滤掉,只取类型是 findedClassType的
        return ClassUtils.isPrimitiveOrWrapper(obj.getClass())//
                        || ClassUtil.isInstanceAnyClass(obj, CharSequence.class, Collection.class, Map.class);
    }
}