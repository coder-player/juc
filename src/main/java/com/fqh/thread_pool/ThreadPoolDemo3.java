package com.fqh.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/31 16:30:54
 */
@Slf4j(topic = "c.l")
public class ThreadPoolDemo3 {

    public static void main(String[] args) {

        test2();
    }

    /**
     * ******************
     * 单线程的线程池始终能保持池中有一个可执行的线程
     * 遇到异常失败, 就会创建一个新的线程来替代
     * *******************
     */
    public static void test2() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            log.debug("1");
            int i = 1 / 0;
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }

}

