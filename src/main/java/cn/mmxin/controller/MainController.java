package cn.mmxin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
