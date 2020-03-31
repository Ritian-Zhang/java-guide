package com.ritian.openjdk;

import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

/**
 * TODO
 *
 * @author ritian
 * @since 2019/7/18 8:51
 **/
public class Demo {
    static String str;

    public static void main(String[] args) {

        System.out.println(VM.current().details());

    }
}
