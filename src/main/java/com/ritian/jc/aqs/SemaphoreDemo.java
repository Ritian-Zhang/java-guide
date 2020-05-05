package com.ritian.jc.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量--用来控制同时访问特定资源的线程数量
 * <p>通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 *
 * @author ritian
 * @since 2020/5/3 23:16
 **/
public class SemaphoreDemo {


    /**
     * 表示老师只有5只笔
     */
    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        //场景:有一天，班主任需要班上10个同学到讲台上来填写一个表格，但是老师只准备了5支笔，
        // 因此，只能保证同时只有5个同学能够拿到笔并填写表格，没有获取到笔的同学只能够等前面的同学用完之后，才能拿到笔去填写表格
        //表示10个学生
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "  同学准备获取笔");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "  同学获取到笔");
                    System.out.println(Thread.currentThread().getName() + "  同学填写表格ing......");
                    TimeUnit.SECONDS.sleep(3);

                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "  同学填写完表格.归还了笔！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        executorService.shutdown();
    }


}
