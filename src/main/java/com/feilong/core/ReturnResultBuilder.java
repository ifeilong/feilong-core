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

import org.apache.commons.lang3.Validate;

/**
 * {@link ReturnResult} 一些工具类.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.11.0
 */
public final class ReturnResultBuilder{

    /** Don't let anyone instantiate this class. */
    private ReturnResultBuilder(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------------

    /**
     * 构造带statusCode 的失败结果.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 对于以下代码:
     * </p>
     * 
     * <pre class="code">
     * 
     * ReturnResult returnResult = new ReturnResult();
     * 
     * returnResult.setResult(false);
     * returnResult.setStatusCode("member.email.error");
     * return returnResult;
     * 
     * </pre>
     * 
     * <b>此时可以重构成:</b>
     * 
     * <pre class="code">
     * return ReturnResultBuilder.buildFailureResult("member.email.error");
     * </pre>
     * 
     * </blockquote>
     *
     * @param statusCode
     *            返回状态码
     * @return 如果 <code>statusCode</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>statusCode</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     */
    public static ReturnResult<Object> buildFailureResult(String statusCode){
        Validate.notBlank(statusCode, "statusCode can't be blank!");

        //---------------------------------------------------------------
        return new ReturnResult<>(false, statusCode);
    }
}
