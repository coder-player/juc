package com.fqh.synchroized;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/26 21:58:42
 */
@Slf4j(topic = "c.k")
public class BiasLock2 {

    public static void main(String[] args) {

        Cat c = new Cat();

        new Thread(() -> {
            log.debug(ClassLayout.parseInstance(c).toPrintable());
            synchronized (c) {
                log.debug(ClassLayout.parseInstance(c).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(c).toPrintable());

            synchronized (BiasLock2.class) {
                BiasLock2.class.notify();
            }
        }, "T1").start();


        new Thread(() -> {
            synchronized (BiasLock2.class) {
                try {
                    BiasLock2.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("-------------T2-\n" + "----------------");
            log.debug(ClassLayout.parseInstance(c).toPrintable());
            synchronized (c) {
                log.debug(ClassLayout.parseInstance(c).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(c).toPrintable());
        }, "T2").start();
    }
}


class Cat {
}