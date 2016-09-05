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

import java.net.URI;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.URIParseException;
import com.feilong.core.net.URIUtil;

/**
 * The Class URIUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class URIUtilCreateTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URIUtilCreateTest.class);
    //***************com.feilong.core.net.URIUtil.create(String)************************************************************************

    /**
     * Test create.
     */
    @Test
    public void testCreate(){
        String value = "http://xy2.cbg.163.com/cgi-bin/equipquery.py?server_name=风花雪月&query_order=selling_time DESC&search_page&areaid=2&server_id=63&act=search_browse&equip_type_ids&search_text=斩妖剑";
        value = "http://xy2.cbg.163.com/cgi-bin/equipquery.py?server_name=风花雪月&query_order=selling_time";
        URI uri = URIUtil.create(value);
        LOGGER.debug("{}", uri.toString());
    }

    /**
     * Test create3.
     */
    @Test
    @Ignore
    public void testCreate3(){
        String uriString = "http://127.0.0.1/cmens?a=%";
        LOGGER.debug("" + URI.create(uriString));
    }

    @Test(expected = URIParseException.class)
    public void testCreateErrorUri(){
        URIUtil.create("://127.0.01/cmens");
    }

    //****************************************************************************************
    @Test(expected = NullPointerException.class)
    public void testCreateNull(){
        URIUtil.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateEmpty(){
        URIUtil.create("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBlank(){
        URIUtil.create(" ");
    }
}
