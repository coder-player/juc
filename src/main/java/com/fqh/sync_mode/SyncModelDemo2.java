package com.fqh.sync_mode;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/7 20:59:37
 */
@Slf4j(topic = "c.l")
public class SyncModelDemo2 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("A");
        }, "T1");
        t1.start();

        Thread t2 = new Thread(() -> {
            log.debug("B");
            LockSupport.unpark(t1);
        }, "T2");
        t2.start();
    }

}
