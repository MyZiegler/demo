package com.wpw.demo.controller;


import com.alibaba.fastjson.JSON;
import com.wpw.demo.pojo.LinePriceBase;
import com.wpw.demo.pojo.LinePriceParam;
import com.wpw.demo.service.DataRPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * quarzcontroller
 *
 * @author limengyang
 * create 2020-02-13
 **/
@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/query")
    public String startQuartzJob(@RequestBody LinePriceParam kaOrderV) {
        LinePriceParam kaOrderVO = new LinePriceParam();
        kaOrderVO.setEndDistance(new BigDecimal(2222));
        return JSON.toJSONString(kaOrderVO);
    }




}
