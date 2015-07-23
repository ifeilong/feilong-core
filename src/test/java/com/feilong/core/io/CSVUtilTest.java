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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.entity.BackWarnEntity;

/**
 * The Class CSVUtilTest.
 * 
 * @author feilong
 * @version 1.0.7 2014-6-25 15:24:05
 */
public class CSVUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVUtilTest.class);

    /**
     * Test write.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testWrite() throws IOException{
        String path = "/home/webuser/nike_int/johnData/${date}/nikeid_pix_demand.csv";
        path = path.replace("${date}", DateUtil.date2String(DateUtil.getYesterday(new Date()), DatePattern.COMMON_DATE));
        LOGGER.info(path);
        String[] columnTitles = { "a", "b" };
        List<Object[]> list = new ArrayList<Object[]>();
        for (int i = 0; i < 20; i++){
            Object[] object = { i + "金,鑫", i + "jin'\"xin" };
            list.add(object);
        }
        CSVUtil.write(path, columnTitles, list);
    }

    /**
     * Test write1.
     */
    @Test
    public void testWrite1(){

        List<BackWarnEntity> list = new ArrayList<BackWarnEntity>();
        BackWarnEntity backWarnEntity = new BackWarnEntity();
        list.add(backWarnEntity);

        String[] excludePropertyNames = { "class" };
        String url = "d:/1.csv";
        CSVUtil.write(url, list, excludePropertyNames);
        //DesktopUtil.open(url);
    }
}
