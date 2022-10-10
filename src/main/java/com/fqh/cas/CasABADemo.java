package com.fqh.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/9 11:28:55
 * ABA问题
 */
@Slf4j(topic = "c.CasABADemo")
public class CasABADemo {

    static AtomicInteger atomicI = new AtomicInteger(100);

    static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

//        abaHappen();
        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            log.info("\t" + "首次版本号: " + stamp);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stampedReference.compareAndSet(100, 101, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            log.info("\t" + "2次版本号: " + stampedReference.getStamp());
            stampedReference.compareAndSet(101, 100, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            log.info("\t" + "3次版本号: " + stampedReference.getStamp());
        }, "T3").start();

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            log.info("\t" + "首次版本号: " + stamp);
            //等到T3线程发生ABA
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = stampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
            log.info(b + "\t" + stampedReference.getReference() + "\t" + stampedReference.getStamp());
        }, "T4").start();
    }


    private static void abaHappen() {
        new Thread(() -> {
            atomicI.compareAndSet(100, 101);
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicI.compareAndSet(101, 100);
        }, "T1").start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(atomicI.compareAndSet(100, 2022) + "\t" + atomicI.get());
        }, "T2").start();
    }
}
