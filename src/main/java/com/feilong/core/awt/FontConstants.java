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
package com.feilong.core.awt;

/**
 * 常用字体的枚举.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-12-31 下午05:02:06
 * @version 1.0.5 2014-5-4 00:24 改成interface
 * @since 1.0.0
 */
public final class FontConstants{

    /** <code>{@value}</code>. */
    public static final String KAITI       = "楷体_gb2312";

    /** <code>{@value}</code>. */
    public static final String SONGTI      = "宋体";

    /** <code>{@value}</code>. */
    public static final String YAHEI       = "微软雅黑";

    /** <code>{@value}</code>. */
    public static final String VERDANA     = "Verdana";

    /** <code>{@value}</code>. */
    public static final String COURIER_NEW = "Courier New";

    /** Don't let anyone instantiate this class. */
    private FontConstants(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}