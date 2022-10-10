package com.fqh.thread_method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 15:32:36
 */
@Slf4j(topic = "c.k")
public class StartRunDemo1 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("执行...");
        }, "T1");

        t1.run(); // 由main线程去执行 并不是T1线程
    }
}
