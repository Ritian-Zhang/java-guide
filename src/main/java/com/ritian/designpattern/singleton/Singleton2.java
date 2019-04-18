package com.ritian.designpattern.singleton;

/**
 * 饿汉式（静态代码块）
 * @author ritian.Zhang
 * @date 2019/04/14
 **/
public class Singleton2 {

    private static Singleton2 instance;

    private Singleton2() {
    }

    static {
        instance = new Singleton2();
    }

    public static  Singleton2 getInstance(){
        return instance;
    }



}
