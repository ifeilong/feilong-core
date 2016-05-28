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

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.feilong.core.Validator;

/**
 * {@link java.lang.Class} 工具类.
 * 
 * <h3>关于 Class {@link java.lang.Class#getCanonicalName() getCanonicalName()} VS {@link java.lang.Class#getName() getName()} VS
 * {@link java.lang.Class#getSimpleName() getSimpleName()}</h3>
 * 
 * <blockquote>
 * <p>
 * 假设class 是 {@link com.feilong.core.DatePattern}
 * </p>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.lang.Class#getCanonicalName() getCanonicalName()}<br>
 * 返回 Java Language Specification 中所定义的底层类的规范化名称</td>
 * <td>"com.feilong.core.date.DatePattern"</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.lang.Class#getName() getName()}</td>
 * <td>"com.feilong.core.date.DatePattern"</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.lang.Class#getSimpleName() getSimpleName()}</td>
 * <td>"DatePattern"</td>
 * </tr>
 * </table>
 * 
 * <p>
 * {@link java.lang.Class#getCanonicalName() getCanonicalName()} && {@link java.lang.Class#getName() getName()} 其实这两个方法,对于大部分class来说,没有什么不同的
 * <br>
 * 但是对于array就显示出来了.
 * </p>
 * <ul>
 * <li>{@link java.lang.Class#getName() getName()} 返回的是<b>[[Ljava.lang.String</b>之类的表现形式,</li>
 * <li>而 {@link java.lang.Class#getCanonicalName() getCanonicalName()} 返回的就是跟我们声明类似的形式.</li>
 * </ul>
 * 
 * </blockquote>
 * 
 * <h3>关于装载类 {@link #loadClass(String)}:</h3>
 * 
 * <blockquote>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td><code>Class klass=对象引用o.{@link java.lang.Object#getClass() getClass()};</code></td>
 * <td>返回引用o运行时真正所指的对象(因为:儿子对象的引用可能会赋给父对象的引用变量中)所属的类O的Class的对象.<br>
 * 谈不上对类O做什么操作.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td><code>Class klass=A.class;</code></td>
 * <td>JVM将使用类A的类装载器,将类A装入内存(前提:类A还没有装入内存),不对类A做类的初始化工作.<br>
 * 返回类A的Class的对象.</td>
 * </tr>
 * <tr valign="top">
 * <td><code>Class klass={@link java.lang.Class#forName(String) Class.forName}("类全名");</code></td>
 * <td>装载连接初始化类.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td><code>Class klass={@link java.lang.ClassLoader#loadClass(String) ClassLoader.loadClass}("类全名");</code></td>
 * <td>装载类,不连接不初始化.</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <h3>instanceof运算符/isAssignableFrom/isInstance(Object obj) 区别</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>instanceof运算符</td>
 * <td>针对实例,是用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例<br>
 * 格式是:oo instanceof TypeName<br>
 * 第一个参数是对象实例名,第二个参数是具体的类名或接口名<br>
 * instanceof是Java的一个二元操作符,{@code ==,>,<}和是同一类东东,作用是测试它左边的对象是否是它右边的类的实例,返回boolean类型的数据</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>isAssignableFrom</td>
 * <td>针对class对象,是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口.<br>
 * 通常调用格式是Class1.isAssignableFrom(Class2)<br>
 * 调用者和参数都是java.lang.Class类型.</td>
 * </tr>
 * <tr valign="top">
 * <td>isInstance(Object obj)方法</td>
 * <td>obj是被测试的对象,如果obj是调用这个方法的class或接口 的实例,则返回true.<br>
 * 这个方法是instanceof运算符的动态等价</td>
 * </tr>
 * </table>
 * 
 * <p>
 * instanceof :子 {@code ----->} 父 <br>
 * isAssignableFrom :父 {@code ----->} 子
 * </p>
 * </blockquote>
 *
 * @author feilong
 * @version 1.0.0 2012-6-1 下午7:19:47
 * @version 1.2.1 2015-6-21 20:50 update javadoc
 * @see org.apache.commons.lang3.ClassUtils
 * @since 1.0.0
 */
public final class ClassUtil{

    /** Don't let anyone instantiate this class. */
    private ClassUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 返回一个类.
     * 
     * <blockquote>
     * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * <tr valign="top">
     * <td><code>Class klass=对象引用o.getClass();</code></td>
     * <td>返回引用o运行时真正所指的对象(因为:儿子对象的引用可能会赋给父对象的引用变量中)所属的类O的Class的对象.<br>
     * 谈不上对类O做什么操作.</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><code>Class klass=A.class;</code></td>
     * <td>JVM将使用类A的类装载器,将类A装入内存(前提:类A还没有装入内存),不对类A做类的初始化工作.<br>
     * 返回类A的Class的对象.</td>
     * </tr>
     * <tr valign="top">
     * <td><code>Class klass=Class.forName("类全名");</code></td>
     * <td>装载连接初始化类.</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><code>Class klass=ClassLoader.loadClass("类全名");</code></td>
     * <td>装载类,不连接不初始化.</td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * @param className
     *            包名+类名 "org.jfree.chart.ChartFactory"
     * @return the class
     * @throws ClassNotFoundException
     *             the class not found exception
     * @since 1.0.7
     */
    public static Class<?> loadClass(String className) throws ClassNotFoundException{
        return Class.forName(className);// JVM查找并加载指定的类
    }

    /**
     * 是不是某个类的实例.
     * 
     * <h3>instanceof运算符/isAssignableFrom/isInstance(Object obj) 区别</h3>
     * 
     * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * <tr valign="top">
     * <td>instanceof运算符</td>
     * <td>针对实例,是用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例<br>
     * 格式是:oo instanceof TypeName<br>
     * 第一个参数是对象实例名,第二个参数是具体的类名或接口名<br>
     * instanceof是Java的一个二元操作符,{@code ==,>,<}和是同一类东东,作用是测试它左边的对象是否是它右边的类的实例,返回boolean类型的数据</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>isAssignableFrom</td>
     * <td>针对class对象,是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口.<br>
     * 通常调用格式是Class1.isAssignableFrom(Class2)<br>
     * 调用者和参数都是java.lang.Class类型.</td>
     * </tr>
     * <tr valign="top">
     * <td>isInstance(Object obj)方法</td>
     * <td>obj是被测试的对象,如果obj是调用这个方法的class或接口 的实例,则返回true.<br>
     * 这个方法是instanceof运算符的 <span style="color:red">动态等价</span></td>
     * </tr>
     * </table>
     * 
     * <p>
     * instanceof :子 {@code ----->} 父 <br>
     * isAssignableFrom :父 {@code ----->} 子
     * </p>
     * </blockquote>
     * 
     * @param obj
     *            实例
     * @param klass
     *            类
     * @return 如果 obj 是此类的实例,则返回 true;<br>
     *         如果 <code>null == klass</code> 返回 false
     * @see java.lang.Class#isInstance(Object)
     */
    public static boolean isInstance(Object obj,Class<?> klass){
        return null == klass ? false : klass.isInstance(obj);
    }

    /**
     * 判断 obj 是否isInstance 任意的一个 <code>klasses</code>.
     *
     * @param obj
     *            the obj
     * @param klasses
     *            the klasses
     * @return true, if checks if is instance; if <code>null == klasses</code> return false
     * @since 1.5.6
     */
    public static boolean isInstanceAnyClass(Object obj,Class<?>[] klasses){
        if (null == klasses){
            return false;
        }

        for (Class<?> klass : klasses){
            if (isInstance(obj, klass)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if is assignable from.
     * 
     * <p>
     * instanceof :子 {@code ----->} 父 <br>
     * isAssignableFrom :父 {@code ----->} 子
     * </p>
     *
     * @param klass
     *            the klass
     * @param cls
     *            the cls
     * @return true, if checks if is assignable from <br>
     *         如果 <code>klass</code> 是null,返回false<br>
     *         如果 <code>cls</code> 是null,返回false
     * @see java.lang.Class#isAssignableFrom(Class)
     * @see org.apache.commons.lang3.ClassUtils#isAssignable(Class, Class)
     * @since 1.4.0
     */
    public static boolean isAssignableFrom(Class<?> klass,Class<?> cls){
        return (null == klass || null == cls) ? false : klass.isAssignableFrom(cls);
    }

    /**
     * 判断对象是否是接口.
     * 
     * @param ownerClass
     *            对象class
     * @return 是返回true<br>
     *         如果 <code>ownerClass</code> 是null,返回false
     * @see java.lang.Class#getModifiers()
     * @see java.lang.reflect.Modifier#isInterface(int)
     */
    public static boolean isInterface(Class<?> ownerClass){
        if (null == ownerClass){
            return false;
        }
        int modifiers = ownerClass.getModifiers();// 返回此类或接口以整数编码的 Java 语言修饰符
        return Modifier.isInterface(modifiers);// 对类和成员访问修饰符进行解码
    }

    /**
     * 解析参数,获得参数类型,如果参数 paramValues 是null 返回 null.
     * 
     * @param paramValues
     *            参数值
     * @return 如果参数 paramValues 是null 返回 null
     * @see org.apache.commons.lang3.ClassUtils#toClass(Object...)
     * @since 1.1.1
     */
    public static Class<?>[] toClass(Object...paramValues){
        return org.apache.commons.lang3.ClassUtils.toClass(paramValues);
    }

    /**
     * 获得 class info map for LOGGER.
     *
     * @param klass
     *            the clz
     * @return the map for log
     */
    public static Map<String, Object> getClassInfoMapForLog(Class<?> klass){
        if (Validator.isNullOrEmpty(klass)){
            return Collections.emptyMap();
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("clz.getCanonicalName()", klass.getCanonicalName());//"com.feilong.core.date.DatePattern"
        map.put("clz.getName()", klass.getName());//"com.feilong.core.date.DatePattern"
        map.put("clz.getSimpleName()", klass.getSimpleName());//"DatePattern"

        map.put("clz.getComponentType()", klass.getComponentType());
        // 类是不是"基本类型". 基本类型,包括void和boolean、byte、char、short、int、long、float 和 double这几种类型.
        map.put("clz.isPrimitive()", klass.isPrimitive());

        // 类是不是"本地类".本地类,就是定义在方法内部的类.
        map.put("clz.isLocalClass()", klass.isLocalClass());
        // 类是不是"成员类".成员类,是内部类的一种,但是它不是"内部类"或"匿名类".
        map.put("clz.isMemberClass()", klass.isMemberClass());

        //isSynthetic()是用来判断Class是不是"复合类".这在java应用程序中只会返回false,不会返回true.因为,JVM中才会产生复合类,在java应用程序中不存在"复合类"！
        map.put("clz.isSynthetic()", klass.isSynthetic());
        map.put("clz.isArray()", klass.isArray());
        map.put("clz.isAnnotation()", klass.isAnnotation());

        //当且仅当这个类是匿名类此方法返回true.
        map.put("clz.isAnonymousClass()", klass.isAnonymousClass());
        map.put("clz.isEnum()", klass.isEnum());

        return map;
    }
}