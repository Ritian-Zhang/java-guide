package com.ritian.jc.aqs;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport工具类
 * <p>用来阻塞线程和唤醒线程
 *
 * @author ritian
 * @since 2020/4/14 11:05
 **/
@Slf4j
public class LockSupportDemo {


    public static void main(String[] args) {
//        LockSupportDemo.testPark();
//        LockSupportDemo.testParkNanos();
//        LockSupportDemo.testParkUntil();
        LockSupportDemo.test2();

    }

    public static void test2(){
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);
        System.out.println("a");
        LockSupport.park();
        System.out.println("b");
        LockSupport.park();
        System.out.println("c");
    }

    public static void unpark(Thread thread){
        ThreadUtil.sleep(2000);
        System.out.println("unpark");
//        thread.interrupt();
        LockSupport.unpark(thread);
    }


    /**
     * 阻塞当前线程，如果调用unpark(Thread thread)方法或者当前线程被中断，才能从park()方法返回
     */
    public static void testPark() {
        System.out.println(Thread.currentThread().getName()+" park");
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
