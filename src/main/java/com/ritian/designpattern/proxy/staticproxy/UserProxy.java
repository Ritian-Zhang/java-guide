package com.ritian.designpattern.proxy.staticproxy;

/**
 * 代理对象，静态代理
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class UserProxy implements UserDao{

    private UserService target;

    public UserProxy(UserService target) {
        this.target = target;
    }
    @Override
    public void save() {
        System.out.println("开始事务...");
        this.target.save();
        System.out.println("提交事务...");

    }
}
