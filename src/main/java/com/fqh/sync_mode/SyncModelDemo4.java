package com.fqh.sync_mode;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/7 21:21:05
 */
@Slf4j(topic = "c.l")
public class SyncModelDemo4 {

    static final AwaitSignal awaitSignal = new AwaitSignal(3);

    public static void main(String[] args) {

        Condition t1PrintCondition = awaitSignal.newCondition();
        Condition t2PrintCondition = awaitSignal.newCondition();
        Condition t3PrintCondition = awaitSignal.newCondition();

        new Thread(() -> awaitSignal.printStr(t1PrintCondition, t2PrintCondition, "蔡"), "T1").start();
        new Thread(() -> awaitSignal.printStr(t2PrintCondition, t3PrintCondition, "虚"), "T1").start();
        new Thread(() -> awaitSignal.printStr(t3PrintCondition, t1PrintCondition, "鲲"), "T1").start();

        sleep(1000);
        try {
            log.debug("Main ===> 开始唤醒T1");
            awaitSignal.lock();
            t1PrintCondition.signal();
        } finally {
            awaitSignal.unlock();
        }

    }

    static class AwaitSignal extends ReentrantLock {
        int cycleCount;

        public AwaitSignal(int cycleCount) {
            this.cycleCount = cycleCount;
        }

        public void printStr(Condition currentCondition, Condition nextCondition, String s) {
            for (int i = 0; i < cycleCount; i++) {
                this.lock();
                try {
                    currentCondition.await();
                    log.debug(s);
                    nextCondition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    this.unlock();
                }
            }
        }
    }
}
