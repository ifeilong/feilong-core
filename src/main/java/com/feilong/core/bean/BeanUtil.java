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
import static com.feilong.core.util.MapUtil.newHashMap;
import static java.util.Collections.emptyMap;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 对 {@link org.apache.commons.beanutils.BeanUtils}的再次封装.
 * 
 * <h3>说明:</h3>
 * <blockquote>
 * <ol>
 * <li>目的是将原来的 checkedException 异常 转换成 {@link BeanOperationException}</li>
 * </ol>
 * </blockquote>
 * 
 * 
 * <h3>{@link PropertyUtils}与 {@link BeanUtils}区别:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * BeanUtils.setProperty(pt1, &quot;x&quot;, &quot;9&quot;); <span style="color:green">// 这里的9是String类型</span>
 * PropertyUtils.setProperty(pt1, &quot;x&quot;, 9); <span style="color:green">// 这里的是int类型</span>
 * <span style="color:green">// 这两个类BeanUtils和PropertyUtils,前者能自动将int类型转化,后者不能</span>
 * </pre>
 * 
 * <p>
 * {@link PropertyUtils}类和 {@link BeanUtils}类很多的方法在参数上都是相同的,但返回值不同. <br>
 * BeanUtils着重于"Bean",返回值通常是String,<br>
 * 而PropertyUtils着重于属性,它的返回值通常是Object. 
 * </p>
 * </blockquote>
 * 
 * <h3><a name="propertyName">关于propertyName</a></h3>
 * 
 * <blockquote>
 * getProperty和setProperty,它们都只有2个参数,第一个是JavaBean对象,第二个是要操作的属性名.
 * 
 * <pre class="code">
 * Company company = new Company();
 * company.setName("Simple");
 * </pre>
 * 
 * <dl>
 * <dt>Simple类型(简单类型,如String Int)</dt>
 * <dd>
 * 对于Simple类型,参数二直接是属性名即可
 * 
 * <pre class="code">
 * LOGGER.debug(BeanUtils.getProperty(company, "name"));
 * </pre>
 * 
 * </dd>
 * 
 * <dt>Map类型</dt>
 * <dd>
 * 对于Map类型,则需要以"<span style="color:red">属性名(key值)</span>"的形式
 * 
 * <pre class="code">
 * LOGGER.debug(BeanUtils.getProperty(company, "address (A2)"));
 * 
 * Map{@code <String,String>} am = newHashMap();
 * am.put("1", "234-222-1222211");
 * am.put("2", "021-086-1232323");
 * 
 * BeanUtils.setProperty(company, "telephone", am);
 * LOGGER.debug(BeanUtils.getProperty(company, "telephone (2)"));
 * </pre>
 * 
 * </dd>
 * 
 * <dt>索引类型(Indexed),如 数组 arrayList</dt>
 * <dd>
 * 对于Indexed,则为"<span style="color:red">属性名[索引值]</span>",注意这里对于ArrayList和数组都可以用一样的方式进行操作.
 * 
 * <pre class="code">
 * LOGGER.debug(BeanUtils.getProperty(company, "otherInfo[2]"));
 * BeanUtils.setProperty(company, "product[1]", "NOTES SERVER");
 * LOGGER.debug(BeanUtils.getProperty(company, "product[1]"));
 * </pre>
 * 
 * </dd>
 * 
 * <dt>组合/嵌套(Nested)</dt>
 * <dd>
 * 当然这3种类也可以组合使用啦！
 * 
 * <pre class="code">
 * LOGGER.debug(BeanUtils.getProperty(company, "employee[1].name"));
 * </pre>
 * 
 * </dd>
 * </dl>
 * </blockquote>
 * 
 * <h3>关于 {@link BeanUtils#copyProperty(Object, String, Object) copyProperty} 和 {@link BeanUtils#setProperty(Object, String, Object)
 * setProperty}的区别:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * 两者功能相似,区别点在于:
 * copyProperty 不支持目标bean是索引类型,但是支持bean有索引类型的setter方法
 * copyProperty 不支持目标bean是Map类型,但是支持bean有Map类型的setter方法
 * 
 * 如果我们只是为bean的属性赋值的话,使用{@link BeanUtils#copyProperty(Object, String, Object)}就可以了;
 * 而{@link BeanUtils#setProperty(Object, String, Object)}方法是实现  {@link BeanUtils#populate(Object,Map)}机制的基础,也就是说如果我们需要自定义实现populate()方法,那么我们可以override {@link BeanUtils#setProperty(Object, String, Object)}方法.
 * 所以,做为一般的日常使用,{@link BeanUtils#setProperty(Object, String, Object)}方法是<span style="color:red">不推荐使用</span>的.
 * </pre>
 * 
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
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
 * 
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

    /** Don't let anyone instantiate this class. */
    private BeanUtil(){
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

    // [start] copyProperties

    /**
     * 将 <code class="code">fromObj</code> 中的全部或者一组属性 <code>includePropertyNames</code> 的值,复制到 <code>toObj</code> 对象中.
     * 
     * <p>
     * 如果需要copy的两个对象属性的<b>类型一样</b>,那么调用此方法会有<span style="color:red">性能消耗</span>,<span style="color:red"><b>强烈建议使用</b></span>
     * {@link PropertyUtil#copyProperties(Object, Object, String...)}
     * </p>
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>这种copy都是 <span style="color:red">浅拷贝</span>,复制后的2个Bean的同一个属性可能拥有同一个对象的ref,在使用时要小心,特别是对于属性为自定义类的情况 .</li>
     * <li>此方法调用了 {@link BeanUtils#copyProperties(Object, Object)},会自动进行{@code Object--->String--->Object}类型转换</li>
     * <li>如果指定了<code>includePropertyNames</code>,会调用 {@link #getProperty(Object, String)},在自动进行{@code Object--->String}
     * 类型转换过程中,如果发现值是数组,只会取第一个元素重新构造数组转到 <code>toObj</code>中,规则参见 {@link ConvertUtil#toString(Object)}</li>
     * <li>不支持<code>toObj</code>是map类型,从{@link BeanUtilsBean#copyProperties(Object, Object)}源码可以看出, <code class="code">fromObj</code>可以是map
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>使用示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 例如两个pojo: user和userForm 都含有字段"enterpriseName","linkMan","phone"
     * 
     * 通常写法:
     * .....
     * user.setEnterpriseName(userForm.getEnterpriseName());
     * user.setLinkMan(userForm.getLinkMan());
     * user.setPhone(userForm.getPhone());
     * ......
     * 
     * 此时,可以使用
     * BeanUtil.copyProperties(user,userForm,"enterpriseName","linkMan","phone");
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注册自定义 {@link Converter}:</h3>
     * 
     * <blockquote>
     * <p>
     * 如果有 {@link java.util.Date}类型的需要copy,那么需要先使用{@link ConvertUtils#register(Converter, Class)}方法:<br>
     * 
     * <code>ConvertUtils.register(new DateLocaleConverter(Locale.US, DatePattern.TO_STRING_STYLE),Date.class);</code>
     * </p>
     * </blockquote>
     * 
     * <h3>{@link BeanUtils#copyProperties(Object, Object)}与 {@link PropertyUtils#copyProperties(Object, Object)}区别</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>{@link BeanUtils#copyProperties(Object, Object)} 提供类型转换功能,即发现两个JavaBean的同名属性为不同类型时,在支持的数据类型范围内进行转换,而
     * {@link PropertyUtils#copyProperties(Object, Object)}不支持这个功能,但是速度会更快一些.</li>
     * <li>commons-beanutils v1.9.0以前的版本 BeanUtils不允许对象的属性值为 null,PropertyUtils可以拷贝属性值 null的对象.<br>
     * (<b>注:</b>commons-beanutils v1.9.0+修复了这个情况,BeanUtilsBean.copyProperties() no longer throws a ConversionException for null properties
     * of certain data types),具体信息,可以参阅commons-beanutils的
     * <a href="http://commons.apache.org/proper/commons-beanutils/javadocs/v1.9.2/RELEASE-NOTES.txt">RELEASE-NOTES.txt</a></li>
     * </ul>
     * </blockquote>
     * 
     * <h3>相比较直接调用 {@link BeanUtils#copyProperties(Object, Object)}的优点:</h3>
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
     *            包含的属性名字数组,(can be nested/indexed/mapped/combo)<br>
     *            <ol>
     *            <li>如果是null或者empty,那么直接调用 {@link BeanUtils#copyProperties(Object, Object)},否则循环调用{@link #getProperty(Object, String)} 再
     *            {@link #setProperty(Object, String, Object)}到 <code>toObj</code>对象中</li>
     *            <li>如果传入的<code>includePropertyNames</code>,含有 <code>fromObj</code>没有的属性名字,将会抛出异常</li>
     *            <li>如果传入的<code>includePropertyNames</code>,含有 <code>fromObj</code>有,但是 <code>toObj</code>没有的属性名字,可以正常运行(跳过该属性设置),see
     *            {@link BeanUtilsBean#copyProperty(Object, String, Object)} Line391</li>
     *            </ol>
     * @throws NullPointerException
     *             如果 <code>toObj</code> 是null,或者 <code>fromObj</code> 是null
     * @throws BeanOperationException
     *             其他调用api有任何异常,转成{@link BeanOperationException}返回
     * @see org.apache.commons.beanutils.BeanUtilsBean#copyProperties(Object, Object)
     * @see <a href="http://www.cnblogs.com/kaka/archive/2013/03/06/2945514.html">Bean复制的几种框架性能比较(Apache BeanUtils、PropertyUtils,Spring
     *      BeanUtils,Cglib BeanCopier)</a>
     */
    //XXX add excludePropertyNames support
    public static void copyProperties(Object toObj,Object fromObj,String...includePropertyNames){
        Validate.notNull(toObj, "toObj [destination bean] not specified!");
        Validate.notNull(fromObj, "fromObj [origin bean] not specified!");

        //---------------------------------------------------------------

        if (isNullOrEmpty(includePropertyNames)){
            try{
                BeanUtils.copyProperties(toObj, fromObj);
                return;
            }catch (Exception e){
                String pattern = "copyProperties exception,toObj:[{}],fromObj:[{}],includePropertyNames:[{}]";
                throw new BeanOperationException(Slf4jUtil.format(pattern, toObj, fromObj, includePropertyNames), e);
            }
        }

        //---------------------------------------------------------------
        for (String propertyName : includePropertyNames){
            String value = getProperty(fromObj, propertyName);
            setProperty(toObj, propertyName, value);
        }
    }

    // [end]

    //---------------------------------------------------------------

    // [start] setProperty

    /**
     * 使用 {@link BeanUtils#setProperty(Object, String, Object)} 来设置属性值(<b>会进行类型转换</b>).
     * 
     * <p>
     * BeanUtils支持把所有类型的属性都作为字符串处理,在后台自动进行类型转换(字符串和真实类型的转换)
     * </p>
     *
     * @param bean
     *            Bean on which setting is to be performed
     * @param propertyName
     *            Property name (can be nested/indexed/mapped/combo),参见<a href="#propertyName">propertyName</a>
     * @param value
     *            Value to be set
     * @throws BeanOperationException
     *             有任何异常,转成{@link BeanOperationException}返回
     * @see org.apache.commons.beanutils.BeanUtils#setProperty(Object, String, Object)
     * @see org.apache.commons.beanutils.BeanUtilsBean#setProperty(Object, String, Object)
     * @see org.apache.commons.beanutils.PropertyUtils#setProperty(Object, String, Object)
     * @see com.feilong.core.bean.PropertyUtil#setProperty(Object, String, Object)
     */
    private static void setProperty(Object bean,String propertyName,Object value){
        try{
            BeanUtils.setProperty(bean, propertyName, value);
        }catch (Exception e){
            String pattern = "setProperty exception,bean:[{}],propertyName:[{}],value:[{}]";
            throw new BeanOperationException(Slf4jUtil.format(pattern, bean, propertyName, value), e);
        }
    }

    // [end]

    //---------------------------------------------------------------

    // [start] getProperty

    /**
     * 使用 {@link BeanUtils#getProperty(Object, String)} 类从对象中取得属性值,不care值原本是什么类型,统统转成 {@link String}返回.
     * 
     * <p>
     * 值转成字符串的规则,参见 {@link ConvertUtil#toString(Object)},比如如果发现值是数组,只会取第一个元素重新构造数组转到<code>toObj</code>中
     * </p>
     *
     * @param bean
     *            bean
     * @param propertyName
     *            属性名称 (can be nested/indexed/mapped/combo),参见 <a href="#propertyName">propertyName</a>
     * @return 使用{@link BeanUtils#getProperty(Object, String)} 从对象中取得属性值
     * @throws NullPointerException
     *             如果 <code>bean</code> 是null,或者如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @throws BeanOperationException
     *             在调用 {@link BeanUtils#getProperty(Object, String)}过程中有任何异常,转成{@link BeanOperationException}返回
     * @see org.apache.commons.beanutils.BeanUtils#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtils#getProperty(Object, String)
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @since 1.9.0 change access to private
     */
    private static String getProperty(Object bean,String propertyName){
        Validate.notNull(bean, "bean can't be null!");
        Validate.notBlank(propertyName, "propertyName can't be blank!");

        //---------------------------------------------------------------
        try{
            return BeanUtils.getProperty(bean, propertyName);
        }catch (Exception e){
            String pattern = "getProperty exception,bean:[{}],propertyName:[{}]";
            throw new BeanOperationException(Slf4jUtil.format(pattern, bean, propertyName), e);
        }
    }

    // [end]

    //---------------------------------------------------------------

    // [start] cloneBean

    /**
     * 调用{@link BeanUtils#cloneBean(Object)}.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * 
     * <ol>
     * <li>这个方法通过<b>默认构造函数</b>建立一个bean的新实例,然后拷贝每一个属性到这个新的bean中,即使这个bean没有实现 {@link Cloneable}接口 .</li>
     * <li>是为那些本身没有实现clone方法的类准备的</li>
     * <li>在源码上看是调用了 <b>getPropertyUtils().copyProperties(newBean, bean)</b>;最后实际上还是<b>复制的引用,无法实现深clone</b><br>
     * 但还是可以帮助我们减少工作量的,假如类的属性不是基础类型的话(即自定义类),可以先clone出那个自定义类,在把他付给新的类,覆盖原来类的引用
     * </li>
     * <li>
     * 如果需要深度clone,可以使用 {@link org.apache.commons.lang3.SerializationUtils#clone(java.io.Serializable) SerializationUtils.clone}
     * ,但是它的性能要慢很多倍</li>
     * <li>由于内部实现是通过 {@link java.lang.Class#newInstance() Class.newInstance()}来构造新的对象,所以需要被clone的对象<b>必须存在默认无参构造函数</b>,否则会出现异常
     * {@link java.lang.InstantiationException InstantiationException}</li>
     * <li>目前无法clone list,总是返回empty list,参见
     * <a href="https://issues.apache.org/jira/browse/BEANUTILS-471">BeanUtils.cloneBean with List is broken</a>
     * </li>
     * </ol>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            Bean to be cloned
     * @return the cloned bean (复制的引用 ,无法实现深clone)<br>
     * @throws NullPointerException
     *             如果 <code>bean</code> 是null
     * @throws BeanOperationException
     *             在调用api有任何异常,转成{@link BeanOperationException}返回
     * @see org.apache.commons.beanutils.BeanUtils#cloneBean(Object)
     * @see org.apache.commons.beanutils.PropertyUtilsBean#copyProperties(Object, Object)
     * @see org.apache.commons.lang3.SerializationUtils#clone(java.io.Serializable)
     * @see org.apache.commons.lang3.ObjectUtils#clone(Object)
     * @see org.apache.commons.lang3.ObjectUtils#cloneIfPossible(Object)
     * @see <a href="https://issues.apache.org/jira/browse/BEANUTILS-471">BeanUtils.cloneBean with List is broken</a>
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(T bean){
        Validate.notNull(bean, "bean can't be null!");

        //---------------------------------------------------------------
        try{
            return (T) BeanUtils.cloneBean(bean);
        }catch (Exception e){
            String message = Slf4jUtil.format("cloneBean exception,bean:[{}]]", bean);
            throw new BeanOperationException(message, e);
        }
    }

    // [end]

    //---------------------------------------------------------------

    // [start] populate(填充) 把properties/map里面的值放入bean中

    /**
     * 把properties/map里面的值 <code>populate</code> <b>(填充)</b>到<code>bean</code>中.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>将Map{@code <Key,value>}中的以值(String或String[])转换到目标bean对应的属性中,Key是目标bean的属性名. </li>
     * <li>apache的javadoc中,明确指明这个方法是为解析http请求参数特别定义和使用的,<b>在正常使用中不推荐使用</b>.推荐使用 {@link #copyProperties(Object, Object, String...)}方法</li>
     * <li>底层方法原理 {@link BeanUtilsBean#populate(Object, Map)},循环map,调用 {@link BeanUtilsBean#setProperty(Object, String, Object)}方法 ,一一对应设置到
     * <code>bean</code>对象</li>
     * <li>如果properties key中有bean中不存在的属性,那么该条数据自动忽略</li>
     * <li>如果properties key中有null,那么该条数据自动忽略,see {@link BeanUtilsBean#populate(Object, Map)} line 817</li>
     * <li><code>bean</code>可以是Map类型,不过转换之后的key和value都会是Object类型,而不是声明的类型,see {@link BeanUtilsBean#setProperty(Object, String, Object)} line
     * 928</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User user = new User();
     * user.setId(5L);
     * 
     * Map{@code <String, Object>} properties = newHashMap();
     * properties.put("id", 8L);
     * 
     * BeanUtil.populate(user, properties);
     * LOGGER.info(JsonUtil.format(user));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "id": 8,
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 将<b>mail.properties</b>配置文件数据直接转成 <code>MailSenderConfig</code>对象
     * </p>
     * 
     * <p>
     * 有以下的<b>mail.properties</b>配置文件信息:
     * </p>
     * 
     * <pre class="code">
     * mailServerHost=smtp.exmail.qq.com <span style="color:green">// this is 胡编乱造的 account</span>
     * userName=feilong@feilong.cn  <span style="color:green">// this is 胡编乱造的 account</span>
     * password=feilong  <span style="color:green">// this is 胡编乱造的 account</span>
     * 
     * personal=feilong store
     * 
     * tos=123456@163.com,1234567@163.cn
     * ccs=123456test@163.com,123456test1@163.cn
     * bccs=123456test2@163.com,123456test3@163.cn
     * 
     * subject=hello world
     * 
     * mailServerPort=25
     * 
     * isDebug=true
     * 
     * isNeedReturnReceipt=true
     * </pre>
     * 
     * <p>
     * 有以下<b>bean</b>信息:
     * </p>
     * 
     * <pre class="code">
     * 
     * public class <b>MailSenderConfig</b>{
     * 
     *     <span style="color:green">//** 发送邮件的服务器的IP</span>
     *     private String mailServerHost;
     * 
     *     <span style="color:green">//** 邮件服务的端口 默认25.</span>
     *     private String mailServerPort = "25";
     * 
     *     <span style="color:green">//** 登录邮件发送服务器的用户名</span>
     *     private String userName;
     * 
     *     <span style="color:green">//** 登录邮件发送服务器的密码</span>
     *     private String password;
     * 
     *     <span style="color:green">//** 是否debug 输出. </span> 
     *     private boolean isDebug = false;
     * 
     *     <span style="color:green">//** 是否需要回执, 默认不需要.</span>
     *     private boolean isNeedReturnReceipt = false;
     * 
     *     <span style="color:green">//** 邮件发送者的地址.</span>
     *     private String fromAddress;
     * 
     *     <span style="color:green">//** 个人名义.</span>
     *     private String personal = "";
     * 
     *     <span style="color:green">//** 邮件多人接收地址.</span>
     *     private String[] tos;
     * 
     *     <span style="color:green">//** 邮件多人接收地址(抄送).</span>
     *     private String[] ccs;
     * 
     *     <span style="color:green">//** 邮件多人接收地址.</span>
     *     private String[] bccs;
     * 
     *      <span style="color:green">//setter getter 略</span>
     * }
     * 
     * </pre>
     * 
     * <b>
     * 此时你可以如此调用代码:
     * </b>
     * 
     * <pre class="code">
     * MailSenderConfig mailSenderConfig = new MailSenderConfig();
     * 
     * ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(FileUtil.getFileInputStream("mail.properties"));
     * BeanUtil.populate(mailSenderConfig, ResourceBundleUtil.toMap(resourceBundle));
     * 
     * LOGGER.debug(JsonUtil.format(mailSenderConfig));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "subject": "hello world",
        "mailServerHost": "smtp.exmail.qq.com",
        "bccs":         [
            "123456test2",
            "163.com",
            "123456test3",
            "163.cn"
        ],
        "ccs":         [
            "123456test",
            "163.com",
            "123456test1",
            "163.cn"
        ],
        "password": "******",
        "mailServerPort": "25",
        "content": "",
        "tos":         [
            "123456",
            "163.com",
            "1234567",
            "163.cn"
        ],
        "personal": "feilong store",
        "isNeedReturnReceipt": true,
        "fromAddress": "",
        "userName": "feilong@feilong.cn",
        "isDebug": true
    }
     * </pre>
     * 
     * <p>
     * 此时你会发现,上面的 tos 期望值是 ["123456@163.com","1234567@163.cn"],但是和你的期望值不符合,ccs和bccs 也是相同的情况<br>
     * 因为,{@link ArrayConverter} 默认允许的字符 allowedChars 只有 <code>'.', '-'</code>,其他都会被做成分隔符
     * </p>
     * 
     * <p>
     * 你可以如此这般:
     * </p>
     *
     * <pre class="code">
     * MailSenderConfig mailSenderConfig = new MailSenderConfig();
     * 
     * ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(FileUtil.getFileInputStream("mail.properties"));
     * 
     * <span style="color:blue">ArrayConverter arrayConverter = new ArrayConverter(String[].class, new StringConverter(), 2);</span>
     * <span style="color:blue">char[] allowedChars = { '@' };</span>
     * <span style="color:blue">arrayConverter.setAllowedChars(allowedChars);</span>
     * <span style="color:blue">ConvertUtils.register(arrayConverter, String[].class);</span>
     * 
     * BeanUtil.populate(mailSenderConfig, ResourceBundleUtil.toMap(resourceBundle));
     * 
     * LOGGER.debug(JsonUtil.format(mailSenderConfig));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "subject": "hello world",
        "mailServerHost": "smtp.exmail.qq.com",
        "bccs":         [
            "123456test2@163.com",
            "123456test3@163.cn"
        ],
        "ccs":         [
            "123456test@163.com",
            "123456test1@163.cn"
        ],
        "password": "******",
        "mailServerPort": "25",
        "content": "",
        "tos":         [
            "123456@163.com",
            "1234567@163.cn"
        ],
        "personal": "feilong store",
        "isNeedReturnReceipt": true,
        "fromAddress": "",
        "userName": "feilong@feilong.cn",
        "isDebug": true
    }
     * </pre>
     * 
     * <p>
     * 如果你的配置文件的key和bean的属性不一致(比如大小写,有分隔符等情况),你可以使用 {@link #populateAliasBean(Object, Map)}
     * </p>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param bean
     *            JavaBean whose properties are being populated
     * @param properties
     *            Map keyed by property name,with the corresponding (String or String[]) value(s) to be set
     * @return 如果properties key中有bean中不存在的属性,那么该条数据自动忽略<br>
     *         如果properties key中有null,那么该条数据自动忽略,see {@link BeanUtilsBean#populate(Object, Map)} line 817<br>
     * @throws NullPointerException
     *             如果 <code>bean</code> 是null,或者如果 <code>properties</code> 是null
     * @throws BeanOperationException
     *             在调用{@link BeanUtils#populate(Object, Map)}过程中有任何异常,转成{@link BeanOperationException}返回
     * @see org.apache.commons.beanutils.BeanUtils#populate(Object, Map)
     */
    public static <T> T populate(T bean,Map<String, ?> properties){
        Validate.notNull(bean, "bean can't be null/empty!");
        Validate.notNull(properties, "properties can't be null/empty!");

        //---------------------------------------------------------------
        try{
            BeanUtils.populate(bean, properties);
            return bean;
        }catch (Exception e){
            String message = Slf4jUtil.format("can't populate:[{}] to bean:{}", properties, bean);
            throw new BeanOperationException(message, e);
        }
    }

    //---------------------------------------------------------------

    /**
     * 将 alias 和value 的map <code>populate</code> <b>(填充)</b>到 <code>aliasBean</code>.
     * 
     * <h3>背景:</h3>
     * 
     * <blockquote>
     * <p>
     * {@link BeanUtil} 有标准的populate功能:{@link #populate(Object, Map)} ,但是要求 map的key 和 bean的属性名称必须是一一对应<br>
     * </p>
     * 
     * <p>
     * 有很多情况,比如 map 的key是 <b>"memcached.alivecheck"</b> 这样的字符串(常见于properties 的配置文件),或者是大写的 <b>"ALIVECHECK"</b> 的字符串(常见于第三方接口 xml属性名称)<br>
     * 而我们的bean里面的属性名称是标准的 java bean 规范的名字,比如 <b>"aliveCheck"</b>,这时就没有办法直接使用 {@link #populate(Object, Map)} 方法了<br>
     * </p>
     * 
     * <p>
     * 你可以使用 {@link #populateAliasBean(Object, Map)}方法~~
     * </p>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 有以下<b>aliasAndValueMap</b>信息:
     * </p>
     * 
     * <pre class="code">
     * {
     * "memcached.alivecheck": "false",
     * "memcached.expiretime": "180",
     * "memcached.initconnection": "10",
     * "memcached.maintSleep": "30",
     * "memcached.maxconnection": "250",
     * "memcached.minconnection": "5",
     * "memcached.nagle": "false",
     * "memcached.poolname": "sidsock2",
     * "memcached.serverlist": "172.20.31.23:11211,172.20.31.22:11211",
     * "memcached.serverweight": "2",
     * "memcached.socketto": "3000"
     * }
     * 
     * </pre>
     * 
     * <p>
     * 有以下<b>aliasBean</b>信息:
     * </p>
     * 
     * <pre class="code">
     * 
     * public class DangaMemCachedConfig{
     * 
     *     <span style="color:green">//** The serverlist.</span>
     *     &#64;Alias(name = "memcached.serverlist",sampleValue = "172.20.31.23:11211,172.20.31.22:11211")
     *     private String[] serverList;
     * 
     *     <span style="color:green">//@Alias(name = "memcached.poolname",sampleValue = "sidsock2")</span>
     *     private String poolName;
     * 
     *     <span style="color:green">//** The expire time 单位分钟.</span>
     *     &#64;Alias(name = "memcached.expiretime",sampleValue = "180")
     *     private Integer expireTime;
     * 
     *     <span style="color:green">//** 权重. </span>
     *     &#64;Alias(name = "memcached.serverweight",sampleValue = "2,1")
     *     private Integer[] weight;
     * 
     *     <span style="color:green">//** The init connection. </span>
     *     &#64;Alias(name = "memcached.initconnection",sampleValue = "10")
     *     private Integer initConnection;
     * 
     *     <span style="color:green">//** The min connection.</span>
     *     &#64;Alias(name = "memcached.minconnection",sampleValue = "5")
     *     private Integer minConnection;
     * 
     *     <span style="color:green">//** The max connection. </span>
     *     &#64;Alias(name = "memcached.maxconnection",sampleValue = "250")
     *     private Integer maxConnection;
     * 
     *     <span style="color:green">//** 设置主线程睡眠时间,每30秒苏醒一次,维持连接池大小.</span>
     *     &#64;Alias(name = "memcached.maintSleep",sampleValue = "30")
     *     private Integer maintSleep;
     * 
     *     <span style="color:green">//** 关闭套接字缓存. </span>
     *     &#64;Alias(name = "memcached.nagle",sampleValue = "false")
     *     private Boolean nagle;
     * 
     *     <span style="color:green">//** 连接建立后的超时时间.</span>
     *     &#64;Alias(name = "memcached.socketto",sampleValue = "3000")
     *     private Integer socketTo;
     * 
     *     <span style="color:green">//** The alive check.</span>
     *     &#64;Alias(name = "memcached.alivecheck",sampleValue = "false")
     *     private Boolean aliveCheck;
     * 
     *     <span style="color:green">//setter getter 略</span>
     * }
     * 
     * </pre>
     * 
     * <b>
     * 此时你可以如此调用代码:
     * </b>
     * 
     * <pre class="code">
     * Map{@code <String, String>} readPropertiesToMap = ResourceBundleUtil.toMap(ResourceBundleUtil.getResourceBundle("memcached"));
     * 
     * DangaMemCachedConfig dangaMemCachedConfig = new DangaMemCachedConfig();
     * BeanUtil.populateAliasBean(dangaMemCachedConfig, readPropertiesToMap);
     * LOGGER.debug(JsonUtil.format(dangaMemCachedConfig));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "maxConnection": 250,
        "expireTime": 180,
        "serverList":         [
            "172.20.31.23",
            "11211",
            "172.20.31.22",
            "11211"
        ],
        "weight": [2],
        "nagle": false,
        "initConnection": 10,
        "aliveCheck": false,
        "poolName": "sidsock2",
        "maintSleep": 30,
        "socketTo": 3000,
        "minConnection": 5
    }
     * </pre>
     * 
     * <p>
     * 此时你会发现,上面的 serverList 期望值是 ["172.20.31.23:11211","172.20.31.22:11211"],但是和你的期望值不符合,<br>
     * 因为, {@link ArrayConverter} 默认允许的字符 allowedChars 只有 <code>'.', '-'</code>,其他都会被做成分隔符
     * </p>
     * 
     * <p>
     * 你需要如此这般:
     * </p>
     *
     * <pre class="code">
     * Map{@code <String, String>} readPropertiesToMap = ResourceBundleUtil.toMap(ResourceBundleUtil.getResourceBundle("memcached"));
     * 
     * DangaMemCachedConfig dangaMemCachedConfig = new DangaMemCachedConfig();
     * 
     * <span style="color:blue">ArrayConverter arrayConverter = new ArrayConverter(String[].class, new StringConverter(), 2);</span>
     * <span style="color:blue">char[] allowedChars = { ':' };</span>
     * <span style="color:blue">arrayConverter.setAllowedChars(allowedChars);</span>
     * <span style="color:blue">ConvertUtils.register(arrayConverter, String[].class);</span>
     * 
     * BeanUtil.populateAliasBean(dangaMemCachedConfig, readPropertiesToMap);
     * LOGGER.debug(JsonUtil.format(dangaMemCachedConfig));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "maxConnection": 250,
        "expireTime": 180,
        "serverList":         [
            "172.20.31.23:11211",
            "172.20.31.22:11211"
        ],
        "weight": [2],
        "nagle": false,
        "initConnection": 10,
        "aliveCheck": false,
        "poolName": "sidsock2",
        "maintSleep": 30,
        "socketTo": 3000,
        "minConnection": 5
    }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param aliasBean
     *            the bean
     * @param aliasAndValueMap
     *            the key and value map
     * @return 如果 <code>aliasAndValueMap</code> 是null或者empty,返回 <code>bean</code><br>
     *         如果 <code>alias name</code> 不在<code>aliasAndValueMap</code>中,或者值是emty,那么不会设置 <code>aliasBean</code>对象值
     * @throws NullPointerException
     *             如果 <code>bean</code> 是null
     * @since 1.8.1
     */
    public static <T> T populateAliasBean(T aliasBean,Map<String, ?> aliasAndValueMap){
        Validate.notNull(aliasBean, "aliasBean can't be null!");
        if (isNullOrEmpty(aliasAndValueMap)){
            return aliasBean;
        }

        //---------------------------------------------------------------

        Map<String, String> propertyNameAndAliasMap = buildPropertyNameAndAliasMap(aliasBean.getClass());
        for (Map.Entry<String, String> entry : propertyNameAndAliasMap.entrySet()){
            String alias = entry.getValue();
            Object value = aliasAndValueMap.get(alias);
            if (isNotNullOrEmpty(value)){
                setProperty(aliasBean, entry.getKey(), value);
            }
        }

        //---------------------------------------------------------------
        return aliasBean;
    }

    //---------------------------------------------------------------

    /**
     * 提取 klass {@link Alias} 注释,将 属性名字和 {@link Alias#name()} 组成map 返回.
     *
     * @param klass
     *            the klass
     * @return 如果<code>klass</code> 没有 {@link Alias} 注释,返回 {@link Collections#emptyMap()}
     * @throws NullPointerException
     *             如果 <code>klass</code> 是null
     * @since 1.8.1
     */
    private static Map<String, String> buildPropertyNameAndAliasMap(Class<?> klass){
        Validate.notNull(klass, "klass can't be null!");

        //---------------------------------------------------------------

        List<Field> aliasFieldsList = FieldUtils.getFieldsListWithAnnotation(klass, Alias.class);
        if (isNullOrEmpty(aliasFieldsList)){
            return emptyMap();
        }

        //---------------------------------------------------------------
        //属性名字和key的对应关系
        Map<String, String> propertyNameAndAliasMap = newHashMap(aliasFieldsList.size());
        for (Field field : aliasFieldsList){
            Alias alias = field.getAnnotation(Alias.class);
            propertyNameAndAliasMap.put(field.getName(), alias.name());
        }
        return propertyNameAndAliasMap;
    }

    // [end]

    //---------------------------------------------------------------

    /**
     * 使用 <code>valueMap</code> 来构造一个 DynaBean.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>一般情况下,你可能不需要使用这个方法</li>
     * <li>很适合那种属性值数量不确定,并且又不想在页面使用map来渲染的地方,比如制作多维度的图表</li>
     * <li>程序内部,默认使用的是 {@link org.apache.commons.beanutils.LazyDynaClass}</li>
     * <li>不需要先创建一个期望的数据结构DynaClass,就能向{@link LazyDynaBean}中填充我们任意想填充的数据。</li>
     * <li>{@link LazyDynaBean}内部会根据我们填充进的数据(即使是一个map中的一个key-value pair),创建metadata的。</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * DynaBean newDynaBean = BeanUtil.newDynaBean(
     *                 toMap(//
     *                                 Pair.of("address", (Object) newHashMap()),
     *                                 Pair.of("firstName", (Object) "Fred"),
     *                                 Pair.of("lastName", (Object) "Flintstone")));
     * LOGGER.debug(JsonUtil.format(newDynaBean));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     *  "address": {},
     *  "firstName": "Fred",
     *  "lastName": "Flintstone"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param valueMap
     *            the value map
     * @return the dyna bean
     * @throws NullPointerException
     *             如果 <code>valueMap</code> 是null,或者 <code>valueMap</code>中有key是null
     * @throws IllegalArgumentException
     *             如果<code>valueMap</code>中有key是empty
     * @see org.apache.commons.beanutils.LazyDynaBean
     * @since 1.8.1
     * @since 1.13.2 param map change from {@code Map<String, ?> valueMap} to {@code Map<?, ?> valueMap}
     */
    public static DynaBean newDynaBean(Map<?, ?> valueMap){
        Validate.notNull(valueMap, "valueMap can't be null!");

        //---------------------------------------------------------------
        LazyDynaBean lazyDynaBean = new LazyDynaBean();
        for (Map.Entry<?, ?> entry : valueMap.entrySet()){
            String key = ConvertUtil.toString(entry.getKey());

            Validate.notBlank(key, "entry.getKey() can't be blank!");
            lazyDynaBean.set(key, entry.getValue());
        }
        return lazyDynaBean;
    }
}