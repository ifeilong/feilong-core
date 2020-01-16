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
 * 主要作用,是在异常message 中追加 cause exception信息,方便查看排查问题
 * </p>
 * 
 * <h3>示例1:</h3>
 * 
 * <blockquote>
 * 
 * 如下代码
 * 
 * 
 * <pre class="code">
 * 
 * public void testRuntimeException(){
 *     try{
 *         int i = 1 / 0;
 *     }catch (Exception e){
 *         throw new RuntimeException("", e);
 *     }
 * }
 * 
 * </pre>
 * 
 * <b>抛出的异常情况在控制台是这样的:</b>
 * 
 * <pre class="code">

java.lang.RuntimeException: 
    at com.feilong.core.DefaultRuntimeExceptionTest.testRuntimeException(DefaultRuntimeExceptionTest.java:63)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at java.lang.reflect.Method.invoke(Method.java:606)
    at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:206)
Caused by: java.lang.ArithmeticException: / by zero
    at com.feilong.core.DefaultRuntimeExceptionTest.testRuntimeException(DefaultRuntimeExceptionTest.java:61)
    ... 23 more
 * </pre>
 * 
 * 
 * 
 * 
 * 而如果使用 DefaultRuntimeException
 * 
 * <pre class="code">
 * 
 * public void testDefaultRuntimeException(){
 *     try{
 *         int i = 1 / 0;
 *     }catch (Exception e){
 *         throw new DefaultRuntimeException("", e);
 *     }
 * }
 * 
 * </pre>
 * 
 * <b>抛出来的信息是这样的 :</b>
 * 
 * <pre class="code">
com.feilong.core.DefaultRuntimeException: <span style="color:red">java.lang.ArithmeticException: / by zero</span>
    at com.feilong.core.DefaultRuntimeExceptionTest.testDefaultRuntimeException(DefaultRuntimeExceptionTest.java:53)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
    at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
    at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)

Caused by: java.lang.ArithmeticException: / by zero
    at com.feilong.core.DefaultRuntimeExceptionTest.testDefaultRuntimeException(DefaultRuntimeExceptionTest.java:51)
    ... 23 more
 * </pre>
 * 
 * </blockquote>
 * 
 * <h3>示例2:</h3>
 * 
 * <blockquote>
 * 
 * 如下代码
 * 
 * 
 * <pre class="code">
 * 
 * public void testRuntimeExceptionMessage(){
 *     try{
 *         int i = 1 / 0;
 *     }catch (Exception e){
 *         throw new RuntimeException("exception", e);
 *     }
 * }
 * 
 * </pre>
 * 
 * <b>抛出的异常情况在控制台是这样的:</b>
 * 
 * <pre class="code">
java.lang.RuntimeException: exception
    at com.feilong.core.DefaultRuntimeExceptionTest.testRuntimeExceptionMessage(DefaultRuntimeExceptionTest.java:105)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:460)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:206)
Caused by: java.lang.ArithmeticException: / by zero
    at com.feilong.core.DefaultRuntimeExceptionTest.testRuntimeExceptionMessage(DefaultRuntimeExceptionTest.java:103)
    ... 23 more
 * </pre>
 * 
 * 
 * 
 * 
 * 而如果使用 DefaultRuntimeException
 * 
 * <pre class="code">
 * 
 * public void testDefaultRuntimeExceptionMessageAppend(){
 *     try{
 *         int i = 1 / 0;
 *     }catch (Exception e){
 *         throw new DefaultRuntimeException("exception", e);
 *     }
 * }
 * 
 * </pre>
 * 
 * <b>抛出来的信息是这样的 :</b>
 * 
 * <pre class="code">
com.feilong.core.DefaultRuntimeException: <span style="color:red">exception,cause by:[java.lang.ArithmeticException: / by zero]</span>
    at com.feilong.core.DefaultRuntimeExceptionTest.testDefaultRuntimeExceptionMessageAppend(DefaultRuntimeExceptionTest.java:95)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:460)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:206)
Caused by: java.lang.ArithmeticException: / by zero
    at com.feilong.core.DefaultRuntimeExceptionTest.testDefaultRuntimeExceptionMessageAppend(DefaultRuntimeExceptionTest.java:93)
    ... 23 more
 * </pre>
 * 
 * </blockquote>
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
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * throw new DefaultRuntimeException(
     *                 "code not 00 is[{}],gatewayResponse:[{}],chinaumsQueryResultCommand:[{}]",
     *                 code,
     *                 gatewayResponse,
     *                 JsonUtil.format(chinaumsQueryResultCommand));
     * 
     * </pre>
     * 
     * </blockquote>
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
        super(cause);
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
            return causeMessage(cause);
        }
        return message + ",cause by:[" + causeMessage(cause) + "]";
    }

    //---------------------------------------------------------------

    /**
     * Cause message.
     *
     * @param cause
     *            the cause
     * @return the string
     * @since 1.11.5
     */
    private static String causeMessage(Throwable cause){
        return cause == null ? null : cause.toString();
    }
}
