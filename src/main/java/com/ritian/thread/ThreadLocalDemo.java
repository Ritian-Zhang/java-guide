package com.ritian.thread;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal
 *
 * @author ritian
 * @since 2019/11/12 16:52
 **/
@Slf4j
public class ThreadLocalDemo {

    private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "thread holder");

    public static void main(String[] args) {

        System.out.println(threadLocal.get());
        ThreadLocalDemo demo = new ThreadLocalDemo();
        demo.testRemove();
        while (true) {
            ThreadUtil.sleep(1000);
            System.out.println(threadLocal.get());
        }
    }

    public void testRemove() {
        final ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.submit(() -> {
                threadLocal.set(index + "");
                log.info("in thread str is {}", threadLocal.get());
                ThreadUtil.sleep(1000 * index);
                threadLocal.remove();
            });

        }
    }


}
