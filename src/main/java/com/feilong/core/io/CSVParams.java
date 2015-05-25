/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.io;

/**
 * CSV参数.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-2-22 下午5:45:23
 * @since 1.0.0
 */
public class CSVParams{

    /** 编码. */
    private String encode    = CharsetType.GBK;

    /** filed 分隔符 默认,逗号. */
    private char   separator = ',';

    /**
     * Gets the 编码.
     * 
     * @return the encode
     */
    public String getEncode(){
        return encode;
    }

    /**
     * Sets the 编码.
     * 
     * @param encode
     *            the encode to set
     */
    public void setEncode(String encode){
        this.encode = encode;
    }

    /**
     * Gets the filed 分隔符 默认,逗号.
     * 
     * @return the separator
     */
    public char getSeparator(){
        return separator;
    }

    /**
     * Sets the filed 分隔符 默认,逗号.
     * 
     * @param separator
     *            the separator to set
     */
    public void setSeparator(char separator){
        this.separator = separator;
    }
}