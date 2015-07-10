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

import java.util.Arrays;
import java.util.Collections;

/**
 * 常用的算法,包括二分搜索法,直接插入排序,快速排序,选择排序,冒泡排序等.
 * 
 * <p>
 * 注：使用场景不高，一般需要用到排序的时候，会调用 {@link Arrays#sort(Object[], java.util.Comparator)}, {@link Collections#sort(java.util.List)}
 * </p>
 * 
 * @author 金鑫 2010-4-15 下午01:51:24
 * @since 1.0.0
 * @deprecated will Re-structure
 */
@Deprecated
public final class Algorithm{

    /** Don't let anyone instantiate this class. */
    private Algorithm(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 二分搜索法，返回座标，不存在返回-1.
     * 
     * @param sort
     *            the sort
     * @param data
     *            the data
     * @return the int
     */
    public static int binarySearch(int[] sort,int data){
        if (data < sort[0] || data > sort[sort.length - 1]){
            return -1;
        }
        int begin = 0;
        int end = sort.length;
        int mid = (begin + end) / 2;
        while (begin <= end){
            mid = (begin + end) / 2;
            if (data > sort[mid]){
                begin = mid + 1;
            }else if (data < sort[mid]){
                end = mid - 1;
            }else{
                return mid;
            }
        }
        return -1;
    }

    /**
     * 直接插入排序.
     * 
     * <pre>
     * 是<b>稳定的排序方法</b>.
     * 将一个数据插入到已经排好序的有序数据中,从而得到一个新的、个数加一的有序数据
     * 算法适用于少量数据的排序，时间复杂度为O(n^2).
     * </pre>
     * 
     * @param sort
     *            the sort
     */
    public static void directInsertSort(int[] sort){
        for (int i = 1; i < sort.length; ++i){
            int index = i - 1;
            int temp = sort[i];
            while (index >= 0 && sort[index] > temp){
                sort[index + 1] = sort[index];
                index--;
            }
            sort[index + 1] = temp;
        }
    }

    /**
     * 快速排序.
     * 
     * <pre>
     * 过一趟排序将要排序的数据分割成独立的两部分， 其中一部分的所有数据都比另外一部分的所有数据都要小，
     * 然后再按此方法对这两部分数据分别进行快速排序， 整个排序过程可以递归进行，以此达到整个数据变成有序序列.
     * </pre>
     * 
     * @param <T>
     *            the generic type
     * @param sort
     *            要排序的数组
     * @param start
     *            排序的开始座标
     * @param end
     *            排序的结束座标
     */
    public static <T extends Number> void quickSort(T[] sort,int start,int end){
        // 设置关键数据key为要排序数组的第一个元素，
        // 即第一趟排序后，key右边的数全部比key大，key左边的数全部比key小
        T key = sort[start];
        // 设置数组左边的索引，往右移动判断比key大的数
        int i = start;
        // 设置数组右边的索引，往左移动判断比key小的数
        int j = end;
        // 如果左边索引比右边索引小，则还有数据没有排序
        while (i < j){
            while (sort[j].doubleValue() > key.doubleValue() && j > start){
                j--;
            }
            while (sort[i].doubleValue() < key.doubleValue() && i < end){
                i++;
            }
            if (i < j){
                T temp = sort[i];
                sort[i] = sort[j];
                sort[j] = temp;
            }
        }
        // 如果左边索引比右边索引要大，说明第一次排序完成，将sort[j]与key对换，
        // 即保持了key左边的数比key小，key右边的数比key大
        if (i > j){
            T temp = sort[j];
            sort[j] = sort[start];
            sort[start] = temp;
        }
        // 递归调用
        if (j > start && j < end){
            quickSort(sort, start, j - 1);
            quickSort(sort, j + 1, end);
        }
    }

    /**
     * 选择排序.
     * 
     * <pre>
     * 每一趟从待排序的数据元素中选出最小（或最大）的一个元素，
     * 顺序放在已排好序的数列的最后，
     * 直到全部待排序的数据元素排完.
     * 
     * 选择排序是不稳定的排序方法.
     * </pre>
     * 
     * @param array
     *            需要排序的数组
     */
    public static void selectSort(int[] array){
        for (int i = 0; i < array.length - 1; ++i){
            for (int j = i + 1; j < array.length; ++j){
                if (array[j] < array[i]){
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序算法.
     * 
     * <pre>
     * 冒泡排序，具有稳定性
     * 
     * 时间复杂度为O（n^2）
     * 
     * 不及堆排序，快速排序O（nlogn，底数为2）
     * 
     * 依次比较相邻的两个数，将小数放在前面，大数放在后面.
     * 
     * 即首先比较第1个和第2个数，将小数放前，大数放后.
     * 然后比较第2个数和第3个数，将小数放前，大数放后，
     * 如此继续，直至比较最后两个数，将小数放前，大数放后.
     * 
     * 重复以上过程，仍从第一对数开始比较（因为可能由于第2个数和第3个数的交换，使得第1个数不再小于第2个数），
     * 将小数放前，大数放后，一直比较到最大数前的一对相邻数，将小数放前，大数放后，
     * 第二趟结束，在倒数第二个数中得到一个新的最大数.如此下去，直至最终完成排序.
     * 
     * 用二重循环实现，外循环变量设为i，内循环变量设为j.
     * 外循环重复9次，内循环依次重复9，8，...，1次.
     * 每次进行比较的两个元素都是与内循环j有关的，
     * 它们可以分别用a[j]和a[j+1]标识，i的值依次为1,2,...,9，对于每一个i, j的值依次为1,2,...10-i.
     * 
     * 设想被排序的数组R［1..N］垂直竖立，将每个数据元素看作有重量的气泡，
     * 根据轻气泡不能在重气泡之下的原则，从下往上扫描数组R，凡扫描到违反本原则的轻气泡，就使其向上&quot;漂浮&quot;，
     * 如此反复进行，直至最后任何两个气泡都是轻者在上，重者在下为止.
     * </pre>
     * 
     * @param <T>
     *            the generic type
     * @param array
     *            需要排序的数组
     * @param isDesc
     *            是否倒序,true倒序 大数字在上面, 小数字在下面,false 顺序
     */
    public static <T extends Number> void bubbleSort(T[] array,boolean isDesc){
        T temp;
        int count = array.length;
        for (int i = 0; i < count - 1; ++i){
            for (int j = 0; j < count - i - 1; ++j){
                temp = array[j];
                if (isDesc){
                    if (array[j].doubleValue() < array[j + 1].doubleValue()){// 把这里改成大于，就是升序了
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }else{
                    if (array[j].doubleValue() > array[j + 1].doubleValue()){// 把这里改成大于，就是升序了
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
    }

    /**
     * 某些情况下 不能快速的转成 T[]数组,可以调用这个方法.
     * 
     * @param array
     *            the array
     * @param isDesc
     *            是否倒序,true倒序 大数字在上面, 小数字在下面,false 顺序
     */
    public static void bubbleSort(Object[] array,boolean isDesc){
        Object temp;
        int count = array.length;
        for (int i = 0; i < count - 1; ++i){
            for (int j = 0; j < count - i - 1; ++j){
                temp = array[j];
                if (isDesc){
                    if (((Number) array[j]).doubleValue() < ((Number) array[j + 1]).doubleValue()){// 把这里改成大于，就是升序了
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }else{
                    if (((Number) array[j]).doubleValue() > ((Number) array[j + 1]).doubleValue()){// 把这里改成大于，就是升序了
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
    }
}