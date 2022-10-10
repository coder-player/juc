package com.fqh.question;

import java.util.concurrent.CountDownLatch;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/8/31 22:19:01
 * <p>
 * }
 * <p>
 * 两个不同的线程将会共用一个 FooBar 实例：
 * <p>
 * 线程 A 将会调用 foo() 方法，而
 * 线程 B 将会调用 bar() 方法
 * <p>
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/print-foobar-alternately
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class QuestionDemo3 {

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(1);
        new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.println("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.println("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    static class FooBar {
        private int n;
        private final Object lock = new Object();
        private int state = 0; // 0-打印foo 1-打印bar

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                // printFoo.run() outputs "foo". Do not change or remove this line.
                synchronized (lock) {
                    while (state != 0) {
                        lock.wait();
                    }
                    printFoo.run();
                    state = 1;
                    lock.notifyAll();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                // printBar.run() outputs "bar". Do not change or remove this line.
                synchronized (lock) {
                    while (state != 1) {
                        lock.wait();
                    }
                    printBar.run();
                    state = 0;
                    lock.notifyAll();
                }
            }
        }
    }
}

