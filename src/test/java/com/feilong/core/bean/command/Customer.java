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
package com.feilong.core.bean.command;

import java.io.Serializable;

/**
 * The Class Customer.
 */
public class Customer implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7749159103412879389L;

    /** The id. */
    private long              id;

    /** The name. */
    private String            name;

    /** The addresses. */
    private Address[]         addresses;

    /**
     * The Constructor.
     */
    public Customer(){
    }

    /**
     * The Constructor.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param addresses
     *            the addresses
     */
    public Customer(long id, String name, Address[] addresses){
        this.id = id;
        this.name = name;
        this.addresses = addresses;
    }

    /**
     * 获得 addresses.
     *
     * @return the addresses
     */
    public Address[] getAddresses(){
        return addresses;
    }

    /**
     * 设置 addresses.
     *
     * @param addresses
     *            the addresses
     */
    public void setAddresses(Address[] addresses){
        this.addresses = addresses;
    }

    /**
     * 获得 id.
     *
     * @return the id
     */
    public long getId(){
        return id;
    }

    /**
     * 设置 id.
     *
     * @param id
     *            the id
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * 获得 name.
     *
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * 设置 name.
     *
     * @param name
     *            the name
     */
    public void setName(String name){
        this.name = name;
    }
}
