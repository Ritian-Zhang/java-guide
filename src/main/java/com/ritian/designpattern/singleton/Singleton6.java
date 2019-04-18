package com.ritian.designpattern.singleton;

/**
 * 双重检查（推荐使用）
 * <p>
 * Double-Check概念对于多线程开发者来说不会陌生，如代码中所示，我们进行了两次if (singleton == null)检查，这样就可以保证线程安全了。
 * 这样，实例化代码只用执行一次，后面再次访问时，判断if (singleton == null)，直接return实例化对象。
 * <p>
 * 优点：线程安全；延迟加载；效率较高。
 * </p>
 *
 * @author ritian.Zhang
 * @date 2019/04/14
 **/
public class Singleton6 {

    private static volatile Singleton6 singleton;

    public Singleton6() {
    }

    public static Singleton6 getInstance() {
        if (null == singleton) {
            synchronized (Singleton6.class) {
                if (null == singleton) {
                    singleton = new Singleton6();
                }
            }
        }
        return singleton;
    }
}
