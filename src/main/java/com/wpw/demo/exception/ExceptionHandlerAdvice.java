package com.wpw.demo.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wpw.demo.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 全局异常拦截
 **/
@ControllerAdvice(basePackages = "com.lecar.datac.controller")
public class ExceptionHandlerAdvice {

    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    /**
     * 请求的 JSON 参数在请求体内的参数校验
     *
     * @param e 异常信息
     * @return 返回数据
     * @throws JsonProcessingException jackson 的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult handleBindException1(MethodArgumentNotValidException e) throws JsonProcessingException {
        StringBuilder strBuilder = new StringBuilder();
        for (ObjectError fieldError : e.getBindingResult().getAllErrors()) {
            strBuilder.append(fieldError.getDefaultMessage()).append("\n");
        }
        return ResponseResult.prompt(0,"参数校验错误");
    }

    /**
     * 自定义 错误信息
     */
    @ExceptionHandler(RestException.class)
    @ResponseBody
    public void returnInfo(HttpServletResponse response, RestException e) throws IOException {
        LOGGER.error("", e);
        response.reset();
        response.resetBuffer();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        PrintWriter os = response.getWriter();
        String json = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":null}", e.getCode(), e.getMsg());
        os.write(json);
        os.flush();
        os.close();
    }


    @ExceptionHandler(RestException.class)
    @ResponseBody
    public void returnBiInfo(HttpServletResponse response, RestException e) throws IOException {
        LOGGER.error("", e);
        response.reset();
        response.resetBuffer();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        PrintWriter os = response.getWriter();
        String json = String.format("{\"success\":false,\"message\":\"%1s\",\"data\":null,\"totalRow\":0,\"code\":%2s}",  e.getMessage(),e.code);
        os.write(json);
        os.flush();
        os.close();
    }
    /**
     * 系统异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void returnInfo(HttpServletResponse response, Exception e) throws IOException {
        LOGGER.error("", e);
        response.reset();
        response.resetBuffer();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        PrintWriter os = response.getWriter();
        String json = String.format("{\"success\":false,\"message\":\"%s\",\"data\":null,\"totalRow\":0}", e.getMessage());
        os.write(json);
        os.flush();
        os.close();
    }

}
