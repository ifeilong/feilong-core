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
import org.apache.commons.lang3.Validate;

import com.feilong.core.Alphabet;

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
 * @see java.util.concurrent.ThreadLocalRandom
 * @since 1.0.0
 */
public final class RandomUtil{

    /**
     * Random object used by random method.
     * 
     * <p>
     * 为了确保同一毫秒不能返回相同的值,不同声明在方法里面.<br>
     * 把Random对象作为一个全局实例(static)来使用. Java中Random是线程安全的(内部进行了加锁处理);
     * </p>
     * 
     * @see org.apache.commons.lang3.RandomUtils
     * @since 1.0.7
     */
    private static final Random JVM_RANDOM = new Random();

    /** Don't let anyone instantiate this class. */
    private RandomUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 生成一个指定长度<code>length</code>的 <b>随机正整数</b>.
     * 
     * <h3>示例:</h3>
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
     * @return 如果 <code>length</code> {@code <=0} ,抛出 {@link IllegalArgumentException}
     */
    public static long createRandomWithLength(int length){
        Validate.isTrue(length > 0, "input param [length] must >0,but is [%s]", length);
        long num = 1;
        for (int i = 0; i < length; ++i){
            num = num * 10;
        }

        // 该值大于等于 0.0 且小于 1.0 正号的 double 值
        double random = JVM_RANDOM.nextDouble();
        random = random < 0.1 ? random + 0.1 : random;// 可能出现 0.09346924349151808
        return (long) (random * num);
    }

    //---------------------------------------------------------------

    /**
     * 随机抽取字符串<code>char</code>,拼接成指定长度<code>length</code>的字符串.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>常用于生成验证码</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
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
     * @return 如果 <code>str</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>str</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>length</code> {@code <=0}, 抛出 {@link IllegalArgumentException}
     * 
     * @see org.apache.commons.lang3.RandomStringUtils#random(int, String)
     */
    public static String createRandomFromString(String str,int length){
        Validate.notBlank(str, "str can't be null/empty!");
        Validate.isTrue(length > 0, "input param [length] must >0,but is [%s]", length);
        return RandomStringUtils.random(length, str);
    }

    /**
     * 从{@link com.feilong.core.Alphabet#DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE
     * Alphabet.DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE} 随机抽取字符串,拼接成指定长度<code>length</code>的字符串.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>常用于生成验证码</li>
     * <li>接口传参,比如微信需要 nonce_str ,不长于32位的随机字符串</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * RandomUtil.createRandomString(5)
     * 从 {@link com.feilong.core.Alphabet#DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE  Alphabet.DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE} 随机抽取字符,组成长度是5的字符串
     * 生成的结果是可能是IFSMB
     * </pre>
     * 
     * </blockquote>
     * 
     * @param length
     *            指定字符串长度,比如 5
     * @return 如果 <code>length</code> {@code <=0}, 抛出 {@link IllegalArgumentException}
     * 
     * @see org.apache.commons.lang3.RandomStringUtils#random(int, String)
     * @since 2.1.0
     */
    public static String createRandomString(int length){
        Validate.isTrue(length > 0, "input param [length] must >0,but is [%s]", length);
        return RandomStringUtils.random(length, Alphabet.DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE);
    }

}