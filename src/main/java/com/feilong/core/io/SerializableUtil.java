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
package com.feilong.core.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link java.io.Serializable}util.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @author Justin Hu
 * @version 1.2.1 2015年6月11日 上午1:18:18
 * @see java.io.Serializable
 * @see "c3p0 com.mchange.v2.ser.SerializableUtils#serializeToByteArray(Object)"
 * @see org.apache.commons.lang3.SerializationUtils
 * @see "org.hibernate.util.SerializationHelper"
 * @see "org.springframework.util.SerializationUtils"
 * @see "org.springframework.core.serializer.DefaultSerializer"
 * @since 1.2.1
 */
public final class SerializableUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializableUtil.class);

    /** Don't let anyone instantiate this class. */
    private SerializableUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * To byte array.
     *
     * @param serializable
     *            the serializable
     * @return the byte[]
     * @see org.apache.commons.lang3.SerializationUtils#serialize(Serializable)
     */
    public static byte[] serialize(Serializable serializable){
        return org.apache.commons.lang3.SerializationUtils.serialize(serializable);
    }

    /**
     * Deserialize.
     *
     * @param <T>
     *            the generic type
     * @param objectData
     *            the object data
     * @return the t
     * @see org.apache.commons.lang3.SerializationUtils#deserialize(byte[])
     */
    public static <T> T deserialize(byte[] objectData){
        return org.apache.commons.lang3.SerializationUtils.deserialize(objectData);
    }

    /**
     * Size.
     *
     * @param serializable
     *            the serializable
     * @return the int
     * @throws SerializationException
     *             the serialization exception
     * @see #toByteArrayOutputStream(Serializable)
     * @see java.io.ByteArrayOutputStream#size()
     */
    public static int size(Serializable serializable) throws SerializationException{
        ByteArrayOutputStream byteArrayOutputStream = null;
        try{
            byteArrayOutputStream = toByteArrayOutputStream(serializable);
            return byteArrayOutputStream.size();
        }finally{
            IOUtils.closeQuietly(byteArrayOutputStream);
        }
    }

    //*******************************************************************************************
    /**
     * To byte array output stream.
     *
     * @param serializable
     *            the serializable
     * @return the byte array output stream
     * @throws SerializationException
     *             the serialization exception
     * @see java.io.ObjectOutputStream#ObjectOutputStream(OutputStream)
     * @see java.io.ObjectOutputStream#writeObject(Object)
     * @see org.apache.commons.lang3.SerializationUtils#serialize(Serializable, OutputStream)
     */
    private static ByteArrayOutputStream toByteArrayOutputStream(Serializable serializable) throws SerializationException{
        ObjectOutputStream objectOutputStream = null;
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);

            return byteArrayOutputStream;
        }catch (IOException e){
            LOGGER.error("", e);
            throw new SerializationException(e);
        }finally{
            IOUtils.closeQuietly(objectOutputStream);
        }
    }

    /**
     * To string.
     *
     * @param serializable
     *            the serializable
     * @return the string
     * @throws SerializationException
     *             the serialization exception
     * @see #toByteArrayOutputStream(Serializable)
     * @deprecated 转字符串值得商榷
     */
    //TODO
    @Deprecated
    public static String toString(Serializable serializable) throws SerializationException{
        ByteArrayOutputStream byteArrayOutputStream = null;
        try{
            byteArrayOutputStream = toByteArrayOutputStream(serializable);

            String serializableString = byteArrayOutputStream.toString(CharsetType.ISO_8859_1);
            serializableString = java.net.URLEncoder.encode(serializableString, CharsetType.UTF8);

            return serializableString;
        }catch (IOException e){
            LOGGER.error("", e);
            throw new SerializationException(e);
        }finally{
            IOUtils.closeQuietly(byteArrayOutputStream);
        }
    }

    //*******************************************************************************************
    /**
     * To object.
     *
     * @param <T>
     *            the generic type
     * @param serializableString
     *            the serializable string
     * @return the t
     * @deprecated 转字符串值得商榷
     */
    //TODO
    @Deprecated
    public static <T> T toObject(String serializableString){
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            String decodeString = java.net.URLDecoder.decode(serializableString, CharsetType.UTF8);
            byteArrayInputStream = new ByteArrayInputStream(decodeString.getBytes(CharsetType.ISO_8859_1));

            return org.apache.commons.lang3.SerializationUtils.deserialize(byteArrayInputStream);
        }catch (IOException e){
            LOGGER.error("", e);
            throw new SerializationException(e);
        }finally{
            IOUtils.closeQuietly(byteArrayInputStream);
            IOUtils.closeQuietly(objectInputStream);
        }
    }

}
