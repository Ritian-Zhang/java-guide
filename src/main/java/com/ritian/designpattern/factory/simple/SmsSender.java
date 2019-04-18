package com.ritian.designpattern.factory.simple;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class SmsSender implements Sender{

    @Override
    public void send() {
        System.out.println("this is sms sender");
    }
}
