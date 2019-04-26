package com.ritian.designpattern.creationtype.factory.method;

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
