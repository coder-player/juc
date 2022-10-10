package com.fqh.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/31 16:39:01
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());

        Thread t1 = new Thread(futureTask, "T1");
        t1.start();

        System.out.println(futureTask.get());
    }
}

/**
 * 异步任务: 多线程/有返回/异步任务
 */
class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("-----------come in call()");
        return "hello Callable";
    }
}