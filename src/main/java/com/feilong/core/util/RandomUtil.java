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

import org.apache.commons.lang3.Validate;

import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 随机数工具类.
 * 
 * <ul>
 * <li>{@link java.lang.Math#random()} 底层也是调用的 new Random(),值＝Random nextDouble()</li>
 * <li>把 {@link java.util.Random} 对象作为一个全局实例(static)来使用.Java中 {@link java.util.Random} 是线程安全的(内部进行了加锁处理)；</li>
 * <li>伪随机数</li>
 * <li>生成随机数的算法有很多种,最简单也是最常用的就是 "线性同余法"：  第n+1个数=(第n个数*29+37) % 1000,其中%是"求余数"运算符.</li>
 * </ul>
 * 
 * @author feilong
 * @version 1.0.0 2010-4-5 下午10:55:19
 * @version 1.0.7 2014年5月19日 下午6:45:01
 * @since 1.0.0
 * @see org.apache.commons.lang3.RandomStringUtils
 * @see org.apache.commons.lang3.RandomUtils
 * @see java.lang.Math#random()
 */
public final class RandomUtil{

    /**
     * Random object used by random method.
     * 
     * <p>
     * This has to be not local to the random method so as to not return the same value in the same millisecond.<br>
     * 把Random对象作为一个全局实例(static)来使用. Java中Random是线程安全的(内部进行了加锁处理)；
     * </p>
     * 
     * @see org.apache.commons.lang.math.RandomUtils
     * @since 1.0.7
     */
    public static final Random JVM_RANDOM = new Random();

    /** Don't let anyone instantiate this class. */
    private RandomUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 创建0-最大值之间的随机数.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre>
     * RandomUtil.createRandom(8)
     * 创建一个小于8的随机数
     * 
     * 生成的结果是可能是 3
     * </pre>
     * 
     * </blockquote>
     * 
     * @param number
     *            随机数最大值
     * @return 创建0-最大值之间的随机数
     */
    public static long createRandom(Number number){
        double random = JVM_RANDOM.nextDouble();
        return (long) Math.floor(random * number.longValue());
    }

    /**
     * 创建最小值 {@code min} 和最大值{@code max}之间的随机数.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre>
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
     * @return 创建最小值和最大值之间的随机数
     */
    public static long createRandom(Number minValue,Number maxValue){
        Validate.notNull(minValue, "min can't be null!");
        Validate.notNull(maxValue, "max can't be null!");

        long maxLong = maxValue.longValue();
        long minLong = minValue.longValue();

        Validate.isTrue(maxLong >= minLong, Slf4jUtil.formatMessage("maxLong:[{}] can not < minLong:[{}]", maxLong, minLong));

        long cha = maxLong - minLong;
        return minLong + createRandom(cha);
    }

    // ********************************************************************

    /**
     * 生成一个指定长度大小 {@code length} 的随机正整数.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre>
     * RandomUtil.createRandomWithLength(2)
     * 生成的结果是可能是 89
     * </pre>
     * 
     * </blockquote>
     *
     * @param length
     *            设定所取出随机数的长度.
     * @return 返回生成的随机数
     */
    public static long createRandomWithLength(int length){
        // 该值大于等于 0.0 且小于 1.0 正号的 double 值
        double random = JVM_RANDOM.nextDouble();
        if (random < 0.1){// 可能出现 0.09346924349151808
            random = random + 0.1;
        }

        // ****************************************
        long num = 1;
        for (int i = 0; i < length; ++i){
            num = num * 10;
        }
        return (long) (random * num);
    }

    // ****************************************************************

    /**
     * 随机抽取字符串char,拼接最小长度是 {@code minLength},最大长度是{@code maxLength}的字符串随机字符串.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre>
     * RandomUtil.createRandomFromString("0123456789", 8, 20)
     * 从0123456789随机抽取字符,组成最小长度是8,最大长度是20的字符串
     * 
     * 生成的结果是可能是 142853574998970631
     * </pre>
     * 
     * </blockquote>
     *
     * @param str
     *            被抽取的字符串,比如示例中的 0123456789
     * @param minLength
     *            最小长度 ,比如示例中的 8
     * @param maxLength
     *            最大长度,比如示例中的 20
     * 
     * @return 随机抽取字符串char,拼接最小长度是 {@code minLength},最大长度是{@code maxLength}的字符串随机字符串. <br>
     *         如上述示例,可能返回142853574998970631<br>
     *         if str empty,will {@link java.lang.NullPointerException NullPointerException};<br>
     *         if maxLength<=0 will {@link java.lang.IllegalArgumentException IllegalArgumentException}<br>
     *         if maxLength<minLength will {@link java.lang.IllegalArgumentException IllegalArgumentException}
     */
    public static String createRandomFromString(String str,int minLength,int maxLength){
        Validate.notEmpty(str, "str can't be null/empty!");

        Validate.isTrue(maxLength > 0, Slf4jUtil.formatMessage("maxLength:[{}] can not zero", maxLength));
        Validate.isTrue(maxLength >= minLength, Slf4jUtil.formatMessage("maxLength:[{}] can not < minLength:[{}]", maxLength, minLength));

        long length = createRandom(minLength, maxLength);
        return createRandomFromString(str, (int) length);
    }

    /**
     * 随机抽取字符串char,拼接成指定长度的字符串.
     * 
     * <h3>Example 1:</h3>
     * <blockquote>
     * 
     * <pre>
     * RandomUtil.createRandomFromString("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy0123456789", 5)
     * 
     * 从ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy0123456789 随机抽取字符,组成长度是5 的字符串
     * 生成的结果是可能是 IFSMB
     * </pre>
     * 
     * </blockquote>
     * 
     * @param str
     *            被抽取的字符串,比如 ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy0123456789
     * @param length
     *            指定字符串长度, 比如 5
     * @return 得到随机字符串, 如上面给到的参数,可能是 IFSMB <br>
     *         if str empty,will {@link java.lang.NullPointerException NullPointerException};<br>
     *         if length<=0 will {@link java.lang.IllegalArgumentException IllegalArgumentException}
     */
    public static String createRandomFromString(String str,int length){
        Validate.notEmpty(str, "str can't be null/empty!");
        Validate.isTrue(length > 0, Slf4jUtil.formatMessage("length:[{}] can not <=0", length));

        char[] ch = new char[length];
        int j = str.length();
        for (int i = 0; i < length; ++i){
            int index = JVM_RANDOM.nextInt(j);// 随机取个字符
            ch[i] = str.charAt(index);
        }
        return new String(ch);
    }
}