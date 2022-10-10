package com.fqh.thread_method;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/8/31 19:50:40
 */
@Slf4j(topic = "c.k")
public class InterruptDemo2 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                log.debug("检查打断标记...");
                if (Thread.currentThread().isInterrupted()) {
                    log.debug("T1被打断了 停止运行...");
                    break;
                }
            }
        }, "T1");
        t1.start();

        sleep(1000);
        log.debug("主线程开始打断T1线程...");
        t1.interrupt();

    }
}
