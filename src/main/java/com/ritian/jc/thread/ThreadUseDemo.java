package com.ritian.jc.thread;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 三种使用线程方法
 *
 * @author ritian
 * @date 2019/04/23
 **/
@Slf4j
public class ThreadUseDemo {


    public static void main(String[] args) throws Exception {
        ThreadUseDemo demo = new ThreadUseDemo();
        // demo.threadTest();
        demo.callableTest();
    }

    public void test() {
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }

    public void threadTest() throws Exception {
        new MyThread().start();
        new Thread(new MyRunnable()).start();
        this.callableTest();
    }

    public void callableTest() throws Exception {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> ft = new FutureTask<>(myCallable);
        new Thread(ft).start();
        System.out.println(Thread.currentThread().getName() + ":" + ft.get());
    }

}

/**
 * 继承Thread类
 */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":我是继承thread");
    }
}

/**
 * 实现runnable接口
 */
class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":我是实现runnable");
    }
}

/**
 * 实现Callable接口
 * <p>与实现runnable相比，其可以有返回值
 */
class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return Thread.currentThread().getName() + ":我是实现callable";
    }
}
