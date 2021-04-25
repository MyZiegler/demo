package com.wpw.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wpw.demo.annotation.ExcludeAccessToken;
import com.wpw.demo.pojo.LinePriceBase;
import com.wpw.demo.pojo.LinePriceParam;
import com.wpw.demo.pojo.ResponseResult;
import com.wpw.demo.service.DataRPCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 直客订单列表
 * <br>
 * created date 2019/7/31 10:41
 *
 * @author ChenShiYuan
 */
@RestController
@RequestMapping("/direct")
@Slf4j
@ExcludeAccessToken
public class DirectOrderListController {

    @Autowired
    private DataRPCService dataRPCService;

    @RequestMapping("/calculateLinePrice")
    public String calculateLinePrice(@RequestBody LinePriceParam kaOrderVO) {
        LinePriceBase price = dataRPCService.calculateLinePrice(kaOrderVO);

        return JSON.toJSONString(price);
    }

}
