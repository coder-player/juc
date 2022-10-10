package com.fqh.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/10 22:11:12
 */

class MyNumber {
    AtomicInteger atomicI = new AtomicInteger();

    public void addPlusPlus() {
        atomicI.getAndIncrement();
    }

}

@Slf4j(topic = "c.AtomicIntegerDemo")
public class AtomicIntegerDemo {

    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {

        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for (int i = 1; i <= SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 1000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }

            }, String.valueOf(i)).start();
        }
        //新的工具类【CountDownLatch】等待多线程完成
        countDownLatch.await();
        log.info("result: {}", myNumber.atomicI.get());
    }
}
