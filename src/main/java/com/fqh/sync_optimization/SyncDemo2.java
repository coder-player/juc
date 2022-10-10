package com.fqh.sync_optimization;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/24 21:15:20
 */

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * ***************
 * 偏向锁
 * 添加VM参数--->XX:BiasedLockingStartupDelay=0
 * ******************
 */

public class SyncDemo2 {

    public static void main(String[] args) {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println("***************************************");

        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "T1").start();

    }

    private static void noLock() {

    }
}
