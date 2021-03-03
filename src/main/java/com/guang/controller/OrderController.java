package com.guang.controller;

import com.guang.annotation.Controller;
import com.guang.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OrderController {

    @RequestMapping("/order/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response){
        System.out.println("order中的findAll方法");
    }

    @RequestMapping("/order/add")
    public void add(HttpServletRequest request, HttpServletResponse response){
        System.out.println("order中的add方法");
    }

    @RequestMapping("/order/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response){
        System.out.println("order中的delete方法");
    }
}
