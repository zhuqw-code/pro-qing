package com.zqw.exception;

import com.zqw.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.zqw.controller")
// 注解：标明该类是全局异常处理类。路径：对哪里的异常进行处理
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    // 对哪些异常进行处理
    @ResponseBody       // 设置异常拦截后相应给前端的数据格式
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result ownError(CustomException e){
        e.printStackTrace();
        return Result.error(e.getCode(), e.getMsg());
    }
}
