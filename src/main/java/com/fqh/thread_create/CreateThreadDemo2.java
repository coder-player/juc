package com.fqh.thread_create;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 14:11:16
 */
@Slf4j(topic = "c.k")
public class CreateThreadDemo2 {

    public static void main(String[] args) {

        Runnable runnable = () -> {
            log.debug("开始run...");
        };
        // 创建线程对象
        Thread t2 = new Thread(runnable, "T2");
        // 启动线程
        t2.start();
    }
}
