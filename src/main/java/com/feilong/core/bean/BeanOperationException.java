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

import com.feilong.core.DefaultRuntimeException;

//Exception又分为两类:一种是CheckedException,一种是UncheckedException.
//
//这两种Exception的区别主要是CheckedException需要用try...catch...显示的捕获,
//而UncheckedException不需要捕获. 通常UncheckedException又叫做RuntimeException.
//  
//《effective java》指出:
//  对于可恢复的条件使用被检查的异常(CheckedException),
//  对于程序错误(言外之意不可恢复,大错已经酿成)使用运行时异常(RuntimeException).

/**
 * 使用<code>org.apache.commons.beanutils</code>包下的类出现的异常.
 * 
 * <h3>说明:</h3>
 * <blockquote>
 * <ol>
 * <li>this is a runtime (unchecked) exception. <br>
 * Beans exceptions are usually fatal; <br>
 * there is no reason for them to be checked.</li>
 * </ol>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.springframework.beans.BeansException"
 * @since 1.9.0
 */
public final class BeanOperationException extends DefaultRuntimeException{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1699987643831455524L;

    /**
     * Instantiates a new bean util exception.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public BeanOperationException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Instantiates a new bean util exception.
     * 
     * @param cause
     *            the cause
     */
    public BeanOperationException(Throwable cause){
        super(cause);
    }
}
