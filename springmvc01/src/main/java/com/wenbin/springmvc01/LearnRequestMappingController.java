package com.wenbin.springmvc01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
// 可以作用与于类之上
@RequestMapping("/learnRequestMapping")
public class LearnRequestMappingController {


    // 1.也可以作用于方法之上
    // 2.value属性值是一个字符串数组，当只有一个的时候可以省略数组，直接写字符串
    // 3.可以省略value
    @RequestMapping(value = {"test01", "test001"})
    public String test01(){
        return "ok";
    }

    // 路径占位符匹配
    // @PathVariable可以取得username的值
    @RequestMapping("/user/{username}/detail")
    public String test02(@PathVariable("username") String username){
        System.out.println("username = " + username);
        return "ok";
    }
}
