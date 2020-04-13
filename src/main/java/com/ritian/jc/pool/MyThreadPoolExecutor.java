package com.ritian.jc.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 *
 * @author ritian
 * @since 2019/12/12 9:16
 **/
public class MyThreadPoolExecutor {

    //ctl 记录了“线程池中的任务数量”和“线程池的状态”两个信息。共32位，其中高3位表示”线程池状态”，低29位表示”线程池中的任务数量”。
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }


    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters.
     *
     * @param corePoolSize    线程池中核心线程数量。当提交一个任务时，线程池会创建一个线程来执行任务。直到当前
     *                        线程数等于corePoolSize.如果调用了线程池的preStartAllCoreThreads()方法,线程池会提
     *                        前创建并启动所有基本线程.
     *                        <p>对于CPU密集型任务（N cpu）+1 .对于IO密集型任务,由于需要等待IO操作，线程并不是
     *                        一直在执行任务，则配置尽可能多的线程 (2N cpu)+1
     *                        </p>
     * @param maximumPoolSize 线程池中允许的最大线程数。线程池的阻塞队列满了之后,如果还有任务提交,如果当前
     *                        线程数小于 maximumPoolSize,则会新建线程来执行任务。
     * @param keepAliveTime   线程的创建和销毁是需要代价的。线程执行完任务后不会立即销毁，而是继续存活一段时间：keepAliveTime。
     *                        非核心线程能够空闲的最长时间，超过时间，线程终止。这个参数默认只有在线程数量超过
     *                        核心线程池大小时才会起作用。只要线程数量不超过核心线程大小，就不会起作用。
     * @param unit            keepAliveTime的单位
     * @param workQueue       用来保存等待执行的任务的阻塞队列，等待的任务必须实现Runnable接口。例：
     *                        1.ArrayBlockingQueue
     *                        2.LinkedBlockingQueue
     *                        3.SynchronousQueue
     *                        4.PriorityBlockingQueue
     * @param threadFactory   用于设置创建线程的工厂 默认{@link java.util.concurrent.Executors.DefaultThreadFactory}
     * @param handler         拒绝处理策略，线程数量大于最大线程数就会采用拒绝处理策略,四种策略为：
     *                        {@link java.util.concurrent.ThreadPoolExecutor.AbortPolicy}:丢弃任务并抛出RejectedExecutionException异常。
     *                        ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     *                        ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     *                        ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
     */
    public MyThreadPoolExecutor(int corePoolSize,
                                int maximumPoolSize,
                                long keepAliveTime,
                                TimeUnit unit,
                                BlockingQueue<Runnable> workQueue,
                                ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {


    }


}
