package com.fqh.thread_safe;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/9 12:56:35
 */
public class ThreadSafeDemo {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        ThreadSafe test = new ThreadSafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "T" + (i + 1)).start();
        }
    }
}

/**
 * ******************
 * <p>
 * 线程安全demo---成员变量的引用?????
 * ***************
 */
@Slf4j(topic = "c.ThreadSafe")
class ThreadSafe {
    ArrayList<String> list = new ArrayList<>();

    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    /**
     * add的源码
     * public boolean add(E e) {
     * ensureCapacityInternal(size + 1);  // Increments modCount!!
     * elementData[size++] = e;
     * return true;
     * }
     * size++非原子操作, 两个线程并发操作导致size本应该是2但是会变成1
     */
    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}
