package com.fqh.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.print.Book;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/9 11:21:37
 */


@Slf4j
public class AtomicStampedDemo {

    public static void main(String[] args) {

        Book javaBook = new Book(1, "javaBook");
        AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<>(javaBook, 1);
        System.out.println((stampedReference.getReference() + "\t" + stampedReference.getStamp()));

        Book mysqlBook = new Book(1, "mysqlBook");
        boolean b;
        b = stampedReference.compareAndSet(javaBook, mysqlBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(b + "\t" + stampedReference.getReference() + "\t" + stampedReference.getStamp());

        b = stampedReference.compareAndSet(mysqlBook, javaBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(b + "\t" + stampedReference.getReference() + "\t" + stampedReference.getStamp());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class Book {
        private int id;
        private String bookName;
    }
}
