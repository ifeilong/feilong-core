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

import java.util.Iterator;

/**
 * {@link Iterator} 工具类.
 * 
 * <h3>迭代器{@link Iterator}与枚举有两点不同:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>迭代器{@link Iterator}允许调用者利用定义良好的语义在迭代期间从迭代器所指向的 collection 移除元素。</li>
 * <li>方法名称得到了改进。</li>
 * </ol>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.collections4.IteratorUtils
 * @since 1.5.3
 */
public final class IteratorUtil{

    /** Don't let anyone instantiate this class. */
    private IteratorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

}
