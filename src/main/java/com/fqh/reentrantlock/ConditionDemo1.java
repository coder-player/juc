package com.fqh.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/28 10:41:53
 */
@Slf4j(topic = "c.l")
public class ConditionDemo1 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        // 创建一个新的条件变量
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        lock.lock();
        // 进入 condition1 等待
        try {
            condition1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 唤醒 condition1 上面等待得线程
        condition1.signal();
    }
}
