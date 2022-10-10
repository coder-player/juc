package com.fqh.semaphore;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/29 23:25:07
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static com.fqh.utils.Sleeper.sleep;

/**
 * ***************
 * 信号量===>限制能同时访问共享资源的线程上限
 * ******************
 */
@Slf4j(topic = "c.k")
public class SemaphoreDemo1 {

    public static void main(String[] args) {
        //1. 创建Semaphore对象
        Semaphore semaphore = new Semaphore(3); //限制访问资源的线程上限为 3

        //2. 10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    sleep(1);
                    log.debug("end...");
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
