package com.zqw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication         // 主入口
@MapperScan("com.zqw.mapper")  // 告诉SpringBoot如何扫描mapper包
public class SpringbootProApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootProApplication.class, args);
    }
}