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
package com.feilong.core.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * NullObject 专门为缓存设计.
 * <p>
 * 缓存的核心数据结构是Map,如果存放时候,value是 null,取的时候,没有办法通过{@link Map#get(Object)}区分{@link Map}中是否含有指定的key<br>
 * 如果先使用 {@link Map#containsKey(Object)}来判断的话,{@link Map#containsKey(Object)}内部原理也是for循环来判断,如果Map key比较多,对性能也会有影响<br>
 * </p>
 * 
 * <p>
 * 因此构建的时候,如果是 value是 null,那么我们将 {@link NullObject} 设置到 {@link Map} 中, 当get的时候,如果是 {@link NullObject},我们转成null返回<br>
 * 
 * 这样一来,我们只需要通过{@link Map#get(Object)} 判断获得的value是null 还是 {@link NullObject} 就可以区分是否包含指定的key,是否设置过数据,对大容量的Map性能会有所提高.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014-5-24 22:52:37
 * @since 1.0.7
 */
public class NullObject implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7374527169034342458L;

}