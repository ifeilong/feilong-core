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
package com.feilong.core.text;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.text.MessageFormatUtil;
import com.feilong.core.tools.json.JsonUtil;

/**
 * The Class MessageFormatUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-3-27 上午1:36:55
 */
public class MessageFormatUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageFormatUtilTest.class);

    /**
     * Format.
     * 
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public final void format() throws ParseException{
        LOGGER.info(MessageFormatUtil.format("name=张三{0}a{1}", "jin", "xin"));
        LOGGER.info(MessageFormatUtil.format("name=张三{0,number}a{1}", 5, "xin"));
        LOGGER.info(MessageFormatUtil.format("name=张三{0,date}a{1}", 15, "xin"));
    }

    /**
     * Test get value with arguments1.
     * 
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public final void testGetValueWithArguments1() throws ParseException{
        MessageFormat mf = new MessageFormat("{0}, {0}, {0}");
        String forParsing = "x, y, z";
        Object[] objs = mf.parse(forParsing, new ParsePosition(0));
        // result now equals {new String("z")}
        LOGGER.info(Arrays.toString(objs));

        LOGGER.info("objs:{}", JsonUtil.format(objs));

        int planet = 7;
        String event = "a disturbance in the Force";
        String result = MessageFormat.format(
                        "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                        planet,
                        new Date(),
                        event);
        LOGGER.info(result);

        MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
        double[] filelimits = { 0, 1, 2 };
        String[] filepart = { "no files", "one file", "{0,number} files" };

        ChoiceFormat fileform = new ChoiceFormat(filelimits, filepart);
        form.setFormatByArgumentIndex(0, fileform);
        int fileCount = 0;
        String diskName = "MyDisk";
        Object[] testArgs = { new Long(fileCount), diskName };
        LOGGER.info(form.format(testArgs));
    }
}
