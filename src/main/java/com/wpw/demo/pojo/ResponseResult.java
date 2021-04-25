package com.wpw.demo.pojo;

import lombok.NonNull;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */
public class ResponseResult<T> {
    @NonNull
    private int code;
    @NonNull
    private String message;
    private T data;

    public static <T> ResponseResult<T> ok() {
        return of(200, "OK");
    }

    public static <T> ResponseResult<T> ok(T data) {
        ResponseResult<T> responseResult = of(200, "OK");
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> prompt(int code, String message, T data) {
        ResponseResult<T> result = of(code, message);
        result.setData(data);
        return result;
    }

    @NonNull
    public int getCode() {
        return this.code;
    }

    @NonNull
    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(@NonNull int code) {
        this.code = code;
    }

    public void setMessage(@NonNull String message) {
        if (message == null) {
            throw new NullPointerException("message is marked non-null but is null");
        } else {
            this.message = message;
        }
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult() {
    }

    private ResponseResult(@NonNull int code, @NonNull String message) {
        if (message == null) {
            throw new NullPointerException("message is marked non-null but is null");
        } else {
            this.code = code;
            this.message = message;
        }
    }

    public static <T> ResponseResult<T> of(@NonNull int code, @NonNull String message) {
        return new ResponseResult(code, message);
    }

    public ResponseResult(@NonNull int code, @NonNull String message, T data) {
        if (message == null) {
            throw new NullPointerException("message is marked non-null but is null");
        } else {
            this.code = code;
            this.message = message;
            this.data = data;
        }
    }
}
