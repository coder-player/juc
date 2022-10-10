package com.fqh.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/4 11:19:08
 * 线程中断机制
 */
public class InterruptDemo {

    //利用volatile的可见性
    static volatile boolean isStop = false;

    //使用原子布尔型
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t 线程的interrupted被标记为true, 程序停止");
                    break;
                }
                System.out.println("T1 -----hello==>interrupted");
            }
        }, "T1");
        t1.start();

        System.out.println("-----T1的默认中断标致位: " + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            t1.interrupt();
        }, "T2").start();
//        t1.interrupt();

//        m1();
//        m2();
    }

    private static void m2() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean被修改为true, 程序终止...");
                    break;
                }
                System.out.println("T1 -----hello atomicBoolean");
            }
        }, "T1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "T2").start();
    }

    private static void m1() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true, 程序终止...");
                    break;
                }
                System.out.println("T1 -----hello volatile");
            }
        }, "T1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop = true;
        }, "T2").start();
    }
}
