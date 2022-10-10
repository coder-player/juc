package com.fqh.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author The End of Death
 * @version 1.0
 * @since 2022/9/14 12:16:08
 */
@Slf4j(topic = "c.l")
public class CustomLock {

    public static void main(String[] args) {

        MyLock lock = new MyLock();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("T1 ===> 加锁成功");
                sleep(2000);
            } finally {
                log.debug("T1 ===> 释放锁");
                lock.unlock();
            }
        }, "T1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("T2 ===> 加锁成功");
                sleep(1000);
            } finally {
                lock.unlock();
                log.debug("T2 ===> 释放锁");
            }
        }, "T2").start();


    }

    /**
     * 自定义不可重入锁
     */
    static class MyLock implements Lock {
        /**
         * 独占锁
         */
        static class MySync extends AbstractQueuedSynchronizer {
            @Override
            protected boolean tryAcquire(int arg) {
                if (compareAndSetState(0, 1)) {
                    // 加锁成功 设置owner为当前线程
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
            }

            @Override
            protected boolean tryRelease(int arg) {
                setExclusiveOwnerThread(null);
                setState(0); // state是volatile修饰 state前面的写操作会同步到主存 对其他线程可见
                return true;
            }

            /**
             * 是否持有独占锁
             *
             * @return
             */
            @Override
            protected boolean isHeldExclusively() {
                return getState() == 1;
            }

            public Condition newCondition() {
                return new ConditionObject();
            }
        }

        private MySync sync = new MySync();

        /**
         * 加锁(不成功进入等待队列)
         */
        @Override
        public void lock() {
            sync.acquire(1);
        }

        /**
         * 可打断的解锁
         *
         * @throws InterruptedException
         */
        @Override
        public void lockInterruptibly() throws InterruptedException {
            sync.acquireInterruptibly(1);
        }

        /**
         * 尝试加锁(一次)
         *
         * @return
         */
        @Override
        public boolean tryLock() {
            return sync.tryAcquire(1);
        }

        /**
         * 尝试加锁 带超时时间
         *
         * @param time 时间
         * @param unit 时间单位
         * @return
         * @throws InterruptedException
         */
        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return sync.tryAcquireNanos(1, unit.toNanos(time));
        }

        /**
         * 解锁
         */
        @Override
        public void unlock() {
            sync.release(1);
        }

        /**
         * 条件变量
         *
         * @return
         */
        @Override
        public Condition newCondition() {
            return sync.newCondition();
        }
    }

}
