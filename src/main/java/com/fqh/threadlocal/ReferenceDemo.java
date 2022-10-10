package com.fqh.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/16 19:15:18
 */


@Slf4j(topic = "c.K")
public class ReferenceDemo {

    @Slf4j(topic = "c.K")
    static class MyObject {

        //这个方法一般不重写
        @Override
        protected void finalize() throws Throwable {
            log.info("-----------invoke finalize()");
        }
    }

    public static void main(String[] args) {

        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);
//        log.info("{}", phantomReference.get());

    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        log.info("-----GC before: {}", weakReference.get());

        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("-----GC after: {}", weakReference.get());
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
//        System.out.println("-----softReference : " + softReference);

        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----GC after: " + softReference.get());

        try {
            byte[] bytes = new byte[20 * 1024 * 1024]; //20MB的对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("-----gc after: " + softReference.get());
        }
    }

    //强引用
    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("GC before: " + myObject);
        myObject = null;
        System.gc(); //手动GC
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("GC after: " + myObject);
    }
}


