package com.ritian.jc.jmm;

import cn.hutool.core.thread.ThreadUtil;

/**
 * synchronized的实现原理与应用
 *
 * @author ritian
 * @since 2020/4/2 22:59
 **/
public class SynchronizedDemo {


    static {
        //锁住的是Class对象
        synchronized (SynchronizedDemo.class) {
            System.out.println("锁住的是这个类的所有对象");
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo demo1 = new SynchronizedDemo();
        SynchronizedDemo demo2 = new SynchronizedDemo();

        Thread thread1 = new Thread(() -> {
            demo1.test1();
        });
        Thread thread2 = new Thread(() -> {
            demo1.test1();
        });

        thread1.start();
        thread2.start();

    }

    private void test1() {
        synchronized (this) {
            ThreadUtil.sleep(1000);
            System.out.println("锁住的是调用这个代码块的对象");
        }
    }

    private synchronized void test2() {
        System.out.println("锁住的是调用这个方法的对象");
    }


}
