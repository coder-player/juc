package com.fqh.locksupport;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/6 23:18:15
 */
@Slf4j(topic = "c.k")
public class LockSupportDemo2 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("T1 ===> start");
            sleep(2000);
            log.debug("T1 ===> park");
            LockSupport.park();
            log.debug("T1 ===> 恢复");
        }, "T1");
        t1.start();

        sleep(1000);
        log.debug("unpark ===> T1");
        LockSupport.unpark(t1);

    }
}
