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
package com.feilong.core.util.comparator;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.tools.slf4j.Slf4jUtil.format;
import static org.apache.commons.lang3.StringUtils.SPACE;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.feilong.core.lang.StringUtil;

/**
 * 排序的辅助类.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.2
 */
public final class SortHelper{

    /** 正序排序因子. */
    private static final String ASC  = "ASC";

    /** 倒序排序因子. */
    private static final String DESC = "DESC";

    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private SortHelper(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 将 <code>propertyNameAndOrder</code>字符串转换成数组.
     * 
     * <p>
     * 会先进行trim操作,再使用{@link StringUtils#SPACE} 分隔成数组
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * SortHelper.parsePropertyNameAndOrder("name") = toArray("name", null);
     * SortHelper.parsePropertyNameAndOrder("name ") = toArray("name", null);
     * SortHelper.parsePropertyNameAndOrder(" name") = toArray("name", null);
     * 
     * SortHelper.parsePropertyNameAndOrder("name asc") = toArray("name", "aSc");
     * SortHelper.parsePropertyNameAndOrder("name aSc") = toArray("name", "asc");
     * SortHelper.parsePropertyNameAndOrder("name   aSc") = toArray("name", "asc");
     * 
     * SortHelper.parsePropertyNameAndOrder("name desc") = toArray("name", "desc");
     * 
     * SortHelper.parsePropertyNameAndOrder("name deSc") = toArray("name", "deSc");
     * </pre>
     * 
     * </blockquote>
     * 
     * @param propertyNameAndOrder
     *            属性名称和排序因子,
     * 
     *            <p>
     *            格式可以是纯的属性名称, 比如 "name"; 也可以是属性名称+排序因子(以空格分隔),比如 "name desc"
     *            </p>
     * 
     *            <h3>说明:</h3>
     *            <blockquote>
     * 
     *            <dl>
     *            <dt>关于属性名称</dt>
     *            <dd>
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be
     *            modified,参见<a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,<br>
     *            该属性对应的value 必须实现 {@link Comparable}接口.
     *            </dd>
     * 
     *            <dt>关于排序因子</dt>
     *            <dd>
     *            可以没有排序因子<br>
     *            如果有,值可以是asc(顺序),desc(倒序)两种;<br>
     *            如果没有,默认按照asc(顺序)排序;<br>
     *            此外,asc/desc忽略大小写
     *            </dd>
     * 
     *            </dl>
     * 
     *            </blockquote>
     * @return 属性名称和排序因子的数组
     * @throws NullPointerException
     *             如果 <code>propertyNameAndOrder</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyNameAndOrder</code> 是blank;<br>
     *             或者如果<code>propertyNameAndOrder</code>使用{@link StringUtils#SPACE}转换的数组length {@code >} 2<br>
     *             或者如果<code>propertyNameAndOrder</code>使用{@link StringUtils#SPACE}转换的数组length {@code =} 2,但是第二个值不是asc也不是desc
     * @see com.feilong.core.lang.StringUtil#tokenizeToStringArray(String, String)
     */
    public static String[] parsePropertyNameAndOrder(String propertyNameAndOrder){
        Validate.notBlank(propertyNameAndOrder, "propertyNameAndOrder can't be blank!");

        //---------------------------------------------------------------
        //空格
        String[] array = StringUtil.tokenizeToStringArray(propertyNameAndOrder.trim(), SPACE);
        if (array.length > 2){
            String message = format("propertyNameAndOrder:[{}] has more than 1 space,must max 1 space", propertyNameAndOrder);
            throw new IllegalArgumentException(message);
        }

        //---------------------------------------------
        String order = null;
        if (array.length == 2){
            order = array[1];//排序因子
            if (!(order.equalsIgnoreCase(ASC) || order.equalsIgnoreCase(DESC))){
                String pattern = "propertyNameAndOrder:[{}] 's order:[{}] must ignoreCase equals [asc or desc]";
                throw new IllegalArgumentException(format(pattern, propertyNameAndOrder, order));
            }
        }

        //---------------------------------------------
        return toArray(array[0], order);
    }

    //---------------------------------------------------------------

    /**
     * 判断属性名称和排序因子数组是不是asc排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * SortHelper.isAsc(toArray("name", null)) = true;
     * 
     * SortHelper.isAsc(toArray("name", "asC")) = true;
     * SortHelper.isAsc(toArray("name", "AsC")) = true;
     * SortHelper.isAsc(toArray("name", "ASC")) = true;
     * 
     * SortHelper.isAsc(toArray("name", "dEsc")) = false;
     * SortHelper.isAsc(toArray("name", "desc")) = false;
     * </pre>
     * 
     * </blockquote>
     * 
     * @param propertyNameAndOrderArray
     *            属性名称和排序因子数组,length 是2<br>
     *            第一个元素是属性名称,比如 "name",第二个元素是排序因子,比如asc或者是desc或者是null<br>
     * @return 如果数组第二个值是null或者empty,或者值是asc(忽略大小写),那么判定是asc排序
     * @throws NullPointerException
     *             如果 <code>propertyNameAndOrderArray</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyNameAndOrderArray</code> 是empty<br>
     *             或者<code>propertyNameAndOrderArray</code>的length不是2<br>
     *             或者解析出来的order值不是[null/ASC/DESC] 中的任意一个
     */
    public static boolean isAsc(String[] propertyNameAndOrderArray){
        Validate.notEmpty(propertyNameAndOrderArray, "propertyNameAndOrderArray can't be null/empty!");

        Validate.isTrue(
                        2 == propertyNameAndOrderArray.length,
                        "propertyNameAndOrderArray.length must 2, but length is:[%s],propertyNameAndOrderArray:[%s]",
                        propertyNameAndOrderArray.length,
                        propertyNameAndOrderArray);

        //---------------------------------------------------------------

        String order = propertyNameAndOrderArray[1];

        Validate.isTrue(
                        null == order || ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order),
                        "order value must one of [null/ASC/DESC], but is:[%s],propertyNameAndOrderArray:[%s]",
                        order,
                        propertyNameAndOrderArray);

        //---------------------------------------------------------------

        return null == order || ASC.equalsIgnoreCase(order);
    }
}
