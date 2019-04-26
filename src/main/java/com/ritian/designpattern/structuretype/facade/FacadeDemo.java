package com.ritian.designpattern.structuretype.facade;

/**
 * 外观模式（门面模式）
 * 外观模式（Facade）,它隐藏了系统的复杂性，并向客户端提供了一个可以访问系统的接口.
 * <p>
 *     涉及到3个角色
 *     1.门面角色：外观模式的核心。它被客户角色调用，它熟悉子系统的功能。内部根据客户角色的需求预定了几种功能的组合
 *     2.子系统角色:实现了子系统的功能。它对客户角色和Facade时未知的。它内部可以有系统内的相互交互，也可以由供外界调用的接口。
 *     3.客户角色:通过调用Facede来完成要实现的功能。
 * </p>
 * <a>参考链接：https://www.cnblogs.com/lthIU/p/5860607.html</a>
 * @author ritian.Zhang
 * @date 2019/04/26
 **/
public class FacadeDemo {

    public static void main(String[] args) throws Exception{
        Computer computer = new Computer();
        computer.start();
        System.out.println("==========================================");
        Thread.sleep(2000);
        computer.shutDown();
    }

}

/**
 * 门面类（核心）
 */
class Computer{
     private Cpu cpu;

     private Disk disk;

     private Memory memory;

    public Computer() {
        cpu = new Cpu();
        disk = new Disk();
        memory = new Memory();
    }

    public void start(){
        System.out.println("Computer start begin");
        cpu.start();
        disk.start();
        memory.start();
        System.out.println("Computer start end");
    }

    public void shutDown(){
        System.out.println("Computer shutDown begin");
        cpu.shutDown();
        disk.shutDown();
        memory.shutDown();
        System.out.println("Computer shutDown end");
    }
}

/**
 * cpu 子系统类
 */
class Cpu{
    public void start(){
        System.out.println("CPU is start...");
    }
    public void shutDown(){
        System.out.println("CPU is shutDown...");
    }
}

/**
 * disk 子系统类
 */
class Disk{
    public void start(){
        System.out.println("Disk is start...");
    }
    public void shutDown(){
        System.out.println("Disk is shutDown...");
    }
}

/**
 * memory 子系统类
 */
class Memory{
    public void start(){
        System.out.println("Memory is start...");
    }
    public void shutDown(){
        System.out.println("Memory is shutDown...");
    }
}