package com.ritian.designpattern.creationtype.factory.simple;

/**
 * 利用反射Class.forName(clz.getName()).newInstance()实现的简单工厂
 *
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
public class SenderFactory2 {

    public static <T extends Sender> T send(Class<T> clz) {
        T result = null;
        try {
            result = (T) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        SmsSender smsSender = send(SmsSender.class);
        smsSender.send();
    }
}
