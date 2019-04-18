package com.ritian.designpattern.factory.abstractfactory;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public interface AbstractDrinksFactory {

    Coffee createCoffee();

    Tea createTea();

    Soda createSoda();


}
