package com.fqh.readwritelock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/12 16:45:25
 */
@Slf4j(topic = "c.x")
public class ReadWriteLockDemo1 {

    public static void main(String[] args) {
        DataContainer container = new DataContainer();
        new Thread(() -> {
            container.read();
        }, "T1").start();

        sleep(1);
        new Thread(() -> {
            container.write();
        }, "T2").start();
    }

    @Slf4j(topic = "c.DataContainer")
    static class DataContainer {

        private Object data;
        private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        private ReentrantReadWriteLock.ReadLock r = rw.readLock();
        private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

        public Object read() {
            log.debug("get readLock...");
            r.lock();
            try {
                log.debug("read...");
                sleep(1);
                return data;
            } finally {
                log.debug("release readLock...");
                r.unlock();
            }
        }

        public void write() {
            log.debug("get writeLock...");
            w.lock();
            try {
                log.debug("write...");
            } finally {
                log.debug("release writeLock...");
                w.unlock();
            }
        }
    }
}
