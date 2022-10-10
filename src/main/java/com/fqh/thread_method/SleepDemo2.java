package com.fqh.thread_method;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 16:11:07
 */
@Slf4j(topic = "c.k")
public class SleepDemo2 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("进入休眠...");
            sleep(2000);
        }, "T1");
        t1.start();

        sleep(1000);
        log.debug("打断T1线程...");
        t1.interrupt();
    }
}
