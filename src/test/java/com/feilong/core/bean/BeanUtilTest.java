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
package com.feilong.core.bean;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.command.Address;
import com.feilong.core.bean.command.Customer;
import com.feilong.core.bean.command.Member;
import com.feilong.core.bean.command.MemberAddress;
import com.feilong.core.bean.command.SalesOrder;
import com.feilong.core.bean.command.SalesOrderDto;
import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.test.Person;
import com.feilong.test.User;

/**
 * The Class BeanUtilTest.
 *
 * @author feilong
 * @version 1.0 2012-5-15 上午10:45:34
 */
public class BeanUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtilTest.class);

    /** The sales order. */
    private SalesOrder          salesOrder;

    /**
     * Inits the.
     */
    @Before
    public void init(){
        salesOrder = new SalesOrder();
        salesOrder.setCreateTime(new Date());
        salesOrder.setCode("258415-002");
        salesOrder.setId(5L);
        salesOrder.setPrice(new BigDecimal(566));

        Member member = new Member();
        member.setCode("222222");
        long memberId = 888L;
        member.setId(memberId);
        member.setLoginName("feilong");

        Map<String, String> loveMap = new HashMap<String, String>();
        loveMap.put("蜀国", "赵子龙");
        loveMap.put("魏国", "张文远");
        loveMap.put("吴国", "甘兴霸");
        member.setLoveMap(loveMap);

        MemberAddress memberAddress1 = new MemberAddress();
        memberAddress1.setAddress("上海市宝山区真大路333弄22号1503室");
        memberAddress1.setAddTime(DateUtil.string2Date("20140615", DatePattern.yyyyMMdd));
        memberAddress1.setId(1L);
        memberAddress1.setMemberId(memberId);

        MemberAddress memberAddress2 = new MemberAddress();
        memberAddress2.setAddress("上海市闸北区阳城路280弄25号802室(阳城贵都)");
        memberAddress2.setAddTime(DateUtil.string2Date("20101001", DatePattern.yyyyMMdd));
        memberAddress2.setId(1L);
        memberAddress2.setMemberId(memberId);

        MemberAddress[] memberAddresses = { memberAddress1, memberAddress2 };
        member.setMemberAddresses(memberAddresses);

        salesOrder.setMember(member);
    }

    /**
     * Demo normal java beans.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDemoNormalJavaBeans() throws Exception{

        LOGGER.debug(StringUtils.center(" demoNormalJavaBeans ", 40, "="));

        // data setup  
        Address addr1 = new Address("CA1234", "xxx", "Los Angeles", "USA");
        Address addr2 = new Address("100000", "xxx", "Beijing", "China");
        Address[] addrs = new Address[2];
        addrs[0] = addr1;
        addrs[1] = addr2;
        Customer cust = new Customer(123, "John Smith", addrs);

        // accessing the city of first address  
        String cityPattern = "addresses[0].city";
        String name = (String) PropertyUtils.getSimpleProperty(cust, "name");
        String city = (String) PropertyUtils.getProperty(cust, cityPattern);
        Object[] rawOutput1 = new Object[] { "The city of customer ", name, "'s first address is ", city, "." };
        LOGGER.debug(StringUtils.join(rawOutput1));

        // setting the zipcode of customer's second address  
        String zipPattern = "addresses[1].zipCode";
        if (PropertyUtils.isWriteable(cust, zipPattern)){//PropertyUtils  
            LOGGER.debug("Setting zipcode ...");
            PropertyUtils.setProperty(cust, zipPattern, "200000");//PropertyUtils  
        }
        String zip = (String) PropertyUtils.getProperty(cust, zipPattern);//PropertyUtils  
        Object[] rawOutput2 = new Object[] { "The zipcode of customer ", name, "'s second address is now ", zip, "." };
        LOGGER.debug(StringUtils.join(rawOutput2));
    }

    /**
     * Demo dyna beans.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void demoDynaBeans() throws Exception{

        LOGGER.debug(StringUtils.center(" demoDynaBeans ", 40, "="));

        // creating a DynaBean  
        DynaProperty[] dynaBeanProperties = new DynaProperty[] {//DynaProperty  
        new DynaProperty("name", String.class), new DynaProperty("inPrice", Double.class), new DynaProperty("outPrice", Double.class), };
        BasicDynaClass cargoClass = new BasicDynaClass("Cargo", BasicDynaBean.class, dynaBeanProperties); //BasicDynaClass  BasicDynaBean  
        DynaBean cargo = cargoClass.newInstance();//DynaBean  

        // accessing a DynaBean  
        cargo.set("name", "Instant Noodles");
        cargo.set("inPrice", new Double(21.3));
        cargo.set("outPrice", new Double(23.8));
        LOGGER.debug("name: " + cargo.get("name"));
        LOGGER.debug("inPrice: " + cargo.get("inPrice"));
        LOGGER.debug("outPrice: " + cargo.get("outPrice"));
    }

    /**
     * Copy property.
     */
    @Test
    public void testCopyProperties1(){
        User a = new User();
        a.setId(5L);
        a.setMoney(new BigDecimal(500000));
        a.setDate(new Date());
        User b = new User();

        ConvertUtils.register(new DateLocaleConverter(Locale.US, DatePattern.TO_STRING_STYLE), Date.class);

        String[] strs = { "date", "money" };
        BeanUtil.copyProperties(b, a, strs);
        LOGGER.info(JsonUtil.format(b));
    }

    /**
     * Test copy property1.
     */
    @Test
    public void testCopyProperty1(){
        User user = new User();
        user.setId(5L);

        Person person = new Person();

        String[] strs = { "age", "name" };
        BeanUtil.copyProperties(person, user, strs);
        LOGGER.info(JsonUtil.format(person));
    }

    /**
     * Copy properties.
     */
    @Test
    public void testCopyProperties(){
        User user1 = new User();
        user1.setId(5L);
        user1.setDate(new Date());
        String[] nickName = { "feilong", "飞天奔月", "venusdrogon" };
        user1.setNickName(nickName);

        User user2 = new User();

        String[] aStrings = { "date", "id", "nickName" };
        ConvertUtils.register(new DateLocaleConverter(Locale.US, DatePattern.TO_STRING_STYLE), Date.class);
        BeanUtil.copyProperties(user2, user1, aStrings);
        LOGGER.debug(JsonUtil.format(user2));
    }

    /**
     * Copy properties1.
     */
    @Test
    public void testCopyProperties3(){
        SalesOrderDto salesOrderDto = new SalesOrderDto();
        //ConvertUtils.register(new DateLocaleConverter(Locale.US, DatePattern.forToString), Date.class);
        BeanUtil.copyProperties(salesOrderDto, salesOrder);
        LOGGER.debug("salesOrderDto:{}", JsonUtil.format(salesOrderDto));
    }

    /**
     * Test set property.
     */
    @Test
    public void testSetProperty(){
        SalesOrderDto salesOrderDto = new SalesOrderDto();
        salesOrderDto.setMember(new Member());
        BeanUtil.setProperty(salesOrderDto, "code", "123456");
        BeanUtil.setProperty(salesOrderDto, "member.code", "123456");
        BeanUtil.setProperty(salesOrderDto, "member.loveMap(mobile)", "iphone");
        LOGGER.debug("salesOrderDto:{}", JsonUtil.format(salesOrderDto));
    }

    /**
     * Test copy property.
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     */
    @Test
    public void testCopyProperty() throws IllegalAccessException,InvocationTargetException{
        SalesOrderDto salesOrderDto = new SalesOrderDto();
        salesOrderDto.setMember(new Member());
        BeanUtils.copyProperty(salesOrderDto, "code", "123456");
        BeanUtils.copyProperty(salesOrderDto, "member.code", "123456");
        BeanUtils.copyProperty(salesOrderDto, "member.loveMap(mobile)", "iphone");
        LOGGER.debug("salesOrderDto:{}", JsonUtil.format(salesOrderDto));
    }

    /**
     * Test set property map.
     */
    @Test
    public void testSetPropertyMap(){
        Map<String, String> map = new HashMap<String, String>();
        BeanUtil.setProperty(map, "code", "123456");
        LOGGER.debug("map:{}", JsonUtil.format(map));

        String[] strs = new String[1];
        strs[0] = "";
        BeanUtil.setProperty(strs, "[0]", "123456");
        LOGGER.debug("array:{}", JsonUtil.format(strs));
    }

    /**
     * Test copy property map.
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     */
    @Test
    public void testCopyPropertyMap() throws IllegalAccessException,InvocationTargetException{
        Map<String, String> map = new HashMap<String, String>();
        BeanUtils.copyProperty(map, "code", "123456");
        LOGGER.debug("map:{}", JsonUtil.format(map));

        String[] strs = new String[1];
        strs[0] = "";
        BeanUtils.copyProperty(strs, "[0]", "123456");
        LOGGER.debug("array:{}", JsonUtil.format(strs));
    }

    /**
     * Describe.
     */
    @Test
    public void describe(){
        User a = new User();
        a.setId(5L);
        Date now = new Date();
        a.setDate(now);

        Map<String, String> map = BeanUtil.describe(a);
        LOGGER.info("map:{}", JsonUtil.format(map));
    }

    /**
     * Populate.
     */
    @Test
    public void populate(){
        User a = new User();
        a.setId(5L);
        Date now = new Date();
        a.setDate(now);
        // DateConverter converter = new DateConverter("yyyy");
        // ConvertUtils.register(converter, Date.class);
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", 8L);
        // properties.put("date", 2010);
        BeanUtil.populate(a, properties);
        LOGGER.info(JsonUtil.format(a));
    }

    /**
     * Clone bean.
     */
    @Test
    public void cloneBean(){
        SalesOrder salesOrder1 = BeanUtil.cloneBean(salesOrder);
        LOGGER.debug(JsonUtil.format(salesOrder1));
    }
}
