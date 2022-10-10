package com.fqh.thread_state;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/8/31 20:24:08
 */
@Slf4j(topic = "c.l")
public class ThreadStateDemo2 {

    static final Object lock = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("T1开始执行...");
        }, "T1");

        Thread t2 = new Thread(() -> {
            while (true) {

            }
        }, "T2");
        t2.start();

        Thread t3 = new Thread(() -> {
            log.debug("T3开始执行...");
        }, "T3");
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (lock) {
                sleep(10000000);
            }
        }, "T4");
        t4.start();

        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T5");
        t5.start();

        Thread t6 = new Thread(() -> {
            synchronized (lock) {
                sleep(10000000);
            }
        }, "T6");
        t6.start();

        log.debug("T1 状态: {}", t1.getState()); // NEW
        log.debug("T2 状态: {}", t2.getState()); // RUNNABLE
        log.debug("T3 状态: {}", t3.getState()); // TERMINATED
        log.debug("T4 状态: {}", t4.getState()); // TIMED_WAITING
        log.debug("T5 状态: {}", t5.getState()); // WAITING
        log.debug("T6 状态: {}", t6.getState()); // BLOCKED
    }
}
