package com.kingwsi.bs.util.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {
    private Integer code;
    private String message;
    private Object data;

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseData<T> OK() {
        return new ResponseData<>(200, "OK");
    }

    public static <T> ResponseData<T> OK(T data) {
        return new ResponseData<>(200, "OK", data);
    }

    public static <T> ResponseData<T> FAIL() {
        return new ResponseData<>(500, "服务器错误");
    }
}
