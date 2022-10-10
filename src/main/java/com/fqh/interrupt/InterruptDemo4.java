package com.fqh.interrupt;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/4 13:11:02
 */
public class InterruptDemo4 {

    public static void main(String[] args) {

        //测试当前线程是否被中断(检查中断标志), 返回一个boolean并清除中断标记
        // 第二次再调用中断状态已经被清除, 将返回一个false

        System.out.println(Thread.currentThread().getName() + "\t " + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t " + Thread.interrupted());
        System.out.println("------ 1");
        Thread.currentThread().interrupt(); //中断标志设置位true
        System.out.println("------ 2");
        System.out.println(Thread.currentThread().getName() + "\t " + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t " + Thread.interrupted());
    }
}

/**
 * *************
 * public static boolean interrupted() {
 * return currentThread().isInterrupted(true);
 * }
 * {ClearInterrupted} 用来传递是否需要清除打断标记
 * private native boolean isInterrupted(boolean ClearInterrupted);
 * <p>
 * ********************
 */