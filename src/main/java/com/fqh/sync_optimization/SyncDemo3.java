package com.fqh.sync_optimization;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/27 20:05:30
 */

import org.openjdk.jol.info.ClassLayout;

/**
 * **************
 * 轻量级锁【本质就是CAS自旋】
 * -XX:-UseBiasedLocking===>关闭偏向锁
 * *******************
 */
public class SyncDemo3 {

    private static Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (lock) {
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        }, "T1").start();

    }

}
