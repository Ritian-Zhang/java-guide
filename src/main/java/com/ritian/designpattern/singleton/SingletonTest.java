package com.ritian.designpattern.singleton;

/**
 * singleton 测试
 *
 * @author ritian.Zhang
 * @date 2029/04/24
 **/
public class SingletonTest {

    public static void main(String[] args) {
        new Thread(() -> {
            Singleton2 instance = Singleton2.getInstance();
            System.out.println(instance.hashCode());
        }).start();

        new Thread(() -> {
            Singleton2 instance = Singleton2.getInstance();
            System.out.println(instance.hashCode());
        }).start();

    }
}
