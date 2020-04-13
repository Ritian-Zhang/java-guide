package com.ritian.jc.thread;

/**
 * 线程状态
 * 1.NEW 2.RUNNABLE 3.BLOCKED 4.WATING 5.TIMED_WAITING 6.TERMINATED
 * {@link Thread.State}
 * <a href="https://github.com/DuHouAn/Java-Notes/blob/master/docs/Java/Java%20%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91.md">参考链接</a>
 *
 * @author ritian
 * @since 2019/10/30 13:59
 **/
public class ThreadStateDemo {


    public static void main(String[] args) throws Exception {
//        threadStateDemonStrate();
        waitState();
//        threadOperate();
    }

    private static void threadStateDemonStrate() throws Exception {
        final Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + ":正在占用锁");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":正在释放锁");
            }
        }).start();

        Thread thread = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + ":获取锁");
                System.out.println("*********线程开始执行*********");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("*********线程结束执行*********");
            }
        });
        //NEW 创建后尚未启动
        System.out.println("1:" + thread.getState());
        thread.start();
        //RUNNABLE 调用 start() 方法后开始运行,可能正在运行，也可能正在等待 CPU 时间片
        System.out.println("2:" + thread.getState());
        Thread.sleep(1000);
        //BLOCKED 等待获取一个排它锁，如果其他线程释放了锁就会结束此状态
        System.out.println("3:" + thread.getState());
        Thread.sleep(1500);
        //TIMED_WAITING 无需等待其它线程显式地唤醒，在一定时间之后会被系统自动唤醒
        System.out.println("5:" + thread.getState());
        thread.join();
        //TERMINATED 程结束任务之后自己结束，或者产生了异常而结束
        System.out.println("6:" + thread.getState());
    }

    private static void waitState() throws Exception {
        final Object lock = new Object();
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":开始WAIT");
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + ":WAIT之后");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("1:" + thread.getState());
        thread.start();
        System.out.println("2:" + thread.getState());
        Thread.sleep(1000);
        //WATING 等待其它线程显式地唤醒，否则不会被分配 CPU 时间片
        System.out.println("4:" + thread.getState());
        synchronized (lock) {
            lock.notify();
        }
        Thread.sleep(1000);
        System.out.println("6:" + thread.getState());
        Thread.yield();

    }

    private static void threadOperate() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":$$$$");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        System.out.println(thread.getName() + ":" + thread.getState());
    }


}
