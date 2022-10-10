package com.fqh.cas;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/9 10:52:19
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * **************
 * CAS自旋锁
 * 好处: 循环比较获取没有类似wait的阻塞
 * <p>
 * *******************
 */
@Slf4j(topic = "c.CasSpinLockDemo")
public class CasSpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        log.info("\t ---come in");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unlock() {

        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        log.info("\ttask---over---释放锁unlock");
    }

    public static void main(String[] args) {

        CasSpinLockDemo spinLockDemo = new CasSpinLockDemo();

        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }, "A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            spinLockDemo.lock();
            spinLockDemo.unlock();
        }, "B").start();
    }

}
