package com.wpw.demo.pojo;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */

import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询站点数据实体
 */
@Data
public class LineDistanceEntity {

    /**
     *  站点地区id
     */
    private String areaId;
    /**
     * 站点地势名称
     */
    private String areaName;
    /**
     *  站点详细地址
     */
    private String fallGoodsAddress;
    /**
     *  站点id
     */
    private String fallGoodsId;
    /**
     * 站点名称
     */
    private String fallGoodsName;
    /**
     *  站点纬度
     */
    private BigDecimal latitude;
    /**
     *  站点经度
     */
    private BigDecimal longitude;

    private String poiId;


}
