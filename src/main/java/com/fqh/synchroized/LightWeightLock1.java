package com.fqh.synchroized;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/26 15:52:44
 */

@Slf4j(topic = "c.k")
public class LightWeightLock1 {

    static final Object lock = new Object();

    public static void main(String[] args) throws Exception {

//        method1();
    }


    public static void method1() {
        synchronized (lock) {
            //
            method2();
        }
    }

    public static void method2() {
        synchronized (lock) {
        }
    }

    /** ******************
     * 轻量级锁: 如果一个对象虽然有多个线程访问, 但多线程访问的时间是错开的, 可以被优化成轻量级锁
     *
     * **************** */
}
