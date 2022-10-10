package com.fqh.future;

import java.util.concurrent.*;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/31 18:48:18
 */
public class CompletableFutureBuildDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
//                () -> {
//                    System.out.println(Thread.currentThread().getName());
//                    try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
//                }
//        , threadPool);

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "hello SupplyAsync....";
                }, threadPool);

        System.out.println(completableFuture.get());

        threadPool.shutdown();
    }
}
