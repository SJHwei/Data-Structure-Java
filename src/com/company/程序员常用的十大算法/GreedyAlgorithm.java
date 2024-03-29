package com.company.程序员常用的十大算法;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author ShiWei
 * @date 2021/4/8 - 13:04
 */
public class GreedyAlgorithm {

    public static void main(String[] args) {
        //创建广播电台，放入到Map
        HashMap<String, HashSet<String>> broadCasts = new HashMap<>();
        //将各个电台放入到broadCasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        //加入到broadCasts(map)中
        broadCasts.put("K1", hashSet1);
        broadCasts.put("K2", hashSet2);
        broadCasts.put("K3", hashSet3);
        broadCasts.put("K4", hashSet4);
        broadCasts.put("K5", hashSet5);

        //allAreas存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");

        //创建ArrayList，存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();

        //定义一个临时的集合，在遍历的过程中，存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();

        //定义给maxKey，保存在一次遍历过程中，能够覆盖最大未覆盖的地区对应的电台的key
        //如果maxKey不为null，则会加入到selects
        String maxKey = null;

        while (allAreas.size() != 0) { //如果allAreas不为0，则表示还没有覆盖到所有的地区
            //细节一：每进行一次while，需要将maxKey置空
            maxKey = null;

            //遍历broadCasts，取出对应的key
            for (String key : broadCasts.keySet()) {
                //细节二：每进行一次for，需要将tempSet清空
                tempSet.clear();

                //得到当前这个key能够覆盖的地区
                HashSet<String> areas = broadCasts.get(key);
                tempSet.addAll(areas);
                //求出tempSet和allAreas集合的交集，交集会赋给tempSet
                tempSet.retainAll(allAreas);
                //如果当前这个集合包含的未覆盖的地区的数量，比maxKey指向的集合地区还多，就需要重置maxKey
                //tempSet.size() > broadCasts.get(maxKey).size()体现出贪心算法的特点：每次都选择最优的
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > broadCasts.get(maxKey).size())) {
                    maxKey = key;
                }
            }
            //如果maxKey不为空，就应该将maxKey加入selects
            if (maxKey != null) {
                selects.add(maxKey);
                //将maxKey指向的广播电台覆盖的地区，从allAreas去掉
                allAreas.removeAll(broadCasts.get(maxKey));
            }
        }

        System.out.println("得到的选择结果是" + selects);
    }
}
