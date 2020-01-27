package com.devsawe.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloResource {

    @RequestMapping("/")
    public String hello(){
        return "Hello World";
    }
}
