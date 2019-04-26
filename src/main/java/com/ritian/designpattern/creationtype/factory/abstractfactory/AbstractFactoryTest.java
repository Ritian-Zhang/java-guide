package com.ritian.designpattern.creationtype.factory.abstractfactory;

/**
 * 抽象工厂模式
 * <p>
 * 使对象的创建被实现在工厂接口所暴露出来的方法中。
 * </p>
 *
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class AbstractFactoryTest {

    public static void main(String[] args) {
        ChinaDrinksFactory chinaDrinksFactory = new ChinaDrinksFactory();

        AmericanDrinksFactory americanDrinksFactory = new AmericanDrinksFactory();

        Coffee coffee = chinaDrinksFactory.createCoffee();
        Tea tea = chinaDrinksFactory.createTea();
        System.out.println(tea.getName());
    }
}
