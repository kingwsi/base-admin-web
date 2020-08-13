package com.kingwsi.bs.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Description: 自定义异常封装
 * Name: CustomException
 * Author: wangshu
 * Date: 2019/7/27 1:24
 */

public class CustomException extends RuntimeException {
    private HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        //父类的构造方法本身会传message进去
        super(message);
        this.status = status;
    }

    public CustomException(String message) {
        //父类的构造方法本身会传message进去
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
