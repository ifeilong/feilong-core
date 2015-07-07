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

/**
 * 数学 {@link java.lang.Math}工具类.
 * 
 * @author feilong
 * @version 1.0 Oct 11, 2013 12:30:10 AM
 * @since 1.0.0
 * @see java.lang.Math
 */
public final class MathUtil{

    /** Don't let anyone instantiate this class. */
    private MathUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 弧度转成度 .
     * 
     * @param radians
     *            the radians
     * @return the double
     */
    public static double radian2Degree(double radians){
        double degree = radians * 180 / Math.PI;
        return degree;
    }

    /**
     * 度转成弧度.
     * 
     * @param degree
     *            the degree
     * @return the double
     */
    public static double degree2Radian(double degree){
        double radians = degree * Math.PI / 180;
        return radians;
    }
}
