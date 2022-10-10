package com.fqh.wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/4 22:06:08
 */
@Slf4j(topic = "c.WaitNotifyDemo2")
public class WaitNotifyDemo2 {

    final static Object obj = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行......");
                try {
                    obj.wait(1000); //让线程在lock上一直等待下去直到被唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码....");
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行......");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码....");
            }
        }, "T2").start();

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("唤醒 obj 上其他线程");
        synchronized (obj) {
//            obj.notify(); //随机唤醒obj上wait的一个线程
            obj.notifyAll(); //唤醒obj上wait的所有线程
        }
    }
}
