package com.ritian.operation;

/**
 * java中常忽略的运算符
 * <p>
 * << 、 >>    <<<、>>>  <、>  ||、|      && 、 &    ^
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
     *       例如 ： 2 ^ 3  表示2和3的二进制位运算
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

    /**
     * <pre>
     *        >>> 位运算之无符号右移：忽略符号位，空位都以0补齐
     *       例如 ： 16 >>> 2  表示16二进制向右移2位等于4
     *
     *             00010000  ====》00000100 = 4
     *
     *               -16 >>> 2 表示
     *             16的源码按位取反+1得到补码
     *             11111111111111111111111111110000 ====》00111111111111111111111111111100
     *
     *  </pre>
     */
    public static int rightUnsigned(int rs, int wc) {
        return rs >>> wc;
    }

    /**
     *  >> 位运算之向右位移
     *  低位溢出截断，高位补符号位
     *  <p>
     *      m>>n即相当于m除以2的n次方，得到的为整数时，即为结果。如果结果为小数，此时会出现两种情况：
     *       1.如果m为正数，得到的商会无条件 的舍弃小数位；
     *       2.如果m为负数，舍弃小数部分，然后把整数部分加+1得到位移后的值。
     *  </p>
     */
    public static int right(int rs,int wc){
        return rs >> wc;
    }


    public static void main(String[] args) {

        System.out.println(rightUnsigned(-16, 16));
        System.out.println(binary2decimal("00000000000000001111111111111111"));

        System.out.println(right(-2, 2));

    }

    static String toBinaryString(int num) {
        return Integer.toBinaryString(num);
    }

    static int binary2decimal(String num) {
        return Integer.parseInt(num, 2);
    }

}
