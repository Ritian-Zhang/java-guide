package com.ritian.designpattern.structuretype.adapter;

/**
 * 对象适配测试
 * @author ritian.Zhang
 * @date 2019/04/23
 **/
public class ObjectAdapterTest {
    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target target = new ObjectAdapter(adaptee);
        target.request();
    }
}

/**
 * 对象适配（创建新类持源类实例并实现新接口）
 */
class ObjectAdapter implements Target{
    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        adaptee.specialRequest();
    }
}
