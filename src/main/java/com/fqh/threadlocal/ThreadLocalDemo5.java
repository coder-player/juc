package com.fqh.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author The End of Death
 * @version 1.0
 * @since 2022/9/23 14:18:40
 */
@Slf4j(topic = "c.l")
public class ThreadLocalDemo5 {

    public static void main(String[] args) {
//    test1();
        test2();
    }

    // 一个线程内的调用 得到的是同一个Connection对象
    private static void test2() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                log.debug("{}", Utils.getConnection()); // 线程内资源共享
                log.debug("{}", Utils.getConnection());
                log.debug("{}", Utils.getConnection());
            }, "T" + (i + 1)).start();
        }
    }

    // 多个线程调用 获取属于自己的Connection对象
    private static void test1() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                log.debug("{}", Utils.getConnection());
            }, "T" + (i + 1)).start();
        }
    }

    static class Utils {
        private static final ThreadLocal<Connection> tl = new ThreadLocal<>();

        public static Connection getConnection() {
            Connection conn = tl.get(); // 获取当前线程的资源
            if (conn == null) {
                conn = innerGetConnection();
                tl.set(conn); // 将资源存入当前线程
            }
            return conn;
        }

        private static Connection innerGetConnection() {
            try {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud?serverTimezone=Asia/Shanghai", "root", "fqh");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
