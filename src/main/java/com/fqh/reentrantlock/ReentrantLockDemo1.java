package com.fqh.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/28 09:51:21
 */
@Slf4j(topic = "c.l")
public class ReentrantLockDemo1 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        try {
            lock.lock();
            log.debug("进入 主方法");
            m1();
        } finally {
            lock.unlock();
        }
    }

    public static void m1() {
        try {
            lock.lock();
            log.debug("进入 m1()");
            m2();
        } finally {
            lock.unlock();
        }
    }

    public static void m2() {
        try {
            lock.lock();
            log.debug("进入 m2()");
        } finally {
            lock.unlock();
        }
    }
}
