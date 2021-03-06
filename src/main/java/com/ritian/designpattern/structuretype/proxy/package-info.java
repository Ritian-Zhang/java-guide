/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
package com.ritian.designpattern.structuretype.proxy;
/*
* 代理模式
* 代理(Proxy)是一种设计模式,提供了对目标对象另外的访问方式;即通过代理对象访问目标对象.
*
* 优点：可以在目标对象实现的基础上,增强额外的功能操作,即扩展目标对象的功能.
*
* 1.静态代理             代理对象与目标对象要实现相同的接口,然后通过调用相同的方法来调用目标对象的方法
* 2.动态代理（JDK代理）  代理对象不需要实现接口,但是目标对象一定要实现接口
* 3.cglib <Code Generator Library> (子类代理)  目标是单独的对象，没有实现任何接口
*
*
* */
