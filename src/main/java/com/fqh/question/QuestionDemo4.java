package com.fqh.question;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/8/31 22:35:41
 */

import java.util.function.IntConsumer;

/**
 * 请你实现一个有四个线程的多线程版  FizzBuzz， 同一个 FizzBuzz 实例会被如下四个线程使用：
 * <p>
 * 线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
 * 线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
 * 线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
 * 线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/fizz-buzz-multithreaded
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/fizz-buzz-multithreaded
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class QuestionDemo4 {

    public static void main(String[] args) {

        FizzBuzz fizzBuzz = new FizzBuzz(15);
        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.println("fizz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.println("buzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.println("fizzbuzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();

        new Thread(() -> {
            try {
                fizzBuzz.number(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "D").start();
    }

    static class FizzBuzz {
        private int n;
        private int count = 1; // 计数
        private final Object lock = new Object();

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            synchronized (lock) {
                do {
                    if (count % 3 == 0 && count % 5 != 0) {
                        printFizz.run();
                        count++;
                        lock.notifyAll();
                    } else {
                        lock.wait();
                    }
                } while (count <= n);
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            synchronized (lock) {
                do {
                    if (count % 5 == 0 && count % 3 != 0) {
                        printBuzz.run();
                        count++;
                        lock.notifyAll();
                    } else {
                        lock.wait();
                    }
                } while (count <= n);
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            synchronized (lock) {
                do {
                    if (count % 3 == 0 && count % 5 == 0) {
                        printFizzBuzz.run();
                        count++;
                        lock.notifyAll();
                    } else {
                        lock.wait();
                    }
                } while (count <= n);
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            synchronized (lock) {
                do {
                    if (count % 3 != 0 && count % 5 != 0) {
                        printNumber.accept(count);
                        count++;
                        lock.notifyAll();
                    } else {
                        lock.wait();
                    }
                } while (count <= n);
            }
        }
    }
}

