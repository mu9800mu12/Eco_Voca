package com.voca.eco.app.controller;

import com.voca.eco.app.dto.MailDTO;
import com.voca.eco.app.dto.MsgDTO;
import com.voca.eco.app.service.IMailService;
import com.voca.eco.common.util.CmmUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
@Controller
public class MailController {

    private final IMailService mailService; // 메일 발송을 위한 서비스 객체를 사용하기

    /**
     * 메일 발송하기폼
     */
    @GetMapping(value = "mailForm")
    public String mailForm() throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + "mailForm Start!");

        return "mail/mailForm";
    }


    }
