package com.ritian.thread.aqs;

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

    private static int threadCount = 10;

    public static void main(String[] args) throws InterruptedException{

        ExecutorService executorService = Executors.newCachedThreadPool();

       final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

       for(int i = 0;i<threadCount;i++){
           int threadNum = i;
           executorService.execute(()->{
               try {
                   test(threadNum);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   countDownLatch.countDown();
               }
           });
       }
        countDownLatch.await();
        //上面的所有线程都执行完了,再执行主线程
        System.out.println("Finished!");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(1000);
        log.info("run: Thread "+threadNum);
        Thread.sleep(1000);
    }
}
