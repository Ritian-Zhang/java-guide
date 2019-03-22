package com.ritian.jvm;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;

/**
 * @author ritian.Zhang
 * @date 2019/03/12
 **/
public class ClassDemo {

    public static void main(String[] args) {
        System.out.println("测试一下，java查看字节码文件");
        try {
            Class<?> c1 = Class.forName("com.ritian.thread.PriDemo");
            Object o = c1.newInstance();
            Method m = c1.getDeclaredMethod("test");
            m.setAccessible(true);
            m.invoke(o);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
