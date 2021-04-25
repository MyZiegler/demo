package com.wpw.demo.pojo;

import lombok.Data;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */
@Data
public class LineDistanceBase {

    private String message;

    private Boolean success;

    private Integer totalRow;

    private Integer code;

    private Integer number;
    /**
     * 信息体
     */
    private LineDistanceData data;

    /**
     *  精拼保价状态：1 ：正常 2：没有报价线路 3：接口报错
     */
    private Integer jpQuoteStatus;
}
