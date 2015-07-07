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
package com.feilong.core.tools.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.Person;

/**
 * JsonUtil测试类 (C) 2009-9-11, jzj.
 */
public class JsonUtilXMLTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilXMLTest.class);

    /**
     * list转XML void.
     */
    @Test
    public void testGetXMLFromObj4(){
        Person ps = new Person();
        ps.setDateAttr(new Date());
        ps.setName("get");
        List list = new ArrayList();
        list.add(ps);

        LOGGER.info(JsonUtil.objectToXML(list));
        /*
         * print: <?xml version="1.0" encoding="UTF-8"?> <a> <e class="object"> <dateAttr type="string">2009-09-12 07:02:31</dateAttr> <name
         * type="string">get</name> </e> </a>
         */
    }

    /**
     * set转XML void.
     */
    @Test
    public void testGetXMLFromObj5(){
        Person ps = new Person();
        ps.setDateAttr(new Date());
        ps.setName("get");
        Set set = new LinkedHashSet();
        set.add(ps);

        LOGGER.info(JsonUtil.objectToXML(set));
        /*
         * print: <?xml version="1.0" encoding="UTF-8"?> <a> <e class="object"> <dateAttr type="string">2009-09-12 07:04:38</dateAttr> <name
         * type="string">get</name> </e> </a>
         */
    }

    /**
     * map转XML void.
     */
    @Test
    public void testGetXMLFromObj6(){
        Person ps = new Person();
        ps.setDateAttr(new Date());
        ps.setName("get");
        Map map = new HashMap();
        map.put("person1", ps);

        LOGGER.info(JsonUtil.objectToXML(map));
        /*
         * print: <?xml version="1.0" encoding="UTF-8"?> <o> <person1 class="object"> <dateAttr type="string">2009-09-12 07:08:35</dateAttr>
         * <name type="string">get</name> </person1> </o>
         */
    }

    /**
     * 实体Bean转XML void.
     */
    @Test
    public void objectToXML7(){
        Person ps = new Person();
        ps.setDateAttr(new Date());
        ps.setName("get");
        LOGGER.info(JsonUtil.objectToXML(ps));
        /*
         * print: <?xml version="1.0" encoding="UTF-8"?> <o> <dateAttr type="string">2009-09-12 07:13:02</dateAttr> <name
         * type="string">get</name> </o>
         */
    }

    /**
     * 从XML对象串转json串 void.
     */
    @Test
    public void xmlToJson1(){
        String xml = "<o><dateAttr type='string'>2009-09-12 07:13:02</dateAttr><name type='string'>get</name></o>";
        // print: {"dateAttr":"2009-09-12 07:13:02","name":"get"}
        LOGGER.info(JsonUtil.xmlToJSON(xml).toString(4, 4));
    }

    /**
     * 从XML数组串转json串 void.
     */
    @Test
    public void xmlToJson2(){
        String xml = "<a class='array'><e class='object'><dateAttr type='string'>2009-09-12 07:04:38</dateAttr><name type='string'>get</name></e></a>";
        // print: [{"dateAttr":"2009-09-12 07:04:38","name":"get"}]
        LOGGER.info(JsonUtil.xmlToJSON(xml).toString(4, 4));
    }

    /**
     * json对象串转XML void.
     */
    @Test
    public void testGetXMLFromObj1(){
        LOGGER.info(JsonUtil.objectToXML("{\"name\":\"json\",\"bool\":true,\"int\":1}"));
        /*
         * print: <?xml version="1.0" encoding="UTF-8"?> <o> <bool type="boolean">true</bool> <int type="number">1</int> <name
         * type="string">json</name> </o>
         */
    }

    /**
     * json数组串转XML void.
     */
    @Test
    public void testGetXMLFromObj2(){
        LOGGER.info(JsonUtil.objectToXML("[1,2,3]"));
        /*
         * print: <?xml version="1.0" encoding="UTF-8"?> <a> <e type="number">1</e> <e type="number">2</e> <e type="number">3</e> </a>
         */
    }

    /**
     * java数组转XML void.
     */
    @Test
    public void testGetXMLFromObj3(){
        Person ps = new Person();
        ps.setDateAttr(new Date());
        ps.setName("get");
        Person[] personArr = new Person[2];
        personArr[0] = ps;

        LOGGER.info(JsonUtil.objectToXML(personArr));
        /*
         * print: <?xml version="1.0" encoding="UTF-8"?> <a> <e class="object"> <dateAttr type="string">2009-09-12 06:58:55</dateAttr> <name
         * type="string">get</name> </e> </a>
         */
    }
}
