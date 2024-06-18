package com.wenbin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {


    @RequestMapping("/")
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/test/{username}/{password}")
    public String testPathPattern(@PathVariable("username") String username, @PathVariable("password") String password){
        System.out.println("username = " + username + ", password = " + password);
        return "ok";
    }
}
