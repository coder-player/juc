package com.fqh.volatile_keyword;

import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/6 19:44:49
 * volatile的非原子性
 */

class MyNumber {

    volatile int number;

    public void addPlusPlus() {
        number++;
    }
}

public class VolatileAtomicDemo {

    public static void main(String[] args) {

        MyNumber myNumber = new MyNumber();

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(myNumber.number);
    }
}
