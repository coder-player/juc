package com.fqh.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/30 09:39:33
 * 测试活锁
 */
@Slf4j(topic = "c.TestLiveLock")
public class TestLiveLock {

    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            while (count > 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                log.info("count: {}", count);
            }
        }, "T1").start();

        new Thread(() -> {
            while (count < 20) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                log.info("count: {}", count);
            }
        }, "T2").start();
    }
}
