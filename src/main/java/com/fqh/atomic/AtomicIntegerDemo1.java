package com.fqh.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/29 16:01:36
 */
public class AtomicIntegerDemo1 {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        /**
         * 自增并获取值 ++i
         */
        System.out.println(atomicInteger.incrementAndGet());
        /**
         * i++
         */
        System.out.println(atomicInteger.getAndIncrement());

        System.out.println(atomicInteger.get());

        /**
         * 获取并增加
         */
        atomicInteger.getAndAdd(5);
        /**
         * 增加并获取
         */
        atomicInteger.addAndGet(5);
    }
}
