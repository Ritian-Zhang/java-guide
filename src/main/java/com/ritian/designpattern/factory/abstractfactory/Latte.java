package com.ritian.designpattern.factory.abstractfactory;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class Latte extends Coffee{
    @Override
    Coffee createCoffee() {
        return new Latte();
    }
}
