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
package com.feilong.core.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.Validator;

/**
 * 互联网协议 (ip) 地址相关操作.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-1-12 上午09:57:10
 * @since 1.0.0
 * @see java.net.InetAddress
 */
public final class InetAddressUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(InetAddressUtil.class);

    /** Don't let anyone instantiate this class. */
    private InetAddressUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将域名转成IP
     * 
     * <pre>
     * domainName2IpAddress("www.e-lining.com")-----{@code >}"210.14.70.88"
     * </pre>
     * 
     * @param domainName
     *            域名,不带参数 如www.e-lining.com
     * @return 域名对应的ip地址
     */
    public static String domainName2IpAddress(String domainName){
        InetAddress inetAddress = getInetAddress(domainName);
        return inetAddress.getHostAddress();
    }

    /**
     * 获得本机 InetAddress.
     * 
     * @return the inet address local host
     */
    public static InetAddress getInetAddressLocalHost(){
        InetAddress inetAddress = null;
        try{
            // 返回本地主机.
            // 如果有安全管理器，则使用本地主机名和 -1 作为参数来调用其 checkConnect 方法，以查看是否允许该操作.如果不允许该操作，则返回表示回送地址的 InetAddress
            inetAddress = InetAddress.getLocalHost();
        }catch (UnknownHostException e){
            LOGGER.error(e.getClass().getName(), e);
        }
        return inetAddress;
    }

    /**
     * 在给定主机名的情况下确定主机的 ip地址. <br>
     * 主机名可以是机器名（如 "java.sun.com"），也可以是其 ip 地址的文本表示形式.<br>
     * 
     * <pre>
     * 如果提供字面值 ip地址，则仅检查地址格式的有效性. 
     * 对于以字面值 ipv6 地址指定的 host，在 RFC 2732 中定义的形式或在 RFC 2373 中定义的字面值 ipv6 地址格式都可以接受.ipv6 范围地址也受支持.
     * 如果主机为 null，则返回表示回送接口地址的 InetAddress.
     * </pre>
     * 
     * @param host
     *            指定的主机，或 null.
     * @return 给定主机名的 ip 地址.
     */
    public static InetAddress getInetAddress(String host){
        // byte的范围是－128～127，所以0，1不用
        // @ byte[] ips = new byte[] { (byte) 127, (byte) 0, 0, 1 };
        try{
            InetAddress inetAddress = InetAddress.getByName(host);
            return inetAddress;
        }catch (UnknownHostException e){
            LOGGER.error(e.getClass().getName(), e);
        }
        return null;
    }

    /**
     * 获得 inetAddress log信息.
     * 
     * @param inetAddress
     *            the inet address
     * @return the inet address object log
     */
    public static Map<String, Object> getInetAddressObjectLog(InetAddress inetAddress){

        if (Validator.isNullOrEmpty(inetAddress)){
            return null;
        }

        Map<String, Object> object = new HashMap<String, Object>();

        // 返回此 InetAddress 对象的原始 IP 地址.
        // 结果按网络字节顺序：地址的高位字节位于 getAddress()[0] 中.
        object.put("getAddress", inetAddress.getAddress());

        // 获取此 IP 地址的完全限定域名.最大努力方法，意味着根据底层系统配置可能不能返回 FQDN.
        // 如果有安全管理器，则此方法首先使用主机名和 -1 作为参数调用其 checkConnect 方法，来查看是否允许调用代码知道此 IP 地址的主机名（即是否允许连接到该主机）.如果不允许该操作，则其返回 IP 地址的文本表示形式.
        object.put("getCanonicalHostName", inetAddress.getCanonicalHostName());

        // 返回 IP 地址字符串（以文本表现形式）.
        object.put("getHostAddress", inetAddress.getHostAddress());

        // 获取此 IP 地址的主机名.
        // 如果此 InetAddress 是用主机名创建的，则记忆并返回主机名；否则，将执行反向名称查找并基于系统配置的名称查找服务返回结果.如果需要查找名称服务，则调用 getCanonicalHostName.

        // 如果有安全管理器，则首先使用主机名和 -1 作为参数来调用其 checkConnect 方法，以查看是否允许该操作.
        // 如果不允许该操作，则其返回 IP 地址的文本表示形式.
        object.put("getHostName", inetAddress.getHostName());

        // 检查 InetAddress 是否是通配符地址的实用例行程序.
        object.put("isAnyLocalAddress", inetAddress.isAnyLocalAddress());

        // 检查 InetAddress 是否是链接本地地址的实用例行程序.
        object.put("isLinkLocalAddress", inetAddress.isLinkLocalAddress());

        // 检查 InetAddress 是否是回送地址的实用例行程序.
        object.put("isLoopbackAddress", inetAddress.isLoopbackAddress());

        // 检查多播地址是否具有全局域的实用例行程序.
        object.put("isMCGlobal", inetAddress.isMCGlobal());

        // 检查多播地址是否具有链接范围的实用例行程序.
        object.put("isMCLinkLocal", inetAddress.isMCLinkLocal());

        // 检查多播地址是否具有节点范围的实用例行程序.
        object.put("isMCNodeLocal", inetAddress.isMCNodeLocal());

        // 检查多播地址是否具有组织范围的实用例行程序.
        object.put("isMCOrgLocal", inetAddress.isMCOrgLocal());

        // 检查多播地址是否具有站点范围的实用例行程序.
        object.put("isMCSiteLocal", inetAddress.isMCSiteLocal());

        // 检查 InetAddress 是否是 IP 多播地址的实用例行程序.
        object.put("isMulticastAddress", inetAddress.isMulticastAddress());

        // 检查 InetAddress 是否是站点本地地址的实用例行程序.
        object.put("isSiteLocalAddress", inetAddress.isSiteLocalAddress());
        object.put("toString", inetAddress.toString());
        try{

            // 测试是否可以达到该地址.实现尽最大努力试图到达主机，但防火墙和服务器配置可能阻塞请求，使其在某些特定的端口可以访问时处于不可到达状态.
            // 如果可以获得权限，则典型实现将使用 ICMP ECHO REQUEST；否则它将试图在目标主机的端口 7 (Echo) 上建立 TCP 连接.
            // 超时值（以毫秒为单位）指示尝试应该使用的最大时间量.如果在获取应答前操作超时了，则视为主机不可到达.负值将导致抛出 IllegalArgumentException.

            // timeout - 调用中止前的时间（以毫秒为单位）
            object.put("isReachable(1000)", inetAddress.isReachable(1000));
        }catch (IOException e){
            LOGGER.error(e.getClass().getName(), e);
        }

        return object;
    }
}
