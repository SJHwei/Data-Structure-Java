package com.company.树结构的实际应用;

import java.util.*;

/**
 * @author ShiWei
 * @date 2021/3/22 - 14:13
 */
public class HuffmanCode {

    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println("原字符串的长度：" + contentBytes.length); //40

        byte[] huffmanCodesBytes = huffmanZip(contentBytes);

        System.out.println("压缩后的结果是：" + Arrays.toString(huffmanCodesBytes));
        System.out.println("压缩后的长度：" + huffmanCodesBytes.length); //17


        //测试一把byteToBitString方法，此处需要强转，从int转成byte。
//        System.out.println(byteToBitString((byte) -88));
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodesBytes);
        System.out.println("原来的字符串=" + new String(sourceBytes)); //"i like like like java do you like a java"

        //如何将数据进行解压(解码)

        //分步过程
        /*
        List<HuffmanNode> nodes = getNodes(contentBytes);
        System.out.println("nodes=" + nodes);

        //测试一把，创建的赫夫曼树
        System.out.println("赫夫曼树");
        HuffmanNode huffmanTreeRoot = createHuffmanTree(nodes);
        System.out.println("前序遍历");
        preOrder(huffmanTreeRoot);

        //测试一把是否生成了对应的哈夫曼编码
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        System.out.println("生成的赫夫曼编码表" + huffmanCodes);

        //测试
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("huffmanCodeBytes" + Arrays.toString(huffmanCodeBytes));
        System.out.println(huffmanCodeBytes.length); //17

        //发送huffmanCodeBytes数组

        */

    }


    //完成数据的解压
    //思路：
    //第一步：将huffmanCodeBytes [-88, ...] 重新先转成赫夫曼编码对应的二进制的字符串 "10101000..."
    //第二步：将赫夫曼编码对应的二进制的字符串对照赫夫曼编码表转换成文本字符串 "i like like like java do you like a java"

    /**
     * 编写一个方法，完成对压缩数据的解码
     *
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {

        //1.先得到huffmanBytes对应的二进制的字符串，形式10101000...
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            //判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, huffmanBytes[i]));
        }

//        System.out.println("对应的二进制字符串：" + stringBuilder.toString());

        //2.把字符串按照指定的赫夫曼编码进行解码
        //把赫夫曼编码表进行调换，因为反向查询a->100，100->a
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        //创建一个集合，存放byte
        ArrayList<Byte> list = new ArrayList<>();
        //i可以理解成是索引，扫描stringBuilder
        for (int i = 0; i < stringBuilder.length();) {
            int count = 1; //小的计数器
            boolean flag = true;
            Byte b = null;

            while (flag) {
                //递增的取出key
                String key = stringBuilder.substring(i, i + count); //i不动，让count移动，直到匹配到一个字符
                b = map.get(key);
                if (b == null) { //说明没有匹配到
                    count++;
                } else {
                    //匹配到
                    flag = false;
                }
            }
            list.add(b);
            i += count; //i直接移动到（i+count）
        }
        //当for循环结束后，list中就存放了所有的字符
        //把list中的数据存放到byte数组，并返回
        byte b[] = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }

        return b;
    }

    /**
     * 将一个byte转成一个二进制的字符串，byte没有直接转换的方法，但是integer中有一个从int到二进制字符串的转换方法。
     *
     * @param flag 标志是否需要补高位。如果是true，表示需要补高位，如果是false，表示不补，如果是最后一个字节，无需补高位。
     * @param b    传入的byte
     * @return 是该b对应的二进制的字符串（注意是按补码返回的）
     */
    private static String byteToBitString(boolean flag, byte b) {
        //使用变量保存b
        int temp = b; //将b转成int，因为后面要使用integer的转换方法将int转成二进制字符串

        //如果是一个正数，还存在补高位的问题，例如如果是1，则返回的是1

        //注意：最后一个可能不满八位，所以需要使用判断
        if (flag) {
            temp |= 256; //按位与256    1 0000 0000 | 0000 0001 => 1 0000 0001
        }

        String str = Integer.toBinaryString(temp); //注意：此处返回的是temp对应的二进制的补码，而且长度很长，所以需要截取，取后面八位

        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }

    }


    //使用一个方法，将前面的方法封装起来，便于我们调用。

    /**
     * @param bytes 原始的字符串对应的字节数组
     * @return 是经过赫夫曼编码处理后的字节数组
     */
    private static byte[] huffmanZip(byte[] bytes) {
        List<HuffmanNode> nodes = getNodes(bytes);
        //根据nodes创建的赫夫曼树
        HuffmanNode huffmanTreeRoot = createHuffmanTree(nodes);
        //创建对应的赫夫曼编码(根据赫夫曼树)
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        //根据生成的赫夫曼编码，压缩得到压缩后的赫夫曼编码字节数组
        byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);

        return huffmanCodeBytes;
    }

    //编写一个方法，将字符串对应的byte[]数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码压缩后的byte[]

    /**
     * @param bytes        这是原始的字符串对应的byte[]
     * @param huffmanCodes 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的byte[]，即赫夫曼编码处理后的字符串形式转为对应的byte[] huffmanCodeBytes，即8位对应一个byte，放入到huffmanCodeBytes
     * huffmanCodeBytes[0]=10101000(补码) => byte
     * huffmanCodeBytes[0]=-88
     * 原因：字符串形式太长，人家原先长度为40，现在成了133，所以要转为byte数组，节省空间，byte数组的长度为17.
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {

        //1.利用huffmanCodes将bytes转成赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //遍历bytes数组
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }

//        System.out.println("测试stringBuilder=" + stringBuilder.toString());

        //2.将字符串形式转成byte[]
        //统计返回byte[] huffmanCodeBytes长度
        //一句话int len = (stringBuilder.length() + 7) / 8
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }
        //创建存储压缩后的byte数组
        byte huffmanCodeBytes[] = new byte[len];
        int index = 0; //记录是第几个byte
        for (int i = 0; i < stringBuilder.length(); i += 8) { //因为是每8位对应一个byte，所以步长+8
            String strByte;
            if (i + 8 > stringBuilder.length()) { //不够8位
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            //将strByte转成一个byte，放入到huffmanCodeBytes
            //注意：一定要转成byte，要不然类型不匹配
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }

        return huffmanCodeBytes;
    }

    //生成赫夫曼树对应的赫夫曼编码
    //思路：
    //1.将赫夫曼编码表放在Map<Byte, String>，形式为：32->01 97->100 100->11000 等等[形式]
    static Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();
    //2.再生成赫夫曼编码表示，需要去拼接路径，定义一个StringBuilder，存储某个叶子节点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    //为了调用方便，我们重载getCodes
    private static Map<Byte, String> getCodes(HuffmanNode root) {
        if (root == null) {
            return null;
        }
        //处理root的左子树
        getCodes(root.left, "0", stringBuilder);
        //处理root的右子树
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }

    /**
     * 功能：将传入的node节点的所有叶子节点的赫夫曼编码得到，并放入到huffmanCodes集合
     *
     * @param node          传入节点
     * @param code          路径：左子节点是0，右子节点1
     * @param stringBuilder 用于拼接路径
     *                      <p>
     *                      两点注意：
     *                      1.利用传入的stringBuilder新建一个stringBuilder2，往后传stringBuilder2，因为不在传入的stringBuilder上添加东西，那么向左递归后，就可以直接向右递归，而不用删除向左递归之后添加的东西。
     *                      2.判断该节点是非叶子节点后，才会递归，而如果是叶子节点就不递归了。这样就可以传入时直接添加字符而不用判断当前节点是否为空。
     */
    private static void getCodes(HuffmanNode node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code加入到stringBuilder2中
        stringBuilder2.append(code);
        if (node != null) { //如果 node=null 不处理
            //判断当前node是叶子节点还是非叶子节点
            if (node.data == null) { //非叶子节点
                //递归处理
                //向左递归
                getCodes(node.left, "0", stringBuilder2);
                //向右递归
                getCodes(node.right, "1", stringBuilder2);
            } else { //说明是一个叶子节点
                //就表示找到某个叶子节点的最后
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }

    //前序遍历的方法
    private static void preOrder(HuffmanNode root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("赫夫曼树为空");
        }
    }

    /**
     * @param bytes 接收字节数组
     * @return 返回的就是List形式
     */
    private static List<HuffmanNode> getNodes(byte[] bytes) {

        //1.创建一个ArrayList
        ArrayList<HuffmanNode> nodes = new ArrayList<>();

        //2.遍历bytes，统计每一个byte出现的次数 -> map[key, value]
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) { //map还没有这个字符数据，第一次
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        //3.把每一个键值对转成一个Node对象，并加入到nodes集合
        //遍历map
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        return nodes;
    }

    //可以通过List创建对应的赫夫曼树
    private static HuffmanNode createHuffmanTree(List<HuffmanNode> nodes) {

        while (nodes.size() > 1) {
            //排序，从小到大
            Collections.sort(nodes);
            //取出第一棵最小的二叉树
            HuffmanNode leftNode = nodes.get(0);
            //取出第二棵最小的二叉树
            HuffmanNode rightNode = nodes.get(1);
            //创建一棵新的二叉树，它的根节点没有data，只有权值
            HuffmanNode parent = new HuffmanNode(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;

            //将已经处理的两棵二叉树从nodes删除
            nodes.remove(leftNode);
            nodes.remove(rightNode);

            //将新的二叉树加到nodes中
            nodes.add(parent);

        }

        //nodes最后的节点就是哈夫曼树的根节点
        return nodes.get(0);
    }
}

//创建Node，带数据和权值
class HuffmanNode implements Comparable<HuffmanNode> {
    Byte data; //存放数据（字符）本身，比如 'a' => 97 ' ' => 32
    int weight; //权值，表示字符出现的次数
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        //从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}
