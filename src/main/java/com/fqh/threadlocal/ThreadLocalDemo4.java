package com.fqh.threadlocal;

/**
 * @author The End of Death
 * @version 1.0
 * @since 2022/9/23 11:19:27
 */

/**
 * ThreadLocal内部结构<br>
 * JDK8前: ThreadLocal关联一个ThreadLocalMap对象 key-Thread对象 val-值对象<br>
 * JDK8后: 每一个Thread关联一个ThreadLocalMap对象 key-ThreadLocal对象 val-值对象<br>
 * 好处<br>
 * (1)减少了ThreadLocalMap存储的Entry数量 jdk8前的Entry数量是由 ===> 线程数决定的<br>
 * 现在是由ThreadLocal数量决定 实际开发场景中 Thread数量远远多于 ThreadLocal数量<br>
 * (2)当Thread销毁之后 对应的ThreadLocalMap也会随之销毁 能减少内存的使用(不能避免内存泄漏问题)
 */
public class ThreadLocalDemo4 {

    public static final ThreadLocal<Integer> threadLocal1 = ThreadLocal.withInitial(() -> 1);
    public static final ThreadLocal<Integer> threadLocal2 = ThreadLocal.withInitial(() -> 2);
    public static final ThreadLocal<Integer> threadLocal3 = ThreadLocal.withInitial(() -> 3);
    public static final ThreadLocal<Integer> threadLocal4 = ThreadLocal.withInitial(() -> 4);
    public static final ThreadLocal<Integer> threadLocal5 = ThreadLocal.withInitial(() -> 5);
    public static final ThreadLocal<Integer> threadLocal6 = ThreadLocal.withInitial(() -> 6);
    public static final ThreadLocal<Integer> threadLocal7 = ThreadLocal.withInitial(() -> 7);
    public static final ThreadLocal<Integer> threadLocal8 = ThreadLocal.withInitial(() -> 8);
    public static final ThreadLocal<Integer> threadLocal9 = ThreadLocal.withInitial(() -> 9);
    public static final ThreadLocal<Integer> threadLocal10 = ThreadLocal.withInitial(() -> 10);


    public static void main(String[] args) {
        System.out.println(threadLocal1.get());
        System.out.println(threadLocal2.get());
        System.out.println(threadLocal3.get());
        System.out.println(threadLocal4.get());
        System.out.println(threadLocal5.get());
        System.out.println(threadLocal6.get());
        System.out.println(threadLocal7.get());
        System.out.println(threadLocal8.get());
        System.out.println(threadLocal9.get());
        System.out.println(threadLocal10.get());

        threadLocal5.remove();
    }
}
