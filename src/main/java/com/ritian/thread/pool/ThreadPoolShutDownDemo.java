package com.ritian.thread.pool;


import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池shutdown demo
 *
 * <p>
 *     1.shutdownNow 线程池拒接收新提交的任务,同时立马关闭线程池,线程池里的任务不再执行
 *     2.shutdown 线程池拒接收新提交的任务，同时等待线程池里的任务执行完毕后关闭线程池
 * </p>
 *
 * @author ritian
 * @since 2020/3/24 10:46
 **/
@Slf4j
public class ThreadPoolShutDownDemo {


    public static void main(String[] args) {

        ThreadFactory namedFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%s").build();
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10,
//                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),namedFactory
//
//        );

        ExecutorService executor = Executors.newCachedThreadPool(namedFactory);
        testShutDown(executor);

//        testShutdownNow();
    }


    public static   void testShutDown(ExecutorService executor){
        for (int i = 0; i <10 ; i++) {
            executor.submit(() -> log.info("demo"));
        }
        while (true) {
            log.info("executor is shutdown now : {}", executor.isTerminated());
            ThreadUtil.sleep(2000);
        }
    }

    public static void testShutdownNow(){

        ExecutorService executor = Executors.newCachedThreadPool();

        executor.submit(()->{
            try {
                log.info("开始执行任务");
                Thread.sleep(2000);
                log.info("执行任务结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.shutdownNow();


    }


}
