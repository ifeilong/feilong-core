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
package com.feilong.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.json.JsonUtil;
import com.feilong.test.User;

/**
 * The Class ListUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-12 上午11:40:44
 * @since 1.0
 */
public class ListUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ListUtilTest.class);

    /**
     * Test list util test222222.
     */
    @Test
    public void testListUtilTest222222(){

        Map<String, String> map = new TreeMap<String, String>();

        map.put("周小州", "123");
        map.put("金鑫", "234");
        map.put("郑猛", "345");
        map.put("谭斌", "8910");
        map.put("郑川旸", "8910");
        map.put("黄传坤", "8910");
        map.put("李辉", "8910");
        map.put("沈健", "8910");
        map.put("吴丹", "8910");
        map.put("丁升", "8910");

        Set<String> keySet = map.keySet();

        List<String> list = new ArrayList<String>(keySet);

        String[] array2 = list.toArray(new String[list.size()]);

        List<String> asList = new ArrayList<String>(Arrays.asList(array2));
        asList.add("小黑");
        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(asList));
        }

    }

    /**
     * TestListUtilTest.
     */
    @Test
    public void testListUtilTest6(){
        List<String> list = new ArrayList<String>();
        log.debug("the param list.size():{}", list.size());

    }

    /**
     * TestListUtilTest.
     */
    @Test
    public void testListUtilTest2(){
        int[] ints = { 1, 2, 3, 4, 5 };
        List<?> list = Arrays.asList(ints);
        log.debug("list'size：" + list.size());

        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(list));
        }
    }

    /**
     * TestListUtilTest.
     */
    @Test
    public void testListUtilTest(){
        User student = null;
        long begin1 = System.currentTimeMillis();
        List<User> list1 = new ArrayList<User>();
        for (Long i = 0L; i < 1000000; i++){
            student = new User(i);
            list1.add(student);
        }
        long end1 = System.currentTimeMillis();
        log.debug("list1 time：" + (end1 - begin1));

        //*******************************************************

        long begin2 = System.currentTimeMillis();
        List<User> list2 = new ArrayList<User>(1000000);
        for (Long i = 0L; i < 1000000; i++){
            student = new User(i);
            list2.add(student);
        }
        long end2 = System.currentTimeMillis();
        log.debug("list2 time：" + (end2 - begin2));
    }

    /**
     * Re.
     */
    @Test
    public void remove1(){

        LinkedList<String> list = new LinkedList<String>(){

            private static final long serialVersionUID = 5180874275617929469L;

            {
                add("a");
                add("a7");
                add("a8");
                add("a81");
                add("a82");
                add("a83");
                add("a84");
                add("a85");
            }
        };

        list.add("a");
        list.add("a7");
        list.add("a8");
        list.add("a81");
        list.add("a82");
        list.add("a83");
        list.add("a84");
        list.add("a85");

        for (String string : list){
            if ("a7".equals(string)){
                list.remove(string);
            }
        }
    }

    /**
     * Name.
     */
    @Test
    public void testRemove(){
        String spy = "曹操";
        List<String> list = new CopyOnWriteArrayList<String>(Arrays.asList("赵云", "张飞", "关羽", "马超", "黄忠", "法正", "庞统", "诸葛亮", "刘备", "曹操"));
        for (String name : list){
            if (name.equals(spy)){
                list.remove(name);
            }
        }

        log.debug(JsonUtil.format(list));
    }

    /**
     * Test remove22.
     */
    @Test
    public void testRemove22(){
        String limoumou = "李某某";

        List<User> userList = new ArrayList<User>();

        userList.add(new User("张三"));
        userList.add(new User("张三2"));
        userList.add(new User("张三3"));
        userList.add(new User("张三4"));
        userList.add(new User("张三5"));
        userList.add(new User("张三6"));
        userList.add(new User("张三7"));
        userList.add(new User(limoumou));
        userList.add(new User("张三8"));
        userList.add(new User("张三9"));
        userList.add(new User("张三12"));

        for (User user : userList){
            if (user.getName().equals(limoumou)){
                userList.remove(user);
            }
        }

        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(userList));
        }

    }

    /**
     * Remove2.
     */
    @Test
    public void remove2(){
        List<User> list = new ArrayList<User>();
        User user = new User();
        user.setId(8L);
        list.add(user);

        user = new User();
        user.setId(9L);
        list.add(user);

        for (User user2 : list){
            if (user2.getId().equals(8L)){
                list.remove(user2);
            }
        }

        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(list));
        }
    }

    /**
     * Removes the.
     */
    @Test
    public void remove(){
        List<String> list = new ArrayList<String>();
        list.add("xinge");
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong3");
        list.add("feilong4");
        list.add("feilong5");
        log.info(list.indexOf("xinge") + "");
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext();){
            String string = iterator.next();
            if ("feilong1".equals(string)){
                iterator.remove();
            }
        }
        log.info("list:{}", JsonUtil.format(list));
    }

    /**
     * Test list to string b.
     */
    @Test
    public final void testListToStringB(){
        List<String> testList = new ArrayList<String>();
        testList.add("xinge");
        testList.add("feilong");
        log.info(ListUtil.toStringReplaceBrackets(testList));
    }

    /**
     * Gets the first item.
     */
    @Test
    public final void testGetFirstItem(){
        List<String> testList = new ArrayList<String>();
        testList.add("xinge");
        testList.add("feilong");
        log.info(ListUtil.getFirstItem(testList));
    }

    /**
     * Test list to string a.
     */
    @Test
    public final void testListToStringA(){
        List<String> testList = new ArrayList<String>();
        testList.add("xinge");
        testList.add("feilong");
        log.info(ListUtil.toStringRemoveBrackets(testList));
    }

    /**
     * {@link com.feilong.core.util.ListUtil#toString(java.util.List, boolean)} 的测试方法。
     */
    @Test
    public final void testListToString(){
        List<String> testList = new ArrayList<String>();
        testList.add("xinge");
        testList.add("feilong");
        log.info(ListUtil.toString(testList, true));
    }

}