package com.fqh.future;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/6/1 16:46:30
 * 需求
 * @1.同一款产品,同时搜索出在各大电商平台的售价
 * @2.同一款产品,同时搜索出在同一个电商平台下,各个入驻商家的售价
 */
public class CompletableFutureMallCase {

    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao"),
            new NetMall("pdd"),
            new NetMall("tianmao")
    );

    /**
     * 一家一家的查询
     *
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        List<String> stringList = list
                .stream()
                .map(netMall ->
                        String.format(productName + " in %s price is %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
        return stringList;
    }

    /**
     * 使用CompletableFuture
     *
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall ->
                        CompletableFuture.supplyAsync(() ->
                                String.format(productName + " in %s price is %.2f",
                                        netMall.getNetMallName(),
                                        netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        List<String> list1 = getPrice(list, "mysql"); //3xxx ms
        List<String> list1 = getPriceByCompletableFuture(list, "mysql"); //1xxx ms
        list1.forEach(item -> {
            System.out.println(item);
        });
        long endTime = System.currentTimeMillis();
        System.out.println("----耗时: " + (endTime - startTime) + "毫秒");

    }
}


class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2
                + productName.charAt(0);

    }
}
