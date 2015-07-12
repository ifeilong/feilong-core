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
package com.feilong.core.lang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface MyAnnotation.
 * 
 * @author feilong
 * @version 1.0.7 2014-5-30 0:25:36
 */
// 这个Annotation可以被写入javadoc
@Documented
// 这个Annotation 可以被继承
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation{

    /** The name_english. */
    String name_english = "venusdrogon";

    /**
     * name属性.
     * 
     * @return the string
     */
    String name() default "关羽";

    /**
     * 性别 1=男.
     * 
     * @return the int
     */
    int sex() default 1;

    /**
     * Love strings.
     * 
     * @return the string[]
     */
    String[] loveStrings();
}
