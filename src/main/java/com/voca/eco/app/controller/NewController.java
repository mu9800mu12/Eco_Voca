package com.voca.eco.app.controller;

import com.voca.eco.app.dto.NewsDTO;
import com.voca.eco.app.service.INewsService;
import com.voca.eco.common.util.CmmUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/news")
@Controller
public class NewController {

    private final INewsService newsService;

    @ResponseBody
    @PostMapping(value = "getNewsList")
    public NewsDTO getNewsList(HttpServletRequest request) throws Exception{

        log.info(this.getClass().getName() + " getNewsList");

        String word = CmmUtil.nvl(request.getParameter("word"));

        log.info(word);

        return newsService.getNewsList(word);
    }
}
