package com.fqh.forkjoin;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/6 17:14:00
 */
@Slf4j(topic = "c.k")
public class ForkJoinPoolDemo1 {

    public static void main(String[] args) throws Exception {

        ThreadPoolExecutor pool1 = new ThreadPoolExecutor(
                4,
                4,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadPoolExecutor.CallerRunsPolicy());

        ForkJoinPool pool2 = new ForkJoinPool(4);

        Task task1 = new Task("task1", pool1, 0);
        Task task2 = new Task("task2", pool1, 1);
        Task task3 = new Task("task3", pool1, 1);
        Task task4 = new Task("task4", pool1, 1);
        Task task5 = new Task("task5", pool1, 10);
        Task task6 = new Task("task6", pool1, 10);
        Task task7 = new Task("task7", pool1, 1);
        Task task8 = new Task("task8", pool1, 1);
        Task task9 = new Task("task9", pool1, 1);

        task1.dependentTasks.add(task2);
        task1.dependentTasks.add(task3);
        task1.dependentTasks.add(task4);
        task1.dependentTasks.add(task5);
        task1.dependentTasks.add(task6);
        task2.dependentTasks.add(task7);
        task3.dependentTasks.add(task8);
        task4.dependentTasks.add(task9);

        /**
         * 任务执行流程依赖关系
         * Task1 ===> Task2 1s ===> Task7 1s
         *       ===> Task3 1s ===> Task8 1s
         *       ===> Task4 1s ===> Task9 1s
         *       ===> Task5 10s
         *       ===> Task6 10s
         */
        log.debug("Start Time: {}", new Date());
//    pool1.submit(task1);

        pool2.invoke(task1);
    }

    static class Task extends RecursiveTask<String> implements Callable<String> {
        String name;
        ThreadPoolExecutor pool;
        long execTime;
        List<Task> dependentTasks = new ArrayList<>();

        public Task(String name, ThreadPoolExecutor pool, long execTime) {
            this.name = name;
            this.pool = pool;
            this.execTime = execTime;
        }

        @SneakyThrows
        @Override
        public String call() throws Exception {
            List<Future<String>> futures = dependentTasks.stream()
                    .map(task -> pool.submit(task))
                    .collect(Collectors.toList());
            for (Future<String> future : futures) {
                future.get();
            }
            Thread.sleep(execTime * 1000);
            log.debug("taskName: {}", name);
            return "time: " + new Date() + " taskName: " + name;
        }

        @SneakyThrows
        @Override
        protected String compute() {
            for (Task dependentTask : dependentTasks) {
                dependentTask.fork();
            }
            for (Task dependentTask : dependentTasks) {
                dependentTask.join();
            }
            Thread.sleep(execTime * 1000);
            log.debug("taskName: {}", name);
            return "";
        }
    }
}
