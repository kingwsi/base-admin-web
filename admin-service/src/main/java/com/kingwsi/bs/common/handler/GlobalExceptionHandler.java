package com.kingwsi.bs.common.handler;

import com.kingwsi.bs.exception.CustomException;
import com.kingwsi.bs.util.bean.ResponseData;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Description: 全局异常处理
 * Name: GlobalExceptionHandler
 * Author: wangshu
 * Date: 2019/7/27 1:25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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
    public ResponseData handlerExceptionTest(CustomException ex) {
        log.warn(ex.getMessage());
        return ResponseData.FAIL(ex.getMessage(), ex.getStatus().value());
    }

    /**
     * Valid 校验异常
     *
     * @return
     * @throws MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseData handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
        return ResponseData.FAIL(fieldError.getDefaultMessage());
    }
}
