package com.ritian.designpattern.behaviortype.strategy;

/**
 * 策略模式 :实际上是通过父类与子类的关系进行实现
 * 主要解决：在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。
 *
 * 何时使用：一个系统有许多许多类，而区分它们的只是他们直接的行为。
 *
 * 如何解决：将这些算法封装成一个一个的类，任意地替换。
 * 优点： 1、算法可以自由切换。 2、避免使用多重条件判断。 3、扩展性良好。
 *
 * 缺点： 1、策略类会增多。 2、所有策略类都需要对外暴露。
 *
 * 使用场景： 1、如果在一个系统里面有许多类，它们之间的区别仅在于它们的行为，那么使用策略模式可以动态地让一个对象在许多行为中选择一种行为。
 *            2、一个系统需要动态地在几种算法中选择一种。 3、如果一个对象有很多的行为，如果不用恰当的模式，这些行为就只好使用多重的条件选择语句来实现。
 * @author ritian.Zhang
 * @date 2019/04/30
 **/
public class StrategyDemo {

    public static void main(String[] args) {
        Context context = new Context(new Add());
        System.out.println("10+5="+context.excute(10,5));
        Context context2 = new Context(new Mutiply());
        System.out.println("10*5="+context2.excute(10,5));
    }

}
interface Strategy{
    public int doOperate(int num1,int num2);
}
class Add implements Strategy{
    @Override
    public int doOperate(int num1, int num2) {
        return num1+num2;
    }
}
class Minus implements Strategy{

    @Override
    public int doOperate(int num1, int num2) {
        return num1-num2;
    }
}
class Mutiply implements Strategy{

    @Override
    public int doOperate(int num1, int num2) {
        return num1*num2;
    }
}

/**
 * 使用了某种策略的类
 */
class Context{
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    public int excute(int num1,int num2){
        return this.strategy.doOperate(num1,num2);
    }
}
