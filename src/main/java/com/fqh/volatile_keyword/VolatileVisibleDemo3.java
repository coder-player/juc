package com.fqh.volatile_keyword;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author The End of Death
 * @version 1.0
 * @since 2022/10/5 14:32:44
 */
@Slf4j(topic = "c.l")
public class VolatileVisibleDemo3 {

    public static volatile boolean run = true;

    public static void main(String[] args) {

        new Thread(() -> {
            while (run) {
            }
            log.debug("T1 ===> end");
        }, "T1").start();

        sleep(2000);
        log.debug("main ===> 修改一个volatile变量");
        run = false;
        log.debug("main ===> end");


    }
}
