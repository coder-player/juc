package com.fqh.unsafe;

import com.fqh.utils.UnsafeAccessor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/15 21:59:42
 * 自定义原子整型类
 */

@Slf4j(topic = "c.K")
public class CustomAtomicInteger {

    static int THREAD_SIZE = 100;

    public static void main(String[] args) throws InterruptedException {

        Account account = new MyAtomicInteger(10000);

        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int i = 0; i < THREAD_SIZE; i++) {
                    account.withdraw(10);
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();

        System.out.println(account.getBalance());
    }
}


class MyAtomicInteger implements Account {
    private volatile int value;
    private static final long valueOffset;
    private static final Unsafe UNSAFE;

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    //比较并交换
    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer account) {
        decrement(account);
    }
}

interface Account {

    Integer getBalance();

    void withdraw(Integer account);
}