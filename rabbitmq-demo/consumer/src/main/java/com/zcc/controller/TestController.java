package com.zcc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zcc
 * @ClassName TestController
 * @description
 * @date 2021/10/22 14:34
 * @Version 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
