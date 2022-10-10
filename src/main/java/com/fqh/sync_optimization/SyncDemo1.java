package com.fqh.sync_optimization;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/18 22:48:09
 */

import org.openjdk.jol.info.ClassLayout;

/**
 * *************
 * Synchronized 锁升级之无锁
 * ********************
 */
public class SyncDemo1 {

    public static void main(String[] args) {

        Object o = new Object();
        System.out.println("10: " + o.hashCode());
        System.out.println("16: " + Integer.toHexString(o.hashCode()));
        System.out.println("2: " + Integer.toBinaryString(o.hashCode()));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
