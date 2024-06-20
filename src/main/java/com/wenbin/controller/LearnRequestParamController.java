package com.wenbin.controller;

import com.wenbin.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/requestParam")
public class LearnRequestParamController {
    @RequestMapping("/")
    public String register(){
        return "register";
    }

    // 1.通过servlet取得请求参数
    @RequestMapping("/register1")
    public String testParam1(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        String introduction = request.getParameter("introduction");

        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("sex = " + sex);
        System.out.println("introduction = " + introduction);

        return "ok";
    }

    // 2.通过servlet取得请求参数
    @RequestMapping("/register2")
    public String testParam2(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("sex") String sex,
            @RequestParam("introduction") String introduction){
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("sex = " + sex);
        System.out.println("introduction = " + introduction);

        return "ok";
    }

    // 3.通过servlet取得请求参数
    @RequestMapping("/register3")
    public String testParam3(
            String username,
            String password,
            String sex,
            String introduction){
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("sex = " + sex);
        System.out.println("introduction = " + introduction);

        return "ok";
    }

    // 4.通过pojo获得请求参数
    @RequestMapping("/register4")
    public String testParam4(
            User user){
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());
        System.out.println("sex = " + user.getSex());
        System.out.println("introduction = " + user.getIntroduction());
        System.out.println("年龄 = " + user.getAge());

        return "ok";
    }
}
