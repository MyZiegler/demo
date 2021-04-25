package com.wpw.demo.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */
@Data
public class  LinePriceParam  {
    /**
     * 出发地（省份到市）
     */
    private String startAreaID;
    /**
     *  目的地 （省份到市）
     */
    private String  endAreaID;

    /**
     * 出发地地势名称（二级市信息名称）
     */
    private String startCityName;

    /**
     * 出发地地势名称（二级市信息名称）
     */
    private String endCityName;
    /**
     * 出发地详细地址
     */
    private String startAddress;
    /**
     * 目的地详细地址
     */
    private String endAddress;
    /**
     * 吨数
     */
    private BigDecimal ton;
    /**
     *  立方数
     */
    private BigDecimal cube;


    /**
     *   出发地站点信息
     */
    private LineDistanceEntity startFallGoodsinfo;
    /**
     *  目的地站点信息
     */
    private LineDistanceEntity endFallGoodsinfo;
    /**
     *  出发地距离
     */
    private BigDecimal startDistance;
    /**
     *  目的地距离
     */
    private BigDecimal endDistance;


    private String requestStartDistanceParamContent;

    private String responseStartDistanceParamContent;

    private String requestEndDistanceParamContent;

    private String responseEndDDistanceParamContent;

    private String requestPriceParamContent;

    private String responsePriceParamContent;

    public boolean chechParam(){
        if(StringUtils.isBlank(startAreaID) || StringUtils.isBlank(endAreaID) ||
                StringUtils.isBlank(startAddress) || StringUtils.isBlank(endAddress) ||
                Objects.isNull(cube) || Objects.isNull(ton)){
            return false;
        }
        return true;
    }

}
