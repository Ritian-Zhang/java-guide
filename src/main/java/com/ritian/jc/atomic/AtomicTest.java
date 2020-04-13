package com.ritian.jc.atomic;

/**
 * TODO
 *
 * @author ritian
 * @since 2019/12/10 17:42
 **/
public class AtomicTest {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int RUNNING    = -1 << COUNT_BITS;

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


    public static void main(String[] args) {
        System.out.println(3|4);
        System.out.println(Integer.toBinaryString(4));

        System.out.println(RUNNING);
    }

}
