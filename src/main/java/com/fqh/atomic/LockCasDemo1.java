package com.fqh.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * @since 2022/7/31 12:42:26
 */
@Slf4j(topic = "c.l")
public class LockCasDemo1 {

    /**
     * 0 没加锁
     * 1 加锁
     */
    private AtomicInteger state = new AtomicInteger(0);

    public void lock() {
        while (true) {
            if (state.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        log.debug("解锁...");
        state.set(0);
    }

    public static void main(String[] args) {
        LockCasDemo1 lock = new LockCasDemo1();
        new Thread(() -> {
            log.debug("T1 ===> 开始执行");
            lock.lock();
            try {
                log.debug("T1 ===> 加锁");
                sleep(2000);
            } finally {
                lock.unlock();
            }
        }, "T1").start();

        new Thread(() -> {
            log.debug("T2 ===> 开始");
            lock.lock();
            try {
                log.debug("T2 ===> 加锁");
            } finally {
                lock.unlock();
            }
        }, "T2").start();
    }
}
