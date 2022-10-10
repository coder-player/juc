package com.fqh.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/4 19:59:20
 */

/**
 * ****************
 * LockSupport: 正常 + 无锁块要求
 * 之前错误的先唤醒后等待LockSupport支持
 * <p>
 * *****************
 */

public class LockSupportDemo1 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "\t ---come in " + System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ---被唤醒");
        }, "T1");
        t1.start();

//        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t ---发出通知 " + System.currentTimeMillis());
        }, "T2").start();
    }

    private static void lockConditionAwaitSignal() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t ---被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "T1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t ---发出通知");
            } finally {
                lock.unlock();
            }
        }, "T2").start();
    }

    private static void syncWaitNotify() {
        Object objectLock = new Object();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t ---come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t ---被唤醒");
            }
        }, "T1").start();

//        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t ---发出通知");
            }
        }, "T2").start();
    }
}

/**
 * ***************
 * 1.wait方法和notify去掉sync块出现异常【IllegalMonitorStateException】
 * 2.T2先notify在T1再wait........T1卡死...
 * 3.去掉lock和unlock块出现异常【IllegalMonitorStateException】
 * <p>
 * ******************
 */