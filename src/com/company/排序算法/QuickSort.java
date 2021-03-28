package com.company.排序算法;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author ShiWei
 * @date 2021/3/28 - 15:11
 */
public class QuickSort {

    public static void main(String[] args) {
        int arr[] = {-9, 78, 0, 23, -567, 70, 42, 123, -412};

        //创建要给80000个的随机的数组
//        int arr[] = new int[80000];
//        for (int i = 0; i < 80000; i++) {
//            arr[i] = (int) (Math.random() * 8000000); //生成一个[0，8000000)数
//        }

//        System.out.println("快速排序");
//        Date date1 = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = simpleDateFormat.format(date1);
//        System.out.println("排序前的时间是=" + date1Str);

        quickSort(arr, 0, arr.length - 1);

//        Date date2 = new Date();
//        String date2Str = simpleDateFormat.format(date2);
//        System.out.println("排序后的时间是=" + date2Str);

        System.out.println("arr=" + Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        int l = left; //左下标
        int r = right; //右下标
        //pivot 中轴值
        int pivot = arr[(left + right) / 2]; //注意：这样除的话，如果是奇数个元素，得到的就是中间元素；如果是偶数个元素，得到的就是中间靠左的元素。
        int temp = 0; //临时变量，作为交换时使用
        //while循环的目的是让比pivot值小的放到左边，比pivot值大的放到右边
        while (l < r) {
            //在pivot的左边一直找，找到大于等于pivot值，才退出
            while (arr[l] < pivot) {
                l += 1;
            }
            //在pivot的右边一直找，找到小于等于pivot值，才退出
            while (arr[r] > pivot) {
                r -= 1;
            }
            //如果l>=r说明pivot的左右两边的值，已经按照左边全是小于等于pivot值，右边全是大于等于pivot值排好了
            if (l >= r) {
                break;
            }

            //交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //如果交换完后，发现这个arr[r]==pivot值相等，l++，后移
            if (arr[r] == pivot) {
                l += 1;
            }
//            l++; //错误

            //如果交换完后，发现这个arr[l]==pivot值相等，r--，前移
            if (arr[l] == pivot) {
                r -= 1;
            }
//            r--; //错误


        }

        //如果l==r，必须l++，r++，否则会出现栈溢出
        //保证基准值不进入向下的递归中，也就是不再参与之后的比较
        if (l == r) {
            l += 1;
            r -= 1;
        }
        //向左递归
        if (left < r) {
            quickSort(arr, left, r);
        }
        //向右递归
        if(right > l) {
            quickSort(arr, l, right);
        }
    }
}
