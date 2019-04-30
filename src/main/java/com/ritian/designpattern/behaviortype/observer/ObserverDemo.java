package com.ritian.designpattern.behaviortype.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 观察者模式：当对象间存在一对多关系时，则使用观察者模式（Observer Pattern）。比如，当一个对象被修改时，则会自动通知它的依赖对象。
 * <p>
 * 意图：定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 * <p>
 * 主要解决：一个对象状态改变给其他对象通知的问题，而且要考虑到易用和低耦合，保证高度的协作。
 * <p>
 * 何时使用：一个对象（目标对象）的状态发生改变，所有的依赖对象（观察者对象）都将得到通知，进行广播通知。
 * <p>
 * 如何解决：使用面向对象技术，可以将这种依赖关系弱化。
 * <p>
 * 关键代码：在抽象类里有一个 ArrayList 存放观察者们。
 * <p>
 * 优点： 1、观察者和被观察者是抽象耦合的。 2、建立一套触发机制。
 * <p>
 * 缺点： 1、如果一个被观察者对象有很多的直接和间接的观察者的话，将所有的观察者都通知到会花费很多时间。
 * 2、如果在观察者和观察目标之间有循环依赖的话，观察目标会触发它们之间进行循环调用，可能导致系统崩溃。
 * 3、观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。
 *
 * @author ritian.Zhang
 * @date 2019/04/30
 **/
public class ObserverDemo {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        subject.add(new Observer1());
        subject.add(new Observer2());
        subject.doSomething();
    }
}

/**
 * 被观察者
 */
abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void add(Observer observer) {
        observers.add(observer);
    }

    public void del(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObserver() {
        observers.forEach(v -> v.update());
    }

    public abstract void doSomething();
}

/**
 * 具体的被观察者
 */
class ConcreteSubject extends Subject {
    @Override
    public void doSomething() {
        System.out.println("被观察者事件发生");
        this.notifyObserver();
    }
}

/**
 * 观察者
 */
interface Observer {
    public void update();
}

class Observer1 implements Observer {

    @Override
    public void update() {
        System.out.println("观察者1收到信息，并进行处理。");
    }
}

class Observer2 implements Observer {
    @Override
    public void update() {
        System.out.println("观察者2收到信息，并进行处理。");
    }
}
