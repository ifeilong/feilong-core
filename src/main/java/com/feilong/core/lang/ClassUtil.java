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

import org.apache.commons.lang3.Validate;

import com.feilong.core.lang.reflect.ReflectException;
import com.feilong.tools.slf4j.Slf4jUtil;

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
 * <th align="left">示例</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.lang.Class#getCanonicalName() getCanonicalName()}</td>
 * <td>返回 <b>Java Language Specification</b> 中所定义的底层类的规范化名称。<br>
 * 如果底层类没有规范化名称（即如果底层类是一个组件类型没有规范化名称的本地类、匿名类或数组）,则返回 null。</td>
 * <td>"com.feilong.core.date.DatePattern"</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.lang.Class#getName() getName()}</td>
 * <td>除了数组外,其他的类都是输出类全名,以 String 的形式返回此 Class 对象所表示的实体（类、接口、数组类、基本类型或 void）名称。
 * 
 * <dl>
 * <dt>1、此类对象表示的是非数组类型的引用类型</dt>
 * <dd>返回该类的二进制名称,Java Language Specification, Second Edition 对此作了详细说明。</dd>
 * 
 * <dt>2、此类对象表示一个基本类型或 void</dt>
 * <dd>返回的名字是一个与该基本类型或 void 所对应的 Java 语言关键字相同的 String。</dd>
 * 
 * <dt>3、此类对象表示一个数组类</dt>
 * <dd>名字的内部形式为：表示该数组嵌套深度的一个或多个 '[' 字符加元素类型名。</dd>
 * </dl>
 * 
 * </td>
 * <td>"com.feilong.core.date.DatePattern"</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.lang.Class#getSimpleName() getSimpleName()}</td>
 * <td>
 * 返回<b>源代码中</b>给出的底层类的简称。<br>
 * 如果底层类是匿名的则返回一个空字符串。<br>
 * 数组的简称即附带 "[]" 的组件类型的简称。特别地,组件类型为匿名的数组的简称是 "[]"。
 * </td>
 * <td>"DatePattern"</td>
 * </tr>
 * 
 * </table>
 * 
 * <p>
 * {@link java.lang.Class#getCanonicalName() getCanonicalName()} 和 {@link java.lang.Class#getName() getName()} 其实这两个方法,对于大部分class来说,没有什么不同的
 * <br>
 * 但是对于array就显示出来了.
 * </p>
 * 
 * <ul>
 * <li>{@link java.lang.Class#getName() getName()} 返回的是<b>[[Ljava.lang.String</b>之类的表现形式,</li>
 * <li>而 {@link java.lang.Class#getCanonicalName() getCanonicalName()} 返回的就是跟我们声明类似的形式.</li>
 * </ul>
 * 
 * </blockquote>
 * 
 * <h3>instanceof运算符/isAssignableFrom/isInstance(Object obj) 区别</h3>
 * 
 * <blockquote>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>instanceof运算符</td>
 * <td>针对实例,是用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例<br>
 * 格式是:oo instanceof TypeName<br>
 * 第一个参数是对象实例名,第二个参数是具体的类名或接口名<br>
 * instanceof是Java的一个二元操作符,{@code ==,>,<}和是同一类东东,作用是测试它左边的对象是否是它右边的类的实例,返回boolean类型的数据</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>isAssignableFrom</td>
 * <td>针对class对象,是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口.<br>
 * 通常调用格式是Class1.isAssignableFrom(Class2)<br>
 * 调用者和参数都是java.lang.Class类型.</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>isInstance(Object obj)方法</td>
 * <td>obj是被测试的对象,如果obj是调用这个方法的class或接口 的实例,则返回true.<br>
 * 这个方法是instanceof运算符的动态等价</td>
 * </tr>
 * 
 * </table>
 * 
 * <p>
 * instanceof :子 {@code ----->} 父 <br>
 * isAssignableFrom :父 {@code ----->} 子
 * </p>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see <a href="http://www.cnblogs.com/maokun/p/6771365.html">Class的getName、getSimpleName与getCanonicalName的区别</a>
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

    //---------------------------------------------------------------

    /**
     * 判断一个对象 <code>obj</code> 是不是某个类 <code>klass</code> 的实例.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>instanceof :子 {@code ----->} 父</li>
     * <li>isAssignableFrom :父 {@code ----->} 子</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ClassUtil.isInstance(new User(), null)               =   false
     * ClassUtil.isInstance(new User(), Comparable.class)   =   true
     * ClassUtil.isInstance("1234", CharSequence.class)     =   true
     * ClassUtil.isInstance("1234", Integer.class)          =   false
     * ClassUtil.isInstance(null, CharSequence.class)       =   false
     * ClassUtil.isInstance(null, Integer.class)            =   false
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>instanceof 运算符/isAssignableFrom/isInstance(Object obj) 区别</h3>
     * 
     * <blockquote>
     * 
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * 
     * <tr valign="top">
     * <td>instanceof运算符</td>
     * <td>
     * <b>针对实例</b>,是用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例<br>
     * instanceof是Java的一个二元操作符,和{@code ==,>,<}是同一类东东,作用是测试它左边的对象是否是它右边的类的实例,返回boolean类型的数据<br>
     * 格式是: oo instanceof TypeName<br>
     * 第一个参数是对象实例名,第二个参数是具体的类名或接口名<br>
     * </td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>isAssignableFrom</td>
     * <td>
     * <b>针对class对象</b>,是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口.<br>
     * 格式是: Class1.isAssignableFrom(Class2)<br>
     * 调用者和参数都是java.lang.Class类型.
     * </td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>java.lang.Class.isInstance(Object obj)方法</td>
     * <td>
     * obj是被测试的对象,如果obj是调用这个方法的class或接口 的实例,则返回true.<br>
     * 这个方法是instanceof运算符的 <span style="color:red">动态等价</span>
     * </td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     * 
     * @param obj
     *            实例
     * @param klass
     *            类
     * @return 如果 obj 是此类的实例,则返回 true;<br>
     *         如果 <code>obj</code> 是null,返回 false<br>
     *         如果 <code>klass</code> 是null,返回 false<br>
     * @see java.lang.Class#isInstance(Object)
     */
    public static boolean isInstance(Object obj,Class<?> klass){
        return null != klass && klass.isInstance(obj);
    }

    /**
     * 判断 <code>obj</code> 是否isInstance 任意的一个 <code>klasses</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ClassUtil.isInstanceAnyClass(null, toArray(Integer.class, CharSequence.class))           =   false
     * ClassUtil.isInstanceAnyClass("1234", toArray(Comparable.class, CharSequence.class))      =   true
     * ClassUtil.isInstanceAnyClass(new User(), null)                                           =   false
     * ClassUtil.isInstanceAnyClass(new User(), toArray(Comparable.class, CharSequence.class))  =   true
     * ClassUtil.isInstanceAnyClass(new User(), toArray(Integer.class, CharSequence.class))     =   false
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj
     *            任意的对象
     * @param klasses
     *            the klasses
     * @return 如果 <code>null == klasses</code> ,返回 false<br>
     *         true, if checks if is instance;
     * @since 1.5.6
     */
    public static boolean isInstanceAnyClass(Object obj,Class<?>...klasses){
        if (null == klasses){
            return false;
        }

        //---------------------------------------------------------------
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
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ClassUtil.isAssignableFrom(Comparable.class, new User().getClass())      = true
     * ClassUtil.isAssignableFrom(null, new User().getClass())                  = false
     * ClassUtil.isAssignableFrom(CharSequence.class, "1234".getClass())        = true
     * ClassUtil.isAssignableFrom(CharSequence.class, null)                     = false
     * </pre>
     * 
     * </blockquote>
     *
     * @param klass
     *            the klass
     * @param cls
     *            the cls
     * @return 如果 <code>klass</code> 是null,返回false<br>
     *         如果 <code>cls</code> 是null,返回false
     * @see java.lang.Class#isAssignableFrom(Class)
     * @see org.apache.commons.lang3.ClassUtils#isAssignable(Class, Class)
     * @since 1.4.0
     */
    public static boolean isAssignableFrom(Class<?> klass,Class<?> cls){
        return null != klass && null != cls && klass.isAssignableFrom(cls);
    }

    /**
     * 判断类 <code>ownerClass</code> 是否是接口.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ClassUtil.isInterface(null)                  =   false
     * ClassUtil.isInterface(DatePattern.class)     =   false
     * ClassUtil.isInterface(Validator.class)       =   false
     * ClassUtil.isInterface(CharSequence.class)    =   true
     * ClassUtil.isInterface(List.class)            =   true
     * ClassUtil.isInterface(Map.class)             =   true
     * </pre>
     * 
     * </blockquote>
     * 
     * @param ownerClass
     *            对象class
     * @return 是返回true<br>
     *         如果 <code>ownerClass</code> 是null,返回false
     * @see java.lang.Class#getModifiers()
     * @see java.lang.reflect.Modifier#isInterface(int)
     */
    public static boolean isInterface(Class<?> ownerClass){
        return null != ownerClass && Modifier.isInterface(ownerClass.getModifiers());// 对类和成员访问修饰符进行解码
    }

    /**
     * 解析对象参数 <code>paramValues</code> ,获得参数类型.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * assertArrayEquals(new Class[] { String.class, String.class }, ClassUtil.toClass("a", "a"));
     * assertArrayEquals(new Class[] { Integer.class, Boolean.class }, ClassUtil.toClass(1, true));
     * </pre>
     * 
     * </blockquote>
     * 
     * @param paramValues
     *            参数值
     * @return 如果 <code>paramValues</code> 是null,返回 null<br>
     * @see org.apache.commons.lang3.ClassUtils#toClass(Object...)
     * @see org.apache.commons.lang3.ClassUtils#convertClassNamesToClasses(java.util.List)
     * @since 1.1.1
     */
    public static Class<?>[] toClass(Object...paramValues){
        return null == paramValues ? null : org.apache.commons.lang3.ClassUtils.toClass(paramValues);
    }

    //---------------------------------------------------------------

    /**
     * JVM查找并加载指定的类.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * FeiLongVersion feiLongVersion = ClassUtil.loadClass("com.feilong.core.FeiLongVersion");
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>和直接调用 {@link Class#forName(String)}的区别:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>一般情况下 = {@link Class#forName(String)}</li>
     * 
     * <li>Returns the class represented by {@code className} using the {@code classLoader}. <br>
     * 支持 " {@code java.util.Map.Entry[]}", "{@code java.util.Map$Entry[]}", "{@code [Ljava.util.Map.Entry;}", and
     * "{@code [Ljava.util.Map$Entry;}".</li>
     * 
     * <li>
     * <p>
     * 会尝试从下面的顺序加载class:
     * </p>
     * 
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}</li>
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}</li>
     * </ul>
     * 
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>对比:</h3>
     * <blockquote>
     * 
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * 
     * <tr valign="top">
     * <td><code>Class klass=对象引用o.{@link java.lang.Object#getClass() getClass()};</code></td>
     * <td>返回引用o运行时<b>真正所指的对象</b>(因为:儿子对象的引用可能会赋给父对象的引用变量中)所属的类O的Class的对象.<br>
     * 谈不上对类O做什么操作.</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><code>Class klass=A.class;</code></td>
     * <td>JVM将使用类A的类装载器,将类A装入内存(前提:类A还没有装入内存),不对类A做类的初始化工作.<br>
     * 返回类A的Class的对象.</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td><code>Class klass={@link java.lang.Class#forName(String) Class.forName}("类全名");</code></td>
     * <td>装载连接<b>初始化类</b>.</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><code>Class klass={@link java.lang.ClassLoader#loadClass(String) ClassLoader.loadClass}("类全名");</code></td>
     * <td>装载类,不连接不初始化.</td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     * 
     * @param className
     *            包名+类名,比如 "com.feilong.core.FeiLongVersion"
     * @return 如果 <code>className</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>className</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>className</code> 找不到相关类,那么抛出 {@link ReflectException}
     * @see java.lang.ClassLoader#loadClass(String)
     * @see java.lang.Class#forName(String)
     * @see java.lang.Class#forName(String, boolean, ClassLoader)
     * @see org.apache.commons.lang3.ClassUtils#getClass(String)
     * @see org.apache.commons.lang3.ClassUtils#getClass(ClassLoader, String, boolean)
     * @see "org.springframework.util.ClassUtils#forName(String, ClassLoader)"
     * @since 1.6.2
     */
    public static Class<?> getClass(String className){
        Validate.notBlank(className, "className can't be blank!");

        //---------------------------------------------------------------
        try{
            return org.apache.commons.lang3.ClassUtils.getClass(className);
        }catch (Exception e){
            throw new ReflectException(Slf4jUtil.format("className:[{}]", className), e);
        }
    }

}