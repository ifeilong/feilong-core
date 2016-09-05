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
package com.feilong.core.net.uriutiltest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.URIUtil;

import static com.feilong.core.CharsetType.UTF8;

public class URIUtilDecodeTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URIUtilDecodeTest.class);

    /**
     * Decode.
     */
    @Test
    public void decode(){
        LOGGER.debug(
                        URIUtil.decode(
                                        "%E9%87%91%E6%80%BB%EF%BC%8C%E4%BD%A0%E6%83%B3%E6%80%8E%E4%B9%88%E4%B9%88%EF%BC%8C%E5%B0%B1%E6%80%8E%E4%B9%88%E4%B9%88",
                                        UTF8));

    }

    /**
     * Decode 1.
     */
    @Test
    public void decode1(){
        String str = "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88";
        LOGGER.debug(URIUtil.decode(str, "utf-8"));
    }

    /**
     * Decode2.
     */
    @Test
    public void decode2(){
        LOGGER.debug(URIUtil.decode("aaaaa%chu111", UTF8));

    }

    /**
     * Decode3.
     */
    @Test
    public void decode3(){
        LOGGER.debug(URIUtil.decode("%c", UTF8));
    }
}
