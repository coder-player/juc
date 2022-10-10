package com.fqh.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/31 16:16:12
 */
@Slf4j(topic = "c.l")
public class ThreadPoolDemo2 {

    public static void main(String[] args) {

        SynchronousQueue<Integer> integers = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                log.debug("放数据 {}", 1);
                integers.put(1);
                log.debug("{} putted...", 1);

                log.debug("放数据...{}", 2);
                integers.put(2);
                log.debug("{} putted...", 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T1").start();

        sleep(1000);

        new Thread(() -> {
            try {
                log.debug("拿数据 {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2").start();

        sleep(1000);

        new Thread(() -> {
            try {
                log.debug("拿数据 {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T3").start();


    }
}
