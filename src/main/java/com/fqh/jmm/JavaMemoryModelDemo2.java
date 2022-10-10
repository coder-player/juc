package com.fqh.jmm;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author The End of Death
 * @version 1.0
 * Date: 2022/9/12 22:43:48
 */
@Slf4j(topic = "c.l")
public class JavaMemoryModelDemo2 {
    int x = 0;
    volatile boolean v = false;

    /**
     * java5 对volatile进行的内存语义增强 ===> 传递性
     * (1)x = 42 happens before v = true
     * (2)v = true happens before 读变量v
     * (3)x = 42 happens before 读变量v
     *
     * @param args
     */

    public static void main(String[] args) {
        JavaMemoryModelDemo2 demo2 = new JavaMemoryModelDemo2();

        new Thread(() -> {
            demo2.writer();
        }, "T1").start();
        sleep(1000);
        new Thread(() -> {
            demo2.reader();
        }, "T2").start();


    }

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v == true) {
            log.debug("x: " + x);
        }
    }
}
