package com.fqh.volatile_keyword;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/7 11:29:29
 * 两阶段终止模式
 */
@Slf4j(topic = "c.TwoStageTermination")
public class TwoStageTermination {

    public static void main(String[] args) {
        TwoStageThread tst = new TwoStageThread();
        tst.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("停止监控...");
        tst.stop();
    }

}

@Slf4j(topic = "c.TwoStageThread")
class TwoStageThread {

    // 监控线程
    private Thread monitorThread;

    private volatile boolean stop = false;

    public void start() {
        monitorThread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (stop) {
                    log.info("处理后事...");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("执行监控记录...");
                } catch (InterruptedException e) {
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }
}