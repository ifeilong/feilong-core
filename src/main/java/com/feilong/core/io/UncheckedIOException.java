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

import java.io.IOException;

/**
 * Wraps an {@link IOException} with an unchecked exception.
 * 注:jdk1.8 自带了 UncheckedIOException
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.9 2014年12月5日 下午3:19:59
 * @since 1.0.9
 * @since jdk1.7
 */
public class UncheckedIOException extends RuntimeException{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8134305061645241065L;

    /**
     * Constructs an instance of this class.
     *
     * @param message
     *            the detail message, can be null
     * @param cause
     *            the {@code IOException}
     */
    public UncheckedIOException(String message, IOException cause){
        super(message, cause);
    }

    /**
     * Constructs an instance of this class.
     *
     * @param cause
     *            the {@code IOException}
     */
    public UncheckedIOException(IOException cause){
        super(cause);
    }

    /**
     * Returns the cause of this exception.
     *
     * @return the {@code IOException} which is the cause of this exception.
     */
    @Override
    public IOException getCause(){
        return (IOException) super.getCause();
    }
}
