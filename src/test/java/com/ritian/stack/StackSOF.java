package com.ritian.stack;

import org.junit.Test;

/**
 * java栈溢出 StackOverFlowError
 * JVM 参数:-Xss 128k
 * @author ritian.Zhang
 * @date 2019/03/08
 **/
public class StackSOF {

    private  int stackLength = -1;

    public void stackLeak(){
        stackLength ++;
        stackLeak();
    }

    @Test
    public void test(){
        // -Xss 128k 940
        //-Xss 108k 951
        StackSOF demo = new StackSOF();
        try {
            demo.stackLeak();
        } catch (Exception  e) {
            System.out.println("stack length :"+demo.stackLength);
            e.printStackTrace();
        }

    }
}
