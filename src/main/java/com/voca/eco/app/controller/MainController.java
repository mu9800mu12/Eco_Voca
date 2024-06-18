package com.voca.eco.app.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {


    @GetMapping(value = "index")
    public String index() {

        log.info(this.getClass().getName() + ": index Start And End!");

        return "index";
    }


    @GetMapping(value = "main")
    public String main1() {

        log.info(this.getClass().getName() + "main Start!");

        return "main";
    }



}
