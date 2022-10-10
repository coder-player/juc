package com.fqh.jmm;

import lombok.extern.slf4j.Slf4j;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 鸽鸽
 * @version 1.0
 * Date: 2022/9/12 21:03:53
 */
@Slf4j(topic = "c.l")
public class JavaMemoryModelDemo1 {

    // volatile ===> 可见性 每次读取都从主存读最新的值
    static volatile boolean run = true;

    // 使用synchronized也能保证共享变量可见性
    static final Object lock = new Object();

    /**
     * java内存模型 ===> 可见性问题
     * 初始状态 T1线程将run的值从主存中读取到工作内存中
     * T1线程频繁的从主存中读取ru工作内存中, 提高读取效率
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (!run) {
                        log.debug("T1 ===> 运行结束");
                        break;
                    }
                }
            }
        }, "T1");

        t1.start();
        sleep(1000);

        log.debug("Main ===> 停止T1");
        synchronized (lock) {
            run = false;
        }
    }
}
