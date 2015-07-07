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
package com.feilong.core.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.ObjectUtil;
import com.feilong.core.util.MapUtil;
import com.feilong.core.util.Validator;

/**
 * cvs工具类.
 * 
 * @author feilong
 * @version 1.0 2011-5-26 上午01:26:47
 * @since 1.0.0
 */
public final class CSVUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER                     = LoggerFactory.getLogger(CSVUtil.class);

    /** 转义引号用的字符 ". */
    private static final char   ESCAPE_CHARACTER        = '"';

    /** 默认的引号字符 "引号. */
    private static final char   DEFAULT_QUOTE_CHARACTER = '"';

    /**
     * \\u转义字符的意思是“\\u后面的1-4位16进制数表示的Unicode码对应的汉字”,而Unicode 0000 代表的字符是 NUL，也就是空的意思，<br>
     * 如果把这个字符输出到控制台，显示为空格.
     */
    private static final char   NO_QUOTE_CHARACTER      = '\u0000';

    /** Don't let anyone instantiate this class. */
    private CSVUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将迭代对象写到文件中.<br>
     * 自动调用 {@link com.feilong.core.bean.BeanUtil#describe(Object)} 获得对象中的可读属性和值
     *
     * @param <T>
     *            the generic type
     * @param fileName
     *            the file name
     * @param collection
     *            the iterable
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @see #write(String, String[], List, CSVParams)
     * @see #write(String, Collection, String[])
     * @see com.feilong.core.bean.BeanUtil#describe(Object)
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     * @since 1.0.9
     */
    public static final <T> void write(String fileName,Collection<T> collection) throws UncheckedIOException,IllegalArgumentException{
        write(fileName, collection, null);
    }

    /**
     * 将迭代对象写到文件中.<br>
     * 自动调用 {@link com.feilong.core.bean.BeanUtil#describe(Object)} 获得对象中的可读属性和值
     *
     * @param <T>
     *            the generic type
     * @param fileName
     *            the file name
     * @param collection
     *            the iterable
     * @param excludePropertyNames
     *            排除的读属性名称
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @see #write(String, String[], List, CSVParams)
     * @see com.feilong.core.bean.BeanUtil#describe(Object)
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     * @since 1.0.9
     */
    public static final <T> void write(String fileName,Collection<T> collection,String[] excludePropertyNames) throws UncheckedIOException,
                    IllegalArgumentException{

        if (Validator.isNullOrEmpty(fileName)){
            throw new NullPointerException("fileName can't be null/empty!");
        }

        if (Validator.isNullOrEmpty(collection)){
            throw new NullPointerException("iterable can't be null/empty!");
        }

        String[] columnTitles = null;
        List<Object[]> dataList = new ArrayList<Object[]>(collection.size());

        for (T t : collection){

            Map<String, Object> propertyValueMap = PropertyUtil.describe(t);
            propertyValueMap = MapUtil.getSubMapExcludeKeys(propertyValueMap, excludePropertyNames);

            int size = propertyValueMap.size();

            Object[] rowData = new Object[size];

            //标题列只需要创建一次
            if (null == columnTitles){
                columnTitles = new String[size];
            }

            int i = 0;
            for (Map.Entry<String, Object> entry : propertyValueMap.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();

                if (Validator.isNullOrEmpty(value)){
                    rowData[i] = StringUtils.EMPTY;
                }else{
                    rowData[i] = ConvertUtils.convert(value, String.class);
                }

                columnTitles[i] = key;
                i++;
            }
            dataList.add(rowData);
        }
        write(fileName, columnTitles, dataList, new CSVParams());
    }

    /**
     * 写cvs文件(默认使用GBK编码).
     *
     * @param fileName
     *            文件名称,全路径,自动生成不存在的父文件夹
     * @param columnTitles
     *            列标题,可以为空
     * @param dataList
     *            数据数组,可以带列名
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws IllegalArgumentException
     *             Validator.isNullOrEmpty(columnTitles) && Validator.isNullOrEmpty(dataList) 标题和内容都是空,没有任何意义,不创建文件
     * @since 1.0
     */
    public static final void write(String fileName,String[] columnTitles,List<Object[]> dataList) throws UncheckedIOException,
                    IllegalArgumentException{
        write(fileName, columnTitles, dataList, new CSVParams());
    }

    /**
     * 写cvs文件.
     *
     * @param fileName
     *            文件名称,全路径,自动生成不存在的父文件夹
     * @param columnTitles
     *            列标题,可以为空
     * @param dataList
     *            数据数组,可以带列名
     * @param csvParams
     *            the csv params
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws IllegalArgumentException
     *             Validator.isNullOrEmpty(columnTitles) && Validator.isNullOrEmpty(dataList) 标题和内容都是空,没有任何意义,不创建文件
     * @see com.feilong.core.io.IOWriteUtil#write(String, String, String)
     * @see #getWriteContent(List, CSVParams)
     */
    public static final void write(String fileName,String[] columnTitles,List<Object[]> dataList,CSVParams csvParams)
                    throws UncheckedIOException,IllegalArgumentException{
        // 标题和内容都是空,没有任何意义,不创建文件
        if (Validator.isNullOrEmpty(columnTitles) && Validator.isNullOrEmpty(dataList)){
            throw new IllegalArgumentException("columnTitles and dataList all null!");
        }else{
            if (Validator.isNullOrEmpty(dataList)){
                dataList = new ArrayList<Object[]>();
            }
            if (Validator.isNotNullOrEmpty(columnTitles)){
                dataList.add(0, columnTitles);
            }
            LOGGER.info("begin write file:" + fileName);
            IOWriteUtil.write(fileName, getWriteContent(dataList, csvParams), csvParams.getEncode());
        }
    }

    // *******************************************************************************
    /**
     * Writes the entire list to a CSV file. The list is assumed to be a String[]
     * 
     * @param allLines
     *            a List of String[], with each String[] representing a line of the file.
     * @param csvParams
     *            the csv params
     * @return the write content
     */
    private static final String getWriteContent(List<Object[]> allLines,CSVParams csvParams){
        if (Validator.isNotNullOrEmpty(allLines)){
            StringBuilder sb = new StringBuilder();
            for (Object[] nextLine : allLines){
                sb.append(getWriteContentLine(nextLine, csvParams));
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * Writes the next line to the file.
     * 
     * @param line
     *            the line
     * @param csvParams
     *            the csv params
     * @return the write content line
     */
    private static final String getWriteContentLine(Object[] line,CSVParams csvParams){
        // *******************用于扩展**********************************
        char separator = csvParams.getSeparator();
        char quotechar = DEFAULT_QUOTE_CHARACTER;

        String lineEnd = SystemUtils.LINE_SEPARATOR;
        // *************************************************************
        StringBuilder sb = new StringBuilder();
        int lineLength = line.length;
        for (int i = 0; i < lineLength; ++i){
            // 分隔符，列为空也要表达其存在.
            if (i != 0){
                sb.append(separator);
            }
            String currentElement = ObjectUtil.toString(line[i]);
            // *****************************************************
            if (currentElement != null){
                // *****************************************************
                if (quotechar != NO_QUOTE_CHARACTER){
                    sb.append(quotechar);
                }
                // *****************************************************
                int length = currentElement.length();
                for (int j = 0; j < length; ++j){
                    char currentChar = currentElement.charAt(j);
                    if (currentChar == quotechar || currentChar == ESCAPE_CHARACTER){
                        sb.append(ESCAPE_CHARACTER).append(currentChar);
                    }else{
                        sb.append(currentChar);
                    }
                }
                // ******************************************************
                if (quotechar != NO_QUOTE_CHARACTER){
                    sb.append(quotechar);
                }
                // *****************************************************
            }
        }
        sb.append(lineEnd);
        return sb.toString();
    }
}
