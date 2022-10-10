package com.fqh.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/1 18:58:01
 */
public class CompletableFutureWithThreadPoolDemo {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1号任务" + "\t" + Thread.currentThread().getName());
                return "abcd";
            }).thenRunAsync(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2号任务" + "\t" + Thread.currentThread().getName());
            }, threadPool).thenRunAsync(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("3号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("4号任务" + "\t" + Thread.currentThread().getName());
            });

            System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

/**
 * ***************
 * 1.任务不指定线程池 不管是 thenRun() 还是 thenRunAsync() 都是用默认的FrokJoinPool
 * 2.当前任务使用自定义的线程池 并且使用的是thenRunAsync() 那么只是当前任务是threadPool
 * 使用了thenRunAsync的会另起一个线程
 * 调用thenRun()方法执行第二个任务时, 则第二个任务和第一个任务是共用同一个线程池
 * 调用thenRunAsync()执行第二个任务时, 则第一个任务使用的是传入的线程池, 第二个任务使用的是ForkJoinPool
 * <p>
 * ******************
 */