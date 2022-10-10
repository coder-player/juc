package com.fqh.future;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/31 19:28:21
 */
public class CompletableFutureMallDemo {

    public static void main(String[] args) {

//        Student student = new Student();
//        student.setId(12).setStuName("li4").setMajor("english");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello 1234";
        });

//        System.out.println(completableFuture.get()); //编译抛出检查型异常
        System.out.println(completableFuture.join()); //运行期间才抛出异常
    }
}


@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
class Student {

    private Integer id;
    private String stuName;
    private String major;
}
