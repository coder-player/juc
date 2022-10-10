package com.fqh.volatile_keyword;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/28 11:22:15
 */
@Slf4j(topic = "c.l")
public class VolatileVisibleDemo2 {

    // 保证run的 可见性
    static volatile boolean run = true;

    public static void main(String[] args) {

        new Thread(() -> {
            while (run) {
            }
        }, "T1").start();
        System.out.println();

        sleep(1);
        log.debug("停止T1...");
        run = false;
    }
}
