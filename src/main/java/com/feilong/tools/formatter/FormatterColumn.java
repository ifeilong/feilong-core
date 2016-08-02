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
 * 
 * <p>
 * 可以使用这个注解来修改显示每列的顺序以及标题的名字
 * </p>
 * 
 * <h3>使用示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre>
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
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface FormatterColumn{

    /**
     * (Optional) The name of the column. Defaults to the property or field name.
     *
     * @return the string
     */
    String name() default "";

    /**
     * (Optional) 排序,数值从小到大排序.
     *
     * @return the int
     */
    int order() default 0;
}
