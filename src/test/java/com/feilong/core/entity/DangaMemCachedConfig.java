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
package com.feilong.core.entity;

import com.feilong.core.bean.Alias;

/**
 * The Class DangaMemCachedConfig.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.1
 */
public class DangaMemCachedConfig{

    /** The serverlist. */
    @Alias(name = "memcached.serverlist",sampleValue = "172.20.31.23:11211,172.20.31.22:11211")
    private String[]  serverList;

    /** The pool name. */
    @Alias(name = "memcached.poolname",sampleValue = "sidsock2")
    private String    poolName;

    /** The expire time 单位分钟. */
    @Alias(name = "memcached.expiretime",sampleValue = "180")
    private Integer   expireTime;

    /** 权重. */
    @Alias(name = "memcached.serverweight",sampleValue = "2,1")
    private Integer[] weight;

    /** The init connection. */
    @Alias(name = "memcached.initconnection",sampleValue = "10")
    private Integer   initConnection;

    /** The min connection. */
    @Alias(name = "memcached.minconnection",sampleValue = "5")
    private Integer   minConnection;

    /** The max connection. */
    @Alias(name = "memcached.maxconnection",sampleValue = "250")
    private Integer   maxConnection;

    /** 设置主线程睡眠时间,每30秒苏醒一次,维持连接池大小. */
    @Alias(name = "memcached.maintSleep",sampleValue = "30")
    private Integer   maintSleep;

    /** 关闭套接字缓存. */
    @Alias(name = "memcached.nagle",sampleValue = "false")
    private Boolean   nagle;

    /** 连接建立后的超时时间. */
    @Alias(name = "memcached.socketto",sampleValue = "3000")
    private Integer   socketTo;

    /** The alive check. */
    @Alias(name = "memcached.alivecheck",sampleValue = "false")
    private Boolean   aliveCheck;

    /**
     * 获得 serverlist.
     *
     * @return the serverList
     */
    public String[] getServerList(){
        return serverList;
    }

    /**
     * 设置 serverlist.
     *
     * @param serverList
     *            the serverList to set
     */
    public void setServerList(String[] serverList){
        this.serverList = serverList;
    }

    /**
     * Gets the pool name.
     *
     * @return the poolName
     */
    public String getPoolName(){
        return poolName;
    }

    /**
     * Sets the pool name.
     *
     * @param poolName
     *            the poolName to set
     */
    public void setPoolName(String poolName){
        this.poolName = poolName;
    }

    /**
     * 获得 expire time 单位分钟.
     *
     * @return the expireTime
     */
    public Integer getExpireTime(){
        return expireTime;
    }

    /**
     * 设置 expire time 单位分钟.
     *
     * @param expireTime
     *            the expireTime to set
     */
    public void setExpireTime(Integer expireTime){
        this.expireTime = expireTime;
    }

    /**
     * 获得 权重.
     *
     * @return the weight
     */
    public Integer[] getWeight(){
        return weight;
    }

    /**
     * 设置 权重.
     *
     * @param weight
     *            the weight to set
     */
    public void setWeight(Integer[] weight){
        this.weight = weight;
    }

    /**
     * 获得 init connection.
     *
     * @return the initConnection
     */
    public Integer getInitConnection(){
        return initConnection;
    }

    /**
     * 设置 init connection.
     *
     * @param initConnection
     *            the initConnection to set
     */
    public void setInitConnection(Integer initConnection){
        this.initConnection = initConnection;
    }

    /**
     * 获得 min connection.
     *
     * @return the minConnection
     */
    public Integer getMinConnection(){
        return minConnection;
    }

    /**
     * 设置 min connection.
     *
     * @param minConnection
     *            the minConnection to set
     */
    public void setMinConnection(Integer minConnection){
        this.minConnection = minConnection;
    }

    /**
     * 获得 max connection.
     *
     * @return the maxConnection
     */
    public Integer getMaxConnection(){
        return maxConnection;
    }

    /**
     * 设置 max connection.
     *
     * @param maxConnection
     *            the maxConnection to set
     */
    public void setMaxConnection(Integer maxConnection){
        this.maxConnection = maxConnection;
    }

    /**
     * 获得 设置主线程睡眠时间,每30秒苏醒一次,维持连接池大小.
     *
     * @return the maintSleep
     */
    public Integer getMaintSleep(){
        return maintSleep;
    }

    /**
     * 设置 设置主线程睡眠时间,每30秒苏醒一次,维持连接池大小.
     *
     * @param maintSleep
     *            the maintSleep to set
     */
    public void setMaintSleep(Integer maintSleep){
        this.maintSleep = maintSleep;
    }

    /**
     * 获得 关闭套接字缓存.
     *
     * @return the nagle
     */
    public Boolean getNagle(){
        return nagle;
    }

    /**
     * 设置 关闭套接字缓存.
     *
     * @param nagle
     *            the nagle to set
     */
    public void setNagle(Boolean nagle){
        this.nagle = nagle;
    }

    /**
     * 获得 连接建立后的超时时间.
     *
     * @return the socketTo
     */
    public Integer getSocketTo(){
        return socketTo;
    }

    /**
     * 设置 连接建立后的超时时间.
     *
     * @param socketTo
     *            the socketTo to set
     */
    public void setSocketTo(Integer socketTo){
        this.socketTo = socketTo;
    }

    /**
     * 获得 alive check.
     *
     * @return the aliveCheck
     */
    public Boolean getAliveCheck(){
        return aliveCheck;
    }

    /**
     * 设置 alive check.
     *
     * @param aliveCheck
     *            the aliveCheck to set
     */
    public void setAliveCheck(Boolean aliveCheck){
        this.aliveCheck = aliveCheck;
    }
}
