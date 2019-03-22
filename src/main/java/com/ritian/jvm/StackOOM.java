package com.ritian.jvm;

/**
 * java栈溢出 OutOfMemoryError
 * JVM参数 ： -Xss2m
 * @author ritian.Zhang
 * @date 2019/03/08
 **/
public class StackOOM {

    public void dontStop(){
       while (true){

       }
    }
    //通过不断的创建新的线程使stack内存耗尽
    public void stackLeakByThread(){
        while (true){
           Thread thread = new Thread(()->dontStop()) ;
            thread.start();
        }
    }

    public static void main(String[] args) {
        StackOOM oom = new StackOOM();
        oom.stackLeakByThread();
    }

}
