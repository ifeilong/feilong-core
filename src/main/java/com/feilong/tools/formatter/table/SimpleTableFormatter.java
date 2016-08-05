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
import static com.feilong.core.date.DateExtensionUtil.formatDuration;
import static com.feilong.core.lang.ArrayUtil.newArray;
import static com.feilong.core.util.CollectionsUtil.addAllIgnoreNull;

/**
 * 简单的格式化成table的实现.
 * 
 * <p>
 * 简单的table 会渲染标题 和分隔符,不包含 padding margin等设定,也不支持复杂的组合表格设置
 * </p>
 * 
 * <h3>说明:</h3>
 * <blockquote>
 * <ol>
 * <li>不建议format 太多的数据,以容易查看为原则</li>
 * <li>如果字段含有中文,显示可能会错位,你可以尝试将结果进行 replace(SPACE, "\u3000") 处理,参见
 * <a href="http://stackoverflow.com/questions/18961628/how-can-i-align-the-next-lines-in-java#answer-18962279">format Chinese
 * characters</a></li>
 * </ol>
 * </blockquote>
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

        //***********************************************************************************
        List<Object[]> rows = combinRowsData(columnTitles, dataList);
        if (isNullOrEmpty(rows)){
            return EMPTY;
        }
        int[] colWidths = buildColumnMaxWidths(rows);

        if (null != columnTitles){
            insertSplitorLine(rows, colWidths);
        }

        //***********************************************************************************
        StringBuilder sb = new StringBuilder();

        for (Object[] cells : rows){
            sb.append(formatRowInfo(cells, colWidths)).append(LINE_SEPARATOR);
        }

        LOGGER.debug("use time:[{}]", formatDuration(beginDate));

        return LINE_SEPARATOR + sb.toString();
    }

    /**
     * Format row info.
     *
     * @param cells
     *            所有的单元格
     * @param colWidths
     *            the col widths
     * @return the string builder
     * @since 1.8.3
     */
    private static StringBuilder formatRowInfo(Object[] cells,int[] colWidths){
        StringBuilder sb = new StringBuilder();
        for (int colNum = 0; colNum < cells.length; colNum++){
            sb.append(StringUtils.rightPad(defaultString(ConvertUtil.toString(cells[colNum])), colWidths[colNum]));
            sb.append(SPACE);
        }
        return sb;
    }

    /**
     * 插入分隔符行.
     *
     * @param rows
     *            the rows
     * @param colWidths
     *            the col widths
     * @since 1.8.3
     */
    private static void insertSplitorLine(List<Object[]> rows,int[] colWidths){
        //**********模拟 分隔符*******************************************
        String[] borders = newArray(String.class, colWidths.length);
        for (int i = 0; i < borders.length; ++i){
            borders[i] = StringUtils.repeat("-", colWidths[i]);
        }
        rows.add(1, borders);
    }

    /**
     * 组合头和数据行.
     *
     * @param columnTitles
     *            the column titles
     * @param dataList
     *            the data list
     * @return 如果 null==columnTitles,将忽略<br>
     *         如果 null==dataList,将忽略<br>
     * @since 1.8.2
     */
    private static List<Object[]> combinRowsData(String[] columnTitles,List<Object[]> dataList){
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
        //最大列数
        int columnCount = -1;
        for (Object[] row : rows){
            columnCount = max(columnCount, row.length);
        }
        //*********************************************************************************
        int[] columnMaxWidths = new int[columnCount];
        for (Object[] row : rows){
            for (int colNum = 0; colNum < row.length; colNum++){
                //双层循环,定位每列最大的宽度
                columnMaxWidths[colNum] = max(columnMaxWidths[colNum], StringUtils.length(ConvertUtil.toString(row[colNum])));
            }
        }
        return columnMaxWidths;
    }
}