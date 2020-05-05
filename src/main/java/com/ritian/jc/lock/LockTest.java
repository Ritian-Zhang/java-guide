package com.ritian.jc.lock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author ritian
 * @since 2020/4/14 10:08
 **/
public class LockDemo {

    public static void main(String[] args) {
        LockSupport.parkUntil(1586833481485L);
//        ReentrantLock
        System.out.println("block.");
    }
}
