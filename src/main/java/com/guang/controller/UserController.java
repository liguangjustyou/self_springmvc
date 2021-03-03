package com.guang.controller;

import com.guang.annotation.Controller;
import com.guang.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @RequestMapping("/user/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response){
        System.out.println("user中的findAll方法");
    }

    @RequestMapping("/user/add")
    public void add(HttpServletRequest request, HttpServletResponse response){
        System.out.println("user中的add方法");
    }

    @RequestMapping("/user/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response){
        System.out.println("user中的delete方法");
    }
}
