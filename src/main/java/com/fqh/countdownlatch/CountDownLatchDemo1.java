package com.fqh.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/28 20:36:05
 */
@Slf4j(topic = "c.k")
public class CountDownLatchDemo1 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
//    method1();
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        threadPool.submit(() -> {
            log.debug("开始执行...");
            sleep(1000);
            latch.countDown();
            log.debug("执行完毕...");
        });

        threadPool.submit(() -> {
            log.debug("开始执行...");
            sleep(1500);
            latch.countDown();
            log.debug("执行完毕...");
        });

        threadPool.submit(() -> {
            log.debug("开始执行...");
            sleep(2000);
            latch.countDown();
            log.debug("执行完毕...");
        });

        threadPool.submit(() -> {
            try {
                log.debug("等待汇总...");
                latch.await();
                log.debug("等待结束...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void method1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        new Thread(() -> {
            log.debug("开始执行...");
            sleep(1000);
            latch.countDown();
            log.debug("执行完毕...");
        }, "T1").start();

        new Thread(() -> {
            log.debug("开始执行...");
            sleep(1500);
            latch.countDown();
            log.debug("执行完毕...");
        }, "T2").start();

        new Thread(() -> {
            log.debug("开始执行...");
            sleep(2000);
            latch.countDown();
            log.debug("执行完毕...");
        }, "T3").start();

        log.debug("主线程 waiting...");
        latch.await();
    }
}
