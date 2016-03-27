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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.Validate;

import com.feilong.core.Validator;
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
 * 在《Effective Java 》这本书中也提到这个原则,float和double只能用来做科学计算或者是工程计算,在商业计算中我们要用 {@link java.math.BigDecimal}。
 * </p>
 * </blockquote>
 * 
 * <h3><a name="RoundingMode">JAVA 8种舍入法:</a></h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
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
 * 若是正数,舍入行为类似于ROUND_DOWN；<br>
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
 * {@code 1.15 return 1.2} {@code 1.25 return 1.2}</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link RoundingMode#UNNECESSARY}</td>
 * <td>无需舍入</td>
 * </tr>
 * </table>
 * </blockquote>
 *
 * @author feilong
 * @version 1.4.0 2015年8月3日 上午3:06:20
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

    /**
     * 四舍五入 {@link RoundingMode#HALF_UP},取整,无小数.
     * 
     * <p style="color:red">
     * 注意{@link RoundingMode#HALF_UP} -2.5 会变成-3,如果是 Math.round(-2.5) 会是-2
     * </p>
     * 
     * @param number
     *            number,可以是字符串类型的数字,也可以是任一number类型
     * @return 四舍五入{@link RoundingMode#HALF_UP},取整,无小数<br>
     *         如果 isNotNullOrEmpty(number)返回null
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     */
    public static BigDecimal toNoScale(Serializable number){
        return toNoScale(number, RoundingMode.HALF_UP);
    }

    /**
     * To no scale.
     * 
     * @param number
     *            number,可以是字符串类型的数字,也可以是任一number类型
     * @param roundingMode
     *            舍入法 {@link RoundingMode}
     * @return {@link RoundingMode},取整,无小数<br>
     *         如果 isNotNullOrEmpty(number)返回null {@link RoundingMode},取整,无小数
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @since 1.0.7
     */
    public static BigDecimal toNoScale(Serializable number,RoundingMode roundingMode){
        if (Validator.isNullOrEmpty(number)){
            return null;
        }
        //　将int、long、double、string类型的数值转为BigDecimal.使用double会造成精度丢失,
        //而使用BigDecimal就是为了解决精度丢失的问题,建议使用String方式转换.
        BigDecimal bigDecimal = ConvertUtil.toBigDecimal(number);
        return setScale(bigDecimal, 0, roundingMode);
    }

    // ***********************************************************************************

    // [start]Divide

    /**
     * 获得 除法结果one/two,四舍五入 取整,不需要再次toNoScale转换了.
     * <p>
     * 当two 是空或者是0的时候,直接返回one<br>
     * 否则返回除法结果one/two,四舍五入取整.
     * 
     * @param one
     *            除数
     * @param two
     *            被除数,自动转成BigDecimal做除法运算
     * @return 当two 是空或者是0的时候,直接返回one<br>
     *         否则返回除法结果one/two,四舍五入 取整
     */
    public static BigDecimal getDivideNoScaleValue(BigDecimal one,Serializable two){
        return getDivideValue(one, two, 0);
    }

    /**
     * 获得进度,默认格式为 {@link NumberPattern#PERCENT_WITH_NOPOINT}.
     *
     * @param current
     *            当前量
     * @param total
     *            总量
     * @return 50% 56% 58%不带小数点格式
     * @see NumberPattern
     * @since 1.0.7
     */
    public static String getProgress(Number current,Number total){
        String numberPattern = NumberPattern.PERCENT_WITH_NOPOINT;
        return getProgress(current, total, numberPattern);
    }

    /**
     * 计算进度.
     * 
     * <pre>
     * {@code
     *   Example 1:  
     *      NumberUtil.getProgress(5, 5, "##%")
     *      return 100%
     *   
     *   Example 2:
     *      NumberUtil.getProgress(2, 3, "#0.0%")
     *      return 66.7%
     * }
     * </pre>
     *
     * @param current
     *            当前量
     * @param total
     *            总量
     * @param numberPattern
     *            the number pattern {@link NumberPattern}
     * @return 根据numberPattern 返回 50.5%,100%.....
     * @see NumberPattern
     * @see #getDivideValue(BigDecimal, Serializable, int)
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
     * 获得 除法结果one/two,四舍五入 {@link RoundingMode#HALF_UP},小数位数指定.
     * 
     * <p>
     * 当two是空或者是0的时候,直接返回one<br>
     * 否则返回除法结果one/two,四舍五入,小数位数指定.
     * </p>
     * 
     * @param one
     *            除数
     * @param two
     *            被除数,自动转成BigDecimal做除法运算
     * @param scale
     *            要返回的 BigDecimal 商的标度,小数的位数
     * @return 当two 是空或者是0的时候,直接返回one<br>
     *         否则返回除法结果one/two,四舍五入 {@link RoundingMode#HALF_UP},小数位数指定
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @see java.math.RoundingMode#HALF_UP
     * @see java.math.BigDecimal#ROUND_HALF_UP
     */
    public static BigDecimal getDivideValue(BigDecimal one,Serializable two,int scale){
        return getDivideValue(one, two, scale, RoundingMode.HALF_UP);
    }

    /**
     * 获得 除法结果one/two,四舍五入,小数位数指定.
     * 
     * <p>
     * 当two 是空或者是0的时候,直接返回one<br>
     * 否则返回除法结果one/two,四舍五入,小数位数指定.
     * </p>
     * 
     * <p>
     * <b>注意:</b> <span style="color:red">不能直接one.divide(two),避免 exception:Non-terminating decimal expansion; no exact representable decimal
     * result</span><br>
     * 应该指定scale和roundingMode,保证对于无限小数有足够的范围来表示结果.
     * </p>
     *
     * @param one
     *            除数
     * @param two
     *            被除数,自动转成BigDecimal做除法运算
     * @param scale
     *            要返回的 BigDecimal 商的标度,小数的位数
     * @param roundingMode
     *            舍入法 {@link RoundingMode}
     * @return 当two 是空或者是0的时候,直接返回one<br>
     *         否则返回除法结果one/two,依据舍入法 {@link RoundingMode},小数位数指定
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @since 1.0.7
     */
    public static BigDecimal getDivideValue(BigDecimal one,Serializable two,int scale,RoundingMode roundingMode){
        Validate.notNull(roundingMode, "the roundingMode is null or empty!");

        String zero = "0";
        if (!isSpecificNumber(two, zero)){
            // 不能直接one.divide(two) 
            // 避免 exception:Non-terminating decimal expansion; no exact representable decimal result
            // 应该指定scale和roundingMode,保证对于无限小数有足够的范围来表示结果.
            BigDecimal divisor = ConvertUtil.toBigDecimal(two);
            return one.divide(divisor, scale, roundingMode);
        }
        return one;
    }

    // [end]

    // [start]Multiply

    /**
     * 获得两个数的乘积.
     * 
     * @param one
     *            乘数
     * @param two
     *            被乘数
     * @param scale
     *            标度,小数的位数,四舍五入
     * @return 获得两个数的乘积 <br>
     *         if isNotNullOrEmpty(two) return one
     * @see #getMultiplyValue(BigDecimal, Serializable)
     * @see #setScale(BigDecimal, int)
     */
    public static BigDecimal getMultiplyValue(BigDecimal one,Serializable two,int scale){
        BigDecimal result = getMultiplyValue(one, two);
        return setScale(result, scale);
    }

    /**
     * 获得 multiply value.
     * 
     * <p>
     * scale: (this.scale() + multiplicand.scale()).
     * </p>
     *
     * @param one
     *            乘数
     * @param two
     *            被乘数
     * @return 获得两个数的乘积 <br>
     *         if isNotNullOrEmpty(two) return one
     * @see java.math.BigDecimal#multiply(BigDecimal)
     * @since 1.0.8
     */
    public static BigDecimal getMultiplyValue(BigDecimal one,Serializable two){
        if (Validator.isNullOrEmpty(two)){
            return one;
        }

        BigDecimal multiplicand = ConvertUtil.toBigDecimal(two);
        return one.multiply(multiplicand);
    }

    /**
     * 获得 multiply value.
     * 
     * <p>
     * scale: (this.scale() + multiplicand.scale()).
     * </p>
     * 
     * @param one
     *            乘数
     * @param two
     *            被乘数
     * @return 获得两个数的乘积 <br>
     *         if isNotNullOrEmpty(two) return one
     * @see #getMultiplyValue(BigDecimal, Serializable)
     * @since 1.2.1
     */
    public static BigDecimal getMultiplyValue(Number one,Serializable two){
        return getMultiplyValue(ConvertUtil.toBigDecimal(one), two);
    }

    // [end]

    // [start]Add

    /**
     * 获得两个数的和,自动判断null的情况.
     * 
     * @param one
     *            第一个数
     * @param two
     *            第二个数
     * @return
     *         <ul>
     *         <li>如果两个数都是 null,则返回null</li>
     *         <li>第一个数是null,第二个数不是null,则,将第二个数转成BigDecimal 返回</li>
     *         <li>第一个数不是null,第二个数是null,则直接返回第一个数</li>
     *         <li>其他情况(两个数都不为空),返回 第一个数+第二个数</li>
     *         </ul>
     * @since 1.0
     */
    public static BigDecimal getAddValue(Number one,Number two){
        // 如果两个数都是 null,则返回null
        if (Validator.isNullOrEmpty(one) && Validator.isNullOrEmpty(two)){
            return null;
        }
        // 第一个数不是null,第二个数是null,则直接返回第一个数
        if (!Validator.isNullOrEmpty(one) && Validator.isNullOrEmpty(two)){
            return ConvertUtil.toBigDecimal(one);
        }

        BigDecimal augend = ConvertUtil.toBigDecimal(two);
        // 第一个数是null,第二个数不是null,则,将第二个数转成BigDecimal 返回
        if (Validator.isNullOrEmpty(one) && !Validator.isNullOrEmpty(two)){
            return augend;
        }

        // 其他情况其他情况(两个数都不为空),返回 第一个数+第二个数
        return ConvertUtil.toBigDecimal(one).add(augend);
    }

    /**
     * 所有数加起来(剔除null的值).
     *
     * @param numbers
     *            the numbers
     * @return the 添加 value
     * @since 1.2.1
     */
    public static BigDecimal getAddValue(Number...numbers){
        BigDecimal returnValue = BigDecimal.ZERO;
        for (Number number : numbers){
            if (Validator.isNotNullOrEmpty(number)){
                BigDecimal bigDecimal = ConvertUtil.toBigDecimal(number);
                returnValue = returnValue.add(bigDecimal);
            }
        }
        return returnValue;
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
     * @return 0.0,0.5,1.0,1.5,2.0,2.5.......
     */
    public static String toPointFive(Number value){
        Validate.notNull(value, "value can't be null/empty!");
        long avgRankLong = Math.round(Double.parseDouble(value.toString()) * 2);

        BigDecimal avgBigDecimal = BigDecimal.valueOf((double) (avgRankLong) / 2);
        return setScale(avgBigDecimal, 1).toString();
    }

    /**
     * 数字格式化,和 {@link NumberFormatUtil#format(Number, String)} 方法相等 .
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * {@code
     *  
     *  将数字转成百分数字符串,不带小数点,如 0.24转成24%
     *  NumberUtil.toString(0.24f, NumberPattern.PERCENT_WITH_NOPOINT)
     *  
     *  将数字转成百分数字符串,带两位小数点,如 0.24转成24.00%
     *  NumberUtil.toString(0.24f, NumberPattern.PERCENT_WITH_2POINT)
     *  }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param value
     *            值
     * @param pattern
     *            规则 {@link NumberPattern}
     * @return 格式化后的数字字符串
     * 
     * @see NumberFormatUtil#format(Number, String)
     */
    public static String toString(Number value,String pattern){
        return NumberFormatUtil.format(value, pattern);
    }

    // *****************************************************************************************************
    /**
     * int类型转换成16进制字符串.
     * 
     * @param i
     *            int值
     * @return int类型转换成16进制字符串
     */
    public static String intToHexString(int i){
        return Integer.toHexString(i);
    }

    /**
     * 16进制字符串转成int类型.
     * 
     * @param hexString
     *            16进制字符串
     * @return int类型
     */
    public static int hexStringToInt(String hexString){
        return Integer.parseInt(hexString, 16);
    }

    /**
     * 判断一个Object 类型的 value,是否是一个特定的数.
     * 
     * <p>
     * 系统自动将value 装成BigDecimal,并将specificNumber 也装成BigDecimal ,两个BigDecimal 进行compareTo,<br>
     * 如果是0 ,则返回true.
     * </p>
     * 
     * @param value
     *            Object 类型的 value,类型必须是 Number 或者 String
     * @param specificNumber
     *            一个特定的数
     * @return 系统自动将value 装成BigDecimal,并将specificNumber 也装成BigDecimal ,两个BigDecimal 进行compareTo,<br>
     *         如果是0 ,则返回true
     */
    public static boolean isSpecificNumber(Serializable value,String specificNumber){
        if (Validator.isNullOrEmpty(value)){
            return false;
        }

        String valueString = value.toString();
        // Number /String
        if (value instanceof Number || value instanceof String){
            BigDecimal bigDecimal = ConvertUtil.toBigDecimal(valueString);
            return 0 == bigDecimal.compareTo(ConvertUtil.toBigDecimal(specificNumber));
        }
        return false;
    }

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
     * @param number
     *            number
     * @param scale
     *            小数点位数
     * @return the big decimal
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     * @see java.math.RoundingMode#HALF_UP
     * @see java.math.BigDecimal#ROUND_HALF_UP
     */
    private static BigDecimal setScale(BigDecimal number,int scale){
        return setScale(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 设置精度.
     * 
     * @param number
     *            number
     * @param scale
     *            小数点位数
     * @param roundingMode
     *            舍入法 {@link RoundingMode} 参考: {@link <a href="#RoundingMode">JAVA 8种舍入法</a>}
     * @return the big decimal
     * @see <a href="#RoundingMode">JAVA 8种舍入法</a>
     */
    private static BigDecimal setScale(BigDecimal number,int scale,RoundingMode roundingMode){
        return number.setScale(scale, roundingMode);
    }
}
