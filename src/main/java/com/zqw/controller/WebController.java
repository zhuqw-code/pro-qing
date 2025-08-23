package com.zqw.controller;

import com.zqw.common.Result;
import com.zqw.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController       // 注解作用：该类中的方法会以json形式传递数据
public class WebController {
    @GetMapping("/hello")
    public Result sayHello(){
        // Integer x = 1 / 0;      // 模拟异常
        return Result.success("<h1>springboot project!!!</h1>");
    }

    @GetMapping("/exception")
    public Result myException(){
        if (true){
            // throw new RuntimeException("错误");    // 系统异常
            throw new CustomException("404", "无法访问到相关页面");
        }
        return Result.success();
    }
}

/**
 *      1.通过设置Result类实现数据类型传递的一致性
 *      2.如何对异常进行捕获
 */
