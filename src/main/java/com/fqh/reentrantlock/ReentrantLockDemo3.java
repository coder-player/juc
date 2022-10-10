package com.fqh.reentrantlock;

import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/28 10:22:45
 */
@Slf4j(topic = "c.l")
public class ReentrantLockDemo3 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            // 尝试获取锁
            log.debug("尝试获取锁...");
            try {
                if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                    log.debug("尝试获取锁失败...");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取不到锁...");
                return;
            }
            try {
                log.debug("尝试获取锁成功...");
            } finally {
                lock.unlock();
            }
        }, "T1");

        lock.lock();
        log.debug("主线程成功获取锁...");

        t1.start();

        log.debug("主线程释放了锁...");
        lock.unlock();

    }
}
