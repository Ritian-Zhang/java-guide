/**
 * @author ritian.Zhang
 * @date 2019/04/23
 **/
package com.ritian.designpattern.prototype;
/*
* 原型模式
* 定义如下：用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象。
* 原理：Object 类的 clone 方法的 原理是从内存中（具体的说就是堆内存）以二进制流的方式进行拷贝，重新分配一个内存块
*
* 原型模式包含以下主要角色。
*  抽象原型类：规定了具体原型对象必须实现的接口。
* 具体原型类：实现抽象原型类的 clone() 方法，它是可被复制的对象。
* 访问类：使用具体原型类中的 clone() 方法来复制新的对象。
*
* ①浅复制
* 浅复制仅仅复制所考虑的对象，而不复制它所引用的对象。
* ②深复制
* 深复制把要复制的对象所引用的对象都复制了一遍
*
*
*
* */