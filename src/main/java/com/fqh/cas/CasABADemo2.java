package com.fqh.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static com.fqh.utils.Sleeper.sleep;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/31 10:59:04
 */
@Slf4j(topic = "c.l")
public class CasABADemo2 {

//    static AtomicReference<String> ref = new AtomicReference<>("A");

    /**
     * 带戳记的原子引用可以通过版本号解决ABA问题
     */
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) {
        log.debug("main start...");
        // 获取A值
        // 这个共享变量被其他线程修改过 A->B->A->C
        String expect = ref.getReference();
        int expectStamp = ref.getStamp();
        log.debug("当前版本号 {}", expectStamp);
        other();
        sleep(1000);
        // 尝试修改为C
        log.debug("当前版本号 {}, 我期望的版本号 {}", expectStamp, ref.getStamp());
        log.debug("修改 A--->C {}", ref.compareAndSet(expect, "C", expectStamp, expectStamp + 1));

    }

    private static void other() {
        new Thread(() -> {
            int expectStamp = ref.getStamp();
            log.debug("当前版本号 {}", expectStamp);
            log.debug("修改 A--->B {}", ref.compareAndSet(ref.getReference(), "B", expectStamp, expectStamp + 1));
        }, "T1").start();
        sleep(500);
        new Thread(() -> {
            int expectStamp = ref.getStamp();
            log.debug("当前版本号 {}", expectStamp);
            log.debug("修改 B--->A {}", ref.compareAndSet(ref.getReference(), "A", expectStamp, expectStamp + 1));
        }, "T2").start();
    }
}
