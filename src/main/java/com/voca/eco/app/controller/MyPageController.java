package com.voca.eco.app.controller;

import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.IMyPageService;
import com.voca.eco.app.service.IUserService;
import com.voca.eco.app.service.impl.MyPageService;
import com.voca.eco.app.service.impl.UserService;
import com.voca.eco.common.util.CmmUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping(value = "/myPage")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final IMyPageService myPageService;

    @GetMapping(value = "myPageIndex")
    public String myPageIndex() {

        log.info(this.getClass().getName() + "myPageIndex 시작");

        return "myPage/myPageIndex";

    }

    @GetMapping(value = "readMyPage")
    public String readMyPage() {

        log.info(this.getClass().getName() + "readMyPage");

        return "myPage/readMyPage";

    }

    @GetMapping(value = "updateMyPage")
    public String updateMyPage() {

        log.info(this.getClass().getName() + "updateMyPage 시작");

        return "myPage/updateMyPage";

    }

    @ResponseBody
    @PostMapping(value = "myPageIndex")
    public UserDTO myPageIndex(HttpSession session) throws Exception {

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        return myPageService.myPageIndex(userId);
    }

}
