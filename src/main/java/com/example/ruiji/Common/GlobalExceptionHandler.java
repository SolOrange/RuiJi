package com.example.ruiji.Common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;


@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 拦截异常处理
     *
     * @return
     */
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());
        String msg;
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            msg = split[2] + "已存在";
        } else {
            msg="未知错误！";
        }
        return R.error(msg);
    }

    /**
     * 拦截自定义异常
     * @param ex
     * @return
     */
    @ExceptionHandler({CustomerException.class})
    public R<String> exceptionHandler(CustomerException ex) {
        return R.error(ex.getMessage());
    }
}


