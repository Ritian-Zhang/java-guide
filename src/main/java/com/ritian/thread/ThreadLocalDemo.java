package com.ritian.thread;

/**
 * ThreadLocal
 *
 * @author ritian
 * @since 2019/11/12 16:52
 **/
public class ThreadLocalDemo {

    private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "thread holder");


    public static void main(String[] args) {
        String s = threadLocal.get();
        System.out.println(s);
    }


}
