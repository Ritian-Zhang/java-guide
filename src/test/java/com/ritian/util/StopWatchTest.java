package com.ritian.util;

import org.junit.Test;

/**
 * TEST
 *
 * @author ritian
 * @since 2019/9/16 17:18
 **/
public class StopWatchTest {

    @Test
    public void test() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("任务一");
        Thread.sleep(1000);
        stopWatch.stop();

        stopWatch.start("任务二");
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
