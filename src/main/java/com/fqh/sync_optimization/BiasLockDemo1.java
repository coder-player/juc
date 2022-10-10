package com.fqh.sync_optimization;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/9 21:41:54
 */
@Slf4j(topic = "c.k")
public class BiasLockDemo1 {

    public static void main(String[] args) {

        BiasLockDemo1 o = new BiasLockDemo1();
//        o.hashCode(); //调用hashCode会撤销掉偏向锁状态
        log.debug(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            log.debug(ClassLayout.parseInstance(o).toPrintable());
        }
        log.debug(ClassLayout.parseInstance(o).toPrintable());
    }
}

