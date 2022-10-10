package com.fqh.thread_create;


import lombok.extern.slf4j.Slf4j;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/8/31 13:45:26
 */
@Slf4j(topic = "c.k")
public class CreateThreadDemo1 {

    public static void main(String[] args) {

        // 创建线程的匿名实现
        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                log.debug("开始执行...");
            }
        };
        // 启动线程
        t1.start();
        log.debug("开始run");
    }
}
