package com.fqh.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/30 19:06:13
 */
public class AqsDemo1 {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}
