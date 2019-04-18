package com.ritian.designpattern.factory.abstractfactory;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class AmericanDrinksFactory implements AbstractDrinksFactory{

    @Override
    public Coffee createCoffee() {
        return new Latte();
    }

    @Override
    public Tea createTea() {
        return new MilkTea();
    }

    @Override
    public Soda createSoda() {
        return null;
    }
}
