package com.ritian.jc.aqs;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用来控制一个线程等待多个线程。
 *
 * @author ritian
 * @since 2019/7/17 19:29
 **/
@Slf4j
public class CountDownLatchDemo {


    private static CountDownLatch startSignal = new CountDownLatch(1);
    private static CountDownLatch endSignal = new CountDownLatch(6);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 运动员等待裁判员想哨！！！");
                    startSignal.await();
                    System.out.println(Thread.currentThread().getName() + " 正在全力冲刺！！！");
                    System.out.println(Thread.currentThread().getName() + "  到达终点！！！");
                    endSignal.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        ThreadUtil.sleep(2000);
        System.out.println("裁判员发号施令啦！！！");
        startSignal.countDown();
        ////调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        endSignal.await();
        System.out.println("所有运动员到达终点，比赛结束");
        executorService.shutdown();
    }
}
