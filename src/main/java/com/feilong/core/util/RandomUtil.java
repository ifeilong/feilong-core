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
package com.feilong.core.util;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;

import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 随机数工具类.
 * 
 * <ul>
 * <li>{@link java.lang.Math#random()}底层也是调用的 new Random(),值＝Random nextDouble()</li>
 * <li>把 {@link java.util.Random}对象作为一个全局实例(static)来使用.Java中 {@link java.util.Random} 是线程安全的(内部进行了加锁处理);</li>
 * <li>伪随机数</li>
 * <li>生成随机数的算法有很多种,最简单也是最常用的就是 "线性同余法":第n+1个数=(第n个数*29+37) % 1000,其中%是"求余数"运算符.</li>
 * </ul>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.lang.Math#random()
 * @see org.apache.commons.lang3.RandomUtils
 * @see org.apache.commons.lang3.RandomStringUtils
 * @since 1.0.0
 */
public final class RandomUtil{

    /**
     * Random object used by random method.
     * 
     * <p>
     * This has to be not local to the random method so as to not return the same value in the same millisecond.<br>
     * 把Random对象作为一个全局实例(static)来使用. Java中Random是线程安全的(内部进行了加锁处理);
     * </p>
     * 
     * @see org.apache.commons.lang.math.RandomUtils
     * @since 1.0.7
     */
    private static final Random JVM_RANDOM = new Random();

    /** Don't let anyone instantiate this class. */
    private RandomUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 创建0-最大值<b>(maxValue)</b>之间的随机数.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * RandomUtil.createRandom(8)
     * 创建一个小于8的随机数
     * 
     * 生成的结果是可能是 3
     * </pre>
     * 
     * </blockquote>
     * 
     * @param maxValue
     *            随机数最大值
     * @return 创建0-最大值之间的随机数<br>
     *         如果 <code>maxValue</code> 是null,那么抛出 {@link java.lang.NullPointerException NullPointerException}
     */
    public static long createRandom(Number maxValue){
        Validate.notNull(maxValue, "maxValue can't be null!");
        double random = JVM_RANDOM.nextDouble();
        return (long) Math.floor(random * maxValue.longValue());
    }

    /**
     * 创建最小值<code>minValue</code>和最大值<code>maxValue</code>之间的随机数.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * RandomUtil.createRandom(10, 20)
     * 创建一个数值是10-20之间的随机数
     * 
     * 生成的结果是可能是 12
     * </pre>
     * 
     * </blockquote>
     * 
     * @param minValue
     *            最小值
     * @param maxValue
     *            最大值
     * @return 创建最小值 <code>minValue</code> 和最大值 <code>maxValue</code> 之间的随机数<br>
     *         如果 <code>minValue</code>是 null,抛出 {@link java.lang.NullPointerException NullPointerException};<br>
     *         如果 <code>maxValue</code>是 null,抛出 {@link java.lang.NullPointerException NullPointerException};<br>
     *         如果 <code>maxLong</code>{@code <} <code>minLong</code>,抛出 {@link java.lang.IllegalArgumentException
     *         IllegalArgumentException}<br>
     *         如果 <code>minLong</code>{@code =} <code>maxLong</code>,直接返回 <code>minLong</code>
     * 
     * @see org.apache.commons.lang3.RandomUtils#nextInt(int, int)
     * @see org.apache.commons.lang3.RandomUtils#nextLong(long, long)
     * @see org.apache.commons.lang3.RandomUtils#nextFloat(float, float)
     * @see org.apache.commons.lang3.RandomUtils#nextDouble(double, double)
     */
    public static long createRandom(Number minValue,Number maxValue){
        Validate.notNull(minValue, "min can't be null!");
        Validate.notNull(maxValue, "max can't be null!");

        long minLong = minValue.longValue();
        long maxLong = maxValue.longValue();

        Validate.isTrue(maxLong >= minLong, Slf4jUtil.format("maxLong:[{}] can not < minLong:[{}]", maxLong, minLong));
        return RandomUtils.nextLong(minLong, maxLong);
    }

    // ********************************************************************

    /**
     * 生成一个指定长度<code>length</code>的 <b>随机正整数</b>.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * RandomUtil.createRandomWithLength(2)
     * 生成的结果是可能是 89
     * </pre>
     * 
     * </blockquote>
     *
     * @param length
     *            设定所取出随机数的长度.
     * @return 返回生成的随机数<br>
     *         如果 <code>length</code> {@code <=0} ,抛出 {@link IllegalArgumentException}
     */
    public static long createRandomWithLength(int length){
        Validate.isTrue(length > 0, "length:[%s] must >0", length);
        long num = 1;
        for (int i = 0; i < length; ++i){
            num = num * 10;
        }

        // 该值大于等于 0.0 且小于 1.0 正号的 double 值
        double random = JVM_RANDOM.nextDouble();
        random = random < 0.1 ? random + 0.1 : random;// 可能出现 0.09346924349151808
        return (long) (random * num);
    }

    // ******************************createRandomFromString**********************************

    /**
     * 随机抽取字符串char,拼接最小长度是<code>minLength</code>,最大长度是<code>maxLength</code>的字符串随机字符串.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * RandomUtil.createRandomFromString({@link com.feilong.core.Alphabet#DECIMAL Alphabet.DECIMAL}, 8, 20)
     * 从{@link com.feilong.core.Alphabet#DECIMAL Alphabet.DECIMAL}随机抽取字符,组成<b>最小长度是8</b>,<b>最大长度是20</b>的字符串
     * 
     * 生成的结果是可能是 142853574998970631
     * </pre>
     * 
     * </blockquote>
     *
     * @param str
     *            被抽取的字符串,比如示例中的 {@link com.feilong.core.Alphabet#DECIMAL}
     * @param minLength
     *            最小长度 ,比如示例中的 8
     * @param maxLength
     *            最大长度,比如示例中的 20
     * 
     * @return 随机抽取字符串char,拼接最小长度是 <code>minLength</code>,最大长度是<code>maxLength</code>的字符串随机字符串. <br>
     *         如上述示例,可能返回142853574998970631<br>
     *         如果 <code>str</code> 是null,抛出 {@link java.lang.NullPointerException NullPointerException};<br>
     *         如果 <code>str</code> 是 is blank,抛出 {@link java.lang.IllegalArgumentException IllegalArgumentException};<br>
     *         如果 <code>maxLength</code> {@code <=}0 ,抛出 {@link java.lang.IllegalArgumentException IllegalArgumentException}<br>
     *         如果 <code>maxLength</code> {@code <} minLength,抛出 {@link java.lang.IllegalArgumentException IllegalArgumentException}
     * 
     * @see #createRandomFromString(String, int)
     */
    public static String createRandomFromString(String str,int minLength,int maxLength){
        Validate.notBlank(str, "str can't be null/empty!");

        Validate.isTrue(maxLength > 0, Slf4jUtil.format("maxLength:[{}] can not zero", maxLength));
        Validate.isTrue(maxLength >= minLength, Slf4jUtil.format("maxLength:[{}] can not < minLength:[{}]", maxLength, minLength));

        long length = createRandom(minLength, maxLength);
        return createRandomFromString(str, (int) length);
    }

    /**
     * 随机抽取字符串<code>char</code>,拼接成指定长度<code>length</code>的字符串.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * RandomUtil.createRandomFromString({@link com.feilong.core.Alphabet#DECIMAL_AND_LETTERS Alphabet.DECIMAL_AND_LETTERS}, 5)
     * 
     * 从 {@link com.feilong.core.Alphabet#DECIMAL_AND_LETTERS  Alphabet.DECIMAL_AND_LETTERS} 随机抽取字符,组成长度是5的字符串
     * 生成的结果是可能是IFSMB
     * </pre>
     * 
     * </blockquote>
     * 
     * @param str
     *            被抽取的字符串,比如{@link com.feilong.core.Alphabet#DECIMAL_AND_LETTERS}
     * @param length
     *            指定字符串长度,比如 5
     * @return 得到随机字符串;<br>
     *         如果 <code>str</code> 是null 或者 empty,抛出 {@link java.lang.NullPointerException NullPointerException};<br>
     *         如果 <code>str</code> 是isBlank,抛出 {@link java.lang.IllegalArgumentException IllegalArgumentException};<br>
     *         如果 <code>length</code> {@code <=0}, 抛出 {@link java.lang.IllegalArgumentException IllegalArgumentException}
     * 
     * @see org.apache.commons.lang3.RandomStringUtils#random(int, String)
     */
    public static String createRandomFromString(String str,int length){
        Validate.notBlank(str, "str can't be null/empty!");
        Validate.isTrue(length > 0, Slf4jUtil.format("length:[{}] can not <=0", length));
        return RandomStringUtils.random(length, str);
    }
}