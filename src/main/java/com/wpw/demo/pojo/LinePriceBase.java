package com.wpw.demo.pojo;

import lombok.Data;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */

@Data
public class LinePriceBase {

    private String message;

    private Boolean success;

    private Integer totalRow;

    private Integer code;

    private Integer number;
    /**
     * 信息体
     */
    private LinePriceData data;
}

