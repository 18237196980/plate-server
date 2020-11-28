package com.fz.zf.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class ApiResult<T> implements Serializable {
    public static final int SUCCESS = 1;
    public static final int ERROR = 0;
    public static final int ERROR_TOKEN = -100;
    private Integer code = 0;
    private String message = "";
    private T data;

    public ApiResult() {
    }

    public ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == 1;
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult(1, "", (Object)null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult(1, "", data);
    }

    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult(1, message, data);
    }

    public static <T> ApiResult<T> error() {
        return new ApiResult(0, "", (Object)null);
    }

    public static <T> ApiResult<T> error(String message) {
        return new ApiResult(0, message, (Object)null);
    }

    public static <T> ApiResult<T> error(int code, String message) {
        return new ApiResult(code, message, (Object)null);
    }

    public static ApiResult errorToken(String message) {
        return error(-100, message);
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        if (StringUtils.isEmpty(message)) {
            message = "";
        }

        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "ApiResult{code=" + this.code + ", message='" + this.message + '\'' + ", data=" + this.data + '}';
    }

}
