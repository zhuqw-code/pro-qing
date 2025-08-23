package com.zqw.exception;


/**
 *    自定义异常
 *      ·因为异常包含多种，系统异常，业务异常。。。如果我们只使用系统异常我们无法涵盖以上多种异常，故我们自定义异常来区分以上异常
 *      ·创建异常类，并抛出异常
 *      ·编写handle方法，实现异常的处理（如：向前端响应信息）
 *      【因为我们需要先抛异常再执行handle处理器，故我们抛异常时就需要将状态码和错误信息传递，然后在handle方法中将具体信息响应给前端】
 */
public class CustomException extends RuntimeException{
    private String code;
    private String msg;

    public CustomException(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
