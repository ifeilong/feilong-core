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
package com.feilong.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ByteUtilTest.
 *
 * @author feilong
 * @version 1.0 2012-4-10 下午11:55:36
 */
public class ByteUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteUtilTest.class);

    /**
     * Test byte to hex string lower case.
     */
    @Test
    public void testByteToHexStringLowerCase(){
        byte b1 = new Byte("1");
        assertEquals("01", ByteUtil.byteToHexStringLowerCase(b1));
        assertEquals("1", toHexString(b1));

        byte b2 = new Byte("100");
        assertEquals("64", ByteUtil.byteToHexStringLowerCase(b2));
        assertEquals("64", toHexString(b2));

        byte b3 = new Byte("127");
        assertEquals("7f", ByteUtil.byteToHexStringLowerCase(b3));
        assertEquals("7f", toHexString(b3));
    }

    /**
     * 下面的处理不了显示 00， 00显示的是0.
     *
     * @param b
     *            the b
     * @return the string
     * @since 1.2.0
     */
    private String toHexString(byte b){
        int intValue = 0;
        if (b >= 0){
            intValue = b;
        }else{
            intValue = 256 + b;
        }
        return Integer.toHexString(intValue);
    }

    /**
     * 字节数组转换成16进制字符串.
     * 
     * @param bytes
     *            byte[]
     * @return 16进制字符串
     * @deprecated ("该方法性能不高,请使用ByteUtil.bytesToHexStringUpperCase(byte[] bytes)")
     */
    @Deprecated
    public static final String bytesToHexString_old(byte[] bytes){
        if (null == bytes){
            throw new IllegalArgumentException("bytes不能为空");
        }
        String returnValue = "";
        String hex = "";
        int length = bytes.length;
        for (int i = 0; i < length; ++i){
            hex = Integer.toHexString(bytes[i] & 0xFF);// 整数转成十六进制表示
            if (hex.length() == 1){
                hex = '0' + hex;
            }
            returnValue += hex;
        }
        // 转成大写
        return returnValue.toUpperCase();
    }

    /**
     * Test.
     */
    @Test
    public void testBexBytesToBytes(){
        String hexString = "5B7B2264617465223A313333343037323035323038312C2273696D706C65536B75436F6D6D616E64223A7B22636F6465223A223331373830392D313030222C22666F625069726365223A323139392C226964223A353636372C226C6973745072696365223A323139392C226E616D65223A2241495220464F52434520312048494748204C5558204D4158204149522027303820515320E7A9BAE5869BE4B880E58FB7EFBC88E99990E9878FE58F91E594AEEFBC89227D7D5D";
        LOGGER.info(new String(ByteUtil.hexBytesToBytes(hexString.getBytes())));
    }
}
