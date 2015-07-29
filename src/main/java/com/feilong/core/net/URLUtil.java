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
package com.feilong.core.net;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.Validator;

/**
 * The Class URLUtil.
 *
 * @author feilong
 * @version 1.2.1 2015年6月21日 上午12:54:15
 * @since 1.2.1
 */
public final class URLUtil{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLUtil.class);

    /** Don't let anyone instantiate this class. */
    private URLUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * To string array.
     *
     * @param urls
     *            the urls
     * @return the string[]
     * @since 1.2.1
     */
    public static String[] toStringArray(URL[] urls){
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

    /**
     * To ur ls.
     *
     * @param paths
     *            the paths
     * @return the UR l[]
     */
    public static URL[] toURLs(String[] paths){
        if (Validator.isNullOrEmpty(paths)){
            throw new NullPointerException("paths can't be null/empty!");
        }

        int length = paths.length;

        URL[] urls = new URL[length];

        int i = 0;
        try{
            for (String path : paths){
                urls[i] = new File(path).toURI().toURL();
                i++;
            }
        }catch (MalformedURLException e){
            throw new URIParseException(e);
        }

        return urls;
    }

    /**
     * To ur ls.
     *
     * @param paths
     *            the paths
     * @return the UR l[]
     */
    public static URL[] toURLs(List<String> paths){
        if (Validator.isNullOrEmpty(paths)){
            throw new NullPointerException("paths can't be null/empty!");
        }
        String[] array = CollectionsUtil.toArray(paths, String.class);
        return toURLs(array);
    }

    /**
     * 将 {@link URL}转成 {@link URI}.
     *
     * @param url
     *            the url
     * @return the uri
     * @see "org.springframework.util.ResourceUtils#toURI(URL)"
     * @since 1.2.2
     */
    public static URI toURI(URL url){
        try{
            return url.toURI();
        }catch (URISyntaxException e){
            throw new URIParseException(e);
        }
    }

    /**
     * New url.
     *
     * @param spec
     *            the <code>String</code> to parse as a URL.
     * @return the url
     * @since 1.3.0
     */
    public static URL newURL(String spec){
        try{
            return new URL(spec);
        }catch (MalformedURLException e){
            LOGGER.error("MalformedURLException:", e);
            throw new URIParseException(e);
        }
    }
}
