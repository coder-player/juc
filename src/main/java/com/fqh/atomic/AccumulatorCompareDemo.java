package com.fqh.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/11 20:34:14
 */

class ClickNumber {

    int number = 0;

    public synchronized void clickBySynchronized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}

/**
 * ***************
 * 高并发下统计点赞次数
 * <p>
 * 20:47:03.150 c.AccumulatorCompareDemo [main] - clickBySynchronized--->点赞50000000次, 耗时: 2216
 * 20:47:04.114 c.AccumulatorCompareDemo [main] - clickByAtomicLong--->点赞50000000次, 耗时: 960
 * 20:47:04.273 c.AccumulatorCompareDemo [main] - clickByLongAdder--->点赞50000000次, 耗时: 159
 * 20:47:04.431 c.AccumulatorCompareDemo [main] - clickByLongAccumulator--->点赞50000000次, 耗时: 157
 * ******************
 */
@Slf4j(topic = "c.AccumulatorCompareDemo")
public class AccumulatorCompareDemo {

    public static final int _1W = 10000;
    public static final int THREAD_NUMBER = 50;

    public static void main(String[] args) throws InterruptedException {

        ClickNumber clickNumber = new ClickNumber();
        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUMBER);

        sync(clickNumber, countDownLatch1);

        atomicLong(clickNumber, countDownLatch2);

        longAdder(clickNumber, countDownLatch3);

        longAccumulator(clickNumber, countDownLatch4);
    }

    private static void longAccumulator(ClickNumber clickNumber, CountDownLatch countDownLatch4) throws InterruptedException {
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch4.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        log.info("clickByLongAccumulator--->点赞{}次, 耗时: {}", clickNumber.number, (endTime - startTime));
    }

    private static void longAdder(ClickNumber clickNumber, CountDownLatch countDownLatch3) throws InterruptedException {
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch3.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        log.info("clickByLongAdder--->点赞{}次, 耗时: {}", clickNumber.number, (endTime - startTime));
    }

    private static void atomicLong(ClickNumber clickNumber, CountDownLatch countDownLatch2) throws InterruptedException {
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        log.info("clickByAtomicLong--->点赞{}次, 耗时: {}", clickNumber.number, (endTime - startTime));
    }

    private static void sync(ClickNumber clickNumber, CountDownLatch countDownLatch1) throws InterruptedException {
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickBySynchronized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        log.info("clickBySynchronized--->点赞{}次, 耗时: {}", clickNumber.number, (endTime - startTime));
    }
}
