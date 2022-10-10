package com.fqh.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/11 19:19:45
 */
@Slf4j(topic = "c.AtomicReferenceDemo")
public class AtomicReferenceDemo {

    //解决是否修改过.....
    static AtomicMarkableReference<Integer> markableReference = new AtomicMarkableReference<>(100, false);

    public static void main(String[] args) {

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            log.info("默认标记位: " + marked);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            markableReference.compareAndSet(100, 1000, marked, !marked);
        }, "T1").start();

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            log.info("默认标记位: " + marked);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = markableReference.compareAndSet(100, 2000, marked, !marked);
            log.info("t2 线程 CAS: {}", b);
            log.info("{}", markableReference.isMarked());
            log.info("{}", markableReference.getReference());
        }, "T2").start();

    }
}


/**
 * ***************
 * CAS--->Unsafe--->do while + ABA
 * <p>
 * AtomicMarkableReference
 * ******************
 */