package com.fqh.thread_method;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/8/31 17:14:28
 */
@Slf4j(topic = "c.k")
public class InterruptDemo1 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("休眠开始...");
            sleep(5000);
        }, "T1");

        t1.start();
        sleep(1000);
        log.debug("主线程开始打断T1线程...");
        t1.interrupt(); // 处于 sleep join wait 的线程被打断 会清除打断标记

        log.debug("T1线程的打断标记: {}", t1.isInterrupted());
    }
}
