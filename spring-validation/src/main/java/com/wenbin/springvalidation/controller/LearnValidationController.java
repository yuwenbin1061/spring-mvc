package com.wenbin.springvalidation.controller;

import com.wenbin.springvalidation.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/learnValidation")
public class LearnValidationController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/add")
    public String addUser(@Valid UserDto user, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                System.out.println("字段：" + error.getField() + ",错误信息：" + error.getDefaultMessage());
                map.put("ERR_" + error.getField(), error.getDefaultMessage());
            }
            return "index";
        }
        return "ok";
    }
}
