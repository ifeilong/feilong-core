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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.store.member.Address;
import com.feilong.store.member.Customer;
import com.feilong.store.member.Member;
import com.feilong.store.member.MemberAddress;
import com.feilong.store.order.OrderLine;
import com.feilong.store.order.SalesOrder;
import com.feilong.store.order.SalesOrderDto;
import com.feilong.test.Person;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class BeanUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
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
        salesOrder.setMember(buildMember());
    }

    /**
     * @return
     * @since 1.7.2
     */
    private Member buildMember(){
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
        memberAddress1.setAddTime(DateUtil.toDate("20140615", DatePattern.yyyyMMdd));
        memberAddress1.setId(1L);
        memberAddress1.setMemberId(memberId);

        MemberAddress memberAddress2 = new MemberAddress();
        memberAddress2.setAddress("上海市闸北区阳城路280弄25号802室(阳城贵都)");
        memberAddress2.setAddTime(DateUtil.toDate("20101001", DatePattern.yyyyMMdd));
        memberAddress2.setId(1L);
        memberAddress2.setMemberId(memberId);

        MemberAddress[] memberAddresses = { memberAddress1, memberAddress2 };
        member.setMemberAddresses(memberAddresses);
        return member;
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

        Customer customer = new Customer(123, "John Smith", toArray(addr1, addr2));

        // accessing the city of first address  
        String name = (String) PropertyUtils.getSimpleProperty(customer, "name");
        String city = (String) PropertyUtils.getProperty(customer, "addresses[0].city");

        Object[] rawOutput1 = new Object[] { "The city of customer ", name, "'s first address is ", city, "." };
        LOGGER.debug(StringUtils.join(rawOutput1));

        // setting the zipcode of customer's second address  
        String zipPattern = "addresses[1].zipCode";
        if (PropertyUtils.isWriteable(customer, zipPattern)){//PropertyUtils  
            LOGGER.debug("Setting zipcode ...");
            PropertyUtils.setProperty(customer, zipPattern, "200000");//PropertyUtils  
        }
        String zip = (String) PropertyUtils.getProperty(customer, zipPattern);//PropertyUtils  
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
        DynaProperty[] dynaBeanProperties = toArray(
                        new DynaProperty("name", String.class),
                        new DynaProperty("inPrice", Double.class),
                        new DynaProperty("outPrice", Double.class));

        BasicDynaClass cargoClass = new BasicDynaClass("Cargo", BasicDynaBean.class, dynaBeanProperties); //BasicDynaClass  BasicDynaBean  
        DynaBean cargoDynaBean = cargoClass.newInstance();//DynaBean  

        // accessing a DynaBean  
        cargoDynaBean.set("name", "Instant Noodles");
        cargoDynaBean.set("inPrice", new Double(21.3));
        cargoDynaBean.set("outPrice", new Double(23.8));

        LOGGER.debug("name: " + cargoDynaBean.get("name"));
        LOGGER.debug("inPrice: " + cargoDynaBean.get("inPrice"));
        LOGGER.debug("outPrice: " + cargoDynaBean.get("outPrice"));
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

        LOGGER.debug(JsonUtil.format(person));
    }

    /**
     * TestBeanUtilTest.
     */
    @Test(expected = NullPointerException.class)
    public void testBeanUtilTest(){
        BeanUtil.copyProperties(null, new Person());
    }

    @Test(expected = NullPointerException.class)
    public void testBeanUtilTest1(){
        BeanUtil.copyProperties(new Person(), null);
    }

    /**
     * Copy property.
     */
    @Test
    public void testCopyProperties1(){
        User user = new User();
        user.setId(5L);
        user.setMoney(new BigDecimal(500000));
        user.setDate(new Date());
        user.setNickNames(toArray("feilong", "飞天奔月", "venusdrogon"));

        //        ConvertUtils.register(new DateLocaleConverter(Locale.US, DatePattern.TO_STRING_STYLE), Date.class);
        BeanUtil.register(new DateLocaleConverter(Locale.US, DatePattern.TO_STRING_STYLE), Date.class);

        Converter converter = ConvertUtils.lookup(Date.class);
        LOGGER.debug("{},{}", converter.getClass().getSimpleName(), converter.convert(Date.class, new Date().toString()));

        String[] strs = { "date", "money", "nickNames" };

        User user2 = new User();
        BeanUtil.copyProperties(user2, user, strs);

        LOGGER.debug(JsonUtil.format(user2));

        converter = ConvertUtils.lookup(Date.class);
        LOGGER.debug("{},{}", converter.getClass().getSimpleName(), converter.convert(Date.class, new Date().toString()));
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
        User user = new User();
        user.setId(5L);
        user.setDate(new Date());

        LOGGER.debug(JsonUtil.format(BeanUtil.describe(user)));
    }

    /**
     * Describe1.
     */
    @Test
    public void describe1(){
        LOGGER.debug(JsonUtil.format(BeanUtil.describe(null)));
    }

    /**
     * Populate.
     */
    @Test
    public void populate(){
        User user = new User();
        user.setId(5L);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", 8L);

        BeanUtil.populate(user, properties);
        LOGGER.debug(JsonUtil.format(user));

        //********************************************************

        user = new User();
        user.setId(5L);

        BeanUtil.copyProperties(user, properties);
        LOGGER.debug(JsonUtil.format(user));
    }

    /**
     * Populate1.
     */
    @Test
    public void populate1(){
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", 8L);

        BeanUtil.populate(map, properties);

        LOGGER.debug(JsonUtil.format(map));
    }

    @Test
    public void cloneBean1(){
        SalesOrder newSalesOrder = salesOrder;
        newSalesOrder.setPrice(ConvertUtil.toBigDecimal(599));
        assertEquals(ConvertUtil.toBigDecimal(599), salesOrder.getPrice());
    }

    @Test
    public void cloneBean2(){
        OrderLine orderLine = new OrderLine();
        orderLine.setCount(8);
        orderLine.setSalePrice(ConvertUtil.toBigDecimal(599));

        List<OrderLine> list = ConvertUtil.toList(orderLine);

        //*******************************************************************

        List<OrderLine> list1 = list;
        List<OrderLine> copyList = new ArrayList<>();

        for (OrderLine orderLineTemp : list){
            copyList.add(BeanUtil.cloneBean(orderLineTemp));
        }
        //PropertyUtil.copyProperties(copyList, list);
        //************************************************************

        // List<OrderLine> cloneList = BeanUtil.cloneBean(list);

        //************************************************************

        String format = JsonUtil.format(list, ConvertUtil.toArray("MSRP"), 0, 0);
        LOGGER.debug("the param format:{}", format);

        List<OrderLine> serializelist = (List<OrderLine>) SerializationUtils.clone((Serializable) list);

        //******************************************************************
        for (OrderLine perOrderLine : list){
            perOrderLine.setSalePrice(ConvertUtil.toBigDecimal(200));
        }
        //******************************************************************
        List<OrderLine> jsonList = JsonUtil.toList(format, OrderLine.class);

        assertEquals(ConvertUtil.toBigDecimal(200), list1.get(0).getSalePrice());
        //assertEquals(ConvertUtil.toBigDecimal(599), cloneList.get(0).getSalePrice());
        assertEquals(ConvertUtil.toBigDecimal(599), serializelist.get(0).getSalePrice());
        assertEquals(ConvertUtil.toBigDecimal(599), jsonList.get(0).getSalePrice());
        assertEquals(ConvertUtil.toBigDecimal(599), copyList.get(0).getSalePrice());
    }
}
