package com.ritian.designpattern.adapter;

/**
 * <p>
 *     适配器主要包含以下3部分
 * 目标（Target）接口：当前系统业务所期待的接口，它可以是抽象类或接口。
 * 适配者（Adaptee）类：它是被访问和适配的现存组件库中的组件接口。
 * 适配器（Adapter）类：它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。
 * </p>
 *  类适配器测试
 *  @author ritian.Zhang
 *  @date 2019/04/23
 */
public class ClassAdapterTest{
    public static void main(String[] args) {
        Target target = new ClassAdapter();
        target.request();
    }
}
interface Target {
    //主要接口
    public void request();
}
//适配者
class Adaptee{

    public void specialRequest(){
        System.out.println("适配者中业务代码被调用。。");
    }
}
//类适配器
class ClassAdapter extends Adaptee implements Target{

    @Override
    public void request() {
        specialRequest();
    }
}

