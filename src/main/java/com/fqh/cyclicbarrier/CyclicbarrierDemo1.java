package com.fqh.cyclicbarrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author The End of Death
 * @version 1.0
 * @since 2022/9/24 19:23:33
 */
@Slf4j(topic = "c.l")
public class CyclicbarrierDemo1 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            log.debug("task1,task2 finish...");
        });

        for (int i = 0; i < 3; i++) {
            service.submit(() -> {
                log.debug("task1 begin...");
                sleep(1000);
                try {
                    barrier.await(); // 2-1=1
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            service.submit(() -> {
                log.debug("task2 begin...");
                sleep(2000);
                try {
                    barrier.await(); // 1-1=0;
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
