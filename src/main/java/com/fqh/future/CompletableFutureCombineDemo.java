package com.fqh.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/2 10:54:17
 */
public class CompletableFutureCombineDemo {

    public static void main(String[] args) {

//        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "\t ---启动");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 10;
//        });
//
//        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "\t ---启动");
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 20;
//        });
//
//        CompletableFuture<Integer> result = completableFuture1.thenCombine(completableFuture2, (x, y) -> {
//            System.out.println("开始结果合并........");
//            return x + y;
//        });
//
//        System.out.println(result.join());
        completableFutureFinalTask();
    }

    /**
     * 多任务结果合并
     */
    public static void completableFutureFinalTask() {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务1......");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务2......");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        }), (x, y) -> {
            System.out.println("一阶段合并结果" + (x + y));
            return x + y;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务3......");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 30;
        }), (x, y) -> {
            System.out.println("二阶段合并结果" + (x + y));
            return x + y;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务4......");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 40;
        }), (x, y) -> {
            System.out.println("三阶段合并结果" + (x + y));
            return x + y;
        });

        System.out.println(result.join());
    }

    public static void completableFutureFinalTask2() {
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            return "hello";
//        }).thenCombine(CompletableFuture.supplyAsync(() -> {
//            return "world";
//        }), (s1, s2) -> {
//            return s1 + s2;
//        }).thenCompose(s ->
//                CompletableFuture.supplyAsync(() -> s + "nice Day!"));
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务1...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务2...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + 10;
        })).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务3...");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + 10;
        }));
        System.out.println(completableFuture.join());
    }
}


