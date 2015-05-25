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

import java.awt.Color;

/**
 * 颜色 {@link java.awt.Color}工具类.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-4-15 上午01:09:40
 * @see java.awt.Color
 * @since 1.0.0
 */
public final class ColorUtil{

    /** Don't let anyone instantiate this class. */
    private ColorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 通过16进制颜色字符串 获得颜色.
     * 
     * @param hex
     *            16进制颜色字符串 ,比如:FF00FF
     * @return the color
     */
    public static Color getColor(String hex){
        return Color.decode("0x" + hex);
    }
}
