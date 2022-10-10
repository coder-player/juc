package com.fqh.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/30 10:03:13
 * 测试死锁
 */
@Slf4j(topic = "c.DeadLock")
public class TestDeadLock {

    static final Object lockA = new Object();
    static final Object lockB = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
//                try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                log.debug("线程T1持有锁A, 尝试获取锁B");
                synchronized (lockB) {
                    log.debug("线程T1操作锁B");
                }
            }
        }, "T1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                log.debug("线程T2持有锁B, 尝试获取锁A");
                synchronized (lockA) {
                    log.debug("线程T2操作锁A");
                }
            }
        }, "T2");

        t1.start();
        t2.start();
    }
}
