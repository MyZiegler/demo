package com.wpw.demo.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 异常处理
 **/
@Getter
@Setter
public class RestException extends RuntimeException{

    /**
     * 错误信息
     */
    public String msg;

    /**
     * 错误码
     */
    public int code;


    public RestException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }



    public RestException(String msg, Throwable e) {
        super(msg, e);
        this.code = 500;
        this.msg = msg;
    }


    public RestException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
