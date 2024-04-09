package com.voca.eco.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping(value = "/")
@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping(value = "index")
    public String index() {

        log.info(this.getClass().getName() + "index Start And End!");

        return "/index";
    }

    @GetMapping(value = "main")
    public String main() {

        log.info(this.getClass().getName() + "main Start!");


        return "/main";
    }

}
