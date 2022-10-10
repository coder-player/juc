package com.fqh.sync_mode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/7 21:02:41
 */
@Slf4j(topic = "c.l")
public class SyncModelDemo3 {

    static final Object lock = new Object();
    static int state = 0; // 0-打印A 1-打印B 2-打印C

    public static void main(String[] args) {

        /**
         * T1线程 ===> 打印A
         * T2线程 ===> 打印B
         * T3线程 ===> 打印C
         * A先于B先于C 打印
         * wait notify
         */
//    faceProcess();

        WaitNotify wn = new WaitNotify(0, 2);
        new Thread(() -> wn.printStr(0, 1, "蔡"), "T1").start();
        new Thread(() -> wn.printStr(1, 2, "虚"), "T2").start();
        new Thread(() -> wn.printStr(2, 0, "鲲"), "T3").start();
    }

    private static void faceProcess() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (state != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.debug("A");
                    state = 1;
                    lock.notifyAll();
                }
            }
        }, "T1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (state != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.debug("B");
                    state = 2;
                    lock.notifyAll();
                }
            }
        }, "T2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (state != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.debug("C");
                    state = 0;
                    lock.notifyAll();
                }
            }
        }, "T3");

        t1.start();
        t2.start();
        t3.start();
    }

    static class WaitNotify {
        int currentState;
        int cycleCount;

        public WaitNotify(int currentState, int cycleCount) {
            this.currentState = currentState;
            this.cycleCount = cycleCount;
        }

        public void printStr(int state, int nextState, String s) {
            for (int i = 0; i < cycleCount; i++) {
                synchronized (this) {
                    while (currentState != state) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.debug(s);
                    currentState = nextState;
                    this.notifyAll();
                }
            }
        }
    }
}
