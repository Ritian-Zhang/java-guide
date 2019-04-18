package com.ritian.designpattern.factory.abstractfactory;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class ChinaDrinksFactory implements AbstractDrinksFactory{
    @Override
    public Coffee createCoffee() {
        return new Latte();
    }

    @Override
    public Tea createTea() {
        return new RedTea();
    }

    @Override
    public Soda createSoda() {
        return null;
    }
}
