package com.wenbin.springvalidation.controller;

import com.wenbin.springvalidation.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/learnValidation")
public class LearnValidationController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/add")
    public String addUser(@Valid UserDto user) {
        return "ok";
    }
}
