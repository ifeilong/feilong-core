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
package com.feilong.core.text;

import java.math.RoundingMode;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.NumberPattern;

/**
 * {@link NumberFormat}是所有数值格式的抽象基类,此类提供格式化和解析数值的接口.
 * 
 * <p>
 * 直接已知子类： {@link ChoiceFormat}, {@link DecimalFormat}.<br>
 * 注意:<span style="color:red">{@link DecimalFormat}不是同步的 </span>,建议为每个线程创建独立的格式实例. (见JAVA API 文档)
 * </p>
 * 
 * @author feilong
 * @version 1.0.2 2012-3-27 上午1:39:53
 * @see Format
 * @see NumberFormat
 * @see DecimalFormat
 * @see NumberPattern
 * @since 1.0.2
 */
public final class NumberFormatUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberFormatUtil.class);

    /** Don't let anyone instantiate this class. */
    private NumberFormatUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将 {@link Number} 使用 numberPattern格式化.
     * 
     * <p>
     * 该方法使用 {@link java.math.RoundingMode#HALF_UP}
     * </p>
     *
     * @param value
     *            the value
     * @param numberPattern
     *            the pattern {@link NumberPattern}
     * @return 如果有异常 将返回null
     * @see NumberPattern
     * @see DecimalFormat
     * @see RoundingMode#HALF_UP
     */
    public static String format(Number value,String numberPattern){
        // 如果不设置, DecimalFormat默认使用的是 RoundingMode.HALF_EVEN
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        return format(value, numberPattern, roundingMode);
    }

    /**
     * 将 {@link Number} 使用 {@link RoundingMode} numberPattern格式化.
     *
     * @param value
     *            the value
     * @param numberPattern
     *            the pattern {@link NumberPattern}
     * @param roundingMode
     *            四舍五入的方法{@link RoundingMode}
     * @return 如果有异常 将返回null
     * @see NumberPattern
     * @see DecimalFormat
     * @see <a href="../util/NumberUtil.html#RoundingMode">JAVA 8种舍入法</a>
     */
    public static String format(Number value,String numberPattern,RoundingMode roundingMode){
        if (null == value){
            throw new NullPointerException("the value is null or empty!");
        }

        if (null == numberPattern){
            throw new NullPointerException("the numberPattern is null or empty!");
        }

        try{
            //改构造方法内部 调用了applyPattern(pattern, false)
            DecimalFormat decimalFormat = new DecimalFormat(numberPattern);

            // 如果不设置默认使用的是 RoundingMode.HALF_EVEN
            // 精确舍入，银行家舍入法.四舍六入，五分两种情况.如果前一位为奇数，则入位，否则舍去.以下例子为保留小数点1位，那么这种舍入方式下的结果.
            // 1.15>1.2    1.25>1.2 
            if (null != roundingMode){
                decimalFormat.setRoundingMode(roundingMode);
            }

            // decimalFormat.applyPattern("##,###.000");
            String format = decimalFormat.format(value);

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug(
                                "value:[{}], pattern:[{}],return:[{}],decimalFormat.toLocalizedPattern():[{}]",
                                value,
                                numberPattern,
                                format,
                                decimalFormat.toLocalizedPattern()//合成一个表示此 Format 对象当前状态的、已本地化的模式字符串. 
                );
            }
            return format;
        }catch (Exception e){
            Object[] objects = { e.getMessage(), value, numberPattern };
            LOGGER.error("{},value:[{}],pattern:[{}]", objects);
            LOGGER.error(e.getClass().getName(), e);
        }
        return StringUtils.EMPTY;
    }
}
