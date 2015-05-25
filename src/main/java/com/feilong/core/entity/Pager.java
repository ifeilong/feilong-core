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
package com.feilong.core.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页 实体.<br>
 * 
 * <p>
 * 可用于 数据库的分页封装,也可用于前端分页的封装.
 * </p>
 * 通过简单的构造方法示例, 你可以得到下面的数据:
 * 
 * <ul>
 * <li>{@link #getAllPageNo()} 得到总页码</li>
 * <li>{@link #getOriginatingAllPageNo()} 获得原始的总页数(不经过 {@link #maxShowPageNo}) 修饰过的,(通过这个值 可以实现一些特殊的功能,一般用不到)</li>
 * <li>{@link #getPrePageNo()} 得到上一页页码</li>
 * <li>{@link #getNextPageNo()} 得到下一页页码</li>
 * <li>{@link #getOriginatingNextPageNo()} 在 原始的总页数 基础上 进行解析的下一页页码(通过这个值 可以实现一些特殊的功能,一般用不到)</li>
 * </ul>
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0.0 2009-9-2 下午02:24:44
 * @version 1.0.1 2012-3-16 01:01 将查询参数 删除,保留分页相关参数
 * @version 1.0.5 2014-5-3 14:22 新增 {@link #maxShowPageNo} 参数
 * @since 1.0.0
 */
public final class Pager implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID           = -903770720729924696L;

    /** 最多显示分页码 <code>{@value}</code>. */
    public static final int   DEFAULT_LIMITED_MAX_PAGENO = -1;

    /** 当前页码. */
    private Integer           currentPageNo;

    /** 每页显示数量,默认10,传入该参数 可以计算分页数量. */
    private Integer           pageSize                   = 10;

    /** 总共数据数,不同的数据库返回的类型不一样. */
    private Integer           count;

    /** 存放的数据集合. */
    private List<?>           itemList;

    /**
     * 最多显示页数,(-1或者不设置,默认显示所有页数)<br>
     * 类似于淘宝不管搜索东西多少,最多显示100页<br>
     * 这是一种折中的处理方式，空间换时间. 数据查询越往后翻，对服务器的压力越大，速度越低，而且从业务上来讲商品质量也越差，所以就没有必要给太多了.<br>
     * 新浪微博的时间轴也只给出了10页，同样的折中处理..
     */
    private Integer           maxShowPageNo;

    /**
     * The Constructor.
     */
    public Pager(){
    }

    /**
     * The Constructor.
     * 
     * @param currentPageNo
     *            当前页码
     * @param pageSize
     *            每页显示数目
     * @param count
     *            总数
     */
    public Pager(Integer currentPageNo, Integer pageSize, Integer count){
        this.currentPageNo = currentPageNo;
        this.pageSize = pageSize;
        this.count = count;
    }

    /**
     * 总页数({@link #maxShowPageNo} 参与装饰).<br>
     * 如果要获得原始的真正的总页数,请使用 {@link #getOriginatingAllPageNo()}
     * 
     * @return <ul>
     *         <li>如果{@code count==0}直接返回0</li>
     *         <li>如果{@code count < pageSize} 直接返回1</li>
     *         <li>如果{@code count % pageSize == 0 }除数是整数, 返回count / pageSize</li>
     *         <li>如果{@code count % pageSize != 0} 除数不是整数, 返回count / pageSize+1</li>
     *         <li>上面是原始的originatingAllPageNo</li>
     *         <li>如果
     *         {@code boolean isMaxShowPageNoDecorate = (null != maxShowPageNo && maxShowPageNo != Pager.DEFAULT_LIMITED_MAX_PAGENO && maxShowPageNo > 0);}
     *         ) <br>
     *         {@code originatingAllPageNo<=maxShowPageNo}) 直接返回 originatingAllPageNo,否则返回maxShowPageNo</li>
     *         <li>如果 !isMaxShowPageNoDecorate ,直接返回originatingAllPageNo</li>
     *         </ul>
     */
    public int getAllPageNo(){
        // maxShowPageNo不是null
        // 且maxShowPageNo !=Pager.DEFAULT_LIMITED_MAX_PAGENO
        // 且maxShowPageNo>0
        boolean isMaxShowPageNoDecorate = (null != maxShowPageNo && maxShowPageNo != Pager.DEFAULT_LIMITED_MAX_PAGENO && maxShowPageNo > 0);
        int originatingAllPageNo = getOriginatingAllPageNo();

        if (isMaxShowPageNoDecorate){
            if (originatingAllPageNo <= maxShowPageNo){
                return originatingAllPageNo;
            }
            return maxShowPageNo;
        }
        return originatingAllPageNo;
    }

    /**
     * 获得原始的总页数(不经过 {@link #maxShowPageNo}) 修饰过的 ,(通过这个值 可以实现一些特殊的功能,一般用不到).
     * 
     * @return <ul>
     *         <li>如果{@code count == 0}直接返回0</li>
     *         <li>如果{@code count < pageSize} 直接返回1</li>
     *         <li>如果{@code count % pageSize == 0}除数是整数, 返回count / pageSize</li>
     *         <li>如果{@code count % pageSize != 0}除数不是整数, 返回count / pageSize+1</li>
     *         </ul>
     */
    public int getOriginatingAllPageNo(){
        if (0 == count){
            return 0;
        }else if (count < pageSize){
            return 1;
        }
        // ***********************************
        // 除后的页数
        int i = count / pageSize;
        if (count % pageSize == 0){
            return i;
        }
        return i + 1;
    }

    /**
     * 上一页页码.
     * 
     * @return <ul>
     *         <li>如果{@code currentPageNo - 1<=1} 返回1</li>
     *         <li>否则返回 currentPageNo - 1</li>
     *         </ul>
     */
    public int getPrePageNo(){
        int prePageNo = currentPageNo - 1;
        if (prePageNo <= 1){
            return 1;
        }
        return prePageNo;
    }

    /**
     * 下一页页码.
     * 
     * @return <ul>
     *         <li>如果 currentPageNo+1 {@code >=} {@link #getAllPageNo()} 返回 {@link #getAllPageNo()}</li>
     *         <li>否则返回 currentPageNo+1</li>
     *         </ul>
     */
    public int getNextPageNo(){
        /** 总页数. */
        int allPageNo = getAllPageNo();
        int nextPage = currentPageNo + 1;
        if (nextPage >= allPageNo){
            return allPageNo;
        }
        return nextPage;
    }

    /**
     * 在 原始的总页数 基础上 进行解析的下一页页码(通过这个值 可以实现一些特殊的功能,一般用不到)
     * 
     * @return <ul>
     *         <li>如果 currentPageNo+1{@code >=} {@link #getOriginatingAllPageNo()} 返回 {@link #getOriginatingAllPageNo()}</li>
     *         <li>否则返回 currentPageNo+1</li>
     *         </ul>
     */
    public int getOriginatingNextPageNo(){
        // 获得原始的总页数(不经过 maxShowPageNo) 修饰过的 .
        int originatingAllPageNo = getOriginatingAllPageNo();
        int nextPage = currentPageNo + 1;
        if (nextPage >= originatingAllPageNo){
            return originatingAllPageNo;
        }
        return nextPage;
    }

    // **************************************************************************
    /**
     * Gets the 当前页码.
     * 
     * @return the currentPageNo
     */
    public Integer getCurrentPageNo(){
        return currentPageNo;
    }

    /**
     * Sets the 当前页码.
     * 
     * @param currentPageNo
     *            the currentPageNo to set
     */
    public void setCurrentPageNo(Integer currentPageNo){
        this.currentPageNo = currentPageNo;
    }

    /**
     * Gets the 每页显示数量,默认10,传入该参数 可以计算分页数量.
     * 
     * @return the pageSize
     */
    public Integer getPageSize(){
        return pageSize;
    }

    /**
     * Sets the 每页显示数量,默认10,传入该参数 可以计算分页数量.
     * 
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(Integer pageSize){
        this.pageSize = pageSize;
    }

    /**
     * Gets the 总共数据数,不同的数据库返回的类型不一样.
     * 
     * @return the count
     */
    public Integer getCount(){
        return count;
    }

    /**
     * Sets the 总共数据数,不同的数据库返回的类型不一样.
     * 
     * @param count
     *            the count to set
     */
    public void setCount(Integer count){
        this.count = count;
    }

    /**
     * Gets the 存放的数据集合.
     * 
     * @return the itemList
     */
    public List<?> getItemList(){
        return itemList;
    }

    /**
     * Sets the 存放的数据集合.
     * 
     * @param itemList
     *            the itemList to set
     */
    public void setItemList(List<?> itemList){
        this.itemList = itemList;
    }

    /**
     * Gets the 最多显示页数,(-1或者不设置,默认显示所有页数)<br>
     * 类似于淘宝不管搜索东西多少,最多显示100页<br>
     * 这是一种折中的处理方式，空间换时间. 数据查询越往后翻，对服务器的压力越大，速度越低，而且从业务上来讲商品质量也越差，所以就没有必要给太多了.<br>
     * 新浪微博的时间轴也只给出了10页，同样的折中处理..
     * 
     * @return the maxShowPageNo
     */
    public Integer getMaxShowPageNo(){
        return maxShowPageNo;
    }

    /**
     * Sets the 最多显示页数,(-1或者不设置,默认显示所有页数)<br>
     * 类似于淘宝不管搜索东西多少,最多显示100页<br>
     * 这是一种折中的处理方式，空间换时间. 数据查询越往后翻，对服务器的压力越大，速度越低，而且从业务上来讲商品质量也越差，所以就没有必要给太多了.<br>
     * 新浪微博的时间轴也只给出了10页，同样的折中处理..
     * 
     * @param maxShowPageNo
     *            the maxShowPageNo to set
     */
    public void setMaxShowPageNo(Integer maxShowPageNo){
        this.maxShowPageNo = maxShowPageNo;
    }
}