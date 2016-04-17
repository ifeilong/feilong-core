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

import com.feilong.core.Validator;

/**
 * {@link Iterator} 工具类.
 *
 * @author feilong
 * @version 1.5.3 2016年4月18日 上午2:50:15
 * @since 1.5.3
 * @see org.apache.commons.collections4.IteratorUtils
 */
public final class IteratorUtil{

    /** Don't let anyone instantiate this class. */
    private IteratorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * iterator是否包含某个值.
     * 
     * <p style="color:red">
     * 注意,比较的是 {@link java.lang.Object#toString()},常常用于自定义标签或者el function
     * </p>
     * 
     * @param iterator
     *            iterator
     * @param value
     *            value
     * @return iterator是否包含某个值,如果iterator为null/empty,则返回false
     * @see Iterator#hasNext()
     * @see Iterator#next()
     * @see "org.springframework.util.CollectionUtils#contains(Iterator, Object)"
     * @see org.apache.commons.collections4.IteratorUtils#contains(Iterator, Object)
     * @deprecated 由于该方法内部实现比较特殊,将可能会移到tag里面去,
     */
    @Deprecated
    public static boolean contains(Iterator<?> iterator,Object value){
        if (Validator.isNullOrEmpty(iterator)){
            return false;
        }
        while (iterator.hasNext()){
            Object object = iterator.next();
            if (object.toString().equals(value.toString())){
                return true;
            }
        }
        return false;
    }
}
