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
package com.feilong.tools.jsonlib;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.entity.HttpMethodTestType;
import com.feilong.test.Person;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;
import com.feilong.tools.AbstractJsonTest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * JsonUtil测试类 (C) 2009-9-11, jzj.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToBeanTest extends AbstractJsonTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilToBeanTest.class);

    /**
     * 从json串转换成实体对象,且实体中Date属性能正确转换 void.
     */
    @Test
    public void toBean1(){
        String json = "{'name':'get','dateAttr':'2009-11-12'}";
        LOGGER.debug(JsonUtil.format(JsonUtil.toBean(json, Person.class)));
        LOGGER.debug(JsonUtil.format(json));
    }

    /**
     * 从json串转换成实体对象,并且实体集合属性存有另外实体Bean void.
     */
    @Test
    public void toBean(){
        String json = "{'data':[{'name':'get'},{'name':'set'}],'id':5}";
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        classMap.put("data", Person.class);

        MyBean myBean = JsonUtil.toBean(json, MyBean.class, classMap);
        LOGGER.debug(JsonUtil.format(myBean));
    }

    /**
     * To bean.
     */
    @Test
    public void toBean3(){
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        classMap.put("userAddresseList", UserAddress.class);

        User user = JsonUtil.toBean(USER_JSON_STRING, User.class, classMap);
        LOGGER.debug(JsonUtil.format(user));
    }

    /**
     * To bean n ull.
     */
    @Test
    public void toBeanNUll(){
        LOGGER.debug(JsonUtil.toJSON(null).toString(4, 4));
        LOGGER.debug(new JSONObject().toString(4));
    }

    /**
     * To json.
     */
    @Test
    public void toJSON(){
        LOGGER.debug(JsonUtil.toJSON(HttpMethodTestType.GET).toString(4, 4));
    }

    /**
     * To bean n ulluser.
     */
    @Test
    public void toBeanNUlluser(){
        User user = new User();
        user.setId(8L);
        user.setName("feilong");

        JsonConfig jsonConfig = new JsonConfig();

        // String[] excludes = { "userInfo" };
        // jsonConfig.setExcludes(excludes);

        Class<UserInfo> target = UserInfo.class;
        String[] properties = { "age" };
        jsonConfig.registerPropertyExclusions(target, properties);
        LOGGER.debug(JsonUtil.toJSON(user, jsonConfig).toString(4, 4));
    }

    /**
     * 把一个json数组串转换成实体数组 void.
     */
    @Test
    public void toArray2(){
        String json = "[{'name':'get'},{'name':'set'}]";
        Person[] objArr = JsonUtil.toArray(json, Person.class);

        LOGGER.debug(JsonUtil.format(objArr));
    }

    /**
     * 把一个json数组串转换成实体数组,且数组元素的属性含有另外实例Bean void.
     */
    @Test
    public void toArray3(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        classMap.put("data", Person.class);

        MyBean[] objArr = JsonUtil.toArray(json, MyBean.class, classMap);
        LOGGER.debug(JsonUtil.format(objArr));
    }

    /**
     * 把一个json数组串转换成集合,且集合里存放的为实例Bean void.
     */
    @Test
    public void toList(){
        String json = "[{'name':'get'},{'name':'set'}]";
        List<Person> list = JsonUtil.toList(json, Person.class);

        LOGGER.debug(JsonUtil.format(list));
    }

    /**
     * 把一个json数组串转换成集合,且集合里的对象的属性含有另外实例Bean void.
     */
    @Test
    public void toList3(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        classMap.put("data", Person.class);

        List<MyBean> list = JsonUtil.toList(json, MyBean.class, classMap);

        LOGGER.debug(JsonUtil.format(list));
    }

    /**
     * Name.
     */
    @Test
    public void name(){
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, Object> map1 = new HashMap<String, Object>();

        String[] aStrings = { "aaaa", "bbbb" };
        map1.put("b", aStrings);
        map1.put("bb", "2");
        map1.put("bbb", "3");

        map.put("a", map1);
        map.put("aa", map1);
        map.put("aaa", map1);
        LOGGER.debug(JsonUtil.toJSON(map).toString(4, 4));
    }

    /**
     * 实体Bean转json串 void.
     */
    @Test
    public void testgetJsonStr1(){
        Person ps = new Person();
        ps.setDateAttr(new Date());
        ps.setName("get");
        MyBean myBean = new MyBean();
        List<Object> list = new ArrayList<Object>();
        list.add(ps);

        myBean.setData(list);
        // print: {"data":[{"dateAttr":"2009-09-12 07:24:54","name":"get"}]}
        LOGGER.debug("" + JsonUtil.toJSON(myBean));
    }

    /**
     * list转json串 void.
     */
    @Test
    public void testgetJsonStr4(){
        Person ps = new Person();
        ps.setDateAttr(new Date());
        ps.setName("get");
        List<Person> list = new ArrayList<Person>();
        list.add(ps);

        // print: [{"dateAttr":"2009-09-12 07:22:49","name":"get"}]
        LOGGER.debug("" + JsonUtil.toJSON(list));

        Set set = new LinkedHashSet();
        set.add(ps);

        // print: [{"dateAttr":"2009-09-12 07:22:16","name":"get"}]
        LOGGER.debug("" + JsonUtil.toJSON(set));

        Person[] personArr = new Person[1];
        personArr[0] = ps;
        // print: [{"dateAttr":"2009-09-12 07:23:54","name":"get"}]
        LOGGER.debug("" + JsonUtil.toJSON(personArr));

        Map map = new LinkedHashMap();
        map.put("person1", ps);

        // print: {"person1":{"dateAttr":"2009-09-12 07:24:27","name":"get"}}
        LOGGER.debug("" + JsonUtil.toJSON(map));
    }

}
