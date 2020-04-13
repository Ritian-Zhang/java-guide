package com.ritian.jc;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 实现处理线程的返回值:
 * 1.主线程等待法
 * 2.使用 Thread 的 join() 阻塞当前线程以等待子线程处理完毕
 * 3.通过 Callable 接口实现：通过 FutureTask
 *
 * @author ritian
 * @since 2019/10/30 11:18
 **/
public class ThreadReturnValueDemo {

    public static void main(String[] args) throws Exception {
        // mainThreadWait();
//        threadJoin();
        callable();
    }

    /**
     * 1.主线程等待法
     */
    private static void mainThreadWait() throws Exception {
        CycleWait cycleWait = new CycleWait();
        new Thread(cycleWait).start();
        while (cycleWait.value == null) {
            Thread.sleep(1000);
        }
        System.out.println(cycleWait.value);

    }

    /**
     * 2.使用 Thread 的 join() 阻塞当前线程以等待子线程处理完毕
     */
    private static void threadJoin() throws Exception {
        CycleWait cycleWait = new CycleWait();
        Thread t = new Thread(cycleWait);
        t.start();
        /**  join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
         程序在main线程中调用t线程的join方法，则main线程放弃cpu控制权，并返回t线程继续执行直到线程t执行完毕
         所以结果是t线程执行完后，才到主线程执行，相当于在main线程中同步t线程，t执行完了，main线程才有执行的机会
         */
        t.join();
        System.out.println(cycleWait.value);
    }

    private static  void callable() throws Exception{
        CycleWait2 cw = new CycleWait2();
        FutureTask<String> ft = new FutureTask<>(cw);
        new Thread(ft).start();
        System.out.println(ft.get());


    }

    static class CycleWait implements Runnable {
        private String value;

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value = "we hava data now";
        }
    }

    static class CycleWait2 implements Callable<String>{
        private String value;
        @Override
        public String call()  {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.value = "we have data now";
            return value;
        }
    }
}
