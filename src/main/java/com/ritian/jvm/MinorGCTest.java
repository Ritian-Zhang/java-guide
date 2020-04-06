package com.ritian.jvm;

/**
 * VM args<p> -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC</p>
 *
 * @author ritian.Zhang
 * @date 2019/03/10
 **/
public class MinorGCTest {

    private final static int ONE_MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[2 * ONE_MB];
        allocation2 = new byte[2 * ONE_MB];
        allocation3 = new byte[2 * ONE_MB];
        allocation4 = new byte[5 * ONE_MB];

    }

    /**
     * 大对象直接进入老年代
     * VM args: -XX:PretenureSizeThreshold=3145728 表示所占用内存大于该值的对象直接分配到老年代，3145728为3MB
     */
    public static void testAllocation2() {
        byte[] allocation;
        allocation = new byte[4 * ONE_MB];
    }

    /**
     * 长期存活对象进入老年代
     * VM args: -XX:MaxTenuringThreshold=1 表示对象晋升为老年代的年龄阀值为1，对象在Survivor中每熬过一次MinorGC年龄增加1
     */
    public static void testAllocation3(){
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[ONE_MB / 4];
        allocation2 = new byte[4 * ONE_MB];
        allocation3 = new byte[4 * ONE_MB];
        allocation3 = null;
        allocation3 = new byte[4 * ONE_MB];
    }

    public static void main(String[] args) {
        // 1.对象优先在eden区分配
        // 2.大对象直接进入老年代
        // 3.长期存活的对象进入老年代
         testAllocation();
//        testAllocation2();
    }
}
