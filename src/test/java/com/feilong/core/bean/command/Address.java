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
 * The Class Address.
 * 
 * @author feilong
 * @version 1.0.7 2014-6-25 15:19:53
 */
public class Address implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7167354662891178506L;

    /** The zip code. */
    private String            zipCode;

    /** The addr. */
    private String            addr;

    /** The city. */
    private String            city;

    /** The country. */
    private String            country;

    /**
     * Instantiates a new address.
     */
    public Address(){
    }

    /**
     * Instantiates a new address.
     * 
     * @param zipCode
     *            the zip code
     * @param addr
     *            the addr
     * @param city
     *            the city
     * @param country
     *            the country
     */
    public Address(String zipCode, String addr, String city, String country){
        this.zipCode = zipCode;
        this.addr = addr;
        this.city = city;
        this.country = country;
    }

    /**
     * Gets the addr.
     * 
     * @return the addr
     */
    public String getAddr(){
        return addr;
    }

    /**
     * Sets the addr.
     * 
     * @param addr
     *            the new addr
     */
    public void setAddr(String addr){
        this.addr = addr;
    }

    /**
     * Gets the city.
     * 
     * @return the city
     */
    public String getCity(){
        return city;
    }

    /**
     * Sets the city.
     * 
     * @param city
     *            the new city
     */
    public void setCity(String city){
        this.city = city;
    }

    /**
     * Gets the country.
     * 
     * @return the country
     */
    public String getCountry(){
        return country;
    }

    /**
     * Sets the country.
     * 
     * @param country
     *            the new country
     */
    public void setCountry(String country){
        this.country = country;
    }

    /**
     * Gets the zip code.
     * 
     * @return the zip code
     */
    public String getZipCode(){
        return zipCode;
    }

    /**
     * Sets the zip code.
     * 
     * @param zipCode
     *            the new zip code
     */
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
}