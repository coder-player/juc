package com.fqh.thread_method;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/8/31 16:51:16
 */
@Slf4j(topic = "c.k")
public class JoinDemo2 {

    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {

        test2();
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(1000);
            r1 = 10;
        }, "T1");


        Thread t2 = new Thread(() -> {
            sleep(2000);
            r2 = 10;
        }, "T2");

        t1.start();
        t2.start();
        long startTime = System.currentTimeMillis();
        log.debug("main线程开始进行等待...");
        t1.join();
        log.debug("T1线程等待结束...");
        t2.join();
        log.debug("T2线程等待结束...");
        long endTime = System.currentTimeMillis();
        log.debug("r1: {} r2: {}", r1, r2);
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }
}
