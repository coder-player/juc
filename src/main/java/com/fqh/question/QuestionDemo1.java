package com.fqh.question;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/19 16:58:27
 */
@Slf4j(topic = "c.k")
public class QuestionDemo1 {

    public static Object lock = new Object();

    public static int count = 0;

    public static int state = 0;

    public static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {

//    method1();

        condition();
    }

    private static void method1() {
        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (count + 1 <= 100 && state == 0) {
                        log.debug("A");
                        count++;
                        state = 1;
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "T1").start();

        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (count + 1 <= 100 && state == 1) {
                        log.debug("B");
                        count++;
                        state = 2;
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "T2").start();

        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (count + 1 <= 100 && state == 2) {
                        log.debug("C");
                        count++;
                        state = 0;
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "T3").start();
    }

    public static void condition() {
        Condition printACondition = reentrantLock.newCondition();
        Condition printBCondition = reentrantLock.newCondition();
        Condition printCCondition = reentrantLock.newCondition();

        new Thread(() -> {

            while (true) {
                reentrantLock.lock();
                if (count + 1 <= 100) {
                    log.info("A 第: {}", count);
                    count++;
                } else {
                    break;
                }
                printBCondition.signal();
                try {
                    printACondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
            }
        }, "T1").start();

        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                if (count + 1 <= 100) {
                    log.info("B");
                    count++;
                } else {
                    break;
                }
                printCCondition.signal();
                try {
                    printBCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
            }
        }, "T2").start();

        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                if (count + 1 <= 100) {
                    log.info("C");
                    count++;
                } else {
                    break;
                }
                printACondition.signal();
                try {
                    printCCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
            }
        }, "T3").start();
    }


}
