package com.fqh.threadlocal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/17 14:14:32
 */
public class ThreadLocalDemo3 {

    volatile boolean flag;

    public static void main(String[] args) {

        ThreadLocal<String> t1 = new ThreadLocal<>(); //line1
        t1.set("1457480465@qq.com"); //line2
        t1.get();
    }
}

/**
 * ***************
 * line1: 新建一个ThreadLocal对象, t1是强引用指向这个对象
 * line2: 调用set()方法后, 新建一个Entry, 源码Entry对象的Key是弱引用指向这个对象(ThreadLocal)
 *
 * @ThreadLocal之为什么Key使用弱引用??? main线程执行完成GC回收t1强引用对象,
 * 1. 如果Entry的key(ThreadLocal)是强引用
 * 那么GC不会回收ThreadLocal这个对象, 后续可能会造成内存泄漏
 * 2. 如果Entry的key(ThreadLocal)是弱引用
 * 只要发生GC, Entry的Key就会被回收, 并且这个Key指向NULL
 * <p>
 * ThreadLocal使用弱引用【存在的问题】
 * @Entry的Key就会被回收,并且这个Key指向NULL
 * @但是Key对应的值还存在,(null,"Value"),Value没有被回收【内存泄漏问题】
 * @所以需要我们手动调用remove()--->源码 if (k == null) {
 * e.value = null;
 * tab[i] = null;
 * size--;
 * }
 * <p>
 * ******************
 */
