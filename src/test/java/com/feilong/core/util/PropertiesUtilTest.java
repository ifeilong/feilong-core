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
 * The Class PropertiesUtilTest.
 * 
 * @author feilong
 * @version 1.0 2011-5-19 下午03:41:14
 * @since 1.0
 */
public class PropertiesUtilTest{

    // @formatter:off

//	public static boolean write(String fileName){
//		// 建立Properties对象
//		Properties properties = new Properties();
//		// 将信息方入Properties对象
//		properties.put("a.b.c", "关羽");
//		properties.put("aaa", "ppp");
//		// 将信息包存在a.ini文件中
//		try{
//			properties.store(new FileOutputStream(fileName), null);
//		}catch (FileNotFoundException e){
//			LOGGER.error(e.getClass().getName(), e);
//		}catch (IOException e){
//			throw new UncheckedIOException(e);
//		}
//		return true;
//	}
//
//	public static void read(String fileName){
//		Properties properties = new Properties();
//		// 可以从a.ini中通过Properties.get方法读取配置信息
//		try{
//			properties.load(new FileInputStream(fileName));
//		}catch (FileNotFoundException e){
//			LOGGER.error(e.getClass().getName(), e);
//		}catch (IOException e){
//			throw new UncheckedIOException(e);
//		}
//		LOGGER.debug(properties.get("a.b.c"));
//		LOGGER.debug(properties.get("aaa"));
//	}
//
//	/**
//	 * 更新（或插入）一对properties信息(主键及其键值) 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值.
//	 * 
//	 * @param keyname
//	 *            键名
//	 * @param keyvalue
//	 *            键值
//	 */
//	public static void writeProperties(String keyname,String keyvalue){
//		try{
//			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性.
//			// 强制要求为属性的键和值使用字符串.返回值是 Hashtable 调用 put 的结果.
//			OutputStream fos = new FileOutputStream(profilepath);
//			props.setProperty(keyname, keyvalue);
//			// 以适合使用 load 方法加载到 Properties 表中的格式，
//			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
//			props.store(fos, "Update '" + keyname + "' value");
//		}catch (IOException e){
//			throw new UncheckedIOException(e);
//		}
//	}
//
//	/**
//	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值.
//	 * 
//	 * @param keyname
//	 *            键名
//	 * @param keyvalue
//	 *            键值
//	 */
//	public void updateProperties(String keyname,String keyvalue){
//		try{
//			props.load(new FileInputStream(profilepath));
//			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性.
//			// 强制要求为属性的键和值使用字符串.返回值是 Hashtable 调用 put 的结果.
//			OutputStream fos = new FileOutputStream(profilepath);
//			props.setProperty(keyname, keyvalue);
//			// 以适合使用 load 方法加载到 Properties 表中的格式，
//			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
//			props.store(fos, "Update '" + keyname + "' value");
//		}catch (IOException e){
//			throw new UncheckedIOException(e);
//		}
//	}

	// @formatter:on
}
