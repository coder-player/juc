package com.fqh.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/7/31 12:12:55
 */
@Slf4j(topic = "c.l")
public class AtomicIntegerFieldUpdaterDemo2 {

    public static void main(String[] args) {

        Student stu = new Student();

        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        System.out.println(updater.compareAndSet(stu, null, "张三"));
        System.out.println(stu);
    }
}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
