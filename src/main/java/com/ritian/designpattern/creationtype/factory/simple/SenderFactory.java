package com.ritian.designpattern.creationtype.factory.simple;

/**
 * 简单工厂模式
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class SenderFactory {

    public static Sender createSender(String type){
        if("sms".equals(type)){
            return new SmsSender();
        }
        if("mail".equals(type)){
            return new MailSender();
        }
        return null;
    }

    public static void main(String[] args) {
        Sender sender = SenderFactory.createSender("sms");
        sender.send();
    }
}
