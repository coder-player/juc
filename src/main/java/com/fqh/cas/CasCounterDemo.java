package com.fqh.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/7 15:10:10
 * cas实现原子操作
 */
public class CasCounterDemo {

    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {

        final CasCounterDemo cas = new CasCounterDemo();
        List<Thread> ts = new ArrayList<>(600);
        long startTime = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    cas.count();
                    cas.safeCount();
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
        //等待所有线程执行完毕
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("非线程安全的计数 i= " + cas.i);
        System.out.println("线程安全的计数 i= " + cas.atomicI.get());
        long endTime = System.currentTimeMillis();
        System.out.println("----耗时: " + (endTime - startTime) + "毫秒");

    }

    /**
     * 使用CAS线程安全的计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /**
     * 非线程安全的计数器
     */
    private void count() {
        i++;
    }
}
