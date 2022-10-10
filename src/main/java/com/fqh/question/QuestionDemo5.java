package com.fqh.question;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/1 10:37:19
 */

import java.util.function.IntConsumer;

/**
 * 给你类 ZeroEvenOdd 的一个实例，该类中有三个函数：zero、even 和 odd 。ZeroEvenOdd 的相同实例将会传递给三个不同线程：
 * <p>
 * 线程 A：调用 zero() ，只输出 0
 * 线程 B：调用 even() ，只输出偶数
 * 线程 C：调用 odd() ，只输出奇数
 * <p>
 * 修改给出的类，以输出序列 "010203040506..." ，其中序列的长度必须为 2n 。
 * <p>
 * 实现 ZeroEvenOdd 类：
 * <p>
 * ZeroEvenOdd(int n) 用数字 n 初始化对象，表示需要输出的数。
 * void zero(printNumber) 调用 printNumber 以输出一个 0 。
 * void even(printNumber) 调用printNumber 以输出偶数。
 * void odd(printNumber) 调用 printNumber 以输出奇数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/print-zero-even-odd
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 输入：n = 2
 * 输出："0102"
 * 解释：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/print-zero-even-odd
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 输入：n = 5
 */
public class QuestionDemo5 {

    public static void main(String[] args) {

        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(2);

        new Thread(() -> {
            try {
                zeroEvenOdd.zero(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                zeroEvenOdd.even(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

        new Thread(() -> {
            try {
                zeroEvenOdd.odd(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
    }

    static class ZeroEvenOdd {
        private int n;
        private final Object lock = new Object();
        private int count = 1; // 计数
        private int zeroState = 0; // 零状态 0-打印 1-不打印

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            synchronized (lock) {
                do {
                    if (zeroState == 0) {
                        printNumber.accept(0);
                        zeroState = 1;
                        lock.notifyAll();
                    } else {
                        lock.wait();
                    }
                } while (count <= n);
            }
        }

        // 偶数
        public void even(IntConsumer printNumber) throws InterruptedException {
            synchronized (lock) {
                do {
                    if (count % 2 == 0 && zeroState == 1) {
                        printNumber.accept(count);
                        count++;
                        zeroState = 0;
                        lock.notifyAll();
                    } else {
                        lock.wait();
                    }
                } while (count <= n);
            }
        }

        // 奇数
        public void odd(IntConsumer printNumber) throws InterruptedException {
            synchronized (lock) {
                do {
                    if (count % 2 != 0 && zeroState == 1) {
                        printNumber.accept(count);
                        count++;
                        zeroState = 0;
                        lock.notifyAll();
                    } else {
                        lock.wait();
                    }
                } while (count <= n);
            }
        }
    }
}
