package com.company.稀疏数组;

public class SparseArray {

    public static void main(String[] args) {

        //创建一个原始的二维数组11*11(即原始棋盘)
        //0:表示没有棋子; 1:表示黑子; 2:表示蓝子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;

        //输出原始的二维数组
        System.out.println("原始的二维数组~~");
        //增强for循环
        for(int[] row : chessArr1) {
            for(int data : row) {
                //System.out.print(data + " ");
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        //将二维数组转为稀疏数组
        //1. 遍历二维数组, 统计非0元素的个数
        int sum = 0;
        for(int[] row : chessArr1) {
            for(int data : row) {
                if(data != 0) {
                    sum++;
                }
            }
        }
        //2. 创建对应的稀疏数组
        int[][] sparseArr = new int[sum+1][3];
        //给稀疏数组赋初值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //3. 再次遍历原始二维数组, 将非0值存放到稀疏数组中
        int count = 1;
        for(int i = 0; i < 11; i++) {
            for(int j = 0; j < 11; j++) {
                if(chessArr1[i][j] != 0) {
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                    count++;
                }
            }
        }

        //输出稀疏数组
        System.out.println("得到的稀疏数组为~~");
        for(int i = 0; i <= sum; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }

        //将稀疏数组恢复为原始的二维数组
        //1. 先读取稀疏数组的第一行, 根据第一行的数据, 创建原始的二维数组
        int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];
        //2. 再读取稀疏数组后几行的数据, 并赋给原始的二维数组即可
        for (int i = 1; i <= sparseArr[0][2]; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        //输出恢复后的二维数组
        System.out.println("恢复后的二维数组为~~");
        for(int[] row : chessArr2) {
            for(int data : row) {
                //System.out.print(data + " ");
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }
}
