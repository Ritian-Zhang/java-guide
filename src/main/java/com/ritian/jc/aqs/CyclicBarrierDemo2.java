package com.ritian.jc.aqs;

import java.util.Map;
import java.util.concurrent.*;

/**
 * CyclicBarrier应用场景：用于多线程计算数据，最后合并计算结果的场景
 *
 * @author ritian
 * @since 2020/5/5 17:47
 **/
public class CyclicBarrierDemo2 implements Runnable{

    /**
     * 创建4个屏障，处理完之后执行当前类的run方法
     */
    private CyclicBarrier barrier = new CyclicBarrier(4,this);

    /**
     * 假设只有4个sheet，所以只启动4个线程
     */
    private Executor executor = Executors.newFixedThreadPool(4);

    /**
     * 保存每个sheet计算出的银行流水结果
     */
    private ConcurrentHashMap<String,Integer> sheetBankWaterCount = new ConcurrentHashMap<String,Integer>();

    private void count(){
        for (int i = 0; i <4 ; i++) {

            executor.execute(()->{
                //计算当前sheet的银流数据，计算代码省略
                sheetBankWaterCount.put(Thread.currentThread().getName(),1);
                try {
                    //计算完成，插入一个屏障
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

        }
    }


    @Override
    public void run() {
       int total = 0;
        //汇总每个sheet的计算出的结果
        for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            total = total+sheet.getValue();
        }
        sheetBankWaterCount.put("total",total);
        System.out.println(total);
    }

    public static void main(String[] args) {
        CyclicBarrierDemo2 demo = new CyclicBarrierDemo2();
        demo.count();
    }
}
