package com.ritian.designpattern.structuretype.proxy.staticproxy;

/**
 * 静态代理测试
 * <p>
 * 1.可以做到在不修改目标对象的功能前提下,对目标功能扩展.
 * 2.缺点:
 *  因为代理对象需要与目标对象实现一样的接口,所以会有很多代理类,类太多.同时,一旦接口增加方法,目标对象与代理对象都要维护.
 * </p>
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class StaticProxyTest {

    public static void main(String[] args) {
        //目标对象
        UserService userService = new UserService();
        UserProxy proxy = new UserProxy(userService);
        //代理对象,把目标对象传给代理对象,建立代理关系
        proxy.save();//执行的是代理的方法

    }
}
