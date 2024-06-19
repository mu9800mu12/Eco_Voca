package com.voca.eco.app.controller;

import com.voca.eco.app.chat.ChatHandler;

import com.voca.eco.common.util.CmmUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Set;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class ChatController {

    /**
     * 클라이언트(인트로) > 필터(핸드셰이크) > 컨트롤러(chatroom) > 클라이언트(채팅방)
     * 핸들러 (메세지 보내기)
     *
     * 카카오톡 챗봇이라는 강력한 녀석이 존재한다.
     */


    /**
     * 채팅창 입장 전
     */
    @GetMapping(value = "intro")
    public String intro(HttpSession session) {

        log.info(this.getClass().getName() + ".intro Start!");

        String userId = (String) session.getAttribute("SS_USER_ID");

        log.info("userId : " + userId);

        log.info(this.getClass().getName() + ".intro Ends!");

        return "chat/intro";
    }

    /**
     * 채팅창 접속
     */
    @PostMapping(value = "chatroom")
    public String chatroom(HttpServletRequest request, ModelMap model, HttpSession session) {
        log.info(this.getClass().getName() + ".chatroom Start!");

        String roomName = CmmUtil.nvl(request.getParameter("roomName"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        log.info("roomName : " + roomName);
        log.info("userName : " + userName);

        model.addAttribute("roomName", roomName);
        model.addAttribute("userName", userName);

        log.info(this.getClass().getName() + ".chatroom End!");

        return "chat/chatroom";
    }

    /**
     * 채팅방 목록
     */
    @RequestMapping(value = "roomList")
    @ResponseBody
    public Set<String> roomList() {

        log.info(this.getClass().getName() + ".roomList Start!");

        log.info(this.getClass().getName() + ".roomList Ends!");

        return ChatHandler.roomInfo.keySet();

    }
}