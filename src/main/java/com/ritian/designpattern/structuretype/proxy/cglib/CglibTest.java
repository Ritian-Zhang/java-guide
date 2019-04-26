package com.ritian.designpattern.structuretype.proxy.cglib;

/**
 * cglib 测试
 * @author ritian.Zhang
 * @date 2019/04/17
 **/
public class CglibTest {
    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();
        //代理对象
        UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
        //执行代理对象的方法
        proxy.save();
    }
}
