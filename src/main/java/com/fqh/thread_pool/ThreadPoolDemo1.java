package com.fqh.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/7 21:25:25
 */
@Slf4j(topic = "c.k")
public class ThreadPoolDemo1 {

    public static void main(String[] args) {

        /**
         * newFixedThreadPool();
         * 创建一个指定大小的线程池【没有救急线程, 阻塞队列采用无界的】
         */
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "MY_POOL_T" + t.getAndIncrement());
            }
        });

        pool.execute(() -> {
            log.debug("1");
        });
        pool.execute(() -> {
            log.debug("2");
        });
        pool.execute(() -> {
            log.debug("3");
        });

        pool.shutdown();
    }
}

/**
 * *****************
 * 线程池7大参数
 * public ThreadPoolExecutor(
 * int corePoolSize,     //核心线程数
 * int maximumPoolSize,  //最大线程数
 * long keepAliveTime,   //生存时间===>针对救急线程
 * TimeUnit unit,        //时间单位===>针对救急线程
 * BlockingQueue<Runnable> workQueue, //阻塞队列
 * ThreadFactory threadFactory,       //线程工厂===>可以为线程创建时起名
 * RejectedExecutionHandler handler)  //拒绝策略
 * {
 * <p>
 * }
 * <p>
 * <p>
 * ****************
 */