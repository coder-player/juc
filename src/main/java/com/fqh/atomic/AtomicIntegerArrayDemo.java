package com.fqh.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/10 22:24:00
 */
public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {

//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        System.out.println();

        int tempInt = 0;
        tempInt = atomicIntegerArray.getAndSet(0, 1122);
        System.out.println(tempInt + "\t" + atomicIntegerArray.get(0));

        tempInt = atomicIntegerArray.getAndIncrement(0);
        System.out.println(tempInt + "\t" + atomicIntegerArray.get(0));

    }

}
