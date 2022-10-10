package com.fqh.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/28 10:52:37
 */
@Slf4j(topic = "c.l")
public class ConditionDemo2 {

    static boolean hasCigarette = false; // 是否有烟
    static boolean hasTakeout = false;

    static ReentrantLock ROOM = new ReentrantLock();
    static Condition waitCigaretteSet = ROOM.newCondition();
    static Condition waitTakeoutSet = ROOM.newCondition();

    public static void main(String[] args) {

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("有外卖吗? 【{}】", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖, 不干了...");
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有外卖, 可以继续干活了...");
            } finally {
                ROOM.unlock();
            }
        }, "小女").start();

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("有烟吗? 【{}】", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟, 不干了...");
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟, 可以继续干活了...");
            } finally {
                ROOM.unlock();
            }
        }, "小南").start();


        sleep(1);

        new Thread(() -> {
            ROOM.lock();
            try {
                hasTakeout = true;
                waitTakeoutSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "美团骑手").start();

        sleep(1);

        new Thread(() -> {
            ROOM.lock();
            try {
                hasCigarette = true;
                waitCigaretteSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "送烟人").start();
    }
}
