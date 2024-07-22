package com.wenbin.controller;

import com.wenbin.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping("/testRestRequestMethod")
    public String index(){
        return "testRestRequestMethod";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getAllUser(){
        System.out.println("正在查询所有用户.....");
        return "ok";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") String id){
        System.out.println("正在通过用户ID查找用户，id = " + id);
        return "ok";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String addUser(User user) {
        System.out.println("正在添加一个用户，username:" + user.getUsername());
        return "ok";
    }

    @RequestMapping(value = "/user" , method = RequestMethod.PUT)
    public  String modifyUser(User user) {
        System.out.println("正在修改一个用户，username:" + user.getUsername());
        return "ok";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String deleteUserById(@PathVariable("id") String id) {
        System.out.println("正在删除一个用户 id = " + id);
        return "ok";
    }
}
