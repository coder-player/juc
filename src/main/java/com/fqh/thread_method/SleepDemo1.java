package com.fqh.thread_method;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 16:08:21
 */
@Slf4j(topic = "c.k")
public class SleepDemo1 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            sleep(2000);
        }, "T1");

        t1.start();
        log.debug("T1线程状态: {}", t1.getState()); // RUNNABLE
        sleep(500);
        log.debug("T1线程状态: {}", t1.getState()); // TIMED_WAITING
    }
}
