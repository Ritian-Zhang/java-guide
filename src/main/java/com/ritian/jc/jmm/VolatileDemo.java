package com.ritian.jc.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * volatile 关键字
 * 保证了共享变量的 "可见性"
 * <br>volatile的两条实现原则
 *       1.Lock前缀指令会引起处理器缓存回写到内存
 *       2.一个处理器的缓存回写到内存会导致其它处理器的缓存无效
 *  so 当处理器对这个数据进行修改操作的时猴，会重新从系统内存读到处理器缓存
 * @author ritian
 * @since 2019/11/7 16:54
 **/
@Slf4j
public class VolatileDemo {

    private volatile int count = 0;


    public static void main(String[] args) {
        new VolatileDemo().test();
    }

    public void test() {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            System.out.println("线程：" + Thread.currentThread().getName() + " 更改count后的值：" + count);
        });

        Thread t2 = new Thread(() -> {
            System.out.println("线程：" + Thread.currentThread().getName() + " 获取到的count的值：" + count);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + " 获取count的值：" + count);

        });

        t1.start();
        t2.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最终 count的值为：" + count);

    }

}
