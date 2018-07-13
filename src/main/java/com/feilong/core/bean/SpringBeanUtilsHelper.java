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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtil;

/**
 * 用来判断 当前环境是否有 spring bean BeanUtils 类.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.12.0
 */
class SpringBeanUtilsHelper{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBeanUtilsHelper.class);

    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private SpringBeanUtilsHelper(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /** The spring bean utils class. */
    private static Class<?> springBeanUtilsClass = null;

    //---------------------------------------------------------------

    static{
        String className = "org.springframework.beans.BeanUtils";
        try{
            springBeanUtilsClass = ClassUtil.getClass(className);
            LOGGER.info("find and load:[{}]", className);
        }catch (Exception e){
            //just want to use e.toString
            LOGGER.warn("can't load:[{}],[{}],if you import spring, getPropertyValue will speed fast", className, e.toString());
        }
    }

    //---------------------------------------------------------------

    /**
     * 判断环境中是否有Spring BeanUtils.
     *
     * @return 如果 SPRING_BEAN_UTILS_CLASS 不是null ,表示有, 返回true; 否则返回false
     */
    static boolean hasSpringBeanUtilsClass(){
        return springBeanUtilsClass != null;
    }

    /**
     * 返回 Spring BeanUtils.
     *
     * @return the spring bean utils class
     */
    static Class<?> getSpringBeanUtilsClass(){
        return springBeanUtilsClass;
    }
}
