package com.fqh.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/30 20:59:53
 */
@Slf4j(topic = "c.k")
public class AqsDemo2 {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock(); //非公平锁

        /** *****************
         * A,B,C 三个线程, 去银行办理业务, A先到, 此时窗口无人, 他优先获得办理窗口的机会, 办理业务
         * A 耗时严重, 长期占有窗口
         * ***************** */
        new Thread(() -> {
            lock.lock();
            try {
                log.info("---Come in A");
                try {
                    TimeUnit.MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }, "A").start();
        //B 是第2个人, B看到窗口被A占用, 只能去排队等待, 进入AQS队列, 等待A办理完成, 尝试抢占窗口
        new Thread(() -> {
            lock.lock();
            try {
                log.info("---Come in B");
            } finally {
                lock.unlock();
            }
        }, "B").start();
        //C 是第3个人, C看到窗口被A占用, 只能去排队等待, 进入AQS队列, 等待A办理完成, 尝试抢占窗口, 前面是B
        new Thread(() -> {
            lock.lock();
            try {
                log.info("---Come in C");
            } finally {
                lock.unlock();
            }
        }, "C").start();
    }
}
