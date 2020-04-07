package com.ritian.other;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO
 *
 * @author ritian
 * @since 2019/9/4 11:44
 **/
public class TimerDemo {

    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行任务：" + LocalDateTime.now());
            }
        };
        Timer timer = new Timer();
        //delay：延迟时间（以毫秒为单位）
        // period：间隔时间（以毫秒为单位）
        timer.schedule(timerTask, 1000, 1000);

    }
}
