package com.fqh.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/31 17:14:06
 */
public class FutureAPIDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "-----come in");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task over";
        });

        Thread t1 = new Thread(futureTask, "T1");
        t1.start();

        System.out.println(Thread.currentThread().getName() + "\t ---忙其他任务");

        while (true) {
            //不停的轮询
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task任务正在处理中........请等待");
            }
        }

//        System.out.println(futureTask.get());
//        System.out.println(futureTask.get(3, TimeUnit.SECONDS));
    }
}
/**
 * 1.get一般放在程序后, task任务还没完就get, 容易导致线程被阻塞
 * 2.指定get等待时长---.>超时抛出---->java.util.concurrent.TimeoutException
 * 3.isDone不停的轮询导致耗费cpu资源
 */