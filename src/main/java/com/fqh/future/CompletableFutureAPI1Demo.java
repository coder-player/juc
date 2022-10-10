package com.fqh.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/1 18:26:43
 */
public class CompletableFutureAPI1Demo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        /** *********获取结果API****************/
//        System.out.println(completableFuture.get());
//        System.out.println(completableFuture.get(2, TimeUnit.SECONDS)); //有限等待时间
//        System.out.println(completableFuture.join());
        /** ********如果完成则返回结果值（或抛出任何遇到的异常），否则返回给定的 valueIfAbsent。********************* */
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(completableFuture.getNow("xxx"));

        /** *************主动触发计算结果********************* */
        System.out.println(completableFuture.complete("completeValue") + "\t" + completableFuture.join());
    }
}
