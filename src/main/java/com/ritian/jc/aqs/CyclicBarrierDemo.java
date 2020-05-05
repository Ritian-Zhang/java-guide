package com.ritian.jc.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步屏障--->实现让一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被拦截的线程才会继续运行
 * <p>CyclicBarrier在使用一次后，下面依然有效，可以继续当做计数器使用，这是与CountDownLatch的区别之一。
 *
 * @author ritian
 * @since 2020/5/3 23:09
 **/
public class CyclicBarrierDemo {

    private static CyclicBarrier barrier = new CyclicBarrier(6, () -> System.out.println("所有运动员入场，裁判员一声令下！！！！"));

    public static void main(String[] args) {
        System.out.println("运动员准备进场，全场欢呼。。。。。");
        ExecutorService executorService = Executors.newFixedThreadPool(12);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 运动员,进场");
                    barrier.await();
                    System.out.println(barrier.getNumberWaiting());
                    System.out.println(Thread.currentThread().getName() + " 运动员,出发");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

}
