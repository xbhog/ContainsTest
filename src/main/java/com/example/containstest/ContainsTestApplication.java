package com.example.containstest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 22511
 */
@SpringBootApplication
@MapperScan("com.example.containstest.containsTestDemo.mapper")
public class ContainsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainsTestApplication.class, args);
    }

}
