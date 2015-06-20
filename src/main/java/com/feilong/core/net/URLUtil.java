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
package com.feilong.core.net;

import java.net.URL;

import com.feilong.core.util.Validator;

/**
 * The Class URLUtil.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.2.1 2015年6月21日 上午12:54:15
 * @since 1.2.1
 */
public class URLUtil{

    /**
     * To string array.
     *
     * @param urls
     *            the urls
     * @return the string[]
     * @throws NullPointerException
     *             if isNullOrEmpty(urls)
     * @since 1.2.1
     */
    public static String[] toStringArray(URL[] urls) throws NullPointerException{
        if (Validator.isNullOrEmpty(urls)){
            throw new NullPointerException("urls can't be null/empty!");
        }

        String[] stringArray = new String[urls.length];

        int i = 0;
        for (URL url : urls){
            stringArray[i] = url.toString();
            i++;
        }
        return stringArray;
    }
}
