package com.ritian.designpattern.creationtype.prototype;

import lombok.ToString;

import java.io.Serializable;

/**
 * 原型模式
 * 如果要克隆，就必须实现克隆接口
 * @author ritian.Zhang
 * @date 2019/04/23
 **/
@ToString
public class Programmer implements Cloneable, Serializable {

    private static final long serialVersionUID = -1306566484862210893L;

    private String name;

    private Major major;

    public Programmer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    @Override
    public  Programmer clone() throws CloneNotSupportedException{
        Programmer proto =(Programmer)super.clone();
        //深度复制 另外一种二进制流
        // proto.major = (Major) major.clone();
        return proto;
    }
}
