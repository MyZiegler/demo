package com.wpw.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * quarzcontroller
 *
 * @author limengyang
 * create 2020-02-13
 **/
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/query")
    public String startQuartzJob() {
       return "query";
    }

}
