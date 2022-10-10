package com.fqh.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/4 12:44:34
 */
public class InterruptDemo2 {

    public static void main(String[] args) {

        //实例方法interrupt()仅仅是设置一个flag并不会中断线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("------: " + i);
            }
            System.out.println("T1线程(interrupt)被打断后的标志02: " + Thread.currentThread().isInterrupted()); //false

        }, "T1");
        t1.start();

        System.out.println("T1线程默认的中断标志: " + t1.isInterrupted()); //false

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt(); //true

        System.out.println("T1线程(interrupt)被打断后的标志01: " + t1.isInterrupted()); //true

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //线程结束后为清除打断标记 flag----->false
        System.out.println("T1线程(interrupt)被打断后的标志03: " + t1.isInterrupted()); //false

    }
}
