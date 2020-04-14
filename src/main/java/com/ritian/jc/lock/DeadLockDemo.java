package com.ritian.jc.lock;

/**
 * 死锁
 *
 * @author ritian
 * @since 2020/4/2 21:43
 **/
public class DeadLockDemo {

    private static String A = "A";
    private static String B = "B";


    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    /**
     * 演示死锁的场景
     * 可以dump线程查看到底是哪个线程出现了问题。(可以看到 线程的状态是 BLOCKED)
     * 命令： jstack pid > dumpfile
     */
    private void deadLock() {
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    System.out.println("1");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (B) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    System.out.println("2");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
