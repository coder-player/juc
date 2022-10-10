package com.fqh.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/2 10:49:29
 */
public class CompletableFutureFastDemo {

    public static void main(String[] args) {

        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println("A come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playerA";
        });

        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println("B come in");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playerB";
        });

        CompletableFuture<String> result = playA.applyToEither(playB, f -> {
            return f + " is Winner";
        });

        System.out.println(Thread.currentThread().getName() + "\t" + result.join());
    }
}
