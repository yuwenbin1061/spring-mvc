package com.wenbin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LearnRequestMappingController {
    @RequestMapping(value = {"testValue", "testValue1"})
    public String testValue1(){
        return "ok";
    }

//    @RequestMapping(value = "/x?y/testValue")
//    @RequestMapping(value = "/x*y/testValue")
    @RequestMapping(value = "/xyz/**")
    public String testValue2(){
        return "ok";
    }

    @RequestMapping(value = "/testValue/{username}/{password}")
    public  String testValue3(@PathVariable("username") String username, @PathVariable("password") String password){
        System.out.println("username = " + username + ", password = " + password);
        return "ok";
    }
}
