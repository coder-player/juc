package com.fqh.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/7 21:30:12
 */
public class CasIncrementDemo {

    public static void main(String[] args) {

        AtomicInteger atomicI = new AtomicInteger(5);

        System.out.println(atomicI.compareAndSet(5, 2022) + "\t" + atomicI.get());
        System.out.println(atomicI.compareAndSet(5, 2022) + "\t" + atomicI.get());

        System.out.println(atomicI.getAndIncrement());
    }
}
