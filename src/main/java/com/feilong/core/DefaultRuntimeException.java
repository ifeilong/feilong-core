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
package com.feilong.core;

import static com.feilong.core.Validator.isNullOrEmpty;

import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 默认的 RuntimeException.
 * 
 * <p>
 * 主要作用,是在异常message 中加入了上一个错误信息,方便查看
 * </p>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.11.5
 */
public class DefaultRuntimeException extends RuntimeException{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1699987643831455524L;

    /**
     * Instantiates a new abstract runtime exception.
     *
     * @param message
     *            the message
     */
    public DefaultRuntimeException(String message){
        super(message);
    }

    /**
     * Instantiates a new abstract runtime exception.
     *
     * @param messagePattern
     *            the message pattern
     * @param args
     *            the args
     */
    public DefaultRuntimeException(String messagePattern, Object...args){
        super(Slf4jUtil.format(messagePattern, args));
    }

    //---------------------------------------------------------------

    /**
     * Instantiates a new abstract runtime exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public DefaultRuntimeException(String message, Throwable cause){
        super(buildMessage(message, cause), cause);
    }

    /**
     * Instantiates a new abstract runtime exception.
     *
     * @param cause
     *            the cause
     */
    public DefaultRuntimeException(Throwable cause){
        super(cause.getMessage(), cause);
    }

    //---------------------------------------------------------------

    /**
     * Builds the message.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @return the string
     */
    private static String buildMessage(String message,Throwable cause){
        if (isNullOrEmpty(message)){
            return cause.getMessage();
        }
        return message + ",cause message:[" + cause.getMessage() + "]";
    }
}
