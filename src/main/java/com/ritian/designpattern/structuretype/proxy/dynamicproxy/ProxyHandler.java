package com.ritian.designpattern.structuretype.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 实现InvocationHandler接口方式
 *
 * @author ritian
 * @since 2020/4/28 10:40
 **/
public class ProxyHandler implements InvocationHandler {

    /**
     * 需求代理的对象
     */
    private Object target;


    public ProxyHandler(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理 开始事务...");
        Object result = method.invoke(target, args);
        System.out.println("动态代理 提交事务...");
        return result;
    }
}
