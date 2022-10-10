package com.fqh.cas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/9 10:47:56
 */

/**
 * 原子引用
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {

        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3", 22);
        User li4 = new User("liz4", 28);

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());

    }

    @Getter
    @ToString
    @AllArgsConstructor
    static class User {

        String userName;
        int age;
    }
}
