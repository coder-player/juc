package com.fqh.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/31 12:25:30
 */
public class LongAdderDemo1 {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            demo(() -> new AtomicLong(0), AtomicLong::getAndIncrement);
        }
        System.out.println("****************************");
        for (int i = 0; i < 5; i++) {
            demo(LongAdder::new, LongAdder::increment);
        }
    }

    /**
     * @param adderSupplier 提供累加器对象
     * @param action        执行累加操作
     * @param <T>
     */
    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println("累加结果: " + adder + " 花费: " + (end - start) / 1000_000);
    }
}
