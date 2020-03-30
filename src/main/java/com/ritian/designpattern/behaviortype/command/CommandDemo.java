package com.ritian.designpattern.behaviortype.command;

/**
 * 命令模式:是一种数据驱动的设计模式，请求以命令的形式包裹在对象中，并传给调用对象。
 *          调用对象寻找可以处理该命令的合适的对象，并把该命令传给相应的对象，该对象执行命令。
 *
 *优点：降低了系统的耦合度。新的命令可以很容易的添加到系统中去
 *
 *缺点：使用命令模式可能会导致某些系统有过多的命令类。
 *
 * @author ritian.Zhang
 * @date 2019/05/04
 **/
public class CommandDemo {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        MyCommand cmd = new MyCommand(receiver);
        Invoker invoker = new Invoker(cmd);
        invoker.action();
    }
}

interface  Command{
    public void exe();
}

/**
 * 接收者
 */
class Receiver{
    public void action(){
        System.out.println(" command received ");
    }
}

/**
 * 命令
 */
class MyCommand implements Command{

    private Receiver receiver;

    public MyCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exe() {
      receiver.action();
    }
}

/**
 * 调用者（司令员）
 */
class  Invoker{
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }
    public void action(){
        command.exe();
    }
}