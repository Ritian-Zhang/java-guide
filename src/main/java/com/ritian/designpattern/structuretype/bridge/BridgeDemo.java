package com.ritian.designpattern.structuretype.bridge;

import lombok.extern.slf4j.Slf4j;

/**
 * 桥接模式：是用于把抽象化与实现化解耦，使得二者可以独立变化
 * 优点： 1、抽象和实现的分离。 2、优秀的扩展能力。 3、实现细节对客户透明。
 * 缺点：桥接模式的引入会增加系统的理解与设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计与编程。
 * @author ritian.Zhang
 * @date 2019/04/27
 **/
public class BridgeDemo {
    public static void main(String[] args) {
        Shape redCircle = new Circle(new RedCircle(), 0, 0, 5);
        Shape greenCircle = new Circle(new GreenCircle(), 0, 0, 10);
        redCircle.draw();
        greenCircle.draw();
    }
}

/**
 * 桥接实现接口
 */
interface DrawAPI{
    public void drawCircle(int radius,int x,int y);
}

/**
 * 实现类
 */
@Slf4j
class RedCircle implements DrawAPI{

    @Override
    public void drawCircle(int radius, int x, int y) {
        log.info("Drawing circle,[color:red,radius:{},x:{},y:{}]",radius,x,y);
    }
}
@Slf4j
class GreenCircle implements DrawAPI{

    @Override
    public void drawCircle(int radius, int x, int y) {
        log.info("Drawing circle,[color:green,radius:{},x:{},y:{}]",radius,x,y);
    }
}

/**
 * 使用接口创建抽象类
 */
abstract class Shape{
   public DrawAPI drawAPI;

    public Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    public abstract void draw();
}

/**
 * 实现Shape接口的实现类
 */
class Circle extends Shape{
    private int x,y,radius;

    public Circle(DrawAPI drawAPI, int x, int y, int radius) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
       this.drawAPI.drawCircle(radius,x,y);
    }
}