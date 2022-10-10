package com.fqh.locksupport;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/27 11:19:55
 */
@Slf4j(topic = "c.l")
public class LockSupportDemo3 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("运行线程...");
            sleep(2);
            log.debug("线程暂停...");
            LockSupport.park();
            log.debug("恢复运行...");
        }, "T1");

        t1.start();

        sleep(1);
        log.debug("unpark===>T1线程");
        LockSupport.unpark(t1);
    }
}

