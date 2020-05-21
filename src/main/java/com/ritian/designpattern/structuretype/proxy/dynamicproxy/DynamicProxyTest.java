package com.ritian.designpattern.structuretype.proxy.dynamicproxy;

import com.ritian.designpattern.structuretype.proxy.staticproxy.UserDao;
import com.ritian.designpattern.structuretype.proxy.staticproxy.UserService;

/**
 * 动态代理测试
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class DynamicProxyTest {

    public static void main(String[] args) {
        UserDao target = new UserService();
//        UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
//        proxy.save();


        /////////2/////////
        UserDao proxy = (UserDao) new ProxyHandler(target).getProxyInstance();
        proxy.save();
    }
}
