package com.fqh.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/31 16:51:33
 */
@Slf4j(topic = "c.l")
public class ThreadPoolDemo4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(2);

//        methodSubmit(pool);
//        methodInvokeAll(pool);
    }

    private static void methodInvokeAll(ExecutorService pool) throws InterruptedException {
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    log.debug("开始...");
                    Thread.sleep(1000);
                    return "1";
                },
                () -> {
                    log.debug("开始...");
                    Thread.sleep(500);
                    return "2";
                },
                () -> {
                    log.debug("开始...");
                    Thread.sleep(2000);
                    return "3";
                }
        ));
        futures.forEach(f -> {
            try {
                log.debug("result: {}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static void methodSubmit(ExecutorService pool) throws InterruptedException, ExecutionException {
        Future<String> future = pool.submit(() -> {
            log.debug("执行中...");
            Thread.sleep(1000);
            return "ok";
        });

        log.debug("result: {}", future.get());
    }
}
