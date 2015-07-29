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

/**
 * {@link java.lang.Byte} 工具类.
 * 
 * @author feilong
 * @version 1.0.0 2010-3-11 下午02:58:49
 * @since 1.0.0
 */
public final class ByteUtil{

    /** 数字 字符数组. */
    private static final char[]   DIGIT_2_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', };

    /** The Constant hexDigits. */
    private static final String[] HEX_DIGITS    = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /** Don't let anyone instantiate this class. */
    private ByteUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 字节数组,转成小写的16进制字符串.<br>
     * md5加密 使用这个.
     * 
     * @param b
     *            the b
     * @return the string
     */
    public static String bytesToHexStringLowerCase(byte[] b){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i){
            sb.append(byteToHexStringLowerCase(b[i]));
        }
        return sb.toString();
    }

    /**
     * 字节数组,转成大写的16进制字符串 .<br>
     * 网友gdpglc的思路.
     * 
     * @param bytes
     *            the bytes
     * @return the string
     */
    public static String bytesToHexStringUpperCase(byte[] bytes){
        if (null == bytes){
            throw new IllegalArgumentException("bytes不能为空");
        }
        char[] tmpData = new char[bytes.length << 1];
        for (int i = 0; i < bytes.length; ++i){
            int left = (bytes[i] & 0xF0) >> 4;
            tmpData[i << 1] = DIGIT_2_CHARS[left];
            int right = bytes[i] & 0x0F;
            tmpData[(i << 1) + 1] = DIGIT_2_CHARS[right];
        }
        return new String(tmpData);
    }

    // XXX UpperCase
    // XXX LowerCase
    /**
     * 字节转成16进制小写字符串.<br>
     * 
     * <pre>
     * {@code
     * Example:
     *  byteToHexStringLowerCase(new Byte(1))====> 01
     *  byteToHexStringLowerCase(new Byte(32))====> 20
     *  byteToHexStringLowerCase(new Byte(127))====> 7f
     * }
     * </pre>
     * 
     * @param b
     *            the b
     * @return the string
     */
    public static String byteToHexStringLowerCase(byte b){
        int i = b;
        if (i < 0){
            i += 256;
        }
        int d1 = i / 16;
        int d2 = i % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    // *****************************************************************************************************
    /**
     * 将两个ASCII字符合成一个字节,如：{@code "EF"--> 0xEF}.
     * 
     * @param byte1
     *            the byte1
     * @param byte2
     *            the byte2
     * @return 将两个ASCII字符合成一个字节； 如：{@code "EF"--> 0xEF}
     */
    public static byte uniteBytes(byte byte1,byte byte2){
        byte b0 = Byte.decode("0x" + new String(new byte[] { byte1 })).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + new String(new byte[] { byte2 })).byteValue();
        return (byte) (b0 ^ b1);
    }

    /**
     * 16进制字符串转成字节数组.
     * 
     * @param bytes
     *            the bytes
     * @return 16进制字符串转成字节数组
     */
    public static byte[] hexBytesToBytes(byte[] bytes){
        int length = bytes.length;
        if ((length % 2) != 0){
            throw new IllegalArgumentException("长度不是偶数,length is:" + length);
        }
        byte[] bytes2 = new byte[length / 2];
        String item = "";
        for (int n = 0; n < length; n += 2){
            item = new String(bytes, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            bytes2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return bytes2;
    }

    /**
     * 将指定字符串hexString,以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}.
     * 
     * @param bytes
     *            the bytes
     * @return the byte[]
     */
    public static byte[] hexBytesToBytes2(byte[] bytes){
        int size = bytes.length / 2;

        byte[] ret = new byte[size];
        for (int i = 0; i < size; ++i){
            ret[i] = ByteUtil.uniteBytes(bytes[i * 2], bytes[i * 2 + 1]);
        }
        return ret;
    }
}