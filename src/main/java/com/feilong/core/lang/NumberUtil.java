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

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import com.feilong.core.NumberPattern;
import com.feilong.core.text.NumberFormatUtil;

/**
 * 处理{@link Integer},{@link Long},{@link BigDecimal}等数据类型.
 * 
 * <h3>{@link RoundingMode#HALF_UP}与 {@link Math#round(double)}的区别:</h3>
 * 
 * <blockquote>
 * <p style="color:red">
 * 注意{@link RoundingMode#HALF_UP} -2.5 会变成-3,如果是 {@link Math#round(double) Math.round(-2.5)} 会是-2
 * </p>
 * </blockquote>
 * 
 * <h3>{@link Double}转{@link BigDecimal}:</h3>
 * 
 * <blockquote>
 * <p>
 * 对于 double 转成 BigDecimal,推荐使用 BigDecimal.valueOf(double),不建议使用new BigDecimal(double),参见 JDK API
 * </p>
 * <ol>
 * <li>new BigDecimal(0.1) {@code ==>} 0.1000000000000000055511151231257827021181583404541015625</li>
 * <li>BigDecimal.valueOf(0.1) {@code ==>} 0.1</li>
 * </ol>
 * <p>
 * 在《Effective Java 》这本书中也提到这个原则,float和double只能用来做科学计算或者是工程计算,在商业计算中我们要用 {@link java.math.BigDecimal}.
 * </p>
 * </blockquote>
 * 
 * <h3><a name="RoundingMode">JAVA 8种舍入法:</a></h3>
 * 
 * <blockquote>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * 
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link RoundingMode#UP}</td>
 * <td>远离零的方向舍入. 向绝对值最大的方向舍入,只要舍弃位非0即进位.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#DOWN}</td>
 * <td>靠近零的方向舍入,向绝对值最小的方向输入,所有的位都要舍弃,不存在进位情况.</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link RoundingMode#CEILING}</td>
 * <td>靠近正无穷方向舍入  向正无穷方向舍入 向正最大方向靠拢.<br>
 * 若是正数,舍入行为类似于ROUND_UP,<br>
 * 若为负数,舍入行为类似于ROUND_DOWN.<br>
 * <span style="color:red">Math.round()方法就是使用的此模式.</span></td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#FLOOR}</td>
 * <td>靠近负无穷方向舍入  向负无穷方向舍入 向负无穷方向靠拢.<br>
 * 若是正数,舍入行为类似于ROUND_DOWN;<br>
 * 若为负数,舍入行为类似于ROUND_UP.</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link RoundingMode#HALF_UP}</td>
 * <td>四舍五入,生活中的舍入方法.<br>
 * 最近数字舍入(5进).<span style="color:red">这是我们最经典的四舍五入</span>.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#HALF_DOWN}</td>
 * <td>五舍六入,最近数字舍入(5舍). 在这里5是要舍弃的.</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link RoundingMode#HALF_EVEN}</td>
 * <td>精确舍入,银行家舍入法. <br>
 * 四舍六入;五分两种情况,如果前一位为奇数,则入位,否则舍去. <br>
 * 以下例子为保留小数点1位,那么这种舍入方式下的结果:  <br>
 * {@code 1.15 返回 1.2} {@code 1.25 返回  1.2}</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#UNNECESSARY}</td>
 * <td>无需舍入</td>
 * </tr>
 * 
 * </table>
 * 
 * <a name="RoundingMode_HALF_EVEN"></a>
 * <h3>关于 {@link RoundingMode#HALF_EVEN}:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * 该算法是由美国银行家提出了,主要用于修正采用上面四舍五入规则而产生的误差。如下：
 * 
 * 舍去位的数值小于5时,直接舍去。
 * 舍去位的数值大于5时,进位后舍去。
 * 当舍去位的数值等于5时,
 *          若5后面还有其他非0数值,则进位后舍去,
 *          若5后面是0时,则根据5前一位数的奇偶性来判断,奇数进位,偶数舍去。
 * </pre>
 * 
 * <b>对于上面的规则我们举例说明:</b>
 * 
 * <pre class="code">
 * 11.556 = 11.56 ------六入
 * 11.554 = 11.55 -----四舍
 * 
 * 11.5551 = 11.56 -----五后有数进位
 * 
 * 11.545 = 11.54 -----五后无数,若前位为偶数应舍去
 * 11.555 = 11.56 -----五后无数,若前位为奇数应进位
 * </pre>
 * 
 * </blockquote>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see Integer
 * @see Long
 * @see BigDecimal
 * @see Number
 * @see NumberPattern
 * @see RoundingMode
 * @see org.apache.commons.lang3.math.NumberUtils
 * @since 1.4.0
 */
public final class NumberUtil{

    /**
     * 一,<code>{@value}</code>.
     *
     * @see BigDecimal#ONE
     * @see NumberUtils#INTEGER_ONE
     * @since 1.10.7
     */
    public static final int  ONE              = 1;

    /**
     * 十,<code>{@value}</code>.
     * 
     * @since 1.10.7
     */
    public static final int  TEN              = 10;

    /**
     * 百,<code>{@value}</code>.
     * 
     * @since 1.10.7
     */
    public static final int  HUNDRED          = 100;

    /**
     * 千,<code>{@value}</code>.
     * 
     * @since 1.10.7
     */
    public static final int  THOUSAND         = 1000;

    //---------------------------------------------------------------
    /**
     * 万,<code>{@value}</code>.
     * 
     * @since 1.10.7
     */
    public static final int  TEN_THOUSAND     = 1_0000;

    /**
     * 十万,<code>{@value}</code>.
     * 
     * @since 1.10.7
     */
    public static final int  HUNDRED_THOUSAND = 10 * TEN_THOUSAND;

    /**
     * 百万,<code>{@value}</code>.
     * 
     * @since 1.10.7
     */
    public static final int  MILLION          = 10 * HUNDRED_THOUSAND;

    /**
     * 千万,<code>{@value}</code>.
     * 
     * @since 1.10.7
     */
    public static final int  TEN_MILLION      = 10 * MILLION;

    //---------------------------------------------------------------

    /**
     * 亿, <code>{@value}</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * {@link Integer#MAX_VALUE}:21_4748_3647 ,21亿, 注意使用的时候,超过最大值使用long来计算
     * </li>
     * </ol>
     * </blockquote>
     * 
     * @since 1.10.7
     */
    public static final int  HUNDRED_MILLION  = 10 * TEN_MILLION;

    /**
     * 十亿, <code>{@value}</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * {@link Integer#MAX_VALUE}:21_4748_3647 ,21亿, 注意使用的时候,超过最大值使用long来计算
     * </li>
     * </ol>
     * </blockquote>
     * 
     * @since 1.10.7
     */
    public static final int  BILLION          = 10 * HUNDRED_MILLION;

    /**
     * 百亿, <code>{@value}</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * {@link Integer#MAX_VALUE}:21_4748_3647 ,21亿, 注意使用的时候,超过最大值使用long来计算
     * </li>
     * </ol>
     * </blockquote>
     * 
     * @since 1.10.7
     */
    public static final long TEN_BILLION      = (long) (BILLION) * 10;

    //---------------------------------------------------------------
    /** Don't let anyone instantiate this class. */
    private NumberUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 判断第1个数字数字值 <code>one</code> 是不是{@code  > } (大于) 第2个数字数字值 <code>two</code>.
     * 
     * <h3>原理:</h3>
     * <blockquote>
     * <ol>
     * <li>是将两个数组转成 BigDecimal 类型,并调用 {@link java.math.BigDecimal#compareTo(BigDecimal)} 比较大小</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.isGatherThan(0, 0)                    =false
     * 
     * NumberUtil.isGatherThan(5, 4)                    =true
     * NumberUtil.isGatherThan(5, 4L)                   =true
     * NumberUtil.isGatherThan(5, 4.0f)                 =true
     * NumberUtil.isGatherThan(5, 4.0d)                 =true
     * NumberUtil.isGatherThan(5, toBigDecimal(4.0d))   =true
     * 
     * NumberUtil.isGatherThan(toBigDecimal(5.0d), 4)   =true
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            第1个数字
     * @param two
     *            第2个数字
     * @return 如果 <code>one</code> 大于 <code>two</code>,那么返回true,否则返回false <br>
     *         如果 <code>one</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.10.7
     */
    public static boolean isGatherThan(Number one,Number two){
        return compare(one, two) == 1;
    }

    /**
     * 判断第1个数字数字值 <code>one</code> 是不是{@code  >= } (大于等于) 第2个数字数字值 <code>two</code>.
     * 
     * <h3>原理:</h3>
     * <blockquote>
     * <ol>
     * <li>是将两个数组转成 BigDecimal 类型,并调用 {@link java.math.BigDecimal#compareTo(BigDecimal)} 比较大小</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.isGatherThanOrEquals(0, 0)                    =true
     * 
     * NumberUtil.isGatherThanOrEquals(5, 4)                    =true
     * NumberUtil.isGatherThanOrEquals(5, 4L)                   =true
     * NumberUtil.isGatherThanOrEquals(5, 4.0f)                 =true
     * NumberUtil.isGatherThanOrEquals(5, 4.0d)                 =true
     * NumberUtil.isGatherThanOrEquals(5, toBigDecimal(4.0d))   =true
     * 
     * NumberUtil.isGatherThanOrEquals(toBigDecimal(5.0d), 4)   =true
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            第1个数字
     * @param two
     *            第2个数字
     * @return 如果 <code>one</code> 大于等于 <code>two</code>,那么返回true,否则返回false<br>
     *         如果 <code>one</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.10.7
     */
    public static boolean isGatherThanOrEquals(Number one,Number two){
        int compareTo = compare(one, two);
        return compareTo == 1 || compareTo == 0;
    }

    /**
     * 判断第1个数字数字值 <code>one</code> 是不是{@code  = } (等于) 第2个数字数字值 <code>two</code>.
     * 
     * <h3>原理:</h3>
     * <blockquote>
     * <ol>
     * <li>是将两个数组转成 BigDecimal 类型,并调用 {@link java.math.BigDecimal#compareTo(BigDecimal)} 比较大小</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.isEquals(0, 0)                    =true
     * 
     * NumberUtil.isEquals(5, 4)                    =false
     * NumberUtil.isEquals(5, 5L)                   =true
     * NumberUtil.isEquals(5, 5.0f)                 =true
     * NumberUtil.isEquals(5, 5.0d)                 =true
     * NumberUtil.isEquals(5, toBigDecimal(5.0d))   =true
     * 
     * NumberUtil.isEquals(toBigDecimal(5.0d), 5)   =true
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            第1个数字
     * @param two
     *            第2个数字
     * @return 如果 <code>one</code> 等于 <code>two</code>,那么返回true,否则返回false.<br>
     *         如果 <code>one</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.10.7
     */
    public static boolean isEquals(Number one,Number two){
        return compare(one, two) == 0;
    }

    //---------------------------------------------------------------

    /**
     * 判断第1个数字数字值 <code>one</code> 是不是{@code  < } (小于) 第2个数字数字值 <code>two</code>.
     * 
     * <h3>原理:</h3>
     * <blockquote>
     * <ol>
     * <li>是将两个数组转成 BigDecimal 类型,并调用 {@link java.math.BigDecimal#compareTo(BigDecimal)} 比较大小</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.isLessThan(0, 0)                  =false
     * 
     * NumberUtil.isLessThan(5, 4)                  =false
     * NumberUtil.isLessThan(5, 5L)                 =false
     * NumberUtil.isLessThan(5, 5.0f)               =false
     * NumberUtil.isLessThan(4, 5.0d)               =true
     * NumberUtil.isLessThan(5, toBigDecimal(5.0d)) =false
     * 
     * NumberUtil.isLessThan(toBigDecimal(5.0d), 5) =false
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            第1个数字
     * @param two
     *            第2个数字
     * @return 如果 <code>one</code> 小于 <code>two</code>,那么返回true,否则返回false.<br>
     *         如果 <code>one</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.10.7
     */
    public static boolean isLessThan(Number one,Number two){
        return compare(one, two) == -1;
    }

    /**
     * 判断第1个数字数字值 <code>one</code> 是不是{@code  <= } (小于等于) 第2个数字数字值 <code>two</code>.
     * 
     * <h3>原理:</h3>
     * <blockquote>
     * <ol>
     * <li>是将两个数组转成 BigDecimal 类型,并调用 {@link java.math.BigDecimal#compareTo(BigDecimal)} 比较大小</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.isLessThanOrEquals(0, 0)                      =true
     * 
     * NumberUtil.isLessThanOrEquals(5, 4)                      =false
     * NumberUtil.isLessThanOrEquals(5, 5L)                     =true
     * NumberUtil.isLessThanOrEquals(5, 5.0f)                   =true
     * NumberUtil.isLessThanOrEquals(4, 5.0d)                   =true
     * NumberUtil.isLessThanOrEquals(5, toBigDecimal(5.0d))     =true
     * 
     * NumberUtil.isLessThanOrEquals(toBigDecimal(5.0d), 5)     =true
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            第1个数字
     * @param two
     *            第2个数字
     * @return 如果 <code>one</code> 小于等于 <code>two</code>,那么返回true,否则返回false <br>
     *         如果 <code>one</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.10.7
     */
    public static boolean isLessThanOrEquals(Number one,Number two){
        int compareTo = compare(one, two);
        return compareTo == -1 || compareTo == 0;
    }

    //---------------------------------------------------------------

    /**
     * Compare.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果one == two ,返回 0</li>
     * <li>如果one.equals(two) , 返回 0</li>
     * <li>其余返回 toBigDecimal(one).compareTo(toBigDecimal(two))</li>
     * </ol>
     * </blockquote>
     *
     * @param one
     *            the one
     * @param two
     *            the two
     * @return 如果 <code>one</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.10.7
     */
    private static int compare(Number one,Number two){
        Validate.notNull(one, "one can't be null!");
        Validate.notNull(two, "two can't be null!");

        //---------------------------------------------------------------
        if (one == two){
            return 0;
        }
        return one.equals(two) ? 0 : toBigDecimal(one).compareTo(toBigDecimal(two));
    }

    //---------------------------------------------------------------

    // [start]Divide

    /**
     * 获得除法结果<code>one/two</code>,四舍五入{@link RoundingMode#HALF_UP},小数位数 <code>scale</code> 指定.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getDivideValue(0, 2, 0)   =   toBigDecimal(0)
     * NumberUtil.getDivideValue(6, 4, 0)   =   toBigDecimal(2)
     * NumberUtil.getDivideValue(10, 3, 2)  =   toBigDecimal(3.33)
     * NumberUtil.getDivideValue(5, 3, 2)   =   toBigDecimal(1.67)
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            除数
     * @param two
     *            被除数,自动转成{@link BigDecimal}做除法运算
     * @param scale
     *            标度,小数的位数,四舍五入,用于 {@link java.math.BigDecimal#setScale(int, RoundingMode)}<br>
     *            如果为零或正数,则标度是小数点后的位数。<br>
     *            如果为负数,则将该数的非标度值乘以 10 的负 scale 次幂 (通常情况用不到负数的情况)
     * @return 如果 <code>one</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是 0,抛出 {@link IllegalArgumentException}<br>
     *         否则转换成{@link BigDecimal} 返回除法结果one/two,四舍五入 {@link RoundingMode#HALF_UP},小数位数 <code>scale</code> 指定
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @see java.math.RoundingMode#HALF_UP
     * @see java.math.BigDecimal#ROUND_HALF_UP
     * @see #getDivideValue(Number, Number, int, RoundingMode)
     * @since 1.5.5
     */
    public static BigDecimal getDivideValue(Number one,Number two,int scale){
        return getDivideValue(one, two, scale, HALF_UP);
    }

    /**
     * 获得除法结果<code>one/two</code>,指定舍入方式 <code>roundingMode</code> 以及 小数位数 <code>scale</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getDivideValue(0, 2, 0,RoundingMode.HALF_UP)   =   0
     * NumberUtil.getDivideValue(6, 4, 0,RoundingMode.HALF_UP)   =   2
     * NumberUtil.getDivideValue(10, 3, 2,RoundingMode.HALF_UP)  =   3.33
     * NumberUtil.getDivideValue(5, 3, 2,RoundingMode.HALF_UP)   =   1.67
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            除数
     * @param two
     *            被除数,自动转成{@link BigDecimal}做除法运算
     * @param scale
     *            标度,小数的位数,四舍五入,用于 {@link java.math.BigDecimal#setScale(int, RoundingMode)}<br>
     *            如果为零或正数,则标度是小数点后的位数。<br>
     *            如果为负数,则将该数的非标度值乘以 10 的负 scale 次幂 (通常情况用不到负数的情况)
     * @param roundingMode
     *            舍入法 {@link RoundingMode}
     * @return 如果 <code>one</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是 0,抛出 {@link IllegalArgumentException}<br>
     *         否则转换成{@link BigDecimal} 返回除法结果one/two,依据舍入法 <code>roundingMode</code>,小数位数 <code>scale</code> 指定
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @see java.math.BigDecimal#divide(BigDecimal, int, RoundingMode)
     * @since 1.5.5
     */
    public static BigDecimal getDivideValue(Number one,Number two,int scale,RoundingMode roundingMode){
        Validate.notNull(one, "one can't be null!");
        Validate.notNull(two, "two can't be null!");

        BigDecimal divisor = toBigDecimal(two);
        Validate.isTrue(!divisor.equals(ZERO), "two can't be zero!");

        // 不能直接one.divide(two),应该指定scale和roundingMode,保证对于无限小数有足够的范围来表示结果.
        // 避免 exception:Non-terminating decimal expansion; no exact representable decimal result (无法结束的除法表达式；没有精确的除结果)
        return toBigDecimal(one).divide(divisor, scale, roundingMode);
    }

    // [end]

    // [start]Multiply

    /**
     * 将第一个数字 <code>one</code> 和第二个数字 <code>two</code> 相乘,指定精度 <code>scale</code>, 返回{@link BigDecimal}.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getMultiplyValue(5, 2, 5)                         =   toBigDecimal("10.00000")
     * NumberUtil.getMultiplyValue(new BigDecimal(6.25), 1.17, 5)   =   toBigDecimal("7.31250")
     * NumberUtil.getMultiplyValue(9.86, 100, 0)                    =   toBigDecimal("986")
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            乘数
     * @param two
     *            被乘数
     * @param scale
     *            标度,小数的位数,四舍五入,用于 {@link java.math.BigDecimal#setScale(int, RoundingMode)}<br>
     *            如果为零或正数,则标度是小数点后的位数。<br>
     *            如果为负数,则将该数的非标度值乘以 10 的负 scale 次幂 (通常情况用不到负数的情况)
     * @return 如果 <code>one</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是 null,抛出 {@link NullPointerException}<br>
     *         否则 convert to {@link BigDecimal} and multiply each other
     * @see #setScale(Number, int)
     * @since 1.5.5
     */
    public static BigDecimal getMultiplyValue(Number one,Number two,int scale){
        Validate.notNull(one, "one can't be null!");
        Validate.notNull(two, "two can't be null!");
        //默认返回的精度: (this.scale() + multiplicand.scale()).
        BigDecimal multiplyValue = toBigDecimal(one).multiply(toBigDecimal(two));
        return setScale(multiplyValue, scale);
    }
    // [end]

    // [start]Add

    /**
     * 所有数加起来.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>支持跳过null 元素相加 (since 1.11.5)</li>
     * <li>但是如果所有元素都是null ,将会抛出 {@link IllegalArgumentException}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getAddValue(2, 4, 5)              =   11
     * NumberUtil.getAddValue(new BigDecimal(6), 5) =   11
     * NumberUtil.getAddValue(new BigDecimal(6), null) =   6
     * </pre>
     * 
     * </blockquote>
     * 
     * @param numbers
     *            the numbers
     * @return 如果 <code>numbers</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>numbers</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     *         如果所有元素都是null,抛出 {@link IllegalArgumentException}<br>
     *         否则将每个元素(跳过 null元素)转换成{@link BigDecimal},并进行累加操作
     * @since 1.5.5
     * @since 1.11.5 allow null element
     */
    public static BigDecimal getAddValue(Number...numbers){
        Validate.notEmpty(numbers, "numbers can't be null/empty!");

        //---------------------------------------------------------------
        if (isAllElementNull(numbers)){
            throw new IllegalArgumentException("can not all numbers is null!");
        }

        //---------------------------------------------------------------
        BigDecimal sum = ZERO;
        for (Number number : numbers){
            if (null == number){
                continue;
            }
            sum = sum.add(toBigDecimal(number));
        }
        return sum;
    }

    /**
     * 是否所有元素是null.
     *
     * @param numbers
     *            the numbers
     * @return true, if is all null
     * @since 1.11.5
     */
    private static boolean isAllElementNull(Number...numbers){
        for (Number number : numbers){
            if (null != number){
                return false;
            }
        }
        return true;
    }

    //---------------------------------------------------------------

    /**
     * 所有数相减.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getSubtractValue(0, 2, 3)                 =   -5
     * NumberUtil.getSubtractValue(0,null)                  =   0
     * NumberUtil.getSubtractValue(0,  new Integer[5])      =   0
     * NumberUtil.getSubtractValue(2, 1.1)                  =   0.9
     * NumberUtil.getSubtractValue(1000, 99.5, 99.0)        =   801.5
     * NumberUtil.getSubtractValue(1000, 50, null)          =   950
     * NumberUtil.getSubtractValue(-1000, -50, 100)         =   -1050
     * 
     * NumberUtil.getSubtractValue(null, 5) // NullPointerException
     * </pre>
     * 
     * </blockquote>
     *
     * @param beSubtractedValue
     *            被减数,如 100-10-5, 其中的100 就是被减数
     * @param subtractions
     *            减数,如 100-10-5, 其中的10 和5 就是减数
     * @return 如果 <code>beSubtractedValue</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>subtractions</code> 是null或者是empty,直接将<code>beSubtractedValue</code> 转成BigDecimal 并返回<br>
     *         循环 <code>subtractions</code> ,使用 <code>beSubtractedValue</code>减去元素的值,如果循环的原始null,跳过这次减法<br>
     * @since 1.10.6
     */
    public static BigDecimal getSubtractValue(Number beSubtractedValue,Number...subtractions){
        Validate.notNull(beSubtractedValue, "beSubtractedValue can't be null/empty!");

        //---------------------------------------------------------------

        BigDecimal result = toBigDecimal(beSubtractedValue);
        if (isNullOrEmpty(subtractions)){
            return result;
        }

        //---------------------------------------------------------------
        for (Number subtraction : subtractions){
            if (null == subtraction){//跳过 null 元素
                continue;
            }
            result = result.subtract(toBigDecimal(subtraction));
        }
        return result;
    }

    // [end]

    /**
     * 将数字 <code>value</code> 按照指定的格式 <code>numberPattern</code> 格式成字符串 .
     * 
     * <p>
     * 调用 {@link NumberFormatUtil#format(Number, String, RoundingMode)},当遇到需要舍入的时候,使用常用的 {@link RoundingMode#HALF_UP}
     * </p>
     * 
     * <h3>关于参数 <code>value</code>:</h3>
     * 
     * <blockquote>
     * <p>
     * <b>请尽量传递Integer,Long,BigDecimal,而不要使用 float,double等浮点类型</b>,否则可能结果不准确,特别是jdk8以下的版本,具体参见
     * <a href="https://github.com/venusdrogon/feilong-core/issues/165">NumberFormatUtilTest 在 jdk8 下面测试不通过</a>
     * </p>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <pre class="code">
     * <span style="color:green">//将数字转成百分数字符串,不带小数点</span>
     * NumberUtil.toString(0.24f, NumberPattern.PERCENT_WITH_NOPOINT)   = 24%
     * 
     * <span style="color:green">//将数字转成百分数字符串,带两位小数点</span>
     * NumberUtil.toString(0.24f, NumberPattern.PERCENT_WITH_2POINT)    = 24.00%
     * 
     * NumberUtil.toString(toBigDecimal(1.15), "#####.#")     =   1.2
     * NumberUtil.toString(toBigDecimal(1.25), "#####.#")     =   1.3
     * NumberUtil.toString(toBigDecimal(1.251), "#####.#")    =   1.3
     * 
     * NumberUtil.toString(toBigDecimal(-1.15), "#####.#")    =   -1.2
     * NumberUtil.toString(toBigDecimal(-1.25), "#####.#")    =   -1.3
     * NumberUtil.toString(toBigDecimal(-1.251), "#####.#")   =   -1.3
     * 
     * NumberUtil.toString(toBigDecimal(25.5), "RP #####")    =   RP 26
     * </pre>
     * 
     * @param value
     *            值
     * @param numberPattern
     *            转成字符串格式 {@link NumberPattern}
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>toStringPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>toStringPattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see NumberFormatUtil#format(Number, String, RoundingMode)
     */
    public static String toString(Number value,String numberPattern){
        return NumberFormatUtil.format(value, numberPattern, HALF_UP);
    }

    //---------------------------------------------------------------

    /**
     * 计算进度(当前量 <code>current</code>/总量 <code>total</code>,然后转成指定的字符串格式 <code>numberPattern</code>).
     * 
     * <p>
     * 常用于友好的显示 <b>下载进度</b>,<code>执行进度</code>等等场景
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * NumberUtil.getProgress(5, 5, NumberPattern.PERCENT_WITH_NOPOINT) = "100%"
     * NumberUtil.getProgress(2, 3, NumberPattern.PERCENT_WITH_1POINT)  = "66.7%"
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @param current
     *            当前量
     * @param total
     *            总量
     * @param numberPattern
     *            转成字符串格式 {@link NumberPattern}
     * @return 如果 <code>current</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>total</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 {@code current <= 0},抛出 {@link IllegalArgumentException}<br>
     *         如果 {@code total <= 0},抛出 {@link IllegalArgumentException}<br>
     *         如果 {@code current > total},抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>toStringPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>toStringPattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see NumberPattern
     * @see #getDivideValue(Number, Number, int)
     * @see #toString(Number, String)
     * @since 1.0.7
     */
    public static String getProgress(Number current,Number total,String numberPattern){
        Validate.notNull(current, "current can't be null/empty!");
        Validate.notNull(total, "total can't be null/empty!");

        Validate.isTrue(current.intValue() > 0, "current can not <=0");
        Validate.isTrue(total.intValue() > 0, "total can not <=0");
        Validate.isTrue(current.doubleValue() <= total.doubleValue(), "current can not > total");

        // XXX  scale = 8不是最优方案
        int scale = 8;
        BigDecimal divideValue = getDivideValue(current, total, scale);
        return toString(divideValue, numberPattern);
    }

    //********************setScale********************************************************

    /**
     * 使用四舍五入 {@link RoundingMode#HALF_UP} 设置小数点位数.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.setScale(5, 5)        = toBigDecimal("5.00000")
     * NumberUtil.setScale(5.2, 3)      = toBigDecimal("5.200")
     * NumberUtil.setScale(5.26, 1)     = toBigDecimal("5.3")
     * NumberUtil.setScale(-5.26, 1)    = toBigDecimal("-5.3")
     * 
     * NumberUtil.setScale(-0, 1)       = toBigDecimal("0.0")
     * 
     * NumberUtil.setScale(0, 1)        = toBigDecimal("0.0")
     * NumberUtil.setScale(0, 2)        = toBigDecimal("0.00")
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>被舍入部分{@code >=}0.5向上,否则向下<br>
     * </li>
     * <li>{@link RoundingMode#HALF_UP} -2.5 会变成-3,如果是 Math.round(-2.5) 会是-2</li>
     * </ol>
     * </blockquote>
     * 
     * @param value
     *            number
     * @param scale
     *            标度,小数的位数,四舍五入,用于 {@link java.math.BigDecimal#setScale(int, RoundingMode)}<br>
     *            如果为零或正数,则标度是小数点后的位数。<br>
     *            如果为负数,则将该数的非标度值乘以 10 的负 scale 次幂 (通常情况用不到负数的情况)
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @see java.math.RoundingMode#HALF_UP
     * @see java.math.BigDecimal#ROUND_HALF_UP
     * @since 1.8.6
     */
    public static BigDecimal setScale(Number value,int scale){
        return setScale(value, scale, HALF_UP);
    }

    /**
     * 使用<code>roundingMode</code> 来设置小数点位数.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.setScale(5, 5,HALF_UP)        = toBigDecimal("5.00000")
     * NumberUtil.setScale(5.2, 3,HALF_UP)      = toBigDecimal("5.200")
     * NumberUtil.setScale(5.26, 1,HALF_UP)     = toBigDecimal("5.3")
     * NumberUtil.setScale(-5.26, 1,HALF_UP)    = toBigDecimal("-5.3")
     * 
     * NumberUtil.setScale(-0, 1,HALF_UP)       = toBigDecimal("0.0")
     * 
     * NumberUtil.setScale(0, 1,HALF_UP)        = toBigDecimal("0.0")
     * NumberUtil.setScale(0, 2,HALF_UP)        = toBigDecimal("0.00")
     * 
     * NumberUtil.setScale(5, 5,null)           = toBigDecimal("5.00000")
     * NumberUtil.setScale(5.2, 3,null)         = toBigDecimal("5.200")
     * NumberUtil.setScale(5.26, 1,null)        = toBigDecimal("5.3")
     * NumberUtil.setScale(-5.26, 1,null)       = toBigDecimal("-5.3")
     * 
     * NumberUtil.setScale(-0, 1,null)          = toBigDecimal("0.0")
     * 
     * NumberUtil.setScale(0, 1,null)           = toBigDecimal("0.0")
     * NumberUtil.setScale(0, 2,null)           = toBigDecimal("0.00")
     * </pre>
     * 
     * </blockquote>
     * 
     * @param value
     *            number
     * @param scale
     *            标度,小数的位数,四舍五入,用于 {@link java.math.BigDecimal#setScale(int, RoundingMode)}<br>
     *            如果为零或正数,则标度是小数点后的位数。<br>
     *            如果为负数,则将该数的非标度值乘以 10 的负 scale 次幂 (通常情况用不到负数的情况)
     * @param roundingMode
     *            舍入模式{@link RoundingMode},如果 为null,使用常用的 {@link RoundingMode#HALF_UP}
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>roundingMode</code>是null,使用常用的 {@link RoundingMode#HALF_UP} <br>
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @since 1.8.6
     */
    public static BigDecimal setScale(Number value,int scale,RoundingMode roundingMode){
        Validate.notNull(value, "value can't be null!");
        return toBigDecimal(value).setScale(scale, defaultIfNull(roundingMode, HALF_UP));
    }
}
