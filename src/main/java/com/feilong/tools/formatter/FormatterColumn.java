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
package com.feilong.tools.formatter;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is used to specify the mapped column for a persistent property or field.
 * If no <code>Column</code> annotation is specified, the default values apply.
 *
 * <blockquote>
 * 
 * <pre>
 *    Example 1:
 *
 *    {@code @}FormatterColumn(name="DESC",order=1)
 *    public String getDescription() { return description; }
 *
 * </pre>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
//表示产生文档,比如通过javadoc产生文档, 将此注解包含在 javadoc 中, 这个Annotation可以被写入javadoc
//在默认情况下,注释 不包括在 Javadoc 中
@Documented
//在jvm加载class时候有效, VM将在运行期也保留注释,因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface FormatterColumn{

    /**
     * (Optional) The name of the column. Defaults to the property or field name.
     */
    String name() default "";

    /**
     * (Optional) 排序 数值从小到大排序.
     */
    int order() default 0;
}
