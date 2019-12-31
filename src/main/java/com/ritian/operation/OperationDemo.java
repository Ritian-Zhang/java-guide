package com.ritian.operation;

/**
 * java中常忽略的运算符
 * <p>
 * << . >>   ||.|      && . &   ^
 *
 * @author ritian
 * @since 2019/12/10 18:28
 **/
public class OperationDemo {

    /**
     * <pre>
     *      "|" 位运算(不短路),表示 两个位只要有一个为1，那么结果就是1，否则就为0
     *       例如 ： 2 | 3  表示2和3的二进制位运算
     *             00000010
     *             00000011
     *         --------------
     *             00000011
     *
     *
     *  </pre>
     */
    public static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    /**
     * <pre>
     *        ^ 位运算(异或),相同为0，不同为1
     *       例如 ： 2 | 3  表示2和3的二进制位运算
     *             00000010
     *             00000011
     *         --------------
     *             00000001
     *
     *
     *  </pre>
     */
    public static int xor(int rs, int wc) {
        return rs ^ wc;
    }


    public static void main(String[] args) {
        System.out.println(xor(3, 3));
    }

}
