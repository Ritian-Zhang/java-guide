package com.ritian.designpattern.factory.method;

/**
 * 工厂方法模式
 * <p>
 *     更加符合开闭原则。弊端是每次扩展都会增加新的类。
 * </p>
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class FactoryMethodTest {
    public static void main(String[] args) {
        AudiFactory audiFactory = new AudiFactory();
        BenzFactory benzFactory = new BenzFactory();
        Car audi = audiFactory.createCar();
        Car benz = benzFactory.createCar();

        audi.run();
        benz.run();


    }
}
