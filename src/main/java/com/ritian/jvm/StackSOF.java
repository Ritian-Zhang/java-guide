package com.ritian.jvm;

/**
 * java栈溢出 StackOverFlowError
 * JVM 参数:-Xss 128k
 *
 * @author ritian.Zhang
 * @date 2019/03/08
 **/
public class StackSOF {
    private int stackLength = -1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        // -Xss 128k 940
        //-Xss 108k 951
        StackSOF demo = new StackSOF();
        try {
            demo.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length :" + demo.stackLength);
            e.printStackTrace();
        }

    }
}
