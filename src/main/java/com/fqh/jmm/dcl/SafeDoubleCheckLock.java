package com.fqh.jmm.dcl;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/5 13:48:21
 * 单例懒汉式=====>安全的双重检查锁
 */
public class SafeDoubleCheckLock {

    // 添加volatile禁止指令重排序
    private volatile static Instance instance;

    public static Instance getInstance() {
        if (instance == null) { //线程B发现instance为null, instance还没有初始化对象
            synchronized (SafeDoubleCheckLock.class) {
                if (instance == null) { //线程A进行操作
                    /** *****************
                     * 分解为: {           有了volatile关键字修饰, instance的初始化操作不会进行指令重排序
                     *     memory = allocate() //1.分配对象的内存空间
                     *     ctorInstance(memory); //2.初始化对象
                     *     instance = memory; //3.设置instance指向刚分配的内存地址
                     * }
                     * ***************** */
                    instance = new Instance();
                }
            }
        }
        return instance;
    }


    static class Instance {
    }
}
