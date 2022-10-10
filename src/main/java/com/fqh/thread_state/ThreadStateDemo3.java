package com.fqh.thread_state;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/6 21:51:27
 */
@Slf4j(topic = "c.k")
public class ThreadStateDemo3 {

    static final Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (lock) {
                log.debug("T1 ===> 开始执行");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("T1 ===> 执行其他代码");
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("T2 ===> 开始执行");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("T2 ===> 执行其他代码");
            }
        }, "T2").start();


        sleep(500);
        log.debug("主线程 ===> 唤醒lock上的所以线程");
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}
