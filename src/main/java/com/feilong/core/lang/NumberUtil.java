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

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.feilong.core.NumberPattern;
import com.feilong.core.bean.ConvertUtil;
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
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link RoundingMode#UP}</td>
 * <td>远离零的方向舍入. 向绝对值最大的方向舍入,只要舍弃位非0即进位.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#DOWN}</td>
 * <td>靠近零的方向舍入,向绝对值最小的方向输入,所有的位都要舍弃,不存在进位情况.</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link RoundingMode#CEILING}</td>
 * <td>靠近正无穷方向舍入  向正无穷方向舍入 向正最大方向靠拢.<br>
 * 若是正数,舍入行为类似于ROUND_UP,<br>
 * 若为负数,舍入行为类似于ROUND_DOWN.<br>
 * <span style="color:red">Math.round()方法就是使用的此模式.</span></td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#FLOOR}</td>
 * <td>靠近负无穷方向舍入  向负无穷方向舍入 向负无穷方向靠拢.<br>
 * 若是正数,舍入行为类似于ROUND_DOWN;<br>
 * 若为负数,舍入行为类似于ROUND_UP.</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link RoundingMode#HALF_UP}</td>
 * <td>四舍五入,生活中的舍入方法.<br>
 * 最近数字舍入(5进).<span style="color:red">这是我们最经典的四舍五入</span>.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#HALF_DOWN}</td>
 * <td>五舍六入,最近数字舍入(5舍). 在这里5是要舍弃的.</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link RoundingMode#HALF_EVEN}</td>
 * <td>精确舍入,银行家舍入法. <br>
 * 四舍六入;五分两种情况,如果前一位为奇数,则入位,否则舍去. <br>
 * 以下例子为保留小数点1位,那么这种舍入方式下的结果:  <br>
 * {@code 1.15 返回 1.2} {@code 1.25 返回  1.2}</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#UNNECESSARY}</td>
 * <td>无需舍入</td>
 * </tr>
 * </table>
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

    /** Don't let anyone instantiate this class. */
    private NumberUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // [start]Divide

    /**
     * 获得除法结果<code>one/two</code>,四舍五入{@link RoundingMode#HALF_UP},小数位数 <code>scale</code> 指定.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getDivideValue(0, 2, 0)   =   0
     * NumberUtil.getDivideValue(6, 4, 0)   =   2
     * NumberUtil.getDivideValue(10, 3, 2)  =   3.33
     * NumberUtil.getDivideValue(5, 3, 2)   =   1.67
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            除数
     * @param two
     *            被除数,自动转成{@link BigDecimal}做除法运算
     * @param scale
     *            标度,小数的位数,四舍五入,see {@link java.math.BigDecimal#setScale(int, RoundingMode)}
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
        return getDivideValue(one, two, scale, RoundingMode.HALF_UP);
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
     *            标度,小数的位数,see {@link java.math.BigDecimal#setScale(int, RoundingMode)}
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

        BigDecimal divisor = ConvertUtil.toBigDecimal(two);
        Validate.isTrue(!divisor.equals(new BigDecimal(0)), "two can't be zero!");

        // 不能直接one.divide(two),应该指定scale和roundingMode,保证对于无限小数有足够的范围来表示结果.
        // 避免 exception:Non-terminating decimal expansion; no exact representable decimal result
        return ConvertUtil.toBigDecimal(one).divide(divisor, scale, roundingMode);
    }

    // [end]

    // [start]Multiply

    /**
     * 获得两个数字的乘积,并转成{@link BigDecimal}返回.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getMultiplyValue(5, 2, 5)                         =   10.00000
     * NumberUtil.getMultiplyValue(new BigDecimal(6.25), 1.17, 5)   =   7.31250
     * </pre>
     * 
     * </blockquote>
     * 
     * @param one
     *            乘数
     * @param two
     *            被乘数
     * @param scale
     *            标度,小数的位数,四舍五入,see {@link java.math.BigDecimal#setScale(int, RoundingMode)}
     * @return 如果 <code>one</code> 是 null,抛出 {@link NullPointerException}<br>
     *         如果 <code>two</code> 是 null,抛出 {@link NullPointerException}<br>
     *         否则 convert to {@link BigDecimal} and multiply each other
     * @see #setScale(BigDecimal, int)
     * @since 1.5.5
     */
    public static BigDecimal getMultiplyValue(Number one,Number two,int scale){
        Validate.notNull(one, "one can't be null!");
        Validate.notNull(two, "two can't be null!");
        BigDecimal multiplyValue = ConvertUtil.toBigDecimal(one).multiply(ConvertUtil.toBigDecimal(two));
        return setScale(multiplyValue, scale);
    }
    // [end]

    // [start]Add

    /**
     * 所有数加起来.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getAddValue(2, 4, 5)              =   11
     * NumberUtil.getAddValue(new BigDecimal(6), 5) =   11
     * </pre>
     * 
     * </blockquote>
     * 
     * @param numbers
     *            the numbers
     * @return 如果 <code>numbers</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果有元素是null,抛出 {@link IllegalArgumentException}<br>
     *         否则将每个元素转换成{@link BigDecimal},并进行累加操作
     * @since 1.5.5
     */
    public static BigDecimal getAddValue(Number...numbers){
        Validate.noNullElements(numbers, "numbers can't be null!");

        BigDecimal sum = BigDecimal.ZERO;
        for (Number number : numbers){
            sum = sum.add(ConvertUtil.toBigDecimal(number));
        }
        return sum;
    }

    // [end]

    /**
     * 将数字转换成 小数点后一位为 0.0,0.5,1.0,1.5,2.0,2.5....
     * 
     * <p>
     * 通常用于 评分制
     * </p>
     *
     * @param value
     *            数字
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}
     */
    public static String toPointFive(Number value){
        Validate.notNull(value, "value can't be null/empty!");

        long avgRankLong = Math.round(Double.parseDouble(value.toString()) * 2);
        BigDecimal avgBigDecimal = BigDecimal.valueOf((double) (avgRankLong) / 2);
        return setScale(avgBigDecimal, 1).toString();
    }

    /**
     * 数字格式化,和 {@link NumberFormatUtil#format(Number, String)}方法相等 .
     * 
     * <h3>示例:</h3>
     * 
     * <pre class="code">
     * //将数字转成百分数字符串,不带小数点
     * NumberUtil.toString(0.24f, NumberPattern.PERCENT_WITH_NOPOINT)   = 24%
     * 
     * //将数字转成百分数字符串,带两位小数点
     * NumberUtil.toString(0.24f, NumberPattern.PERCENT_WITH_2POINT)    = 24.00%
     * </pre>
     * 
     * @param value
     *            值
     * @param numberPattern
     *            规则 {@link NumberPattern}
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>numberPattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>numberPattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果有异常,返回 {@link StringUtils#EMPTY}
     * @see NumberFormatUtil#format(Number, String)
     */
    public static String toString(Number value,String numberPattern){
        return NumberFormatUtil.format(value, numberPattern);
    }

    // *****************************************************************************************************

    /**
     * 获得进度,默认格式为 {@link NumberPattern#PERCENT_WITH_NOPOINT}.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * NumberUtil.getProgress(2, 3)     = 67%
     * </pre>
     * 
     * </blockquote>
     *
     * @param current
     *            当前量
     * @param total
     *            总量
     * @return 如果 <code>current</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>total</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 {@code current<=0},抛出 {@link IllegalArgumentException}<br>
     *         如果 {@code total<=0},抛出 {@link IllegalArgumentException}<br>
     *         如果 {@code current>total},抛出 {@link IllegalArgumentException}<br>
     * @see NumberPattern#PERCENT_WITH_NOPOINT
     * @see #getProgress(Number, Number, String)
     * @since 1.0.7
     */
    public static String getProgress(Number current,Number total){
        return getProgress(current, total, NumberPattern.PERCENT_WITH_NOPOINT);
    }

    /**
     * 计算进度.
     * 
     * <pre class="code">
     * NumberUtil.getProgress(5, 5, NumberPattern.PERCENT_WITH_NOPOINT) =   100%
     * NumberUtil.getProgress(2, 3, NumberPattern.PERCENT_WITH_1POINT)  =   66.7%
     * </pre>
     *
     * @param current
     *            当前量
     * @param total
     *            总量
     * @param numberPattern
     *            the number pattern {@link NumberPattern}
     * @return 如果 <code>current</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>total</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 {@code current<=0},抛出 {@link IllegalArgumentException}<br>
     *         如果 {@code total<=0},抛出 {@link IllegalArgumentException}<br>
     *         如果 {@code current>total},抛出 {@link IllegalArgumentException}<br>
     * @see NumberPattern
     * @see #getDivideValue(Number, Number, int)
     * @since 1.0.7
     */
    public static String getProgress(Number current,Number total,String numberPattern){
        Validate.notNull(current, "current can't be null/empty!");
        Validate.notNull(total, "total can't be null/empty!");

        Validate.isTrue(current.intValue() > 0, "current can not <=0");
        Validate.isTrue(total.intValue() > 0, "total can not <=0");
        Validate.isTrue(current.doubleValue() <= total.doubleValue(), "current can not > total");

        // XXX
        int scale = 8;
        BigDecimal bigDecimalCurrent = ConvertUtil.toBigDecimal(current);
        BigDecimal divideValue = getDivideValue(bigDecimalCurrent, total, scale);
        return toString(divideValue, numberPattern);
    }

    /**
     * 四舍五入 {@link RoundingMode#HALF_UP},取整,无小数.
     * 
     * <p style="color:red">
     * 注意{@link RoundingMode#HALF_UP} -2.5 会变成-3,如果是 {@link Math#round(double) Math.round(-2.5)} 会是-2
     * </p>
     *
     * @param value
     *            the value
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @see #toNoScale(Number, RoundingMode)
     */
    public static BigDecimal toNoScale(Number value){
        return toNoScale(value, RoundingMode.HALF_UP);
    }

    /**
     * 取整,无小数.
     * 
     * <p style="color:red">
     * 注意:{@link RoundingMode#HALF_UP} -2.5 会变成-3,如果是 {@link Math#round(double) Math.round(-2.5)} 会是-2
     * </p>
     * 
     * @param value
     *            the value
     * @param roundingMode
     *            舍入法 {@link RoundingMode}
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>roundingMode</code> 是null,抛出 {@link NullPointerException}<br>
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @since 1.5.5
     */
    public static BigDecimal toNoScale(Number value,RoundingMode roundingMode){
        Validate.notNull(value, "value can't be null!");
        Validate.notNull(roundingMode, "roundingMode can't be null!");

        //将int、long、double、string类型的数值转为BigDecimal.
        //使用double会造成精度丢失,而使用BigDecimal就是为了解决精度丢失的问题,建议使用String方式转换.
        return setScale(ConvertUtil.toBigDecimal(value), 0, roundingMode);
    }

    //************************************************************************************************

    /**
     * 小学学的 四舍五入的方式四舍五入 {@link RoundingMode#HALF_UP} 设置小数点位数.
     * 
     * <p>
     * 被舍入部分>=0.5向上 否则向下<br>
     * </p>
     * 
     * <p style="color:red">
     * 注意{@link RoundingMode#HALF_UP} -2.5 会变成-3,如果是 Math.round(-2.5) 会是-2
     * </p>
     * 
     * @param value
     *            number
     * @param scale
     *            标度,小数的位数,四舍五入,see {@link java.math.BigDecimal#setScale(int, RoundingMode)}
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @see java.math.RoundingMode#HALF_UP
     * @see java.math.BigDecimal#ROUND_HALF_UP
     */
    private static BigDecimal setScale(BigDecimal value,int scale){
        return setScale(value, scale, RoundingMode.HALF_UP);
    }

    /**
     * 设置精度.
     * 
     * @param value
     *            number
     * @param scale
     *            标度,小数的位数,see {@link java.math.BigDecimal#setScale(int, RoundingMode)}
     * @param roundingMode
     *            舍入法 {@link RoundingMode} 参考:<a href="#RoundingMode">JAVA 8种舍入法</a>
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>roundingMode</code>是null,抛出 {@link NullPointerException}
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     */
    private static BigDecimal setScale(BigDecimal value,int scale,RoundingMode roundingMode){
        Validate.notNull(value, "value can't be null!");
        Validate.notNull(roundingMode, "roundingMode can't be null!");
        return value.setScale(scale, roundingMode);
    }
}
