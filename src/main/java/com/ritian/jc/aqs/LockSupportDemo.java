package com.ritian.jc.aqs;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport工具类
 *
 * @author ritian
 * @since 2020/4/14 11:05
 **/
public class LockSupportDemo {


    public static void main(String[] args) {
//        LockSupportDemo.testPark();
//        LockSupportDemo.testParkNanos();
//        LockSupportDemo.testParkUntil();
        LockSupportDemo.testUnpark();

    }

    /**
     * 阻塞当前线程，如果调用unpark(Thread thread)方法或者当前线程被中断，才能从park()方法返回
     */
    public static void testPark() {
        LockSupport.park();
        System.out.println("blocked .");
    }

    /**
     * 阻塞当前线程，最长不超过nanos纳秒
     */
    public static void testParkNanos() {
        LockSupport.parkNanos(1000L * 1000);
        System.out.println("park end .");
    }

    /**
     * 阻塞当前线程，直到deadline时间（从1970年开始到deadline时间的毫秒数）
     */
    public static void testParkUntil() {
        LockSupport.parkUntil(1586833481485L);
        System.out.println("park end .");
    }

    /**
     * 唤醒处于阻塞状态的线程thread
     */
    public static void testUnpark() {
        Thread thread = new Thread(() -> {
            System.out.println("park .");
            LockSupport.park();
            System.out.println("unpark after .");
        });
        thread.start();
        ThreadUtil.sleep(2000);
        LockSupport.unpark(thread);
    }
}
