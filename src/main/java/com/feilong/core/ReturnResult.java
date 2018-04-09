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

import java.io.Serializable;

/**
 * 返回结果.
 * 
 * <p>
 * 规范新的名称,用来替代 BackWarnEntity
 * </p>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            the generic type
 * @since 1.11.0
 */
public class ReturnResult<T> implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6109004143192632423L;
    //---------------------------------------------------------------

    /** 操作是否成功,默认成功. */
    private boolean           isSuccess        = true;

    //---------------------------------------------------------------

    /**
     * 返回状态码（可选值）.
     * 
     * <p>
     * 通常操作失败了 isSuccess为false 的时候,会设置值
     * </p>
     */
    private String            statusCode;

    /**
     * 对应返回的数据对象（可选值）.
     * 
     * <p>
     * 比如,成功了可以放一个bean返回回去以便调用,失败了也可以放一段字符串 等等.
     * </p>
     */
    private T                 returnData;

    //---------------------------------------------------------------

    /**
     * Instantiates a new return result.
     */
    public ReturnResult(){
        super();
    }

    /**
     * Instantiates a new return result.
     *
     * @param isSuccess
     *            the is success
     * @param statusCode
     *            the status code
     */
    public ReturnResult(boolean isSuccess, String statusCode){
        super();
        this.isSuccess = isSuccess;
        this.statusCode = statusCode;
    }

    /**
     * Instantiates a new return result.
     *
     * @param isSuccess
     *            the is success
     * @param returnData
     *            the return object
     */
    public ReturnResult(boolean isSuccess, T returnData){
        super();
        this.isSuccess = isSuccess;
        this.returnData = returnData;
    }

    /**
     * Instantiates a new return result.
     *
     * @param isSuccess
     *            the is success
     * @param statusCode
     *            返回状态码（可选值）.
     * 
     *            <p>
     *            通常操作失败了 isSuccess为false 的时候,会设置值
     *            </p>
     * @param returnData
     *            对应返回的数据对象（可选值）.
     * 
     *            <p>
     *            比如,成功了可以放一个bean返回回去以便调用,失败了也可以放一段字符串 等等.
     *            </p>
     */
    public ReturnResult(boolean isSuccess, String statusCode, T returnData){
        super();
        this.isSuccess = isSuccess;
        this.statusCode = statusCode;
        this.returnData = returnData;
    }

    //---------------------------------------------------------------

    /**
     * 返回状态码（可选值）.
     * 
     * <p>
     * 通常操作失败了 isSuccess为false 的时候,会设置值
     * </p>
     *
     * @return the 返回状态码（可选值）
     */
    public String getStatusCode(){
        return statusCode;
    }

    /**
     * 返回状态码（可选值）.
     * 
     * <p>
     * 通常操作失败了 isSuccess为false 的时候,会设置值
     * </p>
     *
     * @param statusCode
     *            the new 返回状态码（可选值）
     */
    public void setStatusCode(String statusCode){
        this.statusCode = statusCode;
    }

    /**
     * 对应返回的数据对象（可选值）.
     * 
     * <p>
     * 比如,成功了可以放一个bean返回回去以便调用,失败了也可以放一段字符串 等等.
     * </p>
     *
     * @return the 对应返回的数据对象（可选值）
     */
    public T getReturnData(){
        return returnData;
    }

    /**
     * 对应返回的数据对象（可选值）.
     * 
     * <p>
     * 比如,成功了可以放一个bean返回回去以便调用,失败了也可以放一段字符串 等等.
     * </p>
     *
     * @param returnData
     *            the new 对应返回的数据对象（可选值）
     */
    public void setReturnData(T returnData){
        this.returnData = returnData;
    }

    /**
     * 操作是否成功,默认成功.
     *
     * @return the isSuccess
     */
    public boolean getIsSuccess(){
        return isSuccess;
    }

    /**
     * 操作是否成功,默认成功.
     *
     * @param isSuccess
     *            the isSuccess to set
     */
    public void setIsSuccess(boolean isSuccess){
        this.isSuccess = isSuccess;
    }
}
