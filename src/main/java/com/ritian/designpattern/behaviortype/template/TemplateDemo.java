package com.ritian.designpattern.behaviortype.template;

/**
 * 模版方法模式：<ps>父类与子类的关系进行实现</ps>
 * 定义：一个抽象类公开定义了执行它的方法的方式/模板。它的子类可以按需要重写方法实现，但调用将以抽象类中定义的方式进行
 *
 * 主要解决：一些方法通用，却在每一个子类都重新写了这一方法。
 *
 * 何时使用：有一些通用的方法。
 *
 * 如何解决：将这些通用算法抽象出来。
 *
 * 关键代码：在抽象类实现，其他步骤在子类实现。
 *
 * 优点： 1、封装不变部分，扩展可变部分。 2、提取公共代码，便于维护。 3、行为由父类控制，子类实现。
 *
 * 缺点：每一个不同的实现都需要一个子类来实现，导致类的个数增加，使得系统更加庞大。
 *
 * 使用场景： 1、有多个子类共有的方法，且逻辑相同。 2、重要的、复杂的方法，可以考虑作为模板方法。
 *
 * @author ritian.Zhang
 * @date 2019/04/30
 **/
public class TemplateDemo {
    public static void main(String[] args) {
        Game game = new Football();
        game.play();
        game = new Tennis();
        game.play();
    }
}

abstract class Game{
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();

    //模版方法
    public  final void play(){
        initialize();
        startPlay();
        endPlay();
    }
}
class Football extends Game{

    @Override
    void initialize() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {

        System.out.println("Football Game started! enjoy the game.");

    }

    @Override
    void endPlay() {
        System.out.println("Football Game Finished!");
    }
}
class Tennis extends Game{

    @Override
    void initialize() {
        System.out.println("Tennis Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {

        System.out.println("Tennis Game started! enjoy the game.");

    }

    @Override
    void endPlay() {
        System.out.println("Tennis Game Finished!");
    }
}
