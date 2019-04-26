package com.ritian.designpattern.structuretype.proxy.cglib;

/**
 * 目标对象，没有实现任何接口
 * @author ritian.Zhang
 * @date 2019/04/17
 **/
public class UserDao {
    public void save(){
        System.out.println("---------已经保存数据啦----------");
    }
}
