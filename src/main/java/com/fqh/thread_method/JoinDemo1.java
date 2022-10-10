package com.fqh.thread_method;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 16:31:42
 */
@Slf4j(topic = "c.k")
public class JoinDemo1 {

    static int r = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        log.debug("主线程开始执行...");
        Thread t1 = new Thread(() -> {
            log.debug("T1线程睡眠开始...");
            sleep(1000);
            log.debug("T1线程睡眠结束...");
            r = 10;
        }, "T1");
        t1.start();
        t1.join(); // 等待T1线程执行结束
        log.debug("r: {}", r);
        log.debug("主线程结束...");
    }
}
