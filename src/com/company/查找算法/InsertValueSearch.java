package com.company.查找算法;

/**
 * @author ShiWei
 * @date 2021/3/30 - 14:55
 */
public class InsertValueSearch {

    public static void main(String[] args) {

//        int arr[] = new int[100];
//        for (int i = 0; i < 100; i++) {
//            arr[i] = i + 1;
//        }

        int arr[] = {1, 8, 10, 89, 1000, 1000, 1234};

//        System.out.println(Arrays.toString(arr));

        int resIndex = insertValueSearch(arr, 0, arr.length - 1, 1);
        System.out.println("resIndex = " + resIndex);
        int Index = binarySearch(arr, 0, arr.length - 1, 1);
        System.out.println("Index = " + Index);
    }

    /**
     * 编写插值查找算法
     *
     * @param arr     数组
     * @param left    左边索引
     * @param right   右边索引
     * @param findVal 查找值
     * @return 如果找到，就返回对应的下标，如果没有找到，就返回-1
     * <p>
     * 说明：插值查找算法是二分查找的改进，当然也要求数组是有序的
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findVal) {
        System.out.println("插值查找次数");

        //注意：(findVal < arr[0]) 和 (findVal > arr[arr.length - 1])必须需要
        //否则我们得到的mid可能越界
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return -1;
        }

        //求出mid
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        if (findVal > midVal) { //说明应该向右边递归
            return insertValueSearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) { //说明向左边递归
            return insertValueSearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }

    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        System.out.println("二分查找次数");

        //当left>right时，说明递归整个数组，但是没有找到
        if (left > right) {
            return -1;
        }

        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (findVal > midVal) { //向右递归
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) { //向左递归
            return binarySearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }
}
