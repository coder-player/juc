* 调用线程的start()

# NEW ===> RUNNABLE

* 线程获取对象同步锁后
* 调用对象的wait() RUNNABLE ===> WAITING
* 调用join()的线程

# RUNNABLE ===> WAITING

# RUNNABLE <==> WAITING <===> BLOCKED

# 调用对象的notify(), notifyAll(), 线程的interrupt()时

* 竞争锁成功 WAITING ===> RUNNABLE
* 竞争锁失败 WAITING ===> BLOCKED


