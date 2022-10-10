package com.fqh.wait_notify;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/27 10:24:56
 */
@Slf4j(topic = "c.l")
public class WaitNotifyDemo4 {

    static final Object room = new Object();
    static boolean hasCigarette = false; // 是否有烟
    static boolean hasTakeout = false;

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (room) {
                log.debug("有外卖吗? 【{}】", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖, 不干了...");
//                    sleep(2);
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有外卖吗? 【{}】", hasTakeout);
                if (hasTakeout) {
                    log.debug("有外卖, 可以继续干活了...");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();

        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟吗? 【{}】", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟, 不干了...");
//                    sleep(2);
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟吗? 【{}】", hasCigarette);
                if (hasCigarette) {
                    log.debug("有烟, 可以继续干活了...");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活了...");
                }
            }, "其他人" + (i + 1)).start();
        }

        sleep(1);

        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                log.debug("烟 和 外卖 都送到了...");
                room.notifyAll();
            }
        }, "送烟人").start();
    }
}
