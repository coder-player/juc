package com.fqh.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/11 19:39:16
 */
class BankAccount {
    String bankName = "CCB";
    //更新对象的属性必须使用public volatile
    public volatile int money = 0;

    public void add() {
        money++;
    }

    //对象的属性修改类型原子类都是抽象类, 所以每次使用都必须
    //使用静态方法newUpdater()创建一个更新器, 并且需要设置想要更新的类的属性
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    //不加sync, 保证高性能原子性
    public void transferMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }
}

/**
 * *****************
 * 以一种线程安全的方式操作非线程安全对象的字段
 * 不使用sync-----> AtomicIntegerFiledUpdater
 * ****************
 */
@Slf4j(topic = "c.AtomicIntegerFieldUpdaterDemo")
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {

        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 1000; j++) {
                        //bankAccount.add();
                        bankAccount.transferMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();

        log.info("result: " + bankAccount.money);
    }
}
