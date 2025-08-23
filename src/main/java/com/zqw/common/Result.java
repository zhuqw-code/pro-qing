package com.zqw.common;

/**
 * 封装全局响应结果Result
 */
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result success(){        // 无参
        Result result = new Result();
        result.setCode("200");
        result.setMsg("请求成功！！！");
        return result;
    }

    public static Result success(Object data){  // 有参
        Result result = Result.success();
        result.setData(data);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode("500");
        result.setMsg("操作异常");
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
