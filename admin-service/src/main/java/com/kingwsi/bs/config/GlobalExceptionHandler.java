package com.kingwsi.bs.config;

import com.kingwsi.bs.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 全局异常处理
 * Name: GlobalExceptionHandler
 * Author: wangshu
 * Date: 2019/7/27 1:25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ResponseEntity<Object> customExceptionResponseEntity = new ResponseEntity<>(handlerExceptionTest(ex), status);
//        return customExceptionResponseEntity;
//    }

//    @ExceptionHandler(value = CustomException.class)
//    public CustomException handlerException(Exception ex) {
//        StackTraceElement stackTraceElement = ex.getStackTrace()[0];
//        CustomException customException = new CustomException(ex.getMessage());
//        log.error("异常:[{}],在[{}]第[{}]行,方法:[{}]", ex.getMessage(), stackTraceElement.getClassName(), stackTraceElement.getLineNumber(), stackTraceElement.getMethodName());
//        return customException;
//    }

    @ExceptionHandler(value = CustomException.class)
    public Map<String, Object> handlerExceptionTest(CustomException ex) {
        log.warn(ex.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("errCode", ex.getStatus().value());
        return map;
    }
}
