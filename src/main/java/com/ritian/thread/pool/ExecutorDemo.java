package com.ritian.thread.pool;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.*;

/**
 * Executor Demo
 *
 * @author ritian
 * @since 2019/9/17 17:26
 **/
@Slf4j
public class ExecutorDemo {

    public static void main(String[] args) throws Exception {
        ExecutorDemo executorDemo = new ExecutorDemo();
        System.out.println(getProcessID());

//         executorDemo.createSingleHided();
        //executorDemo.createFixed();
//        executorDemo.createScheduled();
//        executorDemo.createCached();
//        executorDemo.testCorePoolSize();
        executorDemo.testKeepAliveTime();
    }


    private void test() {
        Executors.newFixedThreadPool(1);
    }

    ///////////////////// ** singleThreadPool ** /////////////////////////

    private void createSingleHided() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int index = i;
            Future<?> future = executorService.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "正在被执行，打印的值是:" + index);
//                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.out.println(future.get());
            System.out.println(future.isDone());
        }
    }

    private void createSingleDisplayed() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        // execute
    }

    ///////////////////// ** singleThreadPool ** /////////////////////////

    private void createFixed() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "正在被执行，打印的值是:" + index);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    ///////////////////// ** scheduledThreadPool ** /////////////////////////

    private void createScheduled() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        //延迟2s执行
        scheduledExecutorService.schedule(() -> {
            System.out.println("延迟2秒执行");
        }, 2, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("延迟2秒后每3秒执行一次");
        }, 2, 3, TimeUnit.SECONDS);
//        Thread.sleep(5000);
//        scheduledExecutorService.shutdownNow();
    }

    ///////////////////// ** cachedThreadPool ** /////////////////////////

    private void createCached() {
        //创建一个可缓存线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            try {
                //sleep可明显看到使用的是线程池里面以前的线程，没有创建新的线程
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "正在被执行");
            });
        }
    }

    private void testKeepAliveTime() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1),
                ThreadFactoryBuilder.create().setNamePrefix("demo-").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 6; i++) {
            executor.execute(() -> {
                log.info(">>>>正在执行");
                try {
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getId());
                    log.info(Thread.currentThread().getName() + ":" + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(1000);
        }
    }

    /**
     * 核心线程 -> 阻塞队列 -> maximunPoolSize -> RejectedExecutionHandler
     */
    private void testCorePoolSize() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10),
                ThreadFactoryBuilder.create().setNamePrefix("demo-").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                log.info(">>>正在执行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        log.info("count:{}", executor.getTaskCount());
    }

    /**
     * 获取进程ID
     */
    public static int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
    }


}
