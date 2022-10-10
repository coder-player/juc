package com.fqh.future;

import java.util.concurrent.*;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/31 19:01:54
 */
public class CompletableFutureUseDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //使用CompletableFuture的forkjoin线程池出现的问题, 主线程结束, forkjoin也会随之结束
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + "--->Come in");
                        int result = ThreadLocalRandom.current().nextInt(10);
                        if (result > 5) {
                            int i = 10 / 0;
                        }
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("---1s后出结果: " + result);
                        return result;
                    }
                    , threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    System.out.println("----计算完成, updateValue: " + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("异常情况: " + e.getCause() + "\t" + e.getMessage());
                return null;
            });

            System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");
        } catch (Exception e) {

        } finally {
            threadPool.shutdown();
        }


//        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(Thread.currentThread().getName() + "--->Come in");
                    int result = ThreadLocalRandom.current().nextInt(10);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("---1s后出结果: " + result);
                    return result;
                }
        );
        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        System.out.println(completableFuture.get());
    }
}
