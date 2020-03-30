package com.ritian.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author ritian
 * @since 2019/12/12 9:15
 **/
@Slf4j
public class ThreadPoolDemo {

    public  static int COUNT = 0;

    public  final  static  ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 3000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("new-"+(COUNT++));
            return thread;
        }
    });

    public static void main(String[] args) {

        executor.prestartAllCoreThreads();
//        for (int i=0;i<10;i++){
//            executor.submit(()->{
//                System.out.println(Thread.currentThread().getName());
//            });
//        }

        System.out.println( executor.getTaskCount());

    }
}
