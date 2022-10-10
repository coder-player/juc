package com.fqh.stack_frame;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 14:32:45
 */
public class StackFrameDemo1 {

    public static void main(String[] args) {

        new Thread(() -> {
            method1(666);
        }, "T1").start();

        method1(10);
    }

    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        Object n = new Object();
        return n;
    }
}