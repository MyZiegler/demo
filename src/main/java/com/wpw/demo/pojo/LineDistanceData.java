package com.wpw.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */
@Data
public class LineDistanceData {

    /**
     *  距离信息
     */
    private BigDecimal distance;
    /**
     *   站点信息
     */
    private LineDistanceEntity fallGoodsPlaceInfoEntity;
}
