package com.sgj.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class testController {

    @RequestMapping("/")
    public String test(){

        return "hehe";
    }
}
