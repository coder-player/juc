package com.fqh.volatile_keyword;

import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/6 15:59:29
 * volatile的可见性
 */
public class VolatileVisibleDemo {

    //    static boolean flag = true; //不加volatile
    static volatile boolean flag = true; //加volatile

    final static Object lock = new Object(); //加锁也能保证可见性

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---come in");
            while (flag) {
//                System.out.println(""); //添加打印语句不加volatile也可以保证可见性--->println源码【2把sync锁】
            }
            System.out.println(Thread.currentThread().getName() + "\t ---flag被设置为false, 程序停止");
        }, "T1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成 【flag】=" + flag);
    }
}

/**
 * ****************
 * 不加volatile的flag
 * (1)main线程修改了flag变量, 没有刷新到主内存
 * (2)main线程修改了flag变量, 刷新到主内存, 但是T1线程并没有即时主内存获取最新的flag,
 * 一直在本地内存中获取
 * <p>
 * *****************
 */