package com.ritian.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 强引用 软引用 弱引用 虚引用
 *
 * @author ritian.Zhang
 * @date 2019/03/12
 **/
public class ObjectReference {


    /**
     * 强引用
     * <p>只要某个对象有强引用与之关联，jvm必定不会回收这个对象，即使在内存不足的情况下，jvm宁愿抛出OutOfMemory错误也不会回收这种对象</p>
     * <p>如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，这样一来的话，JVM在合适的时间就会回收该对象。</p>
     */
    private static void strongRef() {
        Object object = new Object();
        String str = "hello";
        //当运行至Object[] objArr = new Object[1000];这句时，如果内存不足，JVM会抛出OOM错误也不会回收object指向的对象。
        Object[] objArr = new Object[1000];

    }

    /**
     * 软饮用
     * <p>软引用是用来描述一些有用但并不是必需的对象，在Java中用java.lang.ref.SoftReference类来表示。对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象。并且这个特性很适合用来实现缓存：比如网页缓存、图片缓存等</p>
     * <p>软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中。</p>
     */
    private static void softRef() {
        SoftReference<String> str = new SoftReference<String>(new String("hello"));
        System.out.println(str.get());
        System.gc();
        System.out.println(str.get());
    }
    private static  void softRef2(){
        ReferenceQueue<String>  queue = new ReferenceQueue<>();
        SoftReference<String> str = new SoftReference<String>(new String("hello"),queue);
        System.out.println(str.get());
        System.gc();
        System.out.println(str.get());
//        System.out.println(queue);
    }

    /**
     * 弱引用
     * <p>弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。在java中，用java.lang.ref.WeakReference类来表示</p>
     */
    private static void weakRef(){
        WeakReference<String> str = new WeakReference<String>(new String("hello"));
        System.out.println(str.get());
        System.gc();
        System.out.println(str.get());
    }

    /**
     * 虚引用
     * <p>虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。在java中用java.lang.ref.PhantomReference类表示。如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。</p>
     */
    private static void phantomRef(){
        ReferenceQueue<String>  queue = new ReferenceQueue<>();
        PhantomReference<String> str = new PhantomReference<>(new String("hello"),queue);
        System.out.println(str.get());

    }


    public static void main(String[] args) {
//        softRef();
        softRef2();
//        weakRef();
//        phantomRef();
    }


}
