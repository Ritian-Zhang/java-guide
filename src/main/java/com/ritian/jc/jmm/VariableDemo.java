package com.ritian.jc.jmm;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author ritian
 * @since 2019/11/12 14:35
 **/
public class VariableDemo {

    public static void main(String[] args) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();

        Student student = new Student();
        student.setName("main");
        System.out.println(student.hashCode());
        student.setName("main2");
        System.out.println(student.hashCode());

        for (int i =0;i<10;i++){
            final int index = i;
            executorService.submit(()->{
                System.out.println(index+":"+student.hashCode()+":"+student.getName());
                student.setName(index+"");
            });
        }

        Thread.sleep(1000);
        System.out.println(student.getName());


    }

}
@Data
class Student{
    private String name;
}
