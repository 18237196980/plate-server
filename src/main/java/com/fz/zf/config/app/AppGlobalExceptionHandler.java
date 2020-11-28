package com.fz.zf.config.app;

import com.fz.zf.model.common.Constast;
import com.fz.zf.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class AppGlobalExceptionHandler {

    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public ApiResult authErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("----------------------token异常----------------------");
        String message = e.getMessage();
        return ApiResult.error(Constast.ERROR, message);
    }
}
