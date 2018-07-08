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
package com.feilong.core.lang.reflect;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.util.CollectionsUtil.selectRejected;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * focus on {@link Field} 反射工具类.
 * 
 * <h3>{@link Field} 相关的几个方法的区别:</h3>
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
 * <td>{@link Class#getDeclaredFields() getDeclaredFields}</td>
 * <td>返回 {@link Field} 对象的一个数组,这些对象反映此 Class 对象所表示的类或接口所声明的<span style="color:green">所有字段</span>.
 * 
 * <p>
 * 包括<span style="color:green"><b>公共</b>、<b>保护</b>、<b>默认(包)</b>访问和<b>私有</b>字段</span>, <span style="color:red">但不包括继承的字段</span>.
 * </p>
 * 
 * <b>返回数组中的元素没有排序,也没有任何特定的顺序.</b><br>
 * 如果该类或接口不声明任何字段,或者此 Class 对象表示一个基本类型、一个数组类或 void,则此方法返回一个长度为 0 的数组.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Class#getFields() getFields}</td>
 * <td>返回一个包含某些 {@link Field} 对象的数组,这些对象反映此 Class 对象所表示的类或接口的所有 <span style="color:red">可访问公共字段</span>.
 * 
 * <p>
 * 特别地,如果该 Class 对象表示<b>一个类</b>,则此方法返回<span style="color:red">该类及其所有超类的公共字段</span>.<br>
 * 如果该 Class 对象表示<b>一个接口</b>,则此方法返回<span style="color:red">该接口及其所有超接口的公共字段</span>.
 * </p>
 * 
 * <p>
 * 返回数组中的元素没有排序,也没有任何特定的顺序.
 * </p>
 * <br>
 * 
 * 如果类或接口没有可访问的公共字段,或者表示一个数组类、一个基本类型或 void,则此方法返回长度为 0 的数组. <br>
 * 该方法不反映数组类的隐式长度字段.用户代码应使用 {@link java.lang.reflect.Array} 类的方法来操作数组.</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link Class#getDeclaredField(String) getDeclaredField}</td>
 * <td>返回一个 {@link Field} 对象,该对象反映此 Class 对象所表示的类或接口的指定已声明字段.<br>
 * 注意,此方法不反映数组类的 length 字段.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link Class#getField(String) getField}</td>
 * <td>返回一个 {@link Field} 对象,它反映此 Class 对象所表示的类或接口的指定<span style="color:green">公共成员字段</span>.<br>
 * 要反映的字段由下面的算法确定.<br>
 * 设 C 为此对象所表示的类: <br>
 * 如果 C 声明一个带有指定名的公共字段,则它就是要反映的字段. <br>
 * 如果在第 1 步中没有找到任何字段,则该算法被递归地应用于 C 的每一个<b>直接超接口</b>.直接超接口按其声明顺序进行搜索. <br>
 * 如果在第 1、2 两步没有找到任何字段,且 C 有一个<b>超类 S</b>,则在 S 上递归调用该算法.如果 C 没有超类,则抛出 NoSuchFieldException.<br>
 * </td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.reflect.FieldUtils
 * @see "org.springframework.util.ReflectionUtils"
 * @since 1.0.7
 */
public final class FieldUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtil.class);

    /** Don't let anyone instantiate this class. */
    private FieldUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 获得对象 <code>obj</code> 所有字段的值(<b>不是属性</b>),key是 fieldName,value 是值.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>是所有字段的值(<b>不是属性</b>)</li>
     * <li>自动过滤私有并且静态的字段,比如 LOGGER serialVersionUID</li>
     * <li>返回的map 是 TreeMap,顺序是 field name 的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * User user = new User(12L);
     * LOGGER.debug(JsonUtil.format(FieldUtil.getAllFieldNameAndValueMap(user, "date")));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "age": null,
     * "id": 12
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj
     *            the obj
     * @param excludeFieldNames
     *            需要排除的field names,如果是null或者empty, 那么不会判断
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>excludeFieldNames</code> 是null或者empty,解析所有的field<br>
     *         如果 <code>obj</code>没有字段或者字段都被参数 <code>excludeFieldNames</code> 排除掉了,返回 {@link Collections#emptyMap()}<br>
     * @see #getAllFieldList(Class, String...)
     * @see #getFieldValue(Object, String)
     */
    public static Map<String, Object> getAllFieldNameAndValueMap(Object obj,String...excludeFieldNames){
        Validate.notNull(obj, "obj can't be null!");

        List<Field> fieldList = getAllFieldList(obj.getClass(), excludeFieldNames);
        if (isNullOrEmpty(fieldList)){
            return emptyMap();
        }

        //---------------------------------------------------------------
        Map<String, Object> map = new TreeMap<>();
        for (Field field : fieldList){
            map.put(field.getName(), getFieldValue(obj, field.getName()));
        }
        return map;
    }

    //---------------------------------------------------------------

    /**
     * 获得 <code>klass</code> 排除某些 <code>excludeFieldNames</code> 之后的字段list.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>是所有字段的值(<b>不是属性</b>)</li>
     * <li>自动过滤私有并且静态的字段,比如 LOGGER serialVersionUID</li>
     * </ol>
     * </blockquote>
     * 
     * @param klass
     *            the klass
     * @param excludeFieldNames
     *            需要排除的field names,如果传递过来是null或者empty 那么不会判断
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>excludeFieldNames</code> 是null或者empty,解析所有的field<br>
     *         如果 {@link FieldUtils#getAllFieldsList(Class)} 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>obj</code>没有字段或者字段都被参数 <code>excludeFieldNames</code> 排除掉了,返回 {@link Collections#emptyMap()}<br>
     * @see FieldUtils#getAllFieldsList(Class)
     * @since 1.7.1
     */
    public static List<Field> getAllFieldList(final Class<?> klass,String...excludeFieldNames){
        Validate.notNull(klass, "klass can't be null!");
        //---------------------------------------------------------------

        //获得给定类的所有声明字段 {@link Field},包括所有的parents,包括 public/protect/private/inherited...
        List<Field> fieldList = FieldUtils.getAllFieldsList(klass);
        if (isNullOrEmpty(fieldList)){
            return emptyList();
        }

        //---------------------------------------------------------------
        Predicate<Field> excludeFieldPredicate = BeanPredicateUtil.containsPredicate("name", excludeFieldNames);
        Predicate<Field> staticPredicate = new Predicate<Field>(){

            @Override
            public boolean evaluate(Field field){
                int modifiers = field.getModifiers();
                // 私有并且静态 一般是log 或者  serialVersionUID
                boolean isStatic = Modifier.isStatic(modifiers);

                String pattern = "[{}.{}],modifiers:[{}]{}";
                LOGGER.trace(pattern, klass.getSimpleName(), field.getName(), modifiers, isStatic ? " [isStatic]" : EMPTY);
                return isStatic;
            }
        };
        return selectRejected(fieldList, PredicateUtils.orPredicate(excludeFieldPredicate, staticPredicate));
    }

    //---------------------------------------------------------------

    /**
     * 得到某个对象 <code>owner</code> 的公共字段 <code>fieldName</code> 值.
     *
     * @param <T>
     *            the generic type
     * @param obj
     *            the owner
     * @param fieldName
     *            the field name
     * @return 如果 <code>owner</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fieldName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>obj</code> 中没有 <code>fieldName</code>,抛出 {@link ReflectException}<br>
     * @see org.apache.commons.lang3.reflect.FieldUtils#readField(Object, String, boolean)
     * @since 1.4.0
     * @since 1.9.2 change to private
     */
    @SuppressWarnings("unchecked")
    private static <T> T getFieldValue(Object obj,String fieldName){
        try{
            return (T) FieldUtils.readField(obj, fieldName, true);
        }catch (Exception e){
            String pattern = "getFieldValue exception,ownerObj:[{}],fieldName:[{}]";
            throw new ReflectException(Slf4jUtil.format(pattern, obj, fieldName), e);
        }
    }
}