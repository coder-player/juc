package com.fqh.wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/4 22:02:57
 */
@Slf4j(topic = "c.WaitNotifyDemo")
public class WaitNotifyDemo {

    static final Object lock = new Object();


    public static void main(String[] args) {

        //必须获得对象锁才能调用wait
        new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("开始等待别人唤醒...");
                    lock.wait();
                    log.debug("我被唤醒了!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            log.debug("开始唤醒这个锁上面的等待的线程...");
            lock.notify();

        }
    }
}
