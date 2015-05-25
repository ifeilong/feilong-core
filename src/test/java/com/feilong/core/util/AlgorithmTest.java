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
package com.feilong.core.util;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.Algorithm;

/**
 * The Class AlgorithmTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-1-21 上午09:51:47
 */
public class AlgorithmTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(AlgorithmTest.class);

    /**
     * 测试选择排序.
     */
    @Test
    public void testSelectSort(){
        Random ran = new Random();
        int[] sort = new int[10];
        for (int i = 0; i < 10; i++){
            sort[i] = ran.nextInt(50);
        }
        log.info("排序前的数组为");
        for (int i : sort){
            log.info(i + " ");
        }
        Algorithm.selectSort(sort);
        log.info("排序后的数组为");
        for (int i : sort){
            log.info(i + " ");
        }
    }

    /**
     * 测试快速排序.
     */
    @Test
    public void testQuickSort(){
        Integer[] sort = { 54, 31, 89, 33, 66, 12, 68, 20 };
        log.info("排序前的数组为：");
        for (int data : sort){
            log.info(data + " ");
        }
        Algorithm.quickSort(sort, 0, sort.length - 1);
        log.info("排序后的数组为：");
        for (int data : sort){
            log.info(data + " ");
        }
    }

    /**
     * 测试快速排序.
     */
    @Test
    public void testQuickSort1(){
        String[] sort = { "14", "6", "12", "8.5", "10", "X", "L", "XL", "M", "3XL", "L/XL", "XXL/XXXL" };
        log.info("排序前的数组为：");
        for (String data : sort){
            log.info(data + " ");
        }
        Arrays.sort(sort);// 排序 10 12 14 3XL 6 8.5 L M X XL
        // Algorithm.quickSort(sort, 0, sort.length - 1);
        log.info("排序后的数组为：");
        for (String data : sort){
            log.info(data + " ");
        }
    }

    /**
     * 测试快速排序.
     */
    @Test
    public void quickSort(){
        Number[] sort = { 54.8, 31.2, 89D, 33, 66.08888888, 12.36555565656, 68, 20, 20.000 };
        log.info("排序前的数组为：");
        for (Number data : sort){
            log.info(data + " ");
        }
        Algorithm.quickSort(sort, 0, sort.length - 1);
        log.info("排序后的数组为：");
        for (Number data : sort){
            log.info(data + " ");
        }
    }

    /**
     * 冒泡排序.
     */
    @Test
    public void bubbleSort(){
        Number[] sort = { 54.8, 31.2, 89D, 33, 66.08888888, 12.36555565656, 68, 20, 20.000 };
        log.info("排序前的数组为：");
        for (Number data : sort){
            log.info(data + " ");
        }
        Algorithm.bubbleSort(sort, false);
        log.info("排序后的数组为：");
        for (Number data : sort){
            log.info(data + " ");
        }
    }

    /**
     * 测试直接插入排序.
     */
    @Test
    public void testDirectInsertSort(){
        Random ran = new Random();
        int[] sort = new int[10];
        for (int i = 0; i < 10; i++){
            sort[i] = ran.nextInt(50);
        }
        log.info("排序前的数组为");
        for (int i : sort){
            log.info(i + " ");
        }
        Algorithm.directInsertSort(sort);
        log.info("排序后的数组为");
        for (int i : sort){
            log.info(i + " ");
        }
    }

    /**
     * 测试直接插入排序.
     */
    @Test
    public void testBinarySearch(){
        int[] sort = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int mask = Algorithm.binarySearch(sort, 6);
        log.info("" + mask);
    }
}
