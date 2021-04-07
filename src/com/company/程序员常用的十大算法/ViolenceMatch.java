package com.company.程序员常用的十大算法;

/**
 * @author ShiWei
 * @date 2021/4/7 - 11:02
 */
public class ViolenceMatch {

    public static void main(String[] args) {

        //测试暴力匹配算法
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        int ret = violenceMatch(str1, str2);
        System.out.println("ret = " + ret);
    }

    /**
     * 暴力匹配算法的实现
     * @param str1 被匹配的字符串
     * @param str2 要匹配的子串
     * @return
     */
    public static int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int str1Len = s1.length;
        int str2Len = s2.length;

        int i = 0; //i索引指向s1
        int j = 0; //j索引指向s2
        while (i < str1Len && j < str2Len) { //保证匹配时，不越界

            if (s1[i] == s2[j]) { //匹配ok
                i++;
                j++;
            } else { //匹配失败
                i = i - (j - 1);
                j = 0;
            }
        }

        //判断是否匹配成功
        if (j == str2Len) {
            return i - j;
        } else {
            return -1;
        }
    }
}
