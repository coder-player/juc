package com.fqh.synchroized;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/26 17:03:06
 */
@Slf4j(topic = "c.k")
public class BiasLock1 {

    public static void main(String[] args) throws InterruptedException {

        Dog d = new Dog();
        d.hashCode(); // 会撤销对象的偏向状态
        log.debug(ClassLayout.parseInstance(d).toPrintable());

        synchronized (d) {
            log.debug(ClassLayout.parseInstance(d).toPrintable());
        }

        log.debug(ClassLayout.parseInstance(d).toPrintable());
    }

    /**
     * ****************
     * 偏向锁默认是延迟加载===>使用jvm参数 -XX:BiasedLockingStartupDelay=0禁用延迟
     * 001--->101 标志
     * ---------------------------------------------------
     * 线程ID(23位) | Epoch(2位) | 分代年龄(4位) | 1 | 01(2位)
     * ---------------------------------------------------
     * *****************
     */
}

class Dog {

}
