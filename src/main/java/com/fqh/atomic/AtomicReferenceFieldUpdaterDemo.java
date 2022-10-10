package com.fqh.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/11 19:53:53
 */
public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {

        MyResource resource = new MyResource();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                resource.init(resource);
            }, String.valueOf(i)).start();
        }
    }

}

@Slf4j(topic = "c.MyResource")
class MyResource {

    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyResource, Boolean> referenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyResource.class, Boolean.class, "isInit");

    public void init(MyResource resource) {
        if (referenceFieldUpdater.compareAndSet(resource, Boolean.FALSE, Boolean.TRUE)) {
            log.info("----> start init, cost 3s....");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("----> over init....");
        } else {
            log.info("----> 已经有线程在进行初始化工作...");
        }
    }
}
/**
 * ****************
 * 需求:
 * 多个线程并发初始化一个类的初始化方法, 如果未被初始化, 将执行初始化工作
 * 要求只能被初始化一次, 只有一个线程操作成功
 * *****************
 */