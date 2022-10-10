package com.fqh.sync_mode;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/7 21:44:00
 */
@Slf4j(topic = "c.l")
public class SyncModelDemo5 {

    static Thread t1;
    static Thread t2;
    static Thread t3;
    static final ParkUnpark pu = new ParkUnpark(3);


    public static void main(String[] args) {

        t1 = new Thread(() -> pu.printStr("蔡", t2), "T1");
        t2 = new Thread(() -> pu.printStr("虚", t3), "T2");
        t3 = new Thread(() -> pu.printStr("鲲", t1), "T3");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }

    static class ParkUnpark {
        int cycleCount;

        public ParkUnpark(int cycleCount) {
            this.cycleCount = cycleCount;
        }

        public void printStr(String s, Thread next) {
            for (int i = 0; i < cycleCount; i++) {
                LockSupport.park();
                log.debug(s);
                LockSupport.unpark(next);
            }
        }
    }
}
