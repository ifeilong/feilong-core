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
package com.feilong.core.io;

/**
 * io常用常量.
 * 
 * @author feilong
 * @version 1.0 2010-5-21 下午04:31:08
 * @version 1.1 Apr 24, 2014 1:56:38 AM
 * @since 1.0.0
 */
public final class IOConstants{

    /** KB 1024. */
    public static final Integer KB                    = 1024;

    /** MB 1024 * 1024 1048576. */
    public static final Integer MB                    = 1024 * KB;

    /**
     * GB 1024 * 1024 * 1024 1073741824.<br>
     * <b>注意,{@link Integer#MAX_VALUE}=2147483647 是2G大小</b>
     * */
    public static final Integer GB                    = 1024 * MB;

    /** 默认缓冲大小 10k <code>{@value}</code> */
    public static final Integer DEFAULT_BUFFER_LENGTH = IOConstants.KB * 10;

    /** Don't let anyone instantiate this class. */
    private IOConstants(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
