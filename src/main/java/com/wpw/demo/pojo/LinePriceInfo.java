package com.wpw.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */

@Data
public class LinePriceInfo {
    /**
     * 出发市
     */
    private String startTown;
    /**
     *  目的市
     */
    private String arriveTown;

    private Integer level;

    private String startChar;
    /**
     *  吨报价（元/吨）
     */
    private BigDecimal tonPrice;
    /**
     *  方报价（元/平方米）
     */
    private BigDecimal cubePrice;
    /**
     * 距离（公里）
     */
    private BigDecimal distance;


    private String user;

    private String createTime;

    private String vstartCity;

    private String varriveCity;

    private Integer narriveCity;

    private Integer nstartCity;
    /**
     *  新方报价
     */
    private BigDecimal ncubePrice;
    /**
     *  新 吨报价
     */
    private BigDecimal ntonPrice;

    private String ndistance;
    /**
     * 时效
     */
    private String vtimeEfficiency;


}
