package com.ritian.jc.thread.threadlocal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ThreadLocal 简单demo
 *
 * @author ritian
 * @since 2020/5/21 12:23
 **/
public class ThreadLocalDemo {

    /**
     * 初始化
     */
    private static ThreadLocal<GirlFriend> gfThreadLocal = ThreadLocal.withInitial(() -> new GirlFriend("xx老湿", "D"));


    public static void main(String[] args) throws Exception{
        System.out.println("有一天，小明和小李同时来到好朋友老王家,同时看到了" +gfThreadLocal.get());
        new Thread(new Programmer("程序员小明", "A")).start();
        new Thread(new Programmer("程序员小李", "G")).start();
        Thread.sleep(3000);
        System.out.println("3秒后，老王回过神，看着"+gfThreadLocal.get().toString()+",还好还好，it's my style");

    }

    /**
     * 程序员
     */
    @Data
    @AllArgsConstructor
    static class Programmer implements Runnable {

        private String name;

        private String likedCup;

        @Override
        public void run() {
            changeCup();
            System.out.println(name + "看着" + gfThreadLocal.get().toString() + "，露出了满意的笑容>>>然后做些羞羞的事");
        }

        private void changeCup() {
            GirlFriend girlFriend = gfThreadLocal.get();
            System.out.println(name + "趁着老板打盹，快速拷贝制作了一个" + girlFriend.toString() + "，但是不符合他的style呀");
            girlFriend.setCup(this.likedCup);
            gfThreadLocal.set(girlFriend);
            System.out.println(name + "  经过一番xxxx改造");
        }
    }

    @Data
    @AllArgsConstructor
    static class GirlFriend {

        private String name;

        /**
         * 罩杯
         */
        private String cup;

        @Override
        public String toString() {
            return cup + " cup的" + name;
        }
    }


}
