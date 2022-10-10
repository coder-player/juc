package com.fqh.jmm.dcl;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/5 13:48:53
 * 单例懒汉式=====>不安全的双重检查锁
 */
public class UnSafeDoubleCheckLock {

    private static Instance instance;

    public static Instance getInstance() {
        if (instance == null) { //线程B发现instance不为null, 其实instance还没有初始化对象
            synchronized (UnSafeDoubleCheckLock.class) {
                if (instance == null) { //线程A进行操作
                    /** *****************
                     * 分解为: {
                     *     memory = allocate() //1.分配对象的内存空间
                     *     ctorInstance(memory); //2.初始化对象
                     *     instance = memory; //3.设置instance指向刚分配的内存地址
                     * }
                     * ***************** */
                    instance = new Instance(); //这一步存在指令重排序问题 3可能在2之前
                }
            }
        }
        return instance;
    }


    static class Instance {
    }
}