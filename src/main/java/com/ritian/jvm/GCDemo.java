package com.ritian.jvm;

/**
 * @author ritian.Zhang
 * @date 2019/03/08
 **/
public class GCDemo {

    private  static final int ONE_MB = 1024*1024;

    public static void main(String[] args) {
        System.out.println(1);
        byte[] allocation1, allocation2;
        allocation1 = new byte[8 * ONE_MB];
//        allocation2 = new byte[900*1024];
//        System.gc();
        System.out.println(111);
    }
}
