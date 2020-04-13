package com.ritian.jc.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池的状态
 *
 *   RUNNING:  Accept new tasks and process queued tasks
 *             接受新的任务，处理等待队列中的任务。线程池的初始化状态是RUNNING。
 *             线程池被一旦被创建，就处于RUNNING状态，并且线程池中的任务数为0
 *
 *   SHUTDOWN: Don't accept new tasks, but process queued tasks
 *             不接受新的任务提交，但是会继续处理等待队列中的任务。
 *             调用线程池的shutdown()方法时，线程池由RUNNING -> SHUTDOWN
 *
 *   STOP:     Don't accept new tasks, don't process queued tasks,
 *             and interrupt in-progress tasks
 *             不接受新的任务提交，不再处理等待队列中的任务，中断正在执行任务的线程。
 *             调用线程池的shutdownNow()方法时，线程池由(RUNNING or SHUTDOWN ) -> STOP
 *
 *   TIDYING:  All tasks have terminated, workerCount is zero,
 *             the thread transitioning to state TIDYING
 *             will run the terminated() hook method
 *             所有的任务都销毁了，workCount 为 0，线程池的状态在转换为 TIDYING 状态时，会执行钩子方法 terminated()。
 *             1.当线程池在SHUTDOWN状态下，阻塞队列为空并且线程池中执行的任务也为空时，就会由 SHUTDOWN -> TIDYING。
 *             2.当线程池在STOP状态下，线程池中执行的任务为空时，就会由STOP -> TIDYING。
 *
 *   TERMINATED: terminated() has completed
 *               线程池处在TIDYING状态时，执行完terminated()之后，就会由 TIDYING -> TERMINATED。
 *
 *   {@link java.util.concurrent.ThreadPoolExecutor}
 *
 * @author ritian
 * @since 2020/3/24 13:58
 **/
@Slf4j
public class ThreadPoolStateDemo {

    public static void main(String[] args) {
        ThreadFactory namedFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%s").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 10, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000),namedFactory

        );
        for (int i = 0; i <100 ; i++) {
            final int index = i;
            executor.submit(()->{
                log.info("i:{}",index);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
        while (true){
            log.info("当前排队线程数:{}",executor.getQueue().size());
            log.info("当前活动线程数:{}",executor.getActiveCount());
            log.info("执行完成线程数:{}",executor.getCompletedTaskCount());
            log.info("总线程数:{}",executor.getTaskCount());
            System.out.println("\r\n");
            System.out.println("\r\n");
            System.out.println("\r\n");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
