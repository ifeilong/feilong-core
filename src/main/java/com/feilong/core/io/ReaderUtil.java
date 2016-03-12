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
package com.feilong.core.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

/**
 * {@link java.io.Reader} 工具类.
 *
 * @author feilong
 * @version 1.0.9 2015年3月6日 上午10:51:54
 * @see java.io.BufferedReader
 * @see java.io.CharArrayReader
 * @see java.io.FilterReader
 * @see java.io.InputStreamReader
 * @see java.io.PipedReader
 * @see java.io.StringReader
 * @see java.io.LineNumberReader
 * @since 1.0.9
 */
public final class ReaderUtil{

    /** Don't let anyone instantiate this class. */
    private ReaderUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将 {@link java.io.Reader} 装成 {@link java.lang.String}.
     *
     * @param reader
     *            the reader
     * @return the string
     * @see org.apache.commons.io.IOUtils#toBufferedReader(Reader)
     */
    public static String toString(Reader reader){

        BufferedReader bufferedReader = IOUtils.toBufferedReader(reader);

        try{
            StringBuilder sb = new StringBuilder();
            String line = "";

            // 读取一个文本行.通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行.
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
                sb.append(SystemUtils.LINE_SEPARATOR);
            }
            return sb.toString();
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将 {@link java.io.Reader} 装成 {@link java.lang.String}.
     *
     * @param reader
     *            the reader
     * @return the string
     * @see org.apache.commons.io.IOUtils#toBufferedReader(Reader)
     */
    public static String readLine(Reader reader){

        BufferedReader bufferedReader = IOUtils.toBufferedReader(reader);

        if (null == bufferedReader){
            throw new NullPointerException("the bufferedReader is null or empty!");
        }

        try{
            // 读取一个文本行.通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行.
            return bufferedReader.readLine();
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
}
