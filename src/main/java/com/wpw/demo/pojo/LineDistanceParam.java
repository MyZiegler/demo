package com.wpw.demo.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */
@Data
@Accessors(chain = true)
public class LineDistanceParam {
    /**
     * 详细地址信息
     */
    private String address;
    /**
     *  地势代码（三级信息代码）
     */
    @NotBlank(message = "缺少站点地势代码信息")
    private String cityCode;
    /**
     * 地势名称（二级市信息名称）
     */
    private String cityName;
}

