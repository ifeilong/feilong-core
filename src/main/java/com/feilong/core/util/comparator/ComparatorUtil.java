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
package com.feilong.core.util.comparator;

import java.util.List;

import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.collections4.comparators.FixedOrderComparator.UnknownObjectBehavior;
import org.apache.commons.lang3.Validate;

/**
 * The Class ComparatorUtil.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.collections4.ComparatorUtils
 * @see FixedOrderComparator
 * @since 1.14.3
 */
public final class ComparatorUtil{

    /** Don't let anyone instantiate this class. */
    private ComparatorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * Builds the.
     * 
     * <p>
     * 默认使用的是 {@link UnknownObjectBehavior#AFTER} ,不在指定固定顺序的元素将排在后面
     * </p>
     *
     * @param <T>
     *            the value type
     * @param list
     *            the property values
     * @return 如果 <code>list</code> 是null,抛出 {@link NullPointerException}<br>
     */
    public static <T> FixedOrderComparator<T> buildFixedOrderComparator(List<T> list){
        return buildFixedOrderComparator(list, UnknownObjectBehavior.AFTER);
    }

    /**
     * Builds the.
     *
     * @param <T>
     *            the value type
     * @param list
     *            the property values
     * @param unknownObjectBehavior
     *            the unknown object behavior
     * @return 如果 <code>list</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>unknownObjectBehavior</code> 是null,抛出 {@link NullPointerException}<br>
     */
    public static <T> FixedOrderComparator<T> buildFixedOrderComparator(List<T> list,UnknownObjectBehavior unknownObjectBehavior){
        Validate.notNull(list, "propertyValues can't be null!");
        Validate.notNull(unknownObjectBehavior, "unknownObjectBehavior can't be null!");

        //---------------------------------------------------------------

        FixedOrderComparator<T> fixedOrderComparator = new FixedOrderComparator<>(list);
        fixedOrderComparator.setUnknownObjectBehavior(unknownObjectBehavior);
        return fixedOrderComparator;
    }
}
