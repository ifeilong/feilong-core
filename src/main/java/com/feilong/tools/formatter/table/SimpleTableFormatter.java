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
package com.feilong.tools.formatter.table;

import static java.lang.Math.max;
import static org.apache.commons.collections4.CollectionUtils.addIgnoreNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.SystemUtils.LINE_SEPARATOR;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.tools.formatter.AbstractFormatter;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.date.DateExtensionUtil.getIntervalForView;
import static com.feilong.core.lang.ArrayUtil.newArray;
import static com.feilong.core.util.CollectionsUtil.addAllIgnoreNull;

/**
 * The Class SimpleTableFormatter.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @version 1.8.2 2016-7-21 18:30:09
 */
public class SimpleTableFormatter extends AbstractFormatter{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTableFormatter.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.coreextension.formatter.Formatter#format(java.lang.String[], java.util.List)
     */
    @Override
    public String format(String[] columnTitles,List<Object[]> dataList){
        Date beginDate = new Date();

        List<Object[]> rows = buildRows(columnTitles, dataList);
        if (isNullOrEmpty(rows)){
            return EMPTY;
        }
        int[] colWidths = buildColumnMaxWidths(rows);

        //**********模拟 分隔符*******************************************
        String[] borders = newArray(String.class, columnTitles.length);
        for (int i = 0; i < borders.length; ++i){
            borders[i] = StringUtils.repeat("-", colWidths[i]);
        }
        rows.add(1, borders);

        //***********************************************************************************
        StringBuilder sb = new StringBuilder();

        for (Object[] row : rows){
            for (int colNum = 0; colNum < row.length; colNum++){
                sb.append(StringUtils.rightPad(defaultString(toStringValue(row, colNum)), colWidths[colNum]));
                sb.append(SPACE);
            }
            sb.append(LINE_SEPARATOR);
        }

        LOGGER.debug("use time:{}", getIntervalForView(beginDate));

        return LINE_SEPARATOR + sb.toString();
    }

    /**
     * Builds the rows.
     *
     * @param columnTitles
     *            the column titles
     * @param dataList
     *            the data list
     * @return the list
     * @since 1.8.2
     */
    private static List<Object[]> buildRows(String[] columnTitles,List<Object[]> dataList){
        List<Object[]> rows = new ArrayList<>();
        addIgnoreNull(rows, columnTitles);
        addAllIgnoreNull(rows, dataList);
        return rows;
    }

    /**
     * 定位每列最大的宽度.
     *
     * @param rows
     *            the rows
     * @return the int[]
     */
    private static int[] buildColumnMaxWidths(List<Object[]> rows){
        //列数
        int columnCount = -1;
        for (Object[] row : rows){
            columnCount = max(columnCount, row.length);
        }
        //*********************************************************************************
        int[] columnMaxWidths = new int[columnCount];
        for (Object[] row : rows){
            for (int colNum = 0; colNum < row.length; colNum++){
                //双层循环,定位每列最大的宽度
                columnMaxWidths[colNum] = max(columnMaxWidths[colNum], StringUtils.length(toStringValue(row, colNum)));
            }
        }
        return columnMaxWidths;
    }

    /**
     * To string value.
     *
     * @param row
     *            the row
     * @param colNum
     *            the col num
     * @return the string
     */
    private static String toStringValue(Object[] row,int colNum){
        return ConvertUtil.toString(row[colNum]);
    }
}
