package com.wpw.demo.utils;

import lombok.*;

/**
 * 返回信息标识
 **/
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
public class ResponseResult<T> {

    /**
     * 状态码
     */
    @NonNull
    private int code;

    /**
     * 提示信息
     */
    @NonNull
    private String msg;

    /**
     * 返回的数据信息
     */
    private T data;


    /**
     * 成功信息
     */
    public static <T> ResponseResult<T> ok() {
        ResponseResult<T> responseResult = ResponseResult.of(200, "OK");
        return responseResult;
    }
    public static <T> ResponseResult<T> ok(T data) {
        ResponseResult<T> responseResult = ResponseResult.of(200, "OK");
        responseResult.setData(data);
        return responseResult;
    }


    /**
     * 错误提示信息  自定义code msg
     */
    public static <T> ResponseResult<T> prompt(int code, String msg) {
        return ResponseResult.of(code, msg);
    }

}
