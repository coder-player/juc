package com.fqh.thread_state;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/27 19:43:44
 */
@Slf4j(topic = "c.l")
public class ThreadStateDemo1 {

    final static Object obj = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行...");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码...");
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行...");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码...");
            }
        }, "T2").start();

        sleep(1);
        log.debug("唤醒 obj 上面等待的线程...");
        synchronized (obj) {
            obj.notifyAll();
            // 唤醒 T1 T2 线程后, T1 T2立刻进入 BLOCKED状态
            // 此时主线程还没释放锁呢
        }
        // 主线程释放锁 T1 T2 抢锁, 抢到锁的线程进入 RUNNING状态
    }
}
