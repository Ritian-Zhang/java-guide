package com.ritian.thread.jmm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author ritian
 * @since 2019/11/7 16:54
 **/
@Slf4j
public class VolatileDemo {

    private volatile int count = 0;


    public static void main(String[] args) throws Exception{
        new VolatileDemo().test();
    }

    public void test() throws Exception{
        ExecutorService executorService =
                Executors.newCachedThreadPool();

        for (int i = 1; i < 101; i++) {
            final int index = i;
            executorService.submit(() -> {
//                log.info(index + ":1count:" + count);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info(index + ":2count:" + count);
                count++;
                log.info(index + ":3count:" + count);
            });
        }

        Thread.sleep(4000);
        System.out.println(count);


    }

}
