package com.ritian.jc.aqs;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程间交换数据
 * <p>可以用于遗传算法，也可以用于校对工作
 *
 * @author ritian
 * @since 2020/5/3 23:16
 **/
public class ExchangerDemo {


    private static Exchanger<String> exchanger = new Exchanger<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        //场景：需要将纸制银行流水通过人工的方式录入成电子银行流水，为了避免错误，采用AB岗两人进行录入
        //录入到Excel后，系统需要加载这两个Excel，并对两个Excel数据进行校对，看看是否 录入一致。
        executorService.submit(() -> {
            try {
                String A = "银行流水A";
                exchanger.exchange(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                String B = "银行流水B";
                String A = exchanger.exchange(B);
                System.out.println("A和B的数据是否一致：" + A.equals(B) + ",A录入的是:" + A + ",B录入的是:" + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();

    }


}
