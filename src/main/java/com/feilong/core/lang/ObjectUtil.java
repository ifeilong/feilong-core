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
package com.feilong.core.lang;

import static com.feilong.core.Validator.isNotNullOrEmpty;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

/**
 * {@link Object} 工具类.
 * 
 * <h3>判断相等</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>{@link org.apache.commons.lang3.ObjectUtils#equals(Object, Object)} 支持两个值都是null的情况</li>
 * <li>{@link java.util.Objects#equals(Object, Object)} (since jdk1.7) 也支持两个值都是null的情况</li>
 * </ol>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.ObjectUtils
 * @see java.util.Objects
 * @since 1.0.0
 */
public final class ObjectUtil{

    /** Don't let anyone instantiate this class. */
    private ObjectUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 如果 <code>object</code> 是null或者empty,返回默认值 <code>defaultValue</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * ObjectUtil.defaultIfNullOrEmpty(null, null)      = null
     * ObjectUtil.defaultIfNullOrEmpty(null, "")        = ""
     * ObjectUtil.defaultIfNullOrEmpty(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNullOrEmpty("abc", *)        = "abc"
     * ObjectUtil.defaultIfNullOrEmpty(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>使用该方法,可以简化你的代码</li>
     * <li>如果使用 import static 的特性,代码会更加简洁</li>
     * <li>如果你只需要判断 null的场景,你可以使用 {@link ObjectUtils#defaultIfNull(Object, Object)}</li>
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>对下面的代码重构:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * if (isNotNullOrEmpty(defaultReturnResult.getReturnObject())){
     *     return (String) defaultReturnResult.getReturnObject();
     * }else{
     *     return "redirect:/";
     * }
     * 
     * </pre>
     * 
     * 可以重构成:
     * 
     * <pre class="code">
     * return ObjectUtil.defaultIfNullOrEmpty((String) defaultReturnResult.getReturnObject(), "redirect:/");
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>再比如:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * private void putItemToMap(Map{@code <String, List<Item>>} map,String categoryName,Item item){
     *     List{@code <Item>} itemList = map.get(categoryName);
     * 
     *     if (isNullOrEmpty(itemList)){
     *         itemList = new ArrayList{@code <>}();
     *     }
     *     itemList.add(item);
     *     map.put(categoryName, itemList);
     * }
     * 
     * </pre>
     * 
     * 可以重构成:
     * 
     * <pre class="code">
     * 
     * private void putItemToMap(Map{@code <String, List<Item>>} map,String categoryName,Item item){
     *     List{@code <Item>} itemList = ObjectUtil.defaultIfNullOrEmpty(map.get(categoryName), new ArrayList{@code <Item>}());
     *     itemList.add(item);
     *     map.put(categoryName, itemList);
     * }
     * </pre>
     * 
     * 当然对于上面的case,你还可以直接调用 {@link com.feilong.core.util.MapUtil#putMultiValue(java.util.Map, Object, Object)}
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the type of the object
     * @param object
     *            the {@code Object} to test, 可以是 {@code null} or empty
     * @param defaultValue
     *            the default value to return, 可以是 {@code null} or empty
     * @return 如果 <code>object</code> 是null或者empty,返回 <code>defaultValue</code>,否则返回 <code>object</code>
     * @see org.apache.commons.lang3.ObjectUtils#defaultIfNull(Object, Object)
     * @see org.apache.commons.collections4.ListUtils#defaultIfNull(java.util.List, java.util.List)
     * @since 1.7.2
     */
    public static <T> T defaultIfNullOrEmpty(final T object,final T defaultValue){
        return isNotNullOrEmpty(object) ? object : defaultValue;
    }

    //---------------------------------------------------------------

    /**
     * 判断指定的对象 <code>object</code>是否是数组.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>支持判断原始类型数组 <code>primitive</code> 和包装类型数组</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * int[] i = {};
     * ObjectUtil.isArray(i);                       =true
     * 
     * ObjectUtil.isArray(new int[] { 1, 2, 3 });   =true
     * 
     * ObjectUtil.isArray(new Integer[0]);          =true
     * ObjectUtil.isArray(new String[0]);           =true
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3><code>instanceof</code>和 {@link java.lang.Class#isArray()}的区别:</h3>
     * 
     * <blockquote>
     * <p>
     * 通常使用<code>instanceof</code>操作符去判断一个对象 <code>object</code> 是否是数组 <code>array</code>.<br>
     * 在JVM层次,<code>instanceof</code>操作符 translates to a specific "instanceof" byte code, which is highly optimized in most JVM
     * implementations.<br>
     * </p>
     * 
     * <p>
     * 而反射的方法(getClass().isArray()) is compiled to two separate "invokevirtual" instructions.<br>
     * The more generic optimizations applied by the JVM to these may not be as fast as the hand-tuned optimizations inherent in the
     * "instanceof" instruction.<br>
     * </p>
     * 
     * <p>
     * 有两种特殊情况: null references 和 primitive arrays.<br>
     * </p>
     * 
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * 
     * <tr style="background-color:#ccccff">
     * <th align="left"></th>
     * <th align="left"><code>instanceof</code></th>
     * <th align="left"><code>getClass().isArray()</code></th>
     * </tr>
     * 
     * <tr valign="top">
     * <td>null reference</td>
     * <td>false</td>
     * <td>NullPointerException</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>原始类型数组primitive array</td>
     * <td>false</td>
     * <td>true</td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     *
     * @param object
     *            the object
     * @return 如果 <code>object</code> 是null,抛出 {@link NullPointerException}<br>
     * @see <a href="http://stackoverflow.com/questions/219881/java-array-reflection-isarray-vs-instanceof">Java array reflection: isArray
     *      vs. instanceof</a>
     * @see <a href="http://stackoverflow.com/questions/2725533/how-to-see-if-an-object-is-an-array-without-using-reflection">How to see if
     *      an object is an array without using reflection?</a>
     * @since 1.3.0
     */
    public static boolean isArray(Object object){
        Validate.notNull(object, "object can't be null!");
        return object.getClass().isArray();
    }

    //---------------------------------------------------------------

    /**
     * 判断指定的对象 <code>object</code> 是否是原生类型数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * ObjectUtil.isPrimitiveArray(1)                           = false
     * ObjectUtil.isPrimitiveArray(1L)                          = false
     * ObjectUtil.isPrimitiveArray("1")                         = false
     * 
     * 
     * ObjectUtil.isPrimitiveArray(new int[] {})                = true
     * ObjectUtil.isPrimitiveArray(new int[] { 1, 2 })          = true
     * ObjectUtil.isPrimitiveArray(new byte[] { 1, 2 })         = true
     * 
     * ObjectUtil.isPrimitiveArray(new String[] { "1", "2" })   = false
     * </pre>
     * 
     * </blockquote>
     *
     * @param object
     *            the object
     * @return 如果 <code>object</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.8.4
     */
    public static boolean isPrimitiveArray(Object object){
        Validate.notNull(object, "object can't be null!");
        return isArray(object) && object.getClass().getComponentType().isPrimitive();//原始型的
    }
}
