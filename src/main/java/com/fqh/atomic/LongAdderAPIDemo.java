package com.fqh.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/11 20:25:34
 */
public class LongAdderAPIDemo {

    public static void main(String[] args) {

        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        longAccumulator.accumulate(1); //1
        longAccumulator.accumulate(3); //4
        System.out.println(longAccumulator.get());
    }
}
