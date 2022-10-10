package com.fqh.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/1 18:47:35
 */
public class CompletableFutureAPI3Demo {

    public static void main(String[] args) {

//        CompletableFuture.supplyAsync(() -> {
//            return 1;
//        }).thenApply(f -> {
//            return f + 2;
//        }).thenApply(f -> {
//            return f + 3;
//        }).thenAccept(r -> {
//            System.out.println(r + ": 被消费");
//        });

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
                .thenRun(() -> {
                }).join()); //join不到返回值

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
                .thenAccept(System.out::println).join()); //join不到返回值

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(r -> r + " resultB").join()); //join到返回值
    }
}
/**
 * ****************
 * thenAccept(Consumer<? super T> action)传入消费参数, 没有返回值
 * <p>
 * thenRun(Runnable action)传入runnable无返回结果
 * <p>
 * thenApply(Function<? super T,? extends U> fn)有返回值
 * *****************
 */