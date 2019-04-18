package com.ritian.designpattern.proxy.staticproxy;

/**
 * 接口实现
 * 目标对象
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class UserService implements UserDao{
    @Override
    public void save() {
        System.out.println("--------已经保存数据啦----------");
    }
}
