package com.fqh.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/4 12:56:29
 * InterruptedException异常
 */
public class InterruptDemo3 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t 中断标志位: "
                            + Thread.currentThread().isInterrupted() + " 程序终止");
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    //需要再次打断标记?????--->源码
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("-----hello InterruptDemo3");
            }
        }, "T1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            t1.interrupt();
        }, "T2").start();
    }
}

/**
 * ****************
 * 1. 中断标志位默认是false
 * 2. t2 -----> t2 发出中断协商, t2调用t1.interrupt(), 中断标志位为true
 * 3. 中断标志位为true, 正常情况, 程序停止
 * 4. 中断标志位为true, 异常情况, InterruptedException, 将会包中断状态清除, 并且收到InterruptedException
 * 中断标志位false, 需要在catch块再调用一次interrupt
 * *****************
 */
