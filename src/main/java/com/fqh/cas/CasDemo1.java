package com.fqh.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/29 14:23:37
 */
public class CasDemo1 {

    public static void main(String[] args) {
        Account account1 = new AccountSync(10000);
        Account account2 = new AccountCas(10000);
//    System.out.println("synchronized 多线程取款操作 ===> ");
//    Account.demo(account1);
        System.out.println("*******************");
        System.out.println("CAS 多线程取款操作 ===> ");
        Account.demo(account2);
    }
}

@Slf4j(topic = "c.l")
class AccountCas implements Account {
    private AtomicInteger balance;

    public AccountCas(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withDraw(Integer amount) {
        while (true) {
            // 期望值
            int expect = balance.get();
            // 更新的值
            int update = expect - amount;
            if (balance.compareAndSet(expect, update)) {
                log.debug("CAS 成功!!!");
                break;
            }
            log.error("CAS ===> 失败");
        }
    }
}

class AccountSync implements Account {
    private Integer balance;

    public AccountSync(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public void withDraw(Integer amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }
}

interface Account {
    Integer getBalance();

    void withDraw(Integer amount);

    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withDraw(10);
            }));
        }
        long startTime = System.currentTimeMillis();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long endTime = System.currentTimeMillis();
        System.out.println("剩余余额: " + account.getBalance() + " 耗时: " + (endTime - startTime) + "ms");

    }
}
