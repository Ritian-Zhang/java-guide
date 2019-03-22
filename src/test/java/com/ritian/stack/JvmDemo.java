package com.ritian.stack;

import org.junit.Test;

/**
 * @author ritian.Zhang
 * @date 2019/03/08
 **/
public class JvmDemo {

    @Test
    public void test() {
        String s1 = new String("计算机");
        String s2 = s1.intern();
        String s3 = "计算机";

        System.out.println(s2);

        System.out.println(s1 == s2);
        System.out.println(s3 == s2);
    }

    @Test
    public void test2() {
        Integer i1 = 33;
        Integer i2 = 33;
        System.out.println(i1 == i2);

        Integer i3 = 333;
        Integer i4 = 333;
        System.out.println(i3 == i4);

        Double d1 = 1.2;
        Double d2 = 1.2;
        System.out.println(d1 == d2);

        Boolean b1 = true;
        Boolean b2 = true;

        System.out.println(b1 == b2);

        Character c1 = 'a';
        Character c2 = 'a';
        System.out.println(c1 == c2);

    }
}
