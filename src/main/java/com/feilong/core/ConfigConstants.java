/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core;

import com.feilong.core.configure.ResourceBundleUtil;

/**
 * 配置的常量.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.8 2014年11月30日 下午6:00:36
 * @since 1.0.8
 */
public final class ConfigConstants{

    /** The Constant $FEILONG_CORE_CONFIG. */
    private static final String $FEILONG_CORE_CONFIG                 = "config/feilong-core-config";

    /** 数字和小写的字母. */
    public static final String  VALIDATECODE_NUMBERSANDLITTLELETTERS = ResourceBundleUtil.getValue(
                                                                                     $FEILONG_CORE_CONFIG,
                                                                                     "validateCode_numbersAndLittleLetters");

    /** 数字和大小写字母. */
    public static final String  NUMBERSANDALLLETTERS                 = ResourceBundleUtil.getValue(
                                                                                     $FEILONG_CORE_CONFIG,
                                                                                     "numbersAndAllLetters");

    /** 所有的数字. */
    public static final String  NUMBERS                              = ResourceBundleUtil.getValue($FEILONG_CORE_CONFIG, "numbers");

    /** Don't let anyone instantiate this class. */
    private ConfigConstants(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
