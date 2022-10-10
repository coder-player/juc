package com.fqh.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/15 21:17:41
 */


/**
 * **************
 * 【强制】必须回收自定义的 ThreadLocal变量, 尤其在线程池的场景下
 * *******************
 */
@Slf4j(topic = "c.K")
public class ThreadLocalDemo2 {

    public static void main(String[] args) {

        MyData myData = new MyData();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.submit(() -> {
                    try {
                        Integer beforeInt = myData.threadLocalField.get();
                        myData.add();
                        Integer afterInt = myData.threadLocalField.get();
                        log.info("beforeVal: {}.....afterVal: {}", beforeInt, afterInt);
                    } finally {
                        myData.threadLocalField.remove();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    static class MyData {

        ThreadLocal<Integer> threadLocalField = ThreadLocal.withInitial(() -> 0);

        public void add() {
            threadLocalField.set(1 + threadLocalField.get());
        }
    }
}
