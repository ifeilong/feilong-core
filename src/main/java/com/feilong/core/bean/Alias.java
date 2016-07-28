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
package com.feilong.core.bean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标识 bean 字段和 map的key一对一的关系,简化减少hard coding.
 * 
 * <p>
 * 很多时候,map 不能直接 {@link BeanUtil#populate(Object, java.util.Map)}
 * 到bean里面去,需要配置key和propertyName的对应关系,此时可以在bean里面使用{@link Alias}来配置对应关系,然后使用 {@link BeanUtil#populateAliasBean(Object, java.util.Map)}
 * </p>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Inherited
public @interface Alias{

    /**
     * 对应map里面的key(即别名).
     * 
     * @return the string
     */
    String name();

    /**
     * 示例结果(仅供查看的时候,知道这个字段的结果值格式和大致的值,没有其他作用).
     * 
     * @return the string
     */
    String sampleValue() default "";
}
