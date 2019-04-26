package com.ritian.designpattern.structuretype.proxy.dynamicproxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理对象
 * 代理对象不需要实现接口,但是目标对象一定要实现接口
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class ProxyFactory {

    /**
     *   维护一个目标对象
     */
    private  Object target;
    public ProxyFactory(Object target) {
        this.target = target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance(){
        // classLoader  interfaces invocationHandler
        Object object = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (Object proxy, Method method, Object[] args) -> {
            System.out.println("动态代理 开始事务...");
            //执行目标对象方法
            Object returnValue = method.invoke(target, args);
            System.out.println("动态代理 提交事务...");
            return returnValue;
        });
        return object;
    }
}
