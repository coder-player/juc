package com.fqh.wait_notify;

import com.fqh.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/27 10:16:31
 */
@Slf4j(topic = "c.l")
public class WaitNotifyDemo3 {

    static final Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (lock) {
                log.debug("获取锁");
                try {
//                    Thread.sleep(20000); // sleep不释放锁
                    lock.wait(); // wait会释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T1").start();

        Sleeper.sleep(1);

        synchronized (lock) {
            log.debug("获取锁, 准备唤醒wait的线程");
            lock.notify();
        }
    }

    /** ******************
     * sleep 和 wait 都是 TIME_WAITING
     * **************** */
}
