package com.fqh.reentrantlock;

import com.fqh.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/28 10:14:42
 */
@Slf4j(topic = "c.l")
public class ReentrantLockDemo2 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                log.debug("尝试获取锁...");
                // 如果没有竞争, 此方法就会获取lock锁
                // 如果有竞争, 进入阻塞队列, 可以被其他线程打断
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁, 返回...");
                return;
            }
            try {
                log.debug("获取到锁了...");
            } finally {
                lock.unlock();
            }
        }, "T1");

        lock.lock();

        t1.start();

        Sleeper.sleep(1);
        log.debug("打断 T1线程");
        t1.interrupt();
    }
}
