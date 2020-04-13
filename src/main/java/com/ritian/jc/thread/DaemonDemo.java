package com.ritian.jc.thread;

/**
 * 守护线程
 *
 * @author ritian
 * @since 2020/4/13 20:02
 **/
public class DaemonDemo {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        });
        thread.setDaemon(true);
        thread.start();

        //运行 main 程序 finally 并没有执行

    }
}
