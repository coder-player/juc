package com.fqh.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/7 20:23:55
 */
@Slf4j(topic = "c.l")
public class ReentrantLockDemo4 {

    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                // 没有竞争 ===> 线程获取lock对象锁
                // 有竞争 ===> 进入阻塞队列 可被打断
                log.debug("T1 ===> 尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("T1 ===> 被打断 return");
                return;
            }
            try {
                log.debug("T1 ===> 获取到锁");
            } finally {
                lock.unlock();
            }
        }, "T1");
        log.debug("Main ===> 获取锁");
        lock.lock();

        t1.start();

        sleep(1000);
        log.debug("Main ===> 打断T1");
        t1.interrupt();
    }
}
