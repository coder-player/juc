package com.fqh.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/13 22:20:40
 */


/**
 * ******************
 * 需求: 5个销售卖房子
 * ***************
 */
@Slf4j(topic = "c.ThreadLocalDemo")
public class ThreadLocalDemo1 {

    public static void main(String[] args) {

        House house = new House();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                try {
                    for (int j = 1; j <= size; j++) {
//                    house.saleHouse();
                        house.saleHouseByThreadLocal();
                    }
                    log.info("卖出: {}", house.saleVolume.get());
                } finally { //必须清空【线程池复用会导致ThreadLocal内存泄漏】
                    house.saleVolume.remove();
                }

            }, String.valueOf(i)).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        log.info("总共卖出{}套", house.saleCount);
    }

    static class House { //资源
        int saleCount = 0;

        public synchronized void saleHouse() {
            ++saleCount;
        }

        /**
         * ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>(){
         *
         * @Override protected Integer initialValue() {
         * return 0;
         * }
         * };
         */
        ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

        public void saleHouseByThreadLocal() {
            saleVolume.set(1 + saleVolume.get());
        }
    }
}
