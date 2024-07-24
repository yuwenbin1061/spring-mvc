package com.wenbin.controller;

import com.wenbin.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class LearnRestController {

    /* 使用@ResponseBody注解直接返回字符串 */
    @RequestMapping("/rest/testResponseBody01")
    public String testResponseBody01(){
       return "testResponseBody";
    }

    @RequestMapping("/rest/testResponseBody02")
    @ResponseBody
    /* 使用@ResponseBody注解返回以java对象转换为的json字符串 */
    public User testResponseBody02(){
        return new User("哮天犬", "ps001", "1","是一条好狗",13);
    }
}
