package com.ritian.designpattern.creationtype.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author ritian.Zhang
 * @date 2019/04/23
 **/
public class PrototypeTest {
    public static void main(String[] args) throws Exception {
//        easyClone();
        deepClone();

    }

    /**
     * 浅复制
     * 当被克隆的类中有引用对象（String或Integer等包装类型除外）时，克隆出来的类中的引用变量存储的还是之前的内存地址，
     * 也就是说克隆与被克隆的对象是同一个。这样的话两个对象共享了一个私有变量，所有人都可以改，是一个种非常不安全的方式
     */
    public static void easyClone() throws Exception {
        Programmer p = new Programmer("jack");
        p.setMajor(new Major("java"));
        Programmer a = p.clone();
        a.getMajor().setName("python");
        System.out.println("原对象：" + p.toString());
        System.out.println("拷贝对象：" + a.toString());
    }

    /**
     * 深复制
     * 二进制的写法，需要类序列化
     * 把要复制的对象所引用的对象都复制了一遍
     * @return
     * @throws Exception
     */
    public static Programmer deepClone() throws Exception{
        Programmer p = new Programmer("jack");
        p.setMajor(new Major("java"));
        /* 写入当前对象的二进制流 */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(p);

        /*读出二进制流产生的新对象*/
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Programmer copy = (Programmer) ois.readObject();
        copy.getMajor().setName("python");
        System.out.println("原对象：" + p.toString());
        System.out.println("拷贝对象：" + copy.toString());
        return copy;


    }
}
