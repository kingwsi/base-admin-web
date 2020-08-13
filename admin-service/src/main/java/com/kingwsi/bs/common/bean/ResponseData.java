package com.kingwsi.bs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 同意返回对象封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseData OK() {
        return new ResponseData(200, "OK");
    }

    public static <T> ResponseData OK(T data) {
        return new ResponseData(200, "OK", data);
    }

    public static ResponseData FAIL() {
        return new ResponseData(500, "服务器错误");
    }

    public static ResponseData FAIL(String msg) {
        return new ResponseData(500, msg);
    }

    public static ResponseData FAIL(String msg, Integer code) {
        return new ResponseData(code, msg);
    }
}
