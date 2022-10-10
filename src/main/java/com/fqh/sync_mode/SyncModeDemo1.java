package com.fqh.sync_mode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/7 20:49:26
 */
@Slf4j(topic = "c.l")
public class SyncModeDemo1 {

    static final Object lock = new Object();
    static int state = 0; // 0 - 打印A 1 - 打印B

    public static void main(String[] args) {

        /**
         * A在B之前打印
         * wait notify ===> 同步控制
         */
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (state == 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.debug("A");
                    state = 1;
                    lock.notifyAll();
                }
            }
        }, "T1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (state == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.debug("B");
                    state = 0;
                    lock.notifyAll();
                }
            }
        }, "T2");

        t1.start();
        t2.start();

    }
}
