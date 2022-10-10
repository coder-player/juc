package com.fqh.sync_optimization;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/27 21:37:51
 */

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * **************
 * 锁升级之HashCode的关系
 * *******************
 */
public class SyncDemo4 {

    public static void main(String[] args) {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object lock = new Object();
        System.out.println("本应该是偏向锁");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        lock.hashCode(); //没有重写: 一致性哈希, 当一个对象已近计算了hashcode, 它就无法进入偏向锁状态


        synchronized (lock) {
            System.out.println("本应该是偏向锁, 计算hashcode之后, 会直接升级为轻量级锁");
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }
}
