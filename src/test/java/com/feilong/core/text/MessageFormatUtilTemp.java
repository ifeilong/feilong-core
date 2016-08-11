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
package com.feilong.core.text;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageFormatUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageFormatUtilTemp.class);

    /**
     * Test get value with arguments2.
     */
    @Test
    public void testGetValueWithArguments2(){
        MessageFormat messageFormat = new MessageFormat("The disk \"{1}\" contains {0}.");
        double[] filelimits = { 0, 1, 2 };
        String[] filepart = { "no files", "one file", "{0,number} files" };

        ChoiceFormat choiceFormat = new ChoiceFormat(filelimits, filepart);
        messageFormat.setFormatByArgumentIndex(0, choiceFormat);

        int fileCount = 0;
        String diskName = "MyDisk";

        Object[] testArgs = { new Long(fileCount), diskName };
        LOGGER.debug(messageFormat.format(testArgs));
    }

    /**
     * Test get value with arguments1.
     */
    @Test
    public void testGetValueWithArguments1(){
        MessageFormat messageFormat = new MessageFormat("{0}, {0}, {0}");
        String forParsing = "x, y, z";
        Object[] objs = messageFormat.parse(forParsing, new ParsePosition(0));
        // result now equals {new String("z")}
        LOGGER.debug(Arrays.toString(objs));
    }

    /**
     * Test get value with arguments3.
     */
    @Test
    public void testGetValueWithArguments3(){
        int planet = 7;
        String event = "a disturbance in the Force";
        String string = "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.";
        LOGGER.debug(MessageFormatUtil.format(string, planet, new Date(), event));
    }
}
