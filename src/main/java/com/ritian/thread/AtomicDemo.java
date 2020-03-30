package com.ritian.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JAVA原子操作类
 *
 * @author ritian
 * @since 2019/10/31 10:39
 **/
public class AtomicDemo {

//    private static AtomicInteger count = new AtomicInteger();

    private static int clientTotal = 500;

    private static int threadTotal = 500;

//    private static int count = 0;

    private static volatile int count  = 0;

    public static void main(String[] args) throws Exception{

        ExecutorService executorService =
                Executors.newCachedThreadPool();

        for(int i=0;i<clientTotal;i++){
            executorService.execute(()->{
                add();
                System.out.println(Thread.currentThread().getName()+":"+count);
            });
        }
        Thread.sleep(5000);
        System.out.println(count);


    }

    private static void add(){
//        count.incrementAndGet();
        count++;
    }

}
