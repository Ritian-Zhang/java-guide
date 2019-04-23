package com.ritian.designpattern.adapter;

/**
 * 接口适配器测试
 * 《创建新的抽象类实现旧接口方法》
 *
 * @author ritian.Zhang
 * @date 2019/04/23
 **/
public class InterfaceAdapterTest {
    public static void main(String[] args) {
        TargetInterface target1 = new ImplementClass1();
        TargetInterface target2 = new ImplementClass2();
        target1.method1();
        target1.method2();
        target2.method1();
        target2.method2();
    }
}

/**
 * 接口
 */
interface TargetInterface {
    void method1();

    void method2();

    void method3();

    void method4();

    void method5();
}

/**
 * 抽象类:中间适配器，用于实现默认的接口方法，以至于减少我们适配器的代码量，让代码更加清晰
 */
abstract class AbstractMethodAdapter implements TargetInterface {
    @Override
    public void method1() {
    }

    @Override
    public void method2() {

    }

    @Override
    public void method3() {
    }

    @Override
    public void method4() {
    }

    @Override
    public void method5() {
    }
}

//两个实现类
class ImplementClass1 extends AbstractMethodAdapter {
    @Override
    public void method1() {
        System.out.println("method 1 called");
    }

    @Override
    public void method3() {
        System.out.println("method 3 called");
    }

    @Override
    public void method5() {
        System.out.println("method 5 called");
    }
}

class ImplementClass2 extends AbstractMethodAdapter {
    @Override
    public void method2() {
        System.out.println("method 2 called");
    }

    @Override
    public void method4() {
        System.out.println("method 4 called");
    }
}
