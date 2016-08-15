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
package com.feilong.tools.formatter;

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
 * <h3>初衷:</h3>
 * 
 * <blockquote>
 * <p>
 * 在做开发的时候,我们经常会记录一些日志,使用log,但是对于 list map ,bean的日志输出一直很难做得很好,为了格式化输出,我们可能会使用 json来输出,比如:
 * </p>
 * 
 * <pre class="code">
 * List{@code <Address>} list = toList(
 *                 new Address("china", "shanghai", "wenshui wanrong.lu 888", "216000"),
 *                 new Address("china", "beijing", "wenshui wanrong.lu 666", "216001"),
 *                 new Address("china", "nantong", "wenshui wanrong.lu 222", "216002"),
 *                 new Address("china", "tianjing", "wenshui wanrong.lu 999", "216600"));
 * 
 * LOGGER.debug(JsonUtil.format(list));
 * </pre>
 * 
 * 结果:
 * 
 * <pre class="code">
[{
            "zipCode": "wenshui wanrong.lu 888",
            "addr": "216000",
            "country": "china",
            "city": "shanghai"
        },
                {
            "zipCode": "wenshui wanrong.lu 666",
            "addr": "216001",
            "country": "china",
            "city": "beijing"
        },
                {
            "zipCode": "wenshui wanrong.lu 222",
            "addr": "216002",
            "country": "china",
            "city": "nantong"
        },
                {
            "zipCode": "wenshui wanrong.lu 999",
            "addr": "216600",
            "country": "china",
            "city": "tianjing"
}]
 * </pre>
 * 
 * <p>
 * 可以看出,结果难以阅读,如果list元素更多一些,那么更加难以阅读;
 * </p>
 * 
 * </blockquote>
 * 
 * 
 * <h3>解决方案:</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * 这个时候可以使用
 * </p>
 * 
 * <pre class="code">
 * List{@code <Address>} list = toList(
 *                 new Address("china", "shanghai", "wenshui wanrong.lu 888", "216000"),
 *                 new Address("china", "beijing", "wenshui wanrong.lu 666", "216001"),
 *                 new Address("china", "nantong", "wenshui wanrong.lu 222", "216002"),
 *                 new Address("china", "tianjing", "wenshui wanrong.lu 999", "216600"));
 * 
 * LOGGER.debug(formatToSimpleTable(list));
 * </pre>
 * 
 * 
 * 结果:
 * 
 * <pre class="code">
addr   city     country zipCode                
------ -------- ------- ---------------------- 
216000 shanghai china   wenshui wanrong.lu 888 
216001 beijing  china   wenshui wanrong.lu 666 
216002 nantong  china   wenshui wanrong.lu 222 
216600 tianjing china   wenshui wanrong.lu 999
 * </pre>
 * 
 * <p>
 * 可以看出,输出的结果会更加友好
 * </p>
 * 
 * </blockquote>
 * 
 * <p>
 * 为了方便使用,可以直接使用 {@link FormatterUtil} 工具类
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
 * @since 1.8.5
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