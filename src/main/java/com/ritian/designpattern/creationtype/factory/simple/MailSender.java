package com.ritian.designpattern.creationtype.factory.simple;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class MailSender implements Sender {

    @Override
    public void send() {
        System.out.println("this is mail sender");
    }
}
