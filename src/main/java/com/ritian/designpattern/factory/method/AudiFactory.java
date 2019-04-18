package com.ritian.designpattern.factory.method;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class AudiFactory implements CarFactory{
    @Override
    public Car createCar() {
        return  new Audi();
    }
}
