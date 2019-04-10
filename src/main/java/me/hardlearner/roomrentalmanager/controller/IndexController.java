package me.hardlearner.roomrentalmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index2";
//        return "index3";
    }

    @GetMapping("/index")
    public String index2() {
        return "index";
    }

    @GetMapping("/empty-space")
    public String emptySpace() {
        return "empty-space";
    }
}
