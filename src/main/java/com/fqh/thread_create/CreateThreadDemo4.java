package com.fqh.thread_create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 14:20:59
 */
@Slf4j(topic = "c.k")
public class CreateThreadDemo4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("执行任务...");
            sleep(1000);
            return 666;
        });
        new Thread(task, "T1").start();
        log.debug("任务执行结果: {}", task.get());
    }
}
