package com.ritian.thread.jmm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS
 *
 * @author ritian
 * @since 2020/4/4 17:03
 **/
public class CASDemo {

    private AtomicInteger atomicI = new AtomicInteger(0);


    private int i = 0;

    public static void main(String[] args) {

        CASDemo cas = new CASDemo();

        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();

        for (int j = 0; j < 100; j++) {

            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    cas.count();
                    cas.safeCount();
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }

        //等待所有线程执行完成
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("count i = " + cas.i);
        System.out.println("safeCount i = " + cas.atomicI.get());
        System.out.println("costs: " + (System.currentTimeMillis() - start));
    }

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        i++;
    }
}
