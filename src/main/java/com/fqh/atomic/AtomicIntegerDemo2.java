package com.fqh.atomic;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/29 16:49:20
 */
public class AtomicIntegerDemo2 {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(5);

        /**
         * 读取到的值
         * 返回设置的值
         */
        atomicInteger.updateAndGet(x -> x * 10);

        atomicInteger.updateAndGet(x -> x + 10);

        System.out.println(atomicInteger.get());


    }
}
