package com.fqh.thread_create;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 14:15:12
 */
@Slf4j(topic = "c.k")
public class CreateThreadDemo3 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("lambda写法 线程执行...");
        }, "T1");
        t1.start();
    }
}
